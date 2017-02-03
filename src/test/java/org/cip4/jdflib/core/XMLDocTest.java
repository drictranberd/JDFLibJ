/*--------------------------------------------------------------------------------------------------
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
 */

package org.cip4.jdflib.core;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.xerces.parsers.DOMParser;
import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.JDFElement.EnumValidationLevel;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.util.ByteArrayIOStream;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.jdflib.util.JDFSpawn;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.jdflib.util.ThreadUtil;
import org.cip4.jdflib.util.UrlUtil;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author MuchaD
 */
public class XMLDocTest extends JDFTestCaseBase
{

	protected abstract class MyThread implements Runnable
	{
		public XMLDoc d;
		public int iLoop;
		public Exception hook = null;
		private Object mutex = null;

		protected void waitComplete()
		{
			ThreadUtil.wait(mutex, 0);
		}

		protected abstract void runMyThread();

		/**
		 *
		 *
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run()
		{
			try
			{
				mutex = new Object();
				log.info("Starting " + iLoop);
				runMyThread();
				log.info("Completing " + iLoop);
			}
			catch (final Exception e)
			{
				hook = e;
			}
			finally
			{
				synchronized (mutex)
				{
					mutex.notifyAll();
					mutex = null;
				}
			}
		}
	}

	/**
	 *
	 *
	 * @author rainer prosi
	 * @date Jun 20, 2012
	 */
	protected class MyReadThread extends MyThread
	{

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void runMyThread()
		{
			final KElement root = d.getRoot();
			final NodeList nl = root.getElementsByTagName("elem" + iLoop % 3);
			for (int i = 0; i < nl.getLength(); i++)
			{
				// Node n=
				nl.item(i);
			}
		}
	}

	/**
	 *
	 *
	 * @author rainer prosi
	 * @date Jun 20, 2012
	 */
	protected class MyParseThread extends MyThread
	{
		/**
		 *
		 * @see org.cip4.jdflib.core.XMLDocTest.MyThread#runMyThread()
		 */
		@Override
		public void runMyThread()
		{
			System.out.println("parsing " + sm_dirTestData + "job.jdf " + iLoop);
			XMLDoc d = XMLDoc.parseFile(sm_dirTestData + "job.jdf");
			assertNotNull("parsed " + sm_dirTestData + "job.jdf " + iLoop, d);
		}
	}

	/**
	 *
	 * @author rainer prosi
	 * @date Jun 20, 2012
	 */
	protected class MyWriteThread extends MyThread
	{
		/**
		 * (non-Javadoc)
		 *
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void runMyThread()
		{
			final KElement root = d.getRoot();
			final NodeList nl = root.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++)
			{
				final Node n = nl.item(i);
				if (i % 73 == 0)
				{
					root.removeChild(n);
				}
			}
			System.out.println("Completing " + iLoop);
		}
	}

	/**
	 * thread class that writes a lot of documents
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 *
	 */
	protected class MyManyWriteThread extends MyThread
	{
		protected int outerLoop = 10;

		/**
		 * (non-Javadoc)
		 *
		 * @see java.lang.Runnable#run()
		 */
		@SuppressWarnings("synthetic-access")
		@Override
		public void runMyThread()
		{
			final KElement root = d.getRoot();
			final String baseDir = sm_dirTestDataTemp + File.separator + "threadDir";
			new File(baseDir).mkdirs();
			final String base = baseDir + File.separator + "ThreadWrite_" + iLoop + "_";
			for (int j = 0; j < outerLoop; j++)
			{
				if (j % 100 == 0)
				{
					System.out.print("\n" + iLoop + " " + j + " " + new JDFDate().getTimeISO() + " - ");
				}
				root.appendElement("bar");
				System.out.print(".");
				for (int i = 0; i < 100; i++)
				{
					final String fn = base + i + ".jdf";
					final File file = new File(fn);
					file.delete();
					// assertTrue(file.createNewFile());
					if (!d.write2File(file, 0, true))
					{
						System.out.println("snafu " + iLoop);
						throw new JDFException("Snafu");
					}
				}
			}
		}
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testSetSchemaLocation() throws Exception
	{
		final XMLDoc doc = new XMLDoc("test", null);
		doc.write2File(sm_dirTestDataTemp + "schematest.xml", 0, false); // create
		// a readable dummy
		final File schema = new File(sm_dirTestDataTemp + "schematest.xml");

		final String nsURI = "www.foo.com";
		doc.setSchemaLocation(nsURI, schema);
		assertNotNull(doc.getSchemaLocation(nsURI));
		assertEquals(doc.getSchemaLocationFile(nsURI).getCanonicalFile(), schema.getCanonicalFile());
	}

	/**
	 *
	 */
	@Test
	public void testSetValues()
	{
		final XMLDoc doc = new XMLDoc();
		final XMLDoc docOld = new XMLDoc("test", null);
		docOld.getRoot().setXPathValue("a/b/c", "text");
		docOld.getRoot().setXPathAttribute("a/b/d/@a1", "a1");
		docOld.getRoot().setXPathAttribute("a/b/d[4]/@a1", "a1");
		doc.setXPathValues(docOld.getRoot().getXPathValueMap());
		assertEquals(doc.getRoot().getXPathValueMap(), docOld.getRoot().getXPathValueMap());
	}

	/**
	 *
	 */
	@Test
	public void testDirtyIDs()
	{
		// -i bookintent.jdf -o spawned.jdf -p 4
		final String xmlFile = "bookintent.jdf";
		final String outFile = "spawned.jdf";
		final String strPartID = "4";

		final JDFParser p = new JDFParser();
		final JDFDoc jdfDocIn = p.parseFile(sm_dirTestData + xmlFile);

		assertTrue(jdfDocIn != null);
		if (jdfDocIn == null)
		{
			return; // soothe findbugs ;)
		}

		final XMLDocUserData xmlUserData = jdfDocIn.getCreateXMLDocUserData();
		xmlUserData.setDirtyPolicy(XMLDocUserData.EnumDirtyPolicy.ID);

		final JDFNode rootIn = (JDFNode) jdfDocIn.getRoot();

		JDFNode nodeToSpawn;
		if (strPartID.equals(""))
		{
			nodeToSpawn = rootIn;
		}
		else
		{
			nodeToSpawn = rootIn.getJobPart(strPartID, "");
		}

		if (nodeToSpawn == null)
		{
			fail("No such JobPartID: " + strPartID);
		}
		else
		{
			final VString vRWResources = new VString();
			vRWResources.addElement("Component");
			vRWResources.addElement("RunList");

			final VJDFAttributeMap vSpawnParts = new VJDFAttributeMap();
			final JDFSpawn spawn = new JDFSpawn(nodeToSpawn);

			final JDFNode node = spawn.spawn(xmlFile, outFile, vRWResources, vSpawnParts, false, false, false, false);

			// neu gespawntes File rausschreiben
			final JDFNode rootOut = node;
			final XMLDoc docOut = rootOut.getOwnerDocument_KElement();
			docOut.write2File(sm_dirTestDataTemp + outFile, 0, true);

			// verï¿½ndertes Ausgangsfile rausschreiben
			final String strOutXMLFile = "_" + xmlFile;
			rootIn.eraseEmptyNodes(true);
			jdfDocIn.write2File(sm_dirTestDataTemp + strOutXMLFile, 0, true);
			assertTrue("SpawnJDF ok", true);

			// test, if all changed nodes are in our list

			final VString vstrDirtyIDs = jdfDocIn.getDirtyIDs();
			assertEquals(vstrDirtyIDs.size(), 5);
			assertTrue(vstrDirtyIDs.contains("n0014")); // audit pool was added
			assertTrue(vstrDirtyIDs.contains("n0016")); // status changed:
			// waiting --> spawned
			assertTrue(vstrDirtyIDs.contains("r0017")); // SpawnStatus="SpawnedRW"
			// added
			assertTrue(vstrDirtyIDs.contains("r0018")); // SizeIntent added
		}
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testCreateElement() throws Exception
	{
		final XMLDoc d = new XMLDoc("TEST", null);
		final KElement e = (KElement) d.createElement("foo:bar");
		// e.appendElement("bar:foo");
		e.setAttribute("foo:at", "1");
		e.appendElement("bar2");
		d.getRoot().appendChild(e);
		assertEquals(e.getAttribute("foo:at"), "1");

	}

	/**
	 *
	 */
	@Test
	public void testCreateElementNoNS()
	{
		final XMLDoc d = new XMLDoc("TEST", null);
		final KElement e = (KElement) d.createElement("bar");
		assertNull(e.getNamespaceURI());
	}

	/**
	 *
	 */
	@Test
	public void testNewDocNS()
	{
		final XMLDoc d = new XMLDoc("src:test", "www.test.de");
		final KElement e = d.getRoot();
		assertEquals("www.test.de", e.getNamespaceURI());
		assertEquals(e.toXML().indexOf("xmlns="), -1);
		assertNotSame(e.toXML().indexOf("xmlns:src="), -1);
	}

	/**
	 *
	 */
	@Test
	public void testCreateElementNS()
	{
		final XMLDoc d1 = new XMLDoc("JDF2", null);
		assertEquals("XMLDoc only creates KElement", d1.getRoot().getClass(), KElement.class);
		final JDFDoc jd = new JDFDoc("JDF");
		assertEquals("JDFDoc creates typesafe elements", jd.getRoot().getClass(), JDFNode.class);
		final XMLDoc d2 = new XMLDoc("JDF2", null);
		assertEquals("XMLDoc only creates KElement - Hasmap must not be applied", d2.getRoot().getClass(), KElement.class);
	}

	/**
	 * test whether xmldoc.parse gives a clean empty kelement only doc
	 */
	@Test
	public void testParseFileString()
	{
		final XMLDoc d = new XMLDoc("JDF", null);
		final KElement root = d.getRoot();
		root.appendElement("AuditPool");
		final String fn = sm_dirTestDataTemp + "xmldocParseTest.xml";
		d.write2File(fn, 0, true);

		final XMLDoc d2 = XMLDoc.parseFile(fn);
		assertFalse(d2 instanceof JDFDoc);
		final KElement r2 = d2.getRoot();
		assertFalse(r2 instanceof JDFNode);
		assertFalse(r2.getElement("AuditPool") instanceof JDFAuditPool);
	}

	/**
	 * test whether xmldoc.parse gives a clean empty kelement only doc
	 */
	@Test
	public void testParseFile()
	{
		final XMLDoc d = new XMLDoc("Foo", null);
		final KElement root = d.getRoot();
		final String fn = sm_dirTestDataTemp + "xmldocParseTest2.xml";
		d.write2File(fn, 0, true);

		final XMLDoc d2 = XMLDoc.parseFile(new File(fn));
		assertFalse(d2 instanceof JDFDoc);
		final KElement r2 = d2.getRoot();
		assertTrue(root.isEqual(r2));
	}

	/**
	 * @throws IOException bang
	 * @throws SAXException bang
	 *
	 */
	@Test
	public void testParseDOM() throws SAXException, IOException
	{
		DOMParser domParser = new DOMParser();
		domParser.parse(new InputSource(new StringReader("<JDF ID=\"1\"><AuditPool><Created ID=\"1\"/></AuditPool></JDF>")));
		assertNotNull(domParser.getDocument());
		XMLDoc d = new XMLDoc(domParser.getDocument());
		assertNotNull(d);
		assertNotNull(d.getRoot());
		assertEquals(d.getRoot().getAttribute("ID"), "1");
		assertNotNull(d.getRoot().getXPathElement("/JDF/AuditPool/Created"));
	}

	/**
	 *
	 */
	@Test
	public void testParseNoNS()
	{
		final XMLDoc d = new XMLDoc("TEST", null);
		final String fn = sm_dirTestDataTemp + "testParseNoNS.xml";
		d.write2File(fn, 2, true);
		final JDFParser p = new JDFParser();
		final JDFDoc d2 = p.parseFile(fn);
		final KElement root = d2.getRoot();
		assertNull(root.getNamespaceURI());
		assertFalse(d2.toString().indexOf("xmlns=\"\"") >= 0);
		assertFalse(d.toString().indexOf("xmlns=\"\"") >= 0);
		assertFalse(root.toString().indexOf("xmlns=\"\"") >= 0);
		final KElement foo = root.appendElement("foofoo");
		assertNull(foo.getNamespaceURI());
		assertFalse(d.toString().indexOf("xmlns=\"\"") >= 0);
		assertFalse(root.toString().indexOf("xmlns=\"\"") >= 0);

	}

	/**
	 *
	 */
	@Test
	public void testCreateAttribute()
	{
		final XMLDoc d = new XMLDoc("TEST", null);
		final Attr a = d.createAttribute("dom1");
		assertNotNull("a", a);
		boolean bcatch = false;
		try
		{
			d.createAttribute("xmlns:foo");
		}
		catch (final Exception e)
		{
			bcatch = true;
		}
		assertTrue("catch b", !bcatch);
		bcatch = false;
		try
		{
			d.createAttribute("foo:dom1");
		}
		catch (final Exception e)
		{
			bcatch = true;
		}
		assertTrue("catch c", !bcatch);

	}

	/**
	 *
	 */
	@Test
	public void testRegisterClass()
	{
		JDFDoc.registerCustomClass("JDFTestType", "org.cip4.jdflib.core.JDFTestType");
		JDFDoc.registerCustomClass("fnarf:JDFTestType", "org.cip4.jdflib.core.JDFTestType");
		final JDFDoc d = new JDFDoc("JDF");
		final JDFNode n = d.getJDFRoot();

		JDFTestType tt = (JDFTestType) n.appendElement("JDFTestType", null);
		tt.setAttribute("fnarf", 3, null);
		assertTrue("extension is valid", tt.isValid(EnumValidationLevel.Complete));

		tt = (JDFTestType) n.appendElement("JDFTestType");
		tt.setAttribute("fnarf", 3, null);
		assertTrue("ns extension is valid", tt.isValid(EnumValidationLevel.Complete));
		tt.setAttribute("fnarf", "a", null); // illegal - must be integer
		assertTrue("ns extension is valid", !tt.isValid(EnumValidationLevel.Complete));
		tt.removeAttribute("fnarf", null);
		assertTrue("ns extension is valid", tt.isValid(EnumValidationLevel.Complete));
		tt.setAttribute("gnu", "a", null); // illegal - non existent
		assertFalse("ns extension is valid", tt.isValid(EnumValidationLevel.Complete));

		try
		{
			tt = (JDFTestType) n.appendElement("blub:JDFTestType", "WWW.fnarf2.com");
			fail("ns extension noworks");
		}
		catch (ClassCastException exc)
		{
			// nop
		}
		assertTrue("ns extension works", !(n.appendElement("blub:JDFTestType", "WWW.fnarf2.com") instanceof JDFTestType));
	}

	/**
	 *
	 */
	@Test
	public void testNSRoot()
	{
		XMLDoc d = new XMLDoc("JDF:foo", null);
		KElement e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("a:foo", "bar");
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("_foo", null);
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("bar:foo", JDFConstants.JDFNAMESPACE);
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("Myfoo", JDFConstants.JDFNAMESPACE);
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("JDF:Myfoo", JDFConstants.JDFNAMESPACE);
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);

		d = new XMLDoc("Myfoo", null);
		e = d.getRoot();
		assertFalse("E K", e instanceof JDFElement);
	}

	/**
	 * tests memory leaks in clone()
	 */
	@Test
	public void testCloneMem()
	{
		System.gc();
		final XMLDoc doc = new XMLDoc("foobar", null);
		final long l = doc.getDocMemoryUsed();
		System.out.println(l);
		for (int i = 0; i < 1000000; i++)
		{
			doc.clone();
		}
		System.gc();
		final long l2 = doc.getDocMemoryUsed();
		System.out.println(l2);
		assertTrue(l2 - l < 100000);
	}

	/**
	 * tests .clone()
	 */
	@Test
	public void testClone()
	{
		final XMLDoc doc = new XMLDoc("foobar", null);
		final XMLDoc doc2 = doc.clone();
		assertNotNull(doc.getDocumentElement());
		assertNotNull(doc2.getDocumentElement());
		assertNotSame(doc.getDocumentElement(), doc2.getDocumentElement());
		final KElement e = doc.getRoot();
		e.setAttribute("foo", "bar");
		assertTrue(e.hasAttribute("foo"));
		final KElement e2 = doc2.getRoot();
		assertFalse(e2.hasAttribute("foo"));
		assertEquals(doc.getDoctype(), doc2.getDoctype());
		assertEquals(e2.getOwnerDocument_KElement(), doc2);
	}

	/**
	 *
	 * test graceful null handling
	 */
	@Test
	public void testNull()
	{
		new XMLDoc((XMLDoc) null);
		new XMLDoc((String) null, null);
	}

	/**
	 *
	 */
	@Test
	public void testCopyXMLDoc()
	{
		final XMLDoc d1 = new XMLDoc("JDF", null);
		final KElement root = d1.getRoot();
		assertFalse(root instanceof JDFNode);
		XMLDoc d = new XMLDoc(d1);
		KElement n = d.getRoot();
		assertFalse(n instanceof JDFNode);
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFile()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = sm_dirTestDataTemp + File.separator + "dir" + File.separator + "dir2";
		final File dir = new File(out);
		if (dir.isDirectory())
		{
			dir.delete();
		}
		else
		{
			dir.mkdirs();
		}

		out += File.separator + "d.xml";

		assertTrue(d.write2File(out, 2, true));
		final File f = new File(out);
		assertTrue(f.canRead());
	}

	/**
	 *
	 */
	@Test
	public void testWriteToStringIndent()
	{
		final XMLDoc d = new XMLDoc("a", null);
		final KElement e = d.getRoot();
		e.appendElement("b");
		String s = d.write2String(2);
		assertTrue(s.indexOf("\n ") > 0);
		s = d.write2String(0);
		assertTrue(s.endsWith("<a><b/></a>"));
	}

	/**
	 *
	 */
	@Test
	public void testWriteToStringEscape()
	{
		final XMLDoc d = new XMLDoc("Example", null);
		final KElement e = d.getRoot();
		e.setAttribute("URL", "file://myHost/a/c%20€%25.pdf");
		String s = d.write2String(2);
		final byte[] b = StringUtil.getUTF8Bytes(s);
		s = new String(b);
		assertTrue(s.indexOf("€") >= 0);
	}

	/**
	 *
	 */
	@Test
	public void testWriteToStringHash13()
	{
		String s0 = "<Example at=\"a&#13;b\">AA&#13;BB</Example>";
		final XMLDoc d = new XMLParser().parseString(s0);
		String s = d.write2String(2);
		assertTrue(s.indexOf("&#xd;") >= 0);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testWriteToStreamIndent() throws Exception
	{
		final XMLDoc d = new XMLDoc("a", null);
		final KElement e = d.getRoot();
		final KElement b = e.appendElement("b");
		ByteArrayIOStream bos = new ByteArrayIOStream();
		d.write2Stream(bos, 2, false);
		String s = new String(bos.getBuf());
		assertTrue(s.indexOf("\n ") > 0);
		final String text = "aa\nbb\n";
		b.setText(text);
		bos = new ByteArrayIOStream();
		d.write2Stream(bos, 2, false);
		s = new String(bos.getBuf());
		assertTrue(s.indexOf(text) > 0);
		final JDFParser p = new JDFParser();
		// JDFDoc dd =
		p.parseStream(bos.getInputStream());
		bos = new ByteArrayIOStream();
		d.write2Stream(bos, 2, false);
		s = new String(bos.getBuf());
		assertTrue(s.indexOf(text) > 0);
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileThreadRead()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		final String out = sm_dirTestDataTemp + File.separator + "Thread.jdf";

		final KElement root = d.getRoot();
		for (int i = 0; i < 1000; i++)
		{
			root.appendElement("elem2").appendElement("elem3").setAttribute("foo", "bar" + i);
		}

		final MyReadThread[] mrs = new MyReadThread[100];
		for (int i = 0; i < 100; i++)
		{
			final MyReadThread mr = new MyReadThread();
			mr.d = d;
			mr.iLoop = i;
			mrs[i] = mr;
			new Thread(mr).start();

		}
		System.out.println("Writing start");
		assertTrue(d.write2File(out, 2, true));
		System.out.println("Writing done");

		final File f = new File(out);
		assertTrue(f.canRead());
		for (int i = 0; i < 100; i++)
		{
			if (mrs[i].hook != null)
			{
				fail("exception: " + mrs[i].hook);
			}
		}
	}

	/**
	 * see if we can concurrently parse files (locking/threading)
	 */
	@Test
	public void testParseFileThreadRead()
	{
		MyParseThread[] threads = new MyParseThread[10];
		for (int i = 0; i < 10; i++)
		{
			final MyParseThread pt = new MyParseThread();
			pt.iLoop = i;
			threads[i] = pt;
			new Thread(pt).start();
		}
		for (int i = 0; i < 10; i++)
		{
			threads[i].waitComplete();
		}
		log.info("all done");
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileThreadWrite()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		final String out = sm_dirTestDataTemp + File.separator + "Thread.jdf";

		final KElement root = d.getRoot();
		for (int i = 0; i < 1000; i++)
		{
			root.appendElement("elem0").appendElement("elem1").appendElement("elem2").setAttribute("foo", "bar" + i);
		}
		final MyWriteThread[] mrs = new MyWriteThread[10];
		for (int i = 0; i < 10; i++)
		{
			final MyWriteThread mr = new MyWriteThread();
			mr.d = d;
			mr.iLoop = i;
			mrs[i] = mr;
			new Thread(mr).start();

		}
		System.out.println("Writing start");
		assertTrue(d.write2File(out, 2, true));
		System.out.println("Writing done");
		for (int i = 0; i < 10; i++)
		{
			if (mrs[i].hook != null)
			{
				// fail("exception: "+h.e);
				System.out.println("******** Xerces known defect: not threadsafe: " + mrs[i].hook);
			}
		}

		final File f = new File(out);
		assertTrue(f.canRead());
	}

	/**
	 * test many many writes in parallel threads note that the documents themselves are independent
	 */
	@Test
	public void testWriteToFileThreadWriteMany()
	{
		final MyManyWriteThread[] threads = new MyManyWriteThread[10];
		for (int i = 0; i < 10; i++)
		{
			final MyManyWriteThread mr = new MyManyWriteThread();
			mr.d = new XMLDoc("doc", null);
			mr.iLoop = i;
			mr.outerLoop = 1;// 10000000; // make high number for over night tests
			threads[i] = mr;
			new Thread(mr).start();
		}
		for (int i = 0; i < 10; i++)
		{
			threads[i].waitComplete();
			if (threads[i].hook != null)
			{
				System.out.println("exception " + threads[i].hook);
				break;
			}
			System.out.println("done " + i);
		}
		System.out.println("all done ");
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileFile()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = sm_dirTestDataTemp + File.separator + "dir" + File.separator + "dir2";
		final File dir = new File(out);
		if (dir.isDirectory())
		{
			dir.delete();
		}
		else
		{
			dir.mkdirs();
		}

		out += File.separator + "d%25.xml";

		final File f = UrlUtil.urlToFile(out);
		f.delete();
		assertTrue(d.write2File(f, 2, true));
		assertTrue(f.canRead());
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileBadFile()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = "/bad::\\nogood\\junk.jdf";
		final File f = new File(out);
		f.delete();
		if (FileUtil.isWindows())
		{
			assertFalse(d.write2File(out, 2, true));
			assertFalse(f.exists());
		}
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileYen()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = sm_dirTestDataTemp + "test-¥éç05-pdf - test-¥éç05";
		final File f = new File(out);
		f.delete();
		assertTrue(d.write2File(out, 2, true));
		assertTrue(f.exists());
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileEscape()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = sm_dirTestDataTemp + "foo %2f%20bar/fnarf.xml";
		final File f = new File(out);
		f.delete();
		assertTrue(d.write2File(out, 2, true));
		assertTrue(f.exists());
	}

	/**
	 * @throws IOException TODO Include test case
	 */
	@Test
	public void testWriteToHTTPURL() throws IOException
	{
		final XMLDoc d = new XMLDoc("doc", null);
		final URL url = UrlUtil.stringToURL("http://localhost:8010/httpdump/testXMLDoc?nodump=true");
		HttpURLConnection uc = d.write2HTTPURL(url, null, null);
		if (uc != null && uc.getResponseCode() == 200)
		{
			final long t = System.nanoTime();
			long t1 = t;
			for (int i = 0; i < 1000; i++)
			{
				uc = d.write2HTTPURL(url, null, null);
				assertNotNull("loop " + i, uc);
				uc.getInputStream().read();
				uc.getInputStream().close();
				final long t2 = System.nanoTime();
				if (i % 100 == 0)
				{
					System.out.println(i + " last " + (t2 - t1) + " average " + ((t2 - t) / (i + 1)) + " total " + (t2 - t) / 1000000);
				}
				t1 = t2;

			}

		}
	}

	/**
	 * @throws IOException TODO Include test case
	 */
	@Test
	public void testWriteToURL() throws IOException
	{
		final XMLDoc d = new XMLDoc("doc", null);
		final String url = UrlUtil.normalize("http://localhost:8088/httpdump/testXMLDoc?nodump=true");
		XMLDoc resp = d.write2URL(url, null);
		if (resp != null)
		{
			final long t = System.nanoTime();
			long t1 = System.nanoTime();
			for (int i = 0; i < 10000; i++)
			{
				resp = d.write2URL(url, null);
				assertNotNull(resp);
				final long t2 = System.nanoTime();
				if (i % 100 == 0)
				{
					System.out.println(i + " " + (t2 - t1) + " " + ((t2 - t) / (i + 1)) + " " + (t2 - t) / 1000000);
				}
				t1 = t2;
			}
		}
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileNull()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String file = sm_dirTestDataTemp + "null.jdf";
		new File(file).delete();
		assertFalse(d.write2File((File) null, 2, false));
		d.setOriginalFileName(file);
		d.write2File((File) null, 2, false);
		assertTrue(new File(file).exists());
	}

	/**
	 *
	 */
	@Test
	public void testWriteToFileURL()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		String out = sm_dirTestDataTemp + File.separator + "dir" + File.separator + "dir2";
		final File dir = new File(out);
		if (dir.isDirectory())
		{
			dir.delete();
		}
		else
		{
			dir.mkdirs();
		}
		final String out2 = out + File.separator + "d .xml";
		out += File.separator + "d%20.xml";

		final File f = new File(out2);
		f.delete();
		assertNotNull(d.write2URL("File:" + out, null));
		assertTrue(f.canRead());
		assertNotNull(d.write2URL("File:" + out2, null));
		assertTrue(f.canRead());
	}

	/**
	 * tests all kinds of special characters in file names - including %, ï¿½ and umlauts
	 *
	 */
	@Test
	public void testUmlaut()
	{
		final XMLDoc d = new XMLDoc("doc", null);
		final String out = sm_dirTestDataTemp + "dir" + File.separator + "dir%20 Grüün";
		final File dir = new File(out);
		if (dir.isDirectory())
		{
			dir.delete();
		}
		else
		{
			dir.mkdirs();
		}
		final String out2 = out + File.separator + "7ä .xml";

		final File f = new File(out2);
		f.delete();
		assertTrue(d.write2File(out2, 0, true));
		assertTrue(f.canRead());

		final JDFParser p = new JDFParser();
		final JDFDoc d2 = p.parseFile(out2);
		assertNotNull(d2);
		assertEquals(d2.getRoot().getLocalName(), "doc");

	}

	/**
	 *
	 */
	@Test
	public void testSize()
	{
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		XMLDoc d = new XMLDoc("JDF:foo", null);
		long memlocal = d.getDocMemoryUsed();
		String s = d.write2String(0);
		assertTrue("mem", memlocal + 100000 > s.length());
		// the gc is messy and sometimes returns +/- a few 10k
		assertTrue("mem", memlocal + 100000 > s.length());
		d = JDFTestCaseBase.creatXMDoc();
		memlocal = d.getDocMemoryUsed();
		s = d.write2String(0);
		assertTrue("mem", memlocal + 10000 > s.length());
		d = new XMLDoc("foo", null);
		final KElement e = d.getRoot();
		final KElement e2 = e.appendElement("e2");
		final KElement e3 = e2.appendElement("e3");
		for (int i = 33; i < 999; i++)
		{
			e3.setAttribute("k" + String.valueOf(i), "value" + String.valueOf(i));
		}
		for (int j = 0; j < 99; j++)
		{
			e2.copyElement(e3, null);
		}
		memlocal = d.getDocMemoryUsed();
		s = d.write2String(0);
		assertTrue("mem", memlocal + 10000 > s.length());
	}

	/**
	 *
	 */
	@Test
	public void testCreateBig()
	{
		for (int ii = 0; ii < 4; ii++)
		{
			final XMLDoc d = ii % 2 == 0 ? new XMLDoc("foo", null) : new JDFDoc("JDF");
			final KElement e = d.getRoot();
			final long l = System.nanoTime();
			for (int j = 0; j < 2000; j++)
			{
				final KElement e2 = e.appendElement("AuditPool");
				final KElement e3 = e2.appendElement("Created");
				for (int i = 33; i < 199; i++)
				{
					if (i < 2)
					{
						e3.setAttribute("k" + String.valueOf(i), "value" + String.valueOf(i));
					}
					else
					{
						e3.setAttributeRaw("k" + String.valueOf(i), "value" + String.valueOf(i));
					}
				}
			}
			final long l2 = System.nanoTime();

			System.out.println("xmldoc create: " + ii + " " + (l2 - l) / 1000000);
			final String fil = sm_dirTestDataTemp + "big" + ii + "writ.jdf";
			d.write2File(fil, 2, false);
			final File f = new File(fil);
			final long l3 = System.nanoTime();
			System.out.println("xmldoc write: " + ii + " " + (l3 - l2) / 1000000 + " " + f.length());
			System.out.println("xmldoc total: " + ii + " " + (l3 - l) / 1000000 + "\n");
		}
	}

	// //////////////////////////////////////////////////////
	/**
	 * test whether the serializer correctly serializes quotes etc.
	 */
	@Test
	public void testEscapeStrings()
	{
		final XMLDoc d = new XMLDoc("foo", "www.foo.com");
		final KElement e = d.getRoot();
		e.setAttribute("bar", "><&'\"");
		final String s = d.write2String(0);
		assertTrue(s.indexOf("&lt;") > 0);
		assertTrue(s.indexOf("&amp;") > 0);
		assertTrue(s.indexOf("&quot;") > 0);
	}

	/**
	 *
	 */
	@Test
	public void testGetElementsByTagName()
	{
		final XMLDoc d = new XMLDoc("a", null);
		final KElement root = d.getRoot();
		final KElement b = root.appendElement("b");
		final KElement c = b.appendElement("c");
		final KElement c2 = b.appendElement("c");

		final NodeList nl = d.getElementsByTagName("c");
		assertEquals(nl.getLength(), 2);
		assertEquals(nl.item(0), c);
		assertEquals(nl.item(1), c2);
	}
}
