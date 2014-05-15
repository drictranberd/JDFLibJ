/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2014 The International Cooperation for the Integration of
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

package org.cip4.jdflib.auto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFElement;

/**
*****************************************************************************
class JDFAutoFold : public JDFElement

*****************************************************************************
*/

public abstract class JDFAutoFold extends JDFElement
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.FROM, 0x22222222, AttributeInfo.EnumAttributeType.enumeration, EnumFrom.getEnum(0), null);
		atrInfoTable[1] = new AtrInfoTable(AttributeName.TO, 0x22222222, AttributeInfo.EnumAttributeType.enumeration, EnumTo.getEnum(0), null);
		atrInfoTable[2] = new AtrInfoTable(AttributeName.TRAVEL, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[3] = new AtrInfoTable(AttributeName.RELATIVETRAVEL, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	/**
	 * Constructor for JDFAutoFold
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoFold(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoFold
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoFold(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoFold
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoFold(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * @return  the string representation
	 */
	@Override
	public String toString()
	{
		return " JDFAutoFold[  --> " + super.toString() + " ]";
	}

	/**
	* Enumeration strings for From
	*/

	@SuppressWarnings("rawtypes")
	public static class EnumFrom extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumFrom(String name)
		{
			super(name, m_startValue++);
		}

		/**
		 * @param enumName the string to convert
		 * @return the enum
		 */
		public static EnumFrom getEnum(String enumName)
		{
			return (EnumFrom) getEnum(EnumFrom.class, enumName);
		}

		/**
		 * @param enumValue the integer to convert
		 * @return the enum
		 */
		public static EnumFrom getEnum(int enumValue)
		{
			return (EnumFrom) getEnum(EnumFrom.class, enumValue);
		}

		/**
		 * @return the map of enums
		 */
		public static Map getEnumMap()
		{
			return getEnumMap(EnumFrom.class);
		}

		/**
		 * @return the list of enums
		 */
		public static List getEnumList()
		{
			return getEnumList(EnumFrom.class);
		}

		/**
		 * @return the iterator
		 */
		public static Iterator iterator()
		{
			return iterator(EnumFrom.class);
		}

		/**  */
		public static final EnumFrom Front = new EnumFrom("Front");
		/**  */
		public static final EnumFrom Left = new EnumFrom("Left");
	}

	/**
	* Enumeration strings for To
	*/

	@SuppressWarnings("rawtypes")
	public static class EnumTo extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumTo(String name)
		{
			super(name, m_startValue++);
		}

		/**
		 * @param enumName the string to convert
		 * @return the enum
		 */
		public static EnumTo getEnum(String enumName)
		{
			return (EnumTo) getEnum(EnumTo.class, enumName);
		}

		/**
		 * @param enumValue the integer to convert
		 * @return the enum
		 */
		public static EnumTo getEnum(int enumValue)
		{
			return (EnumTo) getEnum(EnumTo.class, enumValue);
		}

		/**
		 * @return the map of enums
		 */
		public static Map getEnumMap()
		{
			return getEnumMap(EnumTo.class);
		}

		/**
		 * @return the list of enums
		 */
		public static List getEnumList()
		{
			return getEnumList(EnumTo.class);
		}

		/**
		 * @return the iterator
		 */
		public static Iterator iterator()
		{
			return iterator(EnumTo.class);
		}

		/**  */
		public static final EnumTo Up = new EnumTo("Up");
		/**  */
		public static final EnumTo Down = new EnumTo("Down");
	}

	/* ************************************************************************
	 * Attribute getter / setter
	 * ************************************************************************
	 */

	/* ---------------------------------------------------------------------
	Methods for Attribute From
	--------------------------------------------------------------------- */
	/**
	  * (5) set attribute From
	  * @param enumVar the enumVar to set the attribute to
	  */
	public void setFrom(EnumFrom enumVar)
	{
		setAttribute(AttributeName.FROM, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	  * (9) get attribute From
	  * @return the value of the attribute
	  */
	public EnumFrom getFrom()
	{
		return EnumFrom.getEnum(getAttribute(AttributeName.FROM, null, null));
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute To
	--------------------------------------------------------------------- */
	/**
	  * (5) set attribute To
	  * @param enumVar the enumVar to set the attribute to
	  */
	public void setTo(EnumTo enumVar)
	{
		setAttribute(AttributeName.TO, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	  * (9) get attribute To
	  * @return the value of the attribute
	  */
	public EnumTo getTo()
	{
		return EnumTo.getEnum(getAttribute(AttributeName.TO, null, null));
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute Travel
	--------------------------------------------------------------------- */
	/**
	  * (36) set attribute Travel
	  * @param value the value to set the attribute to
	  */
	public void setTravel(double value)
	{
		setAttribute(AttributeName.TRAVEL, value, null);
	}

	/**
	  * (17) get double attribute Travel
	  * @return double the value of the attribute
	  */
	public double getTravel()
	{
		return getRealAttribute(AttributeName.TRAVEL, null, 0.0);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute RelativeTravel
	--------------------------------------------------------------------- */
	/**
	  * (36) set attribute RelativeTravel
	  * @param value the value to set the attribute to
	  */
	public void setRelativeTravel(double value)
	{
		setAttribute(AttributeName.RELATIVETRAVEL, value, null);
	}

	/**
	  * (17) get double attribute RelativeTravel
	  * @return double the value of the attribute
	  */
	public double getRelativeTravel()
	{
		return getRealAttribute(AttributeName.RELATIVETRAVEL, null, 0.0);
	}

}// end namespace JDF
