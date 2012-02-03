/**
 * The CIP4 Software License, Version 1.0
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
package org.cip4.jdflib.util.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.util.ByteArrayIOStream;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.StringUtil;

/**
 * class to read a zip file or stream
 * @author rainer prosi
 * @date Feb 1, 2012
 */
public class ZipReader
{
	final InputStream is;
	ByteArrayIOStream bos;
	ZipInputStream zis;
	final Log log;
	ZipEntry currentEntry;
	boolean caseSensitive;

	/**
	 * 
	 *return true if all checks are cese sensitive (the default)
	 * @return
	 */
	public boolean isCaseSensitive()
	{
		return caseSensitive;
	}

	/**
	 * 
	 * set the case sensitivity for matching strings and regexp
	 * @param caseSensitive
	 */
	public void setCaseSensitive(boolean caseSensitive)
	{
		this.caseSensitive = caseSensitive;
	}

	/**
	 * 
	 * @param inStream
	 */
	public ZipReader(InputStream inStream)
	{
		log = LogFactory.getLog(getClass());
		is = inStream;
		zis = new ZipInputStream(is);
		bos = null;
		caseSensitive = true;
	}

	/**
	 * 
	 * @param file
	 */
	public ZipReader(File file)
	{
		this(FileUtil.getBufferedInputStream(file));
	}

	/**
	 * 
	 * @param fileName
	 */
	public ZipReader(String fileName)
	{
		this(new File(fileName));
	}

	/**
	 * 
	 * get the vector of zip entries
	 * @return
	 */
	public Vector<ZipEntry> getEntries()
	{
		Vector<ZipEntry> vze = new Vector<ZipEntry>();
		ZipEntry ze = getNextEntry();
		while (ze != null)
		{
			vze.add(ze);
			ze = getNextEntry();
		}
		return vze;
	}

	/**
	 * 
	 *returns null in case of snafu
	 * @return
	 */
	public ZipEntry getNextEntry()
	{
		try
		{
			ZipEntry nextEntry = zis.getNextEntry();
			currentEntry = nextEntry;
			return nextEntry;
		}
		catch (IOException e)
		{
			currentEntry = null;
			return null;
		}
	}

	/**
	 * unpack into a directory
	 *  
	 * @param dir the destination directory
	 * @return
	 */
	public int unPack(File dir)
	{
		if (dir == null)
			return 0;
		dir.mkdirs();
		if (!dir.isDirectory())
			return 0;
		int n = 0;
		ZipEntry ze = getNextEntry();
		while (ze != null)
		{
			boolean b = unPack(dir, ze);
			if (b)
				n++;
			ze = getNextEntry();
		}
		return n;
	}

	/**
	 * unpack an individual entry into a directory  
	 * @param dir
	 * @param ze
	 * @return
	 */
	public boolean unPack(File dir, ZipEntry ze)
	{
		if (dir == null)
			return false;
		if (currentEntry != ze)
		{
			log.warn("snafu with entries");
			return false;
		}
		String fileName = ze.getName();
		File file = new File(fileName);
		File absoluteFile = FileUtil.getFileInDirectory(dir, file);
		try
		{
			if (ze.isDirectory())
			{
				absoluteFile.mkdirs();
			}
			else
			{
				File parent = absoluteFile.getParentFile();
				if (parent != null)
					parent.mkdirs();
				final OutputStream fos = new BufferedOutputStream(new FileOutputStream(absoluteFile));
				IOUtils.copy(zis, fos);
				fos.flush();
				fos.close();
			}
			zis.closeEntry();
		}
		catch (IOException e)
		{
			log.error("Snafu unpacking zip to: " + fileName, e);
		}
		return true;
	}

	/**
	 * get an entry by name - note that we need to buffer the entire file for this random access method
	 * 
	 * @param urlString the file path (case sensitive)
	 * @return
	 */
	public ZipEntry getEntry(String urlString)
	{
		buffer();
		if (urlString == null)
		{
			log.error("cannot retrieve null entry");
			return null;
		}
		ZipEntry ze = getNextEntry();
		while (ze != null)
		{
			String name = ze.getName();
			boolean matches = caseSensitive ? urlString.equals(name) : urlString.equalsIgnoreCase(name);
			if (matches)
				return ze;

			ze = getNextEntry();
		}
		return null;
	}

	/**
	 * get an entry by name - note that we need to buffer the entire file for this random access method
	 * 
	 * @param expr the regexp to match - simplified regexp is accepted
	 * @param iSkip how many to skip - default= 0
	 * @return
	 */
	public ZipEntry getMatchingEntry(String expr, int iSkip)
	{
		buffer();
		ZipEntry ze = getNextEntry();
		expr = StringUtil.simpleRegExptoRegExp(expr);
		int n = 0;
		while (ze != null)
		{
			String name = ze.getName();
			boolean matches = caseSensitive ? StringUtil.matches(name, expr) : StringUtil.matchesIgnoreCase(name, expr);
			if (matches)
			{
				if (n >= iSkip)
					return ze;
				else
					n++;
			}

			ze = getNextEntry();
		}
		return null;
	}

	/**
	 * 
	 * get the stream to read this from; note that we must close manually
	 * @param urlString
	 * @return
	 */
	public InputStream getInputStream()
	{
		return currentEntry == null ? null : zis;
	}

	/**
	 * 
	 * get the xmlDoc of the current entry - note not threadsafe!
	 *  
	 * @return
	 */
	public XMLDoc getXMLDoc()
	{
		if (currentEntry == null)
			return null;
		XMLDoc doc = XMLDoc.parseStream(zis);
		if (doc != null)
			doc.setZipReader(this);
		return doc;
	}

	/**
	 * 
	 * get the xmlDoc of the current entry - note not threadsafe!
	 *  
	 * @return
	 */
	public JDFDoc getJDFDoc()
	{
		if (currentEntry == null)
			return null;
		JDFDoc doc = JDFDoc.parseStream(zis);
		if (doc != null)
			doc.setZipReader(this);
		return doc;
	}

	/**
	 *  
	 */
	public void buffer()
	{
		if (bos == null)
		{
			bos = new ByteArrayIOStream(is);
		}
		reset();
	}

	/**
	 *  
	 */
	private void reset()
	{
		if (bos != null)
		{
			zis = new ZipInputStream(bos.getInputStream());
		}
	}
}
