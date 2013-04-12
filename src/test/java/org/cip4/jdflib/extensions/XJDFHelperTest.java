/*
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

package org.cip4.jdflib.extensions;

import junit.framework.TestCase;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.junit.Assert;
import org.junit.Test;
/**
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class XJDFHelperTest extends TestCase
{
	XJDFHelper theHelper = null;

	/**
	 * 
	 * 
	 */
	@Test
	public void testGetSet()
	{
		KElement rlSet = theHelper.appendSet("Parameter", "RunList", null).getSet();
		Assert.assertEquals(rlSet, theHelper.getSet("RunList", 0).getSet());
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testGetPartition()
	{
		KElement rlSet = theHelper.getCreateSet("Parameter", "RunList", null).getCreatePartition(0, true).getResource();
		Assert.assertEquals(rlSet, theHelper.getResource("RunList", 0, 0));
		Assert.assertNull(theHelper.getResource("RunList", 0, 1));
		Assert.assertNull(theHelper.getResource("RunList", 1, 1));
		Assert.assertNull(theHelper.getResource("RunList", 1, 0));
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testCreate()
	{
		theHelper = new XJDFHelper("jID", "jpID", null);
		KElement root = theHelper.getRoot();
		Assert.assertNotNull(theHelper.getSet("NodeInfo", 0));
		Assert.assertNotNull(root);
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testFactory()
	{
		XMLDoc d = new XMLDoc(XJDF20.rootName, null);
		Assert.assertNotNull(XJDFHelper.getHelper(d));
		d = new XMLDoc("abc", null);
		Assert.assertNull(XJDFHelper.getHelper(d));
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testRootProducts()
	{
		theHelper = new XJDFHelper("jID", "jpID", null);
		KElement root = theHelper.getRoot();
		Assert.assertNull(theHelper.getRootProductHelpers());
		root.setXPathAttribute("ProductList/Product/@ID", "idproduct");
		root.setXPathAttribute("ProductList/@RootProducts", "idproduct");
		Assert.assertNotNull(theHelper.getRootProductHelpers().get(0));
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testRootProduct()
	{
		theHelper = new XJDFHelper("jID", "jpID", null);
		KElement root = theHelper.getRoot();
		root.setXPathAttribute("ProductList/Product/@ID", "idproduct");
		Assert.assertNotNull(theHelper.getRootProduct(0));
		root.setXPathAttribute("ProductList/@RootProducts", "idproduct");
		Assert.assertNotNull(theHelper.getRootProduct(0));
		Assert.assertNotNull(theHelper.getRootProduct(-1));
		Assert.assertNull(theHelper.getRootProduct(1));
		Assert.assertNull(theHelper.getRootProduct(-2));
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 * @throws Exception if snafu
	*/
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		KElement.setLongID(false);
		theHelper = new XJDFHelper(null);
	}
}
