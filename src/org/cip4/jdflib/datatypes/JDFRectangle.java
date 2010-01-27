/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFRectangle.java
 *
 * Last changes
 *
 */
package org.cip4.jdflib.datatypes;

import java.util.Vector;
import java.util.zip.DataFormatException;

import org.cip4.jdflib.util.HashUtil;
import org.cip4.jdflib.util.ScaleUtil;

/**
 * This class represents a rectangle JDFRectangle) consisting of a lower left x value (llx), a lower left y value (lly), an upper right x value (urx) and an
 * upper right y value (ury) all values are Double types.
 */
public class JDFRectangle extends JDFNumList
{
	// **************************************** Constructors
	// ****************************************
	/**
	 * constructs a rectangle with all 4 values set to 0.0 Double
	 */
	public JDFRectangle()
	{
		super(MAX_RECTANGLE_DIMENSION);
	}

	/**
	 * constructs a rectangle with all values set via a Vector of Double objects
	 * 
	 * @param v the given Vector with MAX_RECTANGLE_DIMENSION objects of type Double
	 * 
	 * @throws DataFormatException - if the Vector has not a valid format
	 */
	public JDFRectangle(final Vector v) throws DataFormatException
	{
		super(v);
	}

	/**
	 * constructs a rectangle with all values set via a String
	 * 
	 * @param s the given String, blank separated numbers
	 * 
	 * @throws DataFormatException - if the String has not a valid format
	 */
	public JDFRectangle(final String s) throws DataFormatException
	{
		super(s);
	}

	/**
	 * constructs a rectangle with all values set via a JDFRectangle
	 * 
	 * @param rec the given rectangle
	 */
	public JDFRectangle(final JDFRectangle rec)
	{
		super(MAX_RECTANGLE_DIMENSION);
		setLlx(rec.getLlx());
		setLly(rec.getLly());
		setUrx(rec.getUrx());
		setUry(rec.getUry());
	}

	/**
	 * constructs a rectangle with all values set via a JDFNumberList
	 * 
	 * @param nl the given number list
	 * 
	 * @throws DataFormatException - if the JDFNumberList has not a valid format
	 */
	public JDFRectangle(final JDFNumberList nl) throws DataFormatException
	{
		super(MAX_RECTANGLE_DIMENSION);
		if (nl.size() != MAX_RECTANGLE_DIMENSION)
		{
			throw new DataFormatException("JDFRectangle: can't construct JDFRectangle from this JDFNuberList value");
		}
		m_numList.set(0, nl.m_numList.get(0));
		m_numList.set(1, nl.m_numList.get(1));
		m_numList.set(2, nl.m_numList.get(2));
		m_numList.set(3, nl.m_numList.get(3));
	}

	/**
	 * constructs a new JDFRectangle with the given double values
	 * 
	 * @param llx lower left x coordinate
	 * @param lly lower left y coordinate
	 * @param urx lower left x coordinate
	 * @param ury lower left y coordinate
	 */
	public JDFRectangle(final double llx, final double lly, final double urx, final double ury)
	{
		super(MAX_RECTANGLE_DIMENSION);
		setLlx(llx);
		setLly(lly);
		setUrx(urx);
		setUry(ury);
	}

	// **************************************** Methods
	// *********************************************
	/**
	 * isValid - true if the size of the vector is 4 and all instances are Double types
	 * 
	 * @throws DataFormatException - if the Vector has not a valid format
	 */
	@Override
	public boolean isValid() throws DataFormatException
	{
		if (m_numList.size() != MAX_RECTANGLE_DIMENSION)
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
	 * hashCode complements equals() to fulfill the equals/hashCode contract
	 * 
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		return HashUtil.hashCode(super.hashCode(), this.toString());
	}

	/**
	 * getLlx - returns the lower left x coordinate
	 * 
	 * @return double - the lower left x coordinate
	 */
	public double getLlx()
	{
		return ((Double) m_numList.elementAt(0)).doubleValue();
	}

	/**
	 * setLlx - sets the lower left x coordinate
	 * 
	 * @param x the lower left x coordinate
	 */
	public void setLlx(final double x)
	{
		m_numList.set(0, new Double(x));
	}

	/**
	 * setLlXMm - sets the lower left x coordinate in millimeter
	 * 
	 * @param mmX the lower left x coordinate in millimeter
	 * @author Stefan Meissner
	 */
	public void setLlxMm(final double mmX) {
		setLlx(ScaleUtil.mm2Dtp(mmX));
	}

	/**
	 * getLly - returns the lower left y coordinate
	 * 
	 * @return double - the lower left y coordinate
	 */
	public double getLly()
	{
		return ((Double) m_numList.elementAt(1)).doubleValue();
	}

	/**
	 * setLly - sets the lower left y coordinate
	 * 
	 * @param y the lower left y coordinate
	 */
	public void setLly(final double y)
	{
		m_numList.set(1, new Double(y));
	}

	/**
	 * setLlyMm - sets the lower left y coordinate in millimeter
	 * 
	 * @param mmY the lower left y coordinate in millimeter
	 * @author Stefan Meissner
	 */
	public void setLlyMm(final double mmY) {
		setLly(ScaleUtil.mm2Dtp(mmY));
	}

	/**
	 * getUrx - returns the upper right x coordinate
	 * 
	 * @return double - the upper right x coordinate
	 */
	public double getUrx()
	{
		return ((Double) m_numList.elementAt(2)).doubleValue();
	}

	/**
	 * setUrx - sets the upper right x coordinate
	 * 
	 * @param x the upper right x coordinate
	 */
	public void setUrx(final double x)
	{
		m_numList.set(2, new Double(x));
	}

	/**
	 * setUrxMm - sets the upper right x coordinate in millimeter
	 * 
	 * @param mmX the upper right x coordinate in millimeter
	 * @author Stefan Meissner
	 */
	public void setUrxMm(final double mmX) {
		setUrx(ScaleUtil.mm2Dtp(mmX));
	}

	/**
	 * getUry - returns the upper right y coordinate
	 * 
	 * @return double - the upper right y coordinate
	 */
	public double getUry()
	{
		return ((Double) m_numList.elementAt(3)).doubleValue();
	}

	/**
	 * setUry - sets the upper right y coordinate
	 * 
	 * @param y the upper right y coordinate
	 */
	public void setUry(final double y)
	{
		m_numList.set(3, new Double(y));
	}

	/**
	 * setUryMm - sets the upper right y coordinate in millimeter
	 * 
	 * @param mmY the upper right y coordinate in millimeter
	 * @author Stefan Meissner
	 */
	public void setUryMm(final double mmY) {
		setUry(ScaleUtil.mm2Dtp(mmY));
	}

	/**
	 * getWidth - returns the width of the rectangle, the difference between upper right x value and lower left x value as an absolute value
	 * 
	 * @return double - the width of the rectangle
	 */
	public double getWidth()
	{
		return Math.abs(((Double) m_numList.elementAt(2)).doubleValue() - ((Double) m_numList.elementAt(0)).doubleValue());
	}

	/**
	 * getHeight - returns the height of the rectangle, the difference between upper right y value and lower left y value as an absolute value
	 * 
	 * @return double - the height of the rectangle
	 */
	public double getHeight()
	{
		return Math.abs(((Double) m_numList.elementAt(3)).doubleValue() - ((Double) m_numList.elementAt(1)).doubleValue());
	}

	/**
	 * isGreater - equality operator >
	 * 
	 * @param r the JDFRectangle object to compare to
	 * @return boolean - true if <code>this</this> > r
	 */
	public boolean isGreater(final JDFRectangle r)
	{
		return (!equals(r) && (getLlx() <= r.getLlx()) && (getLly() <= r.getLly()) && (getUrx() >= r.getUrx()) && (getUry() >= r.getUry()));
	}

	/**
	 * isGreaterOrEqual - equality operator >=
	 * 
	 * @param r the JDFRectangle object to compare to
	 * @return boolean - true if <code>this</this> >= r
	 */
	public boolean isGreaterOrEqual(final JDFRectangle r)
	{
		return (getLlx() <= r.getLlx()) && (getLly() <= r.getLly()) && (getUrx() >= r.getUrx()) && (getUry() >= r.getUry());
	}

	/**
	 * isLess - equality operator <
	 * 
	 * @param r the JDFRectangle object to compare to
	 * @return boolean - true if <code>this</this> < r
	 */
	public boolean isLess(final JDFRectangle r)
	{
		return (!equals(r) && (getLlx() >= r.getLlx()) && (getLly() >= r.getLly()) && (getUrx() <= r.getUrx()) && (getUry() <= r.getUry()));
	}

	/**
	 * isLessOrEqual - equality operator <=
	 * 
	 * @param r the JDFRectangle object to compare to
	 * @return boolean - true if <code>this</this> <= r
	 */
	public boolean isLessOrEqual(final JDFRectangle r)
	{
		return (getLlx() >= r.getLlx()) && (getLly() >= r.getLly()) && (getUrx() <= r.getUrx()) && (getUry() <= r.getUry());
	}
}