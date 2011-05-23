/*
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
 * 04022005 VF initial version
 */
/*
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cip4.jdflib.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 * 
 */
public class FileUtilTest extends JDFTestCaseBase
{

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public void testisAbsoluteFile()
	{
		assertFalse(FileUtil.isAbsoluteFile(new File("foo")));
		assertFalse(FileUtil.isAbsoluteFile("foo"));

		if (PlatformUtil.isWindows())
		{
			assertTrue(FileUtil.isAbsoluteFile(new File("c:\\")));
			assertTrue(FileUtil.isAbsoluteFile("c:\\"));
			assertTrue(FileUtil.isAbsoluteFile(new File("c:\\a")));
			assertTrue(FileUtil.isAbsoluteFile("c:\\a"));
			assertTrue(FileUtil.isAbsoluteFile(new File("c:/a")));
		}
	}

	/**
	 * 
	 */
	public void testGetExtension()
	{
		assertNull(FileUtil.getExtension(new File("foo")));
		assertEquals("txt", FileUtil.getExtension(new File("foo.txt")));
	}

	/**
	 * 
	 */
	public void testCleanDots()
	{
		assertEquals(FileUtil.cleanDots(new File(".")), new File("."));
		assertEquals(FileUtil.cleanDots(new File("..")), new File(".."));
		assertEquals(FileUtil.cleanDots(UrlUtil.urlToFile("a/..")), UrlUtil.urlToFile("."));
		assertEquals(FileUtil.cleanDots(UrlUtil.urlToFile(".././../c.pdf")), UrlUtil.urlToFile("../../c.pdf"));
		assertEquals(FileUtil.cleanDots(UrlUtil.urlToFile(".././a/../c.pdf")), UrlUtil.urlToFile("../c.pdf"));
	}

	/**
	 * 
	 */
	public void testNewExtension()
	{
		assertNull(FileUtil.getExtension(new File("foo")));
		assertEquals(new File("foo.bar"), FileUtil.newExtension(new File("foo"), "bar"));
		assertEquals(new File("foo.bar"), FileUtil.newExtension(new File("foo"), ".bar"));
		assertEquals(new File("a/foo.bar"), FileUtil.newExtension(new File("a/foo"), ".bar"));
	}

	/**
	 * 
	 */
	public void testCopyBytes()
	{
		final byte[] b = new byte[66666];
		for (int i = 0; i < 66666; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final File f = new File(sm_dirTestDataTemp + "bufOut.dat");
		f.delete();
		FileUtil.copyBytes(b, f);
		assertEquals(f.length(), 66666);
		final byte b2[] = FileUtil.fileToByteArray(f);
		assertEquals(b.length, b2.length);
		for (int i = 0; i < 66666; i++)
		{
			assertEquals(b[i], b2[i]);
		}
		FileUtil.copyBytes(b, f);
		assertEquals(f.length(), 2 * 66666);
		final byte b3[] = FileUtil.fileToByteArray(f);
		for (int i = 0; i < 66666; i++)
		{
			assertEquals(b[i % 66666], b3[i]);
		}
	}

	/**
	 * @throws Exception x
	 */
	public void testFileToByteArray() throws Exception
	{
		final byte[] b = new byte[66666];
		for (int i = 0; i < 66666; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayInputStream is = new ByteArrayInputStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "stream.dat");
		if (f.exists())
		{
			f.delete();
		}

		FileUtil.streamToFile(is, sm_dirTestDataTemp + "stream.dat");
		assertTrue(f.exists());
		final byte b2[] = FileUtil.fileToByteArray(f);
		final byte b3[] = FileUtil.fileToByteArray(f);
		assertEquals(b.length, b2.length);
		for (int i = 0; i < 66666; i++)
		{
			assertEquals(b[i], b2[i]);
			assertEquals(b[i], b3[i]);
		}
		f.delete();
		final File f2 = new File(sm_dirTestDataTemp + "dummy_snafu.dat");
		assertNull(FileUtil.fileToByteArray(f2));
	}

	/**
	 * 
	 */
	public void testisCleanURLFile()
	{
		if (PlatformUtil.isWindows())
		{
			assertEquals(new File("C:"), FileUtil.cleanURL(new File("C:/")));
			assertEquals(new File("C:"), FileUtil.cleanURL(new File("C:\\")));
			assertEquals(new File("C:\\a"), FileUtil.cleanURL(new File("C:\\a")));
			assertEquals(new File("C:\\a"), FileUtil.cleanURL(new File("C:/a")));
		}
	}

	/**
	 * 
	 */
	public void testListTree()
	{
		final File root = new File(sm_dirTestData + File.separator + "dir1");
		Vector<File> list = FileUtil.listFilesInTree(root, "dir(.+)/");
		assertEquals(list.size(), 2);
		assertTrue(list.contains(FileUtil.getFileInDirectory(root, new File("dir2a"))));
		assertTrue(list.contains(FileUtil.getFileInDirectory(root, new File("dir2b"))));
		list = FileUtil.listFilesInTree(root, StringUtil.simpleRegExptoRegExp("dir*/*.txt"));
		assertEquals(list.size(), 4);
	}

	/**
	 * @throws Exception
	 */
	public void testListFilesWithExtension() throws Exception
	{
		final File f = new File(sm_dirTestDataTemp + "/foo");
		f.mkdir(); // make sure we have one
		assertTrue(FileUtil.deleteAll(f));
		assertTrue(f.mkdir());
		assertNull(FileUtil.listFilesWithExtension(null, null));

		for (char c = 'a'; c < 'g'; c++)
		{
			for (int i = 0; i < 3; i++)
			{
				final File f2 = new File(f.getAbsolutePath() + File.separator + i + "." + c);
				assertTrue(f2.createNewFile());
			}
		}
		assertEquals(FileUtil.listFilesWithExtension(f, "a").length, 3);
		assertEquals(FileUtil.listFilesWithExtension(f, "a,b,.c")[0].getName(), "0.a");
		assertEquals(FileUtil.listFilesWithExtension(f, null).length, 18);
		assertNull(FileUtil.listFilesWithExtension(f, "CC"));
		assertNull(FileUtil.listFilesWithExtension(f, ".CC,.dd"));
		new File(f.getAbsolutePath() + File.separator + "a").createNewFile();
		assertEquals(FileUtil.listFilesWithExtension(f, null).length, 19);
		assertEquals(FileUtil.listFilesWithExtension(f, ".").length, 1);
		new File(f.getAbsolutePath() + File.separator + "b.").createNewFile();
		assertEquals(FileUtil.listFilesWithExtension(f, ".").length, 2);

		if (PlatformUtil.isWindows())
		{
			assertEquals(FileUtil.listFilesWithExtension(f, "C")[0].getName(), "0.c");
			assertEquals(FileUtil.listFilesWithExtension(f, "a,b,.c")[8].getName(), "2.c");
		}

	}

	/**
	 * @throws Exception
	 */
	public void testListFilesWithExpression() throws Exception
	{
		final File f = new File(sm_dirTestDataTemp + "/foo");
		f.mkdir(); // make sure we have one
		assertTrue(FileUtil.deleteAll(f));
		assertTrue(f.mkdir());
		assertNull(FileUtil.listFilesWithExtension(null, null));

		for (char c = 'a'; c < 'g'; c++)
		{
			for (int i = 0; i < 3; i++)
			{
				final File f2 = new File(f.getAbsolutePath() + File.separator + i + "." + c);
				assertTrue(f2.createNewFile());
			}
		}
		assertEquals(FileUtil.listFilesWithExtension(f, "a").length, 3);
		assertEquals(FileUtil.listFilesWithExtension(f, "a,b,.c")[0].getName(), "0.a");
		assertEquals(FileUtil.listFilesWithExtension(f, null).length, 18);
		assertNull(FileUtil.listFilesWithExtension(f, "CC"));
		assertNull(FileUtil.listFilesWithExtension(f, ".CC,.dd"));
		new File(f.getAbsolutePath() + File.separator + "a").createNewFile();
		assertEquals(FileUtil.listFilesWithExtension(f, null).length, 19);
		assertEquals(FileUtil.listFilesWithExtension(f, ".").length, 1);
		new File(f.getAbsolutePath() + File.separator + "b.").createNewFile();
		assertEquals(FileUtil.listFilesWithExtension(f, ".").length, 2);

		if (PlatformUtil.isWindows())
		{
			assertEquals(FileUtil.listFilesWithExtension(f, "C")[0].getName(), "0.c");
			assertEquals(FileUtil.listFilesWithExtension(f, "a,b,.c")[8].getName(), "2.c");
		}

	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * @throws Exception
	 */
	public void testListDirectories() throws Exception
	{
		final File f = new File(sm_dirTestDataTemp + File.separator + "foo");
		f.mkdir(); // make sure we have one
		assertTrue(FileUtil.deleteAll(f));
		assertTrue(f.mkdir());
		assertNull(FileUtil.listDirectories(null));
		assertNull(FileUtil.listDirectories(f));
		final File f1 = new File(sm_dirTestDataTemp + File.separator + "foo" + File.separator + "bar1");
		assertTrue(f1.mkdir());
		final File f2 = new File(sm_dirTestDataTemp + File.separator + "foo" + File.separator + "bar2");
		assertTrue(f2.createNewFile());
		assertEquals(FileUtil.listDirectories(f).length, 1);
		assertEquals("skipping bar2 - not a directory", FileUtil.listDirectories(f)[0], f1);

	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public void testCreateFile()
	{
		final File f = new File(sm_dirTestDataTemp + "/aaa_aaa/b/c.txt");
		assertTrue(FileUtil.createNewFile(f));
		assertTrue(FileUtil.createNewFile(f));
		assertFalse(FileUtil.createNewFile(null));
		f.delete();
	}

	/**
	 * 
	 */
	public void testEquals()
	{
		assertTrue(FileUtil.equals(null, null));
		assertFalse(FileUtil.equals(null, new File("a")));
		assertFalse(FileUtil.equals(new File("a"), null));
		assertTrue(FileUtil.equals(new File("a"), new File("a")));
		if (PlatformUtil.isWindows())
		{
			assertTrue(FileUtil.equals(new File("a"), new File("A")));
		}
		else
		{
			assertFalse(FileUtil.equals(new File("a"), new File("A")));
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * @throws Exception
	 */
	public void testMoveFile() throws Exception
	{
		final byte[] b = new byte[55555];
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayInputStream is = new ByteArrayInputStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "streamMove.dat");
		if (f.exists())
		{
			f.delete();
		}
		FileUtil.streamToFile(is, f.getPath());
		final File f2 = new File(sm_dirTestDataTemp + "streamMove2.dat");

		assertTrue(FileUtil.moveFile(f, f2));
		assertFalse(f.exists());
		assertTrue(f2.length() > 50000);
		final String newdir = sm_dirTestDataTemp + File.separator + "newDir";
		final File fd = new File(newdir);
		FileUtil.deleteAll(fd);
		assertFalse(fd.exists());
		fd.mkdirs();
		final File f3 = new File(newdir + File.separator + "streamMove3.dat");
		assertTrue(FileUtil.moveFile(f2, f3));
		assertFalse(f2.exists());
		assertTrue(f3.length() > 50000);

	}

	// /////////////////////////////////////////////////////////////////////////

	/**
	 * @throws Exception
	 */
	public void testCopyFileToDir() throws Exception
	{
		final byte[] b = new byte[55555];
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayInputStream is = new ByteArrayInputStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "streamCopy.dat");
		if (f.exists())
		{
			f.delete();
		}
		FileUtil.streamToFile(is, f.getPath());
		final String newdir = sm_dirTestDataTemp + File.separator + "newDirCopy";
		final File fd = new File(newdir);
		FileUtil.deleteAll(fd);
		assertFalse(fd.exists());
		fd.mkdirs();
		final File nf = FileUtil.copyFileToDir(f, fd);
		assertNotNull(nf);
		assertEquals(nf.getParentFile(), fd);
		assertEquals(nf.getName(), f.getName());
		assertTrue(f.exists());
		assertTrue(nf.exists());
		assertNull("do not copy self", FileUtil.copyFileToDir(nf, fd));
		assertEquals(nf, FileUtil.copyFileToDir(f, fd));
	}

	/**
	 * @throws Exception
	 */
	public void testEnsureFileInDir() throws Exception
	{
		final byte[] b = new byte[55555];
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayInputStream is = new ByteArrayInputStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "streamCopy.dat");
		if (f.exists())
		{
			f.delete();
		}
		FileUtil.streamToFile(is, f.getPath());
		final String newdir = sm_dirTestDataTemp + File.separator + "newDirCopy";
		final File fd = new File(newdir);
		FileUtil.deleteAll(fd);
		assertFalse(fd.exists());
		fd.mkdirs();
		final File nf = FileUtil.ensureFileInDir(f, fd);
		assertNotNull(nf);
		File parentDir = nf.getParentFile();
		assertEquals(parentDir, fd);
		assertEquals(nf.getName(), f.getName());
		assertTrue(f.exists());
		assertTrue(nf.exists());
		assertEquals(nf, FileUtil.ensureFileInDir(f, fd));
		assertEquals(nf, FileUtil.ensureFileInDir(nf, fd));
	}

	/**
	 * @throws Exception
	 */
	public void testMoveFileToDir() throws Exception
	{
		final byte[] b = new byte[55555];
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayInputStream is = new ByteArrayInputStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "streamMove.dat");
		if (f.exists())
		{
			f.delete();
		}
		FileUtil.streamToFile(is, f.getPath());
		final String newdir = sm_dirTestDataTemp + File.separator + "newDir2";
		final File fd = new File(newdir);
		FileUtil.deleteAll(fd);
		assertFalse(fd.exists());
		fd.mkdirs();
		final File nf = FileUtil.moveFileToDir(f, fd);
		assertNotNull(nf);
		assertEquals(nf.getParentFile(), fd);
		assertEquals(nf.getName(), f.getName());
		assertFalse(f.exists());
		assertTrue(nf.exists());
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * @throws Exception
	 */
	public void testStreamToFile() throws Exception
	{
		final byte[] b = new byte[55555];
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) (i % 256);
		}
		final ByteArrayIOStream is = new ByteArrayIOStream(b);
		is.close();
		final File f = new File(sm_dirTestDataTemp + "stream.dat");
		if (f.exists())
		{
			f.delete();
		}

		FileUtil.streamToFile(is.getInputStream(), sm_dirTestDataTemp + "stream.dat");
		assertTrue(f.exists());
		assertEquals(f.length(), 55555, 2000);

		final FileInputStream fis = new FileInputStream(f);
		for (int i = 0; i < 55555; i++)
		{
			b[i] = (byte) fis.read();
			if (i % 287 == 0)
			{
				assertEquals((256 + b[i]) % 256, i % 256);
			}
		}

		final int j = fis.read();
		assertEquals("eof reached", j, -1);
		fis.close();
		FileUtil.streamToFile(is.getInputStream(), sm_dirTestDataTemp + "stream.dat");
		assertTrue(f.exists());
		assertEquals("deleted old stuff", f.length(), 55555, 2000);

		final FileInputStream fis2 = new FileInputStream(f);
		final File f2 = FileUtil.streamToFile(fis2, sm_dirTestDataTemp + "stream2.dat");
		assertTrue(f2.canRead());
		assertTrue(f.delete());
		assertTrue(f2.delete());
		assertFalse(f.delete());
		assertNull("null safe", FileUtil.streamToFile(null, sm_dirTestDataTemp + "stream2.dat"));
	}

	// ////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public void testGetFileInDirectory()
	{
		assertEquals(new File("a/b"), FileUtil.getFileInDirectory(new File("a"), new File("b")));
		assertEquals(new File("a/b"), FileUtil.getFileInDirectory(new File("a/"), new File("b")));
		assertEquals(new File("a/b"), FileUtil.getFileInDirectory(new File("a/"), new File("/b")));
		assertEquals(new File("a/c/b"), FileUtil.getFileInDirectory(new File("a"), new File("c/b")));
		assertEquals(new File("a/aa/c/b"), FileUtil.getFileInDirectory(new File("a/aa"), new File("c/b")));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("c")), new File("a/b/c"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("c/d")), new File("a/b/c/d"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("/c")), new File("a/b/c"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("/c/d")), new File("a/b/c/d"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("../c/d")), new File("a/c/d"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b"), new File("/c/d/")), new File("a/b/c/d"));
		assertEquals(FileUtil.getFileInDirectory(new File("a/b/"), new File("/c/d/")), new File("a/b/c/d"));

		if (PlatformUtil.isWindows())
		{
			assertEquals(new File("a/b"), FileUtil.getFileInDirectory(new File("a\\"), new File("b")));
			assertEquals(new File("a/b"), FileUtil.getFileInDirectory(new File("a\\"), new File("\\b")));
		}
	}

}
