/*
 *
 * The CIP4 Software License, Version 1.0
 *
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
package org.cip4.jdflib.util.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.util.ThreadUtil;

/**
 * class to run heavy tasks one at a time
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class OrderedTaskQueue extends Thread
{
	private final Vector<Runnable> queue;
	private boolean stop;
	private final Log log;
	private MyMutex mutex;
	private static Map<String, OrderedTaskQueue> theMap = new HashMap<String, OrderedTaskQueue>();

	/**
	 * 
	 * grab the queue
	 * @param name
	 * @return
	 */
	public static OrderedTaskQueue getCreateQueue(String name)
	{
		synchronized (theMap)
		{

			OrderedTaskQueue orderedTaskQueue = theMap.get(name);
			if (orderedTaskQueue == null)
			{
				orderedTaskQueue = new OrderedTaskQueue(name);
				theMap.put(name, orderedTaskQueue);
			}
			return orderedTaskQueue;
		}
	}

	/**
	 * @param name 
	 * 
	 */
	public OrderedTaskQueue(String name)
	{
		super(getThreadName(name));
		setDaemon(true);
		log = LogFactory.getLog(getClass());
		queue = new Vector<Runnable>();
		mutex = new MyMutex();
		start();
	}

	private static String getThreadName(String name)
	{
		return name == null ? "OrderedTaskQueue" : "OrderedTaskQueue_" + name;
	}

	/**
	 * 
	 */
	public void shutDown()
	{
		log.info("shutting down ordered queue");
		stop = true;
	}

	/**
	 * 
	 * @param task the thing to send off
	 */
	public void queue(Runnable task)
	{
		if (stop)
		{
			log.error("cannot queue task in stopped queue");
		}
		synchronized (queue)
		{
			queue.add(task);
			ThreadUtil.notifyAll(mutex);
		}
	}

	/**
	 * @see java.lang.Thread#run()
	*/
	@Override
	public void run()
	{
		log.info("starting queue persist loop");
		while (true)
		{
			try
			{
				runTasks();
			}
			catch (Exception e)
			{
				log.error("whazzup queuing delayedPersist ", e);
			}
			if (stop)
			{
				log.info("end of ordered task loop");
				ThreadUtil.notifyAll(mutex);
				mutex = null;
				break;
			}
			if (!ThreadUtil.wait(mutex, 1000000))
			{
				break;
			}
		}
	}

	/**
	 * 
	 */
	private void runTasks()
	{
		while (true)
		{
			Runnable r = null;
			synchronized (queue)
			{
				if (queue.size() > 0)
				{
					r = queue.remove(0);
				}
			}

			// now the unsynchronized stuff
			if (r != null)
			{
				r.run();
				System.gc();
			}
			else
			{
				break;
			}
		}
	}

	/**
	 * @see java.lang.Thread#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return "OrderedTaskQueue Thread " + stop + " queue: " + queue;
	}
}