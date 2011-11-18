/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2011 The International Cooperation for the Integration of 
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
/**
 * JDFXYPairRangeListTest.java
 *
 * @author Elena Skobchenko
 * 
 * Copyright (c) 2001-2004 The International Cooperation for the Integration 
 * of Processes in  Prepress, Press and Postpress (CIP4).  All rights reserved.
 */
package org.cip4.jdflib.datatypes;

import java.util.zip.DataFormatException;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.util.CPUTimer;

/**
 * 
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class JDFNumListTest extends JDFTestCaseBase
{
	/**
	 * 
	 * @throws Exception
	 */
	public final void testSetString() throws Exception
	{
		final JDFDoc d = new JDFDoc("JDF");
		final JDFNode n = d.getJDFRoot();

		JDFIntegerList il = null;
		il = new JDFIntegerList("1 2 INF");
		n.setAttribute("test", il, null);
		assertEquals("il", il.toString(), "1 2 INF");

		JDFNumberList nl = null;
		nl = new JDFNumberList("-INF 1.1 2.2 INF");
		n.setAttribute("test2", nl, null);
		assertEquals("nl", nl.toString(), "-INF 1.1 2.2 INF");
	}

	// ////////////////////////////////////////////////////////////

	/**
	 * @throws Exception 
	 * 
	 */
	public final void testGetIntArray() throws Exception
	{
		final JDFIntegerList il = new JDFIntegerList("1 2 INF");
		final int[] ar = il.getIntArray();
		assertEquals(3, ar.length);
		assertEquals(ar[2], Integer.MAX_VALUE);
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public final void testSetIntArray()
	{
		final int[] iArray = new int[3];
		iArray[0] = 1;
		iArray[1] = 2;
		iArray[2] = 4;
		final JDFIntegerList il = new JDFIntegerList(iArray);
		final int[] ar = il.getIntArray();
		assertEquals(iArray.length, ar.length);
		assertEquals(iArray[0], ar[0]);
		assertEquals(iArray[1], ar[1]);
		assertEquals(iArray[2], ar[2]);
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public final void testScale()
	{
		final int[] iArray = new int[3];
		iArray[0] = 1;
		iArray[1] = 2;
		iArray[2] = 4;
		final JDFIntegerList il = new JDFIntegerList(iArray);
		il.scale(2);
		final int[] ar = il.getIntArray();
		assertEquals(iArray.length, ar.length);
		assertEquals(2 * iArray[0], ar[0]);
		assertEquals(2 * iArray[1], ar[1]);
		assertEquals(2 * iArray[2], ar[2]);
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public final void testAbs()
	{
		final int[] iArray = new int[3];
		iArray[0] = 1;
		iArray[1] = -2;
		iArray[2] = 4;
		final JDFIntegerList il = new JDFIntegerList(iArray);
		il.abs();
		final int[] ar = il.getIntArray();
		assertEquals(iArray.length, ar.length);
		assertEquals(1, ar[0]);
		assertEquals(2, ar[1]);
		assertEquals(4, ar[2]);
	}

	/**
	 * 
	 */
	public final void testScaleFromCM()
	{
		final JDFShape s = new JDFShape(10, 20, 5);
		s.scaleFromCM();
		assertEquals(s.getX(), 10. * 72. / 2.54, 0);
		assertEquals(s.getY(), 20. * 72. / 2.54, 0);
		assertEquals(s.getZ(), 5. * 72. / 2.54, 0);
	}

	/**
	 * 
	 */
	public final void testScaleFromMM()
	{
		final JDFShape s = new JDFShape(100, 200, 50);
		s.scaleFromMM();
		assertEquals(s.getX(), 10. * 72. / 2.54, 0);
		assertEquals(s.getY(), 20. * 72. / 2.54, 0);
		assertEquals(s.getZ(), 5. * 72. / 2.54, 0);
	}

	/**
	 * @throws CloneNotSupportedException 
	 * 
	 */
	public final void testClone() throws CloneNotSupportedException
	{
		JDFShape s = new JDFShape(100, 200, 50);
		s = (JDFShape) s.clone();
		assertEquals(s.getX(), 100., 0);
		assertEquals(s.getY(), 200., 0);
		assertEquals(s.getZ(), 50., 0);
	}

	/**
	 * @throws CloneNotSupportedException 
	 * @throws DataFormatException 
	 * 
	 */
	public final void testMatches() throws CloneNotSupportedException, DataFormatException
	{
		JDFShape s = new JDFShape(100, 200, 50);
		JDFShape s2 = new JDFShape(102, 198, 52);
		assertTrue(s.matches(s2, 555));
		assertTrue(s.matches(s2, 2));
		assertFalse(s.matches(s2, 1));
		JDFIntegerList il = new JDFIntegerList("100 200 300");
		JDFIntegerList il2 = new JDFIntegerList("100 197 303");
		assertTrue(il.matches(il2, 555));
		assertTrue(il.matches(il2, 3));
		assertFalse(il.matches(il2, 1));
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * @throws Exception
	 */
	public final void testCopy() throws Exception
	{
		final JDFCMYKColor cmy1 = new JDFCMYKColor("1 2 3 4");
		final JDFCMYKColor cmy2 = new JDFCMYKColor(cmy1);
		assertEquals(cmy1, cmy2);
		cmy2.setK(0);
		assertEquals(cmy2.getK(), 0., 0.);
		assertEquals(cmy1.getK(), 4., 0.);

	}

	/**
	 * 
	 * @throws Exception
	 */
	public final void testContainsDouble() throws Exception
	{
		final JDFNumberList l = new JDFNumberList("1 2.0 3 4 3.0");
		assertTrue(l.contains(2.0));
		assertTrue(l.contains(4.00));
		assertTrue(l.contains(3));
		assertFalse(l.contains(0));
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public final void testContainsInt() throws Exception
	{
		final JDFIntegerList l = new JDFIntegerList("1 2 3 4 3");
		assertTrue(l.contains(2));
		assertTrue(l.contains(4));
		assertTrue(l.contains(3));
		assertFalse(l.contains(0));
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public final void testContainsAll() throws Exception
	{
		final JDFIntegerList l = new JDFIntegerList("1 2 3 4 3");
		assertTrue(l.containsAll(null));
		assertTrue(l.containsAll(new JDFIntegerList("1 2 4 3")));
		assertFalse(l.containsAll(new JDFIntegerList("1 2 4 3 5")));
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * @throws Exception 
	 * 
	 */
	public final void testContainsList() throws Exception
	{
		final JDFIntegerList l = new JDFIntegerList("1 2 3 4 3");
		assertTrue(l.contains(new JDFIntegerList("1")));
		assertTrue(l.contains(new JDFIntegerList("2 5")));

		assertFalse(l.contains(new JDFIntegerList("5")));
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public final void testRemoveElementAt()
	{
		final int[] iArray = new int[3];
		iArray[0] = 1;
		iArray[1] = 2;
		iArray[2] = 4;
		final JDFIntegerList il = new JDFIntegerList(iArray);
		assertEquals(il.getInt(-1), 4);
		il.removeElementAt(2);
		assertEquals(il.getInt(-1), 2);
		il.removeElementAt(-1);
		assertEquals(il.getInt(-1), 1);
	}

	// ////////////////////////////////////////////////////////////

	/**
	 * @throws Exception 
	 * 
	 */
	public void testGetDouble() throws Exception
	{
		final JDFNumberList nl = new JDFNumberList("1.1 2.2 3.3");
		assertEquals(nl.doubleAt(0), 1.1, 0.0);
		assertEquals(nl.doubleAt(1), 2.2, 0.0);
		assertEquals(nl.doubleAt(2), 3.3, 0.0);
		assertEquals(nl.doubleAt(-1), 3.3, 0.0);
		assertEquals(nl.doubleAt(3), 0.0, 0.0);

	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void testGetDoubleList() throws Exception
	{
		final JDFNumberList nl = new JDFNumberList("1.1 2.2 3.3");
		assertEquals(nl.getDoubleList().length, 3);
		assertEquals(nl.getDoubleList()[1], 2.2, 0.0);
		assertEquals(nl.getDoubleList()[2], 3.3, 0.0);
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void testPerformance() throws Exception
	{
		CPUTimer ct = new CPUTimer(true);
		for (int i = 0; i < 100000; i++)
		{
			new JDFNumberList("1.104534098756 2.2098256107389 3.3098563 4.4234190123874 5.5555555 6.098634198634109875 7.129875 8.9123846");
		}
		System.out.println(ct);
	}

	// ////////////////////////////////////////////////////////////
	/**
	 * @throws Exception 
	 * 
	 */
	public void testShape() throws Exception
	{
		final JDFShape nl = new JDFShape("1.1 2.2 3.3");
		assertEquals(nl.doubleAt(0), 1.1, 0.0);
		assertEquals(nl.doubleAt(1), 2.2, 0.0);
		assertEquals(nl.doubleAt(2), 3.3, 0.0);
		assertEquals(nl.getX(), 1.1, 0.0);
		assertEquals(nl.getY(), 2.2, 0.0);
		assertEquals(nl.getZ(), 3.3, 0.0);

		nl.setY(5);
		assertEquals(nl.getY(), 5, 0.0);

	}

	// ////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testShape2()
	{
		final JDFShape nl = new JDFShape(1, 2);
		assertEquals(nl.doubleAt(0), 1, 0.0);
		assertEquals(nl.doubleAt(1), 2, 0.0);
		assertEquals(nl.doubleAt(2), 0, 0.0);
		assertEquals(nl.getX(), 1, 0.0);
		assertEquals(nl.getY(), 2, 0.0);
		assertEquals(nl.getZ(), 0, 0.0);
	}
	// ////////////////////////////////////////////////////////////

}
