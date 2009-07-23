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
import org.apache.commons.lang.enums.ValuedEnum;    
import org.apache.xerces.dom.CoreDocumentImpl;      
import org.cip4.jdflib.core.*;                      
import org.cip4.jdflib.resource.process.*;

public abstract class JDFAutoImageCompression extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[15];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ANTIALIASIMAGES, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, "false");
        atrInfoTable[1] = new AtrInfoTable(AttributeName.AUTOFILTERIMAGES, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, "true");
        atrInfoTable[2] = new AtrInfoTable(AttributeName.CONVERTIMAGESTOINDEXED, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.DCTQUALITY, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, "0");
        atrInfoTable[4] = new AtrInfoTable(AttributeName.DOWNSAMPLEIMAGES, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, "false");
        atrInfoTable[5] = new AtrInfoTable(AttributeName.ENCODECOLORIMAGES, 0x44444443, AttributeInfo.EnumAttributeType.boolean_, null, null);
        atrInfoTable[6] = new AtrInfoTable(AttributeName.ENCODEIMAGES, 0x33333331, AttributeInfo.EnumAttributeType.boolean_, null, "false");
        atrInfoTable[7] = new AtrInfoTable(AttributeName.IMAGEAUTOFILTERSTRATEGY, 0x33333311, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[8] = new AtrInfoTable(AttributeName.IMAGEDEPTH, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[9] = new AtrInfoTable(AttributeName.IMAGEDOWNSAMPLETHRESHOLD, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, "2.0");
        atrInfoTable[10] = new AtrInfoTable(AttributeName.IMAGEDOWNSAMPLETYPE, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumImageDownsampleType.getEnum(0), null);
        atrInfoTable[11] = new AtrInfoTable(AttributeName.IMAGEFILTER, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[12] = new AtrInfoTable(AttributeName.IMAGERESOLUTION, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[13] = new AtrInfoTable(AttributeName.IMAGETYPE, 0x22222222, AttributeInfo.EnumAttributeType.enumeration, EnumImageType.getEnum(0), null);
        atrInfoTable[14] = new AtrInfoTable(AttributeName.JPXQUALITY, 0x33333311, AttributeInfo.EnumAttributeType.integer, null, null);
    }
    
    @Override
	protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[5];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.CCITTFAXPARAMS, 0x66666611);
        elemInfoTable[1] = new ElemInfoTable(ElementName.DCTPARAMS, 0x66666611);
        elemInfoTable[2] = new ElemInfoTable(ElementName.JBIG2PARAMS, 0x33333111);
        elemInfoTable[3] = new ElemInfoTable(ElementName.JPEG2000PARAMS, 0x33333111);
        elemInfoTable[4] = new ElemInfoTable(ElementName.LZWPARAMS, 0x66666611);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoImageCompression
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoImageCompression(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoImageCompression
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoImageCompression(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoImageCompression
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoImageCompression(
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
        return " JDFAutoImageCompression[  --> " + super.toString() + " ]";
    }


        /**
        * Enumeration strings for ImageDownsampleType
        */

        public static class EnumImageDownsampleType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumImageDownsampleType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumImageDownsampleType getEnum(String enumName)
            {
                return (EnumImageDownsampleType) getEnum(EnumImageDownsampleType.class, enumName);
            }

            public static EnumImageDownsampleType getEnum(int enumValue)
            {
                return (EnumImageDownsampleType) getEnum(EnumImageDownsampleType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumImageDownsampleType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumImageDownsampleType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumImageDownsampleType.class);
            }

            public static final EnumImageDownsampleType Average = new EnumImageDownsampleType("Average");
            public static final EnumImageDownsampleType Bicubic = new EnumImageDownsampleType("Bicubic");
            public static final EnumImageDownsampleType Subsample = new EnumImageDownsampleType("Subsample");
        }      



        /**
        * Enumeration strings for ImageType
        */

        public static class EnumImageType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumImageType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumImageType getEnum(String enumName)
            {
                return (EnumImageType) getEnum(EnumImageType.class, enumName);
            }

            public static EnumImageType getEnum(int enumValue)
            {
                return (EnumImageType) getEnum(EnumImageType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumImageType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumImageType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumImageType.class);
            }

            public static final EnumImageType Color = new EnumImageType("Color");
            public static final EnumImageType Grayscale = new EnumImageType("Grayscale");
            public static final EnumImageType Monochrome = new EnumImageType("Monochrome");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute AntiAliasImages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AntiAliasImages
          * @param value: the value to set the attribute to
          */
        public void setAntiAliasImages(boolean value)
        {
            setAttribute(AttributeName.ANTIALIASIMAGES, value, null);
        }

        /**
          * (18) get boolean attribute AntiAliasImages
          * @return boolean the value of the attribute
          */
        public boolean getAntiAliasImages()
        {
            return getBoolAttribute(AttributeName.ANTIALIASIMAGES, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute AutoFilterImages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AutoFilterImages
          * @param value: the value to set the attribute to
          */
        public void setAutoFilterImages(boolean value)
        {
            setAttribute(AttributeName.AUTOFILTERIMAGES, value, null);
        }

        /**
          * (18) get boolean attribute AutoFilterImages
          * @return boolean the value of the attribute
          */
        public boolean getAutoFilterImages()
        {
            return getBoolAttribute(AttributeName.AUTOFILTERIMAGES, null, true);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ConvertImagesToIndexed
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ConvertImagesToIndexed
          * @param value: the value to set the attribute to
          */
        public void setConvertImagesToIndexed(boolean value)
        {
            setAttribute(AttributeName.CONVERTIMAGESTOINDEXED, value, null);
        }

        /**
          * (18) get boolean attribute ConvertImagesToIndexed
          * @return boolean the value of the attribute
          */
        public boolean getConvertImagesToIndexed()
        {
            return getBoolAttribute(AttributeName.CONVERTIMAGESTOINDEXED, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute DCTQuality
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute DCTQuality
          * @param value: the value to set the attribute to
          */
        public void setDCTQuality(double value)
        {
            setAttribute(AttributeName.DCTQUALITY, value, null);
        }

        /**
          * (17) get double attribute DCTQuality
          * @return double the value of the attribute
          */
        public double getDCTQuality()
        {
            return getRealAttribute(AttributeName.DCTQUALITY, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute DownsampleImages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute DownsampleImages
          * @param value: the value to set the attribute to
          */
        public void setDownsampleImages(boolean value)
        {
            setAttribute(AttributeName.DOWNSAMPLEIMAGES, value, null);
        }

        /**
          * (18) get boolean attribute DownsampleImages
          * @return boolean the value of the attribute
          */
        public boolean getDownsampleImages()
        {
            return getBoolAttribute(AttributeName.DOWNSAMPLEIMAGES, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute EncodeColorImages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute EncodeColorImages
          * @param value: the value to set the attribute to
          */
        public void setEncodeColorImages(boolean value)
        {
            setAttribute(AttributeName.ENCODECOLORIMAGES, value, null);
        }

        /**
          * (18) get boolean attribute EncodeColorImages
          * @return boolean the value of the attribute
          */
        public boolean getEncodeColorImages()
        {
            return getBoolAttribute(AttributeName.ENCODECOLORIMAGES, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute EncodeImages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute EncodeImages
          * @param value: the value to set the attribute to
          */
        public void setEncodeImages(boolean value)
        {
            setAttribute(AttributeName.ENCODEIMAGES, value, null);
        }

        /**
          * (18) get boolean attribute EncodeImages
          * @return boolean the value of the attribute
          */
        public boolean getEncodeImages()
        {
            return getBoolAttribute(AttributeName.ENCODEIMAGES, null, false);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageAutoFilterStrategy
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ImageAutoFilterStrategy
          * @param value: the value to set the attribute to
          */
        public void setImageAutoFilterStrategy(String value)
        {
            setAttribute(AttributeName.IMAGEAUTOFILTERSTRATEGY, value, null);
        }

        /**
          * (23) get String attribute ImageAutoFilterStrategy
          * @return the value of the attribute
          */
        public String getImageAutoFilterStrategy()
        {
            return getAttribute(AttributeName.IMAGEAUTOFILTERSTRATEGY, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageDepth
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ImageDepth
          * @param value: the value to set the attribute to
          */
        public void setImageDepth(int value)
        {
            setAttribute(AttributeName.IMAGEDEPTH, value, null);
        }

        /**
          * (15) get int attribute ImageDepth
          * @return int the value of the attribute
          */
        public int getImageDepth()
        {
            return getIntAttribute(AttributeName.IMAGEDEPTH, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageDownsampleThreshold
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ImageDownsampleThreshold
          * @param value: the value to set the attribute to
          */
        public void setImageDownsampleThreshold(double value)
        {
            setAttribute(AttributeName.IMAGEDOWNSAMPLETHRESHOLD, value, null);
        }

        /**
          * (17) get double attribute ImageDownsampleThreshold
          * @return double the value of the attribute
          */
        public double getImageDownsampleThreshold()
        {
            return getRealAttribute(AttributeName.IMAGEDOWNSAMPLETHRESHOLD, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageDownsampleType
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute ImageDownsampleType
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setImageDownsampleType(EnumImageDownsampleType enumVar)
        {
            setAttribute(AttributeName.IMAGEDOWNSAMPLETYPE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute ImageDownsampleType
          * @return the value of the attribute
          */
        public EnumImageDownsampleType getImageDownsampleType()
        {
            return EnumImageDownsampleType.getEnum(getAttribute(AttributeName.IMAGEDOWNSAMPLETYPE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageFilter
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ImageFilter
          * @param value: the value to set the attribute to
          */
        public void setImageFilter(String value)
        {
            setAttribute(AttributeName.IMAGEFILTER, value, null);
        }

        /**
          * (23) get String attribute ImageFilter
          * @return the value of the attribute
          */
        public String getImageFilter()
        {
            return getAttribute(AttributeName.IMAGEFILTER, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageResolution
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ImageResolution
          * @param value: the value to set the attribute to
          */
        public void setImageResolution(double value)
        {
            setAttribute(AttributeName.IMAGERESOLUTION, value, null);
        }

        /**
          * (17) get double attribute ImageResolution
          * @return double the value of the attribute
          */
        public double getImageResolution()
        {
            return getRealAttribute(AttributeName.IMAGERESOLUTION, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ImageType
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute ImageType
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setImageType(EnumImageType enumVar)
        {
            setAttribute(AttributeName.IMAGETYPE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute ImageType
          * @return the value of the attribute
          */
        public EnumImageType getImageType()
        {
            return EnumImageType.getEnum(getAttribute(AttributeName.IMAGETYPE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute JPXQuality
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute JPXQuality
          * @param value: the value to set the attribute to
          */
        public void setJPXQuality(int value)
        {
            setAttribute(AttributeName.JPXQUALITY, value, null);
        }

        /**
          * (15) get int attribute JPXQuality
          * @return int the value of the attribute
          */
        public int getJPXQuality()
        {
            return getIntAttribute(AttributeName.JPXQUALITY, null, 0);
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element CCITTFaxParams
     * @return JDFCCITTFaxParams the element
     */
    public JDFCCITTFaxParams getCCITTFaxParams()
    {
        return (JDFCCITTFaxParams) getElement(ElementName.CCITTFAXPARAMS, null, 0);
    }

    /** (25) getCreateCCITTFaxParams
     * 
     * @return JDFCCITTFaxParams the element
     */
    public JDFCCITTFaxParams getCreateCCITTFaxParams()
    {
        return (JDFCCITTFaxParams) getCreateElement_KElement(ElementName.CCITTFAXPARAMS, null, 0);
    }

    /**
     * (29) append element CCITTFaxParams
     */
    public JDFCCITTFaxParams appendCCITTFaxParams() throws JDFException
    {
        return (JDFCCITTFaxParams) appendElementN(ElementName.CCITTFAXPARAMS, 1, null);
    }

    /**
     * (24) const get element DCTParams
     * @return JDFDCTParams the element
     */
    public JDFDCTParams getDCTParams()
    {
        return (JDFDCTParams) getElement(ElementName.DCTPARAMS, null, 0);
    }

    /** (25) getCreateDCTParams
     * 
     * @return JDFDCTParams the element
     */
    public JDFDCTParams getCreateDCTParams()
    {
        return (JDFDCTParams) getCreateElement_KElement(ElementName.DCTPARAMS, null, 0);
    }

    /**
     * (29) append element DCTParams
     */
    public JDFDCTParams appendDCTParams() throws JDFException
    {
        return (JDFDCTParams) appendElementN(ElementName.DCTPARAMS, 1, null);
    }

    /** (26) getCreateJBIG2Params
     * 
     * @param iSkip number of elements to skip
     * @return JDFJBIG2Params the element
     */
    public JDFJBIG2Params getCreateJBIG2Params(int iSkip)
    {
        return (JDFJBIG2Params)getCreateElement_KElement(ElementName.JBIG2PARAMS, null, iSkip);
    }

    /**
     * (27) const get element JBIG2Params
     * @param iSkip number of elements to skip
     * @return JDFJBIG2Params the element
     * default is getJBIG2Params(0)     */
    public JDFJBIG2Params getJBIG2Params(int iSkip)
    {
        return (JDFJBIG2Params) getElement(ElementName.JBIG2PARAMS, null, iSkip);
    }

    /**
     * Get all JBIG2Params from the current element
     * 
     * @return Collection<JDFJBIG2Params>, null if none are available
     */
    public Collection<JDFJBIG2Params> getAllJBIG2Params()
    {
        final VElement vc = getChildElementVector(ElementName.JBIG2PARAMS, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFJBIG2Params> v = new Vector<JDFJBIG2Params>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFJBIG2Params) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element JBIG2Params
     */
    public JDFJBIG2Params appendJBIG2Params() throws JDFException
    {
        return (JDFJBIG2Params) appendElement(ElementName.JBIG2PARAMS, null);
    }

    /** (26) getCreateJPEG2000Params
     * 
     * @param iSkip number of elements to skip
     * @return JDFJPEG2000Params the element
     */
    public JDFJPEG2000Params getCreateJPEG2000Params(int iSkip)
    {
        return (JDFJPEG2000Params)getCreateElement_KElement(ElementName.JPEG2000PARAMS, null, iSkip);
    }

    /**
     * (27) const get element JPEG2000Params
     * @param iSkip number of elements to skip
     * @return JDFJPEG2000Params the element
     * default is getJPEG2000Params(0)     */
    public JDFJPEG2000Params getJPEG2000Params(int iSkip)
    {
        return (JDFJPEG2000Params) getElement(ElementName.JPEG2000PARAMS, null, iSkip);
    }

    /**
     * Get all JPEG2000Params from the current element
     * 
     * @return Collection<JDFJPEG2000Params>, null if none are available
     */
    public Collection<JDFJPEG2000Params> getAllJPEG2000Params()
    {
        final VElement vc = getChildElementVector(ElementName.JPEG2000PARAMS, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFJPEG2000Params> v = new Vector<JDFJPEG2000Params>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFJPEG2000Params) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element JPEG2000Params
     */
    public JDFJPEG2000Params appendJPEG2000Params() throws JDFException
    {
        return (JDFJPEG2000Params) appendElement(ElementName.JPEG2000PARAMS, null);
    }

    /**
     * (24) const get element LZWParams
     * @return JDFLZWParams the element
     */
    public JDFLZWParams getLZWParams()
    {
        return (JDFLZWParams) getElement(ElementName.LZWPARAMS, null, 0);
    }

    /** (25) getCreateLZWParams
     * 
     * @return JDFLZWParams the element
     */
    public JDFLZWParams getCreateLZWParams()
    {
        return (JDFLZWParams) getCreateElement_KElement(ElementName.LZWPARAMS, null, 0);
    }

    /**
     * (29) append element LZWParams
     */
    public JDFLZWParams appendLZWParams() throws JDFException
    {
        return (JDFLZWParams) appendElementN(ElementName.LZWPARAMS, 1, null);
    }

}// end namespace JDF
