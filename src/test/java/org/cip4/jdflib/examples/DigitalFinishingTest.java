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
package org.cip4.jdflib.examples;

import java.util.Vector;
import java.util.zip.DataFormatException;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoComponent.EnumAutomation;
import org.cip4.jdflib.auto.JDFAutoComponent.EnumComponentType;
import org.cip4.jdflib.auto.JDFAutoIdentificationField.EnumPosition;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFPartAmount;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFRectangle;
import org.cip4.jdflib.jmf.JDFCommand;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage.EnumFamily;
import org.cip4.jdflib.jmf.JDFMessage.EnumType;
import org.cip4.jdflib.jmf.JDFPipeParams;
import org.cip4.jdflib.jmf.JMFBuilderFactory;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumProcessUsage;
import org.cip4.jdflib.pool.JDFAmountPool;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.resource.JDFResource.EnumPartUsage;
import org.cip4.jdflib.resource.devicecapability.JDFNameEvaluation;
import org.cip4.jdflib.resource.devicecapability.JDFTerm.EnumTerm;
import org.cip4.jdflib.resource.process.JDFComponent;
import org.cip4.jdflib.resource.process.JDFExpr;
import org.cip4.jdflib.resource.process.JDFIdentificationField;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.resource.process.JDFMetadataMap;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.JDFSpawn;
import org.junit.Test;

/**
 * jmf pipe example file test
 * @author rainer prosi
 * @date Mar 3 2013
 */
public class DigitalFinishingTest extends JDFTestCaseBase
{
	/**
	 *
	 */
	public DigitalFinishingTest()
	{
		super();
	}

	/**
	 *
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		KElement.setLongID(false);
		JMFBuilderFactory.setSenderID(null, "SenderID");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testNearLineSimpleBarcode()
	{
		JDFDoc jdfDoc = new JDFDoc("JDF");
		JDFNode n = jdfDoc.getJDFRoot();
		n.setJobID("SimpeBarcode");
		n.setType(JDFNode.EnumType.ProcessGroup);

		JDFNode idp = n.addCombined(new VString("DigitalPrinting", null));
		JDFComponent c = (JDFComponent) idp.addResource(ElementName.COMPONENT, null);
		c.setAutomation(EnumAutomation.Dynamic);
		c = (JDFComponent) c.addPartition(EnumPartIDKey.SetIndex, "0~-1");
		idp.ensureLink(c, EnumUsage.Output, null);
		JDFIdentificationField barcode = c.appendIdentificationField();
		barcode.setBoundingBox(new JDFRectangle(6, 6, 66, 66));
		barcode.setPosition(EnumPosition.Front);
		barcode.setValueFormat("%6s/Set%i/%i_Page%i/%i");
		barcode.setValueTemplate("jobID SetIndex TotalSets PoolSheetIndex TotalSheetsInPool");

		JDFNode booklet = n.addCombined(new VString("Collecting Stitching", null));
		booklet.ensureLink(c, EnumUsage.Input, EnumProcessUsage.Cover);

		idp = new JDFSpawn(idp).spawn();
		new JDFSpawn(idp).unSpawnChild(idp);
		writeTest(idp, "SimpleBarcodeIDP.jdf", true, null);

		booklet = new JDFSpawn(booklet).spawn();
		new JDFSpawn(booklet).unSpawnChild(booklet);
		writeTest(booklet, "SimpleBarcodeFinishing.jdf", true, null);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testNearLineJDFBookletBarcode()
	{
		JDFDoc jdfDoc = new JDFDoc("JDF");
		JDFNode n = jdfDoc.getJDFRoot();
		n.setJobID("J1");
		n.setType(JDFNode.EnumType.ProcessGroup);

		JDFNode idp = n.addCombined(new VString("DigitalPrinting", null));
		JDFComponent c = (JDFComponent) idp.addResource(ElementName.COMPONENT, null);
		c.setAutomation(EnumAutomation.Dynamic);
		c = (JDFComponent) c.addPartition(EnumPartIDKey.SetIndex, "0~-1");
		idp.ensureLink(c, EnumUsage.Output, null);
		JDFComponent cover = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Cover");
		cover.setSurfaceCount(2);
		JDFIdentificationField barcode = c.appendIdentificationField();
		barcode.setBoundingBox(new JDFRectangle(6, 6, 66, 66));
		barcode.setPosition(EnumPosition.Front);
		barcode.setValueFormat("Set%i/%i_Page%i/%i%.1s");
		barcode.setValueTemplate("SetIndex,TotalSets,PoolSheetIndex,TotalSheetsInPool,MetaFoo");

		JDFMetadataMap meta1 = (JDFMetadataMap) barcode.appendElement(ElementName.METADATAMAP);
		meta1.setName("RunTag");
		JDFExpr exp11 = meta1.appendExpr();
		exp11.setValue("Cover");
		JDFNameEvaluation n1 = (JDFNameEvaluation) exp11.appendTerm(EnumTerm.NameEvaluation);
		n1.setValueList(new VString("b", null));
		n1.setPath("@MetaFoo");
		JDFExpr exp12 = meta1.appendExpr();
		exp12.setValue("Body");
		JDFNameEvaluation n2 = (JDFNameEvaluation) exp12.appendTerm(EnumTerm.NameEvaluation);
		n2.setPath("@MetaFoo");
		n2.setValueList(new VString("c", null));

		JDFComponent body = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Body");
		body.setSurfaceCount(-1);

		JDFNode booklet = n.addCombined(new VString("Collecting Stitching", null));
		booklet.ensureLink(cover, EnumUsage.Input, EnumProcessUsage.Cover);
		booklet.linkResource(body, EnumUsage.Input, null);
		writeTest(jdfDoc, "BookletBarcode.jdf");

		idp = new JDFSpawn(idp).spawn();
		new JDFSpawn(idp).unSpawnChild(idp);
		writeTest(idp, "BookletBarcodeIDP.jdf", false, null);

		booklet = new JDFSpawn(booklet).spawn();
		new JDFSpawn(booklet).unSpawnChild(booklet);
		writeTest(booklet, "BookletBarcodeFinishing.jdf", false, null);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testNearLineJDFBookletPipe()
	{
		JDFDoc jdfDoc = new JDFDoc(ElementName.JDF);
		JDFNode n = jdfDoc.getJDFRoot();
		n.setVersion(EnumVersion.Version_1_5);
		n.setJobID("J1");
		n.setType(JDFNode.EnumType.ProcessGroup);

		JDFNode idp = n.addCombined(new VString("DigitalPrinting", null));

		JDFRunList rl = (JDFRunList) idp.addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.setAttribute("Automation", "Dynamic");

		idp.addResource(ElementName.DIGITALPRINTINGPARAMS, EnumUsage.Input);

		JDFComponent c = (JDFComponent) idp.addResource(ElementName.COMPONENT, null);
		c.setAttribute("Automation", "Dynamic");
		c.setPipeID("PipeSheet");
		c.setPipeProtocol("JMFPush");
		c.setPipeURL("http:foo.com");
		c.setComponentType(EnumComponentType.PartialProduct, EnumComponentType.Sheet);

		c = (JDFComponent) c.addPartition(EnumPartIDKey.SetIndex, "0~-1");
		JDFResourceLink rlIDP = idp.ensureLink(c, EnumUsage.Output, null);
		rlIDP.setAmount(1, null);

		JDFComponent cover = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Cover");
		cover.setSurfaceCount(2);
		JDFComponent body = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Body");
		body.setSurfaceCount(-1);

		JDFNode booklet = n.addCombined(new VString("Collecting Stitching", null));
		booklet.ensureLink(cover, EnumUsage.Input, EnumProcessUsage.Cover);
		booklet.addResource(ElementName.STITCHINGPARAMS, EnumUsage.Input);
		JDFComponent outComponent = (JDFComponent) booklet.addResource(ElementName.COMPONENT, EnumUsage.Output);
		outComponent.setComponentType(EnumComponentType.FinalProduct, EnumComponentType.Block);
		JDFResourceLink rlOutComp = booklet.ensureLink(c, EnumUsage.Output, null);
		rlOutComp.setAmount(1, null);

		jdfDoc.write2File(sm_dirTestDataTemp + "BookletPipe.jdf", 2, false);
		idp = new JDFSpawn(idp).spawn();
		new JDFSpawn(idp).unSpawnChild(idp);
		writeTest(idp, "BookletPipeIDP.jdf", false, null);

		booklet = new JDFSpawn(booklet).spawn();
		new JDFSpawn(booklet).unSpawnChild(booklet);
		writeTest(booklet, "BookletPipeFinishing.jdf", false, null);
	}

	/**
	 *
	 *
	 */
	public void testNearLineJDFBookletPipeMetaData()
	{
		JDFDoc jdfDoc = new JDFDoc("JDF");
		JDFNode n = jdfDoc.getJDFRoot();
		n.setJobID("J1");
		n.setType(JDFNode.EnumType.ProcessGroup);
		JDFNode idp = n.addCombined(new VString("DigitalPrinting", null));
		JDFComponent c = (JDFComponent) idp.addResource(ElementName.COMPONENT, null);
		c.setAttribute("Automation", "Dynamic");
		c.setPipeID("PipeSheet");
		c.setPartUsage(EnumPartUsage.Sparse);

		c = (JDFComponent) c.addPartition(EnumPartIDKey.SetIndex, "0~-1");
		idp.ensureLink(c, EnumUsage.Output, null);

		JDFMedia media = (JDFMedia) n.addResource("Media", EnumUsage.Input);
		JDFMedia m1 = (JDFMedia) media.addPartition(EnumPartIDKey.Option, "BodyMedia");
		JDFMedia m2 = (JDFMedia) media.addPartition(EnumPartIDKey.Option, "RichCoverMedia");
		JDFMedia m3 = (JDFMedia) media.addPartition(EnumPartIDKey.Option, "PoorCoverMedia");

		JDFComponent cover = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Cover");
		cover.setSurfaceCount(2);
		JDFComponent coverRich = (JDFComponent) cover.addPartition(EnumPartIDKey.Metadata0, "Rich");
		coverRich.refMedia(m2);
		JDFComponent coverPoor = (JDFComponent) cover.addPartition(EnumPartIDKey.Metadata0, "Poor");
		coverPoor.refMedia(m3);

		JDFComponent body = (JDFComponent) c.addPartition(EnumPartIDKey.DocTags, "Body");
		body.setSurfaceCount(-1);
		body.refMedia(m1);

		JDFNode booklet = n.addCombined(new VString("Collecting Stitching", null));
		booklet.ensureLink(cover, EnumUsage.Input, EnumProcessUsage.Cover);
		booklet.linkResource(body, EnumUsage.Input, null);
		writeTest(jdfDoc, "BookletPipeMeta.jdf");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPipePushSet()
	{
		JDFJMF jmf = JMFBuilderFactory.getJMFBuilder(null).createJMF(EnumFamily.Command, EnumType.PipePush);
		jmf.setSenderID("Guru");
		JDFCommand command = jmf.getCreateCommand(0);
		command.setXMLComment("The initial push: cover + 7 body sheets");
		command.setType(EnumType.PipePush);
		command.setSenderID("Printer");
		JDFPipeParams pp = createPipeParams(command);
		createAmountPool(pp, 0, 7, 7);

		command = jmf.appendCommand();
		command.setXMLComment("The 2nd push: cover + 5 body sheets");
		command.setType(EnumType.PipePush);
		command.setSenderID("Printer");
		pp = createPipeParams(command);
		createAmountPool(pp, 1, 5, 5);

		command = jmf.appendCommand();
		command.setXMLComment("paper jam in finisher");
		command.setType(EnumType.PipePause);
		command.setSenderID("Finisher");
		pp = createPipeParams(command);

		command = jmf.appendCommand();
		command.setXMLComment("request for restart at start of 2nd booklet");
		command.setType(EnumType.PipePull);
		command.setSenderID("Finisher");
		pp = createPipeParams(command);
		JDFAmountPool ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		JDFAttributeMap m = new JDFAttributeMap("SetIndex", "1~-1");
		ap.getCreatePartAmount(m);

		command = jmf.appendCommand();
		command.setXMLComment("resend the 2nd push: cover + 5 body sheets");
		command.setType(EnumType.PipePush);
		command.setSenderID("Printer");
		pp = createPipeParams(command);
		createAmountPool(pp, 1, 5, 5);

		command = jmf.appendCommand();
		command.setXMLComment("The 3rd push: cover + 12 body sheets");
		command.setType(EnumType.PipePush);
		command.setSenderID("Printer");
		pp = createPipeParams(command);
		createAmountPool(pp, 2, 12, 12);

		command = jmf.appendCommand();
		command.setXMLComment("paper jam in printer - invalidate the cover + 4 pages previously sent");
		command.setType(EnumType.PipePause);
		command.setSenderID("Printer");
		pp = createPipeParams(command);
		ap = createAmountPool(pp, 2, 4, 12);
		ap.getPartAmount(0).getPart(0).setAttribute("Condition", "Waste");
		ap.getPartAmount(1).getPart(0).setAttribute("Condition", "Waste");

		command = jmf.appendCommand();
		command.setXMLComment("resend the 3rd push: cover + 12 body sheets");
		command.setType(EnumType.PipePush);
		command.setSenderID("Printer");
		pp = createPipeParams(command);
		createAmountPool(pp, 2, 12, 12);

		command = jmf.appendCommand();
		command.setXMLComment("we are done");
		command.setType(EnumType.PipeClose);
		command.setSenderID("Printer");
		pp = createPipeParams(command);

		writeTest(jmf, "PipePushSet.jmf", true, null);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPipePushSheet()
	{
		JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setSenderID("Guru");
		for (int j = 0; j < 2; j++)
		{
			JDFCommand command = jmf.appendCommand();
			command.setXMLComment("The initial push: cover page");
			command.setType(EnumType.PipePush);
			command.setSenderID("Printer");
			JDFPipeParams pp = createPipeParams(command);
			createPartAmount(pp, j, 0, 1, true);
			for (int i = 0; i < 3 + j; i++)
			{
				command = jmf.appendCommand();
				command.setXMLComment("The " + (i + 1) + " push: " + (3 + j) + " body sheets");
				command.setType(EnumType.PipePush);
				command.setSenderID("Printer");
				pp = createPipeParams(command);
				createPartAmount(pp, j, i, 3 + j, false);
			}
		}
		writeTest(jmf, "PipePushSheet.jmf", true, null);
	}

	/**
	 *
	 *
	 */
	void createPipePushSheetExample(int set, int sheet, int sheets, boolean bCover)
	{
		JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setSenderID("Printer");
		jmf.setVersion(EnumVersion.Version_1_5);
		JDFCommand command = jmf.appendCommand();
		command.setType(EnumType.PipePush);
		JDFPipeParams pp = createPipeParams(command);
		createPartAmount(pp, set, sheet, sheets, bCover);
		writeTest(jmf, "PipePushSheetExample" + set + "." + sheet + (bCover ? "c" : "b") + ".jmf", true, null);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPipePushSheetExample()
	{
		createPipePushSheetExample(0, 0, 1, true);
		createPipePushSheetExample(0, 0, 5, false);
		createPipePushSheetExample(0, 1, 5, false);
		createPipePushSheetExample(0, 4, 5, false);
		createPipePushSheetExample(1, 0, 1, true);
		createPipePushSheetExample(1, 0, 1, false);
		createPipePushSheetExample(35, 4, 7, false);

		JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		jmf.setSenderID("Finisher");
		JDFCommand command = jmf.appendCommand();
		command.setType(EnumType.PipePause);
		JDFPipeParams pp = createPipeParams(command);
		JDFAmountPool ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		JDFAttributeMap m = new JDFAttributeMap("SetIndex", "34 35");
		m.put("Condition", "Waste");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipePauseFinisherExample.jmf", true, null);

		jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		command = jmf.appendCommand();
		command.setType(EnumType.PipePull);
		command.setSenderID("Finisher");
		pp = createPipeParams(command);
		ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		m = new JDFAttributeMap("SetIndex", "34~-1");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipePullFinisherExample.jmf", true, null);

		createPipePushSheetExample(34, 0, 1, true);
		createPipePushSheetExample(34, 0, 5, false);

		createPipePushSheetExample(122, 4, 7, false);

		jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		jmf.setSenderID("Printer");
		command = jmf.appendCommand();
		command.setType(EnumType.PipePause);
		pp = createPipeParams(command);
		ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		m = new JDFAttributeMap("SetIndex", "122");
		m.put("Condition", "Waste");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipePausePrinterExample.jmf", true, null);

		jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		jmf.setSenderID("Finisher");
		command = jmf.appendCommand();
		command.setType(EnumType.PipePause);
		pp = createPipeParams(command);
		ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		m = new JDFAttributeMap("SetIndex", "122");
		m.put("Condition", "Waste");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipePauseFinisher2Example.jmf", true, null);

		jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		command = jmf.appendCommand();
		command.setType(EnumType.PipePull);
		command.setSenderID("Finisher");
		pp = createPipeParams(command);
		ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		m = new JDFAttributeMap("SetIndex", "122~-1");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipePullFinisher2Example.jmf", true, null);

		createPipePushSheetExample(122, 0, 1, true);

		createPipePushSheetExample(221, 5, 6, false);

		jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setVersion(EnumVersion.Version_1_5);
		jmf.setSenderID("Printer");
		command = jmf.appendCommand();
		command.setType(EnumType.PipeClose);
		pp = createPipeParams(command);
		ap = (JDFAmountPool) pp.appendElement(ElementName.AMOUNTPOOL);
		m = new JDFAttributeMap("SetIndex", "0~221");
		ap.getCreatePartAmount(m);
		writeTest(jmf, "PipeCloseExample.jmf", true, null);
	}

	/**
	 * @throws DataFormatException
	 *
	 *
	 */
	@Test
	public void testPipePushSheetMeta() throws DataFormatException
	{
		JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		jmf.setSenderID("Guru");
		for (int j = 0; j < 2; j++)
		{
			JDFCommand command = jmf.appendCommand();
			command.setXMLComment("The initial push: cover page");
			command.setType(EnumType.PipePush);
			command.setSenderID("Printer");
			JDFPipeParams pp = createPipeParams(command);
			Vector<JDFPartAmount> vpa = createPartAmount(pp, j, 0, 1, true);
			for (JDFPartAmount pa : vpa)
				pa.getPart(0).setMetadata(0, j == 0 ? "Rich" : "Poor");

			for (int i = 0; i < 1 + j; i++)
			{
				command = jmf.appendCommand();
				command.setXMLComment("The " + (i + 1) + " push: " + (3 + j) + " body sheets");
				command.setType(EnumType.PipePush);
				command.setSenderID("Printer");
				pp = createPipeParams(command);
				vpa = createPartAmount(pp, j, i, 3 + j, false);
				for (JDFPartAmount pa : vpa)
					pa.getPart(0).setMetadata(0, j == 0 ? "Rich" : "Poor");
			}
		}
		jmf.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp + "PipePushSheetMeta.jmf", 2, false);
	}

	/**
	 *
	 *
	 * @param command
	 * @return
	 */
	JDFPipeParams createPipeParams(JDFCommand command)
	{
		JDFPipeParams pp = command.appendPipeParams();
		pp.setPipeID("PipeSheet");
		pp.setJobID("J1");
		pp.setJobPartID("Part");
		return pp;
	}

	/**
	 *
	 * @param pp
	 * @param set
	 * @param sheets
	 */
	Vector<JDFPartAmount> createPartAmount(JDFPipeParams pp, int set, int sheets, int plannedSheets, boolean bCover)
	{
		JDFAmountPool ap = (JDFAmountPool) pp.getCreateElement(ElementName.AMOUNTPOOL);
		JDFAttributeMap m = new JDFAttributeMap("SetIndex", "" + set);
		m.put(EnumPartIDKey.DocTags, bCover ? "Cover" : "Body");
		JDFPartAmount pa = ap.getCreatePartAmount(m);
		pa.setAmount(plannedSheets, null);
		if (sheets != plannedSheets)
		{
			m.put("SheetIndex", "" + sheets);
			pa = ap.appendPartAmount(m);
			pa.setActualAmount(1, null);
		}
		else
		{
			pa.setActualAmount(sheets, null);
		}
		return (Vector<JDFPartAmount>) ap.getAllPartAmount();
	}

	/**
	 *
	 * @param pp
	 * @param set
	 * @param sheets
	 */
	JDFAmountPool createAmountPool(JDFPipeParams pp, int set, int sheets, int plannedSheets)
	{
		JDFAmountPool ap = (JDFAmountPool) pp.getCreateElement(ElementName.AMOUNTPOOL);
		createPartAmount(pp, set, sheets == plannedSheets ? 1 : 0, 1, true);
		createPartAmount(pp, set, sheets, plannedSheets, false);
		return ap;
	}

}
