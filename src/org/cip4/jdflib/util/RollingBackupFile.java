/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2010 The International Cooperation for the Integration of
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
 * 
 */
package org.cip4.jdflib.util;

import java.io.File;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * class to generate rolling backup files using a simple <FileName>.n naming algorithm.
 * 
 * The oldest file dies when the maximum number is reached
 * 
 * 08.12.2008
 */
public class RollingBackupFile extends File
{
	private final int nBackup;

	/**
	 * @param pathname the base filename
	 * @param nBackupp the number of backups to retain
	 */
	public RollingBackupFile(final String pathname, final int nBackupp)
	{
		super(pathname);
		this.nBackup = nBackupp;
	}

	/**
	 * @param file the base file
	 * @param nBackupp the number of backups to retain
	 */
	public RollingBackupFile(final File file, final int nBackupp)
	{
		super(file.getPath());
		this.nBackup = nBackupp;
	}

	/**
	 * @return the file to write, i.e. this
	 */
	public File getNewFile()
	{
		init();
		return this;
	}

	/**
	 * remove all files, including main file
	 */
	public void clearAll()
	{
		final String pathname = getPath();
		final String extension = FileUtil.getExtension(this);
		for (int i = nBackup; i >= 0; i--)
		{
			final String sBak = getPathFor(pathname, extension, i);
			final File lastFile = new File(sBak);
			if (lastFile.exists())
			{
				lastFile.delete();
			}
			delete();
		}
	}

	/**
	 * the big simple rolling method
	 */
	private synchronized void init()
	{
		final String pathname = getPath();
		final String extension = FileUtil.getExtension(this);
		for (int i = nBackup; i > 0; i--)
		{
			final String sBak = getPathFor(pathname, extension, i);
			final String sNewBak = (i == 1) ? pathname : getPathFor(pathname, extension, i - 1);
			final File lastFile = new File(sBak);
			if (lastFile.exists())
			{
				lastFile.delete();
			}
			new File(sNewBak).renameTo(lastFile);
		}
	}

	/**
	 * @param pathname
	 * @param extension
	 * @param i
	 * @return
	 */
	private String getPathFor(final String pathname, final String extension, int i)
	{
		final String newExtension = i + ((extension == null) ? "" : "." + extension);
		final String sBak = StringUtil.newExtension(pathname, newExtension);
		return sBak;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1521423479897L;

}
