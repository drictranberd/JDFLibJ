/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2013 The International Cooperation for the Integration of 
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
package org.cip4.jdflib.util.xml;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.core.XMLParser;
import org.cip4.jdflib.util.ByteArrayIOStream;
import org.junit.Test;

/**
 * @author rainer prosi
 * @date Jan 22, 2013
 */
public class XSLTransformHelperTest extends JDFTestCaseBase
{
	/**
	 * make sure we also get all valid deep elements
	 */
	@Test
	public void testGetTransformElement()
	{
		XMLDoc xsl = new XMLDoc("xsl:stylesheet", "http://www.w3.org/1999/XSL/Transform");
		KElement style = xsl.getRoot();
		KElement template = style.appendElement("xsl:template");
		template.setAttribute("match", "*");
		template.appendElement("html", "http://www.w3.org/1999/xhtml");
		KElement a = new XMLDoc("a", null).getRoot();
		KElement t = new XSLTransformHelper(a, xsl).getTransformElement().getRoot();
		assertNotNull(t);
	}

	/**
	 * make sure we also get all valid deep elements
	 */
	@Test
	public void testGetTransformStream()
	{
		XMLDoc xsl = new XMLDoc("xsl:stylesheet", "http://www.w3.org/1999/XSL/Transform");
		KElement style = xsl.getRoot();
		KElement template = style.appendElement("xsl:template");
		template.setAttribute("match", "*");
		template.appendElement("html", "http://www.w3.org/1999/xhtml");
		KElement a = new XMLDoc("a", null).getRoot();
		ByteArrayIOStream s = new ByteArrayIOStream();
		new XSLTransformHelper(a, xsl).writeStream(s);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf("<html") >= 0);
	}

	/**
	* make sure we also get all valid deep elements
	*/
	@Test
	public void testXSLMath()
	{
		XMLDoc xsl = new XMLDoc("xsl:stylesheet", "http://www.w3.org/1999/XSL/Transform");
		KElement style = xsl.getRoot();
		KElement var1 = style.appendElement("xsl:variable");
		var1.setAttribute("name", "v0");
		var1.setAttribute("select", "44");
		KElement template = style.appendElement("xsl:template");
		template.setAttribute("match", "*");
		KElement var = template.appendElement("xsl:variable");
		var.setAttribute("name", "v1");
		var.setAttribute("select", "21");
		var = template.appendElement("xsl:variable");
		var.setAttribute("name", "v2");
		var.setAttribute("select", "25.4 div 72");

		KElement html = template.appendElement("html", "http://www.w3.org/1999/xhtml");
		html.appendElement("h1").appendElement("xsl:value-of").setAttribute("select", "5 * 6");
		html.appendElement("h1").appendElement("xsl:value-of").setAttribute("select", "2 * $v1");
		html.appendElement("h1").appendElement("xsl:value-of").setAttribute("select", "$v0");
		html.appendElement("h1").appendElement("xsl:value-of").setAttribute("select", "144 * $v2");
		KElement a = new XMLDoc("a", null).getRoot();
		ByteArrayIOStream s = new ByteArrayIOStream();
		new XSLTransformHelper(a, xsl).writeStream(s);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf(">30<") >= 0);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf(">42<") >= 0);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf(">44<") >= 0);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf(">50.8<") >= 0);
	}

	/**
	 * 
	 *  
	 */
	public void testXSLListMM()
	{
		XMLDoc xsl = new XMLDoc("xsl:stylesheet", "http://www.w3.org/1999/XSL/Transform");
		KElement style = xsl.getRoot();
		KElement var1 = style.appendElement("xsl:variable");
		var1.setAttribute("name", "v0");
		var1.setAttribute("select", "44");
		KElement template = style.appendElement("xsl:template");
		template.setAttribute("match", "*");
		KElement ct = template.appendElement("h1").appendElement("xsl:call-template");
		ct.setAttribute("name", "mmList");
		ct = ct.appendElement("xsl:with-param");
		ct.setAttribute("name", "pts");
		ct.setAttribute("select", ".");

		KElement a = new XMLDoc("a", null).getRoot();
		a.setText("10 20 30");
		ByteArrayIOStream s = new ByteArrayIOStream();
		String st = "<xsl:template xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" name=\"mmList\"><xsl:param name=\"pts\" /><xsl:if test=\"string-length($pts)\"><xsl:choose><xsl:when test=\"substring-before($pts, ' ')\"><xsl:value-of select=\"substring-before($pts, ' ') * 4\" />mm<xsl:text> </xsl:text><xsl:call-template name=\"mmList\"><xsl:with-param name=\"pts\" select=\"substring-after($pts, ' ')\" /></xsl:call-template></xsl:when><xsl:otherwise><xsl:value-of select=\"$pts\" />mm</xsl:otherwise></xsl:choose></xsl:if></xsl:template>";
		style.copyElement(new XMLParser().parseString(st).getRoot(), null);
		new XSLTransformHelper(a, xsl).writeStream(s);
		assertTrue(new String(s.getInputStream().getBuf()).indexOf("mm") >= 0);
	}
}
