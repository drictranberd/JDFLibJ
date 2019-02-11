/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2019 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
 * 04022005 VF initial version
 */
/*
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cip4.jdflib.util;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;
import org.junit.Test;

/**
 * @author MatternK
 *
 *         To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SScanfTest extends JDFTestCaseBase
{
	/**
	 *
	 */
	@Test
	public void testSScanfInt()
	{
		Vector<Object> o = SScanf.sscanf("4", "%i");
		Integer i2 = (Integer) o.elementAt(0);
		assertEquals(i2.intValue(), 4);
		o = SScanf.sscanf("abc4", "abc%i");
		i2 = (Integer) o.elementAt(0);
		assertEquals(i2.intValue(), 4);
		o = SScanf.sscanf("abc6%", "abc%i%%");
		i2 = (Integer) o.elementAt(0);
		assertEquals(i2.intValue(), 6);
		o = SScanf.sscanf("abccv", "abc%xv");
		i2 = (Integer) o.elementAt(0);
		assertEquals(i2.intValue(), 12);
		o = SScanf.sscanf("abc55", "abc%o");
		i2 = (Integer) o.elementAt(0);
		assertEquals(i2.intValue(), 055);
	}

	/**
	 *
	 */
	@Test
	public void testSScanfDouble()
	{
		final Vector<Object> o = SScanf.sscanf("4", "%f");
		final Double d2 = (Double) o.elementAt(0);
		assertEquals(d2.intValue(), 4);
	}

	/**
	 *
	 */
	@Test
	public void testSScanfString()
	{
		Vector<Object> o = SScanf.sscanf("4", "%s");
		String s2 = (String) o.elementAt(0);
		assertEquals(s2, "4");
		o = SScanf.sscanf("abc4", "abc%s");
		s2 = (String) o.elementAt(0);
		assertEquals(s2, "4");
		o = SScanf.sscanf("abc4", "abc%s");
		s2 = (String) o.elementAt(0);
		assertEquals(s2, "4");
		o = SScanf.sscanf("abc6%", "abc%1s%%");
		s2 = (String) o.elementAt(0);
		assertEquals(s2, "6");
		o = SScanf.sscanf("abc7%", "abc%2c");
		s2 = (String) o.elementAt(0);
		assertEquals(s2, "7%");
	}

	/**
	 *
	 */
	@Test
	public void testSScanfMemString()
	{
		final long mem = new MemorySpy().getCurrentMem();
		for (int i = 0; i < 100000; i++)
		{
			Vector<Object> o = SScanf.sscanf("4", "%s");
			String s2 = (String) o.elementAt(0);
			assertEquals(s2, "4");
			o = SScanf.sscanf("abc4", "abc%s");
			s2 = (String) o.elementAt(0);
			assertEquals(s2, "4");
			o = SScanf.sscanf("abc4", "abc%s");
			s2 = (String) o.elementAt(0);
			assertEquals(s2, "4");
			o = SScanf.sscanf("abc6%", "abc%1s%%");
			s2 = (String) o.elementAt(0);
			assertEquals(s2, "6");
			o = SScanf.sscanf("abc7%", "abc%2c");
			s2 = (String) o.elementAt(0);
			assertEquals(s2, "7%");
		}
		System.gc();
		ThreadUtil.sleep(123);
		final long mem2 = new MemorySpy().getCurrentMem();
		if (mem2 > mem)
			assertEquals(mem2, mem, 42000000);
	}

	/**
	 *
	 */
	@Test
	public void testSScanfMulti()
	{
		Vector<Object> o = SScanf.sscanf("abc 4", "%s %i");
		String s2 = (String) o.elementAt(0);
		Integer i2 = (Integer) o.elementAt(1);
		assertEquals(s2, "abc");
		assertEquals(i2.intValue(), 4);
		o = SScanf.sscanf("abc 4", "a%s %i");
		s2 = (String) o.elementAt(0);
		i2 = (Integer) o.elementAt(1);
		assertEquals(s2, "bc");
		assertEquals(i2.intValue(), 4);
	}

}
