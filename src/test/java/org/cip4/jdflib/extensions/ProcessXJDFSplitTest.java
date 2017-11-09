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
package org.cip4.jdflib.extensions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoMedia.EnumMediaType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement.EnumValidationLevel;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFIntegerList;
import org.cip4.jdflib.extensions.xjdfwalker.XJDFToJDFConverter;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 *
 */
public class ProcessXJDFSplitTest extends JDFTestCaseBase
{

	/**
	 *
	 */
	@Test
	public void testSplit()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes("ImageSetting PreviewGeneration ConventionalPrinting Cutting Folding");
		SetHelper s = h.appendResourceSet("Media", EnumUsage.Input);
		ResourceHelper p = s.appendPartition(null, true);
		((JDFMedia) p.getResource()).setMediaType(EnumMediaType.Plate);

		s = h.appendResourceSet("ExposedMedia", null);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("PreviewGenerationParams", EnumUsage.Input);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("Preview", null);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("ConventionalPrintingParams", EnumUsage.Input);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("CuttingParams", EnumUsage.Input);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("FoldingParams", EnumUsage.Input);
		p = s.appendPartition(null, true);

		s = h.appendResourceSet("Component", EnumUsage.Output);
		p = s.appendPartition(null, true);

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		splitter.addGroup(new VString("ImageSetting PreviewGeneration", null));
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		d.write2File(sm_dirTestDataTemp + "splitxjdf.jdf", 2, false);
		assertTrue(d.getJDFRoot().isValid(EnumValidationLevel.Incomplete));
	}

	/**
	 *
	 */
	@Test
	public void testSplitDevice()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes("ImageSetting ConventionalPrinting");

		SetHelper s = h.appendResourceSet("Device", EnumUsage.Input);
		s.setCombinedProcessIndex(new JDFIntegerList(0));
		s.setDescriptiveName("Dev PlateSetter");
		KElement dev = s.appendPartition(null, true).getResource();
		dev.setAttribute(AttributeName.DEVICEID, "PS1");

		s = h.appendResourceSet("Device", EnumUsage.Input);
		s.setCombinedProcessIndex(new JDFIntegerList(1));
		dev = s.appendPartition(null, true).getResource();
		dev.setAttribute(AttributeName.DEVICEID, "P1");
		s.setDescriptiveName("Dev Print");
		s.appendPartition(null, true);

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		splitter.addGroup(new VString("ImageSetting PreviewGeneration", null));
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		final JDFNode root = d.getJDFRoot();
		final JDFNode imSet = (JDFNode) root.getvJDFNode("ImageSetting", null, true).get(0);
		final JDFResource jdfDev = imSet.getResource(ElementName.DEVICE, null, 0);
		assertEquals("PS1", jdfDev.getAttribute(AttributeName.DEVICEID));
		assertNull(imSet.getLink(jdfDev, null).getNonEmpty(AttributeName.COMBINEDPROCESSINDEX));
		final JDFNode cp = (JDFNode) root.getvJDFNode("ConventionalPrinting", null, true).get(0);
		final JDFResource cpDev = cp.getResource(ElementName.DEVICE, null, 0);
		assertEquals("P1", cpDev.getAttribute(AttributeName.DEVICEID));
		assertNull(cp.getLink(cpDev, null).getNonEmpty(AttributeName.COMBINEDPROCESSINDEX));

		d.write2File(sm_dirTestDataTemp + "splitDevxjdf.jdf", 2, false);
		assertTrue(d.getJDFRoot().isValid(EnumValidationLevel.Incomplete));
	}

	/**
	 *
	 */
	@Test
	public void testSplitNullTypes()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes((String) null);
		final SetHelper s = h.appendResourceSet("Media", EnumUsage.Input);
		final ResourceHelper p = s.appendPartition(null, true);
		((JDFMedia) p.getResource()).setMediaType(EnumMediaType.Plate);

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		splitter.addGroup(new VString("ImageSetting PreviewGeneration", null));
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		d.write2File(sm_dirTestDataTemp + "splitxjdfNull.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitJPIDTypes()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes("Screening ImageSetting ConventionalPrinting");
		final SetHelper s = h.appendResourceSet("Media", EnumUsage.Input);
		final ResourceHelper p = s.appendPartition(null, true);
		((JDFMedia) p.getResource()).setMediaType(EnumMediaType.Plate);

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		splitter.addGroup(new VString("Screening ImageSetting PreviewGeneration", null));
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		final VElement v = d.getJDFRoot().getvJDFNode(null, null, false);
		for (final KElement e : v)
		{
			assertEquals(-1, e.getNonEmpty(AttributeName.JOBPARTID).indexOf(" "));
		}
		d.write2File(sm_dirTestDataTemp + "splitxjdfTypes.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitCategory()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes("Screening ImageSetting ConventionalPrinting");
		h.setAttribute(AttributeName.CATEGORY, "w2p");
		final SetHelper s = h.appendResourceSet("Media", EnumUsage.Input);
		final ResourceHelper p = s.appendPartition(null, true);
		((JDFMedia) p.getResource()).setMediaType(EnumMediaType.Plate);

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		final VElement v = d.getJDFRoot().getvJDFNode(null, null, false);
		for (final KElement e : v)
		{
			final JDFNode n = (JDFNode) e;
			assertEquals(n.isJDFRoot() ? "w2p" : "", n.getCategory());
		}
		d.write2File(sm_dirTestDataTemp + "splitxjdfCategory.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitCategorySingle()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.setTypes("Screening");
		h.setAttribute(AttributeName.CATEGORY, "w2p");

		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h.getRoot());
		final VElement v = d.getJDFRoot().getvJDFNode(null, null, false);
		assertEquals(1, v.size());
		final JDFNode n = (JDFNode) v.get(0);
		assertEquals("w2p", n.getCategory());

		d.write2File(sm_dirTestDataTemp + "splitxjdfCategory.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitEndCustomer()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.appendProduct();
		h.setTypes("Product ConventionalPrinting");
		final SetHelper s = h.appendResourceSet(ElementName.CUSTOMERINFO, EnumUsage.Input);
		s.setProcessUsage("EndCustomer");
		s.getCreatePartition(0, true).getResource().setAttribute(AttributeName.CUSTOMERID, "c1");
		final SetHelper s2 = h.appendResourceSet(ElementName.CUSTOMERINFO, EnumUsage.Input);
		s2.getCreatePartition(0, true).getResource().setAttribute(AttributeName.CUSTOMERID, "c2");
		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h);
		final JDFNode n = d.getJDFRoot();

		assertNotNull(n.getCustomerInfo());
		assertNotNull(n.getResource(ElementName.CUSTOMERINFO, EnumUsage.Input, "EndCustomer", null, 0));
		d.write2File(sm_dirTestDataTemp + "ci2.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitColorPool()
	{
		final XJDFHelper h = new XJDFHelper("j1", "root", null);
		h.appendProduct();
		h.setTypes("Product");
		final SetHelper s = h.appendResourceSet(ElementName.COLOR, EnumUsage.Input);
		s.appendPartition(new JDFAttributeMap(AttributeName.SEPARATION, "Cyan"), true).getResource().setAttribute(AttributeName.ACTUALCOLORNAME, "c1");
		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		c.setSplitter(splitter);

		final JDFDoc d = c.convert(h);
		final JDFNode n = d.getJDFRoot();

		assertNotNull(n.getResource(ElementName.COLORPOOL, EnumUsage.Input, 0));
		d.write2File(sm_dirTestDataTemp + "cp.jdf", 2, false);
	}

	/**
	 *
	 */
	@Test
	public void testSplitFromFile()
	{
		final XJDFToJDFConverter c = new XJDFToJDFConverter(null);
		final ProcessXJDFSplit splitter = new ProcessXJDFSplit();
		splitter.addGroup(new VString("ImageSetting PreviewGeneration", null));
		c.setSplitter(splitter);

		final KElement root = XMLDoc.parseFile(sm_dirTestData + "29694232.ptk").getRoot();
		final KElement xjdf = root.getChildByTagName(XJDF20.rootName, null, 0, null, false, true);
		final JDFDoc d = c.convert(xjdf);
		d.write2File(sm_dirTestDataTemp + "splitxjdfFile.jdf", 2, false);
		//assertTrue(d.getJDFRoot().isValid(EnumValidationLevel.Incomplete));
	}

}
