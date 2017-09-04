/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2017 The International Cooperation for the Integration of
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
package org.cip4.jdflib.elementwalker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Set;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.MimeUtilTest;
import org.cip4.jdflib.util.ThreadUtil;
import org.cip4.jdflib.util.UrlUtil;
import org.cip4.jdflib.util.UrlUtil.URLProtocol;
import org.cip4.jdflib.util.mime.MimeReader;
import org.cip4.jdflib.util.zip.ZipReader;
import org.junit.Test;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class URLExtractorTest extends JDFTestCaseBase
{
	/**
	 * @return the created doc
	 *
	 */
	public JDFDoc testWalk()
	{
		try
		{
			new MimeUtilTest().testBuildMimePackageDoc();
		}
		catch (Exception x)
		{
			fail("no build");
		}
		final String mimeFile = sm_dirTestDataTemp + File.separator + "testMimePackageDoc.mjm";

		MimeReader mr = new MimeReader(mimeFile);
		JDFDoc d = mr.getBodyPartHelper(0).getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtract");
		URLExtractor ex = new URLExtractor(dumpDir, null, "http://foo");
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") > 0);
		assertTrue(FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());
		return d;
	}

	/**
	 * @return the created doc
	 *
	 */
	@Test
	public void testWalkNoURL()
	{
		try
		{
			new MimeUtilTest().testBuildMimePackageDoc();
		}
		catch (Exception x)
		{
			fail("no build");
		}
		final String mimeFile = sm_dirTestDataTemp + File.separator + "testMimePackageDoc.mjm";

		MimeReader mr = new MimeReader(mimeFile);
		JDFDoc d = mr.getBodyPartHelper(0).getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtract");
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") < 0);
		assertTrue(write2String.indexOf("file:/") > 0);
		assertTrue(FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());
	}

	/**
	*
	*
	*/
	@Test
	public void testWantLog()
	{
		try
		{
			new MimeUtilTest().testBuildMimePackageDoc();
		}
		catch (Exception x)
		{
			fail("no build");
		}
		final String mimeFile = sm_dirTestDataTemp + File.separator + "testMimePackageDoc.mjm";

		MimeReader mr = new MimeReader(mimeFile);
		JDFDoc d = mr.getBodyPartHelper(0).getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtract");
		URLExtractor ex = new URLExtractor(dumpDir, null, "http://foo");
		ex.setWantLog(true);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") > 0);
		assertTrue(FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());
	}

	/**
	*
	*
	*/
	@Test
	public void testGetSave()
	{
		try
		{
			new MimeUtilTest().testBuildMimePackageDoc();
		}
		catch (Exception x)
		{
			fail("no build");
		}
		final String mimeFile = sm_dirTestDataTemp + File.separator + "testMimePackageDoc.mjm";

		MimeReader mr = new MimeReader(mimeFile);
		JDFDoc d = mr.getBodyPartHelper(0).getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtract");
		URLExtractor ex = new URLExtractor(dumpDir, null, "http://foo");
		ex.setWantLog(true);
		ex.walkTree(d.getJDFRoot(), null);
		Set<String> set = ex.getSaved();
		assertEquals(4, set.size());
	}

	/**
	 * extract from zip stream - also test CommentURL
	 */
	@Test
	public void testReadZip()
	{
		ZipReader zipReader = new ZipReader(sm_dirTestData + "testZip.zip");
		zipReader.getEntry("dummy.jdf");
		JDFDoc d = zipReader.getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "ZipExtractor");
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		assertTrue("", FileUtil.getFileInDirectory(dumpDir, new File("content/boo.pdf")).canRead());
		assertTrue("also extract commenturl", FileUtil.getFileInDirectory(dumpDir, new File("content/commentURL.pdf")).canRead());
	}

	/**
	 *
	 */
	@Test
	public void testIgnoreSelf()
	{
		JDFDoc d = testWalk();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtractSelf");
		URLExtractor ex = new URLExtractor(dumpDir, null, "http://foo");
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") > 0);
		assertFalse("we did not dump to #2 since our base is also foo", FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());
	}

	/**
	 *
	 */
	@Test
	public void testRelativePath()
	{
		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF("./content/boo.pdf", 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn8/dummy.jdf", 2, false);

		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn8/content/boo.pdf"));

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut8");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, sm_dirTestDataTemp + "URLIn8", "http://foo");
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/content/boo.pdf") > 0);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut8/content/boo.pdf").exists());
	}

	/**
	 *
	 */
	@Test
	public void testRelativePathFromJDF()
	{
		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF("./content/boooo.pdf", 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn9/dummy.jdf", 2, false);

		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn9/content/boooo.pdf"));

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut9");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut9/content/boooo.pdf").exists());
		assertTrue(new File(sm_dirTestDataTemp + "URLIn9/content/boooo.pdf").exists());
		assertTrue(write2String.indexOf("URLOut9/content/boooo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testFromJDF()
	{
		File file = new File(sm_dirTestDataTemp + "URLIn1/content/boooo.pdf");
		FileUtil.createNewFile(file);

		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF(UrlUtil.fileToUrl(file, false), 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn1/dummy.jdf", 2, false);

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut1");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut1/boooo.pdf").exists());
		assertTrue(file.exists());
		assertTrue(write2String.indexOf("URLOut1/boooo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testFromJDFBad()
	{
		File file = new File(sm_dirTestDataTemp + "URLIn2/content/boooo.pdf");
		FileUtil.createNewFile(file);
		file.setReadable(false);
		File out = new File(sm_dirTestDataTemp + "URLOut2/boooo.pdf");
		out.delete();
		ThreadUtil.sleep(10);

		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF(UrlUtil.fileToUrl(file, false), 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn2/dummy.jdf", 2, false);

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut2");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertFalse(out.exists());
		assertTrue(file.exists());
		assertTrue(write2String.indexOf("URLIn2/content/boooo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testFromJDFBadParent()
	{
		File file = new File(sm_dirTestDataTemp + "URLIn4/content/boooo.pdf");
		FileUtil.createNewFile(file);
		file.getParentFile().setReadable(false);
		file.getParentFile().setExecutable(false);
		File out = new File(sm_dirTestDataTemp + "URLOut4/boooo.pdf");
		out.delete();
		ThreadUtil.sleep(10);

		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF(UrlUtil.fileToUrl(file, false), 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn4/dummy.jdf", 2, false);

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut4");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertFalse(out.exists());
		assertTrue(write2String.indexOf("URLIn4/content/boooo.pdf") > 0);
		file.getParentFile().delete();
	}

	/**
	*
	*/
	@Test
	public void testFromJDFBad2()
	{
		File file = new File(sm_dirTestDataTemp + "URLIn3/content/boooo.pdf");
		file.delete();
		File out = new File(sm_dirTestDataTemp + "URLOut3/boooo.pdf");
		out.delete();
		ThreadUtil.sleep(10);

		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF(UrlUtil.fileToUrl(file, false), 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn3/dummy.jdf", 2, false);

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut3");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertFalse(out.exists());
		assertTrue(write2String.indexOf("URLIn3/content/boooo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testRelativePathFromJDFSpace()
	{
		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF("./content/boo%20oo.pdf", 0, -1);
		rl.addPDF("content/aaa%20oo.pdf", 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn5/dummy.jdf", 2, false);

		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn5/content/boo oo.pdf"));
		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn5/content/aaa oo.pdf"));

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut5");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut5/content/boo oo.pdf").exists());
		assertTrue(new File(sm_dirTestDataTemp + "URLIn5/content/boo oo.pdf").exists());
		assertTrue(write2String.indexOf("URLOut5/content/boo%20oo.pdf") > 0);
		assertTrue(write2String.indexOf("URLOut5/content/aaa%20oo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testRelativePathFromJDFUmlaut()
	{
		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF("./content/boo%C3%BCoo.pdf", 0, -1);
		rl.addPDF("content/aaa%C3%BCoo.pdf", 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn6/dummy.jdf", 2, false);

		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn6/content/booüoo.pdf"));
		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn6/content/aaaüoo.pdf"));

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut6");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut6/content/booüoo.pdf").exists());
		assertTrue(new File(sm_dirTestDataTemp + "URLIn6/content/booüoo.pdf").exists());
		assertTrue(write2String.indexOf("URLOut6/content/booüoo.pdf") > 0);
		assertTrue(write2String.indexOf("URLOut6/content/aaaüoo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testRelativePathDelete()
	{
		JDFDoc d = new JDFDoc(ElementName.JDF);
		JDFRunList rl = (JDFRunList) d.getJDFRoot().addResource(ElementName.RUNLIST, EnumUsage.Input);
		rl.addPDF("./content/booo.pdf", 0, -1);
		d.write2File(sm_dirTestDataTemp + "URLIn7/dummy.jdf", 2, false);

		FileUtil.createNewFile(new File(sm_dirTestDataTemp + "URLIn7/content/booo.pdf"));

		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLOut7");
		dumpDir.delete();
		URLExtractor ex = new URLExtractor(dumpDir, null, null);
		ex.setDeleteFile(true);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = rl.toDisplayXML(2);
		assertTrue(new File(sm_dirTestDataTemp + "URLOut7/content/booo.pdf").exists());
		assertFalse(new File(sm_dirTestDataTemp + "URLIn7/content/booo.pdf").exists());
		assertTrue(write2String.indexOf("URLOut7/content/booo.pdf") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testAddProtocol()
	{
		try
		{
			new MimeUtilTest().testBuildMimePackageDoc();
		}
		catch (Exception x)
		{
			fail("no build");
		}
		final String mimeFile = sm_dirTestDataTemp + File.separator + "testMimePackageDoc.mjm";

		MimeReader mr = new MimeReader(mimeFile);
		JDFDoc d = mr.getBodyPartHelper(0).getJDFDoc();
		assertNotNull(d);
		File dumpDir = new File(sm_dirTestDataTemp + File.separator + "URLExtract");
		FileUtil.deleteAll(dumpDir);

		URLExtractor ex = new URLExtractor(dumpDir, null, "http://foo");

		// only file protocols are modified
		ex.addProtocol(URLProtocol.file);
		ex.walkTree(d.getJDFRoot(), null);
		String write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") < 0);
		assertFalse(FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());

		// only file and cid protocols are modified
		ex.addProtocol(URLProtocol.cid);
		ex.walkTree(d.getJDFRoot(), null);
		write2String = d.write2String(0);
		assertTrue(write2String.indexOf("http://foo/url2.pdf") > 0);
		assertTrue(FileUtil.getFileInDirectory(dumpDir, new File("url2.pdf")).canRead());
	}
}
