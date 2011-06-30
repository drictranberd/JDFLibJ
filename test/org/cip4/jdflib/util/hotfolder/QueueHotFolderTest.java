/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2009 The International Cooperation for the Integration of
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
package org.cip4.jdflib.util.hotfolder;

import java.io.File;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage;
import org.cip4.jdflib.util.FileUtil;
import org.cip4.jdflib.util.ThreadUtil;

/**
 * @author Rainer
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueueHotFolderTest extends JDFTestCaseBase
{
	private File theHF;
	private File theStorage;
	QueueHotFolder hf;
	JDFDoc doc = null;

	protected class MyListener implements QueueHotFolderListener
	{
		protected VElement vJMF = new VElement();

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cip4.jdflib.util.QueueHotFolderListener#submitted(org.cip4.jdflib .jmf.JDFJMF)
		 */
		public boolean submitted(final JDFJMF submissionJMF)
		{
			vJMF.add(submissionJMF);
			return true;
		}
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		theHF = new File(sm_dirTestDataTemp + File.separator + "QHFTest");
		theStorage = new File(sm_dirTestDataTemp + File.separator + "QHFStore");
		FileUtil.deleteAll(theHF);
		theHF.mkdirs();
		FileUtil.deleteAll(theStorage);
		theStorage.mkdirs();
		HotFolder.setDefaultStabilizeTime(333);
		doc = new JDFDoc("JDF");

	}

	/**
	 * @throws Exception
	 */
	public void testSubmitSingleFile() throws Exception
	{
		final MyListener myListener = new MyListener();
		final File file = new File(theHF + File.separator + "f1.jdf");

		doc.write2File(file, 2, false);
		assertTrue(file.exists());
		assertNull(FileUtil.listFilesWithExtension(theStorage, null));
		hf = new QueueHotFolder(theHF, theStorage, null, myListener, null);
		for (int i = 0; i < 20; i++)
		{
			if (!file.exists())
			{
				break;
			}
			System.out.println("Waiting " + i);
			ThreadUtil.sleep(1000);
		}
		assertFalse(file.exists());
		ThreadUtil.sleep(1000);
		assertNull(FileUtil.listFilesWithExtension(theStorage, null));
		assertEquals(myListener.vJMF.size(), 1);
		final JDFJMF elementAt = (JDFJMF) myListener.vJMF.elementAt(0);
		assertEquals(elementAt.getCommand(0).getEnumType(), JDFMessage.EnumType.SubmitQueueEntry);
		assertTrue(elementAt.getCommand(0).getQueueSubmissionParams(0).getURL().endsWith(".jdf"));
	}

	/**
	 * @throws Exception
	 */
	public void testOKFile() throws Exception
	{
		final MyListener myListener = new MyListener();
		final File file = new File(theHF + File.separator + "f1.jdf");

		doc.write2File(file, 2, false);
		assertTrue(file.exists());

		assertNull(FileUtil.listFilesWithExtension(theStorage, null));
		hf = new QueueHotFolder(theHF, theStorage, null, myListener, null);
		hf.setOKStorage(new File("ok"));
		for (int i = 0; i < 20; i++)
		{
			if (!file.exists())
			{
				break;
			}
			System.out.println("Waiting " + i);
			ThreadUtil.sleep(1000);
		}
		assertFalse(file.exists());
		ThreadUtil.sleep(1000);
		assertEquals(FileUtil.listFilesWithExtension(FileUtil.getFileInDirectory(theHF, new File("ok")), null).length, 1);
		assertEquals(myListener.vJMF.size(), 1);
		final JDFJMF elementAt = (JDFJMF) myListener.vJMF.elementAt(0);
		assertEquals(elementAt.getCommand(0).getEnumType(), JDFMessage.EnumType.SubmitQueueEntry);
		assertTrue(elementAt.getCommand(0).getQueueSubmissionParams(0).getURL().endsWith(".jdf"));
	}

	/**
	 * @throws Exception
	 */
	public void teststopStart() throws Exception
	{
		final MyListener myListener = new MyListener();
		final File file = new File(theHF + File.separator + "f1.jdf");
		doc.write2File(file, 2, false);
		assertTrue(file.exists());
		assertNull(FileUtil.listFilesWithExtension(theStorage, null));
		hf = new QueueHotFolder(theHF, theStorage, null, myListener, null);
		hf.stop();
		ThreadUtil.sleep(3000);
		assertTrue(file.exists());
		assertNull("File is still there after stop", FileUtil.listFilesWithExtension(theStorage, null));
		assertEquals(myListener.vJMF.size(), 0);
		hf.restart();
		ThreadUtil.sleep(2000);
		assertFalse("File is gone after stop", file.exists());
		assertNull(FileUtil.listFilesWithExtension(theStorage, null));
		assertEquals(myListener.vJMF.size(), 1);
		final JDFJMF elementAt = (JDFJMF) myListener.vJMF.elementAt(0);
		assertEquals(elementAt.getCommand(0).getEnumType(), JDFMessage.EnumType.SubmitQueueEntry);
		assertTrue(elementAt.getCommand(0).getQueueSubmissionParams(0).getURL().endsWith(".jdf"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cip4.jdflib.JDFTestCaseBase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		hf.stop();
	}

	// /////////////////////////////////////////////////////////////////////////

}
