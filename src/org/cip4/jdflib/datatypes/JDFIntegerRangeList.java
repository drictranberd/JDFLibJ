/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2007 The International Cooperation for the Integration of
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
 * @author Elena Skobchenko
 *
 * JDFIntegerRangeList.java
 *
 */
package org.cip4.jdflib.datatypes;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.zip.DataFormatException;

import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.util.StringUtil;

/**
 * This class is a representation of an integer range list (JDFIntegerRangeList). It is a whitespace separated list of
 * integer ranges, for example "12~15 19~33"
 */
public class JDFIntegerRangeList extends JDFRangeList
{
	// **************************************** Attributes
	// ******************************************
	private int m_xDef = JDFIntegerRange.getDefaultDef();

	// **************************************** Constructors
	// ****************************************
	/**
	 * constructs an empty range list
	 */
	public JDFIntegerRangeList()
	{
		// default super constructor
	}

	/**
	 * constructs a JDFIntegerRangeList with the given string the default value for -1 is set to 0, i.e positive and
	 * negative numbers are handled explicitly
	 * 
	 * @param s - the given string
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFIntegerRangeList(String s) throws DataFormatException
	{
		this(s, JDFIntegerRange.getDefaultDef());
	}

	/**
	 * constructs a JDFIntegerRangeList with the given String and sets the number of items
	 * 
	 * @param s - the given string
	 * @param xdef - the default value that
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFIntegerRangeList(String s, int xdef) throws DataFormatException
	{
		setString(s);
		setDef(xdef);
	}

	/**
	 * constructs a JDFIntegerRangeList with the given JDFIntegerRangeList and sets the number of items
	 * 
	 * @param irl the given JDFIntegerRangeList
	 */
	public JDFIntegerRangeList(JDFIntegerRangeList irl)
	{
		rangeList = new Vector(irl.rangeList);
		setDef(irl.getDef());
	}

	/**
	 * constructs a JDFIntegerRangeList with the given JDFIntegerRange and sets xDef
	 * 
	 * @param ir the given JDFIntegerRange
	 */
	public JDFIntegerRangeList(JDFIntegerRange ir)
	{
		rangeList = new Vector();
		rangeList.addElement(ir);
		setDef(ir.getDef());
	}

	// **************************************** Methods
	// *********************************************

	/**
	 * inRange - returns true if the given int value is in one of the ranges of the range list
	 * 
	 * @param x the given int value to compare
	 * 
	 * @return boolean - true if in range otherwise false
	 */
	public boolean inRange(int x)
	{
		int sz = rangeList.size();
		for (int i = 0; i < sz; i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(i);

			if (r.inRange(x))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * setString - parse the given string and set the integer ranges
	 * 
	 * @param s the given string
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public void setString(String s) throws DataFormatException
	{
		rangeList.clear();
		if (s == null || s.equals(JDFConstants.EMPTYSTRING))
			return;
		if (s.indexOf(JDFConstants.TILDE) == 0 || s.lastIndexOf(JDFConstants.TILDE) == (s.length() - 1))
			throw new DataFormatException("JDFIntegerRangeList::SetString: Illegal string " + s);
		String zappedWS = StringUtil.zappTokenWS(s, "~");
		VString vs = StringUtil.tokenize(zappedWS, " \t", false);
		for (int i = 0; i < vs.size(); i++)
		{
			String str = vs.elementAt(i);
			try
			{
				JDFIntegerRange ir = new JDFIntegerRange(str);
				rangeList.addElement(ir);
			}
			catch (DataFormatException dfe)
			{
				throw new DataFormatException("JDFIntegerRangeList::SetString: Illegal string " + s);
			}
		}
	}

	/**
	 * isValid - validate the given String
	 * 
	 * @param s the given string
	 * 
	 * @return boolean - false if the String has not a valid format
	 */
	public boolean isValid(String s)
	{
		try
		{
			new JDFIntegerRangeList(s);
		}
		catch (DataFormatException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * getElementCount - returns the number of elements in the list. On the C++ side of the JDF library this method is
	 * called NElements. <br>
	 * E.g. the following list has 14 elements: "1~5 10~15 20~22" if any if any range cannot be resolved due to an
	 * unknown negative value without a known default, -1 is returned
	 * 
	 * @return int - the number of elements in this range, -1 if any range cannot be resolved
	 */
	public int getElementCount()
	{
		int sz = rangeList.size();
		int elementCount = 0;

		for (int i = 0; i < sz; i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(i);
			final int elemCount = r.getElementCount();
			if (elemCount <= 0)
				return -1;
			elementCount += elemCount;
		}

		return elementCount;
	}

	/**
	 * Element - value of the ith element in the range. If the index is negative, the position is counted from the end
	 * of the range.<br>
	 * For example the range is 3~7, the 2nd element is 5 and the -2nd element is 6.
	 * <p>
	 * performace warning: don't loop over getElement for potentially large lists with many individual elements.<br>
	 * Prefer to call getIntegerList() and loop over the list.
	 * 
	 * @param i the position, if it is a negative value start counting from the right side +1
	 * 
	 * @return int - the value at the ith position
	 * 
	 * @throws NoSuchElementException - if the index is out of range
	 */

	public int getElement(int i) throws NoSuchElementException
	{
		int iLocal = i;

		int n = this.getElementCount();

		if ((iLocal >= n) || (iLocal < -n))
		{
			throw new NoSuchElementException("JDFIntegerRangeList::Element out of range error!");
		}

		if (iLocal < 0)
		{
			return getElement(n + iLocal);
		}

		n = 0;

		for (int j = 0; j < rangeList.size(); j++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(j);
			int k = r.getElementCount();
			if (iLocal >= k)
			{
				// go to next range
				iLocal -= k;
			}
			else
			{
				return r.getElement(iLocal);
			}
		}

		return 0;
	}

	/**
	 * append - appends a new IntegerRange to the IntegerRangeList
	 * 
	 * @param r the given JDFIntegerRange
	 */
	public void append(JDFIntegerRange r)
	{
		r.setDef(getDef());
		rangeList.addElement(r);
	}

	/**
	 * append - appends a new IntegerRange to the IntegerRangeList
	 * 
	 * @param xMin the left value of the new range
	 * @param xMax the right value of the new range
	 */
	public void append(int xMin, int xMax)
	{
		this.append(new JDFIntegerRange(xMin, xMax, m_xDef));
	}

	/**
	 * append - appends an integer to the last IntegerRange of the IntegerRangelist if possible, examples if the last
	 * range list element looks like:
	 * 
	 * <pre>
	 * &quot;3&tilde;5&quot;        append(6)   -&gt; &quot;3&tilde;6&quot;
	 * &quot;5&quot;          append(6)   -&gt; &quot;5&tilde;6&quot;
	 * &quot;5&quot;          append(7)   -&gt; &quot;5 7&quot;
	 * &quot;5 6&quot;        append(7)   -&gt; &quot;5 &tilde; 7&quot;
	 * &quot;3&tilde;7 5&tilde;7&quot;    append(8)   -&gt; &quot;3&tilde;7 5&tilde;8&quot;
	 * &quot;3&tilde;7 5&tilde;9&quot;    append(8)   -&gt; &quot;3&tilde;7 5&tilde;9 8&quot;
	 * &quot;3&tilde;7 5&tilde;7&quot;    append(18)  -&gt; &quot;3&tilde;7 5&tilde;7 18&quot;
	 * </pre>
	 * 
	 * note that lists are not preserved. If you want to guarantee individual entries use append(x,x);
	 * 
	 * @param x the given int x
	 */
	public void append(int x)
	{
		/**
		 * only the last range list element, because its append
		 */
		if (rangeList != null && rangeList.size() > 0)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(rangeList.size() - 1);

			if (r.append(x))
			{
				return;
			}
		}
		this.append(new JDFIntegerRange(x, x, m_xDef));
	}

	/**
	 * getIntegerList - returns this integer range list as a JDFIntegerList
	 * 
	 * @return JDFIntegerList - the integer range list as a JDFIntegerList
	 */
	public JDFIntegerList getIntegerList()
	{
		JDFIntegerList irl = new JDFIntegerList();

		for (int i = 0; i < rangeList.size(); i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(i);
			irl.addIntegerList(r.getIntegerList());
		}

		return irl;
	}

	/**
	 * setDef - sets xDef, the default value which is used for negative numbers<br>
	 * if xdef==0 (the default), the neg numbers themselves are used<br>
	 * the value represents the index that is one past the end of the list
	 * 
	 * @param xdef one above the value that -1 will represent in this range i.e. the value that -0, were it possible to
	 *            specify, would represent
	 */
	public void setDef(int xdef)
	{
		m_xDef = xdef;
		for (int i = 0; i < rangeList.size(); i++)
			((JDFIntegerRange) rangeList.elementAt(i)).setDef(xdef);
	}

	/**
	 * getDef - gets xDef, the default value which is used for negative numbers
	 * 
	 * @return int - one above the value that -1 will represent in this range<br>
	 *         i.e. the value that -0, were it possible to specify, would represent
	 */
	public int getDef()
	{
		return m_xDef;
	}

	/**
	 * isOrdered - tests if 'this' is OrderedRangeList
	 * 
	 * @return boolean - true if 'this' is a OrdneredRangeList
	 */
	@Override
	public boolean isOrdered()
	{
		int siz = rangeList.size();
		if (siz == 0)
			return false; // attempt to operate on a null element

		Vector<Integer> v = new Vector<Integer>(); // vector of ranges
		for (int i = 0; i < siz; i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(i);
			v.addElement(new Integer(r.getLeft()));
			if (r.getLeft() != r.getRight())
			{
				v.addElement(new Integer(r.getRight()));
			}
		}

		int n = v.size() - 1;
		if (n == 0)
			return true; // single value

		int first = (v.elementAt(0)).intValue();
		int last = (v.elementAt(n)).intValue();

		for (int j = 0; j < n; j++)
		{
			int value = (v.elementAt(j)).intValue();
			int nextvalue = (v.elementAt(j + 1)).intValue();

			if (((first == last && value == nextvalue) || (first < last && value <= nextvalue) || (first > last && value >= nextvalue)) == false)
				return false;
		}
		return true;
	}

	/**
	 * isUniqueOrdered - tests if 'this' is UniqueOrdered RangeList
	 * 
	 * @return boolean - true if 'this' is UniqueOrdered RangeList
	 */
	@Override
	public boolean isUniqueOrdered()
	{

		int siz = rangeList.size();
		if (siz == 0)
		{
			return false; // attempt to operate on a null element
		}

		Vector<Integer> v = new Vector<Integer>(); // vector of ranges
		for (int i = 0; i < siz; i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) rangeList.elementAt(i);
			v.addElement(new Integer(r.getLeft()));
			if (!new Integer(r.getLeft()).equals(new Integer(r.getRight())))
			{
				v.addElement(new Integer(r.getRight()));
			}
		}

		int n = v.size() - 1;
		if (n == 0)
		{
			return true; // single value
		}
		int first = (v.elementAt(0)).intValue();
		int last = (v.elementAt(n)).intValue();

		if (first == last)
		{
			return false;
		}
		for (int j = 0; j < n; j++)
		{
			int value = (v.elementAt(j)).intValue();
			int nextvalue = (v.elementAt(j + 1)).intValue();

			if (((first < last && value < nextvalue) || (first > last && value < nextvalue)) == false)
				return false;
		}
		return true;
	}

	/**
	 * deepCopy - a deep copy of this JDFIntegerRangeList
	 * 
	 * @return JDFIntegerRangeList - this object
	 */
	public JDFIntegerRangeList deepCopy() throws DataFormatException
	{
		JDFIntegerRangeList rl = new JDFIntegerRangeList();
		rl.setString(toString());
		return rl;
	}

	/**
	 * isOverlapping
	 * 
	 * @param newRange the range to check, if is overlapping one of the ranges in the list
	 *            <p>
	 *            x: x.upper < y.lower,<br>
	 *            z: z.lower > y.upper,<br>
	 *            one of these situation, means there is no overlapping
	 * 
	 * @param newRange
	 * @param oldRange the JDFRangeList removed from the RangeList, before check for overlap. If null, the oldRange is
	 *            ignored
	 * 
	 * @return boolean - true if there is an overlapping, otherwise false
	 */
	public boolean isOverlapping(JDFIntegerRange newRange, JDFIntegerRange oldRange)
	{
		Vector<JDFRange> rangeListToCheck = rangeList;

		if (oldRange != null)
		{
			rangeListToCheck = (Vector<JDFRange>) rangeList.clone();
			rangeListToCheck.remove(oldRange);
		}

		return checkOverlap(rangeListToCheck, newRange);
	}

	/**
	 * checkOverlap - checks if the newRange overlaps one of the JDFIntegerRanges in the rangeList
	 * 
	 * @param rangeList the JDFIntegerRanges to check for overlap
	 * @param newRange the JDFIntergeRange to check against
	 * 
	 * @return boolean - true if overlapping otherwise false
	 */
	private boolean checkOverlap(Vector<JDFRange> vRangeList, JDFIntegerRange newRange)
	{
		int rangeLower = newRange.getLowerValue();
		int rangeUpper = newRange.getUpperValue();

		for (int i = 0; i < vRangeList.size(); i++)
		{
			JDFIntegerRange r = (JDFIntegerRange) vRangeList.elementAt(i);

			if (((rangeUpper < r.getLowerValue()) || (rangeLower > r.getUpperValue())) == false)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * normalize this range by removing any consecutive entries and creating ranges instead
	 * 
	 * @param bSort if true, sort the rangelist prior to normalizing
	 * 
	 */
	public void normalize(boolean bSort)
	{
		int[] l = getIntegerList().getIntArray();
		if (bSort)
			Arrays.sort(l);

		clear();
		int lSiz = l.length;
		for (int i = 0; i < lSiz; i++)
		{
			append(l[i]);
		}
	}
}
