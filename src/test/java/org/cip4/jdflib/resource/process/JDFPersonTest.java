/*
 * The CIP4 Software License, Version 1.0
 *
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
package org.cip4.jdflib.resource.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.junit.Test;

/**
 * @author prosirai
 *
 */
public class JDFPersonTest extends JDFTestCaseBase
{
	private JDFPerson person;

	/**
	 *
	 */
	@Test
	public void testFamilyName()
	{
		person.setFamilyName("Müller");
		assertEquals(person.getFamilyName(), "Müller");
		assertEquals(person.getDescriptiveName(), "Müller");
		person.setFamilyName("Meyer");
		assertEquals(person.getFamilyName(), "Meyer");
		assertEquals(person.getDescriptiveName(), "Meyer");
		person.setFamilyName("Müller");
		assertEquals(person.getFamilyName(), "Müller");
		assertEquals(person.getDescriptiveName(), "Müller");
	}

	/**
	 *
	 */
	@Test
	public void testFirstName()
	{
		person.setFirstName("Joe");
		assertEquals(person.getFirstName(), "Joe");
		assertEquals(person.getDescriptiveName(), "Joe");
		person.setFirstName("Mary");
		assertEquals(person.getFirstName(), "Mary");
		assertEquals(person.getDescriptiveName(), "Mary");
		person.setFirstName("Joe");
		assertEquals(person.getFirstName(), "Joe");
		assertEquals(person.getDescriptiveName(), "Joe");
	}

	/**
	 *
	 */
	@Test
	public void testFirstLastName()
	{
		testFirstName();
		person.setFamilyName("M�ller");
		assertEquals(person.getFamilyName(), "M�ller");
		assertEquals(person.getDescriptiveName(), "Joe M�ller");
		person.setFirstName("Mary");
		assertEquals(person.getFirstName(), "Mary");
		assertEquals(person.getDescriptiveName(), "Mary M�ller");
		person.setFamilyName("Meyer");
		assertEquals(person.getFamilyName(), "Meyer");
		assertEquals(person.getDescriptiveName(), "Mary Meyer");
		person.setFamilyName("Meyer");
		assertEquals(person.getFamilyName(), "Meyer");
		assertEquals(person.getDescriptiveName(), "Mary Meyer");
		person.setFamilyName("Schmidt");
		assertEquals(person.getFamilyName(), "Schmidt");
		assertEquals(person.getDescriptiveName(), "Mary Schmidt");
		person.setFamilyName(null);
		assertFalse(person.hasAttribute("FamilyName"));
		assertEquals(person.getDescriptiveName(), "Mary Schmidt");
	}

	/**
	 *
	 */
	@Test
	public void testLastFirstName()
	{
		testFamilyName();
		person.setFirstName("Joe");
		assertEquals(person.getFirstName(), "Joe");
		assertEquals(person.getDescriptiveName(), "Joe Müller");
	}

	/**
	*
	*/
	@Test
	public void testPhoneticFirstName()
	{
		testFamilyName();
		person.setPhoneticFirstName("Joe");
		assertEquals(person.getPhoneticFirstName(), "Joe");
	}

	/**
	*
	*/
	@Test
	public void testPhoneticLastName()
	{
		testFamilyName();
		person.setPhoneticLastName("Joe");
		assertEquals(person.getPhoneticLastName(), "Joe");
	}

	/**
	 *
	 */
	@Test
	public void testKeepDescName()
	{
		person.setDescriptiveName("foo");
		person.setFirstName("Joe");
		assertEquals(person.getFirstName(), "Joe");
		assertEquals("no overwrite of non-matching name", person.getDescriptiveName(), "foo");
	}

	/**
	 *
	 */
	@Test
	public void testGetDescName()
	{
		person.setFirstName("Joe");
		assertEquals(person.getDescriptiveName(), "Joe");
		person.setFamilyName("Cool");
		assertEquals("create correct descname", person.getDescriptiveName(), "Joe Cool");
		person.setFirstName(null);
		assertEquals("create correct descname", person.getDescriptiveName(), "Cool");
		person.setNamePrefix("Prof.");
		person.setFirstName("Joe");
		assertEquals("create correct descname", person.getDescriptiveName(), "Prof. Joe Cool");
		person.setNameSuffix("IV");
		assertEquals("create correct descname", person.getDescriptiveName(), "Prof. Joe Cool IV");
	}

	/**
	 *
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		person = (JDFPerson) new JDFDoc(ElementName.PERSON).getRoot();
	}

	/**
	 *
	 *
	 */
	@Test
	public final void testMatches()
	{
		JDFDoc doc = new JDFDoc("Person");
		JDFPerson c = (JDFPerson) doc.getRoot();
		JDFPerson c2 = (JDFPerson) new JDFDoc("Person").getRoot();
		assertTrue(c.matches(c2));
		c.setFirstName("foo");
		assertTrue(c.matches(c2));
		c2.setFirstName("Foo");
		assertTrue(c.matches(c));
		assertTrue(c.matches("foo"));
		c2.setFamilyName("bar");
		assertFalse(c.matches(c2));
		c.setFamilyName("bar");
		assertTrue(c.matches("foo bar"));
	}
}
