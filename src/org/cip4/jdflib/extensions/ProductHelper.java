/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2010 The International Cooperation for the Integration of
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
 *    Alternately, this acknowledgment mrSubRefay appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior writtenrestartProcesses()
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
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIrSubRefAL DAMAGES (INCLUDING, BUT NOT
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
 * originally based on software restartProcesses()
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *
 */
package org.cip4.jdflib.extensions;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;

/**
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class ProductHelper
{
	/**
	 * 
	 */
	public static boolean partitionProducts = false;
	protected KElement theProduct;

	/**
	 * @param product
	 */
	public ProductHelper(KElement product)
	{
		theProduct = product;
		theProduct.appendAnchor(null);
	}

	/**
	 * 
	 */
	public void setRoot()
	{
		KElement list = theProduct.getParentNode_KElement();
		list.appendAttribute("RootProducts", theProduct.getID(), null, " ", true);
	}

	/**
	 * @param name
	 * @return
	 */
	public IntentHelper getCreateIntent(String name)
	{
		IntentHelper ih = getIntent(name);
		if (ih == null)
		{
			KElement intent = theProduct.appendElement("Intent");
			ih = new IntentHelper(intent);
			intent.appendElement(name);
			intent.setAttribute("Name", name);
		}
		return ih;
	}

	/**
	 * @param name
	 * @return
	 */
	public IntentHelper getIntent(String name)
	{
		KElement intent = theProduct.getChildWithAttribute("Intent", "Name", null, name, 0, true);
		return intent == null ? null : new IntentHelper(intent);
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return "ProductHelper: " + theProduct;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount)
	{
		theProduct.setAttribute(AttributeName.AMOUNT, amount, null);
	}

	/**
	 * @param phCover
	 * @param amount 
	 */
	public void setChild(ProductHelper phCover, int amount)
	{
		KElement e = theProduct.getChildWithAttribute("ChildProduct", "Childref", null, phCover.theProduct.getID(), 0, true);
		if (e == null)
		{
			e = theProduct.appendElement("ChildProduct");
			e.copyAttribute("ChildRef", phCover.theProduct, "ID", null, null);
		}
		if (amount > 0)
			e.setAttribute("Amount", amount, null);
	}

	/**
	 * @return 
	 * 
	 */
	public KElement getProduct()
	{
		return theProduct;
	}

}
