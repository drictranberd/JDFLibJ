/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2010 The International Cooperation for the Integration of 
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

/*
 * @author muchadie
 */
package org.cip4.jdflib.util;

import org.cip4.jdflib.JDFTestCaseBase;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * June 11, 2009
 */
public class CPUTimerTest extends JDFTestCaseBase
{

	CPUTimer t;

	/**
	 * 
	 */
	public void testCPUTime()
	{
		t.start();
		for (int i = 0; i < 100000; i++)
		{
			Math.sin(i);
		}
		assertTrue(t.getCPUTime() > 0);
	}

	/**
	 * 
	 */
	public void testToXML()
	{
		t.start();
		for (int i = 0; i < 100000; i++)
		{
			t.toXML();
		}
		t.stop();
		System.out.print(t.toXML());
		assertTrue(t.getCPUTime() > 0);
	}

	/**
	 * 
	 */
	public void testStartStop()
	{
		for (int i = 0; i < 100; i++)
		{
			t.start();
			for (int ii = 0; ii < 33000; ii++)
			{
				Math.sin(ii);
			}
			t.stop();
		}
		assertTrue(t.getCPUTime() > 0);
		assertEquals(t.getCPUTime() / 100, t.getAverageCPUTime());
		assertEquals(t.getRealTime() / 100, t.getAverageRealTime());
	}

	/**
	 * 
	 */
	public void testAverage()
	{
		t.start();
		for (int ii = 0; ii < 330000; ii++)
		{
			Math.sin(ii);
		}
		assertTrue(t.getCPUTime() > 0);
		assertEquals(t.getCPUTime(), t.getAverageCPUTime());
		assertEquals(t.getRealTime(), t.getAverageRealTime());
	}

	/**
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 * @throws Exception
	*/
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		t = new CPUTimer(false);
	}

	/**
	 * @see org.cip4.jdflib.JDFTestCaseBase#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return super.toString() + " " + t;
	}

}
