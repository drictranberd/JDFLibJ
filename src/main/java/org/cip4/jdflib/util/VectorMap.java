/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2019 The International Cooperation for the Integration of
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * HashMap of multiple elements utility class
 *
 * @author Rainer Prosi
 * @param <key> the type used for the key
 * @param <vectorObject> the type used for individual elements of each vector in the map
 * @deprecated use ListMap
 */
@Deprecated
public class VectorMap<key, vectorObject> extends ListMap<key, vectorObject>
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2180413692846276265L;

	/**
	 * null constructor
	 */
	public VectorMap()
	{
		super();
		setUnique(true);
	}

	/**
	 * put the value for key, ensuring uniqueness
	 * @param key the key of the vector
	 * @param val the vector element
	 */
	@Override
	public void putOne(final key key, final vectorObject val)
	{
		List<vectorObject> v = get(key);
		if (v == null)
		{
			v = new Vector<vectorObject>();
			put(key, v);
		}
		if (!bUnique || !v.contains(val))
		{
			v.add(val);
		}
	}

	/**
	 *
	 * append a map, ensuring uniqueness
	 * @param key the key of the vector
	 * @param vVal the vector of elements
	 */
	public synchronized void appendUnique(final key key, final Vector<vectorObject> vVal)
	{
		super.appendUnique(key, vVal);
	}

	/**
	 *
	 * append a VectorMap, ensuring uniqueness
	 * @param map the map to add
	 *
	 */
	public void appendUnique(final VectorMap<key, vectorObject> map)
	{
		super.appendUnique(map);
	}

	/**
	 * get all values as one big vector, multiple entries are retained (see {@link ContainerUtil}.unify())
	 * @return a vector of all values, null if empty
	 */
	@Override
	public Vector<vectorObject> getAllValues()
	{
		Vector<vectorObject> v = new Vector<vectorObject>();
		final Collection<List<vectorObject>> c = values();
		final Iterator<List<vectorObject>> it = c.iterator();
		while (it.hasNext())
		{
			v = (Vector<vectorObject>) ContainerUtil.addAll(v, it.next());
		}
		return v.size() == 0 ? null : v;
	}

	/**
	 * insert the value for keyat position pos
	 *
	 * @param key the key of the vector
	 * @param newObj the new object to set
	 * @param pos the index in the vector, may be <0 to count from the end
	 * @throws IllegalArgumentException if pos is negative and abs(pos)>size()
	 */
	@Override
	public void setOne(final key key, final vectorObject newObj, int pos)
	{
		List<vectorObject> v = get(key);
		if (v == null)
		{
			v = new Vector<vectorObject>();
			put(key, v);
		}
		if (pos < 0)
			pos += v.size();

		if (pos < 0)
		{
			throw new IllegalArgumentException("index <0 after adding size: " + pos);
		}
		ContainerUtil.ensureSize(pos + 1, v);
		v.set(pos, newObj);
	}

	/**
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Vector<vectorObject> get(final Object key)
	{
		return (Vector<vectorObject>) super.get(key);
	}

}
