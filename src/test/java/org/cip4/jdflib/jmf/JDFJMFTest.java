/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2012 The International Cooperation for the Integration of 
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
package org.cip4.jdflib.jmf;

import java.util.Iterator;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoDeviceInfo.EnumDeviceStatus;
import org.cip4.jdflib.auto.JDFAutoStatusQuParams.EnumDeviceDetails;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFElement.EnumValidationLevel;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFParser;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.jmf.JDFMessage.EnumFamily;
import org.cip4.jdflib.jmf.JDFMessage.EnumType;
import org.cip4.jdflib.resource.JDFNotification;
import org.junit.Assert;
import org.junit.Test;
/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 * 
 */
public class JDFJMFTest extends JDFTestCaseBase
{

	/**
	 * 
	 */
	@Test
	public void testGetMessageVector()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.Status);
		Assert.assertEquals(jmf.getMessageVector(null, EnumType.Status).elementAt(0), command);
		Assert.assertEquals(jmf.getMessageVector(null, EnumType.Status).size(), 1);
		final JDFSignal signal = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.Status);
		Assert.assertEquals(jmf.getMessageVector(null, EnumType.Status).elementAt(0), command);
		Assert.assertEquals(jmf.getMessageVector(null, EnumType.Status).elementAt(1), signal);
		Assert.assertEquals(jmf.getMessageVector(null, EnumType.Status).size(), 2);
	}

	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testInit()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		jmf.setSenderID("sid");
		final JDFCommand c = jmf.appendCommand();
		Assert.assertTrue(c.getID().indexOf("." + "sid".hashCode() + ".") != -1);
		Assert.assertTrue(jmf.toString().indexOf("xsi:type=") > 0);
		Assert.assertEquals(jmf.getMaxVersion(), jmf.getVersion(false));
		Assert.assertEquals(jmf.getMaxVersion(), c.getMaxVersion(true));
	}

	/**
	 * 
	 */
	@Test
	public void testInitMaxVersion()
	{
		JDFElement.setDefaultJDFVersion(EnumVersion.Version_1_2);
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		Assert.assertEquals(jmf.getMaxVersion(), jmf.getVersion(false));
		final JDFCommand c = jmf.appendCommand();
		Assert.assertEquals(jmf.getMaxVersion(), c.getMaxVersion(true));
	}

	// //////////////////////////////////////////////////////////////////////////
	// /
	/**
	 * 
	 */
	@Test
	public void testTheSenderID()
	{
		JDFJMF.setTheSenderID("sid");
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFCommand c = jmf.appendCommand();
		Assert.assertTrue(c.getID().indexOf("." + "sid".hashCode() + ".") != -1);
		JDFJMF.setTheSenderID(null);
	}

	/**
	 * 
	 */
	@Test
	public void testToXML()
	{
		final JDFDoc d = new JDFDoc("JMF");
		final JDFJMF jmf = d.getJMFRoot();
		jmf.appendAcknowledge();
		final JDFParser p = new JDFParser();
		final String xml = jmf.toXML();
		final JDFDoc newDoc = p.parseString(xml);
		System.out.print(xml);
		Assert.assertNotNull(newDoc);
	}

	/**
	 * 
	 */
	@Test
	public void testgetSubmissionParams()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		Assert.assertNull(jmf.getSubmissionURL());
		final JDFCommand c = jmf.appendCommand(EnumType.ResubmitQueueEntry);
		Assert.assertNull(jmf.getSubmissionURL());
		final JDFResubmissionParams rsp = c.appendResubmissionParams();
		Assert.assertNull(jmf.getSubmissionURL());
		rsp.setURL("url");
		Assert.assertEquals("url", jmf.getSubmissionURL());
	}

	// //////////////////////////////////////////////////////////////////////////
	// /
	/**
	 * 
	 */
	@Test
	public void testCreateResponse()
	{
		final JDFJMF queries = JDFJMF.createJMF(EnumFamily.Query, EnumType.Status);
		queries.appendCommand(EnumType.Resource);
		queries.appendCommand(EnumType.Resource);
		queries.appendRegistration(EnumType.Resource);

		final JDFJMF responses = queries.createResponse();
		final VElement messageVector = queries.getMessageVector(null, null);
		final VElement responseVector = responses.getMessageVector(null, null);
		Assert.assertEquals(responseVector.size(), 4);
		for (int i = 0; i < responseVector.size(); i++)
		{
			final JDFResponse r = (JDFResponse) responseVector.elementAt(i);
			final JDFMessage m = (JDFMessage) messageVector.elementAt(i);
			Assert.assertEquals(r.getrefID(), m.getID());
			Assert.assertEquals(r.getType(), m.getType());
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	@Test
	public void testCollectICSVersions()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		jmf.collectICSVersions();
		Assert.assertFalse(jmf.hasAttribute(AttributeName.ICSVERSIONS));
		final VString s12 = new VString("s1 s2", null);
		jmf.appendSignal().setICSVersions(s12);
		Assert.assertTrue(s12.containsAll(jmf.collectICSVersions()));
		Assert.assertTrue(jmf.collectICSVersions().containsAll(s12));

		jmf.appendSignal().setICSVersions(s12);
		Assert.assertTrue(s12.containsAll(jmf.collectICSVersions()));
		Assert.assertTrue(jmf.collectICSVersions().containsAll(s12));

		jmf.setICSVersions(new VString("j1 j1 j2", null));
		s12.add("j1");
		s12.add("j2");

		Assert.assertTrue(s12.containsAll(jmf.collectICSVersions()));
		Assert.assertTrue(jmf.collectICSVersions().containsAll(s12));

		Assert.assertEquals("multiple calls should not stack", jmf.getICSVersions(), jmf.collectICSVersions());
	}

	/**
	 * 
	 */
	@Test
	public void testConvertResponseToSignal()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFDoc doc2 = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf2 = doc2.getJMFRoot();
		JDFSignal s = jmf.appendSignal();
		final JDFResponse r = jmf2.appendResponse();
		final JDFQuery q = jmf.appendQuery();
		q.setType("KnownMessages");
		q.appendKnownMsgQuParams();
		r.setQuery(q);
		Assert.assertEquals("refID", q.getID(), r.getrefID());

		final JDFMessageService ms = r.appendMessageService();
		ms.setType("KnownMessages");
		s.convertResponse(r, q);
		Assert.assertEquals("type", r.getType(), s.getType());
		Assert.assertTrue("ms equal", ms.isEqual(s.getMessageService(0)));
		Assert.assertTrue(s.getXSIType().startsWith("Signal"));
		Assert.assertEquals(s.getKnownMsgQuParams(0).getNextSiblingElement(), s.getMessageService(0));

		s = jmf.appendSignal();
		s.convertResponse(r, null);
		Assert.assertEquals("type", r.getType(), s.getType());
		Assert.assertTrue("ms equal", ms.isEqual(s.getMessageService(0)));
		Assert.assertTrue(s.getXSIType().startsWith("Signal"));
	}

	/**
	 * 
	 */
	@Test
	public void testConvertResponseToSignalNameSpace()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmfQuery = doc.getJMFRoot();
		final JDFDoc doc2 = new JDFDoc(ElementName.JMF);
		final JDFJMF jmfResp = doc2.getJMFRoot();
		final JDFDoc docSig = new JDFDoc(ElementName.JMF);
		final JDFJMF jmfSig = docSig.getJMFRoot();

		JDFSignal s = jmfSig.appendSignal();
		final JDFResponse r = jmfResp.appendResponse();
		final JDFQuery q = jmfQuery.appendQuery();
		q.setType("KnownMessages");
		q.appendKnownMsgQuParams();
		r.setQuery(q);
		Assert.assertEquals("refID", q.getID(), r.getrefID());

		final JDFMessageService ms = r.appendMessageService();
		jmfResp.setAttribute("xmlns:foo", "www.foo.com");
		ms.setType("KnownMessages");
		ms.setAttribute("foo:key", "val");
		ms.appendElement("foo:bar");
		s.convertResponse(r, q);
		Assert.assertEquals("type", r.getType(), s.getType());
		Assert.assertTrue("ms equal", ms.isEqual(s.getMessageService(0)));
		Assert.assertTrue(s.getXSIType().startsWith("Signal"));
		Assert.assertEquals(s.getKnownMsgQuParams(0).getNextSiblingElement(), s.getMessageService(0));

		s = jmfQuery.appendSignal();
		s.convertResponse(r, null);
		Assert.assertEquals("type", r.getType(), s.getType());
		Assert.assertTrue("ms equal", ms.isEqual(s.getMessageService(0)));
		Assert.assertTrue(s.getXSIType().startsWith("Signal"));
	}

	/**
	 * 
	 */
	@Test
	public void testConvertResponseToAcknowledge()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFDoc doc2 = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf2 = doc2.getJMFRoot();
		JDFAcknowledge ack = jmf.appendAcknowledge();
		final JDFResponse r = jmf2.appendResponse();
		final JDFQuery q = jmf.appendQuery();
		q.setType("KnownMessages");
		r.setQuery(q);
		final JDFMessageService ms = r.appendMessageService();
		ms.setType("KnownMessages");
		ack.convertResponse(r, q);
		Assert.assertEquals("refID", q.getID(), r.getrefID());
		Assert.assertEquals("type", r.getType(), ack.getType());
		Assert.assertTrue("ms equal", ms.isEqual(ack.getMessageService(0)));
		ack = jmf.appendAcknowledge();
		ack.convertResponse(r, null);
		Assert.assertEquals("type", r.getType(), ack.getType());
		Assert.assertTrue("ms equal", ms.isEqual(ack.getMessageService(0)));
		Assert.assertTrue(ack.getXSIType().startsWith("Acknowledge"));
	}

	/**
	 * 
	 */
	@Test
	public void testSplitAcknowledge()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFDoc doc2 = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf2 = doc2.getJMFRoot();
		final JDFResponse r = jmf2.appendResponse();
		final JDFQuery q = jmf.appendQuery();
		q.setType("KnownMessages");
		r.setQuery(q);
		final JDFMessageService ms = r.appendMessageService();
		ms.setType("KnownMessages");
		final JDFAcknowledge ack = r.splitAcknowledge();
		Assert.assertEquals("refID", q.getID(), r.getrefID());
		Assert.assertEquals("type", r.getType(), ack.getType());
		Assert.assertTrue("ms equal", ms.isEqual(ack.getMessageService(0)));
		Assert.assertNull(r.getMessageService(0));
	}

	/**
	 * 
	 */
	@Test
	public void testConvertResponses()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFDoc doc2 = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf2 = doc2.getJMFRoot();
		final JDFResponse r = jmf2.appendResponse();
		final JDFQuery q = jmf.appendQuery();
		q.setType("KnownMessages");
		r.setQuery(q);
		Assert.assertEquals("refID", q.getID(), r.getrefID());

		jmf2.convertResponses(q);
		Assert.assertNull(jmf2.getResponse(0));
		Assert.assertEquals(jmf2.getSignal(0).getrefID(), q.getID());
	}

	/**
	 * 
	 */
	@Test
	public void testCreateJMF()
	{
		final JDFJMF jmf = JDFJMF.createJMF(EnumFamily.Response, EnumType.AbortQueueEntry);
		Assert.assertEquals(jmf.getResponse(0).getEnumType(), EnumType.AbortQueueEntry);
		Assert.assertEquals(jmf.getResponse(0).getLocalName(), "Response");
	}

	// ///////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	@Test
	public void testSenderIDBlank()
	{
		JDFJMF.setTheSenderID("a b");
		final JDFJMF jmf = JDFJMF.createJMF(EnumFamily.Response, EnumType.AbortQueueEntry);
		final JDFResponse response = jmf.getResponse(0);
		Assert.assertTrue("the sender id was added but stripped", response.getID().indexOf("." + "ab".hashCode() + ".") > 0);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testDeviceInfo()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFSignal s = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, null);
		s.setType("Status");
		final JDFStatusQuParams sqp = s.appendStatusQuParams();
		sqp.setDeviceDetails(EnumDeviceDetails.Brief);
		final JDFDeviceInfo di = s.appendDeviceInfo();
		di.setDeviceStatus(EnumDeviceStatus.Unknown);
		JDFJobPhase jp = di.appendJobPhase();
		Assert.assertEquals("", jp, di.getJobPhase(0));
		jp = (JDFJobPhase) di.appendElement("jdf:JobPhase", JDFElement.getSchemaURL());
		Assert.assertEquals("", jp, di.getJobPhase(1));
		Assert.assertNull("", di.getJobPhase(2));
		jp.appendNode();
		Assert.assertTrue(jp.isValid(EnumValidationLevel.Incomplete));
		jp.setAttribute("Status", "fnarf");
		Assert.assertFalse(jp.isValid(EnumValidationLevel.Incomplete));
	}

	/**
	 * 
	 */
	@Test
	public void testError()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFResponse r = (JDFResponse) jmf.appendMessageElement(EnumFamily.Response, null);
		r.setType("Status");
		r.setrefID("r1");
		final JDFNotification n = r.setErrorText("blub", null);
		Assert.assertEquals("get comment text", n.getComment(0).getText(), "blub");
		Assert.assertEquals("type", n.getType(), "Error");
		Assert.assertTrue(r.isValid(EnumValidationLevel.Complete));
		jmf.setSenderID("S1");
		Assert.assertTrue(jmf.isValid(EnumValidationLevel.Complete));
	}

	/**
	 * 
	 */
	@Test
	public void testGetMessageElement()
	{
		final JDFDoc d = new JDFDoc("JMF");
		final JDFJMF jmf = d.getJMFRoot();
		final JDFCommand c = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.Events);
		Assert.assertEquals(c, jmf.getMessageElement(EnumFamily.Command, EnumType.Events, 0));
		jmf.appendComment();

		final JDFSignal s = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.Events);
		Assert.assertEquals(s, jmf.getMessageElement(EnumFamily.Signal, EnumType.Events, 0));
		Assert.assertEquals(s, jmf.getMessageElement(null, EnumType.Events, 1));
		Assert.assertEquals(s, jmf.getMessageElement(null, null, 1));

		final JDFSignal s2 = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.Status);
		Assert.assertEquals(s2, jmf.getMessageElement(EnumFamily.Signal, EnumType.Status, 0));
		Assert.assertEquals(s2, jmf.getMessageElement(EnumFamily.Signal, null, 1));
		Assert.assertEquals(s2, jmf.getMessageElement(null, null, 2));
		Assert.assertEquals(s2, jmf.getMessageElement(null, null, -1));
		Assert.assertEquals(s, jmf.getMessageElement(null, null, -2));
		Assert.assertEquals(c, jmf.getMessageElement(null, null, -3));
		Assert.assertNull(jmf.getMessageElement(null, null, -4));
	}

	/**
	 * 
	 */
	@Test
	public void testGetResponseByRefID()
	{
		final JDFDoc d = new JDFDoc("JMF");
		final JDFJMF jmf = d.getJMFRoot();
		jmf.appendMessageElement(EnumFamily.Command, EnumType.Events);
		jmf.appendComment();

		final JDFResponse r = (JDFResponse) jmf.appendMessageElement(EnumFamily.Response, EnumType.Events);
		r.setrefID("i42");
		Assert.assertEquals(r, jmf.getResponse("i42"));
	}

	// ///////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	@Test
	public void testGetAcknowledgeByRefID()
	{
		final JDFDoc d = new JDFDoc("JMF");
		final JDFJMF jmf = d.getJMFRoot();
		jmf.appendMessageElement(EnumFamily.Command, EnumType.Events);
		jmf.appendComment();

		final JDFAcknowledge a = (JDFAcknowledge) jmf.appendMessageElement(EnumFamily.Acknowledge, EnumType.Events);
		a.setrefID("i42");
		Assert.assertEquals(a, jmf.getAcknowledge("i42"));
		Assert.assertEquals(a, jmf.getAcknowledge(null));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testJobPhase()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFSignal s = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, null);
		s.setType("Status");
		final JDFStatusQuParams sqp = s.appendStatusQuParams();
		sqp.setDeviceDetails(EnumDeviceDetails.Brief);
		final JDFDeviceInfo di = s.appendDeviceInfo();
		JDFJobPhase jp = di.appendJobPhase();
		Assert.assertEquals("", jp, di.getJobPhase(0));
		jp = (JDFJobPhase) di.appendElement("jdf:JobPhase", JDFElement.getSchemaURL());
		Assert.assertEquals("", jp, di.getJobPhase(1));
		Assert.assertNull("", di.getJobPhase(2));
		jp.appendNode();
		Assert.assertTrue(jp.isValid(EnumValidationLevel.Incomplete));
	}

	/**
	 * 
	 */
	@Test
	public void testMessage()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		jmf.setSenderID("Pippi Langstrumpf");

		final Iterator<JDFMessage.EnumFamily> it = JDFMessage.EnumFamily.iterator();
		while (it.hasNext())
		{
			final JDFMessage.EnumFamily f = it.next();
			if (f == null)
			{
				continue;
			}
			final JDFMessage m = jmf.appendMessageElement(f, null);
			m.setType("KnownMessages");

			if (f.equals(JDFMessage.EnumFamily.Acknowledge))
			{
				final JDFAcknowledge a = (JDFAcknowledge) m;
				a.setrefID("refID");
			}

			if (f.equals(JDFMessage.EnumFamily.Registration))
			{
				final JDFRegistration r = (JDFRegistration) m;
				r.appendSubscription();
			}

			Assert.assertTrue(" added messages", jmf.getMessageVector(f, null).size() == 1);
			Assert.assertTrue("xsi type", jmf.getMessageElement(f, null, 0).hasAttribute(AttributeName.XSITYPE));
			Assert.assertEquals("xsi type", jmf.getMessageElement(f, null, 0).getAttribute(AttributeName.XSITYPE), f.getName() + "KnownMessages");

		}

		Assert.assertTrue(" added messages", jmf.getMessageVector(null, null).size() == 6);
		Assert.assertTrue("valid", jmf.isValid(EnumValidationLevel.Complete));
	}

	/**
	 * 
	 */
	@Test
	public void testPrivateMessage()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFSignal s = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, null);
		s.setType("foo:test");
		s.appendDevice();
		Assert.assertNull(s.getXSIType());
		Assert.assertTrue("get device", s.getDevice(0) != null);
	}

	/**
	 * 
	 */
	@Test
	public void testReturnQueueEntry()
	{
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		final JDFJMF jmf = doc.getJMFRoot();
		final JDFCommand c = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, null);
		c.setType("ReturnQueueEntry");
		final JDFReturnQueueEntryParams rqe = c.appendReturnQueueEntryParams();
		rqe.setURL("http://foo.jdf");
		rqe.setQueueEntryID("dummyID");
		Assert.assertTrue("JDFReturnQueueEntryParams", rqe.isValid(EnumValidationLevel.Complete));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	public void tearDown() throws Exception
	{
		JDFJMF.setTheSenderID(null);
		super.tearDown();
	}

}