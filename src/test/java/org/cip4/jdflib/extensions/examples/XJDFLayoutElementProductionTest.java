/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2017 The International Cooperation for the Integration of
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
package org.cip4.jdflib.extensions.examples;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoShapeElement.EnumShapeType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.datatypes.JDFRectangle;
import org.cip4.jdflib.extensions.ResourceHelper;
import org.cip4.jdflib.extensions.SetHelper;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.resource.JDFShapeElement;
import org.cip4.jdflib.resource.process.JDFLayoutElementProductionParams;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.resource.process.JDFShapeDef;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 *
 */
public class XJDFLayoutElementProductionTest extends JDFTestCaseBase
{
	/**
	 *
	 */
	@Test
	public void testLoPPage()
	{
		XJDFHelper xjdfHelper = new XJDFHelper(ElementName.LAYOUTELEMENTPRODUCTION, "PageSize", null);
		xjdfHelper.setTypes(JDFConstants.LAYOUTELEMENTPRODUCTION);
		SetHelper shLO = xjdfHelper.getCreateResourceSet(ElementName.LAYOUTELEMENTPRODUCTIONPARAMS, EnumUsage.Input);
		SetHelper shC = xjdfHelper.getCreateResourceSet(XJDFConstants.Content, null);
		SetHelper shRL = xjdfHelper.getCreateResourceSet(ElementName.RUNLIST, EnumUsage.Output);
		ResourceHelper rh = shLO.appendPartition(null, true);
		JDFLayoutElementProductionParams lop = (JDFLayoutElementProductionParams) rh.getResource();
		ResourceHelper rhc = shC.appendPartition(null, true);
		ResourceHelper rhrl = shRL.appendPartition(null, true);
		KElement content = rhc.getResource();
		content.setAttribute(AttributeName.SOURCECLIPBOX, new JDFRectangle(0, 0, 23, 31).scaleFromCM().toString());
		content.setAttribute(AttributeName.SOURCETRIMBOX, new JDFRectangle(0, 0, 21, 29.7).scaleFromCM().toString());
		content.setAttribute(AttributeName.CONTENTTYPE, "Page");

		JDFRunList rl = (JDFRunList) rhrl.getResource();
		rl.setNPage(4);
		lop.setAttribute(AttributeName.CONTENTREFS, rhc.getID());
		xjdfHelper.cleanUp();
		cleanSnippets(xjdfHelper);
		writeTest(xjdfHelper, "resources/LayoutElementPage.xjdf");
	}

	/**
	 *
	 */
	@Test
	public void testLoPShape()
	{
		XJDFHelper xjdfHelper = new XJDFHelper(ElementName.LAYOUTELEMENTPRODUCTION, "ShapeDef", null);
		xjdfHelper.setTypes(JDFConstants.LAYOUTELEMENTPRODUCTION);
		SetHelper shLO = xjdfHelper.getCreateResourceSet(ElementName.LAYOUTELEMENTPRODUCTIONPARAMS, EnumUsage.Input);
		SetHelper shC = xjdfHelper.getCreateResourceSet(ElementName.SHAPEDEF, null);
		SetHelper shRL = xjdfHelper.getCreateResourceSet(ElementName.RUNLIST, EnumUsage.Output);
		ResourceHelper rh = shLO.appendPartition(null, true);
		JDFLayoutElementProductionParams lop = (JDFLayoutElementProductionParams) rh.getResource();
		ResourceHelper rhc = shC.appendPartition(null, true);
		ResourceHelper rhrl = shRL.appendPartition(null, true);
		JDFShapeElement s = ((JDFShapeDef) rhc.getResource()).appendShape();
		s.setDDESCutType(101);
		s.setShapeType(EnumShapeType.Path);
		s.setCutPath("10 0 l");
		JDFRunList rl = (JDFRunList) rhrl.getResource();
		rl.setNPage(1);
		lop.setAttribute(XJDFConstants.ShapeDefRef, rhc.getID());
		xjdfHelper.cleanUp();
		cleanSnippets(xjdfHelper);
		writeTest(xjdfHelper, "resources/LayoutElementShape.xjdf");
	}

	/**
	 *
	 */
	@Test
	public void testLoPBox()
	{
		XJDFHelper xjdfHelper = new XJDFHelper(ElementName.LAYOUTELEMENTPRODUCTION, "ShapeDef", null);
		xjdfHelper.setTypes(JDFConstants.LAYOUTELEMENTPRODUCTION);
		SetHelper shLO = xjdfHelper.getCreateResourceSet(ElementName.LAYOUTELEMENTPRODUCTIONPARAMS, EnumUsage.Input);
		SetHelper shC = xjdfHelper.getCreateResourceSet(ElementName.SHAPEDEF, null);
		SetHelper shRL = xjdfHelper.getCreateResourceSet(ElementName.RUNLIST, EnumUsage.Output);
		ResourceHelper rh = shLO.appendPartition(null, true);
		JDFLayoutElementProductionParams lop = (JDFLayoutElementProductionParams) rh.getResource();
		ResourceHelper rhc = shC.appendPartition(null, true);
		ResourceHelper rhrl = shRL.appendPartition(null, true);
		JDFShapeDef s = ((JDFShapeDef) rhc.getResource());
		s.appendFileSpec().setURL("file://myserver/myshare/olive.dd3");
		JDFRunList rl = (JDFRunList) rhrl.getResource();
		rl.setNPage(1);
		lop.setAttribute(XJDFConstants.ShapeDefRef, rhc.getID());
		xjdfHelper.cleanUp();
		cleanSnippets(xjdfHelper);
		writeTest(xjdfHelper, "resources/LayoutElementBox.xjdf");
	}

	/**
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Before
	@Override
	protected void setUp() throws Exception
	{
		KElement.setLongID(false);
	}
}
