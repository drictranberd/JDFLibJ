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

package org.cip4.jdflib.resource;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.JDFLayoutPreparationParams.StrippingConverter;

/**
 * all kinds of fun tests around JDF 1.2 vs JDF 1.3 Layouts also some tests for automated layout
 * 
 */
public class JDFLayoutPreparationParamsTest extends JDFTestCaseBase
{

	JDFNode n = null;
	JDFLayoutPreparationParams lpp = null;

	/**
	 * 
	 */
	public void testConvertStripMarks()
	{
		lpp.setFrontMarkList(new VString("RegisterMark FoldMark", null));
		lpp.setBackMarkList(new VString("RegisterMark ColorBar", null));
		final StrippingConverter sc = lpp.convertToStripping(null);
		final JDFStrippingParams strippingParams = sc.getStrippingParams();
		assertEquals(strippingParams, n.getResource(ElementName.STRIPPINGPARAMS, EnumUsage.Input, 0));
		final VElement v = strippingParams.getChildElementVector(ElementName.STRIPMARK, null);
		assertEquals("2 front + 2 back", v.size(), 4);
	}

	/**
	 * 
	 */
	public void testConvertStripSimple()
	{
		lpp.convertToStripping(null);
		assertNull(n.getResource(ElementName.LAYOUTPREPARATIONPARAMS, null, 0));
		assertNotNull(n.getResource(ElementName.STRIPPINGPARAMS, EnumUsage.Input, 0));
		assertEquals(EnumType.Stripping, n.getEnumType());
	}

	/**
	 * 
	 */
	public void testConvertStripDeep()
	{
		JDFNode nProd = new JDFDoc("JDF").getJDFRoot();
		nProd.setType(EnumType.Product);
		n = (JDFNode) nProd.moveElement(n, null);
		lpp = (JDFLayoutPreparationParams) n.getResource(ElementName.LAYOUTPREPARATIONPARAMS, null, 0);
		assertNotNull(lpp);
		nProd.getCreateResourcePool().moveElement(lpp, null);
		lpp.convertToStripping(n);
		assertNull(n.getResource(ElementName.LAYOUTPREPARATIONPARAMS, null, 0));
		assertNotNull(n.getResource(ElementName.STRIPPINGPARAMS, EnumUsage.Input, 0));
		assertEquals(EnumType.Stripping, n.getEnumType());

	}

	/**
	 * 
	 */
	public void testConvertStripSimpleCombined()
	{
		n.setCombined(new VString("LayoutPreparation Imposition", null));
		lpp.convertToStripping(null);
		final VString types = n.getTypes();
		assertTrue(types.contains(EnumType.Stripping.getName()));
		assertTrue(types.contains(EnumType.Imposition.getName()));
		assertFalse(types.contains(EnumType.LayoutPreparation.getName()));
	}

	/**
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		n = new JDFDoc("JDF").getJDFRoot();
		n.setType(EnumType.LayoutPreparation);
		lpp = (JDFLayoutPreparationParams) n.addResource(ElementName.LAYOUTPREPARATIONPARAMS, EnumUsage.Input);

	}

	/**
	 * @see junit.framework.TestCase#toString()
	 */
	@Override
	public String toString()
	{
		return n.toString();
	}

}