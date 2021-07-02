/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2021 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.jdflib.util.thread;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.util.ThreadUtil;
import org.junit.Test;

public class MultiJobTaskQueueTest extends JDFTestCaseBase
{
	int nRun;

	class WaitRunner implements Runnable
	{
		/**
		 *
		 * @param i
		 */
		WaitRunner(final int i)
		{
			this(i, 100);
		}

		WaitRunner(final int i, final int t)
		{
			super();
			this.i = i;
			this.t = t;
		}

		private final int i;
		private final int t;

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run()
		{
			log.info("running: " + i);
			final boolean b = ThreadUtil.sleep(t);
			log.info(b + " waited: " + i);
			nRun++;
		}
	}

	/**
	 *
	 */
	@Test
	public void testSize()
	{
		final MultiTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multij0", 3);
		assertEquals(0, q.size());
	}

	/**
	 *
	 */
	@Test
	public void testExecuting()
	{
		final MultiTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multij0", 3);
		assertEquals(0, q.executing());
	}

	/**
	 *
	 */
	@Test
	public void testMaxParrallel()
	{
		final MultiTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multij0", 3);
		assertEquals(3, q.getMaxParallel());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testMulti()
	{
		final MultiJobTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multij1", 7);
		assertEquals(0, q.getAvQueue());
		assertEquals(0, q.getAvRun());
		for (int i = 0; i < 10; i++)
			q.queue(new WaitRunner(i, 100), "" + (i % 3));

		assertEquals(q.getAvQueue(), 0);
		for (int i = 0; i < 342; i++)
		{
			ThreadUtil.sleep(42);
			if (q.size() <= 7)
			{
				break;
			}
		}
		assertEquals(q.size(), 6, 2);
		for (int i = 0; i < 342; i++)
		{
			ThreadUtil.sleep(14);
			if (q.size() == 0)
			{
				break;
			}
		}
		assertTrue(q.getAvQueue() > 0);
		assertTrue(q.getAvRun() > 0);
		assertEquals(q.size(), 0, 1);
		assertTrue(q.queue(new WaitRunner(4)));
		for (int i = 0; i < 342; i++)
		{
			ThreadUtil.sleep(4);
			if (q.size() == 0)
			{
				break;
			}
		}
		assertEquals(q.size(), 0, 1);
		assertTrue(q.getAvQueue() > 0);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testUniqueMulti()
	{
		final MultiJobTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multiju", 3);
		q.setUnique(true);
		for (int i = 0; i < 10; i++)
			q.queue(new WaitRunner(i, 100), "" + (i % 4));

		assertEquals(2, q.waiting(), 2);
		for (int i = 0; i < 342; i++)
		{
			ThreadUtil.sleep(14);
			if (q.size() == 0)
			{
				break;
			}
		}
		assertTrue(q.getAvQueue() > 0);
		assertTrue(q.getAvRun() > 0);
		assertEquals(q.size(), 0, 1);

		for (int i = 0; i < 4; i++)
			assertTrue(q.queue(new WaitRunner(i, 100), "" + (i % 4)));
		for (int i = 0; i < 44; i++)
		{
			q.queue(new WaitRunner(i, 100), "" + (i % 4));
			assertEquals(2, q.waiting(), 2);
		}
	}

	/**
	 *
	 *
	 */
	@Test
	public void testToString()
	{
		final MultiJobTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multiju", 7);
		q.setUnique(true);
		assertNotNull(q.toString());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testManyMulti()
	{
		nRun = 0;
		final MultiJobTaskQueue q = MultiJobTaskQueue.getCreateJobQueue("multij2", 7);
		assertEquals(0, q.getAvQueue());
		assertEquals(0, q.getAvRun());
		for (int i = 0; i < 444; i++)
		{
			q.queue(new WaitRunner(i, 10), "" + ((int) (10.0 * Math.random())));
		}
		ThreadUtil.sleep(40);

		for (int i = 0; i < 442; i++)
		{
			ThreadUtil.sleep(40);
			if (q.size() == 0)
			{
				break;
			}
		}
		ThreadUtil.sleep(40);
		assertEquals(nRun, 444, 5);
	}
}
