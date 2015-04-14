/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2015 The International Cooperation for the Integration of 
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
package org.cip4.jdflib.extensions.xjdfwalker.xjdftojdf;

import java.util.Vector;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.ProductHelper;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.node.JDFNode.EnumType;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
 */
public class WalkProductList extends WalkXElement
{
	/**
	 * 
	 */
	public WalkProductList()
	{
		super();
	}

	/**
	 * @param e
	 * @return the root, else null if we are in a second pass
	 */
	@Override
	public KElement walk(final KElement e, final KElement trackElem)
	{
		final boolean bFirst = xjdfToJDFImpl.foundProductList;
		xjdfToJDFImpl.foundProductList = true;
		KElement eXJDF = e.getParentNode_KElement();
		XJDFHelper h = new XJDFHelper(eXJDF);
		// only convert products in the first pass
		// TODO rethink product conversion switch
		int numProductHelpers = h.numProductHelpers(true);
		if (xjdfToJDFImpl.createProduct && (!xjdfToJDFImpl.foundProduct || numProductHelpers > 1))
		{
			if (!EnumType.Product.equals(xjdfToJDFImpl.currentJDFNode.getEnumType()))
				xjdfToJDFImpl.createProductRoot();
			xjdfToJDFImpl.firstproductInList = numProductHelpers <= 1;
		}
		Vector<ProductHelper> vRoot = h.getRootProductHelpers();
		Vector<ProductHelper> vOther = h.getProductHelpers();
		if(vRoot!=null && vOther!=null){
			vOther.removeAll(vRoot);
			for (ProductHelper ph : vOther)
			{
				e.moveElement(ph.getRoot(), null);
			}
		}

		KElement theReturn = xjdfToJDFImpl.currentJDFNode;
		if (!"Product".equals(xjdfToJDFImpl.currentJDFNode.getType()))
			theReturn = xjdfToJDFImpl.jdfDoc.getJDFRoot();
		e.deleteNode();
		return xjdfToJDFImpl.createProduct && !bFirst ? theReturn : null;
	}

	/**
	 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
	 * @param toCheck
	 * @return true if it matches
	 */
	@Override
	public boolean matches(final KElement toCheck)
	{
		return super.matches(toCheck) && ProductHelper.PRODUCTLIST.equals(toCheck.getLocalName());
	}
}