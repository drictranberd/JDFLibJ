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
 * JDFDocTest.java
 * 
 * @author Kai Mattern
 *
 * Copyright (C) 2002 Heidelberger Druckmaschinen AG. All Rights Reserved.
 */
package org.cip4.jdflib.core;

import java.io.File;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.JDFResource.EnumPartUsage;
import org.cip4.jdflib.resource.process.JDFIdentificationField;
import org.cip4.jdflib.resource.process.JDFPreview;
import org.cip4.jdflib.util.FileUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 * 
 */
public class JDFSchemaTest extends JDFTestCaseBase {
	JDFParser p = null;

	/**
	 * parse a simple JDF against all official schemas this test catches corrupt xml schemas
	 * 
	 */
	@Test
	public void testSchema() {
		final JDFDoc d = p.parseFile(new File(sm_dirTestData + "job.jdf"));
		Assert.assertNotNull(d);
	}

	/**
	 * parse a simple JDF against all official schemas this test catches corrupt xml schemas
	 * 
	 */
	@Test
	public void testDieMaking() {
		final JDFDoc d0 = new JDFDoc("JDF");
		final JDFNode n = d0.getJDFRoot();
		n.setType(EnumType.DieMaking);
		n.addResource(ElementName.TOOL, EnumUsage.Output);
		final String s = d0.write2String(2);
		final JDFDoc d = p.parseString(s);
		Assert.assertNotNull(d);
		Assert.assertNull(p.m_lastExcept);
	}

	/**
	 * parse a simple JDF against all official schemas this test catches corrupt xml schemas
	 * 
	 */
	@Test
	public void testPreviewResource() {
		final JDFDoc d0 = new JDFDoc("JDF");
		final JDFNode n = d0.getJDFRoot();
		n.setType(EnumType.PreviewGeneration);
		final JDFPreview pv = (JDFPreview) n.addResource(ElementName.PREVIEW, EnumUsage.Output);
		pv.setPartUsage(EnumPartUsage.Explicit);
		final String s = d0.write2String(2);
		final JDFDoc d = p.parseString(s);
		Assert.assertNotNull(d);
		Assert.assertNull(p.m_lastExcept);
	}

	/**
	 * parse a simple JDF against all official schemas this test catches corrupt xml schemas
	 * 
	 */
	@Test
	public void testSchemafolder() {
		File[] jdfs = FileUtil.listFilesWithExtension(new File(sm_dirTestData + "schema"), "jdf");

		for (File jdf : jdfs) {
			final JDFDoc d = p.parseFile(jdf);
			Assert.assertNotNull(d);
			log.info("Parsing: " + jdf.getName());
			Assert.assertNull("schema error in: " + jdf.getName(), p.m_lastExcept);
		}
	}

	/**
	 * parse a simple JDF against all official schemas this test catches corrupt xml schemas
	 * 
	 */
	@Test
	public void testIdentificationField() {
		final JDFDoc d0 = new JDFDoc("JDF");
		final JDFNode n = d0.getJDFRoot();
		n.setType(EnumType.Verification);
		final JDFIdentificationField idf = (JDFIdentificationField) n.addResource(ElementName.IDENTIFICATIONFIELD, EnumUsage.Input);
		idf.setPartUsage(EnumPartUsage.Explicit);
		final String s = d0.write2String(2);
		final JDFDoc d = p.parseString(s);
		Assert.assertNotNull(d);
		Assert.assertNull(p.m_lastExcept);
	}

	/**
	 * 
	 * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		KElement.setLongID(false);
		final File foo = new File(sm_dirTestSchema).getParentFile();
		Assert.assertTrue("please mount the svn schema parallel to jdflibJ", foo.isDirectory());
		p = new JDFParser();
		final File jdfxsd = new File(sm_dirTestSchema + File.separator + "JDF.xsd");
		Assert.assertTrue(jdfxsd.canRead());
		p.setJDFSchemaLocation(jdfxsd);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

}
