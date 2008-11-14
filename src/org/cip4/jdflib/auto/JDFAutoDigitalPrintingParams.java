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

import java.util.Iterator;                          
import java.util.List;                              
import java.util.Map;                               
import org.apache.commons.lang.enums.ValuedEnum;    
import org.apache.xerces.dom.CoreDocumentImpl;      
import org.cip4.jdflib.core.*;                      
import org.cip4.jdflib.resource.*;                  
import org.cip4.jdflib.resource.process.*;          
import org.cip4.jdflib.resource.process.prepress.*;

public abstract class JDFAutoDigitalPrintingParams extends JDFResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[13];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.DIRECTPROOFAMOUNT, 0x33333311, AttributeInfo.EnumAttributeType.integer, null, "0");
        atrInfoTable[1] = new AtrInfoTable(AttributeName.MANUALFEED, 0x33333331, AttributeInfo.EnumAttributeType.boolean_, null, "false");
        atrInfoTable[2] = new AtrInfoTable(AttributeName.COLLATE, 0x33333331, AttributeInfo.EnumAttributeType.enumeration, EnumCollate.getEnum(0), null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.OUTPUTBIN, 0x33333331, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.PRINTQUALITY, 0x44444443, AttributeInfo.EnumAttributeType.enumeration, EnumPrintQuality.getEnum(0), null);
        atrInfoTable[5] = new AtrInfoTable(AttributeName.NONPRINTABLEMARGINBOTTOM, 0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[6] = new AtrInfoTable(AttributeName.NONPRINTABLEMARGINLEFT, 0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[7] = new AtrInfoTable(AttributeName.NONPRINTABLEMARGINRIGHT, 0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[8] = new AtrInfoTable(AttributeName.NONPRINTABLEMARGINTOP, 0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[9] = new AtrInfoTable(AttributeName.PAGEDELIVERY, 0x33333331, AttributeInfo.EnumAttributeType.enumeration, EnumPageDelivery.getEnum(0), null);
        atrInfoTable[10] = new AtrInfoTable(AttributeName.PRINTINGTYPE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumPrintingType.getEnum(0), null);
        atrInfoTable[11] = new AtrInfoTable(AttributeName.SHEETLAY, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumSheetLay.getEnum(0), null);
        atrInfoTable[12] = new AtrInfoTable(AttributeName.SIDES, 0x33333111, AttributeInfo.EnumAttributeType.enumeration, EnumSides.getEnum(0), null);
    }
    
    @Override
	protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[6];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.COMPONENT, 0x66666661);
        elemInfoTable[1] = new ElemInfoTable(ElementName.APPROVALPARAMS, 0x66666611);
        elemInfoTable[2] = new ElemInfoTable(ElementName.DISJOINTING, 0x66666661);
        elemInfoTable[3] = new ElemInfoTable(ElementName.INK, 0x77776111);
        elemInfoTable[4] = new ElemInfoTable(ElementName.MEDIA, 0x66666661);
        elemInfoTable[5] = new ElemInfoTable(ElementName.MEDIASOURCE, 0x77777776);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoDigitalPrintingParams
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoDigitalPrintingParams(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDigitalPrintingParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoDigitalPrintingParams(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDigitalPrintingParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoDigitalPrintingParams(
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
        return " JDFAutoDigitalPrintingParams[  --> " + super.toString() + " ]";
    }


    @Override
	public boolean  init()
    {
        boolean bRet = super.init();
        setResourceClass(JDFResource.EnumResourceClass.Parameter);
        return bRet;
    }


    @Override
	public EnumResourceClass getValidClass()
    {
        return JDFResource.EnumResourceClass.Parameter;
    }


        /**
        * Enumeration strings for Collate
        */

        public static class EnumCollate extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumCollate(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumCollate getEnum(String enumName)
            {
                return (EnumCollate) getEnum(EnumCollate.class, enumName);
            }

            public static EnumCollate getEnum(int enumValue)
            {
                return (EnumCollate) getEnum(EnumCollate.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumCollate.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumCollate.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumCollate.class);
            }

            public static final EnumCollate None = new EnumCollate("None");
            public static final EnumCollate Sheet = new EnumCollate("Sheet");
            public static final EnumCollate SheetAndSet = new EnumCollate("SheetAndSet");
            public static final EnumCollate SheetSetAndJob = new EnumCollate("SheetSetAndJob");
            public static final EnumCollate SystemSpecified = new EnumCollate("SystemSpecified");
        }      



        /**
        * Enumeration strings for PrintQuality
        */

        public static class EnumPrintQuality extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumPrintQuality(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumPrintQuality getEnum(String enumName)
            {
                return (EnumPrintQuality) getEnum(EnumPrintQuality.class, enumName);
            }

            public static EnumPrintQuality getEnum(int enumValue)
            {
                return (EnumPrintQuality) getEnum(EnumPrintQuality.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumPrintQuality.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumPrintQuality.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumPrintQuality.class);
            }

            public static final EnumPrintQuality High = new EnumPrintQuality("High");
            public static final EnumPrintQuality Normal = new EnumPrintQuality("Normal");
            public static final EnumPrintQuality Draft = new EnumPrintQuality("Draft");
            public static final EnumPrintQuality SystemSpecified = new EnumPrintQuality("SystemSpecified");
        }      



        /**
        * Enumeration strings for PageDelivery
        */

        public static class EnumPageDelivery extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumPageDelivery(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumPageDelivery getEnum(String enumName)
            {
                return (EnumPageDelivery) getEnum(EnumPageDelivery.class, enumName);
            }

            public static EnumPageDelivery getEnum(int enumValue)
            {
                return (EnumPageDelivery) getEnum(EnumPageDelivery.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumPageDelivery.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumPageDelivery.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumPageDelivery.class);
            }

            public static final EnumPageDelivery FanFold = new EnumPageDelivery("FanFold");
            public static final EnumPageDelivery SameOrderFaceUp = new EnumPageDelivery("SameOrderFaceUp");
            public static final EnumPageDelivery SameOrderFaceDown = new EnumPageDelivery("SameOrderFaceDown");
            public static final EnumPageDelivery ReverseOrderFaceUp = new EnumPageDelivery("ReverseOrderFaceUp");
            public static final EnumPageDelivery ReverseOrderFaceDown = new EnumPageDelivery("ReverseOrderFaceDown");
            public static final EnumPageDelivery SystemSpecified = new EnumPageDelivery("SystemSpecified");
        }      



        /**
        * Enumeration strings for PrintingType
        */

        public static class EnumPrintingType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumPrintingType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumPrintingType getEnum(String enumName)
            {
                return (EnumPrintingType) getEnum(EnumPrintingType.class, enumName);
            }

            public static EnumPrintingType getEnum(int enumValue)
            {
                return (EnumPrintingType) getEnum(EnumPrintingType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumPrintingType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumPrintingType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumPrintingType.class);
            }

            public static final EnumPrintingType SheetFed = new EnumPrintingType("SheetFed");
            public static final EnumPrintingType WebFed = new EnumPrintingType("WebFed");
            public static final EnumPrintingType ContinuousFed = new EnumPrintingType("ContinuousFed");
            public static final EnumPrintingType SystemSpecified = new EnumPrintingType("SystemSpecified");
        }      



        /**
        * Enumeration strings for SheetLay
        */

        public static class EnumSheetLay extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumSheetLay(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumSheetLay getEnum(String enumName)
            {
                return (EnumSheetLay) getEnum(EnumSheetLay.class, enumName);
            }

            public static EnumSheetLay getEnum(int enumValue)
            {
                return (EnumSheetLay) getEnum(EnumSheetLay.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumSheetLay.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumSheetLay.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumSheetLay.class);
            }

            public static final EnumSheetLay Left = new EnumSheetLay("Left");
            public static final EnumSheetLay Right = new EnumSheetLay("Right");
            public static final EnumSheetLay Center = new EnumSheetLay("Center");
            public static final EnumSheetLay SystemSpecified = new EnumSheetLay("SystemSpecified");
        }      



        /**
        * Enumeration strings for Sides
        */

        public static class EnumSides extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumSides(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumSides getEnum(String enumName)
            {
                return (EnumSides) getEnum(EnumSides.class, enumName);
            }

            public static EnumSides getEnum(int enumValue)
            {
                return (EnumSides) getEnum(EnumSides.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumSides.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumSides.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumSides.class);
            }

            public static final EnumSides OneSidedBackFlipX = new EnumSides("OneSidedBackFlipX");
            public static final EnumSides OneSidedBackFlipY = new EnumSides("OneSidedBackFlipY");
            public static final EnumSides OneSidedFront = new EnumSides("OneSidedFront");
            public static final EnumSides TwoSidedFlipX = new EnumSides("TwoSidedFlipX");
            public static final EnumSides TwoSidedFlipY = new EnumSides("TwoSidedFlipY");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute DirectProofAmount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute DirectProofAmount
          * @param value: the value to set the attribute to
          */
        public void setDirectProofAmount(int value)
        {
            setAttribute(AttributeName.DIRECTPROOFAMOUNT, value, null);
        }

        /**
          * (15) get int attribute DirectProofAmount
          * @return int the value of the attribute
          */
        public int getDirectProofAmount()
        {
            return getIntAttribute(AttributeName.DIRECTPROOFAMOUNT, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ManualFeed
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ManualFeed
          * @param value: the value to set the attribute to
          */
        public void setManualFeed(boolean value)
        {
            setAttribute(AttributeName.MANUALFEED, value, null);
        }

        /**
          * (18) get boolean attribute ManualFeed
          * @return boolean the value of the attribute
          */
        public boolean getManualFeed()
        {
            return getBoolAttribute(AttributeName.MANUALFEED, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Collate
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Collate
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setCollate(EnumCollate enumVar)
        {
            setAttribute(AttributeName.COLLATE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Collate
          * @return the value of the attribute
          */
        public EnumCollate getCollate()
        {
            return EnumCollate.getEnum(getAttribute(AttributeName.COLLATE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute OutputBin
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute OutputBin
          * @param value: the value to set the attribute to
          */
        public void setOutputBin(String value)
        {
            setAttribute(AttributeName.OUTPUTBIN, value, null);
        }

        /**
          * (23) get String attribute OutputBin
          * @return the value of the attribute
          */
        public String getOutputBin()
        {
            return getAttribute(AttributeName.OUTPUTBIN, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute PrintQuality
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute PrintQuality
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setPrintQuality(EnumPrintQuality enumVar)
        {
            setAttribute(AttributeName.PRINTQUALITY, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute PrintQuality
          * @return the value of the attribute
          */
        public EnumPrintQuality getPrintQuality()
        {
            return EnumPrintQuality.getEnum(getAttribute(AttributeName.PRINTQUALITY, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute NonPrintableMarginBottom
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute NonPrintableMarginBottom
          * @param value: the value to set the attribute to
          */
        public void setNonPrintableMarginBottom(double value)
        {
            setAttribute(AttributeName.NONPRINTABLEMARGINBOTTOM, value, null);
        }

        /**
          * (17) get double attribute NonPrintableMarginBottom
          * @return double the value of the attribute
          */
        public double getNonPrintableMarginBottom()
        {
            return getRealAttribute(AttributeName.NONPRINTABLEMARGINBOTTOM, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute NonPrintableMarginLeft
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute NonPrintableMarginLeft
          * @param value: the value to set the attribute to
          */
        public void setNonPrintableMarginLeft(double value)
        {
            setAttribute(AttributeName.NONPRINTABLEMARGINLEFT, value, null);
        }

        /**
          * (17) get double attribute NonPrintableMarginLeft
          * @return double the value of the attribute
          */
        public double getNonPrintableMarginLeft()
        {
            return getRealAttribute(AttributeName.NONPRINTABLEMARGINLEFT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute NonPrintableMarginRight
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute NonPrintableMarginRight
          * @param value: the value to set the attribute to
          */
        public void setNonPrintableMarginRight(double value)
        {
            setAttribute(AttributeName.NONPRINTABLEMARGINRIGHT, value, null);
        }

        /**
          * (17) get double attribute NonPrintableMarginRight
          * @return double the value of the attribute
          */
        public double getNonPrintableMarginRight()
        {
            return getRealAttribute(AttributeName.NONPRINTABLEMARGINRIGHT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute NonPrintableMarginTop
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute NonPrintableMarginTop
          * @param value: the value to set the attribute to
          */
        public void setNonPrintableMarginTop(double value)
        {
            setAttribute(AttributeName.NONPRINTABLEMARGINTOP, value, null);
        }

        /**
          * (17) get double attribute NonPrintableMarginTop
          * @return double the value of the attribute
          */
        public double getNonPrintableMarginTop()
        {
            return getRealAttribute(AttributeName.NONPRINTABLEMARGINTOP, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute PageDelivery
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute PageDelivery
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setPageDelivery(EnumPageDelivery enumVar)
        {
            setAttribute(AttributeName.PAGEDELIVERY, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute PageDelivery
          * @return the value of the attribute
          */
        public EnumPageDelivery getPageDelivery()
        {
            return EnumPageDelivery.getEnum(getAttribute(AttributeName.PAGEDELIVERY, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute PrintingType
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute PrintingType
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setPrintingType(EnumPrintingType enumVar)
        {
            setAttribute(AttributeName.PRINTINGTYPE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute PrintingType
          * @return the value of the attribute
          */
        public EnumPrintingType getPrintingType()
        {
            return EnumPrintingType.getEnum(getAttribute(AttributeName.PRINTINGTYPE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute SheetLay
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute SheetLay
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setSheetLay(EnumSheetLay enumVar)
        {
            setAttribute(AttributeName.SHEETLAY, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute SheetLay
          * @return the value of the attribute
          */
        public EnumSheetLay getSheetLay()
        {
            return EnumSheetLay.getEnum(getAttribute(AttributeName.SHEETLAY, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Sides
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Sides
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setSides(EnumSides enumVar)
        {
            setAttribute(AttributeName.SIDES, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Sides
          * @return the value of the attribute
          */
        public EnumSides getSides()
        {
            return EnumSides.getEnum(getAttribute(AttributeName.SIDES, null, null));
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element Component
     * @return JDFComponent the element
     */
    public JDFComponent getComponent()
    {
        return (JDFComponent) getElement(ElementName.COMPONENT, null, 0);
    }

    /** (25) getCreateComponent
     * 
     * @return JDFComponent the element
     */
    public JDFComponent getCreateComponent()
    {
        return (JDFComponent) getCreateElement_KElement(ElementName.COMPONENT, null, 0);
    }

    /**
     * (29) append element Component
     */
    public JDFComponent appendComponent() throws JDFException
    {
        return (JDFComponent) appendElementN(ElementName.COMPONENT, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refComponent(JDFComponent refTarget)
    {
        refElement(refTarget);
    }

    /**
     * (24) const get element ApprovalParams
     * @return JDFApprovalParams the element
     */
    public JDFApprovalParams getApprovalParams()
    {
        return (JDFApprovalParams) getElement(ElementName.APPROVALPARAMS, null, 0);
    }

    /** (25) getCreateApprovalParams
     * 
     * @return JDFApprovalParams the element
     */
    public JDFApprovalParams getCreateApprovalParams()
    {
        return (JDFApprovalParams) getCreateElement_KElement(ElementName.APPROVALPARAMS, null, 0);
    }

    /**
     * (29) append element ApprovalParams
     */
    public JDFApprovalParams appendApprovalParams() throws JDFException
    {
        return (JDFApprovalParams) appendElementN(ElementName.APPROVALPARAMS, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refApprovalParams(JDFApprovalParams refTarget)
    {
        refElement(refTarget);
    }

    /**
     * (24) const get element Disjointing
     * @return JDFDisjointing the element
     */
    public JDFDisjointing getDisjointing()
    {
        return (JDFDisjointing) getElement(ElementName.DISJOINTING, null, 0);
    }

    /** (25) getCreateDisjointing
     * 
     * @return JDFDisjointing the element
     */
    public JDFDisjointing getCreateDisjointing()
    {
        return (JDFDisjointing) getCreateElement_KElement(ElementName.DISJOINTING, null, 0);
    }

    /**
     * (29) append element Disjointing
     */
    public JDFDisjointing appendDisjointing() throws JDFException
    {
        return (JDFDisjointing) appendElementN(ElementName.DISJOINTING, 1, null);
    }

    /**
     * (24) const get element Ink
     * @return JDFInk the element
     */
    public JDFInk getInk()
    {
        return (JDFInk) getElement(ElementName.INK, null, 0);
    }

    /** (25) getCreateInk
     * 
     * @return JDFInk the element
     */
    public JDFInk getCreateInk()
    {
        return (JDFInk) getCreateElement_KElement(ElementName.INK, null, 0);
    }

    /**
     * (29) append element Ink
     */
    public JDFInk appendInk() throws JDFException
    {
        return (JDFInk) appendElementN(ElementName.INK, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refInk(JDFInk refTarget)
    {
        refElement(refTarget);
    }

    /**
     * (24) const get element Media
     * @return JDFMedia the element
     */
    public JDFMedia getMedia()
    {
        return (JDFMedia) getElement(ElementName.MEDIA, null, 0);
    }

    /** (25) getCreateMedia
     * 
     * @return JDFMedia the element
     */
    public JDFMedia getCreateMedia()
    {
        return (JDFMedia) getCreateElement_KElement(ElementName.MEDIA, null, 0);
    }

    /**
     * (29) append element Media
     */
    public JDFMedia appendMedia() throws JDFException
    {
        return (JDFMedia) appendElementN(ElementName.MEDIA, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refMedia(JDFMedia refTarget)
    {
        refElement(refTarget);
    }

    /**
     * (24) const get element MediaSource
     * @return JDFMediaSource the element
     */
    public JDFMediaSource getMediaSource()
    {
        return (JDFMediaSource) getElement(ElementName.MEDIASOURCE, null, 0);
    }

    /** (25) getCreateMediaSource
     * 
     * @return JDFMediaSource the element
     */
    public JDFMediaSource getCreateMediaSource()
    {
        return (JDFMediaSource) getCreateElement_KElement(ElementName.MEDIASOURCE, null, 0);
    }

    /**
     * (29) append element MediaSource
     */
    public JDFMediaSource appendMediaSource() throws JDFException
    {
        return (JDFMediaSource) appendElementN(ElementName.MEDIASOURCE, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refMediaSource(JDFMediaSource refTarget)
    {
        refElement(refTarget);
    }

}// end namespace JDF
