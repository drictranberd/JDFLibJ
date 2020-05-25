/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2020 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.jdflib.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoApprovalDetails.EnumApprovalState;
import org.cip4.jdflib.auto.JDFAutoComChannel.EnumChannelType;
import org.cip4.jdflib.auto.JDFAutoMedia.EnumISOPaperSubstrate;
import org.cip4.jdflib.auto.JDFAutoMedia.EnumMediaType;
import org.cip4.jdflib.core.JDFAudit.EnumAuditType;
import org.cip4.jdflib.core.JDFElement.EnumValidationLevel;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.elementwalker.FixVersion;
import org.cip4.jdflib.goldenticket.IDPGoldenTicket;
import org.cip4.jdflib.jmf.JDFCommand;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage.EnumType;
import org.cip4.jdflib.jmf.JDFResponse;
import org.cip4.jdflib.jmf.JMFBuilder;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumProcessUsage;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFCreated;
import org.cip4.jdflib.resource.JDFDevice;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.resource.JDFResource.EnumResStatus;
import org.cip4.jdflib.resource.JDFResource.EnumResourceClass;
import org.cip4.jdflib.resource.JDFTool;
import org.cip4.jdflib.resource.intent.JDFColorIntent;
import org.cip4.jdflib.resource.intent.JDFDeliveryIntent;
import org.cip4.jdflib.resource.process.JDFApprovalDetails;
import org.cip4.jdflib.resource.process.JDFApprovalSuccess;
import org.cip4.jdflib.resource.process.JDFAssembly;
import org.cip4.jdflib.resource.process.JDFAssemblySection;
import org.cip4.jdflib.resource.process.JDFBendingParams;
import org.cip4.jdflib.resource.process.JDFComChannel;
import org.cip4.jdflib.resource.process.JDFComponent;
import org.cip4.jdflib.resource.process.JDFContentMetaData;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.resource.process.prepress.JDFColorSpaceConversionOp;
import org.cip4.jdflib.resource.process.prepress.JDFColorSpaceConversionParams;
import org.cip4.jdflib.util.CPUTimer;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.jdflib.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class FixVersionTest extends JDFTestCaseBase
{
	private JDFDoc mDoc;
	private JDFNode n;

	/**
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		mDoc = new JDFDoc(ElementName.JDF);
		n = mDoc.getJDFRoot();

	}

	/**
	 *
	 */
	@Test
	public void testApprovalSuccess()
	{
		n.setType("Approval", true);
		JDFApprovalSuccess as = (JDFApprovalSuccess) n.appendMatchingResource(ElementName.APPROVALSUCCESS, EnumProcessUsage.AnyOutput, null);
		n.setVersion(EnumVersion.Version_1_2);
		as.appendContact();
		as.appendFileSpec();
		boolean bRet = n.fixVersion(EnumVersion.Version_1_3);
		assertTrue("fix ok", bRet);
		assertNotNull("approvaldetails", as.getApprovalDetails(0));
		bRet = n.fixVersion(EnumVersion.Version_1_2);
		assertTrue("fix ok", bRet);
		assertNull("approvaldetails", as.getApprovalDetails(0));
		bRet = n.fixVersion(EnumVersion.Version_1_3);
		assertTrue("fix ok", bRet);
		as = (JDFApprovalSuccess) n.getMatchingResource(ElementName.APPROVALSUCCESS, EnumProcessUsage.AnyOutput, null, 0);
		final JDFApprovalDetails ad = as.getApprovalDetails(0);
		ad.setApprovalState(EnumApprovalState.Rejected);
		bRet = n.fixVersion(EnumVersion.Version_1_2);
		assertFalse("fix not ok", bRet);
	}

	// //////////////////////////////////////////////////////////////////////

	/**
	 *
	 */
	@Test
	public void testRRefs()
	{
		final JDFResourcePool rp = n.appendResourcePool();
		rp.setAttribute(AttributeName.RREFS, "a b", null);
		n.fixVersion(null);
		assertFalse(rp.hasAttribute(AttributeName.RREFS));
	}

	/**
	 *
	 */
	@Test
	public void testNamespace()
	{
		final KElement ns = n.appendElement("foo:abc", "www.foobar.com");
		n.fixVersion(null);
		KElement ns2 = n.getElement("foo:abc");
		assertEquals(ns, ns2);
		assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getElement("foo:abc");
		assertEquals(ns, ns2);
		assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
	}

	/**
	 *
	 */
	@Test
	public void testNamespaceRes()
	{
		final KElement ns = n.addResource("foo:abc", EnumResourceClass.Parameter, null, null, null, "www.foobar.com", null);
		n.fixVersion(null);
		KElement ns2 = n.getResourcePool().getElement("foo:abc");
		assertEquals(ns, ns2);
		assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getResourcePool().getElement("foo:abc");
		assertEquals(ns, ns2);
		assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
	}

	/**
	 *
	 */
	@Test
	public void testNamespaceParse()
	{
		n = JDFDoc.parseFile(sm_dirTestData + "fixns.jdf").getJDFRoot();
		n.fixVersion(null);
		KElement ns2 = n.getResourcePool().getElement("foo:myresource");
		assertEquals(ns2.getNamespaceURI(), "http://www.foo.com/schema");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getResourcePool().getElement("foo:myresource");
		assertEquals(ns2.getNamespaceURI(), "http://www.foo.com/schema");
	}

	/**
	 *
	 */
	@Test
	public void testNamespaceRetain()
	{
		n = new JDFDoc(ElementName.JDF).getJDFRoot();
		n.addNameSpace("foo", "http://www.foo.com/schema");
		n.fixVersion(EnumVersion.Version_1_3);
		assertEquals(n.getAttribute("xmlns:foo"), "http://www.foo.com/schema");
	}

	/**
	 *
	 */
	@Test
	public void testNamespaceRetainZappDeprecated()
	{
		n = new JDFDoc(ElementName.JDF).getJDFRoot();
		n.addNameSpace("foo", "http://www.foo.com/schema");
		final FixVersion fv = new FixVersion(EnumVersion.Version_1_5);
		fv.setZappDeprecated(true);
		fv.convert(n);
		assertEquals(n.getAttribute("xmlns:foo"), "http://www.foo.com/schema");
	}

	/**
	 *
	 */
	@Test
	public void testICSVersions()
	{
		final JDFDevice r = (JDFDevice) n.addResource("Device", EnumUsage.Input);
		final VString ics0 = new VString("Base_L2-1.2 MIS_L3-1.2 PerCP_L2_1.2", null);
		final VString ics1 = new VString("Base_L2-1.4 MIS_L3-1.4 PerCP_L2_1.4", null);
		r.setICSVersions(ics0);
		final FixVersion f0 = new FixVersion(EnumVersion.Version_1_4);
		f0.walkTree(n, null);
		assertEquals(r.getICSVersions(), ics0);
		final FixVersion f1 = new FixVersion(EnumVersion.Version_1_4);
		f1.setFixICSVersions(true);
		f1.walkTree(n, null);
		assertEquals(r.getICSVersions(), ics1);
	}

	/**
	 *
	 */
	@Test
	public void testJobID()
	{
		final FixVersion f1 = new FixVersion(EnumVersion.Version_1_4);
		n.removeAttribute(AttributeName.JOBID);
		f1.convert(n);
		assertTrue(n.hasNonEmpty(AttributeName.JOBID));

	}

	/**
	 *
	 */
	@Test
	public void testJobPartID()
	{
		final FixVersion f1 = new FixVersion(EnumVersion.Version_1_7);
		n.setJobID("j1");
		n.removeAttribute(AttributeName.JOBPARTID);
		f1.convert(n);
		assertEquals("P_j1", n.getJobPartID(true));

	}

	/**
	 *
	 */
	@Test
	public void testAudit()
	{
		final JDFAuditPool ap = n.getAuditPool();
		assertNotNull(ap);
		final JDFCreated crea = (JDFCreated) ap.getAudit(0, EnumAuditType.Created, null, null);
		crea.setAgentName("Agent");
		crea.setAgentVersion("V1");
		final String agent = crea.getAgentName();
		crea.setAuthor(agent);
		assertNotNull(agent);
		String author = crea.getAuthor();
		assertNotNull(author);

		n.fixVersion(EnumVersion.Version_1_1);
		author = crea.getAuthor();
		assertEquals(StringUtil.token(author, 1, "_|_"), agent);
		assertTrue(author.startsWith(agent));
		String agent2 = crea.getAgentName();
		assertEquals(agent2, "");

		n.fixVersion(EnumVersion.Version_1_3);
		author = crea.getAuthor();
		assertEquals(author.indexOf("_|_"), -1);
		agent2 = crea.getAgentName();
		assertEquals(agent, agent2);

		n.fixVersion(EnumVersion.Version_1_2);
		author = crea.getAuthor();
		assertEquals(author.indexOf("_|_"), -1);
		agent2 = crea.getAgentName();
		assertEquals(agent, agent2);
	}

	/**
	 *
	 */
	@Test
	public void testResourceStatus()
	{
		final JDFMedia m = (JDFMedia) n.addResource("Media", null, EnumUsage.Input, null, null, null, null);
		m.setResStatus(EnumResStatus.Available, true);
		assertEquals(m.getResStatus(true), EnumResStatus.Available);
		assertTrue(m.fixVersion(EnumVersion.Version_1_1));
		assertEquals(m.getResStatus(true), EnumResStatus.Available);
		assertTrue(m.fixVersion(EnumVersion.Version_1_3));
		assertEquals(m.getResStatus(true), EnumResStatus.Available);
	}

	/**
	 *
	 */
	@Test
	public void testMultiConstruct()
	{
		final CPUTimer ct = new CPUTimer(false);
		for (int i = 0; i < 4; i++)
		{
			ct.start();
			final FixVersion v = new FixVersion(EnumVersion.Version_1_3);
			assertNotNull(v);
			log.info(ct.toXML());
			ct.stop();
		}
	}

	/**
	 *
	 */
	@Test
	public void testConvert()
	{
		final JDFMedia m = (JDFMedia) n.addResource("Media", null, EnumUsage.Input, null, null, null, null);
		m.setResStatus(EnumResStatus.Available, true);
		assertEquals(m.getResStatus(true), EnumResStatus.Available);
		assertTrue(new FixVersion(EnumVersion.Version_1_1).convert(n));
		assertEquals(n.getVersion(true), EnumVersion.Version_1_1);
	}

	/**
	 *
	 */
	@Test
	public void testCustomerMessage()
	{
		final JDFCustomerInfo ci = (JDFCustomerInfo) n.addResource(ElementName.CUSTOMERINFO, null, EnumUsage.Input, null, null, null, null);
		ci.appendCustomerMessage().setText("foo");
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setZappDeprecated(true);
		fixVersion.convert(ci);
		assertNull(ci.getCustomerMessage(0));
	}

	/**
	 *
	 */
	@Test
	public void testChannelType()
	{
		final JDFComChannel c = (JDFComChannel) new JDFDoc("ComChannel").getRoot();
		c.setChannelType(EnumChannelType.Mobile);
		assertTrue(new FixVersion(EnumVersion.Version_1_1).convert(c));
		assertEquals(c.getChannelTypeDetails(), "Mobile");
		assertEquals(c.getAttribute(AttributeName.CHANNELTYPE), "Phone");
		assertTrue(new FixVersion(EnumVersion.Version_1_5).convert(c));
		assertEquals(c.getAttribute(AttributeName.CHANNELTYPE), "Mobile");
	}

	/**
	 *
	 */
	@Test
	public void testIgnore()
	{
		final JDFComChannel c = (JDFComChannel) new JDFDoc("ComChannel").getRoot();
		c.setAttribute("bad", "blah");
		c.setAttribute("notbad", "blah");
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_1);
		fixVersion.setBZappInvalid(true);
		fixVersion.addIgnore(ElementName.COMCHANNEL, "notbad");
		assertTrue(fixVersion.convert(c));
		assertNull(c.getNonEmpty("bad"));
		assertEquals("blah", c.getAttribute("notbad"));
		assertTrue(new FixVersion(EnumVersion.Version_1_5).convert(c));
	}

	/**
	 *
	 */
	@Test
	public void testFriendlyName()
	{
		final JDFDevice d = (JDFDevice) new JDFDoc(ElementName.DEVICE).getRoot();
		d.setFriendlyName("f1");
		d.setDescriptiveName("d1");
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		assertTrue(fixVersion.convert(d));
		assertEquals("f1", d.getFriendlyName());
		assertEquals("d1", d.getDescriptiveName());
		fixVersion.setZappDeprecated(true);
		assertTrue(fixVersion.convert(d));
		assertNull(d.getNonEmpty(AttributeName.FRIENDLYNAME));
		assertEquals("d1", d.getDescriptiveName());
	}

	/**
	 *
	 */
	@Test
	public void testContentMetaData()
	{
		final JDFContentMetaData c = (JDFContentMetaData) new JDFDoc(ElementName.CONTENTMETADATA).getRoot();
		c.setISBN13("12345");
		assertTrue(new FixVersion(EnumVersion.Version_1_6).convert(c));
		assertEquals(c.getAttribute(AttributeName.ISBN), "12345");
		assertTrue(new FixVersion(EnumVersion.Version_1_1).convert(c));
		assertEquals(c.getAttribute(AttributeName.ISBN10), "12345");
		assertFalse(c.hasAttribute(AttributeName.ISBN));
	}

	/**
	 *
	 */
	@Test
	public void testNamedFeature()
	{
		n.setNamedFeatures(new VString("a b", null));
		assertTrue(new FixVersion(EnumVersion.Version_1_5).convert(n));
		assertEquals(n.getGeneralID("a", 0), "b");
		assertNull(n.getAttribute(AttributeName.NAMEDFEATURES, null, null));
		assertTrue(new FixVersion(EnumVersion.Version_1_4).convert(n));
		assertEquals(n.getAttribute(AttributeName.NAMEDFEATURES, null, null), "a b");
		assertNull(n.getGeneralID(null, 0));
	}

	/**
	 *
	 */
	@Test
	public void testNumColors()
	{
		final JDFColorIntent ci = (JDFColorIntent) n.getCreateResource(ElementName.COLORINTENT, EnumUsage.Input, 0);
		ci.setNumColors(4);
		boolean converted = new FixVersion(EnumVersion.Version_1_4).convert(ci);
		assertTrue(converted);
		assertNull(ci.getAttribute(AttributeName.NUMCOLORS, null, null));
		assertEquals(ci.getColorsUsed().getSeparations().size(), 4);
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setFixNewDuplicate(true);
		converted = fixVersion.convert(ci);
		assertTrue(converted);
		assertEquals(ci.getNumColors(), 4);
		assertEquals(ci.getColorsUsed().getSeparations().size(), 0);
	}

	/**
	 *
	 */
	@Test
	public void testNumColorsK()
	{
		final JDFColorIntent ci = (JDFColorIntent) n.getCreateResource(ElementName.COLORINTENT, EnumUsage.Input, 0);
		ci.appendColorsUsed().setSeparations(new VString("K", null));
		final boolean converted = new FixVersion(EnumVersion.Version_1_4).convert(ci);
		assertTrue(converted);
		assertNull(ci.getAttribute(AttributeName.NUMCOLORS, null, null));
		assertEquals(ci.getColorsUsed().getSeparations().size(), 1);
		assertEquals("K", ci.getColorsUsed().getSeparations().get(0));
	}

	/**
	 *
	 */
	@Test
	public void testSourceObjects()
	{
		final JDFColorSpaceConversionOp co = ((JDFColorSpaceConversionParams) n.getCreateResource(ElementName.COLORSPACECONVERSIONPARAMS, EnumUsage.Input, 0)).appendColorSpaceConversionOp();

		co.addSourceObject(JDFColorSpaceConversionOp.EnumSourceObjects.All);
		final boolean converted = new FixVersion(EnumVersion.Version_1_6).convert(co);
		assertTrue(converted);
		assertNull(co.getSourceObjects());
	}

	/**
	 *
	 */
	@Test
	public void testSourceObjects2()
	{
		final JDFColorSpaceConversionOp co = ((JDFColorSpaceConversionParams) n.getCreateResource(ElementName.COLORSPACECONVERSIONPARAMS, EnumUsage.Input, 0)).appendColorSpaceConversionOp();

		co.addSourceObject(JDFColorSpaceConversionOp.EnumSourceObjects.ImagePhotographic);
		final boolean converted = new FixVersion(EnumVersion.Version_1_6).convert(co);
		assertTrue(converted);
		assertEquals(1, co.getSourceObjects().size());
	}

	/**
	 *
	 */
	@Test
	public void testSourceObjectsNull()
	{
		final JDFColorSpaceConversionOp co = ((JDFColorSpaceConversionParams) n.getCreateResource(ElementName.COLORSPACECONVERSIONPARAMS, EnumUsage.Input, 0)).appendColorSpaceConversionOp();

		co.addSourceObject(JDFColorSpaceConversionOp.EnumSourceObjects.ImagePhotographic);
		co.addSourceObject(JDFColorSpaceConversionOp.EnumSourceObjects.All);
		final boolean converted = new FixVersion((EnumVersion) null).convert(co);
		assertTrue(converted);
		assertEquals(2, co.getSourceObjects().size());
	}

	/**
	 *
	 */
	@Test
	public void testNumColors15()
	{
		final JDFColorIntent ci = (JDFColorIntent) n.getCreateResource(ElementName.COLORINTENT, EnumUsage.Input, 0);
		ci.appendColorsUsed().setSeparations(new VString("Black", null));
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setFixNewDuplicate(false);
		final boolean converted = fixVersion.convert(ci);
		assertTrue(converted);
		assertNull(ci.getNonEmpty(AttributeName.NUMCOLORS));
		assertEquals(ci.getColorsUsed().getSeparations().size(), 1);
		assertEquals("Black", ci.getColorsUsed().getSeparations().get(0));

		fixVersion.setFixNewDuplicate(true);
		fixVersion.convert(ci);
		assertEquals("1", ci.getNonEmpty(AttributeName.NUMCOLORS));
	}

	/**
	 *
	 */
	@Test
	public void testNodeInfo()
	{
		n.setVersion(EnumVersion.Version_1_1);
		n.appendNodeInfo().appendJMF();
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setZappDeprecated(true);
		final boolean converted = fixVersion.convert(n);
		assertTrue(converted);
		assertNotNull(n.getResource(ElementName.NODEINFO, EnumUsage.Input, 0));
		assertNull(n.getResource(ElementName.NODEINFO, EnumUsage.Input, 0).getElement(ElementName.JMF));
	}

	/**
	 *
	 */
	@Test
	public void testNodeInfoTime()
	{
		final JDFNodeInfo ni = n.appendNodeInfo();
		ni.setAttribute(AttributeName.START, new JDFDate().getDateISO());
		ni.setAttribute(AttributeName.LASTEND, new JDFDate().getDateISO());
		final FixVersion fixVersion = new FixVersion((EnumVersion) null);
		fixVersion.setBZappInvalid(true);
		final boolean converted = fixVersion.convert(n);
		assertTrue(converted);
		assertTrue(ni.getAttribute(AttributeName.START).indexOf("T06:00") > 0);
		assertTrue(ni.getAttribute(AttributeName.LASTEND).indexOf("T18:00") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testNodeInfoTimeSet()
	{
		final JDFNodeInfo ni = n.appendNodeInfo();
		ni.setAttribute(AttributeName.START, new JDFDate().getDateISO());
		ni.setAttribute(AttributeName.LASTEND, new JDFDate().getDateISO());
		final FixVersion fixVersion = new FixVersion((EnumVersion) null);
		fixVersion.setFirsthour(3);
		fixVersion.setLasthour(22);
		fixVersion.setBZappInvalid(true);
		final boolean converted = fixVersion.convert(n);
		assertTrue(converted);
		assertTrue(ni.getAttribute(AttributeName.START).indexOf("T03:00") > 0);
		assertTrue(ni.getAttribute(AttributeName.LASTEND).indexOf("T22:00") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testDeliveryIntentTimeSet()
	{
		final JDFDeliveryIntent ni = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		ni.appendEarliest().setAttribute(AttributeName.ACTUAL, new JDFDate().getDateISO());
		ni.appendRequired().setAttribute(AttributeName.ACTUAL, new JDFDate().getDateISO());
		final FixVersion fixVersion = new FixVersion((EnumVersion) null);
		fixVersion.setFirsthour(3);
		fixVersion.setLasthour(22);
		fixVersion.setBZappInvalid(true);
		final boolean converted = fixVersion.convert(n);
		assertTrue(converted);
		assertTrue(ni.getEarliest().getAttribute(AttributeName.ACTUAL).indexOf("T03:00") > 0);
		assertTrue(ni.getRequired().getAttribute(AttributeName.ACTUAL).indexOf("T22:00") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testInheritedAttributes()
	{
		final JDFRunList ru = (JDFRunList) n.getCreateResource(ElementName.RUNLIST, EnumUsage.Input, 0);
		final JDFResource rup = ru.addPartition(EnumPartIDKey.Run, "r");
		ru.setNPage(7);
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setZappDeprecated(true);
		final boolean converted = fixVersion.convert(n);
		assertTrue(converted);
		assertEquals(7, ru.getNPage());
		assertFalse(rup.hasAttribute_KElement(AttributeName.NPAGE, null, false));
	}

	/**
	 *
	 */
	@Test
	public void testNumColorsDouble()
	{
		final JDFColorIntent ci = (JDFColorIntent) n.getCreateResource(ElementName.COLORINTENT, EnumUsage.Input, 0);
		ci.setNumColors(4);
		ci.getCreateColorsUsed().setCMYK();
		boolean converted = new FixVersion(EnumVersion.Version_1_4).convert(ci);
		assertTrue(converted);
		assertNull(ci.getAttribute(AttributeName.NUMCOLORS, null, null));
		assertEquals(ci.getColorsUsed().getSeparations().size(), 4);
		final FixVersion fixVersion = new FixVersion(EnumVersion.Version_1_5);
		fixVersion.setFixNewDuplicate(true);
		converted = fixVersion.convert(ci);
		assertTrue(converted);
		assertEquals(ci.getNumColors(), 4);
		assertEquals(ci.getColorsUsed().getSeparations().size(), 0);
	}

	/**
	 *
	 */
	@Test
	public void testTool()
	{
		final JDFTool t = (JDFTool) n.addResource("Tool", null, EnumUsage.Input, null, null, null, null);
		t.setResStatus(EnumResStatus.Available, true);
		t.setProductID("toolID");
		assertTrue(t.fixVersion(EnumVersion.Version_1_1));
		assertEquals(t.getToolID(), "toolID");
		assertEquals(t.getProductID(), "toolID");
		assertTrue(t.fixVersion(EnumVersion.Version_1_3));
		assertEquals(t.getToolID(), "");
		assertEquals(t.getProductID(), "toolID");
	}

	/**
	 *
	 */
	@Test
	public void testLayoutPrep()
	{
		final IDPGoldenTicket idpGoldenTicket = new IDPGoldenTicket(1);

		idpGoldenTicket.assign(null);
		final JDFNode node = idpGoldenTicket.getNode();
		assertTrue(node.isValid(EnumValidationLevel.Complete));
		final FixVersion fix = new FixVersion(EnumVersion.Version_1_5);
		fix.setLayoutPrepToStripping(true);
		fix.walkTree(node, null);
		node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp + "FixLayoutPrep.jdf", 2, false);
		assertTrue(node.isValid(EnumValidationLevel.Complete));
	}

	/**
	 *
	 */
	@Test
	public void testJMF()
	{
		final JDFJMF jmf = new JMFBuilder().buildNewJDFCommand();
		jmf.setAgentName("AName");
		jmf.setAgentVersion("AVersion");
		final FixVersion fix = new FixVersion(EnumVersion.Version_1_3);
		fix.setBZappInvalid(true);
		fix.walkTree(jmf, null);
		assertNull(StringUtil.getNonEmpty(jmf.getAgentName()));
		final FixVersion fix2 = new FixVersion(EnumVersion.Version_1_4);
		fix2.walkTree(jmf, null);
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueFilter()
	{
		final JDFJMF jmf = new JMFBuilder().buildAbortQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		command.appendQueueFilter();
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueFilter(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueFilter(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueAbortQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildAbortQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueAbortQueueEntryNull()
	{
		final JDFJMF jmf = new JMFBuilder().buildAbortQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		assertNotNull(command.getQueueEntryDef(0));
		FixVersion fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		assertEquals("42", jmf.getXPathAttribute("Command/AbortQueueEntryParams/QueueFilter/QueueEntryDef/@QueueEntryID", null));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueHoldQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildHoldQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueRemoveQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildRemoveQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueSuspendQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildSuspendQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueSusbmitQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildSubmitQueueEntry("http://www.example.com");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command);
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueueResumeQueueEntry()
	{
		final JDFJMF jmf = new JMFBuilder().buildResumeQueueEntry("42");
		final JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertEquals(1, command.numChildElements("ResumeQueueEntryParams", null));
		assertNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(command.getQueueEntryDef(0));
		assertEquals(1, command.numChildElements("ResumeQueueEntryParams", null));
		fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(command.getQueueEntryDef(0));
	}

	/**
	 *
	 */
	@Test
	public void testJMFQueue()
	{
		final JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		final JDFResponse r = jmf.appendResponse();
		r.setType(EnumType.RemoveQueueEntry);
		r.appendQueue();
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		assertNotNull(r.getQueue(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		assertNull(r.getQueue(0));
	}

	// //////////////////////////////////////////////////////////////////////
	/**
	 *
	 */
	@Test
	public void testAssembly()
	{
		final JDFAssembly a = (JDFAssembly) n.addResource(ElementName.ASSEMBLY, EnumUsage.Input);
		a.setResStatus(EnumResStatus.Available, true);
		final VString ai = new VString("a1", null);
		a.setAssemblyIDs(ai);
		final JDFAssemblySection as = a.appendAssemblySection();
		final VString asi = new VString("a1.1", null);
		as.setAssemblyIDs(asi);
		assertTrue(a.fixVersion(EnumVersion.Version_1_1));
		assertEquals(a.getAssemblyID(), "a1");
		assertNull(a.getAttribute("AssemblyIDs", null, null));
		assertEquals(as.getAssemblyID(), "a1.1");
		assertNull(as.getAttribute("AssemblyIDs", null, null));
		assertTrue(a.fixVersion(EnumVersion.Version_1_4));
		assertEquals(a.getAssemblyIDs(), ai);
		assertEquals(a.getAssemblyID(), "");
		assertEquals(as.getAssemblyIDs(), asi);
		assertEquals(as.getAssemblyID(), "");
	}

	/**
	 * tests updating multiple versions at once
	 */
	@Test
	public void testMultiskip()
	{
		n.setVersion(EnumVersion.Version_1_4);
		final JDFAssembly a = (JDFAssembly) n.addResource(ElementName.ASSEMBLY, EnumUsage.Input);
		a.setResStatus(EnumResStatus.Available, true);
		final VString ai = new VString("a1", null);
		a.setAssemblyIDs(ai);
		final JDFAssemblySection as = a.appendAssemblySection();
		final VString asi = new VString("a1.1", null);
		as.setAssemblyIDs(asi);
		assertTrue(a.fixVersion(EnumVersion.Version_1_1));
		assertEquals(a.getAssemblyID(), "a1");
		assertNull(a.getAttribute("AssemblyIDs", null, null));
		assertEquals(as.getAssemblyID(), "a1.1");
		assertNull(as.getAttribute("AssemblyIDs", null, null));
		assertTrue(a.fixVersion(EnumVersion.Version_1_4));
		assertEquals(a.getAssemblyIDs(), ai);
		assertEquals(a.getAssemblyID(), "");
		assertEquals(as.getAssemblyIDs(), asi);
		assertEquals(as.getAssemblyID(), "");
	}

	/**
	 *
	 */
	@Test
	public void testMediaGrade()
	{
		final JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		n.setType(JDFNode.EnumType.ConventionalPrinting);
		final JDFMedia med = (JDFMedia) n.addResource(ElementName.MEDIA, EnumUsage.Input);
		med.setMediaType(EnumMediaType.Paper);
		med.setWeight(42);
		med.setGrade(1);

		assertTrue(med.fixVersion(EnumVersion.Version_1_6));
		assertEquals(EnumISOPaperSubstrate.PS1, med.getISOPaperSubstrate());
	}

	/**
	 * tests updating multiple versions at once
	 */
	@Test
	public void testAmount()
	{
		n.setVersion(EnumVersion.Version_1_4);
		final JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Input);
		final JDFResourceLink rl = n.getLink(c, null);
		rl.setAttribute(AttributeName.AMOUNT, "333.0");
		n.fixVersion(null);
		assertEquals(rl.getAttribute(AttributeName.AMOUNT), "333");
	}

	/**
	 * tests updating multiple versions at once
	 */
	@Test
	public void testBoolean()
	{
		n.setVersion(EnumVersion.Version_1_4);
		final JDFBendingParams c = (JDFBendingParams) n.addResource(ElementName.BENDINGPARAMS, EnumUsage.Input);
		c.setAttribute(AttributeName.BEND, "1");
		n.fixVersion(null);
		assertEquals("true", c.getAttribute(AttributeName.BEND));
	}

	/**
	 * tests updating multiple versions at once
	 */
	@Test
	public void testBoolean0()
	{
		n.setVersion(EnumVersion.Version_1_4);
		final JDFBendingParams c = (JDFBendingParams) n.addResource(ElementName.BENDINGPARAMS, EnumUsage.Input);
		c.setAttribute(AttributeName.BEND, "0");
		n.fixVersion(null);
		assertEquals("false", c.getAttribute(AttributeName.BEND));
	}

	/**
	 *
	 * @see junit.framework.TestCase#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString() + "\n" + n;
	}

}
