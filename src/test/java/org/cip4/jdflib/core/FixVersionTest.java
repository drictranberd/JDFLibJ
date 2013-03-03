/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2013 The International Cooperation for the Integration of
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
package org.cip4.jdflib.core;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoApprovalDetails.EnumApprovalState;
import org.cip4.jdflib.core.JDFAudit.EnumAuditType;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.elementwalker.FixVersion;
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
import org.cip4.jdflib.resource.JDFResource.EnumResStatus;
import org.cip4.jdflib.resource.JDFResource.EnumResourceClass;
import org.cip4.jdflib.resource.JDFTool;
import org.cip4.jdflib.resource.process.JDFApprovalDetails;
import org.cip4.jdflib.resource.process.JDFApprovalSuccess;
import org.cip4.jdflib.resource.process.JDFAssembly;
import org.cip4.jdflib.resource.process.JDFAssemblySection;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class FixVersionTest extends JDFTestCaseBase {
	private JDFDoc mDoc;
	private JDFNode n;

	/**
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDoc = new JDFDoc("JDF");
		n = mDoc.getJDFRoot();

	}

	// /////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testApprovalSuccess() {
		n.setType("Approval", true);
		JDFApprovalSuccess as = (JDFApprovalSuccess) n.appendMatchingResource(ElementName.APPROVALSUCCESS, EnumProcessUsage.AnyOutput, null);
		n.setVersion(EnumVersion.Version_1_2);
		as.appendContact();
		as.appendFileSpec();
		boolean bRet = n.fixVersion(EnumVersion.Version_1_3);
		Assert.assertTrue("fix ok", bRet);
		Assert.assertNotNull("approvaldetails", as.getApprovalDetails(0));
		bRet = n.fixVersion(EnumVersion.Version_1_2);
		Assert.assertTrue("fix ok", bRet);
		Assert.assertNull("approvaldetails", as.getApprovalDetails(0));
		bRet = n.fixVersion(EnumVersion.Version_1_3);
		Assert.assertTrue("fix ok", bRet);
		as = (JDFApprovalSuccess) n.getMatchingResource(ElementName.APPROVALSUCCESS, EnumProcessUsage.AnyOutput, null, 0);
		final JDFApprovalDetails ad = as.getApprovalDetails(0);
		ad.setApprovalState(EnumApprovalState.Rejected);
		bRet = n.fixVersion(EnumVersion.Version_1_2);
		Assert.assertFalse("fix not ok", bRet);
	}

	// //////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testRRefs() {
		final JDFResourcePool rp = n.appendResourcePool();
		rp.setAttribute(AttributeName.RREFS, "a b", null);
		n.fixVersion(null);
		Assert.assertFalse(rp.hasAttribute(AttributeName.RREFS));
	}

	/**
	 * 
	 */
	@Test
	public void testNamespace() {
		KElement ns = n.appendElement("foo:abc", "www.foobar.com");
		n.fixVersion(null);
		KElement ns2 = n.getElement("foo:abc");
		Assert.assertEquals(ns, ns2);
		Assert.assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getElement("foo:abc");
		Assert.assertEquals(ns, ns2);
		Assert.assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
	}

	/**
	 * 
	 */
	@Test
	public void testNamespaceRes() {
		KElement ns = n.addResource("foo:abc", EnumResourceClass.Parameter, null, null, null, "www.foobar.com", null);
		n.fixVersion(null);
		KElement ns2 = n.getResourcePool().getElement("foo:abc");
		Assert.assertEquals(ns, ns2);
		Assert.assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getResourcePool().getElement("foo:abc");
		Assert.assertEquals(ns, ns2);
		Assert.assertEquals(ns2.getNamespaceURI(), "www.foobar.com");
	}

	/**
	 * 
	 */
	@Test
	public void testNamespaceParse() {
		n = JDFDoc.parseFile(sm_dirTestData + "fixns.jdf").getJDFRoot();
		n.fixVersion(null);
		KElement ns2 = n.getResourcePool().getElement("foo:myresource");
		Assert.assertEquals(ns2.getNamespaceURI(), "http://www.foo.com/schema");
		n.fixVersion(EnumVersion.Version_1_3);
		ns2 = n.getResourcePool().getElement("foo:myresource");
		Assert.assertEquals(ns2.getNamespaceURI(), "http://www.foo.com/schema");
	}

	/**
	 * 
	 */
	@Test
	public void testICSVersions() {
		final JDFDevice r = (JDFDevice) n.addResource("Device", EnumUsage.Input);
		final VString ics0 = new VString("Base_L2-1.2 MIS_L3-1.2 PerCP_L2_1.2", null);
		final VString ics1 = new VString("Base_L2-1.4 MIS_L3-1.4 PerCP_L2_1.4", null);
		r.setICSVersions(ics0);
		final FixVersion f0 = new FixVersion(EnumVersion.Version_1_4);
		f0.walkTree(n, null);
		Assert.assertEquals(r.getICSVersions(), ics0);
		final FixVersion f1 = new FixVersion(EnumVersion.Version_1_4);
		f1.setFixICSVersions(true);
		f1.walkTree(n, null);
		Assert.assertEquals(r.getICSVersions(), ics1);
	}

	// //////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testAudit() {
		final JDFAuditPool ap = n.getAuditPool();
		Assert.assertNotNull(ap);
		final JDFCreated crea = (JDFCreated) ap.getAudit(0, EnumAuditType.Created, null, null);
		final String agent = crea.getAgentName();
		crea.setAuthor(agent);
		Assert.assertNotNull(agent);
		String author = crea.getAuthor();
		Assert.assertNotNull(author);

		n.fixVersion(EnumVersion.Version_1_1);
		author = crea.getAuthor();
		Assert.assertEquals(StringUtil.token(author, 1, "_|_"), agent);
		Assert.assertTrue(author.startsWith(agent));
		String agent2 = crea.getAgentName();
		Assert.assertEquals(agent2, "");

		n.fixVersion(EnumVersion.Version_1_3);
		author = crea.getAuthor();
		Assert.assertEquals(author.indexOf("_|_"), -1);
		agent2 = crea.getAgentName();
		Assert.assertEquals(agent, agent2);

		n.fixVersion(EnumVersion.Version_1_2);
		author = crea.getAuthor();
		Assert.assertEquals(author.indexOf("_|_"), -1);
		agent2 = crea.getAgentName();
		Assert.assertEquals(agent, agent2);

	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testResourceStatus() {
		final JDFMedia m = (JDFMedia) n.addResource("Media", null, EnumUsage.Input, null, null, null, null);
		m.setResStatus(EnumResStatus.Available, true);
		Assert.assertEquals(m.getResStatus(true), EnumResStatus.Available);
		Assert.assertTrue(m.fixVersion(EnumVersion.Version_1_1));
		Assert.assertEquals(m.getResStatus(true), EnumResStatus.Available);
		Assert.assertTrue(m.fixVersion(EnumVersion.Version_1_3));
		Assert.assertEquals(m.getResStatus(true), EnumResStatus.Available);
	}

	/**
	 * 
	 */
	@Test
	public void testConvert() {
		final JDFMedia m = (JDFMedia) n.addResource("Media", null, EnumUsage.Input, null, null, null, null);
		m.setResStatus(EnumResStatus.Available, true);
		Assert.assertEquals(m.getResStatus(true), EnumResStatus.Available);
		Assert.assertTrue(new FixVersion(EnumVersion.Version_1_1).convert(n));
		Assert.assertEquals(n.getVersion(true), EnumVersion.Version_1_1);
	}

	/**
	 * 
	 */
	@Test
	public void testNamedFeature() {
		n.setNamedFeatures(new VString("a b", null));
		Assert.assertTrue(new FixVersion(EnumVersion.Version_1_5).convert(n));
		Assert.assertEquals(n.getGeneralID("a", 0), "b");
		Assert.assertNull(n.getAttribute(AttributeName.NAMEDFEATURES, null, null));
		Assert.assertTrue(new FixVersion(EnumVersion.Version_1_4).convert(n));
		Assert.assertEquals(n.getAttribute(AttributeName.NAMEDFEATURES, null, null), "a b");
		Assert.assertNull(n.getGeneralID(null, 0));
	}

	// //////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testTool() {
		final JDFTool t = (JDFTool) n.addResource("Tool", null, EnumUsage.Input, null, null, null, null);
		t.setResStatus(EnumResStatus.Available, true);
		t.setProductID("toolID");
		Assert.assertTrue(t.fixVersion(EnumVersion.Version_1_1));
		Assert.assertEquals(t.getToolID(), "toolID");
		Assert.assertEquals(t.getProductID(), "toolID");
		Assert.assertTrue(t.fixVersion(EnumVersion.Version_1_3));
		Assert.assertEquals(t.getToolID(), "");
		Assert.assertEquals(t.getProductID(), "toolID");
	}

	/**
	 * 
	 */
	@Test
	public void testJMF() {
		JDFJMF jmf = new JMFBuilder().buildNewJDFCommand();
		jmf.setAgentName("AName");
		jmf.setAgentVersion("AVersion");
		FixVersion fix = new FixVersion(EnumVersion.Version_1_3);
		fix.setBZappInvalid(true);
		fix.walkTree(jmf, null);
		Assert.assertNull(StringUtil.getNonEmpty(jmf.getAgentName()));
		FixVersion fix2 = new FixVersion(EnumVersion.Version_1_4);
		fix2.walkTree(jmf, null);
		Assert.assertNotNull(StringUtil.getNonEmpty(jmf.getAgentName()));
	}

	/**
	 * 
	 */
	@Test
	public void testJMFQueueFilter() {
		JDFJMF jmf = new JMFBuilder().buildAbortQueueEntry("42");
		JDFCommand command = jmf.getCommand(0);
		command.appendQueueFilter();
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		Assert.assertNotNull(command.getQueueFilter(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		Assert.assertNull(command.getQueueFilter(0));
	}

	/**
	 * 
	 */
	@Test
	public void testJMFQueueAbortQueueEntry() {
		JDFJMF jmf = new JMFBuilder().buildAbortQueueEntry("42");
		JDFCommand command = jmf.getCommand(0);
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		Assert.assertNotNull(command.getQueueEntryDef(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		Assert.assertNull(command.getQueueEntryDef(0));
	}

	/**
	 * 
	 */
	@Test
	public void testJMFQueue() {
		JDFJMF jmf = new JDFDoc("JMF").getJMFRoot();
		JDFResponse r = jmf.appendResponse();
		r.setType(EnumType.RemoveQueueEntry);
		r.appendQueue();
		FixVersion fix = new FixVersion(EnumVersion.Version_1_4);
		fix.walkTree(jmf, null);
		Assert.assertNotNull(r.getQueue(0));
		fix = new FixVersion(EnumVersion.Version_1_5);
		fix.walkTree(jmf, null);
		Assert.assertNull(r.getQueue(0));
	}

	// //////////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	@Test
	public void testAssembly() {
		final JDFAssembly a = (JDFAssembly) n.addResource(ElementName.ASSEMBLY, EnumUsage.Input);
		a.setResStatus(EnumResStatus.Available, true);
		VString ai = new VString("a1", null);
		a.setAssemblyIDs(ai);
		JDFAssemblySection as = a.appendAssemblySection();
		VString asi = new VString("a1.1", null);
		as.setAssemblyIDs(asi);
		Assert.assertTrue(a.fixVersion(EnumVersion.Version_1_1));
		Assert.assertEquals(a.getAssemblyID(), "a1");
		Assert.assertNull(a.getAttribute("AssemblyIDs", null, null));
		Assert.assertEquals(as.getAssemblyID(), "a1.1");
		Assert.assertNull(as.getAttribute("AssemblyIDs", null, null));
		Assert.assertTrue(a.fixVersion(EnumVersion.Version_1_4));
		Assert.assertEquals(a.getAssemblyIDs(), ai);
		Assert.assertEquals(a.getAssemblyID(), "");
		Assert.assertEquals(as.getAssemblyIDs(), asi);
		Assert.assertEquals(as.getAssemblyID(), "");
	}

	/**
	 * tests updating multiple versions at once
	 */
	@Test
	public void testMultiskip() {
		n.setVersion(EnumVersion.Version_1_4);
		final JDFAssembly a = (JDFAssembly) n.addResource(ElementName.ASSEMBLY, EnumUsage.Input);
		a.setResStatus(EnumResStatus.Available, true);
		VString ai = new VString("a1", null);
		a.setAssemblyIDs(ai);
		JDFAssemblySection as = a.appendAssemblySection();
		VString asi = new VString("a1.1", null);
		as.setAssemblyIDs(asi);
		Assert.assertTrue(a.fixVersion(EnumVersion.Version_1_1));
		Assert.assertEquals(a.getAssemblyID(), "a1");
		Assert.assertNull(a.getAttribute("AssemblyIDs", null, null));
		Assert.assertEquals(as.getAssemblyID(), "a1.1");
		Assert.assertNull(as.getAttribute("AssemblyIDs", null, null));
		Assert.assertTrue(a.fixVersion(EnumVersion.Version_1_4));
		Assert.assertEquals(a.getAssemblyIDs(), ai);
		Assert.assertEquals(a.getAssemblyID(), "");
		Assert.assertEquals(as.getAssemblyIDs(), asi);
		Assert.assertEquals(as.getAssemblyID(), "");
	}

	/**
	 * 
	 * @see junit.framework.TestCase#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + n;
	}

}
