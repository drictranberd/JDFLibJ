/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2020 The International Cooperation for the Integration of
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
import java.util.Vector;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFNumberRange;

/**
 *****************************************************************************
 * class JDFAutoScreenSelector : public JDFElement
 *****************************************************************************
 *
 */

public abstract class JDFAutoScreenSelector extends JDFElement
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[11];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.SEPARATION, 0x33333333, AttributeInfo.EnumAttributeType.string, null, "All");
		atrInfoTable[1] = new AtrInfoTable(AttributeName.ANGLE, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[2] = new AtrInfoTable(AttributeName.ANGLEMAP, 0x33333331, AttributeInfo.EnumAttributeType.string, null, null);
		atrInfoTable[3] = new AtrInfoTable(AttributeName.DOTSIZE, 0x33333331, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[4] = new AtrInfoTable(AttributeName.FREQUENCY, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[5] = new AtrInfoTable(AttributeName.OBJECTTAGS, 0x33331111, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
		atrInfoTable[6] = new AtrInfoTable(AttributeName.SCREENINGFAMILY, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
		atrInfoTable[7] = new AtrInfoTable(AttributeName.SCREENINGTYPE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumScreeningType.getEnum(0), null);
		atrInfoTable[8] = new AtrInfoTable(AttributeName.SOURCEFREQUENCY, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
		atrInfoTable[9] = new AtrInfoTable(AttributeName.SOURCEOBJECTS, 0x33333333, AttributeInfo.EnumAttributeType.enumerations, EnumSourceObjects.getEnum(0), null);
		atrInfoTable[10] = new AtrInfoTable(AttributeName.SPOTFUNCTION, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	/**
	 * Constructor for JDFAutoScreenSelector
	 *
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoScreenSelector(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoScreenSelector
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoScreenSelector(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoScreenSelector
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoScreenSelector(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * Enumeration strings for ScreeningType
	 */

	@SuppressWarnings("rawtypes")
	public static class EnumScreeningType extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumScreeningType(String name)
		{
			super(name, m_startValue++);
		}

		/**
		 * @param enumName the string to convert
		 * @return the enum
		 */
		public static EnumScreeningType getEnum(String enumName)
		{
			return (EnumScreeningType) getEnum(EnumScreeningType.class, enumName);
		}

		/**
		 * @param enumValue the integer to convert
		 * @return the enum
		 */
		public static EnumScreeningType getEnum(int enumValue)
		{
			return (EnumScreeningType) getEnum(EnumScreeningType.class, enumValue);
		}

		/**
		 * @return the map of enums
		 */
		public static Map getEnumMap()
		{
			return getEnumMap(EnumScreeningType.class);
		}

		/**
		 * @return the list of enums
		 */
		public static List getEnumList()
		{
			return getEnumList(EnumScreeningType.class);
		}

		/**
		 * @return the iterator
		 */
		public static Iterator iterator()
		{
			return iterator(EnumScreeningType.class);
		}

		/**  */
		public static final EnumScreeningType AM = new EnumScreeningType("AM");
		/**  */
		public static final EnumScreeningType FM = new EnumScreeningType("FM");
		/**  */
		public static final EnumScreeningType Adaptive = new EnumScreeningType("Adaptive");
		/**  */
		public static final EnumScreeningType ErrorDiffusion = new EnumScreeningType("ErrorDiffusion");
		/**  */
		public static final EnumScreeningType HybridAM_FM = new EnumScreeningType("HybridAM-FM");
		/**  */
		public static final EnumScreeningType HybridAMline_dot = new EnumScreeningType("HybridAMline-dot");
	}

	/**
	 * Enumeration strings for SourceObjects
	 */

	@SuppressWarnings("rawtypes")
	public static class EnumSourceObjects extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumSourceObjects(String name)
		{
			super(name, m_startValue++);
		}

		/**
		 * @param enumName the string to convert
		 * @return the enum
		 */
		public static EnumSourceObjects getEnum(String enumName)
		{
			return (EnumSourceObjects) getEnum(EnumSourceObjects.class, enumName);
		}

		/**
		 * @param enumValue the integer to convert
		 * @return the enum
		 */
		public static EnumSourceObjects getEnum(int enumValue)
		{
			return (EnumSourceObjects) getEnum(EnumSourceObjects.class, enumValue);
		}

		/**
		 * @return the map of enums
		 */
		public static Map getEnumMap()
		{
			return getEnumMap(EnumSourceObjects.class);
		}

		/**
		 * @return the list of enums
		 */
		public static List getEnumList()
		{
			return getEnumList(EnumSourceObjects.class);
		}

		/**
		 * @return the iterator
		 */
		public static Iterator iterator()
		{
			return iterator(EnumSourceObjects.class);
		}

		/**  */
		public static final EnumSourceObjects All = new EnumSourceObjects("All");
		/**  */
		public static final EnumSourceObjects ImagePhotographic = new EnumSourceObjects("ImagePhotographic");
		/**  */
		public static final EnumSourceObjects ImageScreenShot = new EnumSourceObjects("ImageScreenShot");
		/**  */
		public static final EnumSourceObjects LineArt = new EnumSourceObjects("LineArt");
		/**  */
		public static final EnumSourceObjects SmoothShades = new EnumSourceObjects("SmoothShades");
		/**  */
		public static final EnumSourceObjects Text = new EnumSourceObjects("Text");
	}

	/* ************************************************************************
	 * Attribute getter / setter
	 * ************************************************************************
	 */

	/* ---------------------------------------------------------------------
	Methods for Attribute Separation
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute Separation
	 *
	 * @param value the value to set the attribute to
	 */
	public void setSeparation(String value)
	{
		setAttribute(AttributeName.SEPARATION, value, null);
	}

	/**
	 * (23) get String attribute Separation
	 *
	 * @return the value of the attribute
	 */
	public String getSeparation()
	{
		return getAttribute(AttributeName.SEPARATION, null, "All");
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute Angle
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute Angle
	 *
	 * @param value the value to set the attribute to
	 */
	public void setAngle(double value)
	{
		setAttribute(AttributeName.ANGLE, value, null);
	}

	/**
	 * (17) get double attribute Angle
	 *
	 * @return double the value of the attribute
	 */
	public double getAngle()
	{
		return getRealAttribute(AttributeName.ANGLE, null, 0.0);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute AngleMap
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute AngleMap
	 *
	 * @param value the value to set the attribute to
	 */
	public void setAngleMap(String value)
	{
		setAttribute(AttributeName.ANGLEMAP, value, null);
	}

	/**
	 * (23) get String attribute AngleMap
	 *
	 * @return the value of the attribute
	 */
	public String getAngleMap()
	{
		return getAttribute(AttributeName.ANGLEMAP, null, JDFCoreConstants.EMPTYSTRING);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute DotSize
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute DotSize
	 *
	 * @param value the value to set the attribute to
	 */
	public void setDotSize(double value)
	{
		setAttribute(AttributeName.DOTSIZE, value, null);
	}

	/**
	 * (17) get double attribute DotSize
	 *
	 * @return double the value of the attribute
	 */
	public double getDotSize()
	{
		return getRealAttribute(AttributeName.DOTSIZE, null, 0.0);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute Frequency
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute Frequency
	 *
	 * @param value the value to set the attribute to
	 */
	public void setFrequency(double value)
	{
		setAttribute(AttributeName.FREQUENCY, value, null);
	}

	/**
	 * (17) get double attribute Frequency
	 *
	 * @return double the value of the attribute
	 */
	public double getFrequency()
	{
		return getRealAttribute(AttributeName.FREQUENCY, null, 0.0);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute ObjectTags
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute ObjectTags
	 *
	 * @param value the value to set the attribute to
	 */
	public void setObjectTags(VString value)
	{
		setAttribute(AttributeName.OBJECTTAGS, value, null);
	}

	/**
	 * (21) get VString attribute ObjectTags
	 *
	 * @return VString the value of the attribute
	 */
	public VString getObjectTags()
	{
		final VString vStrAttrib = new VString();
		final String s = getAttribute(AttributeName.OBJECTTAGS, null, JDFCoreConstants.EMPTYSTRING);
		vStrAttrib.setAllStrings(s, " ");
		return vStrAttrib;
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute ScreeningFamily
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute ScreeningFamily
	 *
	 * @param value the value to set the attribute to
	 */
	public void setScreeningFamily(String value)
	{
		setAttribute(AttributeName.SCREENINGFAMILY, value, null);
	}

	/**
	 * (23) get String attribute ScreeningFamily
	 *
	 * @return the value of the attribute
	 */
	public String getScreeningFamily()
	{
		return getAttribute(AttributeName.SCREENINGFAMILY, null, JDFCoreConstants.EMPTYSTRING);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute ScreeningType
	--------------------------------------------------------------------- */
	/**
	 * (5) set attribute ScreeningType
	 *
	 * @param enumVar the enumVar to set the attribute to
	 */
	public void setScreeningType(EnumScreeningType enumVar)
	{
		setAttribute(AttributeName.SCREENINGTYPE, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	 * (9) get attribute ScreeningType
	 *
	 * @return the value of the attribute
	 */
	public EnumScreeningType getScreeningType()
	{
		return EnumScreeningType.getEnum(getAttribute(AttributeName.SCREENINGTYPE, null, null));
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute SourceFrequency
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute SourceFrequency
	 *
	 * @param value the value to set the attribute to
	 */
	public void setSourceFrequency(JDFNumberRange value)
	{
		setAttribute(AttributeName.SOURCEFREQUENCY, value, null);
	}

	/**
	 * (20) get JDFNumberRange attribute SourceFrequency
	 *
	 * @return JDFNumberRange the value of the attribute, null if a the attribute value is not a valid to create a JDFNumberRange
	 */
	public JDFNumberRange getSourceFrequency()
	{
		final String strAttrName = getAttribute(AttributeName.SOURCEFREQUENCY, null, null);
		final JDFNumberRange nPlaceHolder = JDFNumberRange.createNumberRange(strAttrName);
		return nPlaceHolder;
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute SourceObjects
	--------------------------------------------------------------------- */
	/**
	 * (5.2) set attribute SourceObjects
	 *
	 * @param v vector of the enumeration values
	 */
	public void setSourceObjects(Vector<? extends ValuedEnum> v)
	{
		setEnumerationsAttribute(AttributeName.SOURCEOBJECTS, v, null);
	}

	/**
	 * (9.2) get SourceObjects attribute SourceObjects
	 *
	 * @return Vector of the enumerations
	 */
	public Vector<? extends ValuedEnum> getSourceObjects()
	{
		return getEnumerationsAttribute(AttributeName.SOURCEOBJECTS, null, EnumSourceObjects.getEnum(0), false);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute SpotFunction
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute SpotFunction
	 *
	 * @param value the value to set the attribute to
	 */
	public void setSpotFunction(String value)
	{
		setAttribute(AttributeName.SPOTFUNCTION, value, null);
	}

	/**
	 * (23) get String attribute SpotFunction
	 *
	 * @return the value of the attribute
	 */
	public String getSpotFunction()
	{
		return getAttribute(AttributeName.SPOTFUNCTION, null, JDFCoreConstants.EMPTYSTRING);
	}

}
