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
/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * KString.java
 *
 * Last changes
 *
 */
package org.cip4.jdflib.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.cip4.jdflib.core.VString;

/**
 * collection of helper routines to work with files
 * 
 * @author prosirai
 * 
 */
public class FileUtil
{

	/**
	 * list all files with a given extension (directories are skipped
	 * 
	 * @param dir the directory to search
	 * @param extension comma separated list of extensions to check for (null = list all)
	 * @return Files[] the matching files, null if none are found
	 */
	public static File[] listFilesWithExtension(File dir, String extension)
	{
		if (dir == null)
			return null;
		File[] files = dir.listFiles(new ExtensionFileFilter(extension));
		return (files == null || files.length == 0) ? null : files;
	}

	/**
	 * list all files matching given regexp 
	 * 
	 * @param dir the directory to search
	 * @param expression regular expression
	 * @return Files[] the matching files, null if none are found
	 */
	public static File[] listFilesWithExpression(File dir, String expression)
	{
		if (dir == null)
			return null;
		File[] files = dir.listFiles(new ExpressionFileFilter(expression));
		return (files == null || files.length == 0) ? null : files;
	}

	/**
	 * list all files matching given regexp 
	 * 
	 * @param dir the directory to search
	 * @param expression comma separated list of regular expression of a tree with slashes separating directories
	 * @return Files[] the matching files, null if none are found
	 */
	public static Vector<File> listFilesInTree(File dir, String expression)
	{
		if (dir == null || expression == null)
			return null;

		int posSlash = expression.indexOf('/');
		if (posSlash < 0)
		{
			File[] f = listFilesWithExpression(dir, expression);
			return ContainerUtil.toVector(f);
		}
		else
		{
			String nextDir = expression.substring(0, posSlash);
			File[] f = listFilesWithExpression(dir, nextDir);
			if (f == null)
				return null;
			Vector<File> v = new Vector<File>();
			for (int i = 0; i < f.length; i++)
			{
				if (f[i].isDirectory())
					v.add(f[i]);
			}
			if (v.size() == 0)
				v = null;
			if (posSlash + 1 == expression.length()) // last token ends with /	
			{
				return v;
			}
			else
			{
				if (v == null)
					return v;
				Vector<File> ret = new Vector<File>();
				String next = expression.substring(posSlash + 1);
				for (int i = 0; i < v.size(); i++)
				{
					Vector<File> v2 = listFilesInTree(v.get(i), next);
					if (v2 != null)
						ret.addAll(v2);
				}
				return ret.size() == 0 ? null : ret;
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////
	/**
	 * list all direct child directories
	 * 
	 * @param dir the directory to search
	 * 
	 * @return Files[] the matching directories, null if none are found
	 */
	public static File[] listDirectories(File dir)
	{
		if (dir == null)
			return null;
		File[] files = dir.listFiles(new DirectoryFileFilter());
		return (files == null || files.length == 0) ? null : files;
	}

	// //////////////////////////////////////////////////////////////////////////

	/************************
	 * Inner class ***********************
	 * 
	 * UtilFileFilter
	 * 
	 ************************************************************/
	public static class ExtensionFileFilter implements FileFilter
	{
		private Set<String> m_extension;

		/**
		 * @param fileExtension comma separated list of valid regular expressions
		 * 
		 */
		protected ExtensionFileFilter(String fileExtension)
		{
			if (fileExtension != null)
			{
				VString list = StringUtil.tokenize(fileExtension, ",", false);
				m_extension = new HashSet<String>();
				for (int i = 0; i < list.size(); i++)
				{
					String st = list.stringAt(i);
					if (st.startsWith("."))
						st = st.substring(1);
					st = st.toLowerCase();
					m_extension.add(st);
				}
			}
		}

		/**
		 * @param fileExtensionVector Vector of valid regular expressions
		 * 
		 */
		protected ExtensionFileFilter(VString fileExtensionVector)
		{
			if (fileExtensionVector != null)
			{
				m_extension = new HashSet<String>();
				for (int i = 0; i < fileExtensionVector.size(); i++)
				{
					String st = fileExtensionVector.stringAt(i);
					if (st.startsWith("."))
						st = st.substring(1);
					st = st.toLowerCase();
					m_extension.add(st);
				}
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File checkFile)
		{
			if ((checkFile == null) || !checkFile.isFile())
				return false;
			if (m_extension == null)
				return true;
			String xt = UrlUtil.extension(checkFile.getPath());
			if (xt == null)
				xt = "";
			else
				xt = xt.toLowerCase();

			return m_extension.contains(xt);
		}
	}

	/**
	 * simple file filter that lists all directories
	 * 
	 * @author prosirai
	 * 
	 */
	protected static class DirectoryFileFilter implements FileFilter
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File checkFile)
		{
			return checkFile != null && checkFile.isDirectory();
		}
	}

	/**
	 * simple file filter that lists all files that match a regular expression
	 * 
	 * @author Rainer Prosi
	 * 
	 */
	protected static class ExpressionFileFilter implements FileFilter
	{
		private final String regExp;

		/**
		 * @param _regExp the simplified regular expression to match
		 * 
		 */
		public ExpressionFileFilter(String _regExp)
		{
			regExp = StringUtil.simpleRegExptoRegExp(_regExp);
		}

		/**
		 * true if a file matches a regular expression
		 * 
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File checkFile)
		{
			return checkFile != null && StringUtil.matches(checkFile.getName(), regExp);
		}
	}

	/**
	 * very brutal tree zapper that will delete a file or directory tree recursively
	 * 
	 * @param dirToZapp the file directory to utterly anihilate
	 * @return true if ciao
	 */
	public static boolean deleteAll(File dirToZapp)
	{
		if (dirToZapp == null)
			return false;

		boolean b = true;
		if (dirToZapp.isDirectory())
		{
			File[] ff = dirToZapp.listFiles();
			if (ff != null)
			{
				int siz = ff.length;
				for (int i = 0; i < siz; i++)
					b = deleteAll(ff[i]) && b;
			}
		}

		return dirToZapp.delete() && b;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////
	/**
	 * dump a stream to a newly created file
	 * 
	 * @param fis the inputstream to read
	 * @param fileName the file to stream to
	 * @return the file created by the stream, null if snafu
	 */
	public static File streamToFile(InputStream fis, String fileName)
	{
		if (fis == null)
			return null;
		File tmp = UrlUtil.urlToFile(fileName);
		if (tmp == null)
		{
			return null;
		}
		try
		{
			FileOutputStream fos = new FileOutputStream(tmp);
			IOUtils.copy(fis, fos);
			fos.flush();
			fos.close();
			fis.close();
		}
		catch (FileNotFoundException x)
		{
			return null;
		}
		catch (IOException x)
		{
			return null;
		}

		return tmp;
	}

	/**
	 * moves a File to directory by trying to rename, if this fails, a copy with subsequent delete is performed. if toFile
	 * exists, it is brutally overwritten
	 * 
	 * @param fromFile the File to move
	 * @param toDir the Directory to move to
	 * @return File the moved File if success, else null, i.e. toFile exists with the contents of fromFile
	 */
	public static File moveFileToDir(File fromFile, File toDir)
	{
		if (fromFile == null || toDir == null)
			return null;
		final File newFile = getFileInDirectory(toDir, new File(fromFile.getName()));
		boolean b = moveFile(fromFile, newFile);
		return b ? newFile : null;
	}

	/**
	 * moves a File by trying to rename, if this fails, a copy with subsequent delete is performed. if toFile exists, it
	 * is brutally overwritten
	 * 
	 * @param fromFile the File to move
	 * @param toFile the File to create
	 * @return boolean true if success, i.e. toFile exists with the contents of fromFile
	 */
	public static boolean moveFile(File fromFile, File toFile)
	{
		if (fromFile.equals(toFile))
			return true;
		if (fromFile.renameTo(toFile))
			return true;
		if (!copyFile(fromFile, toFile))
			return false;
		return fromFile.delete();

	}

	/**
	 * copy a file
	 * 
	 * @param fromFile the source File
	 * @param toFile the destination File
	 * @return true if success
	 */
	public static boolean copyFile(File fromFile, File toFile)
	{
		if (toFile.exists())
			toFile.delete();
		try
		{
			if (!toFile.createNewFile())
				return false;
			FileOutputStream fos = new FileOutputStream(toFile);
			FileInputStream fis = new FileInputStream(fromFile);
			IOUtils.copy(fis, fos);
			fis.close();
			fos.close();
		}
		catch (IOException x)
		{
			return false;
		}
		return true;
	}

	/**
	 * returns a File object corresponding to an instance of localFile placed in dir - No OS calls are made and File is
	 * NOT created
	 * 
	 * @param dir the File Object representing the directory
	 * @param localFile the local file to place in dir, note that only the path is copied - this does copy trees
	 * @return File the File object that represents localFile in Dir
	 */
	public static File getFileInDirectory(File dir, File localFile)
	{
		if (dir == null)
			return localFile;
		if (localFile == null)
			return null;
		return new File(dir.getPath() + File.separator + localFile.getPath());
	}

	/**
	 * @param f the file to cleanup 
	 * @return the cleaned file
	 */
	public static File cleanURL(File f)
	{
		String s = UrlUtil.fileToUrl(f, false);
		return UrlUtil.urlToFile(s);
	}

	/**
	 * @param f the file to test 
	 * @return true if s is absolute
	 */
	public static boolean isAbsoluteFile(File f)
	{
		String s = f.getPath();
		return isAbsoluteFile(s);
	}

	/**
	 * @param f the file path to test 
	 * @return true if s is absolute
	 */
	public static boolean isAbsoluteFile(String f)
	{
		String fLocal = f;

		if (fLocal == null)
			return false;

		if (fLocal.startsWith(File.separator))
			return true;

		File[] roots = File.listRoots();
		if (roots != null)
		{
			fLocal = fLocal.toLowerCase();
			for (int i = 0; i < roots.length; i++)
			{
				if (fLocal.startsWith(roots[i].getPath().toLowerCase()))
					return true;
			}
		}

		return false;

	}

	/**
	 * @param file
	 * @return the file extension
	 */
	public static String getExtension(File file)
	{
		return file == null ? null : UrlUtil.extension(file.getName());
	}
}
