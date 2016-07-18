/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2016 The International Cooperation for the Integration of 
 * Processes in  Prepress, Press and Postpress (CIP4).  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        The International Cooperation for the Integration of 
 *        Processes in  Prepress, Press and Postpress (www.cip4.org)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of 
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior written
 *    permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For
 * details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR
 * THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the The International Cooperation for the Integration 
 * of Processes in Prepress, Press and Postpress and was
 * originally based on software 
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG 
 * copyright (c) 1999-2001, Agfa-Gevaert N.V. 
 *  
 * For more information on The International Cooperation for the 
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *  
 * 
 */
package org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf;

import java.util.Vector;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.extensions.PartitionHelper;
import org.cip4.jdflib.extensions.SetHelper;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.jmf.JDFResourceInfo;
import org.cip4.jdflib.resource.JDFResource;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
 */
public class WalkResourceInfo extends WalkJDFSubElement
{

	/**
	 * 
	 */
	public WalkResourceInfo()
	{
		super();
	}

	/**
	 * @param resInfo
	 * @return the created resource
	 */
	@Override
	public KElement walk(final KElement resInfo, final KElement xjdf)
	{
		final JDFResourceInfo ri = (JDFResourceInfo) resInfo;
		final KElement eNew = super.walk(ri, xjdf);

		final VElement vr = ri.getChildElementVector(null, null);
		int nRes = 0;
		for (KElement e : vr)
		{
			if (e instanceof JDFResource)
			{
				final JDFResource r = (JDFResource) e;
				if (nRes == 0)
				{
					setResource(ri, r, eNew);
				}
				else
				{
					setResource(null, r, eNew);
				}
				r.deleteNode();
				nRes++;
			}
		}
		moveToResourceSet((JDFResourceInfo) eNew);
		updateInfos((JDFResourceInfo) eNew);
		return eNew;
	}

	void updateInfos(JDFResourceInfo eNew)
	{
		VElement v = eNew.getChildElementVector(XJDFConstants.ResourceSet, null);
		int size = v.size();
		if (size > 1)
		{
			KElement parent = eNew.getParentNode_KElement();
			if (parent != null)
			{
				int n = 0;
				VElement vRI = new VElement();
				vRI.add(eNew);
				for (int i = 1; i < size; i++)
				{
					vRI.add(parent.copyElement(eNew, null));
				}
				for (KElement ri : vRI)
				{
					for (int ii = size - 1; ii >= 0; ii--)
					{
						if (ii != n)
						{
							ri.removeChild(XJDFConstants.ResourceSet, null, ii);
						}
					}
					n++;
				}
			}
		}
	}

	/**
	 * 
	 * @param ri
	 * @param eNew
	 */
	private void moveToResourceSet(JDFResourceInfo ri)
	{
		VJDFAttributeMap vPartMap = ri.getPartMapVector();
		String resName = ri.getXPathAttribute("ResourceSet/@Name", null);
		KElement set = ri.getChildWithAttribute(XJDFConstants.ResourceSet, AttributeName.NAME, null, resName, 0, true);
		if (set == null)
		{
			set = ri.appendElement(XJDFConstants.ResourceSet);
			set.setAttribute(AttributeName.NAME, resName);
		}

		set.moveAttribute(AttributeName.PROCESSUSAGE, ri);
		set.moveAttribute(AttributeName.ORIENTATION, ri);

		SetHelper sh = new SetHelper(set);
		Vector<PartitionHelper> newParts = sh.getCreatePartitions(vPartMap, false);
		for (PartitionHelper ph : newParts)
		{
			//TODO use correct amounts
			ph.setAmount(ri.getActualAmount(), null, true);
		}
		ri.removeAttribute(AttributeName.RESOURCENAME);
	}

	/**
	 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
	 * @param toCheck
	 * @return true if it matches
	 */
	@Override
	public boolean matches(final KElement toCheck)
	{
		return !jdfToXJDF.isRetainAll() && toCheck instanceof JDFResourceInfo;
	}

	/**
	 * @see org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf.WalkJDFSubElement#updateAttributes(org.cip4.jdflib.datatypes.JDFAttributeMap)
	 */
	@Override
	protected void updateAttributes(JDFAttributeMap map)
	{
		map.remove(AttributeName.LOTCONTROLLED);
		map.remove(AttributeName.DEVICEID);
		map.remove(AttributeName.LEVEL);
		super.updateAttributes(map);
	}

	/**
	 * @see org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf.WalkJDFElement#makeRefElements(org.cip4.jdflib.core.JDFElement)
	 */
	@Override
	void makeRefElements(JDFElement je)
	{
		// nop
	}
}