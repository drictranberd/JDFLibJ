/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2015 The International Cooperation for the Integration of
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
 *    Alternately, this acknowledgment mrSubRefay appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior writtenrestartProcesses()
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
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIrSubRefAL DAMAGES (INCLUDING, BUT NOT
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
 * originally based on software restartProcesses()
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *
 */
/**
 * 
 */
package org.cip4.jdflib.elementwalker;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFComment;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.resource.JDFPart;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.process.JDFComChannel;
import org.cip4.jdflib.resource.process.JDFGeneralID;
import org.cip4.jdflib.util.StringUtil;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG<br/>
 * removes any empty or unlinked resources
 * 
 */
public class RemoveEmpty extends BaseElementWalker
{
	boolean zappElements;
	final JDFPart part;

	/**
	 * 
	 */
	public RemoveEmpty()
	{
		super(new BaseWalkerFactory());
		zappElements = true;
		part = (JDFPart) new JDFDoc("Part").getRoot();
		part.appendElement("foo");
		new BaseWalker(getFactory()); // need a default walker
	}

	/**
	 * 
	 * remove all unlinked crap and empty string attributes
	 * @param n
	 */
	public void removEmpty(JDFNode n)
	{
		new UnLinkFinder().eraseUnlinked(n);
		removEmptyElement(n);
		new UnLinkFinder().eraseUnlinked(n);
	}

	/**
	 * 
	 *
	 * @param e
	 */
	public void removEmptyElement(KElement e)
	{
		walkTreeKidsFirst(e);
	}

	/**
	 * 
	 * remove all empty string attributes
	 * @param e
	 */
	public void removEmptyAttributes(KElement e)
	{
		boolean keep = zappElements;
		zappElements = false;
		walkTreeKidsFirst(e);
		zappElements = keep;
	}

	/**
	 * the resource walker note the naming convention Walkxxx so that it is automagically instantiated by the super classes
	 * 
	 * @author prosirai
	 * 
	 */
	public class WalkElement extends BaseWalker
	{

		/**
		 * fills this into the factory
		 */
		public WalkElement()
		{
			super(getFactory());
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 * @param e1 - the element to track
		 * @param trackElem - always null
		 * @return the element to continue walking
		 */
		@Override
		public KElement walk(final KElement e1, final KElement trackElem)
		{
			boolean hasGood = walkAttributes(e1);
			if (zappElements && e1.numChildElements_KElement(null, null) == 0 && e1.getText() == null && !hasGood)
			{
				e1.deleteNode();
				return null;
			}
			else
			{
				return e1;
			}
		}

		/**
		 * 
		 * @param e1
		 * @return true if something good was inside
		 */
		protected boolean walkAttributes(final KElement e1)
		{
			JDFAttributeMap map = e1.getAttributeMap();
			VString allKeys = map.getKeys();
			boolean hasGood = false;
			VString dummy = zappElements ? getDummyAttributes() : null;
			for (String key : allKeys)
			{
				if (StringUtil.getNonEmpty(map.get(key)) == null)
				{
					e1.removeAttribute(key);
				}
				else if (zappElements && !hasGood && !dummy.contains(key))
				{
					hasGood = true;
				}
			}
			return hasGood;
		}

		/**
		 *  
		 * @return
		 */
		protected VString getDummyAttributes()
		{
			VString dummy = new VString();
			dummy.add(AttributeName.ID);
			dummy.add(AttributeName.AGENTNAME);
			dummy.add(AttributeName.AGENTVERSION);
			return dummy;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return true;
		}
	}

	/**
	 * zapp me
	 * 
	 * @author prosirai
	 * 
	 */
	public class WalkGeneralID extends WalkElement
	{

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 * @param e - the element to track
		 * @param trackElem - always null
		 * @return the element to continue walking
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			String idUsage = ((JDFGeneralID) e).getIDUsage();
			String idValue = ((JDFGeneralID) e).getIDValue();
			if (StringUtil.getNonEmpty(idUsage) == null || StringUtil.getNonEmpty(idValue) == null)
			{
				e.deleteNode();
				return null;
			}
			else
			{
				return super.walk(e, trackElem);
			}
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFGeneralID;
		}
	}

	/**
	* zapp me
	* 
	* @author prosirai
	* 
	*/
	public class WalkComment extends WalkElement
	{
		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 * @param e - the element to track
		 * @param trackElem - always null
		 * @return the element to continue walking
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			String text = StringUtil.getNonEmpty(e.getText());
			if (text == null)
			{
				e.deleteNode();
				return null;
			}
			else
			{
				return super.walk(e, trackElem);
			}
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFComment;
		}
	}

	/**
	 * zapp me
	 * 
	 * @author prosirai
	 * 
	 */
	public class WalkResource extends WalkElement
	{
		/**
		 * @see org.cip4.jdflib.elementwalker.RemoveEmpty.WalkElement#getDummyAttributes()
		 */
		@Override
		protected VString getDummyAttributes()
		{
			VString v = super.getDummyAttributes();
			v.add(AttributeName.CLASS);
			v.add(AttributeName.STATUS);
			v.add(AttributeName.PARTIDKEYS);
			v.add(AttributeName.PARTUSAGE);
			VString partAttribs = part.knownAttributes();
			JDFElement foo = (JDFElement) part.getElement("foo");
			VString fooAttribs = foo.knownAttributes();
			partAttribs.removeStrings(fooAttribs, 0);

			v.appendUnique(partAttribs);
			return v;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResource;
		}

		/**
		 * 
		 * @see org.cip4.jdflib.elementwalker.RemoveEmpty.WalkElement#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(KElement e1, KElement trackElem)
		{
			boolean hasGood = false;
			if (zappElements)
			{
				hasGood = walkAttributes(e1);
				JDFResource r = (JDFResource) e1;
				if (!hasGood && r.getLinksAndRefs(false, true) != null)
				{
					hasGood = true;
				}
				if (!hasGood)
				{
					VElement links = r.getLinksAndRefs(true, false);
					if (links != null)
					{
						for (KElement e : links)
						{
							JDFResourceLink rl = (JDFResourceLink) e;
							removEmptyElement(rl);
							if (rl.getAmountPool() != null || rl.getPart(0) != null)
							{
								hasGood = true;
								break;
							}
							JDFAttributeMap map = rl.getAttributeMap();
							map.removeKeys(super.getDummyAttributes());
							map.remove(AttributeName.RREF);
							map.remove(AttributeName.USAGE);
							if (!map.isEmpty())
							{
								hasGood = true;
								break;
							}
						}
					}
				}
			}
			return hasGood ? e1 : super.walk(e1, trackElem);
		}
	}

	/**
	* zapp me
	* 
	* @author prosirai
	* 
	*/
	public class WalkComChannel extends WalkResource
	{
		/**
		 * @see org.cip4.jdflib.elementwalker.RemoveEmpty.WalkElement#getDummyAttributes()
		 */
		@Override
		protected VString getDummyAttributes()
		{
			VString v = super.getDummyAttributes();
			// if only channeltype is specified, we have an empty dummy
			v.add(AttributeName.CHANNELTYPE);
			return v;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFComChannel;
		}
	}

	/**
	 * 
	 * if set to true, attributes with proprietary namespace prefixes are zapped, else kept
	 * @param zappElements if true, zapp 'em (the default) else keep 'em
	 */
	public void setZappElements(boolean zappElements)
	{
		this.zappElements = zappElements;
	}

}
