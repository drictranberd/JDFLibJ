/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2023 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
/**
 * ========================================================================== class JDFFileSpec ==========================================================================
 *
 * @COPYRIGHT Heidelberger Druckmaschinen AG, 1999-2001 ALL RIGHTS RESERVED Author: sabjon@topmail.de using a code generator Warning! very preliminary test version. Interface subject to change without
 *            prior notice!
 */

package org.cip4.jdflib.resource.process;

import java.io.File;
import java.io.InputStream;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoFileSpec;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.ifaces.IURLSetter;
import org.cip4.jdflib.util.MimeUtil;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.jdflib.util.UrlUtil;
import org.cip4.jdflib.util.mime.MimeReader;
import org.w3c.dom.DOMException;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 *
 *         July 24, 2009
 */
public class JDFFileSpec extends JDFAutoFileSpec implements IURLSetter
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for JDFFileSpec
	 *
	 * @param myOwnerDocument
	 * @param qualifiedName
	 *
	 * @throws DOMException
	 */
	public JDFFileSpec(final CoreDocumentImpl myOwnerDocument, final String qualifiedName) throws DOMException
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFFileSpec
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @throws DOMException
	 *
	 */
	public JDFFileSpec(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName) throws DOMException
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFFileSpec
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 * @throws DOMException
	 *
	 */
	public JDFFileSpec(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName, final String myLocalName) throws DOMException
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * @see org.cip4.jdflib.auto.JDFAutoFileSpec#toString()
	 */
	@Override
	public String toString()
	{
		return "JDFFileSpec[  --> " + super.toString() + " ]";
	}

	/**
	 * sets the URL attribute to an absolute file IRL (internationalized)
	 *
	 * @param f the file to set the URL to
	 * @param bEscape128 if true, escape chars>128 (URL) else don't escape (IRL)
	 */
	public void setAbsoluteFileURL(final File f, final boolean bEscape128)
	{
		final String s = UrlUtil.fileToUrl(f, bEscape128);
		setMimeURL(s);
	}

	/**
	 * sets the URL attribute to an absolute file IRL (internationalized)
	 *
	 * @param f the file to set the URL to
	 * @param baseDir the File representing the relative location. if null use current working dir
	 * @param bEscape128 if true, escape chars>128 (URL) else don't escape (IRL)
	 */
	public void setRelativeURL(final File f, final File baseDir, final boolean bEscape128)
	{
		final String s = UrlUtil.getRelativeURL(f, baseDir, bEscape128);
		setMimeURL(s);
	}

	/**
	 *
	 * list of predefined resourceUsages
	 *
	 */
	public enum EnumResourceUsage
	{
		Accepted, AbstractProfile, AddressList, ActualOutputProfile, CIP3, ColorProfile, Combined, CorrectionProfile, CurrentSchema, DataList, DeliveryContents, DeviceLinkProfile, ExternalImpositionTemplate, FinalTargetDevice, Font, Image, MailingList, OutputProfile, PDL, PDLSourceProfile, Preview, RasterFileLocation, ReferenceOutputProfile, Rejected, RemoteURL, ScanProfile, Schema, SearchPath, Setup, SourceProfile, TargetProfile, Unknown, Verification, WorkingColorSpace;

		public static EnumResourceUsage getEnum(final String val)
		{
			for (final EnumResourceUsage e : values())
			{
				if (e.name().equalsIgnoreCase(val))
				{
					return e;
				}
			}
			return null;
		}

	}

	/**
	 * get the input stream that reads from URL
	 *
	 * @return InputStream the input stream that the url points to, null if the url is inaccessible
	 */
	public InputStream getURLInputStream()
	{
		return getURLInputStream(getURL());
	}

	/**
	 * sets URL and MimeType by matching the extensions
	 *
	 * @param url the url to set URL
	 */
	public void setMimeURL(final String url)
	{
		setURL(url);
		final String mimeTypeFromURL = UrlUtil.getMimeTypeFromURL(url);
		if (!MimeUtil.TEXT_UNKNOWN.equals(mimeTypeFromURL))
		{
			setMimeType(mimeTypeFromURL);
		}
	}

	/**
	 * physically store the file at the location specified in dir and also modify this to reflect the new location
	 *
	 * @param dir
	 * @return the file that corresponds to the moved url reference, null if an error occurred
	 * @deprecated use URLUTil.moveToDir(this)
	 */
	@Deprecated
	public File moveToDir(final File dir)
	{
		return UrlUtil.moveToDir(this, dir, true);
	}

	/**
	 * returns the filename of the referenced object, even if it is a cid or http url
	 *
	 * @return the file name of the referenced object
	 */
	public String getFileName()
	{
		String filename = getUserFileName();
		if (!StringUtil.isEmpty(filename))
			return filename;

		final String url = getURL();
		if (StringUtil.isEmpty(url))
		{
			return null;
		}
		return new MimeReader(getOwnerDocument_KElement().getMultiPart()).getFileName(url);
	}

	/**
	 * generates the correct MIMEType for a given URL and sets it
	 *
	 * @param url
	 * @return
	 * @deprecated use @see UrlUtil.getMimeTypeFromURL(url)
	 */
	@Deprecated
	public static String getMimeTypeFromURL(final String url)
	{
		return UrlUtil.getMimeTypeFromURL(url);
	}

	/**
	 * create a container and prefill a filespec
	 *
	 * @param containerUrl
	 * @param mimeType
	 * @return the Container/FileSpec
	 */
	public JDFFileSpec setContainer(final String containerUrl, final String mimeType)
	{
		final JDFContainer c = getCreateContainer();
		final JDFFileSpec fs = c.getCreateFileSpec();
		fs.setURL(containerUrl);
		fs.setMimeType(mimeType);
		return fs;
	}

	/**
	 * (36) set attribute FileSize
	 *
	 * @param value the value to set the attribute to
	 */
	public void setFileSize(final long value)
	{
		setAttribute(AttributeName.FILESIZE, value, null);
	}

	/**
	 *
	 * @return the value of FileSize
	 */
	public long getFileSizeLong()
	{
		return getLongAttribute(AttributeName.FILESIZE, null, -1);
	}

	/**
	 * @see org.cip4.jdflib.auto.JDFAutoFileSpec#setCheckSum(java.lang.String)
	 */
	public void setCheckSum(final byte[] value)
	{
		super.setCheckSum(StringUtil.setHexBinaryBytes(value, -1));
	}

	/**
	 * @see org.cip4.jdflib.auto.JDFAutoFileSpec#setCheckSum(java.lang.String)
	 */
	public byte[] getCheckSumBytes()
	{
		return StringUtil.getHexBinaryBytes(getCheckSum().getBytes());
	}

	/**
	 *
	 * @param usage
	 */
	public void setResourceUsage(final EnumResourceUsage usage)
	{
		super.setResourceUsage(usage == null ? null : usage.name());
	}

	/**
	 *
	 * @return
	 */
	public EnumResourceUsage getResourceUsageEnum()
	{
		return EnumResourceUsage.getEnum(getResourceUsage());
	}

}
