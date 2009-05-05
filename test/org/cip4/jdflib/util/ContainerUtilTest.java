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
/*
 * @author muchadie
 */
package org.cip4.jdflib.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.ifaces.IMatches;

/**
 * general utilities for containers and objects
 * 
 * @author Rainer Prosi
 * 
 */
public class ContainerUtilTest extends JDFTestCaseBase
{
	private class SimpleMatch implements IMatches
	{
		private final int i;

		public SimpleMatch(final int pi)
		{
			super();
			this.i = pi;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cip4.jdflib.ifaces.IMatches#matches(java.lang.Object)
		 */
		public boolean matches(final Object subset)
		{
			return ((SimpleMatch) subset).i == i;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj)
		{
			return matches(obj);
		}

	}

	/**
	 * 
	 */
	public void testEquals()
	{
		assertTrue(ContainerUtil.equals(null, null));
		assertFalse(ContainerUtil.equals(null, ""));
		assertFalse(ContainerUtil.equals("", null));
		assertFalse(ContainerUtil.equals("", " "));
		assertTrue(ContainerUtil.equals("a", "a"));
	}

	/**
	 * 
	 */
	public void testGetMatch()
	{
		final Vector<SimpleMatch> v = new Vector<SimpleMatch>();
		for (int i = 0; i < 10; i++)
		{
			v.add(new SimpleMatch(i % 2));
		}
		final SimpleMatch simpleMatch1 = new SimpleMatch(1);
		assertEquals(ContainerUtil.getMatches(v, simpleMatch1).size(), 5);
		assertEquals(ContainerUtil.getMatch(v, simpleMatch1, 0), simpleMatch1);
	}

	/**
	 * 
	 */
	public void testToHashSetArray()
	{
		final String[] a = { "a", "b" };
		final Set s = ContainerUtil.toHashSet(a);
		assertTrue(s.contains("a"));
		assertTrue(s.contains("b"));
		assertFalse(s.contains("c"));
		assertEquals(s.size(), a.length);
	}

	/**
	 * 
	 */
	public void testToValueVector()
	{
		final HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < 10; i++)
		{
			hm.put("" + i, "a" + i);
		}
		final Vector<String> v = ContainerUtil.toValueVector(hm, false);
		assertEquals(v.size(), 10);
		final Vector<String> vs = ContainerUtil.toValueVector(hm, true);
		assertTrue(vs.containsAll(v));
		assertTrue(v.containsAll(vs));
		for (int i = 1; i < 10; i++)
		{
			assertTrue(vs.get(i - 1).compareTo(vs.get(i)) < 0);
		}

	}

	/**
	 * 
	 */
	public void testGetKeyVector()
	{
		final HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < 10; i++)
		{
			hm.put("" + i, "a" + i);
		}
		final Vector<String> v = ContainerUtil.getKeyVector(hm);
		assertEquals(v.size(), 10);
		final Vector<String> vs = ContainerUtil.getKeyVector(hm);
		assertTrue(vs.containsAll(v));
		assertTrue(v.containsAll(vs));
		for (int i = 0; i < 10; i++)
		{
			assertTrue(v.contains("" + i));
		}

	}

	/**
	 * 
	 */
	public void testEnsureSize()
	{
		final VString v = new VString();
		ContainerUtil.ensureSize(4, v);
		assertEquals(v.size(), 4);
		v.set(2, "foo");
		assertEquals(v.get(2), "foo");
		assertNull(v.get(0));
	}

	/**
	 * 
	 */
	public void testCompare()
	{
		assertEquals(ContainerUtil.compare("1", "0"), 1);
		assertEquals(ContainerUtil.compare("1", "1"), 0);
		assertEquals(ContainerUtil.compare("1", "2"), -1);
		assertEquals(ContainerUtil.compare("1", null), 1);
		assertEquals(ContainerUtil.compare(null, "2"), -1);
		assertEquals(ContainerUtil.compare(null, null), 0);
	}
}
