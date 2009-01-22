package org.cip4.jdflib.util;

/**
 * trivial typesafe pair class
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * 11.12.2008
 * @param <aData> datatype of a
 * @param <bData> datatype of b
 */
public class MyPair<aData, bData>
{

	/**
	 * @param ap aData value
	 * @param bp bData value
	 */
	public MyPair(final aData ap, final bData bp)
	{
		super();
		this.a = ap;
		this.b = bp;
	}

	/**
	 * the aData value
	 */
	public aData a;
	/**
	 * the bData value
	 */
	public bData b;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Pair" + a + "," + b;
	}
}