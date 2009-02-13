/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFTransferFunction.java
 *
 * Last changes
 *
 */
package org.cip4.jdflib.datatypes;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.DataFormatException;

import org.cip4.jdflib.core.JDFConstants;

/**
 * This class is a representation of a whitespace separated list of numbers representing a set of XY coordinates of a transfer function. The total number of x y
 * values must be even because of the pairs.
 */
public class JDFTransferFunction extends JDFNumList
{
	// **************************************** Constructors
	// ****************************************
	/**
	 * constructs a xy pair with all values set to 0.0 Double
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFTransferFunction() throws DataFormatException
	{
		super(JDFConstants.EMPTYSTRING);
	}

	/**
	 * constructs a number list with the given string the number of tokens must be even
	 * 
	 * @param s the given String in number list format
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFTransferFunction(final String s) throws DataFormatException
	{
		super(s);
	}

	/**
	 * constructs a number list with the given vector the number of elements must be even
	 * 
	 * @param v the number list as a vector
	 * 
	 * @throws DataFormatException - if the Vector has not a valid format
	 */
	public JDFTransferFunction(final Vector v) throws DataFormatException
	{
		super(v);
	}

	/**
	 * constructs a number list with the given number list
	 * 
	 * @param nl the given number list
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFTransferFunction(final JDFNumList nl) throws DataFormatException
	{
		super(nl.toString());
	}

	/**
	 * copy constructor<br>
	 * constructs a number list with the given transfer function
	 * 
	 * @param tf the given number list
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFTransferFunction(final JDFTransferFunction tf) throws DataFormatException
	{
		super(tf);
	}

	// **************************************** Methods
	// *********************************************
	/**
	 * isValid - true if the size of the vector is even and all instances are Double types
	 * 
	 * @throws DataFormatException - if the Vector has not a valid format
	 */
	@Override
	public boolean isValid() throws DataFormatException
	{
		if ((m_numList.size() % 2) != 0)
		{
			throw new DataFormatException("Data format exception!");
		}

		for (int i = 0; i < m_numList.size(); i++)
		{
			if (!(m_numList.elementAt(i) instanceof Double))
			{
				throw new DataFormatException("Data format exception!");
			}
		}
		return true;
	}

	/**
	 * add - adds a xy coordinate to the vector
	 * 
	 * @param xy the xy coordinate to add
	 */
	public void add(final JDFXYPair xy)
	{
		m_numList.add(new Double(xy.getX()));
		m_numList.add(new Double(xy.getY()));
	}

	/**
	 * add - adds a x and a y coordinate to the vector
	 * 
	 * @param x the x coordinate to add
	 * @param y the y coordinate to add
	 */
	public void add(final Double x, final Double y)
	{
		m_numList.add(x);
		m_numList.add(y);
	}

	/**
	 * add - adds a x and a y coordinate to the vector
	 * 
	 * @param x the x coordinate to add
	 * @param y the y coordinate to add
	 */
	public void add(final double x, final double y)
	{
		m_numList.add(new Double(x));
		m_numList.add(new Double(y));
	}

	/**
	 * add - adds a x and a y coordinate to the vector
	 * 
	 * @param s a string with the x and y coordinate to add
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public void add(final String s) throws DataFormatException
	{
		final StringTokenizer sToken = new StringTokenizer(s, JDFConstants.BLANK);

		if ((sToken.countTokens() % 2) != 0)
		{
			throw new DataFormatException("Data format exception!");
		}

		while (sToken.hasMoreTokens())
		{
			final String t = sToken.nextToken().trim();

			try
			{
				m_numList.addElement(new Double(t));
			}
			catch (final NumberFormatException e)
			{
				throw new DataFormatException("Data format exception!");
			}
		}
	}

	/**
	 * add - adds a complete transfer function to the vector
	 * 
	 * @param tf the given transfer function to add
	 */
	public void add(final JDFTransferFunction tf)
	{
		m_numList.addAll(tf.copyNumList());
	}
}