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

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.resource.intent.JDFIntentResource;
import org.cip4.jdflib.span.JDFIntegerSpan;
import org.cip4.jdflib.span.JDFNameSpan;
import org.cip4.jdflib.span.JDFNumberSpan;
import org.cip4.jdflib.span.JDFOptionSpan;
import org.cip4.jdflib.span.JDFSpanCoatings;
import org.cip4.jdflib.span.JDFSpanGrainDirection;
import org.cip4.jdflib.span.JDFSpanMediaType;
import org.cip4.jdflib.span.JDFSpanMediaUnit;
import org.cip4.jdflib.span.JDFSpanNamedColor;
import org.cip4.jdflib.span.JDFSpanOpacity;
import org.cip4.jdflib.span.JDFStringSpan;
import org.cip4.jdflib.span.JDFXYPairSpan;
    /*
    *****************************************************************************
    class JDFAutoMediaIntent : public JDFIntentResource

    *****************************************************************************
    */

public abstract class JDFAutoMediaIntent extends JDFIntentResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[3];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.PREPRINTED, 0x33333333, AttributeInfo.EnumAttributeType.boolean_, null, "false");
        atrInfoTable[1] = new AtrInfoTable(AttributeName.MEDIASETCOUNT, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.USERMEDIATYPE, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[24];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.BACKCOATINGS, 0x66666666);
        elemInfoTable[1] = new ElemInfoTable(ElementName.BRIGHTNESS, 0x66666666);
        elemInfoTable[2] = new ElemInfoTable(ElementName.BUYERSUPPLIED, 0x66666666);
        elemInfoTable[3] = new ElemInfoTable(ElementName.DIMENSIONS, 0x77777766);
        elemInfoTable[4] = new ElemInfoTable(ElementName.FRONTCOATINGS, 0x66666666);
        elemInfoTable[5] = new ElemInfoTable(ElementName.GRADE, 0x66666666);
        elemInfoTable[6] = new ElemInfoTable(ElementName.GRAINDIRECTION, 0x66666611);
        elemInfoTable[7] = new ElemInfoTable(ElementName.HOLECOUNT, 0x77777776);
        elemInfoTable[8] = new ElemInfoTable(ElementName.HOLETYPE, 0x66666661);
        elemInfoTable[9] = new ElemInfoTable(ElementName.MEDIACOLOR, 0x66666666);
        elemInfoTable[10] = new ElemInfoTable(ElementName.MEDIACOLORDETAILS, 0x66666611);
        elemInfoTable[11] = new ElemInfoTable(ElementName.MEDIATYPE, 0x66666661);
        elemInfoTable[12] = new ElemInfoTable(ElementName.MEDIATYPEDETAILS, 0x66666111);
        elemInfoTable[13] = new ElemInfoTable(ElementName.MEDIAUNIT, 0x77777766);
        elemInfoTable[14] = new ElemInfoTable(ElementName.OPACITY, 0x66666666);
        elemInfoTable[15] = new ElemInfoTable(ElementName.OPACITYLEVEL, 0x66666611);
        elemInfoTable[16] = new ElemInfoTable(ElementName.RECYCLED, 0x77777766);
        elemInfoTable[17] = new ElemInfoTable(ElementName.RECYCLEDPERCENTAGE, 0x66666611);
        elemInfoTable[18] = new ElemInfoTable(ElementName.STOCKBRAND, 0x66666666);
        elemInfoTable[19] = new ElemInfoTable(ElementName.STOCKTYPE, 0x66666666);
        elemInfoTable[20] = new ElemInfoTable(ElementName.TEXTURE, 0x66666666);
        elemInfoTable[21] = new ElemInfoTable(ElementName.THICKNESS, 0x66666661);
        elemInfoTable[22] = new ElemInfoTable(ElementName.USWEIGHT, 0x77777766);
        elemInfoTable[23] = new ElemInfoTable(ElementName.WEIGHT, 0x66666666);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoMediaIntent
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoMediaIntent(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoMediaIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoMediaIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoMediaIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoMediaIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoMediaIntent[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute PrePrinted
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute PrePrinted
          * @param value: the value to set the attribute to
          */
        public void setPrePrinted(boolean value)
        {
            setAttribute(AttributeName.PREPRINTED, value, null);
        }



        /**
          * (18) get boolean attribute PrePrinted
          * @return boolean the value of the attribute
          */
        public boolean getPrePrinted()
        {
            return getBoolAttribute(AttributeName.PREPRINTED, null, false);
        }



        
        /* ---------------------------------------------------------------------
        Methods for Attribute MediaSetCount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute MediaSetCount
          * @param value: the value to set the attribute to
          */
        public void setMediaSetCount(int value)
        {
            setAttribute(AttributeName.MEDIASETCOUNT, value, null);
        }



        /**
          * (15) get int attribute MediaSetCount
          * @return int the value of the attribute
          */
        public int getMediaSetCount()
        {
            return getIntAttribute(AttributeName.MEDIASETCOUNT, null, 0);
        }



        
        /* ---------------------------------------------------------------------
        Methods for Attribute UserMediaType
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute UserMediaType
          * @param value: the value to set the attribute to
          */
        public void setUserMediaType(String value)
        {
            setAttribute(AttributeName.USERMEDIATYPE, value, null);
        }



        /**
          * (23) get String attribute UserMediaType
          * @return the value of the attribute
          */
        public String getUserMediaType()
        {
            return getAttribute(AttributeName.USERMEDIATYPE, null, JDFConstants.EMPTYSTRING);
        }



/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element BackCoatings
     * @return JDFSpanCoatings the element
     */
    public JDFSpanCoatings getBackCoatings()
    {
        return (JDFSpanCoatings) getElement(ElementName.BACKCOATINGS, null, 0);
    }



    /** (25) getCreateBackCoatings
     * 
     * @return JDFSpanCoatings the element
     */
    public JDFSpanCoatings getCreateBackCoatings()
    {
        return (JDFSpanCoatings) getCreateElement_KElement(ElementName.BACKCOATINGS, null, 0);
    }





    /**
     * (29) append elementBackCoatings
     */
    public JDFSpanCoatings appendBackCoatings() throws JDFException
    {
        return (JDFSpanCoatings) appendElementN(ElementName.BACKCOATINGS, 1, null);
    }
    /**
     * (24) const get element Brightness
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getBrightness()
    {
        return (JDFNumberSpan) getElement(ElementName.BRIGHTNESS, null, 0);
    }



    /** (25) getCreateBrightness
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateBrightness()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.BRIGHTNESS, null, 0);
    }





    /**
     * (29) append elementBrightness
     */
    public JDFNumberSpan appendBrightness() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.BRIGHTNESS, 1, null);
    }
    /**
     * (24) const get element BuyerSupplied
     * @return JDFOptionSpan the element
     */
    public JDFOptionSpan getBuyerSupplied()
    {
        return (JDFOptionSpan) getElement(ElementName.BUYERSUPPLIED, null, 0);
    }



    /** (25) getCreateBuyerSupplied
     * 
     * @return JDFOptionSpan the element
     */
    public JDFOptionSpan getCreateBuyerSupplied()
    {
        return (JDFOptionSpan) getCreateElement_KElement(ElementName.BUYERSUPPLIED, null, 0);
    }





    /**
     * (29) append elementBuyerSupplied
     */
    public JDFOptionSpan appendBuyerSupplied() throws JDFException
    {
        return (JDFOptionSpan) appendElementN(ElementName.BUYERSUPPLIED, 1, null);
    }
    /**
     * (24) const get element Dimensions
     * @return JDFXYPairSpan the element
     */
    public JDFXYPairSpan getDimensions()
    {
        return (JDFXYPairSpan) getElement(ElementName.DIMENSIONS, null, 0);
    }



    /** (25) getCreateDimensions
     * 
     * @return JDFXYPairSpan the element
     */
    public JDFXYPairSpan getCreateDimensions()
    {
        return (JDFXYPairSpan) getCreateElement_KElement(ElementName.DIMENSIONS, null, 0);
    }





    /**
     * (29) append elementDimensions
     */
    public JDFXYPairSpan appendDimensions() throws JDFException
    {
        return (JDFXYPairSpan) appendElementN(ElementName.DIMENSIONS, 1, null);
    }
    /**
     * (24) const get element FrontCoatings
     * @return JDFSpanCoatings the element
     */
    public JDFSpanCoatings getFrontCoatings()
    {
        return (JDFSpanCoatings) getElement(ElementName.FRONTCOATINGS, null, 0);
    }



    /** (25) getCreateFrontCoatings
     * 
     * @return JDFSpanCoatings the element
     */
    public JDFSpanCoatings getCreateFrontCoatings()
    {
        return (JDFSpanCoatings) getCreateElement_KElement(ElementName.FRONTCOATINGS, null, 0);
    }





    /**
     * (29) append elementFrontCoatings
     */
    public JDFSpanCoatings appendFrontCoatings() throws JDFException
    {
        return (JDFSpanCoatings) appendElementN(ElementName.FRONTCOATINGS, 1, null);
    }
    /**
     * (24) const get element Grade
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getGrade()
    {
        return (JDFIntegerSpan) getElement(ElementName.GRADE, null, 0);
    }



    /** (25) getCreateGrade
     * 
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getCreateGrade()
    {
        return (JDFIntegerSpan) getCreateElement_KElement(ElementName.GRADE, null, 0);
    }





    /**
     * (29) append elementGrade
     */
    public JDFIntegerSpan appendGrade() throws JDFException
    {
        return (JDFIntegerSpan) appendElementN(ElementName.GRADE, 1, null);
    }
    /**
     * (24) const get element GrainDirection
     * @return JDFSpanGrainDirection the element
     */
    public JDFSpanGrainDirection getGrainDirection()
    {
        return (JDFSpanGrainDirection) getElement(ElementName.GRAINDIRECTION, null, 0);
    }



    /** (25) getCreateGrainDirection
     * 
     * @return JDFSpanGrainDirection the element
     */
    public JDFSpanGrainDirection getCreateGrainDirection()
    {
        return (JDFSpanGrainDirection) getCreateElement_KElement(ElementName.GRAINDIRECTION, null, 0);
    }





    /**
     * (29) append elementGrainDirection
     */
    public JDFSpanGrainDirection appendGrainDirection() throws JDFException
    {
        return (JDFSpanGrainDirection) appendElementN(ElementName.GRAINDIRECTION, 1, null);
    }
    /**
     * (24) const get element HoleCount
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getHoleCount()
    {
        return (JDFIntegerSpan) getElement(ElementName.HOLECOUNT, null, 0);
    }



    /** (25) getCreateHoleCount
     * 
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getCreateHoleCount()
    {
        return (JDFIntegerSpan) getCreateElement_KElement(ElementName.HOLECOUNT, null, 0);
    }





    /**
     * (29) append elementHoleCount
     */
    public JDFIntegerSpan appendHoleCount() throws JDFException
    {
        return (JDFIntegerSpan) appendElementN(ElementName.HOLECOUNT, 1, null);
    }
    /**
     * (24) const get element HoleType
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getHoleType()
    {
        return (JDFStringSpan) getElement(ElementName.HOLETYPE, null, 0);
    }



    /** (25) getCreateHoleType
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateHoleType()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.HOLETYPE, null, 0);
    }





    /**
     * (29) append elementHoleType
     */
    public JDFStringSpan appendHoleType() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.HOLETYPE, 1, null);
    }
    /**
     * (24) const get element MediaColor
     * @return JDFSpanNamedColor the element
     */
    public JDFSpanNamedColor getMediaColor()
    {
        return (JDFSpanNamedColor) getElement(ElementName.MEDIACOLOR, null, 0);
    }



    /** (25) getCreateMediaColor
     * 
     * @return JDFSpanNamedColor the element
     */
    public JDFSpanNamedColor getCreateMediaColor()
    {
        return (JDFSpanNamedColor) getCreateElement_KElement(ElementName.MEDIACOLOR, null, 0);
    }





    /**
     * (29) append elementMediaColor
     */
    public JDFSpanNamedColor appendMediaColor() throws JDFException
    {
        return (JDFSpanNamedColor) appendElementN(ElementName.MEDIACOLOR, 1, null);
    }
    /**
     * (24) const get element MediaColorDetails
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getMediaColorDetails()
    {
        return (JDFStringSpan) getElement(ElementName.MEDIACOLORDETAILS, null, 0);
    }



    /** (25) getCreateMediaColorDetails
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateMediaColorDetails()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.MEDIACOLORDETAILS, null, 0);
    }





    /**
     * (29) append elementMediaColorDetails
     */
    public JDFStringSpan appendMediaColorDetails() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.MEDIACOLORDETAILS, 1, null);
    }
    /**
     * (24) const get element MediaType
     * @return JDFSpanMediaType the element
     */
    public JDFSpanMediaType getMediaType()
    {
        return (JDFSpanMediaType) getElement(ElementName.MEDIATYPE, null, 0);
    }



    /** (25) getCreateMediaType
     * 
     * @return JDFSpanMediaType the element
     */
    public JDFSpanMediaType getCreateMediaType()
    {
        return (JDFSpanMediaType) getCreateElement_KElement(ElementName.MEDIATYPE, null, 0);
    }





    /**
     * (29) append elementMediaType
     */
    public JDFSpanMediaType appendMediaType() throws JDFException
    {
        return (JDFSpanMediaType) appendElementN(ElementName.MEDIATYPE, 1, null);
    }
    /**
     * (24) const get element MediaTypeDetails
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getMediaTypeDetails()
    {
        return (JDFNameSpan) getElement(ElementName.MEDIATYPEDETAILS, null, 0);
    }



    /** (25) getCreateMediaTypeDetails
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateMediaTypeDetails()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.MEDIATYPEDETAILS, null, 0);
    }





    /**
     * (29) append elementMediaTypeDetails
     */
    public JDFNameSpan appendMediaTypeDetails() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.MEDIATYPEDETAILS, 1, null);
    }
    /**
     * (24) const get element MediaUnit
     * @return JDFSpanMediaUnit the element
     */
    public JDFSpanMediaUnit getMediaUnit()
    {
        return (JDFSpanMediaUnit) getElement(ElementName.MEDIAUNIT, null, 0);
    }



    /** (25) getCreateMediaUnit
     * 
     * @return JDFSpanMediaUnit the element
     */
    public JDFSpanMediaUnit getCreateMediaUnit()
    {
        return (JDFSpanMediaUnit) getCreateElement_KElement(ElementName.MEDIAUNIT, null, 0);
    }





    /**
     * (29) append elementMediaUnit
     */
    public JDFSpanMediaUnit appendMediaUnit() throws JDFException
    {
        return (JDFSpanMediaUnit) appendElementN(ElementName.MEDIAUNIT, 1, null);
    }
    /**
     * (24) const get element Opacity
     * @return JDFSpanOpacity the element
     */
    public JDFSpanOpacity getOpacity()
    {
        return (JDFSpanOpacity) getElement(ElementName.OPACITY, null, 0);
    }



    /** (25) getCreateOpacity
     * 
     * @return JDFSpanOpacity the element
     */
    public JDFSpanOpacity getCreateOpacity()
    {
        return (JDFSpanOpacity) getCreateElement_KElement(ElementName.OPACITY, null, 0);
    }





    /**
     * (29) append elementOpacity
     */
    public JDFSpanOpacity appendOpacity() throws JDFException
    {
        return (JDFSpanOpacity) appendElementN(ElementName.OPACITY, 1, null);
    }
    /**
     * (24) const get element OpacityLevel
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getOpacityLevel()
    {
        return (JDFNumberSpan) getElement(ElementName.OPACITYLEVEL, null, 0);
    }



    /** (25) getCreateOpacityLevel
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateOpacityLevel()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.OPACITYLEVEL, null, 0);
    }





    /**
     * (29) append elementOpacityLevel
     */
    public JDFNumberSpan appendOpacityLevel() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.OPACITYLEVEL, 1, null);
    }
    /**
     * (24) const get element Recycled
     * @return JDFOptionSpan the element
     */
    public JDFOptionSpan getRecycled()
    {
        return (JDFOptionSpan) getElement(ElementName.RECYCLED, null, 0);
    }



    /** (25) getCreateRecycled
     * 
     * @return JDFOptionSpan the element
     */
    public JDFOptionSpan getCreateRecycled()
    {
        return (JDFOptionSpan) getCreateElement_KElement(ElementName.RECYCLED, null, 0);
    }





    /**
     * (29) append elementRecycled
     */
    public JDFOptionSpan appendRecycled() throws JDFException
    {
        return (JDFOptionSpan) appendElementN(ElementName.RECYCLED, 1, null);
    }
    /**
     * (24) const get element RecycledPercentage
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getRecycledPercentage()
    {
        return (JDFNumberSpan) getElement(ElementName.RECYCLEDPERCENTAGE, null, 0);
    }



    /** (25) getCreateRecycledPercentage
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateRecycledPercentage()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.RECYCLEDPERCENTAGE, null, 0);
    }





    /**
     * (29) append elementRecycledPercentage
     */
    public JDFNumberSpan appendRecycledPercentage() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.RECYCLEDPERCENTAGE, 1, null);
    }
    /**
     * (24) const get element StockBrand
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getStockBrand()
    {
        return (JDFStringSpan) getElement(ElementName.STOCKBRAND, null, 0);
    }



    /** (25) getCreateStockBrand
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateStockBrand()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.STOCKBRAND, null, 0);
    }





    /**
     * (29) append elementStockBrand
     */
    public JDFStringSpan appendStockBrand() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.STOCKBRAND, 1, null);
    }
    /**
     * (24) const get element StockType
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getStockType()
    {
        return (JDFNameSpan) getElement(ElementName.STOCKTYPE, null, 0);
    }



    /** (25) getCreateStockType
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateStockType()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.STOCKTYPE, null, 0);
    }





    /**
     * (29) append elementStockType
     */
    public JDFNameSpan appendStockType() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.STOCKTYPE, 1, null);
    }
    /**
     * (24) const get element Texture
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getTexture()
    {
        return (JDFNameSpan) getElement(ElementName.TEXTURE, null, 0);
    }



    /** (25) getCreateTexture
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateTexture()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.TEXTURE, null, 0);
    }





    /**
     * (29) append elementTexture
     */
    public JDFNameSpan appendTexture() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.TEXTURE, 1, null);
    }
    /**
     * (24) const get element Thickness
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getThickness()
    {
        return (JDFNumberSpan) getElement(ElementName.THICKNESS, null, 0);
    }



    /** (25) getCreateThickness
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateThickness()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.THICKNESS, null, 0);
    }





    /**
     * (29) append elementThickness
     */
    public JDFNumberSpan appendThickness() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.THICKNESS, 1, null);
    }
    /**
     * (24) const get element USWeight
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getUSWeight()
    {
        return (JDFNumberSpan) getElement(ElementName.USWEIGHT, null, 0);
    }



    /** (25) getCreateUSWeight
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateUSWeight()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.USWEIGHT, null, 0);
    }





    /**
     * (29) append elementUSWeight
     */
    public JDFNumberSpan appendUSWeight() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.USWEIGHT, 1, null);
    }
    /**
     * (24) const get element Weight
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getWeight()
    {
        return (JDFNumberSpan) getElement(ElementName.WEIGHT, null, 0);
    }



    /** (25) getCreateWeight
     * 
     * @return JDFNumberSpan the element
     */
    public JDFNumberSpan getCreateWeight()
    {
        return (JDFNumberSpan) getCreateElement_KElement(ElementName.WEIGHT, null, 0);
    }





    /**
     * (29) append elementWeight
     */
    public JDFNumberSpan appendWeight() throws JDFException
    {
        return (JDFNumberSpan) appendElementN(ElementName.WEIGHT, 1, null);
    }
}// end namespace JDF
