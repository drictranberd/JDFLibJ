/*
 *
 * The CIP4 Software License, Version 1.0
 *
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

package org.cip4.jdflib.resource.process;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoComChannel.EnumChannelType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.process.JDFContact.EnumContactType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author rainer prosi
 * @date Jan 18, 2013
 */
public class JDFContactTest extends JDFTestCaseBase
{

	JDFContact co;

	/**
	 *
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		final JDFDoc doc = new JDFDoc(ElementName.JDF);
		final JDFNode n = doc.getJDFRoot();
		n.setType(EnumType.Imposition);
		co = (JDFContact) n.addResource(ElementName.CONTACT, null);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testSetContactTypeEnum()
	{
		co.setContactTypes(EnumContactType.Accounting);
		Assertions.assertEquals(co.getAttribute(AttributeName.CONTACTTYPES), "Accounting");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testaddContactType()
	{
		co.addContactTypes(null);
		Assertions.assertTrue(co.getContactTypes().isEmpty());
		co.addContactTypes(EnumContactType.Accounting);
		Assertions.assertEquals(co.getAttribute(AttributeName.CONTACTTYPES), "Accounting");
		co.addContactTypes(EnumContactType.Delivery);
		Assertions.assertTrue(co.getContactTypes().contains("Accounting"));
		Assertions.assertTrue(co.getContactTypes().contains("Delivery"));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testSetContactType()
	{
		co.setContactTypes("Customer");
		Assertions.assertEquals(co.getContactTypes().get(0), "Customer");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testsetPerson()
	{
		Assertions.assertNull(co.setPerson(null, null));

		co.setPerson("foo", "bar");
		Assertions.assertEquals(co.getPerson().getFirstName(), "foo");
		Assertions.assertEquals(co.getPerson().getFamilyName(), "bar");

		co.setPerson("foo", null);
		Assertions.assertEquals(co.getPerson().getFirstName(), "foo");
		Assertions.assertEquals(co.getPerson().getFamilyName(), "");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetCreateCompany()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFCompany comp = (JDFCompany) root.addResource(ElementName.COMPANY, null);
		comp.setOrganizationName("ccc");
		co.refElement(comp);

		Assertions.assertEquals(comp, co.getCreateCompany());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetCreateCompany2()
	{
		final JDFNode root = co.getJDFRoot();

		final JDFCompany comp = (JDFCompany) root.addResource(ElementName.COMPANY, null);
		comp.setOrganizationName("ccc");
		co.refElement(comp);
		final JDFCompany comp2 = co.appendCompany();
		Assertions.assertEquals(comp, co.getCreateCompany());
		comp.deleteNode();
		Assertions.assertEquals(comp2, co.getCreateCompany());

	}

	/**
	*
	*
	*/
	@Test
	public void testGetCompany()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFCompany comp = (JDFCompany) root.addResource(ElementName.COMPANY, null);
		comp.setOrganizationName("ccc");
		co.refElement(comp);

		Assertions.assertEquals(comp, co.getCompany());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetAddress()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFAddress comp = (JDFAddress) root.addResource(ElementName.ADDRESS, null);

		co.refElement(comp);

		Assertions.assertEquals(comp, co.getAddress());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetAddressPerson()
	{
		final JDFAddress a = co.appendPerson().appendAddress();
		Assertions.assertEquals(a, co.getAddress());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetComChannel()
	{
		final JDFComChannel a = co.appendComChannel(EnumChannelType.Phone, "1234");
		Assertions.assertEquals(a, co.getComChannel(EnumChannelType.Phone));
		Assertions.assertEquals(null, co.getComChannel(EnumChannelType.Mobile));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetComChannelPerson()
	{
		final JDFComChannel a = co.appendPerson().appendComChannel(EnumChannelType.Phone, "1234");
		Assertions.assertEquals(a, co.getComChannel(EnumChannelType.Phone));
		Assertions.assertEquals(null, co.getComChannel(EnumChannelType.Mobile));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetCreateAddressPerson()
	{
		final JDFAddress a = co.appendPerson().appendAddress();
		Assertions.assertEquals(a, co.getCreateAddress());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetCreateAddress()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFAddress comp = (JDFAddress) root.addResource(ElementName.ADDRESS, null);

		co.refElement(comp);

		Assertions.assertEquals(comp, co.getCreateAddress());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetCreatePerson()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFPerson comp = (JDFPerson) root.addResource(ElementName.PERSON, null);
		co.refElement(comp);

		Assertions.assertEquals(comp, co.getCreatePerson());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetPerson()
	{
		final JDFNode root = co.getJDFRoot();
		final JDFPerson comp = (JDFPerson) root.addResource(ElementName.PERSON, null);
		co.refElement(comp);

		Assertions.assertEquals(comp, co.getPerson());
	}

	/**
	 *
	 *
	 */
	@Test
	public final void testMatches()
	{
		final JDFDoc doc = new JDFDoc(ElementName.CONTACT);
		final JDFContact c = (JDFContact) doc.getRoot();
		final JDFContact c2 = (JDFContact) new JDFDoc(ElementName.CONTACT).getRoot();
		Assertions.assertFalse(c.matches(c2));
		final JDFPerson p = c.appendPerson();
		final JDFPerson p2 = c2.appendPerson();
		Assertions.assertTrue(c.matches(c2));
		p.setFirstName("foo");
		Assertions.assertTrue(c.matches(c2));
		p2.setFirstName("Foo");
		Assertions.assertTrue(c.matches(c));
		p2.setFamilyName("bar");
		Assertions.assertFalse(c.matches(c2));
		p.setFamilyName("bar");

		c.setUserID("foo");
		Assertions.assertTrue(c.matches("foo"));
	}

	/**
	 *
	 *
	 */
	@Test
	public final void testMerge()
	{
		final JDFDoc doc = new JDFDoc(ElementName.CONTACT);
		final JDFContact c = (JDFContact) doc.getRoot();
		c.addContactTypes(EnumContactType.Accounting);
		final JDFContact c2 = (JDFContact) new JDFDoc(ElementName.CONTACT).getRoot();
		c2.addContactTypes(EnumContactType.Accounting);
		c2.addContactTypes(EnumContactType.Billing);
		final JDFPerson p = c2.appendPerson();

		c.appendComChannel(EnumChannelType.Email).setEMailLocator("a@b.com");
		c2.appendComChannel(EnumChannelType.Email).setEMailLocator("a@b.com");
		c2.appendComChannel(EnumChannelType.Phone).setPhoneNumber("+12345");
		c.merge(c2);
		Assertions.assertEquals(c.getComChannel(1).getPhoneNumber(false), "+12345");
		Assertions.assertTrue(c.getPerson().matches(p));
		Assertions.assertEquals(2, c.getContactTypes().size());
	}

	/**
	 *
	 *
	 */
	@Test
	public final void testMergeNoComChannel()
	{
		final JDFDoc doc = new JDFDoc(ElementName.CONTACT);
		final JDFContact c = (JDFContact) doc.getRoot();
		c.addContactTypes(EnumContactType.Accounting);
		final JDFContact c2 = (JDFContact) new JDFDoc(ElementName.CONTACT).getRoot();
		c2.addContactTypes(EnumContactType.Accounting);
		c2.addContactTypes(EnumContactType.Billing);
		final JDFPerson p = c2.appendPerson();

		c2.appendComChannel(EnumChannelType.Email).setEMailLocator("a@b.com");
		c2.appendComChannel(EnumChannelType.Phone).setPhoneNumber("+12345");
		c.merge(c2);
		Assertions.assertEquals(c.getComChannel(1).getPhoneNumber(false), "+12345");
		Assertions.assertTrue(c.getPerson().matches(p));
		Assertions.assertEquals(2, c.getContactTypes().size());
	}
}
