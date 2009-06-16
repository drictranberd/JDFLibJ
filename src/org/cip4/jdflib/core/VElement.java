/*
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
 * VElement.java
 *
 * Last changes
 *
 */
package org.cip4.jdflib.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import org.cip4.jdflib.core.KElement.SimpleNodeComparator;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.resource.JDFResource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 */
public class VElement extends Vector<KElement>
{
	private static final long serialVersionUID = 1L;

	// **************************************** Constructors
	// ****************************************
	/**
	 * constructor
	 */
	public VElement()
	{
		super();
	}

	/**
	 * constructor
	 * 
	 * @param m
	 */
	public VElement(final Vector m)
	{
		super();
		addAll(m);
	}

	/**
	 * constructor
	 * 
	 * @param n
	 */
	public VElement(final NodeList n)
	{
		super();

		if (n != null)
		{
			final int len = n.getLength();

			for (int i = 0; i < len; i++)
			{
				if (n.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					this.addElement((KElement) n.item(i));
				}
			}
		}
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
		return "VElement[ --> " + super.toString() + " ]";
	}

	/**
	 * index - get the index of s in the vector
	 * 
	 * @param s KElement to search for
	 * 
	 * @return int - the index of s in the vector
	 */
	public int index(final KElement s)
	{
		for (int i = 0; i < size(); i++)
		{
			if ((elementAt(i)).isEqual(s))
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * hasElement - checks if kElem is in the vector in contrast to contains, this uses the isEquals method
	 * 
	 * @param kElem the element to look for
	 * @deprecated 060216 use containsElement
	 * @return true if s is contained in this
	 */
	@Deprecated
	public boolean hasElement(final KElement kElem)
	{
		return index(kElem) >= 0;
	}

	/**
	 * appendUniqueNotNull - append a string but ignore multiple entries
	 * 
	 * @param KElement v
	 * @deprecated simply use appendUnique
	 */
	@Deprecated
	public void appendUniqueNotNull(final KElement v)
	{
		if (v != null && !contains(v))
		{
			addElement(v);
		}
	}

	/**
	 * AppendUniqueNotNull - append a vector but ignore multiple entries
	 * 
	 * @param VElement v
	 * @deprecated simply use appendUnique
	 */
	@Deprecated
	public void appendUniqueNotNull(final VElement v)
	{
		if (v != null)
		{
			for (int i = 0; i < v.size(); i++)
			{
				appendUniqueNotNull(v.elementAt(i));
			}
		}
	}

	/**
	 * AppendUnique - append a string but ignore multiple entries
	 * 
	 * @param v the element to append
	 */
	public void appendUnique(final KElement elem)
	{
		if (elem == null)
		{
			return;
		}
		if (!(index(elem) >= 0))
		{
			addElement(elem);
		}
	}

	/**
	 * addAll ignoring null collections
	 * 
	 * @param v the vector of elements to append
	 */
	public void addAll(final VElement elem)
	{
		if (elem == null)
		{
			return;
		}
		super.addAll(elem);

	}

	/**
	 * does this contain an equivalent element similar to contains but uses isEqual() instead of equals()
	 * 
	 * @param v the element to look for
	 * @return true, if v is contained in this
	 */
	public boolean containsElement(final KElement elem)
	{
		final int size = size();
		for (int i = 0; i < size; i++)
		{
			if (elem.isEqual(item(i)))
			{
				return true;
			}
		}
		return false;

	}

	/**
	 * are the two vectors equivalent, i.e. do thay only contain elements that are isEqual() or if this is empty and the comparison is against null
	 * 
	 * @param v the vector to compare
	 * @return true, if v is equal to this
	 */
	public boolean isEqual(final VElement v)
	{
		final int size = size();
		if (v == null)
		{
			return size == 0;
		}
		if (size != v.size())
		{
			return false;
		}
		final VElement v0 = new VElement(this);
		final VElement v1 = new VElement(v);
		v0.sort();
		v1.sort();
		for (int i = 0; i < size; i++)
		{
			if (!v0.get(i).isEqual(v1.get(i)))
			{
				return false;
			}
		}
		return true;

	}

	/**
	 * AppendUnique - append a vector but ignore multiple entries - equivalence is calculated with .equals
	 * 
	 * @param v the vector of elements to append
	 */
	public void appendUnique(final VElement v)
	{
		if (v == null)
		{
			return;
		}

		addAll(v);
		unify();
	}

	/**
	 * AppendUnique - append a vector but ignore multiple entries - equivalence is calculated with isEqual,
	 * 
	 * @param v the vector of elements to append
	 */
	public void appendUniqueElement(final VElement v)
	{
		if (v == null)
		{
			return;
		}

		addAll(v);
		unifyElement();
	}

	/**
	 * set the attribute key to the values defined in vValue
	 * 
	 * @param key key the attribute name
	 * @param vValue vValue the vector of values
	 * @param nameSpaceURI nameSpace of the attribute to set
	 * 
	 * @default setAttributes(key, vValue, null)
	 */
	public void setAttributes(final String key, final Vector vValue, final String nameSpaceURI)
	{
		if (size() != vValue.size())
		{
			System.out.println("VElement.SetAttributes: size mismatch");
		}

		for (int i = 0; i < size(); i++)
		{
			final KElement k = elementAt(i);
			k.setAttribute(key, (String) vValue.elementAt(i), nameSpaceURI);
		}
	}

	/**
	 * sorts the vector in canonical order using SimpleNodeComparator
	 * 
	 */
	public void sort()
	{
		Collections.sort(this, new SimpleNodeComparator());
	}

	/**
	 * SetAttribute in all elements of this
	 * 
	 * @param key key the attribute name
	 * @param value the value
	 * @param nameSpaceURI nameSpace of the attribute to set
	 * 
	 * @default SetAttributes(key, value, null)
	 */
	public void setAttribute(final String key, final String value, final String nameSpaceURI)
	{
		final int siz = size();
		for (int i = 0; i < siz; i++)
		{
			final KElement k = elementAt(i);
			k.setAttribute(key, value, nameSpaceURI);
		}
	}

	/**
	 * Remove Attribute in all elements of this
	 * 
	 * @param key key the attribute name
	 * @param nameSpaceURI nameSpace of the attribute to set
	 * 
	 * @default removeAttributes(key, null)
	 */
	public void removeAttribute(final String key, final String nameSpaceURI)
	{
		final int siz = size();
		for (int i = 0; i < siz; i++)
		{
			final KElement k = elementAt(i);
			k.removeAttribute(key, nameSpaceURI);
		}
	}

	/**
	 * Remove elements listed in v from this
	 * 
	 * @param v elements to remove
	 */
	public void removeElements(final VElement v)
	{
		for (int i = size() - 1; i >= 0; i--)
		{
			if (v.containsElement(elementAt(i)))
			{
				removeElementAt(i);
			}
		}
	}

	/**
	 * RemoveElements
	 * 
	 * @param e the element to remove
	 * @param nMax maximum number of dulicate elements to remove
	 * 
	 * @default RemoveElements(e, 0)
	 */
	public void removeElements(final KElement e, final int nMax)
	{
		int j = 0;

		for (int i = size() - 1; i >= 0; i--)
		{
			if (e == elementAt(i))
			{
				removeElementAt(i);

				if (++j == nMax)
				{
					break;
				}
			}
		}
	}

	/**
	 * get the node names of this vector in the same order
	 * 
	 * @param bLocal if true use getLocalName() else getNodeName() o each item
	 * @return VString vector of node names
	 */
	public VString getElementNameVector(final boolean bLocal)
	{
		final VString v = new VString();
		final int size = size();
		v.ensureCapacity(size);
		for (int i = 0; i < size; i++)
		{
			v.add(bLocal ? item(i).getLocalName() : item(i).getNodeName());
		}
		return v;
	}

	/**
	 * ToVector - parse a node list for elements spezified through parameters note that the vector is static - i.e. the elements are NOT modified by operations
	 * to the nodeList. This behavior is different than that of the actual nodelist!
	 * 
	 * @param element name of the element typ you want
	 * @param mAttrib a attribute typ you want
	 * @param bAnd true, if you want to add the element if mAttrib was found in the element
	 * @param nameSpaceURI the namespace to search in
	 * @return VElement - vector of all elements matching the conditions above
	 */
	public VElement toVector(final String element, final JDFAttributeMap mAttrib, final boolean bAnd, final String nameSpaceURI)
	{
		final VElement v = new VElement();
		final boolean bWantName = KElement.isWildCard(element);
		final boolean bWantNS = KElement.isWildCard(nameSpaceURI);

		// loop over the list
		final int len = size();
		// optimize -> walk tree once to set it up!
		if (len > 0)
		{
			elementAt(len - 1);
		}
		for (int i = 0; i < len; i++)
		{
			final KElement k = elementAt(i);
			if (bWantName)
			{
				// want only named elements
				if (!k.getNodeName().equals(element))
				{
					continue;
				}
			}
			if (bWantNS)
			{
				// want only named elements
				if (!k.getNamespaceURI().equals(nameSpaceURI))
				{
					continue;
				}
			}
			if (k.includesAttributes(mAttrib, bAnd))
			{
				v.addElement(k);
			}
		}
		return v;
	}

	/**
	 * item - returns null if index is out of bounds or the requested item is not an ELEMENT_NODE !
	 * 
	 * @param index vector index of the element you want
	 * @return KElement - the requested item or null, if index is out of bounds
	 */
	@Override
	public KElement elementAt(int index)
	{
		if (index < 0)
		{
			index += size();
		}
		if (size() <= index)
		{
			return null;
		}
		return super.elementAt(index);
	}

	/**
	 * item - returns null if index is out of bounds or the requested item is not an ELEMENT_NODE !
	 * 
	 * @param index vector index of the element you want
	 * @return KElement - the requested item or null, if index is out of bounds
	 */
	@Override
	public KElement get(int index)
	{
		if (index < 0)
		{
			index += size();
		}
		if (size() <= index)
		{
			return null;
		}
		return super.get(index);
	}

	/**
	 * item - returns null if index is out of bounds or the requested item is not an ELEMENT_NODE !
	 * 
	 * @param index vector index of the element you want
	 * @return KElement - the requested item or null, if index is out of bounds
	 */
	public KElement item(final int index)
	{

		return elementAt(index);
	}

	/**
	 * returns the common ancestor of all entries of this
	 * 
	 * @return the element that is a common ancestor of all vector members
	 * @since 050721
	 */
	KElement getCommonAncestor()
	{
		final int siz = size();
		if (siz == 0)
		{
			return null;
		}

		if (siz == 1)
		{
			return elementAt(0);
		}

		KElement ret = elementAt(0); // could all be the same, therefore do not
		// start with parent node

		// loop down searching for a common ancestor
		while (ret != null)
		{
			boolean bOK = true;
			for (int i = 1; i < siz; i++)
			{ // start at 1, since the parents of 0 are ok by definition
				if (!ret.isAncestor(elementAt(i)))
				{
					bOK = false;
					break;
				}
			}
			if (bOK)
			{ // found the ancestor of all members
				return ret;
			}
			ret = ret.getParentNode_KElement();
		}
		return ret; // if we get here it is null
	}

	/**
	 * Resource
	 * 
	 * @param int i
	 * 
	 * @return JDFResource
	 * @deprecated used only to facilitate migration from vResource to vElement
	 */
	@Deprecated
	public JDFResource resource(final int i)
	{
		return (JDFResource) elementAt(i);
	}

	/**
	 * unify - make VElement unique, retaining initial order
	 */
	public void unify()
	{
		final HashSet set = new HashSet();
		int size = size();
		for (int i = 0; i < size; i++)
		{
			final KElement e = this.item(i);
			if (set.contains(e))
			{
				this.removeElementAt(i);
				i--;
				size--;
			}
			else
			{
				set.add(e);
			}
		}
	}

	/**
	 * unify - make VElement unique, retaining initial order uses containsElement, not contains
	 */
	public void unifyElement()
	{
		int size = size();
		for (int i = 0; i < size; i++)
		{
			final KElement e = this.item(i);
			for (int j = size - 1; j > i; j--)
			{
				final KElement e2 = this.item(j);
				if (e.isEqual(e2))
				{
					removeElementAt(j);
					size--;
				}
			}
		}
	}
}
