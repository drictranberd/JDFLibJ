/*
 *
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
 * ==========================================================================
 * class JDFDate extends Date
 * 
 * JDFDate additionally stores the timezone offset of the original date, 
 * so that after mDate = new JDFDate("1999-09-26T11:43:10+03:00") the following
 * equation holds: mDate.dateTimeISO() == "1999-09-26T11:43:10+03:00"
 * independent of the default timezone
 * ==========================================================================
 * COPYRIGHT Heidelberger Druckmaschinen AG, 1999-2003. ALL RIGHTS RESERVED 
 **/

package org.cip4.jdflib.util;

import java.util.zip.DataFormatException;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG
 * 
 * Aug 10, 2009
 */
public class JDFDuration implements Comparable<JDFDuration>
{
	private static final long serialVersionUID = 1L;

	private double m_lDuration = 0; // in seconds

	// private static final String REGEX_DURATION_RESTRICTED is a
	// RegularExpression
	// for a validation of incoming duration Strings, where is important that
	// values of seconds, minutes do not exceed 59, hours do not exceed 23...
	// It's a restricted form of a REGEX_DURATION
	//
	// Formatted string looks like "PyYmMdDThHmMsS"
	//
	// date Part "yYmMdD"
	// year (0?[0-9]|[1-9][0-9]) -- 0(00) - 99 are valid years
	// month (0?[0-9]|1[01]) -- 0(00) - 11 are valid months
	// day (0?[0-9]|[12][0-9]|3[0]) -- 0(00) - 30 are valid days
	// 
	// time Part "hHmMsS"
	// hour (0?[0-9]|1[0-9]|2[0123]|) -- 0(00) - 23 are valid hours
	// min (0?[0-9]|[1-5][0-9]) -- 0(00) - 59 are valid seconds
	// sec (0?[0-9]|[1-5][0-9]) -- 0(00) - 59 are valid minutes
	// regual expressions to validate incoming duration Strings

	// private static final String REGEX_DURATION_RESTRICTED =
	// "[P]((0?[0-9]|[1-9][0-9])[Y])?((0?[0-9]|1[01])[M])?((0?[0-9]|[12][0-9]|3[0])[D])?"
	// +
	// "([T]((0?[0-9]|1[0-9]|2[0123])[H])?((0?[0-9]|[1-5][0-9])[M])?((0?[0-9]|[1-5][0-9])[S])?)?"
	// ;

	// private static final String REGEX_DURATION is a RegularExpression
	// for a validation of incoming duration Strings
	// Formatted string looks like "PyYmMdDThHmMsS"
	// y,m,d,h,m,s are any int values.
	// E.g. expressions "P60D" that is equal 60 days or "PT68H" that is equal
	// 68hours are allowed

	private static final String REGEX_DURATION = "([-])?[P](((\\d)+)[Y])?((\\d)+[M])?((\\d)+[D])?" + "([T]((\\d)+[H])?((\\d)+[M])?((\\d)+([.](\\d)+)?[S])?)?";

	/**
	 * Allocates a <code>JDFDuration</code> object and initializes it with 0
	 */
	public JDFDuration()
	{
		m_lDuration = 0;
	}

	/**
	 * Makes a copy of the<code>JDFDuration</code> object 'd'
	 * @param d the duration
	 */
	public JDFDuration(final JDFDuration d)
	{
		m_lDuration = d.m_lDuration;
	}

	/**
	 * creates a duration from two dates; may be negative if start later end
	 * 
	 * @param start the starting point
	 * @param end the end point
	 * 
	 */
	public JDFDuration(final JDFDate start, final JDFDate end)
	{
		if (start == null || end == null)
		{
			return;
		}
		m_lDuration = (end.getTimeInMillis() - start.getTimeInMillis()) / 1000.;
	}

	/**
	 * Allocates a <code>JDFDuration</code> object and initializes it with 's'
	 * 
	 * @param s duration in seconds s may be fractional
	 */
	public JDFDuration(final double s)
	{
		m_lDuration = s;
	}

	/**
	 * Allocates a <code>JDFDuration</code> object and initializes it with 's'
	 * 
	 * @param s duration in seconds s may be fractional
	 */
	public JDFDuration(final int s)
	{
		m_lDuration = s;
	}

	/**
	 * Allocates a <code>JDFDuration</code> object and initializes it with a value of <code>strDuration</code>, represented as a formatted duration string. <br>
	 * Duration examples: <li>"P1Y2M3DT10H30M"</li> <li>"P8MT12M"</li> Durations with overflows, e.g. P13M (13 Months) are also handled and correctly output, in
	 * this case P1Y1M
	 * 
	 * @param strDuration - formatted duration
	 * @throws DataFormatException if strDuration is not a valid string representation of JDFDuration
	 */
	public JDFDuration(final String strDuration) throws DataFormatException
	{
		init(strDuration);
	}

	/**
	 * add seconds to a duration
	 * 
	 * @param seconds number of seconds to add
	 * @return the new duration in seconds
	 * 
	 */
	public double addSeconds(final double seconds)
	{
		m_lDuration += seconds;
		return m_lDuration;
	}

	/**
	 * for debug purposes
	 * 
	 * @return Object informations
	 */
	@Override
	public String toString()
	{
		return "JDFDuration[ m_lDuration=(" + m_lDuration + ")  --> " + super.toString() + " ]";
	}

	/**
	 * Method init handles Strings of type: <br>
	 * <li>"P1Y2M3DT10H30M"</li> <li>"PM8T12M"</li> <li>"PT30M"</li> <li>
	 * "PT30M40S"</li> <li>"PT30M40.3333S"</li>
	 * 
	 * @param strDuration
	 * @throws DataFormatException
	 */
	private void init(final String strDuration) throws DataFormatException
	{
		String strDurationLocal = strDuration;

		strDurationLocal = StringUtil.zappTokenWS(strDurationLocal, " ");

		boolean bComplete = strDurationLocal.matches(REGEX_DURATION);
		m_lDuration = 0;

		if (bComplete)
		{
			bComplete = setDurationISO(strDurationLocal);
		}

		if (!bComplete)
		{
			// its not a DateTime nor a Duration
			throw new DataFormatException("JDFDuration.init: invalid duration String " + strDurationLocal);
		}
	}

	/**
	 * Format and return the duration set by 'setDuration(int i)' or 'setDurationString(String a_aDuration)' as an ISO conforming String.<br>
	 * For example: 'P1Y2M3DT10H30M'
	 * 
	 * @return String - the duration formatted as an ISO 8601 conforming String if duration is '0' return value is 'PT00M'
	 */
	public String getDurationISO()
	{
		if (m_lDuration == 0)
		{
			return "PT00M";
		}

		int temp = Math.abs((int) m_lDuration);
		final double abs = Math.abs(m_lDuration);
		final StringBuffer iso = new StringBuffer(32);
		if (m_lDuration < 0)
		{
			iso.append("-");
		}
		iso.append("P"); // P is the indicator that 'iso' is a duration

		int i = (int) abs / (60 * 60 * 24 * 365);
		if (i != 0)
		{
			iso.append(i).append("Y"); // string with years
			temp = (int) abs - (i * 60 * 60 * 24 * 365);
		}
		i = temp;
		i = i / (60 * 60 * 24 * 30);
		if (i != 0)
		{
			iso.append(i).append("M"); // string with months
			temp = temp - (i * 60 * 60 * 24 * 30);
		}
		i = temp % (60 * 60 * 24 * 30);
		i = i / (60 * 60 * 24);
		if (i != 0)
		{
			iso.append(i).append("D"); // string with days
		}
		iso.append("T");

		i = (int) abs % (60 * 60 * 24);
		i = i / (60 * 60);
		if (i != 0)
		{
			iso.append(i).append("H"); // string with hours
		}
		i = (int) abs % (60 * 60);
		i = i / (60);
		if (i != 0)
		{
			iso.append(i).append("M"); // string with minutes
		}
		i = (int) abs % (60);
		boolean bSec = false;
		if (i != 0)
		{
			iso.append(i); // string with seconds
			bSec = true;
		}
		final double deltaS = abs - ((int) (abs));
		if (deltaS > 0)
		{

			final String s = StringUtil.formatDouble(deltaS);
			if (!bSec)
			{
				iso.append("0"); // add missing 0
			}
			iso.append(s.substring(1));
			bSec = true;
		}
		if (bSec)
		{
			iso.append("S");
		}

		final int lastIndex = iso.length() - 1;
		if (iso.charAt(lastIndex) == 'T')
		{
			iso.deleteCharAt(lastIndex);
		}

		return iso.toString();
	}

	/**
	 * Set a duration. Durations are not bound to time or date and can be set independently
	 * 
	 * @return true - the duration was set<br>
	 * false - the duration was not set, because a NumberFormatException was thrown (-> parseInt())
	 * 
	 * @param a_aDuration formatted duration string 'P1Y2M3DT10H30M'
	 */
	public boolean setDurationISO(final String a_aDuration)
	{
		boolean result = true;

		String strDate = null;
		String strTime = null;
		int iYears = 0;
		int iMonths = 0;
		int iDays = 0;
		int iHours = 0;
		int iMinutes = 0;
		int iSeconds = 0;
		int iduration = 0;
		int iTimeLastPos = 0;
		int iDateLastPos = 0;
		int factor = 1; // the factor for negative durations

		int iPPos = a_aDuration.indexOf("P");
		if (iPPos > 0) // check for negative duration
		{
			final char c = a_aDuration.charAt(iPPos - 1);
			if (c == '-')
			{
				factor = -1;
			}
		}

		final String strPeriod = a_aDuration.substring(++iPPos, a_aDuration.length());

		// devide periodInstant into date and time part, which are separated by
		// 'T'
		int iTPos = strPeriod.indexOf("T");

		if (iTPos >= 0)
		{
			if (iTPos == 0)
			{ // e.g. if durationInstant looks like "PT10H30M" - without date
				// part
				strTime = strPeriod.substring(1, strPeriod.length());
			}
			else
			{ // e.g. if durationInstant looks like "P1Y2M3DT10H30M"
				strDate = strPeriod.substring(0, iTPos);
				strTime = strPeriod.substring(++iTPos, strPeriod.length());
			}
		}
		else
		{ // e.g. if durationInstant looks like "P1Y2M3D" - without time part
			strDate = strPeriod;
		}
		double fracSecs = 0;
		try
		{
			if (strDate != null)
			{
				int iYPos = strDate.indexOf("Y");
				if (iYPos > 0)
				{
					iYears = Integer.parseInt(strDate.substring(0, iYPos));
					iduration += iYears * 365 * 24 * 60 * 60;
					iDateLastPos = ++iYPos;
				}

				int iMPos = strDate.indexOf("M");
				if (iMPos > 0)
				{
					iMonths = Integer.parseInt(strDate.substring(iDateLastPos, iMPos));
					final int nYears = iMonths / 12;
					iduration += (iMonths * 30 + nYears * 5) * 24 * 60 * 60; // add
					// 5
					// days
					// for
					// each
					// complete
					// year
					// (
					// 360
					// --
					// >
					// 365
					// )
					iDateLastPos = ++iMPos;
				}

				final int iDPos = strDate.indexOf("D");
				if (iDPos > 0)
				{
					iDays = Integer.parseInt(strDate.substring(iDateLastPos, iDPos));
					iduration += iDays * 24 * 60 * 60;
				}
			}

			if (strTime != null)
			{
				int iHPos = strTime.indexOf("H");
				if (iHPos > 0)
				{
					iHours = Integer.parseInt(strTime.substring(0, iHPos));
					iduration += iHours * 60 * 60;
					iTimeLastPos = ++iHPos;
				}
				int iMPos = strTime.indexOf("M");
				if (iMPos > 0)
				{
					iMinutes = Integer.parseInt(strTime.substring(iTimeLastPos, iMPos));
					iduration += iMinutes * 60;
					iTimeLastPos = ++iMPos;
				}

				final int iSPos = strTime.indexOf("S");
				if (iSPos > 0)
				{
					int iDotPos = strTime.indexOf(".");
					if (iDotPos > 0)
					{
						iSeconds = Integer.parseInt(strTime.substring(iTimeLastPos, iDotPos));
						iDotPos++;
						final int mLen = iSPos - iDotPos;
						if (mLen > 0)
						{
							final String sMilli = "0." + strTime.substring(iDotPos, iSPos);
							fracSecs = Double.parseDouble(sMilli);
						}
						iduration += iSeconds;

					}
					else
					{
						iSeconds = Integer.parseInt(strTime.substring(iTimeLastPos, iSPos));
						iduration += iSeconds;
					}
				}
			}

			m_lDuration = iduration;
			if (fracSecs != 0)
			{
				m_lDuration += fracSecs;
			}
			m_lDuration *= factor;
		}
		catch (final NumberFormatException e)
		{
			result = false;
		}
		return result;
	}

	/**
	 * setDuration: sets a duration for <code>this</code> in seconds. This duration is used in multiple classes of the JDF (e.g. Heating time).
	 * 
	 * @param seconds the duration in seconds.
	 */
	public void setDuration(final int seconds)
	{
		m_lDuration = seconds;
	}

	/**
	 * setDuration: sets a duration for <code>this</code> in seconds, including fractions. This duration is used in multiple classes of the JDF (e.g. Heating
	 * time).
	 * 
	 * @param seconds the duration in seconds.
	 */
	public void setDuration(final double seconds)
	{
		m_lDuration = seconds;
	}

	/**
	 * the duration in seconds
	 * 
	 * @return int - the duration in seconds; '0' default
	 */
	public int getDuration()
	{
		return (int) m_lDuration;
	}

	/**
	 * isLess - tests if the duration of this JDFDuration is longer than the duration of the specified JDFDuration. Compares the integer durations, thus -PT15S
	 * is shorter than -PT5S
	 * 
	 * @param x the JDFDuration object to compare to <code>this</code>
	 * @return boolean - true if the duration of this JDFDuration is longer than the duration of the JDFDuration 'x'.
	 */
	public boolean isLonger(final JDFDuration x)
	{
		return this.getDuration() > x.getDuration();
	}

	/**
	 * isShorter - tests if the duration of this JDFDuration is less than the duration of the specified JDFDuration. Compares the integer durations, thus -PT15S
	 * is shorter than -PT5S
	 * 
	 * @param x the JDFDuration object to compare to <code>this</code>
	 * @return boolean - true if the duration of this JDFDuration is shorter than the duration of the JDFDuration 'x'.
	 */
	public boolean isShorter(final JDFDuration x)
	{
		return this.getDuration() < x.getDuration();
	}

	/**
	 * Compares two JDFDuration objects for equality.<br>
	 * The result is <code>true</code> if and only if the argument is not <code>null</code> and is a <code>JDFDuration</code> object that represents the same
	 * duration, as this object.
	 * <p>
	 */
	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (!(other instanceof JDFDuration))
		{
			return false;
		}

		return (this.m_lDuration == ((JDFDuration) other).m_lDuration);
	}

	/**
	 * hashCode: complements equals() to fulfill the equals/hashCode contract
	 */
	@Override
	public int hashCode()
	{
		return (int) (m_lDuration * 1000);
	}

	/**
	 * 
	 * @param arg0
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final JDFDuration arg0)
	{
		double l = (arg0 == null) ? 0 : arg0.m_lDuration;
		l -= m_lDuration;
		return (int) Math.signum(-l);
	}

}