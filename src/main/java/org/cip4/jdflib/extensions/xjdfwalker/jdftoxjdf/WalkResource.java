/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2022 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment may appear in the software itself, if and wherever such third-party acknowledgments
 * normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf;

import java.util.List;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumResourceClass;

// //////////////////////////////////////////////////////////////////////////////
/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 *
 *         walker for the various resource sets
 */
public class WalkResource extends WalkJDFElement
{
	/**
	 *
	 */
	public WalkResource()
	{
		super();
	}

	/**
	 * @param jdf
	 * @param xjdf
	 * @return the created resource
	 */
	@Override
	public KElement walk(final KElement jdf, final KElement xjdf)
	{
		final JDFResource r = (JDFResource) jdf;
		if (r.getNoOp() || jdf.getNodeName().equals(xjdf.getNodeName()))
			return null;
		final KElement newResLeaf = super.walk(jdf, xjdf);

		if (newResLeaf != null)
		{
			newResLeaf.removeAttribute(AttributeName.ID);
			moveAttribsToBase(xjdf, newResLeaf);
			removeDeprecatedResourceAttribs(r, newResLeaf);
			final boolean bRoot = isRootXJDFResource(xjdf);
			if (bRoot)
			{
				removeDeprecatedResourceAttribs(r, xjdf);
				xjdf.removeAttribute(AttributeName.STATUS);
				xjdf.removeAttribute(AttributeName.STATUSDETAILS);
			}
		}
		return newResLeaf;
	}

	/**
	 * @see org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf.WalkJDFElement#updateAttributes(org.cip4.jdflib.datatypes.JDFAttributeMap)
	 */
	@Override
	protected void updateAttributes(final JDFAttributeMap map)
	{
		if (!jdfToXJDF.isRetainAll())
		{
			map.remove(AttributeName.DOCCOPIES);
			map.remove(AttributeName.SETCOPIES);
			map.remove(AttributeName.RUNTAG);
			map.remove(AttributeName.AMOUNTPRODUCED);
			map.remove(AttributeName.AMOUNTREQUIRED);
			map.remove(AttributeName.PARTUSAGE);
			map.remove(AttributeName.MINSTATUS);
			map.remove(AttributeName.NOOP);
		}
		super.updateAttributes(map);
	}

	/**
	 * @param xjdf
	 * @param newResLeaf
	 */
	protected void moveAttribsToBase(final KElement xjdf, final KElement newResLeaf)
	{
		final boolean bRoot = isRootXJDFResource(xjdf);
		final List<String> resAttribs = JDFToXJDFDataCache.getResAttribs();
		for (final String attrib : resAttribs)
		{
			if (newResLeaf.hasAttribute(attrib))
			{
				if (bRoot)
				{
					xjdf.moveAttribute(attrib, newResLeaf);
				}
				else
				{
					newResLeaf.removeAttribute(attrib);
				}
			}
		}
	}

	/**
	 *
	 * @param xjdf
	 * @return
	 */
	private boolean isRootXJDFResource(final KElement xjdf)
	{
		final String localName = xjdf == null ? null : xjdf.getLocalName();
		final boolean bRoot = XJDFConstants.Intent.equals(localName) || "Parameter".equals(localName) || XJDFConstants.Resource.equals(localName);
		return bRoot;
	}

	/**
	 *
	 * @param eNew
	 * @param details
	 * @param key
	 * @param oldKey
	 */
	protected void moveToDetails(final KElement eNew, final String details, final String key, final String oldKey)
	{
		final String val = eNew.getNonEmpty(key);
		if (val != null)
		{
			eNew.getCreateElement(details).moveAttribute(key, eNew, oldKey, null, null);
		}
	}

	/**
	 * @param r
	 * @param newResLeaf
	 */
	private void removeDeprecatedResourceAttribs(final JDFResource r, final KElement newResLeaf)
	{
		newResLeaf.removeAttributes(r.getPartIDKeys());
		newResLeaf.removeAttribute(AttributeName.PARTIDKEYS);
		newResLeaf.removeAttribute(AttributeName.PARTUSAGE);
		newResLeaf.removeAttribute(AttributeName.CLASS);
		newResLeaf.removeAttribute(AttributeName.LOCKED);
		newResLeaf.removeAttribute(AttributeName.SPAWNIDS);
		newResLeaf.removeAttribute(AttributeName.SPAWNSTATUS);
		newResLeaf.removeAttribute(AttributeName.AGENTNAME);
		newResLeaf.removeAttribute(AttributeName.AGENTVERSION);
	}

	/**
	 *
	 *
	 * @param r
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected String getClassName(final JDFResource r)
	{
		if (r == null)
		{
			return null;
		}

		EnumResourceClass resourceClass = r.getResourceClass();
		if (resourceClass == null)
		{
			final KElement r2 = new JDFDoc(r.getLocalName()).getRoot();
			if (r2 instanceof JDFResource)
			{
				r2.init();
				resourceClass = ((JDFResource) r2).getResourceClass();
			}
		}
		String className = jdfToXJDF.isParameterSet() ? XJDFHelper.PARAMETER : XJDFConstants.Resource;
		if (resourceClass == null)
		{
			return className;
		}
		else if (resourceClass.equals(EnumResourceClass.Intent))
		{
			className = (jdfToXJDF.wantProduct) ? resourceClass.getName() : null;
		}
		else if (resourceClass.equals(EnumResourceClass.PlaceHolder))
		{
			return null;
		}
		else if (!EnumResourceClass.Parameter.equals(resourceClass))
		{
			return XJDFConstants.Resource;
		}
		return className;
	}

	/**
	 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
	 * @param toCheck
	 * @return true if it matches
	 */
	@Override
	public boolean matches(final KElement toCheck)
	{
		return toCheck instanceof JDFResource;
	}

	/**
	 *
	 * @param linkTarget
	 * @return
	 */
	protected boolean isProductResource(final JDFResource linkTarget)
	{
		return EnumResourceClass.Intent.equals(linkTarget.getResourceClass());
	}

}