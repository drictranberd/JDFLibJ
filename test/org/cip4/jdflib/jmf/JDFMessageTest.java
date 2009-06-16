/*
 *
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

import junit.framework.TestCase;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.KElement.EnumValidationLevel;
import org.cip4.jdflib.jmf.JDFMessage.EnumFamily;
import org.cip4.jdflib.jmf.JDFMessage.EnumType;
import org.cip4.jdflib.util.JDFDate;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * far before May 17, 2009
 */
public class JDFMessageTest extends TestCase
{
	private JDFJMF jmf;

	/**
	 * 
	 */
	public void testIsValidMessageElement()
	{
		final JDFSignal sig = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.UpdateJDF);
		assertTrue(sig.isValidMessageElement(ElementName.UPDATEJDFCMDPARAMS, 0));
		assertFalse(sig.isValidMessageElement(ElementName.MODIFYNODECMDPARAMS, 0));

		final JDFResponse resp = (JDFResponse) jmf.appendMessageElement(EnumFamily.Response, EnumType.RepeatMessages);
		assertTrue(resp.isValidMessageElement(ElementName.SIGNAL, 3));
		assertTrue(resp.isValidMessageElement(ElementName.REGISTRATION, 3));
		assertFalse(resp.isValidMessageElement(ElementName.QUEUE, 0));

		final JDFRegistration reg = (JDFRegistration) jmf.appendMessageElement(EnumFamily.Registration, EnumType.RepeatMessages);
		assertFalse(reg.isValidMessageElement(ElementName.SIGNAL, 3));

		final JDFQuery q = (JDFQuery) jmf.appendMessageElement(EnumFamily.Query, EnumType.KnownSubscriptions);
		assertTrue(q.isValidMessageElement(ElementName.SUBSCRIPTIONFILTER, 0));
		assertFalse(q.isValidMessageElement(ElementName.SUBSCRIPTIONINFO, 0));

		final JDFCommand c = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.RequestForAuthentication);
		assertTrue(c.isValidMessageElement(ElementName.AUTHENTICATIONCMDPARAMS, 0));
		assertFalse(c.isValidMessageElement(ElementName.AUTHENTICATIONQUPARAMS, 0));

	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * 
	 */
	public void testAppendValidElement()
	{
		final JDFSignal sig = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.UpdateJDF);
		assertNotNull(sig.appendValidElement(ElementName.UPDATEJDFCMDPARAMS, null));
	}

	// //////////////////////////////////////////////////////////////////////////
	// /
	/**
	 * 
	 */
	public void testGetInvalidAttributes()
	{
		final JDFSignal sig = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.UpdateJDF);
		assertNotNull(sig.appendValidElement(ElementName.UPDATEJDFCMDPARAMS, null));
		assertFalse(sig.getInvalidAttributes(EnumValidationLevel.Complete, true, 999).contains(AttributeName.XSITYPE));
		sig.setAttribute("Type", EnumType.AbortQueueEntry.getName());
		assertTrue(sig.getInvalidAttributes(EnumValidationLevel.Complete, true, 999).contains(AttributeName.XSITYPE));
	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * 
	 */
	public void testModifyNode()
	{
		final JDFSignal sig = (JDFSignal) jmf.appendMessageElement(EnumFamily.Signal, EnumType.ModifyNode);
		final JDFModifyNodeCmdParams mnp = sig.appendModifyNodeCmdParams();
		assertNotNull(mnp);
		JDFModifyNodeCmdParams mnp2 = sig.getModifyNodeCmdParams();
		assertEquals(mnp, mnp2);
		mnp2 = sig.getCreateModifyNodeCmdParams();
		assertEquals(mnp, mnp2);
		try
		{
			sig.appendModifyNodeCmdParams();
			fail("oops");
		}
		catch (final JDFException e)
		{
			// nop
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * 
	 */
	public void testUpdateJDF()
	{
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.UpdateJDF);
		final JDFUpdateJDFCmdParams ujn = command.appendUpdateJDFCmdParams();
		assertNotNull(ujn);
		JDFUpdateJDFCmdParams ujn2 = command.getUpdateJDFCmdParams();
		assertEquals(ujn, ujn2);
		ujn2 = command.getCreateUpdateJDFCmdParams();
		assertEquals(ujn, ujn2);
		try
		{
			command.appendUpdateJDFCmdParams();
			fail("oops");
		}
		catch (final JDFException e)
		{
			// nop
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * test for the validity checks of KnownSubscriptions
	 */
	public void testKnownSubscriptions()
	{
		final JDFSignal sig = jmf.appendSignal(EnumType.KnownSubscriptions);
		final JDFCommand cmd = jmf.appendCommand(EnumType.KnownSubscriptions);
		assertNotNull(sig.appendSubscriptionFilter());
		assertNotNull(sig.appendSubscriptionInfo());
		assertNotNull(sig.appendSubscriptionInfo());
		assertNotSame(sig.appendSubscriptionInfo(), sig.appendSubscriptionInfo());
		try
		{
			sig.appendSubscriptionFilter();
			fail("one is enough");
		}
		catch (final JDFException x)
		{
			// nop
		}
		try
		{
			cmd.appendSubscriptionFilter();
			fail("not a command");
		}
		catch (final JDFException x)
		{
			// nop
		}
		try
		{
			cmd.appendSubscriptionInfo();
			fail("not a command");
		}
		catch (final JDFException x)
		{
			// nop
		}

	}

	/**
	 * 
	 */
	public void testSetType()
	{
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.UpdateJDF);
		assertEquals(command.getXSIType(), "CommandUpdateJDF");
		command.setType("foo:bar");
		assertNull(command.getXSIType());
		assertEquals(command.getType(), "foo:bar");
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		final JDFDoc doc = new JDFDoc(ElementName.JMF);
		jmf = doc.getJMFRoot();
	}

	// //////////////////////////////////////////////////////////////////////////
	// /
	/**
	 * 
	 */
	public void testSenderID()
	{
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.UpdateJDF);
		assertEquals(jmf.getSenderID(), command.getSenderID());
		command.setSenderID("foo:bar");
		assertEquals(command.getSenderID(), "foo:bar");
	}

	/**
	 * 
	 */
	public void testGetTime()
	{
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.UpdateJDF);
		assertEquals(jmf.getTimeStamp(), command.getTime());
		final JDFDate newDate = new JDFDate(10000000);
		command.setTime(newDate);
		assertEquals(command.getTime(), newDate);
	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * 
	 */
	public void testCreateResponse()
	{
		final JDFCommand command = (JDFCommand) jmf.appendMessageElement(EnumFamily.Command, EnumType.UpdateJDF);
		assertEquals(command.getXSIType(), "CommandUpdateJDF");
		command.setType("foo:bar");
		assertNull(command.getXSIType());
		assertEquals(command.getType(), "foo:bar");
		final JDFJMF resp = command.createResponse();
		final JDFResponse response = resp.getResponse(0);
		assertEquals(response, resp.getMessageElement(null, null, 0));
		assertEquals(response.getType(), "foo:bar");
		assertEquals(response.getrefID(), command.getID());

	}

	// //////////////////////////////////////////////////////////////////////////
	// /

	/**
	 * @see junit.framework.TestCase#toString()
	 */
	@Override
	public String toString()
	{
		return jmf == null ? "JMFMessageTest - null" : "JMFMessageTest:\n" + jmf;
	}

}