/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.jdflib.util.mime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFParser;
import org.cip4.jdflib.core.JDFParserFactory;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.util.ByteArrayIOStream;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.MimeUtil;
import org.cip4.jdflib.util.MimeUtil.ByteArrayDataSource;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.jdflib.util.UrlUtil;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 *
 *         Jul 24, 2009
 */
public class BodyPartHelper
{
	protected BodyPart theBodyPart;
	private static Log log = LogFactory.getLog(BodyPartHelper.class);

	/**
	 * @param bp
	 *
	 */
	public BodyPartHelper(final BodyPart bp)
	{
		super();
		theBodyPart = bp;
	}

	/**
	 * creates a new bodypart
	 */
	public BodyPartHelper()
	{
		super();
		createBodyPart();
	}

	/**
	 * @return
	 */
	public BodyPart getBodyPart()
	{
		return theBodyPart;
	}

	/**
	 *
	 */
	public void createBodyPart()
	{
		theBodyPart = new MimeBodyPart();
	}

	/**
	 * @param cid
	 *
	 */
	public void setContentID(final String cid)
	{
		if (cid == null)
		{
			return;
		}

		try
		{
			theBodyPart.setHeader(UrlUtil.CONTENT_ID, "<" + MimeUtil.urlToCid(cid).substring(4) + ">");
		}
		catch (final MessagingException x)
		{
			// nop
		}
	}

	/**
	 * set the filename header of a bodypart to a string
	 *
	 * @param path the path to set
	 */
	public void setFileName(final String path)
	{
		if (path == null)
		{
			return;
		}
		try
		{
			theBodyPart.setFileName(new File(path).getName());
		}
		catch (final MessagingException x)
		{
			// nop
		}
	}

	/**
	 * get the filename header of a bodypart a string if no file name is set, a unique filename is generated from cid and content type
	 *
	 * @return the file name, null if bp is null
	 */
	public String getFileName()
	{
		if (theBodyPart == null)
		{
			return null; // duh
		}
		String s = null;
		try
		{
			s = StringUtil.getNonEmpty(theBodyPart.getFileName());
			if (s != null)
			{
				return s;
			}
		}
		catch (final MessagingException x)
		{
			// nop
		}
		s = getContentID();
		if (s == null)
		{
			final int index = getIndex();
			s = StringUtil.sprintf("part_%04i.txt", "" + index);
		}

		return s;
	}

	/**
	 * check if a BodyPart matches a given cid
	 *
	 * @param cid the cid string any '<' '>' or 'cid:' prefixes are removed if null, anything matches
	 * @return true if this bp matches the cid
	 */
	public boolean matchesCID(String cid)
	{
		if (cid == null)
		{
			return true; // wildcard
		}

		if (cid.startsWith("<"))
		{
			cid = cid.substring(1);
		}

		if (cid.toLowerCase().startsWith("cid:"))
		{
			cid = cid.substring(4);
		}

		if (cid.endsWith(">"))
		{
			cid = cid.substring(0, cid.length() - 1);
		}

		final String s = getContentID();
		if (s == null)
		{
			return false;
		}

		return cid.equalsIgnoreCase(s);
	}

	/**
	 * get the ContentID header of a bodypart a string
	 *
	 * @return the cid, null if there was an error
	 */
	public String getContentID()
	{
		if (theBodyPart == null)
		{
			return null;
		}
		String[] cids = null;
		try
		{
			cids = theBodyPart.getHeader(UrlUtil.CONTENT_ID);
		}
		catch (final MessagingException e)
		{
			return null;
		}
		final String s = StringUtil.setvString(cids, null, null, null);
		if (s == null)
		{
			return s;
		}

		return MimeUtil.urlToCid(s).substring(4);
	}

	/**
	 * sets the content of a bodypart to the xmlDoc - correctly handling non-ascii features and setting the correct content type
	 *
	 * @param xmlDoc the xmlDoc to fill in
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void setContent(final XMLDoc xmlDoc) throws MessagingException, IOException
	{
		if (theBodyPart == null || xmlDoc == null)
		{
			throw new MessagingException("null parameters in setContent");
		}

		// TODO better performing solution for multibyte this quick hack makes
		// quite a few copies...
		final ByteArrayIOStream ios = new ByteArrayIOStream();
		xmlDoc.write2Stream(ios, 0, true);
		final ByteArrayDataSource ds = new ByteArrayDataSource(ios, "text/xml");
		theBodyPart.setDataHandler(new DataHandler(ds));
		xmlDoc.setBodyPart(theBodyPart);
		final KElement root = xmlDoc.getRoot();
		if (root instanceof JDFJMF)
		{
			theBodyPart.setHeader(UrlUtil.CONTENT_TYPE, JDFConstants.MIME_JMF); // JMF
		}
		else if (root instanceof JDFNode)
		{
			theBodyPart.setHeader(UrlUtil.CONTENT_TYPE, JDFConstants.MIME_JDF); // JDF
		}

	}

	/**
	 * @param urlString
	 * @return
	 */
	public BodyPart createFromURL(final String urlString)
	{
		try
		{
			DataSource dataSrc = null;
			final File f = UrlUtil.urlToFile(urlString);
			if (f != null && f.canRead())
			{
				dataSrc = new FileDataSource(f);
			}

			if (dataSrc == null)
			{
				return null; // no data source
			}

			theBodyPart.setDataHandler(new DataHandler(dataSrc));

			setFileName(f.getAbsolutePath());
			setContentID(urlString);
		}
		catch (final MessagingException e1)
		{
			log.warn("cannot open file: " + urlString, e1);
			return null;
		}
		return theBodyPart;
	}

	/**
	 * get the JDF Doc from a given body part
	 *
	 * @return JDFDoc the parsed xml JDFDoc, null if bp does not contain xml
	 */
	public JDFDoc getJDFDoc()
	{
		if (theBodyPart == null)
		{
			return null;
		}

		try
		{
			final String mimeType = theBodyPart.getContentType();
			if (!MimeUtil.isJDFMimeType(mimeType))
			{
				return null;
			}
			final InputStream is = theBodyPart.getInputStream();
			final JDFParser p = JDFParserFactory.getFactory().get();
			final JDFDoc doc = p.parseStream(is);
			if (doc != null)
			{
				doc.setBodyPart(theBodyPart);
			}
			JDFParserFactory.getFactory().push(p);
			return doc;
		}
		catch (final IOException e)
		{
			return null; // snafu
		}
		catch (final MessagingException e)
		{
			return null; // snafu
		}
	}

	/**
	 * get the JDF Doc from a given body part
	 *
	 * @return JDFDoc the parsed xml JDFDoc, null if bp does not contain xml
	 */
	public XMLDoc getXMLDoc()
	{
		if (theBodyPart == null)
		{
			return null;
		}

		try
		{
			final InputStream is = theBodyPart.getInputStream();
			final XMLDoc doc = XMLDoc.parseStream(is);
			if (doc != null)
			{
				doc.setBodyPart(theBodyPart);
			}
			return doc;
		}
		catch (final IOException e)
		{
			return null; // snafu
		}
		catch (final MessagingException e)
		{
			return null; // snafu
		}
	}

	/**
	 * @param directory
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void writeToDir(final File directory) throws IOException, MessagingException
	{
		boolean exists = directory.exists();
		if (!exists)
		{
			exists = directory.mkdirs();
		}

		if (!exists)
		{
			throw new FileNotFoundException();
		}

		final String fileName = getFileName();
		final File outFile = new File(directory.getPath() + File.separator + fileName);
		FileUtil.streamToFile(getInputStream(), outFile);
	}

	/**
	 * @return
	 */
	public int getIndex()
	{
		final Multipart mp = theBodyPart.getParent();
		if (mp == null)
			return 0;
		final MimeHelper mh = new MimeHelper(mp);
		final BodyPart[] bps = mh.getBodyParts();
		for (int i = 0; i < bps.length; i++)
		{
			if (bps[i] == theBodyPart)
				return i;
		}
		return 0;
	}

	/**
	 * @return
	 */
	public InputStream getInputStream()
	{
		try
		{
			return theBodyPart == null ? null : theBodyPart.getInputStream();
		}
		catch (final IOException e)
		{
			// nop
		}
		catch (final MessagingException e)
		{
			// nop
		}
		return null;
	}

}
