/*
 *
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
 */
package org.cip4.jdflib.util.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.util.ContainerUtil;
import org.cip4.jdflib.util.MyLong;
import org.cip4.jdflib.util.ThreadUtil;
import org.cip4.jdflib.util.ThreadUtil.MyMutex;

/**
 * class to persist stuff later
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class DelayedPersist extends Thread
{
	private final HashMap<IPersistable, MyLong> persistQueue;
	private boolean stop;
	private static DelayedPersist theDelayed = null;
	private final MyMutex waitMutex;
	private final Log log;

	private DelayedPersist()
	{
		super("DelayedPersist");
		log = LogFactory.getLog(getClass());
		persistQueue = new HashMap<IPersistable, MyLong>();
		stop = false;
		waitMutex = new MyMutex();
		start();
	}

	/**
	 * 
	 * @return
	 */
	public static DelayedPersist getDelayedPersist()
	{
		if (theDelayed == null)
			theDelayed = new DelayedPersist();
		return theDelayed;
	}

	/**
	 * 
	 */
	public static void shutDown()
	{
		if (theDelayed == null)
			return;
		theDelayed.stop = true;
		theDelayed.persistQueues();
		ThreadUtil.notifyAll(theDelayed.waitMutex);
		theDelayed = null;
	}

	/**
	 * 
	 * @param qp
	 * @param deltaTime
	 */
	public void queue(IPersistable qp, long deltaTime)
	{
		synchronized (persistQueue)
		{
			MyLong l = persistQueue.get(qp);
			long t = System.currentTimeMillis();
			if (l == null)
			{
				persistQueue.put(qp, new MyLong(t + deltaTime));
			}
			else if (t + deltaTime < l.i)
			{
				// we want it sooner
				l.i = t + deltaTime;
			}
		}
		if (deltaTime <= 0)
			ThreadUtil.notify(waitMutex);
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
				persistQueues();
			}
			catch (Exception e)
			{
				log.error("whazzup queuing delayedPersist ", e);
			}
			if (stop)
			{
				log.info("end of queue persist loop");
				break;
			}
			ThreadUtil.wait(waitMutex, 10000);
		}
	}

	/**
	 * 
	 */
	private void persistQueues()
	{
		long t = System.currentTimeMillis();
		Vector<IPersistable> theList = new Vector<IPersistable>();

		synchronized (persistQueue)
		{
			Vector<IPersistable> v = ContainerUtil.getKeyVector(persistQueue);
			if (v == null)
				return;
			Iterator<IPersistable> it = v.iterator();

			while (it.hasNext())
			{
				IPersistable qp = it.next();
				MyLong l = persistQueue.get(qp);
				if (stop || l.i < t)
				{
					theList.add(qp);
					persistQueue.remove(qp);
				}
			}
		}

		// now the unsynchronized stuff
		Iterator<IPersistable> it = theList.iterator();
		while (it.hasNext())
		{
			IPersistable qp = it.next();
			qp.persist();
		}
		System.gc();
	}

	/**
	 * @see java.lang.Thread#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return "DelayedPersist Thread " + stop + " queue: " + persistQueue;
	}
}