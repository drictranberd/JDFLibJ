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
package org.cip4.jdflib.core;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 * 
 */
public class VStringTest extends TestCase
{
	/**
	 * 
	 */
	@Test
	public void testGetAllString()
	{
		final VString v = new VString();
		v.appendUnique("a");
		v.appendUnique("b");
		v.appendUnique("c");
		v.appendUnique("c");
		Assert.assertEquals("a b c", StringUtil.setvString(v, " ", null, null), "a b c");

	}

	/**
	 * 
	 */
	@Test
	public void testGet()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		Assert.assertEquals("a", v.get(0));
		Assert.assertEquals("c", v.get(3));
		Assert.assertEquals("c", v.get(-2));
		Assert.assertEquals("b", v.get(-3));
	}

	/**
	 * 
	 */
	@Test
	public void testElementAt()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		Assert.assertEquals("a", v.elementAt(0));
		Assert.assertEquals("c", v.elementAt(3));
		Assert.assertEquals("c", v.elementAt(-2));
		Assert.assertEquals("b", v.elementAt(-3));
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testRemove() throws Exception
	{
		final VString v = new VString("a b c", null);
		Assert.assertEquals(v.remove(-1), "c");
		Assert.assertEquals(v.remove(-1), "b");
		Assert.assertEquals(v.size(), 1);
	}

	/**
	 * 
	 */
	@Test
	public void testRemoveStrings()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		VString v2 = new VString(v);
		v2.removeStrings((String) null, 2);
		Assert.assertEquals(v2, v);
		v2 = new VString(v);
		v2.removeStrings("c", 1);
		Assert.assertEquals(v2.size(), 3);
		Assert.assertTrue(v2.contains("c"));
		v2 = new VString(v);
		v2.removeStrings("c", 0);
		Assert.assertFalse(v2.contains("c"));
	}

	/**
	 * 
	 */
	@Test
	public void testRemoveStringsV()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		VString v2 = new VString(v);
		v2.removeStrings((VString) null, 2);
		Assert.assertEquals(v2, v);
		v2 = new VString(v);
		v2.removeStrings(new VString("a c", null), 1);
		Assert.assertEquals(v2.size(), 3);
		Assert.assertTrue(v2.contains("c"));
		v2 = new VString(v);
		v2.removeStrings(new VString("a c", null), 0);
		Assert.assertFalse(v2.contains("c"));
		v2 = new VString(v);
		v2.removeStrings(new VString("a b c", null), 0);
		Assert.assertEquals(v2.size(), 0);
	}

	/**
	 * 
	 */
	@Test
	public void testContainsAny()
	{
		final VString v = new VString();
		v.appendUnique("a");
		v.appendUnique("b");
		v.appendUnique("c");
		v.appendUnique("c");
		Assert.assertFalse(v.containsAny(null));
		Assert.assertFalse(v.containsAny(new VString("d e", " ")));
		Assert.assertTrue(v.containsAny(new VString("b e", " ")));
		Assert.assertTrue(v.containsAny(new VString("e b", " ")));
		Assert.assertTrue(v.containsAny(new VString("g c h", " ")));
		Assert.assertTrue(v.containsAny(v));

	}

	/**
	 * 
	 */
	@Test
	public void testSort()
	{
		final VString v = new VString();
		v.add("a");
		v.add("c");
		v.add("b");
		v.sort();
		Assert.assertEquals("a b c", StringUtil.setvString(v, " ", null, null), "a b c");
	}

	/**
	 * 
	 */
	@Test
	public void testSetNull()
	{
		final VString v = new VString();
		v.add((String) null);
		v.add("b");
		v.add("c");
		Assert.assertEquals("b c", StringUtil.setvString(v, " ", null, null), "b c");
	}

	/**
	 * 
	 */
	@Test
	public void testGetSet()
	{
		final VString v = new VString();
		v.add("a");
		v.add("c");
		v.add("b");
		final Set<?> s = v.getSet();
		Assert.assertEquals(v.size(), s.size());
		Assert.assertTrue(s.contains("c"));

	}

	// /////////////////////////////////////////////////

	/**
	 * 
	 */
	@Test
	public void testUnify()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		final VString w = new VString();
		w.add("c");
		w.add("b");
		w.add("a");
		w.add("a");
		w.add("d");

		v.unify();
		Assert.assertEquals("a b c", StringUtil.setvString(v, " ", null, null), "a b c");
		v.appendUnique(w);
		Assert.assertEquals("a b c d", StringUtil.setvString(v, " ", null, null), "a b c d");

	}

	/**
	 * 
	 */
	@Test
	public void testAddAll()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		final HashSet<String> h = new HashSet<String>();
		h.add("c");
		h.add("b");
		h.add("a");
		h.add("d");

		v.unify();
		Assert.assertEquals("a b c", StringUtil.setvString(v, " ", null, null), "a b c");
		v.addAll(h);
		v.unify();
		Assert.assertEquals("a b c d", StringUtil.setvString(v, " ", null, null), "a b c d");

	}

	/**
	 * 
	 */
	@Test
	public void testConstructEmpty()
	{
		VString v = new VString("a,b,c, ,", ",");
		Assert.assertEquals(v.get(-1), " ");
		v = new VString("a,b,c,,", ",");
		Assert.assertEquals("double tokens are ignored", v.get(-1), "c");
	}

	/**
	 * 
	 */
	@Test
	public void testFactory()
	{
		Assert.assertNull(VString.getVString(null, null));
		Assert.assertNull(VString.getVString("", null));
		Assert.assertEquals(VString.getVString("a", null).get(0), "a");
	}

	/**
	 * 
	 */
	@Test
	public void testadd()
	{
		final VString v = new VString();
		v.add(EnumType.AdhesiveBinding);
		Assert.assertEquals(StringUtil.setvString(v, " ", null, null), EnumType.AdhesiveBinding.getName());
	}

	/**
	 * 
	 */
	@Test
	public void testSetElementAt()
	{
		final VString v = new VString();
		v.add("a");
		v.add("b");
		v.add("c");
		v.add("c");
		v.add("e");
		v.setElementAt("d", 3);
		Assert.assertEquals("a b c d e", StringUtil.setvString(v, " ", null, null));

	}

}
