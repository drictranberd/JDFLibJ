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

public abstract class JDFAutoByteMap extends JDFResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[8];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.BANDORDERING, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumBandOrdering.getEnum(0), null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.ELEMENTTYPE, 0x33331111, AttributeInfo.EnumAttributeType.enumeration, EnumElementType.getEnum(0), null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.FRAMEHEIGHT, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.FRAMEWIDTH, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.HALFTONED, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, null);
        atrInfoTable[5] = new AtrInfoTable(AttributeName.INTERLEAVED, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, null);
        atrInfoTable[6] = new AtrInfoTable(AttributeName.PIXELSKIP, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[7] = new AtrInfoTable(AttributeName.RESOLUTION, 0x33333333, AttributeInfo.EnumAttributeType.XYPair, null, null);
    }
    
    @Override
	protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[4];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.BAND, 0x22222222);
        elemInfoTable[1] = new ElemInfoTable(ElementName.COLORPOOL, 0x66666611);
        elemInfoTable[2] = new ElemInfoTable(ElementName.FILESPEC, 0x33333333);
        elemInfoTable[3] = new ElemInfoTable(ElementName.PIXELCOLORANT, 0x33333333);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoByteMap
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoByteMap(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoByteMap
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoByteMap(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoByteMap
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoByteMap(
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
        return " JDFAutoByteMap[  --> " + super.toString() + " ]";
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
        * Enumeration strings for BandOrdering
        */

        public static class EnumBandOrdering extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumBandOrdering(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumBandOrdering getEnum(String enumName)
            {
                return (EnumBandOrdering) getEnum(EnumBandOrdering.class, enumName);
            }

            public static EnumBandOrdering getEnum(int enumValue)
            {
                return (EnumBandOrdering) getEnum(EnumBandOrdering.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumBandOrdering.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumBandOrdering.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumBandOrdering.class);
            }

            public static final EnumBandOrdering BandMajor = new EnumBandOrdering("BandMajor");
            public static final EnumBandOrdering ColorMajor = new EnumBandOrdering("ColorMajor");
        }      



        /**
        * Enumeration strings for ElementType
        */

        public static class EnumElementType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumElementType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumElementType getEnum(String enumName)
            {
                return (EnumElementType) getEnum(EnumElementType.class, enumName);
            }

            public static EnumElementType getEnum(int enumValue)
            {
                return (EnumElementType) getEnum(EnumElementType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumElementType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumElementType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumElementType.class);
            }

            public static final EnumElementType Auxiliary = new EnumElementType("Auxiliary");
            public static final EnumElementType Barcode = new EnumElementType("Barcode");
            public static final EnumElementType Composed = new EnumElementType("Composed");
            public static final EnumElementType Document = new EnumElementType("Document");
            public static final EnumElementType Graphic = new EnumElementType("Graphic");
            public static final EnumElementType IdentificationField = new EnumElementType("IdentificationField");
            public static final EnumElementType Image = new EnumElementType("Image");
            public static final EnumElementType MultiDocument = new EnumElementType("MultiDocument");
            public static final EnumElementType MultiSet = new EnumElementType("MultiSet");
            public static final EnumElementType Page = new EnumElementType("Page");
            public static final EnumElementType Reservation = new EnumElementType("Reservation");
            public static final EnumElementType Surface = new EnumElementType("Surface");
            public static final EnumElementType Text = new EnumElementType("Text");
            public static final EnumElementType Tile = new EnumElementType("Tile");
            public static final EnumElementType Unknown = new EnumElementType("Unknown");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute BandOrdering
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute BandOrdering
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setBandOrdering(EnumBandOrdering enumVar)
        {
            setAttribute(AttributeName.BANDORDERING, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute BandOrdering
          * @return the value of the attribute
          */
        public EnumBandOrdering getBandOrdering()
        {
            return EnumBandOrdering.getEnum(getAttribute(AttributeName.BANDORDERING, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ElementType
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute ElementType
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setElementType(EnumElementType enumVar)
        {
            setAttribute(AttributeName.ELEMENTTYPE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute ElementType
          * @return the value of the attribute
          */
        public EnumElementType getElementType()
        {
            return EnumElementType.getEnum(getAttribute(AttributeName.ELEMENTTYPE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute FrameHeight
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute FrameHeight
          * @param value: the value to set the attribute to
          */
        public void setFrameHeight(int value)
        {
            setAttribute(AttributeName.FRAMEHEIGHT, value, null);
        }

        /**
          * (15) get int attribute FrameHeight
          * @return int the value of the attribute
          */
        public int getFrameHeight()
        {
            return getIntAttribute(AttributeName.FRAMEHEIGHT, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute FrameWidth
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute FrameWidth
          * @param value: the value to set the attribute to
          */
        public void setFrameWidth(int value)
        {
            setAttribute(AttributeName.FRAMEWIDTH, value, null);
        }

        /**
          * (15) get int attribute FrameWidth
          * @return int the value of the attribute
          */
        public int getFrameWidth()
        {
            return getIntAttribute(AttributeName.FRAMEWIDTH, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Halftoned
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Halftoned
          * @param value: the value to set the attribute to
          */
        public void setHalftoned(boolean value)
        {
            setAttribute(AttributeName.HALFTONED, value, null);
        }

        /**
          * (18) get boolean attribute Halftoned
          * @return boolean the value of the attribute
          */
        public boolean getHalftoned()
        {
            return getBoolAttribute(AttributeName.HALFTONED, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Interleaved
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Interleaved
          * @param value: the value to set the attribute to
          */
        public void setInterleaved(boolean value)
        {
            setAttribute(AttributeName.INTERLEAVED, value, null);
        }

        /**
          * (18) get boolean attribute Interleaved
          * @return boolean the value of the attribute
          */
        public boolean getInterleaved()
        {
            return getBoolAttribute(AttributeName.INTERLEAVED, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute PixelSkip
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute PixelSkip
          * @param value: the value to set the attribute to
          */
        public void setPixelSkip(int value)
        {
            setAttribute(AttributeName.PIXELSKIP, value, null);
        }

        /**
          * (15) get int attribute PixelSkip
          * @return int the value of the attribute
          */
        public int getPixelSkip()
        {
            return getIntAttribute(AttributeName.PIXELSKIP, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Resolution
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Resolution
          * @param value: the value to set the attribute to
          */
        public void setResolution(JDFXYPair value)
        {
            setAttribute(AttributeName.RESOLUTION, value, null);
        }

        /**
          * (20) get JDFXYPair attribute Resolution
          * @return JDFXYPair the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFXYPair
          */
        public JDFXYPair getResolution()
        {
            String strAttrName = "";
            JDFXYPair nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.RESOLUTION, null, JDFConstants.EMPTYSTRING);
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

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /** (26) getCreateBand
     * 
     * @param iSkip number of elements to skip
     * @return JDFBand the element
     */
    public JDFBand getCreateBand(int iSkip)
    {
        return (JDFBand)getCreateElement_KElement(ElementName.BAND, null, iSkip);
    }

    /**
     * (27) const get element Band
     * @param iSkip number of elements to skip
     * @return JDFBand the element
     * default is getBand(0)     */
    public JDFBand getBand(int iSkip)
    {
        return (JDFBand) getElement(ElementName.BAND, null, iSkip);
    }

    /**
     * Get all Band from the current element
     * 
     * @return Collection<JDFBand>
     */
    public Collection<JDFBand> getAllBand()
    {
        Vector<JDFBand> v = new Vector<JDFBand>();

        JDFBand kElem = (JDFBand) getFirstChildElement(ElementName.BAND, null);

        while (kElem != null)
        {
            v.add(kElem);

            kElem = (JDFBand) kElem.getNextSiblingElement(ElementName.BAND, null);
        }

        return v;
    }

    /**
     * (30) append element Band
     */
    public JDFBand appendBand() throws JDFException
    {
        return (JDFBand) appendElement(ElementName.BAND, null);
    }

    /**
     * (24) const get element ColorPool
     * @return JDFColorPool the element
     */
    public JDFColorPool getColorPool()
    {
        return (JDFColorPool) getElement(ElementName.COLORPOOL, null, 0);
    }

    /** (25) getCreateColorPool
     * 
     * @return JDFColorPool the element
     */
    public JDFColorPool getCreateColorPool()
    {
        return (JDFColorPool) getCreateElement_KElement(ElementName.COLORPOOL, null, 0);
    }

    /**
     * (29) append element ColorPool
     */
    public JDFColorPool appendColorPool() throws JDFException
    {
        return (JDFColorPool) appendElementN(ElementName.COLORPOOL, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refColorPool(JDFColorPool refTarget)
    {
        refElement(refTarget);
    }

    /** (26) getCreateFileSpec
     * 
     * @param iSkip number of elements to skip
     * @return JDFFileSpec the element
     */
    public JDFFileSpec getCreateFileSpec(int iSkip)
    {
        return (JDFFileSpec)getCreateElement_KElement(ElementName.FILESPEC, null, iSkip);
    }

    /**
     * (27) const get element FileSpec
     * @param iSkip number of elements to skip
     * @return JDFFileSpec the element
     * default is getFileSpec(0)     */
    public JDFFileSpec getFileSpec(int iSkip)
    {
        return (JDFFileSpec) getElement(ElementName.FILESPEC, null, iSkip);
    }

    /**
     * Get all FileSpec from the current element
     * 
     * @return Collection<JDFFileSpec>
     */
    public Collection<JDFFileSpec> getAllFileSpec()
    {
        Vector<JDFFileSpec> v = new Vector<JDFFileSpec>();

        JDFFileSpec kElem = (JDFFileSpec) getFirstChildElement(ElementName.FILESPEC, null);

        while (kElem != null)
        {
            v.add(kElem);

            kElem = (JDFFileSpec) kElem.getNextSiblingElement(ElementName.FILESPEC, null);
        }

        return v;
    }

    /**
     * (30) append element FileSpec
     */
    public JDFFileSpec appendFileSpec() throws JDFException
    {
        return (JDFFileSpec) appendElement(ElementName.FILESPEC, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refFileSpec(JDFFileSpec refTarget)
    {
        refElement(refTarget);
    }

    /** (26) getCreatePixelColorant
     * 
     * @param iSkip number of elements to skip
     * @return JDFPixelColorant the element
     */
    public JDFPixelColorant getCreatePixelColorant(int iSkip)
    {
        return (JDFPixelColorant)getCreateElement_KElement(ElementName.PIXELCOLORANT, null, iSkip);
    }

    /**
     * (27) const get element PixelColorant
     * @param iSkip number of elements to skip
     * @return JDFPixelColorant the element
     * default is getPixelColorant(0)     */
    public JDFPixelColorant getPixelColorant(int iSkip)
    {
        return (JDFPixelColorant) getElement(ElementName.PIXELCOLORANT, null, iSkip);
    }

    /**
     * Get all PixelColorant from the current element
     * 
     * @return Collection<JDFPixelColorant>
     */
    public Collection<JDFPixelColorant> getAllPixelColorant()
    {
        Vector<JDFPixelColorant> v = new Vector<JDFPixelColorant>();

        JDFPixelColorant kElem = (JDFPixelColorant) getFirstChildElement(ElementName.PIXELCOLORANT, null);

        while (kElem != null)
        {
            v.add(kElem);

            kElem = (JDFPixelColorant) kElem.getNextSiblingElement(ElementName.PIXELCOLORANT, null);
        }

        return v;
    }

    /**
     * (30) append element PixelColorant
     */
    public JDFPixelColorant appendPixelColorant() throws JDFException
    {
        return (JDFPixelColorant) appendElement(ElementName.PIXELCOLORANT, null);
    }

}// end namespace JDF
