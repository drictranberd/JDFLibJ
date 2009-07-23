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
import org.cip4.jdflib.core.*;                      
import org.cip4.jdflib.datatypes.*;                 
import org.cip4.jdflib.resource.*;                  
import org.cip4.jdflib.resource.process.*;

public abstract class JDFAutoStripMark extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[15];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ABSOLUTEHEIGHT, 0x33333111, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.ABSOLUTEWIDTH, 0x33333111, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.ANCHOR, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumAnchor.getEnum(0), null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.HORIZONTALFITPOLICY, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumHorizontalFitPolicy.getEnum(0), null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.ID, 0x33333111, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[5] = new AtrInfoTable(AttributeName.MARKCONTEXT, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumMarkContext.getEnum(0), null);
        atrInfoTable[6] = new AtrInfoTable(AttributeName.MARKNAME, 0x33333111, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[7] = new AtrInfoTable(AttributeName.MARKSIDE, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumMarkSide.getEnum(0), null);
        atrInfoTable[8] = new AtrInfoTable(AttributeName.OFFSET, 0x33333111, AttributeInfo.EnumAttributeType.XYPair, null, null);
        atrInfoTable[9] = new AtrInfoTable(AttributeName.ORD, 0x33333111, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[10] = new AtrInfoTable(AttributeName.ORIENTATION, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumOrientation.getEnum(0), null);
        atrInfoTable[11] = new AtrInfoTable(AttributeName.RELATIVEHEIGHT, 0x33333111, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[12] = new AtrInfoTable(AttributeName.RELATIVEWIDTH, 0x33333111, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[13] = new AtrInfoTable(AttributeName.STRIPMARKDETAILS, 0x33333111, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[14] = new AtrInfoTable(AttributeName.VERTICALFITPOLICY, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumVerticalFitPolicy.getEnum(0), null);
    }
    
    @Override
	protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[3];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.POSITION, 0x66666111);
        elemInfoTable[1] = new ElemInfoTable(ElementName.JOBFIELD, 0x66666111);
        elemInfoTable[2] = new ElemInfoTable(ElementName.REFANCHOR, 0x33333111);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoStripMark
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoStripMark(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoStripMark
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoStripMark(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoStripMark
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoStripMark(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    @Override
	public String toString()
    {
        return " JDFAutoStripMark[  --> " + super.toString() + " ]";
    }


        /**
        * Enumeration strings for Anchor
        */

        public static class EnumAnchor extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumAnchor(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumAnchor getEnum(String enumName)
            {
                return (EnumAnchor) getEnum(EnumAnchor.class, enumName);
            }

            public static EnumAnchor getEnum(int enumValue)
            {
                return (EnumAnchor) getEnum(EnumAnchor.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumAnchor.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumAnchor.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumAnchor.class);
            }

            public static final EnumAnchor TopLeft = new EnumAnchor("TopLeft");
            public static final EnumAnchor TopCenter = new EnumAnchor("TopCenter");
            public static final EnumAnchor TopRight = new EnumAnchor("TopRight");
            public static final EnumAnchor CenterLeft = new EnumAnchor("CenterLeft");
            public static final EnumAnchor Center = new EnumAnchor("Center");
            public static final EnumAnchor CenterRight = new EnumAnchor("CenterRight");
            public static final EnumAnchor BottomLeft = new EnumAnchor("BottomLeft");
            public static final EnumAnchor BottomCenter = new EnumAnchor("BottomCenter");
            public static final EnumAnchor BottomRight = new EnumAnchor("BottomRight");
        }      



        /**
        * Enumeration strings for HorizontalFitPolicy
        */

        public static class EnumHorizontalFitPolicy extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumHorizontalFitPolicy(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumHorizontalFitPolicy getEnum(String enumName)
            {
                return (EnumHorizontalFitPolicy) getEnum(EnumHorizontalFitPolicy.class, enumName);
            }

            public static EnumHorizontalFitPolicy getEnum(int enumValue)
            {
                return (EnumHorizontalFitPolicy) getEnum(EnumHorizontalFitPolicy.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumHorizontalFitPolicy.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumHorizontalFitPolicy.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumHorizontalFitPolicy.class);
            }

            public static final EnumHorizontalFitPolicy NoRepeat = new EnumHorizontalFitPolicy("NoRepeat");
            public static final EnumHorizontalFitPolicy StretchToFit = new EnumHorizontalFitPolicy("StretchToFit");
            public static final EnumHorizontalFitPolicy UndistortedScaleToFit = new EnumHorizontalFitPolicy("UndistortedScaleToFit");
            public static final EnumHorizontalFitPolicy RepeatToFill = new EnumHorizontalFitPolicy("RepeatToFill");
            public static final EnumHorizontalFitPolicy RepeatUnclipped = new EnumHorizontalFitPolicy("RepeatUnclipped");
        }      



        /**
        * Enumeration strings for MarkContext
        */

        public static class EnumMarkContext extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumMarkContext(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumMarkContext getEnum(String enumName)
            {
                return (EnumMarkContext) getEnum(EnumMarkContext.class, enumName);
            }

            public static EnumMarkContext getEnum(int enumValue)
            {
                return (EnumMarkContext) getEnum(EnumMarkContext.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumMarkContext.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumMarkContext.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumMarkContext.class);
            }

            public static final EnumMarkContext Sheet = new EnumMarkContext("Sheet");
            public static final EnumMarkContext BinderySignature = new EnumMarkContext("BinderySignature");
            public static final EnumMarkContext Cell = new EnumMarkContext("Cell");
            public static final EnumMarkContext CellPair = new EnumMarkContext("CellPair");
        }      



        /**
        * Enumeration strings for MarkSide
        */

        public static class EnumMarkSide extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumMarkSide(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumMarkSide getEnum(String enumName)
            {
                return (EnumMarkSide) getEnum(EnumMarkSide.class, enumName);
            }

            public static EnumMarkSide getEnum(int enumValue)
            {
                return (EnumMarkSide) getEnum(EnumMarkSide.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumMarkSide.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumMarkSide.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumMarkSide.class);
            }

            public static final EnumMarkSide Front = new EnumMarkSide("Front");
            public static final EnumMarkSide Back = new EnumMarkSide("Back");
            public static final EnumMarkSide TwoSidedBackToBack = new EnumMarkSide("TwoSidedBackToBack");
            public static final EnumMarkSide TwoSidedIndependent = new EnumMarkSide("TwoSidedIndependent");
        }      



        /**
        * Enumeration strings for Orientation
        */

        public static class EnumOrientation extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumOrientation(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumOrientation getEnum(String enumName)
            {
                return (EnumOrientation) getEnum(EnumOrientation.class, enumName);
            }

            public static EnumOrientation getEnum(int enumValue)
            {
                return (EnumOrientation) getEnum(EnumOrientation.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumOrientation.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumOrientation.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumOrientation.class);
            }

            public static final EnumOrientation Rotate0 = new EnumOrientation("Rotate0");
            public static final EnumOrientation Rotate90 = new EnumOrientation("Rotate90");
            public static final EnumOrientation Rotate180 = new EnumOrientation("Rotate180");
            public static final EnumOrientation Rotate270 = new EnumOrientation("Rotate270");
            public static final EnumOrientation Flip0 = new EnumOrientation("Flip0");
            public static final EnumOrientation Flip90 = new EnumOrientation("Flip90");
            public static final EnumOrientation Flip180 = new EnumOrientation("Flip180");
            public static final EnumOrientation Flip270 = new EnumOrientation("Flip270");
        }      



        /**
        * Enumeration strings for VerticalFitPolicy
        */

        public static class EnumVerticalFitPolicy extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumVerticalFitPolicy(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumVerticalFitPolicy getEnum(String enumName)
            {
                return (EnumVerticalFitPolicy) getEnum(EnumVerticalFitPolicy.class, enumName);
            }

            public static EnumVerticalFitPolicy getEnum(int enumValue)
            {
                return (EnumVerticalFitPolicy) getEnum(EnumVerticalFitPolicy.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumVerticalFitPolicy.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumVerticalFitPolicy.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumVerticalFitPolicy.class);
            }

            public static final EnumVerticalFitPolicy NoRepeat = new EnumVerticalFitPolicy("NoRepeat");
            public static final EnumVerticalFitPolicy StretchToFit = new EnumVerticalFitPolicy("StretchToFit");
            public static final EnumVerticalFitPolicy UndistortedScaleToFit = new EnumVerticalFitPolicy("UndistortedScaleToFit");
            public static final EnumVerticalFitPolicy RepeatToFill = new EnumVerticalFitPolicy("RepeatToFill");
            public static final EnumVerticalFitPolicy RepeatUnclipped = new EnumVerticalFitPolicy("RepeatUnclipped");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute AbsoluteHeight
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AbsoluteHeight
          * @param value: the value to set the attribute to
          */
        public void setAbsoluteHeight(double value)
        {
            setAttribute(AttributeName.ABSOLUTEHEIGHT, value, null);
        }

        /**
          * (17) get double attribute AbsoluteHeight
          * @return double the value of the attribute
          */
        public double getAbsoluteHeight()
        {
            return getRealAttribute(AttributeName.ABSOLUTEHEIGHT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute AbsoluteWidth
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AbsoluteWidth
          * @param value: the value to set the attribute to
          */
        public void setAbsoluteWidth(double value)
        {
            setAttribute(AttributeName.ABSOLUTEWIDTH, value, null);
        }

        /**
          * (17) get double attribute AbsoluteWidth
          * @return double the value of the attribute
          */
        public double getAbsoluteWidth()
        {
            return getRealAttribute(AttributeName.ABSOLUTEWIDTH, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Anchor
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Anchor
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setAnchor(EnumAnchor enumVar)
        {
            setAttribute(AttributeName.ANCHOR, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Anchor
          * @return the value of the attribute
          */
        public EnumAnchor getAnchor()
        {
            return EnumAnchor.getEnum(getAttribute(AttributeName.ANCHOR, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute HorizontalFitPolicy
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute HorizontalFitPolicy
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setHorizontalFitPolicy(EnumHorizontalFitPolicy enumVar)
        {
            setAttribute(AttributeName.HORIZONTALFITPOLICY, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute HorizontalFitPolicy
          * @return the value of the attribute
          */
        public EnumHorizontalFitPolicy getHorizontalFitPolicy()
        {
            return EnumHorizontalFitPolicy.getEnum(getAttribute(AttributeName.HORIZONTALFITPOLICY, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ID
          * @param value: the value to set the attribute to
          */
        public void setID(String value)
        {
            setAttribute(AttributeName.ID, value, null);
        }

        /**
          * (23) get String attribute ID
          * @return the value of the attribute
          */
        @Override
		public String getID()
        {
            return getAttribute(AttributeName.ID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MarkContext
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute MarkContext
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setMarkContext(EnumMarkContext enumVar)
        {
            setAttribute(AttributeName.MARKCONTEXT, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute MarkContext
          * @return the value of the attribute
          */
        public EnumMarkContext getMarkContext()
        {
            return EnumMarkContext.getEnum(getAttribute(AttributeName.MARKCONTEXT, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MarkName
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute MarkName
          * @param value: the value to set the attribute to
          */
        public void setMarkName(String value)
        {
            setAttribute(AttributeName.MARKNAME, value, null);
        }

        /**
          * (23) get String attribute MarkName
          * @return the value of the attribute
          */
        public String getMarkName()
        {
            return getAttribute(AttributeName.MARKNAME, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MarkSide
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute MarkSide
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setMarkSide(EnumMarkSide enumVar)
        {
            setAttribute(AttributeName.MARKSIDE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute MarkSide
          * @return the value of the attribute
          */
        public EnumMarkSide getMarkSide()
        {
            return EnumMarkSide.getEnum(getAttribute(AttributeName.MARKSIDE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Offset
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Offset
          * @param value: the value to set the attribute to
          */
        public void setOffset(JDFXYPair value)
        {
            setAttribute(AttributeName.OFFSET, value, null);
        }

        /**
          * (20) get JDFXYPair attribute Offset
          * @return JDFXYPair the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFXYPair
          */
        public JDFXYPair getOffset()
        {
            String strAttrName = "";
            JDFXYPair nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.OFFSET, null, JDFConstants.EMPTYSTRING);
            try
            {
                nPlaceHolder = new JDFXYPair(strAttrName);
            }
            catch(DataFormatException e)
            {
                return null;
            }
            return nPlaceHolder;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Ord
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Ord
          * @param value: the value to set the attribute to
          */
        public void setOrd(int value)
        {
            setAttribute(AttributeName.ORD, value, null);
        }

        /**
          * (15) get int attribute Ord
          * @return int the value of the attribute
          */
        public int getOrd()
        {
            return getIntAttribute(AttributeName.ORD, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Orientation
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Orientation
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setOrientation(EnumOrientation enumVar)
        {
            setAttribute(AttributeName.ORIENTATION, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Orientation
          * @return the value of the attribute
          */
        public EnumOrientation getOrientation()
        {
            return EnumOrientation.getEnum(getAttribute(AttributeName.ORIENTATION, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute RelativeHeight
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute RelativeHeight
          * @param value: the value to set the attribute to
          */
        public void setRelativeHeight(double value)
        {
            setAttribute(AttributeName.RELATIVEHEIGHT, value, null);
        }

        /**
          * (17) get double attribute RelativeHeight
          * @return double the value of the attribute
          */
        public double getRelativeHeight()
        {
            return getRealAttribute(AttributeName.RELATIVEHEIGHT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute RelativeWidth
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute RelativeWidth
          * @param value: the value to set the attribute to
          */
        public void setRelativeWidth(double value)
        {
            setAttribute(AttributeName.RELATIVEWIDTH, value, null);
        }

        /**
          * (17) get double attribute RelativeWidth
          * @return double the value of the attribute
          */
        public double getRelativeWidth()
        {
            return getRealAttribute(AttributeName.RELATIVEWIDTH, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute StripMarkDetails
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute StripMarkDetails
          * @param value: the value to set the attribute to
          */
        public void setStripMarkDetails(String value)
        {
            setAttribute(AttributeName.STRIPMARKDETAILS, value, null);
        }

        /**
          * (23) get String attribute StripMarkDetails
          * @return the value of the attribute
          */
        public String getStripMarkDetails()
        {
            return getAttribute(AttributeName.STRIPMARKDETAILS, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute VerticalFitPolicy
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute VerticalFitPolicy
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setVerticalFitPolicy(EnumVerticalFitPolicy enumVar)
        {
            setAttribute(AttributeName.VERTICALFITPOLICY, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute VerticalFitPolicy
          * @return the value of the attribute
          */
        public EnumVerticalFitPolicy getVerticalFitPolicy()
        {
            return EnumVerticalFitPolicy.getEnum(getAttribute(AttributeName.VERTICALFITPOLICY, null, null));
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element Position
     * @return JDFPosition the element
     */
    public JDFPosition getPosition()
    {
        return (JDFPosition) getElement(ElementName.POSITION, null, 0);
    }

    /** (25) getCreatePosition
     * 
     * @return JDFPosition the element
     */
    public JDFPosition getCreatePosition()
    {
        return (JDFPosition) getCreateElement_KElement(ElementName.POSITION, null, 0);
    }

    /**
     * (29) append element Position
     */
    public JDFPosition appendPosition() throws JDFException
    {
        return (JDFPosition) appendElementN(ElementName.POSITION, 1, null);
    }

    /**
     * (24) const get element JobField
     * @return JDFJobField the element
     */
    public JDFJobField getJobField()
    {
        return (JDFJobField) getElement(ElementName.JOBFIELD, null, 0);
    }

    /** (25) getCreateJobField
     * 
     * @return JDFJobField the element
     */
    public JDFJobField getCreateJobField()
    {
        return (JDFJobField) getCreateElement_KElement(ElementName.JOBFIELD, null, 0);
    }

    /**
     * (29) append element JobField
     */
    public JDFJobField appendJobField() throws JDFException
    {
        return (JDFJobField) appendElementN(ElementName.JOBFIELD, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refJobField(JDFJobField refTarget)
    {
        refElement(refTarget);
    }

    /** (26) getCreateRefAnchor
     * 
     * @param iSkip number of elements to skip
     * @return JDFRefAnchor the element
     */
    public JDFRefAnchor getCreateRefAnchor(int iSkip)
    {
        return (JDFRefAnchor)getCreateElement_KElement(ElementName.REFANCHOR, null, iSkip);
    }

    /**
     * (27) const get element RefAnchor
     * @param iSkip number of elements to skip
     * @return JDFRefAnchor the element
     * default is getRefAnchor(0)     */
    public JDFRefAnchor getRefAnchor(int iSkip)
    {
        return (JDFRefAnchor) getElement(ElementName.REFANCHOR, null, iSkip);
    }

    /**
     * Get all RefAnchor from the current element
     * 
     * @return Collection<JDFRefAnchor>, null if none are available
     */
    public Collection<JDFRefAnchor> getAllRefAnchor()
    {
        final VElement vc = getChildElementVector(ElementName.REFANCHOR, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFRefAnchor> v = new Vector<JDFRefAnchor>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFRefAnchor) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element RefAnchor
     */
    public JDFRefAnchor appendRefAnchor() throws JDFException
    {
        return (JDFRefAnchor) appendElement(ElementName.REFANCHOR, null);
    }

}// end namespace JDF
