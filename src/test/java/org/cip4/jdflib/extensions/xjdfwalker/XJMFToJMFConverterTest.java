/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2021 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.jdflib.extensions.xjdfwalker;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoResourceCmdParams.EnumUpdateMethod;
import org.cip4.jdflib.auto.JDFAutoResourceQuParams.EnumScope;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.extensions.MessageHelper;
import org.cip4.jdflib.extensions.XJMFHelper;
import org.cip4.jdflib.jmf.JDFMessage.EnumFamily;
import org.cip4.jdflib.jmf.JDFMessage.EnumType;
import org.cip4.jdflib.jmf.JDFResourceQuParams;
import org.cip4.jdflib.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author rainer prosi
 *
 */
public class XJMFToJMFConverterTest extends JDFTestCaseBase
{

	/**
	 *
	 */
	@Test
	public void testResubmitQueueEntry()
	{
		final XJMFHelper h = new XJMFHelper();
		final MessageHelper mh = h.appendMessage(EnumFamily.Command, EnumType.ResubmitQueueEntry);
		mh.appendElement(ElementName.RESUBMISSIONPARAMS).setAttribute(AttributeName.UPDATEMETHOD, EnumUpdateMethod.Incremental.getName());
		final XJDFToJDFConverter xc = new XJDFToJDFConverter(null);
		final JDFDoc d = xc.convert(h.getRoot());
		Assertions.assertNotNull(d);
		// assertTrue(d.getJMFRoot().isValid(EnumValidationLevel.Incomplete));
	}

	/**
	 *
	 */
	@Test
	public void testScope()
	{
		final XJMFHelper h = new XJMFHelper();
		final MessageHelper mh = h.appendMessage(EnumFamily.Query, EnumType.Resource);
		mh.appendElement(ElementName.RESOURCEQUPARAMS).setAttribute(AttributeName.SCOPE, EnumScope.Allowed.getName());
		final XJDFToJDFConverter xc = new XJDFToJDFConverter(null);
		final JDFDoc d = xc.convert(h.getRoot());
		final JDFResourceQuParams rqp2 = d.getJMFRoot().getQuery(0).getResourceQuParams();
		Assertions.assertEquals(EnumScope.Allowed, rqp2.getScope());
	}

	/**
	 *
	 */
	@Test
	public void testSenderID()
	{
		final XJMFHelper h = new XJMFHelper();
		final MessageHelper mh = h.appendMessage(EnumFamily.Signal, EnumType.Status);
		mh.appendElement(ElementName.DEVICEINFO).setAttribute(AttributeName.DEVICEID, "d1");
		final XJDFToJDFConverter xc = new XJDFToJDFConverter(null);
		final JDFDoc d = xc.convert(h.getRoot());
		Assertions.assertNull(StringUtil.getNonEmpty(d.getJMFRoot().getDeviceID()));
	}

	/**
	 *
	 */
	@Test
	public void testSenderID2()
	{
		final XJMFHelper h = new XJMFHelper();
		h.getHeader().setAttribute(AttributeName.DEVICEID, "xjmfdev");
		final MessageHelper mh = h.appendMessage(EnumFamily.Query, EnumType.Status);
		mh.getHeader().setAttribute(AttributeName.DEVICEID, "qdev");
		mh.appendElement(ElementName.SUBSCRIPTION).setAttribute(AttributeName.URL, "foo");
		final XJDFToJDFConverter xc = new XJDFToJDFConverter(null);
		final JDFDoc d = xc.convert(h.getRoot());
		Assertions.assertNull(StringUtil.getNonEmpty(d.getJMFRoot().getDeviceID()));
	}

	/**
	 *
	 */
	@Test
	public void testSenderID3()
	{
		final XJMFHelper h = new XJMFHelper();
		h.getHeader().setAttribute(AttributeName.DEVICEID, "xjmfdev");
		final MessageHelper mh = h.appendMessage(EnumFamily.Query, EnumType.Status);
		mh.getHeader().setAttribute(AttributeName.DEVICEID, "qdev");
		mh.appendElement(ElementName.SUBSCRIPTION).setAttribute(AttributeName.URL, "foo");
		final XJDFToJDFConverter xc = new XJDFToJDFConverter(null);
		final JDFDoc d = xc.convert(h.getRoot());
		Assertions.assertNull(StringUtil.getNonEmpty(d.getJMFRoot().getQuery(0).getSenderID()));
	}

}
