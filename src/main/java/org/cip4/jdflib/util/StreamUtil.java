/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2016 The International Cooperation for the Integration of 
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
 * 
 */
package org.cip4.jdflib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.util.ByteArrayIOStream.ByteArrayIOInputStream;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * August 10, 2009
 */
public class StreamUtil
{
	/**
	 * return a reasonably well performing stream for is
	 * @param is
	 * @return the buffered stream
	 */
	public static InputStream getBufferedInputStream(final InputStream is)
	{
		if (is == null)
		{
			return null;
		}
		if (is instanceof ByteArrayInputStream)
		{
			return is;
		}
		if (is instanceof BufferedInputStream)
		{
			return is;
		}
		return new BufferedInputStream(is);
	}

	/**
	 * return a reasonably well performing stream for os
	 * @param os
	 * @return the buffered stream
	 */
	public static OutputStream getBufferedOutputStream(final OutputStream os)
	{
		if (os == null)
		{
			return null;
		}
		if (os instanceof ByteArrayOutputStream)
		{
			return os;
		}
		if (os instanceof BufferedOutputStream)
		{
			return os;
		}
		return new BufferedOutputStream(os);
	}

	/**
	 * read and tokenize a stream by cr/lf
	 * @param in the input stream to read
	 * @return a vector of strings, one for each line
	 */
	public static VString getLines(InputStream in)
	{
		ByteArrayIOInputStream ios = ByteArrayIOStream.getBufferedInputStream(in);
		if (ios == null)
		{
			return null;
		}
		else
		{
			byte[] bytes = ios.getBuf();
			final String s = bytes == null ? null : new String(bytes, 0, bytes.length);
			return StringUtil.tokenize(s, "\n\r", false);
		}
	}

	/**
	 * calculate the md5 hash
	 * @param is
	 * @return
	 */
	public static byte[] getMD5(InputStream is)
	{
		if (is == null)
			return null;
		MessageDigest md5;
		try
		{
			md5 = MessageDigest.getInstance(JDFConstants.MD5);
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
		synchronized (md5)
		{
			byte[] b = new byte[100000];
			int n;
			try
			{
				n = is.read(b);
				while (n > 0)
				{
					md5.update(b, 0, n);
					n = is.read(b);
				}
				return md5.digest();
			}
			catch (IOException e)
			{
				return null;
			}
		}

	}

	/**
	 * 
	 * exception catching null safe close
	 * @param ios
	 */
	static public void close(OutputStream ios)
	{
		if (ios != null)
		{
			try
			{
				ios.close();
			}
			catch (IOException e)
			{
				// NOP
			}
		}
	}

	/**
	 * 
	 * @param stream
	 */
	static public void reset(InputStream stream)
	{
		if (stream == null)
			return;
		try
		{
			stream.reset();
		}
		catch (IOException e)
		{
			//nop
		}
	}

	/**
	 * 
	 * exception catching null safe close
	 * @param ios
	 */
	static public void close(InputStream ios)
	{
		if (ios != null)
		{
			try
			{
				ios.close();
			}
			catch (IOException e)
			{
				// NOP
			}
		}
	}
}
