/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2005 The International Cooperation for the Integration of
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.DataFormatException;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFComment;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFMatrix;
import org.cip4.jdflib.datatypes.JDFNameRangeList;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.process.JDFGeneralID;

public abstract class JDFAutoPreview extends JDFResource
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[18];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.PREVIEWFILETYPE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumPreviewFileType.getEnum(0), "PNG");
		atrInfoTable[1] = new AtrInfoTable(AttributeName.PREVIEWUSAGE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumPreviewUsage.getEnum(0), "Separation");
		atrInfoTable[2] = new AtrInfoTable(AttributeName.URL, 0x33333333, AttributeInfo.EnumAttributeType.URL, null, null);
		atrInfoTable[3] = new AtrInfoTable(AttributeName.PARTUSAGE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumPartUsage.getEnum(0), null);
		atrInfoTable[4] = new AtrInfoTable(AttributeName.COMPENSATION, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumCompensation.getEnum(0), null);
		atrInfoTable[5] = new AtrInfoTable(AttributeName.CTM, 0x33333333, AttributeInfo.EnumAttributeType.matrix, null, null);
		atrInfoTable[6] = new AtrInfoTable(AttributeName.DIRECTORY, 0x33333333, AttributeInfo.EnumAttributeType.URL, null, null);
		atrInfoTable[7] = new AtrInfoTable(AttributeName.MIMETYPEDETAILS, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
		atrInfoTable[8] = new AtrInfoTable(AttributeName.METADATA0, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[9] = new AtrInfoTable(AttributeName.METADATA1, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[10] = new AtrInfoTable(AttributeName.METADATA2, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[11] = new AtrInfoTable(AttributeName.METADATA3, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[12] = new AtrInfoTable(AttributeName.METADATA4, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[13] = new AtrInfoTable(AttributeName.METADATA5, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[14] = new AtrInfoTable(AttributeName.METADATA6, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[15] = new AtrInfoTable(AttributeName.METADATA7, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[16] = new AtrInfoTable(AttributeName.METADATA8, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
		atrInfoTable[17] = new AtrInfoTable(AttributeName.METADATA9, 0x33333333, AttributeInfo.EnumAttributeType.NameRangeList, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[2];
	static
	{
		elemInfoTable[0] = new ElemInfoTable(ElementName.COMMENT, 0x33333333);
		elemInfoTable[1] = new ElemInfoTable(ElementName.GENERALID, 0x33333333);
	}

	@Override
	protected ElementInfo getTheElementInfo()
	{
		return super.getTheElementInfo().updateReplace(elemInfoTable);
	}

	/**
	 * Constructor for JDFAutoPreview
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoPreview(final CoreDocumentImpl myOwnerDocument, final String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoPreview
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoPreview(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoPreview
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoPreview(final CoreDocumentImpl myOwnerDocument, final String myNamespaceURI, final String qualifiedName, final String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	@Override
	public String toString()
	{
		return " JDFAutoPreview[  --> " + super.toString() + " ]";
	}

	/**
	 * Enumeration strings for PreviewFileType
	 */

	public static class EnumPreviewFileType extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumPreviewFileType(final String name)
		{
			super(name, m_startValue++);
		}

		public static EnumPreviewFileType getEnum(final String enumName)
		{
			return (EnumPreviewFileType) getEnum(EnumPreviewFileType.class, enumName);
		}

		public static EnumPreviewFileType getEnum(final int enumValue)
		{
			return (EnumPreviewFileType) getEnum(EnumPreviewFileType.class, enumValue);
		}

		public static Map getEnumMap()
		{
			return getEnumMap(EnumPreviewFileType.class);
		}

		public static List getEnumList()
		{
			return getEnumList(EnumPreviewFileType.class);
		}

		public static Iterator iterator()
		{
			return iterator(EnumPreviewFileType.class);
		}

		public static final EnumPreviewFileType PNG = new EnumPreviewFileType("PNG");
		public static final EnumPreviewFileType CIP3Multiple = new EnumPreviewFileType("CIP3Multiple");
		public static final EnumPreviewFileType CIP3Single = new EnumPreviewFileType("CIP3Single");
	}

	/**
	 * Enumeration strings for PreviewUsage
	 */

	public static class EnumPreviewUsage extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumPreviewUsage(final String name)
		{
			super(name, m_startValue++);
		}

		public static EnumPreviewUsage getEnum(final String enumName)
		{
			return (EnumPreviewUsage) getEnum(EnumPreviewUsage.class, enumName);
		}

		public static EnumPreviewUsage getEnum(final int enumValue)
		{
			return (EnumPreviewUsage) getEnum(EnumPreviewUsage.class, enumValue);
		}

		public static Map getEnumMap()
		{
			return getEnumMap(EnumPreviewUsage.class);
		}

		public static List getEnumList()
		{
			return getEnumList(EnumPreviewUsage.class);
		}

		public static Iterator iterator()
		{
			return iterator(EnumPreviewUsage.class);
		}

		public static final EnumPreviewUsage Separation = new EnumPreviewUsage("Separation");
		public static final EnumPreviewUsage SeparatedThumbNail = new EnumPreviewUsage("SeparatedThumbNail");
		public static final EnumPreviewUsage SeparationRaw = new EnumPreviewUsage("SeparationRaw");
		public static final EnumPreviewUsage ThumbNail = new EnumPreviewUsage("ThumbNail");
		public static final EnumPreviewUsage Viewable = new EnumPreviewUsage("Viewable");
	}

	/**
	 * Enumeration strings for Compensation
	 */

	public static class EnumCompensation extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumCompensation(final String name)
		{
			super(name, m_startValue++);
		}

		public static EnumCompensation getEnum(final String enumName)
		{
			return (EnumCompensation) getEnum(EnumCompensation.class, enumName);
		}

		public static EnumCompensation getEnum(final int enumValue)
		{
			return (EnumCompensation) getEnum(EnumCompensation.class, enumValue);
		}

		public static Map getEnumMap()
		{
			return getEnumMap(EnumCompensation.class);
		}

		public static List getEnumList()
		{
			return getEnumList(EnumCompensation.class);
		}

		public static Iterator iterator()
		{
			return iterator(EnumCompensation.class);
		}

		public static final EnumCompensation Unknown = new EnumCompensation("Unknown");
		public static final EnumCompensation None = new EnumCompensation("None");
		public static final EnumCompensation Film = new EnumCompensation("Film");
		public static final EnumCompensation Plate = new EnumCompensation("Plate");
		public static final EnumCompensation Press = new EnumCompensation("Press");
	}

	/*
	 * Attribute getter / setter
	 */

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PreviewFileType
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (5) set attribute PreviewFileType
	 * @param enumVar: the enumVar to set the attribute to
	 */
	public void setPreviewFileType(final EnumPreviewFileType enumVar)
	{
		setAttribute(AttributeName.PREVIEWFILETYPE, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	 * (9) get attribute PreviewFileType
	 * @return the value of the attribute
	 */
	public EnumPreviewFileType getPreviewFileType()
	{
		return EnumPreviewFileType.getEnum(getAttribute(AttributeName.PREVIEWFILETYPE, null, "PNG"));
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PreviewUsage
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (5) set attribute PreviewUsage
	 * @param enumVar: the enumVar to set the attribute to
	 */
	public void setPreviewUsage(final EnumPreviewUsage enumVar)
	{
		setAttribute(AttributeName.PREVIEWUSAGE, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	 * (9) get attribute PreviewUsage
	 * @return the value of the attribute
	 */
	public EnumPreviewUsage getPreviewUsage()
	{
		return EnumPreviewUsage.getEnum(getAttribute(AttributeName.PREVIEWUSAGE, null, "Separation"));
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute URL
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute URL
	 * @param value: the value to set the attribute to
	 */
	public void setURL(final String value)
	{
		setAttribute(AttributeName.URL, value, null);
	}

	/**
	 * (23) get String attribute URL
	 * @return the value of the attribute
	 */
	public String getURL()
	{
		return getAttribute(AttributeName.URL, null, JDFConstants.EMPTYSTRING);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Compensation
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (5) set attribute Compensation
	 * @param enumVar: the enumVar to set the attribute to
	 */
	public void setCompensation(final EnumCompensation enumVar)
	{
		setAttribute(AttributeName.COMPENSATION, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	 * (9) get attribute Compensation
	 * @return the value of the attribute
	 */
	public EnumCompensation getCompensation()
	{
		return EnumCompensation.getEnum(getAttribute(AttributeName.COMPENSATION, null, null));
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute CTM
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute CTM
	 * @param value: the value to set the attribute to
	 */
	public void setCTM(final JDFMatrix value)
	{
		setAttribute(AttributeName.CTM, value, null);
	}

	/**
	 * (20) get JDFMatrix attribute CTM
	 * @return JDFMatrix the value of the attribute, null if a the attribute value is not a valid to create a JDFMatrix
	 */
	public JDFMatrix getCTM()
	{
		String strAttrName = "";
		JDFMatrix nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.CTM, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFMatrix(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Directory
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Directory
	 * @param value: the value to set the attribute to
	 */
	public void setDirectory(final String value)
	{
		setAttribute(AttributeName.DIRECTORY, value, null);
	}

	/**
	 * (23) get String attribute Directory
	 * @return the value of the attribute
	 */
	public String getDirectory()
	{
		return getAttribute(AttributeName.DIRECTORY, null, JDFConstants.EMPTYSTRING);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute MimeTypeDetails
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute MimeTypeDetails
	 * @param value: the value to set the attribute to
	 */
	public void setMimeTypeDetails(final String value)
	{
		setAttribute(AttributeName.MIMETYPEDETAILS, value, null);
	}

	/**
	 * (23) get String attribute MimeTypeDetails
	 * @return the value of the attribute
	 */
	public String getMimeTypeDetails()
	{
		return getAttribute(AttributeName.MIMETYPEDETAILS, null, JDFConstants.EMPTYSTRING);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata0
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata0
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata0(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA0, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata0
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata0()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA0, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata1
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata1
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata1(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA1, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata1
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata1()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA1, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata2
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata2
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata2(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA2, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata2
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata2()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA2, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata3
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata3
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata3(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA3, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata3
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata3()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA3, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata4
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata4
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata4(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA4, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata4
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata4()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA4, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata5
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata5
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata5(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA5, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata5
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata5()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA5, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata6
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata6
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata6(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA6, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata6
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata6()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA6, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata7
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata7
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata7(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA7, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata7
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata7()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA7, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata8
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata8
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata8(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA8, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata8
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata8()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA8, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Metadata9
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Metadata9
	 * @param value: the value to set the attribute to
	 */
	public void setMetadata9(final JDFNameRangeList value)
	{
		setAttribute(AttributeName.METADATA9, value, null);
	}

	/**
	 * (20) get JDFNameRangeList attribute Metadata9
	 * @return JDFNameRangeList the value of the attribute, null if a the attribute value is not a valid to create a JDFNameRangeList
	 */
	public JDFNameRangeList getMetadata9()
	{
		String strAttrName = "";
		JDFNameRangeList nPlaceHolder = null;
		strAttrName = getAttribute(AttributeName.METADATA9, null, JDFConstants.EMPTYSTRING);
		try
		{
			nPlaceHolder = new JDFNameRangeList(strAttrName);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
		return nPlaceHolder;
	}

	/*
	 * Element getter / setter
	 */

	/**
	 * (26) getCreateComment
	 * 
	 * @param iSkip number of elements to skip
	 * @return JDFComment the element
	 */
	@Override
	public JDFComment getCreateComment(final int iSkip)
	{
		return (JDFComment) getCreateElement_KElement(ElementName.COMMENT, null, iSkip);
	}

	/**
	 * (27) const get element Comment
	 * @param iSkip number of elements to skip
	 * @return JDFComment the element default is getComment(0)
	 */
	@Override
	public JDFComment getComment(final int iSkip)
	{
		return (JDFComment) getElement(ElementName.COMMENT, null, iSkip);
	}

	/**
	 * Get all Comment from the current element
	 * 
	 * @return Collection<JDFComment>, null if none are available
	 */
	public Collection<JDFComment> getAllComment()
	{
		final VElement vc = getChildElementVector(ElementName.COMMENT, null);
		if (vc == null || vc.size() == 0)
		{
			return null;
		}

		final Vector<JDFComment> v = new Vector<JDFComment>();
		for (int i = 0; i < vc.size(); i++)
		{
			v.add((JDFComment) vc.get(i));
		}

		return v;
	}

	/**
	 * (30) append element Comment
	 */
	@Override
	public JDFComment appendComment() throws JDFException
	{
		return (JDFComment) appendElement(ElementName.COMMENT, null);
	}

	/**
	 * (26) getCreateGeneralID
	 * 
	 * @param iSkip number of elements to skip
	 * @return JDFGeneralID the element
	 */
	public JDFGeneralID getCreateGeneralID(final int iSkip)
	{
		return (JDFGeneralID) getCreateElement_KElement(ElementName.GENERALID, null, iSkip);
	}

	/**
	 * (27) const get element GeneralID
	 * @param iSkip number of elements to skip
	 * @return JDFGeneralID the element default is getGeneralID(0)
	 */
	@Override
	public JDFGeneralID getGeneralID(final int iSkip)
	{
		return (JDFGeneralID) getElement(ElementName.GENERALID, null, iSkip);
	}

	/**
	 * Get all GeneralID from the current element
	 * 
	 * @return Collection<JDFGeneralID>, null if none are available
	 */
	public Collection<JDFGeneralID> getAllGeneralID()
	{
		final VElement vc = getChildElementVector(ElementName.GENERALID, null);
		if (vc == null || vc.size() == 0)
		{
			return null;
		}

		final Vector<JDFGeneralID> v = new Vector<JDFGeneralID>();
		for (int i = 0; i < vc.size(); i++)
		{
			v.add((JDFGeneralID) vc.get(i));
		}

		return v;
	}

	/**
	 * (30) append element GeneralID
	 */
	@Override
	public JDFGeneralID appendGeneralID() throws JDFException
	{
		return (JDFGeneralID) appendElement(ElementName.GENERALID, null);
	}

}// end namespace JDF
