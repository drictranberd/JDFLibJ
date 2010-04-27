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
 */
/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFQueueFilter.java
 *
 * Last changes
 *
 * 2002-07-02 JG - init() Also call super::init()
 *
 */
package org.cip4.jdflib.jmf;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoQueueFilter;
import org.cip4.jdflib.auto.JDFAutoQueueEntry.EnumQueueEntryStatus;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.resource.JDFDevice;
import org.cip4.jdflib.util.ContainerUtil;
import org.cip4.jdflib.util.EnumUtil;
import org.cip4.jdflib.util.StringUtil;

/**
 *
 */
public class JDFQueueFilter extends JDFAutoQueueFilter
{
	private class QueueMatcher
	{
		/**
		 * @param theQueue
		 * @param lastQueue
		 */
		public QueueMatcher(final JDFQueue theQueue, final JDFQueue lastQueue)
		{
			super();
			qed = getQueueEntryDetails();
			this.theQueue = theQueue;
			this.lastQueue = lastQueue;
			qeMatch = new QueueEntryMatcher();
			if (lastQueue != null && EnumUpdateGranularity.ChangesOnly.equals(getUpdateGranularity()))
			{
				lastMap = lastQueue.getQueueEntryIDMap();
				if (!lastQueue.hasAttribute(AttributeName.QUEUESIZE)) // we calc queuesize - so also calc queuesize for the original prior queue
				{
					lastQueue.setQueueSize(lastQueue.getQueueSize());
				}
			}
			else
			{
				lastMap = null;
			}
		}

		private final JDFQueue lastQueue; // the prior queue to compare to for matching
		private JDFQueue theQueue; // the prior queue to compare to for matching
		private Map<String, JDFQueueEntry> lastMap;
		private EnumQueueEntryDetails qed;
		private final QueueEntryMatcher qeMatch;

		/**
		 * modifies queue to match this filter by removing all non-matching entries
		 * 
		 * make sure that this is a copy of any original queue as the incoming queue itself is not cloned
		 * @return 
		 * @deprecated use copyTo
		 *  
		 */
		@Deprecated
		protected JDFQueue apply()
		{
			final int maxEntries = hasAttribute(AttributeName.MAXENTRIES) ? getMaxEntries() : 999999;

			VElement v = theQueue.getQueueEntryVector();
			if (v != null)
			{
				final int size = v.size();
				theQueue.setQueueSize(size);

				for (int i = 0; i < size; i++)
				{
					final JDFQueueEntry qe = (JDFQueueEntry) v.elementAt(i);
					apply(qe);
				}
			}

			addRemoved();
			// zapp n>maxEntries
			final int numEntries = theQueue.numEntries(null);
			if (numEntries > maxEntries)
			{
				v = theQueue.getQueueEntryVector();
				for (int i = maxEntries; i < numEntries; i++)
				{
					v.get(i).deleteNode();
				}
			}
			if (numEntries == 0 && lastMap != null) // empty queue in diff mode
			{
				final JDFAttributeMap mapQueue = theQueue.getAttributeMap();
				final JDFAttributeMap mapLast = lastQueue.getAttributeMap();
				if (ContainerUtil.equals(mapLast, mapQueue))
				{
					theQueue.deleteNode();
					theQueue = null;
				}
			}
			return theQueue;
		}

		/**
		 * 
		 */
		private void addRemoved()
		{
			if (lastMap != null && lastMap.size() > 0)
			{
				final JDFQueueEntry qeFirst = theQueue.getQueueEntry(0);
				final Iterator<String> qeIt = lastMap.keySet().iterator();
				while (qeIt.hasNext())
				{
					final String qeID = qeIt.next();
					final JDFQueueEntry qe = (JDFQueueEntry) theQueue.copyElement(lastMap.get(qeID), qeFirst);
					qe.setQueueEntryStatus(EnumQueueEntryStatus.Removed);
					qe.removeAttribute(AttributeName.STATUSDETAILS);
				}
			}
		}

		/**
		 * modifies queueEntry to match this filter by removing all non-matching attributes and elements
		 * 
		 * make sure that this is a copy of any original queue as the incoming queue itself is not cloned
		 * 
		 * @param qe
		 * @deprecated use copyTo
		 */
		@Deprecated
		protected void apply(final JDFQueueEntry qe)
		{
			if (qe == null)
			{
				return;
			}

			if (!matches(qe))
			{
				qe.deleteNode();
			}
			if (noDifference(qe))
			{
				qe.deleteNode();
			}
			cleanQE(qe);
		}

		/**
		 * copies queueEntry if it matches this filter and removes all non-matching attributes and elements
		 * @param newQueue the new parent queue
		 * 
		 * @param qe the queue entry to copy
		 * @return the copied element, null if this was not copied
		 */
		private JDFQueueEntry copyTo(JDFQueue newQueue, JDFQueueEntry qe)
		{
			if (qe == null)
				return null;

			if (qeMatch.matches(qe) && !noDifference(qe))
			{
				qe = (JDFQueueEntry) newQueue.copyElement(qe, null);
			}

			cleanQE(qe);
			return qe;
		}

		/**
		 * remove any redundant details
		 * @param qe
		 */
		private void cleanQE(JDFQueueEntry qe)
		{
			if (qed == null)
			{
				qed = EnumQueueEntryDetails.Brief;
			}
			if (EnumUtil.aLessEqualsThanB(EnumQueueEntryDetails.Brief, qed))
			{
				qe.removeChildren(ElementName.JOBPHASE, null, null);
			}
			if (EnumUtil.aLessEqualsThanB(EnumQueueEntryDetails.JobPhase, qed))
			{
				qe.removeChildren(ElementName.JDF, null, null);
			}
		}

		/**
		 * @param qe the queue entry to apply the change only filter to
		 * 
		 * @return true if this element has been removed because it is identical to a previous element (no change)
		 */
		private boolean noDifference(final JDFQueueEntry qe)
		{
			if (lastMap == null)
			{
				return false;
			}
			final String qeID = qe.getQueueEntryID();
			final JDFQueueEntry lastQueueEntry = lastMap.get(qeID);
			if (lastQueueEntry == null)
			{
				return false;
			}
			lastMap.remove(qeID);
			final boolean equal = qe.isEqual(lastQueueEntry);
			return equal;
		}

		/**
		 * copy a queue to a parent element while applying the filter
		 * 
		 * @param parent the parent element to create the queue in; may be null
		 * @return
		 */
		protected JDFQueue copyTo(KElement parent)
		{
			final int maxEntries = hasAttribute(AttributeName.MAXENTRIES) ? getMaxEntries() : 999999;
			JDFQueue newQueue = (JDFQueue) (parent == null ? new JDFDoc(ElementName.QUEUE).getRoot() : parent.appendElement(ElementName.QUEUE));
			newQueue.setAttributes(theQueue);
			JDFQueueEntry qe = (JDFQueueEntry) theQueue.getFirstChildElement(ElementName.QUEUEENTRY, null);
			int n = 0;
			while (n < maxEntries && qe != null)
			{
				JDFQueueEntry qeNew = copyTo(newQueue, qe);
				if (qeNew != null)
					n++;
				qe = qe.getNextQueueEntry();
			}
			addRemoved();
			final int numEntries = newQueue.numEntries(null);
			if (numEntries == 0 && lastMap != null) // empty queue in diff mode
			{
				final JDFAttributeMap mapQueue = newQueue.getAttributeMap();
				final JDFAttributeMap mapLast = lastQueue.getAttributeMap();
				if (ContainerUtil.equals(mapLast, mapQueue))
				{
					newQueue = null;
				}
			}
			return newQueue;
		}

	}

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for JDFQueueFilter
	 * 
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	public JDFQueueFilter(final CoreDocumentImpl myOwnerDocument, final String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFQueueFilter
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	public JDFQueueFilter(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFQueueFilter
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	public JDFQueueFilter(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName, final String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	// **************************************** Methods
	// *********************************************
	/**
	 * toString
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		return "JDFQueueFilter[  --> " + super.toString() + " ]";
	}

	/**
	 * GetPartMapVector returns a vector of partmaps, null if no parts are present
	 * 
	 * @return VJDFAttributeMap
	 */
	@Override
	public VJDFAttributeMap getPartMapVector()
	{
		return super.getPartMapVector();
	}

	/**
	 * SetPartMapVector
	 * 
	 * @param vPart
	 */
	@Override
	public void setPartMapVector(final VJDFAttributeMap vPart)
	{
		super.setPartMapVector(vPart);
	}

	/**
	 * modifies queue to match this filter by removing all non-matching entries
	 * 
	 * make sure that this is a copy of any original queue as the incoming queue itself is not cloned
	 * 
	 * @param theQueue the queue to modify
	 * @return
	 * @deprecated use the 2 parameter version
	 */
	@Deprecated
	public JDFQueue match(final JDFQueue theQueue)
	{
		return apply(theQueue, null);
	}

	/**
	 * @deprected - use copyTo 
	 * modifies queue to match this filter by removing all non-matching entries
	 * 
	 * make sure that this is a copy of any original queue as the incoming queue itself is not cloned
	 * 
	 * @param theQueue the queue to modify
	 * @param lastQueue the last queue to diff against, note that this must be the complete queue prior to the last call of match
	 * @return
	 */
	public JDFQueue apply(final JDFQueue theQueue, final JDFQueue lastQueue)
	{
		if (theQueue == null)
		{
			return null;
		}
		return new QueueMatcher(theQueue, lastQueue).apply();
	}

	/**
	 * (9) get attribute QueueEntryDetails
	 * @return the value of the attribute
	 */
	@Override
	public EnumQueueEntryDetails getQueueEntryDetails()
	{
		String det = getAttribute(AttributeName.QUEUEENTRYDETAILS, null, "Brief");
		if ("None".equals(det))
			return EnumQueueEntryDetails.None;
		if ("Brief".equals(det))
			return EnumQueueEntryDetails.Brief;
		if ("JobPhase".equals(det))
			return EnumQueueEntryDetails.JobPhase;
		if ("JDF".equals(det))
			return EnumQueueEntryDetails.JDF;
		return null;
	}

	class QueueEntryMatcher
	{
		Set<String> qeDefs;
		Set<String> devIDs;
		VString gangNames;
		VString statusList;
		String jobID;
		String jobPartID;

		QueueEntryMatcher()
		{
			qeDefs = getQueueEntryDefSet();
			devIDs = getDeviceIDSet();
			gangNames = getGangNames();
			if (gangNames.size() == 0)
			{
				gangNames = null;
			}
			jobID = StringUtil.getNonEmpty(getJobID());
			jobPartID = StringUtil.getNonEmpty(getJobPartID());

			Vector<EnumQueueEntryStatus> vs = getStatusList();
			if (vs == null)
			{
				statusList = null;
			}
			else
			{
				statusList = new VString();
				for (EnumQueueEntryStatus e : vs)
				{
					statusList.add(e.getName());
				}
			}
		}

		/**
		 * return true if the queuentry matches this filter
		 * @param qe the queueentry to check
		 * @return
		 */
		protected boolean matches(final JDFQueueEntry qe)
		{
			if (qe == null)
			{
				return false;
			}

			if (EnumQueueEntryDetails.None.equals(getQueueEntryDetails()))
			{
				return false;
			}

			if (qeDefs != null && !qeDefs.contains(qe.getQueueEntryID()))
			{
				return false;
			}

			if (devIDs != null && !devIDs.contains(qe.getDeviceID()))
			{
				return false;
			}

			if (gangNames != null && !gangNames.contains(qe.getGangName()))
			{
				return false;
			}

			if (statusList != null && !statusList.contains(qe.getAttribute(AttributeName.STATUS)))
			{
				return false;
			}

			if (jobID != null && !StringUtil.matches(jobID, qe.getJobID()))
			{
				return false;
			}

			if (jobPartID != null && !StringUtil.matches(jobPartID, qe.getJobPartID()))
			{
				return false;
			}
			return true;
		}
	}

	/**
	 * return true if the queuentry matches this filter
	 * @param qe the queueentry to check
	 * @return
	 */
	public boolean matches(final JDFQueueEntry qe)
	{
		return new QueueEntryMatcher().matches(qe);
	}

	/**
	 * (9.2) get StatusList attribute StatusList
	 * 
	 * @return Vector of the enumerations this version uses queueEntryStatus rather than an own enumeration
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Vector<EnumQueueEntryStatus> getStatusList()
	{
		return (Vector<EnumQueueEntryStatus>) getEnumerationsAttribute(AttributeName.STATUSLIST, null, EnumQueueEntryStatus.getEnum(0), false);
	}

	/**
	 * get the list of QueueEntryDef/@QueueEntryIDs strings as a set
	 * 
	 * @return the set of QueueEntryIDs, null if no QueueEntryDef is specified
	 */
	public Set<String> getQueueEntryDefSet()
	{
		HashSet<String> set = null;

		final VElement v = getChildElementVector(ElementName.QUEUEENTRYDEF, null);
		if (v != null)
		{
			final int siz = v.size();
			set = siz == 0 ? null : new HashSet<String>();
			for (int i = 0; i < siz; i++)
			{
				final String qeid = ((JDFQueueEntryDef) v.elementAt(i)).getQueueEntryID();
				if (!isWildCard(qeid))
				{
					set.add(qeid);
				}
			}
		}

		return set != null && set.size() > 0 ? set : null;
	}

	/**
	 * get the list of Device/@DeviceIDs strings as a set
	 * 
	 * @return the set of DeviceIDs, null if no Device is specified
	 */
	public Set<String> getDeviceIDSet()
	{
		int siz = 0;
		HashSet<String> set = null;

		final VElement v = getChildElementVector(ElementName.DEVICE, null);
		if (v != null)
		{
			siz = v.size();
			set = siz == 0 ? null : new HashSet<String>();
			for (int i = 0; i < siz; i++)
			{
				final String qeid = ((JDFDevice) v.elementAt(i)).getDeviceID();
				if (!isWildCard(qeid))
				{
					set.add(qeid);
				}
			}
		}

		return set != null && set.size() > 0 ? set : null;
	}

	/**
	 * append a Device element with @DeviceID
	 * 
	 * @param deviceID the deviceID to set
	 * @return 
	 * @throws JDFException 
	 * @see org.cip4.jdflib.auto.JDFAutoQueueFilter#appendDevice()
	 */
	public JDFDevice appendDevice(final String deviceID) throws JDFException
	{
		final JDFDevice device = super.appendDevice();
		device.setDeviceID(deviceID);
		return device;
	}

	/**
	 * @param queueEntryID the queueEntryID to set
	 * @return 
	 * @throws JDFException 
	 * @see org.cip4.jdflib.auto.JDFAutoQueueFilter#appendQueueEntryDef()
	 */
	public JDFQueueEntryDef appendQueueEntryDef(final String queueEntryID) throws JDFException
	{
		final JDFQueueEntryDef queueEntryDef = super.appendQueueEntryDef();
		queueEntryDef.setQueueEntryID(queueEntryID);
		return queueEntryDef;
	}

	/**
	 * copy theQueue to newParent while applying the filter
	 * @param theQueue
	 * @param lastQueue
	 * @param resp 
	 * @return 
	 */
	public JDFQueue copy(JDFQueue theQueue, JDFQueue lastQueue, KElement resp)
	{
		QueueMatcher queueMatcher = new QueueMatcher(theQueue, lastQueue);
		return queueMatcher.copyTo(resp);
	}

}
