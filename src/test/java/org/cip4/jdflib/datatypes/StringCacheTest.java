/*
 *
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
 */
package org.cip4.jdflib.datatypes;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.VString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCacheTest extends JDFTestCaseBase
{

	/**
	*
	*/
	@Test
	public synchronized void testAddAll()
	{
		Assertions.assertEquals(51, StringCache.size(), 10);
		StringCache.addAll(new VString("a b c d e f g h i j"));
		Assertions.assertEquals(60, StringCache.size(), 10);
	}

	/**
	 *
	 */
	@Test
	public synchronized void testAddMany()
	{
		for (int i = 0; i < 10000; i++)
		{
			StringCache.getCreateString("" + (i % 100));
		}
		Assertions.assertEquals(110, StringCache.size(), 10);
		StringCache.getCreateString(null);
		Assertions.assertEquals(111, StringCache.size(), 10);
		StringCache.enable(false);
		Assertions.assertEquals(0, StringCache.size());
	}

	/**
	 *
	 */
	@Test
	public synchronized void testGetMany()
	{
		StringCache.enable(false);
		StringCache.enable(true);
		for (int i = 0; i < 10000; i++)
		{
			StringCache.getString("_" + (i % 100));
		}
		Assertions.assertEquals(51, StringCache.size(), 10);
		StringCache.getCreateString(null);
		Assertions.assertEquals(52, StringCache.size(), 10);
		StringCache.enable(false);
		Assertions.assertEquals(0, StringCache.size());
		StringCache.enable(true);
	}

	/**
	 *
	 */
	@Test
	public synchronized void testCreateNull()
	{
		StringCache.enable(true);
		Assertions.assertNull(StringCache.getCreateString(null));
		StringCache.enable(false);
	}

	/**
	 *
	 */
	@Test
	public synchronized void testNull()
	{
		StringCache.enable(true);
		Assertions.assertNull(StringCache.getString(null));
		StringCache.enable(false);
	}

	/**
	 * @see JDFTestCaseBase#setUp()
	 */
	@Override
	@BeforeEach
	public void setUp() throws Exception
	{
		StringCache.enable(false);
		StringCache.enable(true);
		super.setUp();
	}

	/**
	 * @see JDFTestCaseBase#tearDown()
	 */
	@Override
	public void tearDown() throws Exception
	{
		StringCache.enable(false);
		super.tearDown();
	}

}
