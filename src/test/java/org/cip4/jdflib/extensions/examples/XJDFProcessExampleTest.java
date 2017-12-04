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

import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoConventionalPrintingParams.EnumWorkStyle;
import org.cip4.jdflib.auto.JDFAutoMedia.EnumMediaType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFNodeInfo;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFXYPair;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.extensions.ProductHelper;
import org.cip4.jdflib.extensions.ResourceHelper;
import org.cip4.jdflib.extensions.SetHelper;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.process.JDFContact.EnumContactType;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.util.JDFDate;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 *
 */
public class XJDFProcessExampleTest extends JDFTestCaseBase
{
	/**
	*
	*
	*/
	@Test
	public final void testCombined()
	{
		final XJDFHelper xjdfHelper = new XJDFHelper("CombinedExample", null, null);
		xjdfHelper.addType(EnumType.Interpreting.getName(), 0);
		xjdfHelper.addType(EnumType.Rendering.getName(), -1);
		xjdfHelper.addType(EnumType.DigitalPrinting.getName(), -1);

		cleanSnippets(xjdfHelper);
		writeTest(xjdfHelper, "processes/CombinedExample.xjdf");
	}

	/**
	*
	*
	*/
	@Test
	public void testDrops()
	{
		final XJDFHelper xjdfHelper = new XJDFHelper("splitDelivery", null, null);
		xjdfHelper.setTypes(JDFConstants.PRODUCT);
		final ProductHelper product = xjdfHelper.getCreateRootProduct(0);
		product.setAmount(30);
		product.setProductType("Book");
		product.setID("IDBook");
		final SetHelper shc = xjdfHelper.getCreateSet(XJDFConstants.Resource, ElementName.CONTACT, EnumUsage.Input);
		final SetHelper shdp = xjdfHelper.getCreateSet(XJDFConstants.Resource, ElementName.DELIVERYPARAMS, EnumUsage.Input);
		for (int i = 1; i < 3; i++)
		{
			final JDFAttributeMap map = new JDFAttributeMap("DropID", "Drop" + i);
			final ResourceHelper rhdp = shdp.getCreatePartition(map, true);
			final KElement dropItem = rhdp.getResource().appendElement(ElementName.DROPITEM);
			dropItem.setAttribute(AttributeName.AMOUNT, "" + (i * 10));
			dropItem.setAttribute(XJDFConstants.ItemRef, product.getID());
			map.put(XJDFConstants.ContactType, EnumContactType.Delivery.getName());
			final ResourceHelper rhc = shc.getCreatePartition(map, true);
			rhc.getResource().appendElement(ElementName.ADDRESS).setAttribute(AttributeName.CITY, "city" + i);
			rhc.getResource().appendElement(ElementName.PERSON).setAttribute(AttributeName.FIRSTNAME, "Name" + i);
		}

		cleanSnippets(xjdfHelper);
		writeTest(xjdfHelper, "processes/deliverydrops.xjdf");
	}

	/**
	*
	*
	*/
	@Test
	public final void testQualityControl()
	{
		final XJDFHelper xjdfHelper = new XJDFHelper("QualityControlExample", null, null);
		xjdfHelper.addType(EnumType.ConventionalPrinting.getName(), 0);
		xjdfHelper.addType(EnumType.QualityControl.getName(), -1);

		xjdfHelper.getCreateSet(ElementName.COMPONENT, EnumUsage.Output);
		final SetHelper qqp = xjdfHelper.getCreateSet(ElementName.QUALITYCONTROLPARAMS, EnumUsage.Input);
		final ResourceHelper qpr = qqp.appendPartition(null, true);
		qpr.getRoot().appendElement("cc:CxF", "http://colorexchangeformat.com/CxF3-core").setText("CxF data is in here");
		qpr.getResource().setAttribute(AttributeName.SAMPLEINTERVAL, "42");
		cleanSnippets(xjdfHelper);
		writeRoundTripX(xjdfHelper.getRoot(), "QualityControlCxF");

	}

	/**
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		JDFElement.setLongID(false);
		super.setUp();
	}
}
