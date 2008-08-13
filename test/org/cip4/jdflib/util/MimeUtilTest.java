/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2008 The International Cooperation for the Integration of 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFParser;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.jmf.JDFCommand;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.process.JDFFileSpec;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.resource.process.prepress.JDFColorSpaceConversionParams;
import org.cip4.jdflib.util.MimeUtil.MIMEDetails;

public class MimeUtilTest extends JDFTestCaseBase
{

	public void testGetMimeTypeFromExt() throws Exception
	{
		assertEquals(MimeUtil.TEXT_UNKNOWN, MimeUtil
				.getMimeTypeFromExt("www.foobar.com"));
		assertEquals(MimeUtil.VND_JDF, MimeUtil.getMimeTypeFromExt(".JDF"));
		assertEquals(MimeUtil.VND_JDF, MimeUtil.getMimeTypeFromExt(".jdf"));
		assertEquals(MimeUtil.VND_JDF, MimeUtil
				.getMimeTypeFromExt("http://fobar.con/snarf.jdf"));
		assertEquals(MimeUtil.VND_JMF, MimeUtil
				.getMimeTypeFromExt("http://fobar.con/snarf.JMF"));
		assertEquals(MimeUtil.TEXT_XML, MimeUtil
				.getMimeTypeFromExt("http://fobar.con/snarf.xml"));
	}

	public void testBuildMimePackageDocJMF() throws Exception
	{
		JDFDoc docJMF = new JDFDoc("JMF");
		docJMF.setOriginalFileName("JMF.jmf");
		JDFJMF jmf = docJMF.getJMFRoot();
		JDFCommand com = (JDFCommand) jmf.appendMessageElement(
				JDFMessage.EnumFamily.Command,
				JDFMessage.EnumType.SubmitQueueEntry);
		com.appendQueueSubmissionParams().setURL("TheJDF");

		JDFDoc doc = new JDFDoc("JDF");
		doc.setOriginalFileName("JDF.jdf");
		JDFNode n = doc.getJDFRoot();
		n.setType(EnumType.ColorSpaceConversion);
		JDFColorSpaceConversionParams cscp = (JDFColorSpaceConversionParams) n
				.addResource(ElementName.COLORSPACECONVERSIONPARAMS, null,
						EnumUsage.Input, null, null, null, null);
		JDFFileSpec fs0 = cscp.appendFinalTargetDevice();
		fs0.setURL(StringUtil.uncToUrl(sm_dirTestData + File.separator
				+ "test.icc", true));
		JDFRunList rl = (JDFRunList) n.addResource(ElementName.RUNLIST, null,
				EnumUsage.Input, null, null, null, null);
		rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
				+ "url1.pdf", false), 0, -1);
		for (int i = 0; i < 100; i++)
			rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
					+ "url?.pdf", false), 0, -1);
		Multipart m = MimeUtil.buildMimePackage(docJMF, doc, true);
		MimeUtil.writeToFile(m, sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc.mjm", null);

	}

	public void testGetJMFSubmission() throws Exception
	{
		JDFDoc d1 = new JDFDoc("JMF");
		d1.setOriginalFileName("JMF.jmf");
		JDFJMF jmf = d1.getJMFRoot();
		jmf.setDeviceID("gr?n?");
		JDFCommand com = (JDFCommand) jmf.appendMessageElement(
				JDFMessage.EnumFamily.Command,
				JDFMessage.EnumType.SubmitQueueEntry);

		com.appendQueueSubmissionParams().setURL("TheJDF");

		JDFDoc doc = new JDFDoc("JDF");
		doc.setOriginalFileName("JDF.jdf");
		JDFNode n = doc.getJDFRoot();
		n.setType(EnumType.ColorSpaceConversion);
		JDFColorSpaceConversionParams cscp = (JDFColorSpaceConversionParams) n
				.addResource(ElementName.COLORSPACECONVERSIONPARAMS, null,
						EnumUsage.Input, null, null, null, null);
		JDFFileSpec fs0 = cscp.appendFinalTargetDevice();
		fs0.setURL(StringUtil.uncToUrl(sm_dirTestData + File.separator
				+ "test.icc", true));
		JDFRunList rl = (JDFRunList) n.addResource(ElementName.RUNLIST, null,
				EnumUsage.Input, null, null, null, null);
		rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
				+ "url1.pdf", false), 0, -1);
		for (int i = 0; i < 100; i++)
			rl.addPDF("gr?n?" + i + ".pdf", 0, -1);
		Multipart m = MimeUtil.buildMimePackage(d1, doc, true);

		JDFDoc[] d2 = MimeUtil.getJMFSubmission(m);
		assertNotNull(d2);
		assertEquals(d2[0].getJMFRoot().getCommand(0).getQueueSubmissionParams(
				0).getURL(), "cid:JDF.jdf");
		assertEquals(d2[1].getJDFRoot().getEnumType(),
				EnumType.ColorSpaceConversion);

		// now serialize to file and reread - should still work
		MimeUtil.writeToFile(m, sm_dirTestDataTemp + "test2.mjm", null);
		Multipart m2 = MimeUtil.getMultiPart(sm_dirTestDataTemp + "test2.mjm");
		assertNotNull(m2);
		d2 = MimeUtil.getJMFSubmission(m);
		assertNotNull(d2);
		assertEquals(d2[0].getJMFRoot().getCommand(0).getQueueSubmissionParams(
				0).getURL(), "cid:JDF.jdf");
		assertEquals(d2[1].getJDFRoot().getEnumType(),
				EnumType.ColorSpaceConversion);

	}

	public void testGetPartByCID() throws Exception
	{
		testBuildMimePackageDocJMF();
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		BodyPart bp = MimeUtil.getPartByCID(mp, "JDF.jdf");
		assertNotNull(bp);
		assertEquals(bp.getFileName(), "JDF.jdf");
	}

	public void testGetContentID() throws Exception
	{
		testBuildMimePackageDocJMF();
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		BodyPart bp = MimeUtil.getPartByCID(mp, "JDF.jdf");
		assertNotNull(bp);
		assertEquals(bp.getFileName(), "JDF.jdf");
		assertEquals(MimeUtil.getContentID(bp), "JDF.jdf");
		MimeUtil.setContentID(bp, "TheJDF");
		assertEquals(MimeUtil.getContentID(bp), "TheJDF");
		MimeUtil.setContentID(bp, "<TheJDF>");
		assertEquals(MimeUtil.getContentID(bp), "TheJDF");
		MimeUtil.setContentID(bp, "<cid:TheJDF>");
		assertEquals(MimeUtil.getContentID(bp), "TheJDF");
	}

	public void testGetMultiPart() throws Exception
	{
		testBuildMimePackageDocJMF();
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		MimeUtil.writeToFile(mp, sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc_out.mjm", null);
	}

	public void testGetCreatePartByCID() throws Exception
	{
		Message message = new MimeMessage((Session) null);
		Multipart multipart = new MimeMultipart("related"); // JDF:
															// multipart/related
		message.setContent(multipart);
		BodyPart bp = MimeUtil.getCreatePartByCID(multipart, "cid1");
		bp.setContent("boo", "Text/plain");
		BodyPart bp2 = MimeUtil.getCreatePartByCID(multipart, "cid2");
		bp2.setContent("bar", "Text/plain");
		BodyPart bp3 = MimeUtil.getCreatePartByCID(multipart, "cid1");
		assertEquals(bp, bp3);
		assertEquals(multipart.getCount(), 2);
		assertEquals(bp3.getContent(), "boo");
	}

	public void testGetJDFDoc() throws Exception
	{
		testBuildMimePackageDocJMF();
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		BodyPart bp = MimeUtil.getPartByCID(mp, "JDF.jdf");
		assertNotNull(bp);
		BodyPart bp2 = MimeUtil.getPartByCID(mp, "CID:JDF.jdf");
		assertEquals(bp, bp2);
		BodyPart bp3 = MimeUtil.getPartByCID(mp, "<cid:JDF.jdf>");
		assertEquals(bp, bp3);
		assertEquals(bp.getFileName(), "JDF.jdf");
		JDFDoc d = MimeUtil.getJDFDoc(bp);
		assertNotNull(d);
		JDFNode n = d.getJDFRoot();
		assertNotNull(n);
	}

	public void testBuildMimePackageDoc() throws Exception
	{
		for (int i = 0; i < 2; i++)
		{
			JDFDoc doc = new JDFDoc("JDF");
			if (i == 1)
				doc.setOriginalFileName("JDF.jdf");
			JDFNode n = doc.getJDFRoot();
			n.setType(EnumType.ColorSpaceConversion);
			JDFColorSpaceConversionParams cscp = (JDFColorSpaceConversionParams) n
					.addResource(ElementName.COLORSPACECONVERSIONPARAMS, null,
							EnumUsage.Input, null, null, null, null);
			JDFFileSpec fs0 = cscp.appendFinalTargetDevice();
			fs0.setURL(StringUtil.uncToUrl(sm_dirTestData + File.separator
					+ "test.icc", true));
			JDFRunList rl = (JDFRunList) n.addResource(ElementName.RUNLIST,
					null, EnumUsage.Input, null, null, null, null);
			rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
					+ "url1.pdf", false), 0, -1);
			rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
					+ "url1.pdf", false), 0, -1);
			rl.addPDF(StringUtil.uncToUrl(sm_dirTestData + File.separator
					+ "url2.pdf", false), 0, -1);
			Multipart m = MimeUtil.buildMimePackage(null, doc, true);

			final String mimeFile = sm_dirTestDataTemp + File.separator
					+ "testMimePackageDoc" + (i == 0 ? "0" : "") + ".mjm";
			MimeUtil.writeToFile(m, mimeFile, null);

			Multipart mp = MimeUtil.getMultiPart(mimeFile);
			assertEquals("JDF, 2* rl, 1 icc", mp.getCount(), 4);
		}
	}

	public void testUpdateXMLMultipart() throws Exception
	{
		Message message = new MimeMessage((Session) null);
		Multipart multipart = new MimeMultipart("related"); // JDF:
															// multipart/related
		message.setContent(multipart);
		JDFDoc jDoc = new JDFDoc("JMF");

		MimeUtil.updateXMLMultipart(multipart, jDoc, null);
		final String mimeFile = sm_dirTestDataTemp + File.separator
				+ "testUpdateXML";
		MimeUtil.writeToFile(multipart, mimeFile + "0.mjm", null);
		Multipart multiparsed = MimeUtil.getMultiPart(mimeFile + "0.mjm");
		BodyPart bp = multiparsed.getBodyPart(0);
		assertTrue("cid >cid_",
				bp.getHeader(MimeUtil.CONTENT_ID)[0].length() > 5);

		JDFDoc jDoc1 = new JDFDoc("JDF");
		jDoc1.setOriginalFileName("jdf1.jdf");

		MimeUtil.updateXMLMultipart(multipart, jDoc1, null);

		MimeUtil.writeToFile(multipart, mimeFile + "1.mjm", null);
		multiparsed = MimeUtil.getMultiPart(mimeFile + "1.mjm");
		bp = multiparsed.getBodyPart(0);
		assertTrue("cid >cid_",
				bp.getHeader(MimeUtil.CONTENT_ID)[0].length() > 5);

		JDFDoc jDoc2 = new JDFDoc("JDF");
		jDoc2.setOriginalFileName("jdf1.jdf");
		jDoc2.getJDFRoot().setDescriptiveName("updated jdf");
		MimeUtil.updateXMLMultipart(multipart, jDoc2, "jdf1.jdf");
		MimeUtil.writeToFile(multipart, mimeFile + "2.mjm", null);

		Multipart multipart3 = MimeUtil.getMultiPart(mimeFile + "2.mjm");
		jDoc2.getJDFRoot().setDescriptiveName("3rd jdf");
		MimeUtil.updateXMLMultipart(multipart3, jDoc2, "jdf2.jdf");
		MimeUtil.writeToFile(multipart3, mimeFile + "3.mjm", null);

	}

	public void testReplaceContents() throws Exception
	{
		JDFDoc doc = new JDFDoc("JDF");
		doc.setOriginalFileName("JDF.jdf");
		JDFNode n = doc.getJDFRoot();
		n.setType(EnumType.ColorSpaceConversion);
		JDFColorSpaceConversionParams cscp = (JDFColorSpaceConversionParams) n
				.addResource(ElementName.COLORSPACECONVERSIONPARAMS, null,
						EnumUsage.Input, null, null, null, null);
		JDFFileSpec fs0 = cscp.appendFinalTargetDevice();
		fs0.setURL(StringUtil.uncToUrl(sm_dirTestData + File.separator
				+ "test.icc", true));
		Multipart mp = MimeUtil.buildMimePackage(null, doc, true);
		assertEquals("JDF,  1 icc", mp.getCount(), 2);

		BodyPart bp = mp.getBodyPart(1);
		assertNotNull(bp);
		String newContents = "hello World";
		bp.setContent(newContents, "text/plain");

		BodyPart bp2 = mp.getBodyPart(1);
		Object o = bp2.getContent();
		assertEquals(o.toString(), "hello World");

		final String mimeFile = sm_dirTestDataTemp + File.separator
				+ "testReplaceContents";
		MimeUtil.writeToFile(mp, mimeFile + ".mjm", null);

		Multipart mp2 = MimeUtil.getMultiPart(mimeFile + ".mjm");
		BodyPart bp21 = mp2.getBodyPart(1);
		Object o2 = bp21.getContent();
		assertEquals(o2.toString(), "hello World");

		BodyPart bp22 = mp2.getBodyPart(1);
		assertNotNull(bp22);
		String newContents2 = "bye World";
		bp22.setContent(newContents2, "text/plain");
		Object o22 = bp22.getContent();
		assertEquals(o22.toString(), "bye World");
		BodyPart bp23 = mp2.getBodyPart(1);
		assertEquals(bp22, bp23);

		MimeUtil.writeToFile(mp2, mimeFile + "_1.mjm", null);
		Multipart mp3 = MimeUtil.getMultiPart(mimeFile + "_1.mjm");
		BodyPart bp31 = mp3.getBodyPart(1);
		Object o3 = bp31.getContent();
		assertEquals(o3.toString(), "bye World");

	}

	/**
	 * Tests that MimeUtil can resolve FileSpec URLs with relative URLs.
	 * 
	 * @author Claes Buckwalter
	 */
	public void testResolveRelativeUrls()
	{
		// Build MIME package
		String path = sm_dirTestData + File.separator
				+ "MISPrepress-ICS-Complex.jdf";
		JDFDoc jdfDoc = new JDFParser().parseFile(path);
		assertNotNull("Could not parse JDF: " + path, jdfDoc);
		Multipart multipart = MimeUtil.buildMimePackage(null, jdfDoc, true);
		assertNotNull("Could not build multipart", multipart);
		// Verify contents
		BodyPart[] bodyParts = MimeUtil.getBodyParts(multipart);
		assertEquals(3, bodyParts.length);
		JDFDoc jdfDoc2 = MimeUtil.getJDFDoc(bodyParts[0]);
		assertNotNull(jdfDoc2);
		JDFNode jdf = jdfDoc2.getJDFRoot();
		assertNotNull(jdf);
		List fileSpecs = jdf.getChildrenByTagName(ElementName.FILESPEC, null,
				new JDFAttributeMap(AttributeName.URL, "*"), false, false, 0);
		assertEquals(3, fileSpecs.size());
		for (Iterator i = fileSpecs.iterator(); i.hasNext();)
		{
			JDFFileSpec fileSpec = (JDFFileSpec) i.next();
			String cid = fileSpec.getURL();
			assertTrue(cid.startsWith("cid:"));
			assertNotNull(MimeUtil.getPartByCID(multipart, cid));
		}

	}

	// ///////////////////////////////////////////////////

	public void testBuildMimePackage() throws Exception
	{
		JDFDoc d1 = new JDFDoc("JMF");
		d1.setOriginalFileName("JMF.jmf");
		JDFDoc d2 = new JDFDoc("JDF");
		d2.setOriginalFileName("JDF.jdf");
		Vector vXMLDocs = new Vector();
		vXMLDocs.add(d1);
		vXMLDocs.add(d2);

		Multipart m = MimeUtil.buildMimePackage(vXMLDocs);
		final File file = MimeUtil.writeToFile(m, sm_dirTestDataTemp
				+ File.separator + "test.mjm", null);

		FileInputStream fis = new FileInputStream(file);
		BodyPart[] aBp = MimeUtil.extractMultipartMime(fis);
		assertEquals(aBp.length, 2);

		assertEquals(aBp[0].getFileName(), "JMF.jmf");
		assertEquals(aBp[1].getFileName(), "JDF.jdf");
	}

	// /////////////////////////////////////////////////////

	public void testWriteToDir() throws Exception
	{
		testBuildMimePackageDocJMF();

		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		File directory = new File(sm_dirTestDataTemp + File.separator
				+ "TestWriteMime");
		if (directory.exists())
			directory.delete();
		MimeUtil.writeToDir(mp, directory);
		assertTrue(new File(sm_dirTestDataTemp + File.separator
				+ "TestWriteMime" + File.separator + "test.icc").exists());
	}

	// ////////////////////////////////////////////////////////////////////

	public void testWriteToFile() throws Exception
	{
		testBuildMimePackageDocJMF();

		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		MimeUtil.writeToFile(mp, sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc2.mjm", null);
		final File f1 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc2.mjm");
		final File f2 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc.mjm");
		assertTrue(f1.exists());
		assertEquals(f1.length(), f2.length(), 100);
		Multipart mp2 = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc2.mjm");
		assertNotNull(mp2);
		assertEquals(mp.getCount(), mp2.getCount());

	}

	// /////////////////////////////////////////////////////////////

	public void testWriteToFileMimeDetails() throws Exception
	{
		testBuildMimePackageDocJMF();

		MIMEDetails md = new MIMEDetails();
		md.modifyBoundarySemicolon = true;
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		MimeUtil.writeToFile(mp, sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc2.mjm", md);
		final File f2 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc2.mjm");
		final File f1 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc.mjm");
		assertTrue(f2.exists());
		assertEquals(f1.length(), f2.length(), 100);
		Multipart mp2 = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc2.mjm");
		assertNotNull(mp2);
		assertEquals(mp.getCount(), mp2.getCount());
		StringWriter sw = new StringWriter();
		IOUtils.copy(new FileInputStream(f2), sw);
		assertEquals(sw.getBuffer().toString().indexOf("related;"), -1);

	}

	////////////////////////////////////////////////////////////////////////////
	// //////////////////
	public void testWriteToURLFile() throws Exception
	{
		testBuildMimePackageDocJMF();

		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "testMimePackageDoc.mjm");
		final File f1 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc3.mjm");
		MimeUtil.writeToURL(mp, UrlUtil.fileToUrl(f1, false));
		final File f2 = new File(sm_dirTestDataTemp + File.separator
				+ "testMimePackageDoc.mjm");
		assertTrue(f1.exists());
		assertEquals(f1.length(), f2.length(), 100);
	}

	// /////////////////////////////////////////////////////

	public void testPerformance() throws Exception
	{
		testWritePerformance();

		long write = System.currentTimeMillis();
		Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
				+ File.separator + "performance.mjm");
		long getMP = System.currentTimeMillis();
		System.out.println("get multipart time: " + (getMP - write));
		BodyPart bp = MimeUtil.getPartByCID(mp, "bigger.pdf");
		long getCID = System.currentTimeMillis();
		System.out.println("get big time: " + (getCID - getMP));
		assertNotNull(bp);
		assertEquals(bp.getFileName(), "bigger.pdf");
		File outFile = FileUtil.streamToFile(bp.getInputStream(),
				sm_dirTestDataTemp + File.separator + "performance.pdf");
		assertNotNull(outFile);
		/*
		 * InputStream is=bp.getInputStream(); byte[] b=new byte[1000]; int l=0;
		 * while (true) { int i=is.read(b); if(i<=0) break; l+=i; }
		 */
		long extract = System.currentTimeMillis();
		//System.out.println("extracted "+l+" bytes in time: "+(extract-getCID))
		// ;
		System.out.println("extracted  bytes in time: " + (extract - getCID));

	}

	// /////////////////////////////////////////////////////
	public void testURLPerformance()
	{
		// testWritePerformance();
		try
		{
			long write = System.currentTimeMillis();
			Multipart mp = MimeUtil.getMultiPart(sm_dirTestDataTemp
					+ File.separator + "performance.mjm");
			long getMP = System.currentTimeMillis();
			System.out.println("get multipart time: " + (getMP - write));
			BodyPart bp = MimeUtil.getPartByCID(mp, "bigger.pdf");
			long getCID = System.currentTimeMillis();
			System.out.println("get big time: " + (getCID - getMP));
			assertNotNull(bp);
			assertEquals(bp.getFileName(), "bigger.pdf");
			HttpURLConnection uc = MimeUtil.writeToURL(mp,
					"http://localhost:8080/JDFUtility/dump");
			InputStream is = uc.getInputStream();
			IOUtils.copy(is, System.out);
			is.close();
			System.out.println();
			// System.out.println("extracted "+l+" bytes in time: "+(extract-
			// getCID));
			long extract = System.currentTimeMillis();
			System.out.println("sent  bytes in time: " + (extract - getCID));
		} catch (Exception x)
		{
			// nop
		}
	}

	// /////////////////////////////////////////////////////

	public void testWritePerformance() throws IOException,
			FileNotFoundException, MalformedURLException
	{
		long start = System.currentTimeMillis();
		final String big = sm_dirTestData + File.separator + "big.pdf";
		final String bigger = sm_dirTestDataTemp + File.separator
				+ "bigger.pdf";
		JDFDoc docJMF = new JDFDoc("JMF");
		docJMF.setOriginalFileName("JMF.jmf");
		JDFJMF jmf = docJMF.getJMFRoot();
		JDFCommand com = (JDFCommand) jmf.appendMessageElement(
				JDFMessage.EnumFamily.Command,
				JDFMessage.EnumType.SubmitQueueEntry);
		com.appendQueueSubmissionParams().setURL("TheJDF");
		File fBigger = new File(bigger);
		fBigger.createNewFile();
		FileInputStream fis = new FileInputStream(big);
		FileOutputStream fos = new FileOutputStream(bigger);
		byte[] b = new byte[10000];
		while (true)
		{
			int i = fis.read(b);
			if (i <= 0)
				break;
			for (int j = 0; j < 3; j++)
				fos.write(b, 0, i);
		}
		fis.close();
		fos.flush();
		fos.close();

		JDFDoc doc = new JDFDoc("JDF");
		doc.setOriginalFileName("JDF.jdf");
		JDFNode n = doc.getJDFRoot();
		n.setType(EnumType.Interpreting);
		JDFRunList rl = (JDFRunList) n.addResource(ElementName.RUNLIST,
				EnumUsage.Input);
		rl.addPDF(StringUtil.uncToUrl(bigger, false), 0, -1);
		long setup = System.currentTimeMillis();
		System.out.println("Setup time: " + (setup - start));
		Multipart m = MimeUtil.buildMimePackage(null, doc, true);
		long build = System.currentTimeMillis();
		System.out.println("Build time: " + (build - setup));
		assertNotNull(MimeUtil.writeToFile(m, sm_dirTestDataTemp
				+ "performance.mjm", null));
		long write = System.currentTimeMillis();
		System.out.println("Write time: " + (write - build));

	}
}
