/*
 *
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
package org.cip4.jdflib.util.hotfolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileTimeTest extends JDFTestCaseBase
{

	@TempDir
	Path tempPath;

	@Test
	public void testExists()
	{
		assertTrue(new FileTime(new File(sm_dirTestData), false).exists());
	}

	@Test
	public void testToString()
	{
		assertNotNull(new FileTime(new File(sm_dirTestData), true).toString());
		assertNotNull(new FileTime(new File(sm_dirTestData), false).toString());
	}

	@Test
	public void testmodified()
	{
		final FileTime ft = new FileTime(tempPath.toFile(), false);
		final long updateModified = ft.updateModified();
		assertEquals(System.currentTimeMillis(), updateModified, 1000);
		ThreadUtil.sleep(400);
		assertEquals(updateModified, ft.updateModified(), 300);
	}

	@Test
	public void testmodifiedFile() throws Exception
	{
		for (final boolean b : new boolean[] { true, false })
		{
			final File dummy = tempPath.resolve(System.nanoTime() + ".file").toFile();
			final FileTime ft = new FileTime(dummy, b);
			assertTrue(FileUtil.forceDelete(dummy));
			FileUtil.createNewFile(dummy);
			ThreadUtil.sleep(2);
			for (int i = 0; i < 3; i++)
			{
				final FileOutputStream fos = new FileOutputStream(dummy, true);
				for (int j = 0; j < 42; j++)
				{// incrementally fill file
					fos.write(i);
				}
				fos.flush();
				fos.close();
				ThreadUtil.sleep(220);
				final long updateModified = ft.updateModified();
				log.info("loop " + i + " " + updateModified % 10000 + " " + ft.length);
				assertEquals(System.currentTimeMillis(), updateModified, 24200, "loop " + i + " " + b);

			}
		}
	}

	@Test
	public void testSameModified() throws IOException
	{
		final FileTime ft = new FileTime(File.createTempFile("abbb", "txt", new File(sm_dirTestDataTemp)), false);
		assertFalse(ft.sameModified());
		ft.updateModified();
		assertTrue(ft.sameModified());
	}

	@Test
	public void testSameModifiedRO() throws IOException
	{
		final FileTime ft = new FileTime(File.createTempFile("abbb", "txt", new File(sm_dirTestDataTemp)), false);
		assertFalse(ft.sameModified());
		ft.updateModified();
		assertTrue(ft.sameModified());
	}

}
