/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment mrSubRefay appear in the software itself, if and wherever such third-party
 * acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior writtenrestartProcesses() permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIrSubRefAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software restartProcesses() copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 */
package org.cip4.jdflib.extensions;

import java.util.Collection;
import java.util.Vector;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.util.ContainerUtil;
import org.cip4.jdflib.util.StringUtil;

/**
 *
 * XJDF splitter that splits based on the types list
 *
 * @author rainer prosi
 *
 */
public class ProcessXJDFSplit extends AbstractXJDFSplit
{
	private final Vector<VString> groups;

	/**
	 * @return the groups
	 */
	protected Vector<VString> getGroups()
	{
		return groups;
	}

	/**
	 *
	 */
	public ProcessXJDFSplit()
	{
		super();
		groups = new Vector<>();
	}

	/**
	 *
	 * @see org.cip4.jdflib.extensions.AbstractXJDFSplit#splitXJDF(org.cip4.jdflib.extensions.XJDFHelper)
	 */
	@Override
	public Collection<XJDFHelper> splitXJDF(final XJDFHelper root)
	{
		final Vector<VString> newTypes = splitTypes(root);
		final Vector<XJDFHelper> ret = new Vector<>();
		if (newTypes != null && newTypes.size() > 0)
		{
			final VString allTypes = root.getTypes();
			for (final VString types : newTypes)
			{
				final XJDFHelper h = root.clone();
				processSingle(h, types, allTypes);
				ret.add(h);
			}
			consolidateExchangeResources(ret);
		}
		else
		{
			ret.add(root);
		}
		return ret;
	}

	/**
	 *
	 * @param h
	 * @param types
	 * @param allTypes
	 */
	protected void processSingle(final XJDFHelper h, final VString types, final VString allTypes)
	{
		fixCategory(h, types, allTypes);
		h.setJobPartID(getJobPartID(h, types));
		h.setTypes(types);
		h.setID(null);
		fixInOutLinks(h, allTypes);
	}

	/**
	 *
	 * @param h
	 * @param types
	 * @param allTypes
	 */
	protected void fixCategory(final XJDFHelper h, final VString types, final VString allTypes)
	{
		if (!JDFConstants.PRODUCT.equals(types.get(0)) || ContainerUtil.equals(types, allTypes))
		{
			h.removeAttribute(AttributeName.CATEGORY, null);
		}
	}

	protected String getJobPartID(final XJDFHelper xjdf, final VString types)
	{
		String jobPartID = xjdf.getJobPartID();
		if (jobPartID == null)
			jobPartID = "Part_";
		return jobPartID + StringUtil.setvString(types, "_", ".", null);
	}

	/**
	 *
	 * @param root
	 *
	 * @return the list of types to split into, null is a flag for no split
	 */
	protected Vector<VString> splitTypes(final XJDFHelper root)
	{
		final Vector<VString> ret = new Vector<>();
		final VString types = calcTypes(root);
		if (types == null || types.size() <= 1)
		{
			// only one element - no need to split
			return null;
		}
		boolean hasProduct = false;
		while (types.size() > 0)
		{
			final VString overlap = extractTypes(types);
			if (overlap.contains(XJDFConstants.Product))
			{
				ret.insertElementAt(overlap, 0);
				hasProduct = true;
			}
			else
			{
				ret.add(overlap);
			}
		}
		if (!hasProduct && ret.size() > 1)
		{
			ret.insertElementAt(new VString(XJDFConstants.Product, null), 0);
		}
		return ret.size() == 0 ? null : ret;
	}

	/**
	 *
	 * @param types
	 * @return
	 */
	protected VString extractTypes(final VString types)
	{
		final String first = types.get(0);
		VString overlap = null;
		for (final VString group : groups)
		{
			if (group.contains(first))
			{
				final VString newOverlap = types.getOverlapping(group);
				if ((newOverlap != null) && ((overlap == null) || (overlap.size() < newOverlap.size())))
				{
					overlap = newOverlap;
				}
			}
		}

		if (overlap == null)
		{
			overlap = new VString();
			overlap.add(first);
		}
		types.removeAll(overlap);
		return overlap;
	}

	/**
	 * ensure that we have the appropriate types setting for the given product list
	 *
	 * @param root
	 * @return
	 */
	protected VString calcTypes(final XJDFHelper root)
	{
		final boolean hasType = root.indexOfType(JDFConstants.PRODUCT, 0) >= 0;
		final Vector<ProductHelper> productHelpers = root.getProductHelpers();
		if (!hasType && productHelpers != null)
		{
			root.addType(XJDFConstants.Product, 0);
		}
		VString types = root.getTypes();
		if (types == null)
			types = new VString(XJDFConstants.Product, null);
		return types;
	}

	/**
	 * add a group to split together
	 *
	 * @param group
	 */
	public void addGroup(final VString group)
	{
		if (group != null && !group.isEmpty())
		{
			groups.add(group);
			ContainerUtil.unify(groups);
		}
	}

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " [groups=" + groups + "]";
	}
}
