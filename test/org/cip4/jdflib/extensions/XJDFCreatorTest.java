/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2009 The International Cooperation for the Integration of
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

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoConventionalPrintingParams.EnumWorkStyle;
import org.cip4.jdflib.auto.JDFAutoMedia.EnumMediaType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFXYPair;
import org.cip4.jdflib.goldenticket.MISCPGoldenTicket;
import org.cip4.jdflib.node.JDFNode;

/**
 * 
 * class that tests/demonstrates creation of XJDF either from scratch or by conversion from golden tickets
 *
 * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class XJDFCreatorTest extends JDFTestCaseBase
{

	KElement theXJDF = null;
	XJDFHelper theHelper = null;

	/**
	 * 
	 * 
	 */
	public void testFromCPGoldenTicket()
	{
		MISCPGoldenTicket gt = new MISCPGoldenTicket(2, EnumVersion.Version_1_4, 2, 2, true, null);
		gt.assign(null);
		JDFNode n = gt.getNode();
		n.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp + "cpGoldenTicket.jdf", 2, false);
		XJDF20 jdfToXJD = new XJDF20();
		jdfToXJD.bMergeLayout = true;
		KElement xjdf = jdfToXJD.makeNewJDF(n, null);
		XMLDoc d = xjdf.getOwnerDocument_KElement();
		d.write2File(sm_dirTestDataTemp + "cpGoldenTicket.xjdf", 2, false);
	}

	/**
	 * 
	 * 
	 */
	public void testFromCPFromScratch()
	{
		theHelper = new XJDFHelper(null);
		theXJDF = theHelper.getRoot();
		theXJDF.setAttribute("Types", "InkZoneCalculation ConventionalPrinting");
		SetHelper nih = theHelper.appendParameter("NodeInfo");
		nih.setUsage(EnumUsage.Input);
		JDFAttributeMap sheetMap = new JDFAttributeMap("SheetName", "S1");
		PartitionHelper niS1 = nih.getCreatePartition(sheetMap);
		niS1.getResource().setAttribute("Amount", "5000");

		SetHelper cpSetHelper = theHelper.appendResource(ElementName.CONVENTIONALPRINTINGPARAMS);
		cpSetHelper.setUsage(EnumUsage.Input);
		cpSetHelper.getCreatePartition(sheetMap).getResource().setAttribute(AttributeName.WORKSTYLE, EnumWorkStyle.Perfecting.getName());
		SetHelper mediaSetHelper = theHelper.appendResource(ElementName.MEDIA);
		mediaSetHelper.setUsage(EnumUsage.Input);
		PartitionHelper mediaHelper = mediaSetHelper.getCreatePartition(sheetMap);
		KElement mediaPart = mediaHelper.getPartition();
		mediaPart.setAttribute("Brand", "TestBrand");
		mediaPart.setAttribute("ProductID", "ID");
		KElement media = mediaHelper.getResource();
		media.setAttribute("Dimension", new JDFXYPair(72, 49).scaleFromCM().toString(), null);
		media.setAttribute(AttributeName.MEDIATYPE, EnumMediaType.Paper.getName());

		theXJDF.getOwnerDocument_KElement().write2File(sm_dirTestDataTemp + "cpFromScratch.jdf", 2, false);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 * @throws Exception
	*/
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		theXJDF = new JDFDoc("XJDF").getRoot();
		theHelper = new XJDFHelper(theXJDF);

	}

	/**
	 * @see junit.framework.TestCase#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return "XJDFCreatorTest: " + theHelper;
	}
}
