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

import java.util.zip.DataFormatException;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.datatypes.JDFIntegerRangeList;
import org.cip4.jdflib.datatypes.JDFMatrix;
import org.cip4.jdflib.datatypes.JDFXYPair;
import org.cip4.jdflib.resource.JDFEdgeGluing;
import org.cip4.jdflib.resource.JDFHardCoverBinding;
import org.cip4.jdflib.resource.JDFSoftCoverBinding;
import org.cip4.jdflib.resource.JDFStripBinding;
import org.cip4.jdflib.resource.JDFTabs;
import org.cip4.jdflib.resource.JDFTape;
import org.cip4.jdflib.resource.process.postpress.JDFChannelBinding;
import org.cip4.jdflib.resource.process.postpress.JDFCoilBinding;
import org.cip4.jdflib.resource.process.postpress.JDFPlasticCombBinding;
import org.cip4.jdflib.resource.process.postpress.JDFRingBinding;
import org.cip4.jdflib.resource.process.postpress.JDFSaddleStitching;
import org.cip4.jdflib.resource.process.postpress.JDFSideSewing;
import org.cip4.jdflib.resource.process.postpress.JDFSideStitching;
import org.cip4.jdflib.resource.process.postpress.JDFThreadSealing;
import org.cip4.jdflib.resource.process.postpress.JDFThreadSewing;
import org.cip4.jdflib.resource.process.postpress.JDFWireCombBinding;
import org.cip4.jdflib.span.JDFSpanBindingType;
    /*
    *****************************************************************************
    class JDFAutoBindItem : public JDFElement

    *****************************************************************************
    */

public abstract class JDFAutoBindItem extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.CHILDFOLIO, 0x33333331, AttributeInfo.EnumAttributeType.XYPair, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.PARENTFOLIO, 0x22222221, AttributeInfo.EnumAttributeType.XYPair, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.TRANSFORMATION, 0x33333331, AttributeInfo.EnumAttributeType.matrix, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.WRAPPAGES, 0x33333331, AttributeInfo.EnumAttributeType.IntegerRangeList, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[17];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.BINDINGTYPE, 0x66666661);
        elemInfoTable[1] = new ElemInfoTable(ElementName.CHANNELBINDING, 0x66666661);
        elemInfoTable[2] = new ElemInfoTable(ElementName.COILBINDING, 0x66666661);
        elemInfoTable[3] = new ElemInfoTable(ElementName.EDGEGLUING, 0x66666661);
        elemInfoTable[4] = new ElemInfoTable(ElementName.HARDCOVERBINDING, 0x66666661);
        elemInfoTable[5] = new ElemInfoTable(ElementName.PLASTICCOMBBINDING, 0x66666661);
        elemInfoTable[6] = new ElemInfoTable(ElementName.RINGBINDING, 0x66666661);
        elemInfoTable[7] = new ElemInfoTable(ElementName.SADDLESTITCHING, 0x66666661);
        elemInfoTable[8] = new ElemInfoTable(ElementName.SIDESEWING, 0x66666661);
        elemInfoTable[9] = new ElemInfoTable(ElementName.SIDESTITCHING, 0x66666661);
        elemInfoTable[10] = new ElemInfoTable(ElementName.SOFTCOVERBINDING, 0x66666661);
        elemInfoTable[11] = new ElemInfoTable(ElementName.TAPE, 0x66666661);
        elemInfoTable[12] = new ElemInfoTable(ElementName.TABS, 0x66666661);
        elemInfoTable[13] = new ElemInfoTable(ElementName.THREADSEALING, 0x66666661);
        elemInfoTable[14] = new ElemInfoTable(ElementName.THREADSEWING, 0x66666661);
        elemInfoTable[15] = new ElemInfoTable(ElementName.STRIPBINDING, 0x66666661);
        elemInfoTable[16] = new ElemInfoTable(ElementName.WIRECOMBBINDING, 0x66666661);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoBindItem
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoBindItem(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoBindItem
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoBindItem(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoBindItem
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoBindItem(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoBindItem[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ChildFolio
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ChildFolio
          * @param value: the value to set the attribute to
          */
        public void setChildFolio(JDFXYPair value)
        {
            setAttribute(AttributeName.CHILDFOLIO, value, null);
        }



        /**
          * (20) get JDFXYPair attribute ChildFolio
          * @return JDFXYPair the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFXYPair
          */
        public JDFXYPair getChildFolio()
        {
            String strAttrName = "";
            JDFXYPair nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.CHILDFOLIO, null, JDFConstants.EMPTYSTRING);
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
        Methods for Attribute ParentFolio
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ParentFolio
          * @param value: the value to set the attribute to
          */
        public void setParentFolio(JDFXYPair value)
        {
            setAttribute(AttributeName.PARENTFOLIO, value, null);
        }



        /**
          * (20) get JDFXYPair attribute ParentFolio
          * @return JDFXYPair the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFXYPair
          */
        public JDFXYPair getParentFolio()
        {
            String strAttrName = "";
            JDFXYPair nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.PARENTFOLIO, null, JDFConstants.EMPTYSTRING);
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
        Methods for Attribute Transformation
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Transformation
          * @param value: the value to set the attribute to
          */
        public void setTransformation(JDFMatrix value)
        {
            setAttribute(AttributeName.TRANSFORMATION, value, null);
        }



        /**
          * (20) get JDFMatrix attribute Transformation
          * @return JDFMatrix the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFMatrix
          */
        public JDFMatrix getTransformation()
        {
            String strAttrName = "";
            JDFMatrix nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.TRANSFORMATION, null, JDFConstants.EMPTYSTRING);
            try
            {
                nPlaceHolder = new JDFMatrix(strAttrName);
            }
            catch(DataFormatException e)
            {
                return null;
            }
            return nPlaceHolder;
        }



        
        /* ---------------------------------------------------------------------
        Methods for Attribute WrapPages
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute WrapPages
          * @param value: the value to set the attribute to
          */
        public void setWrapPages(JDFIntegerRangeList value)
        {
            setAttribute(AttributeName.WRAPPAGES, value, null);
        }



        /**
          * (20) get JDFIntegerRangeList attribute WrapPages
          * @return JDFIntegerRangeList the value of the attribute, null if a the
          *         attribute value is not a valid to create a JDFIntegerRangeList
          */
        public JDFIntegerRangeList getWrapPages()
        {
            String strAttrName = "";
            JDFIntegerRangeList nPlaceHolder = null;
            strAttrName = getAttribute(AttributeName.WRAPPAGES, null, JDFConstants.EMPTYSTRING);
            try
            {
                nPlaceHolder = new JDFIntegerRangeList(strAttrName);
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

    /**
     * (24) const get element BindingType
     * @return JDFSpanBindingType the element
     */
    public JDFSpanBindingType getBindingType()
    {
        return (JDFSpanBindingType) getElement(ElementName.BINDINGTYPE, null, 0);
    }



    /** (25) getCreateBindingType
     * 
     * @return JDFSpanBindingType the element
     */
    public JDFSpanBindingType getCreateBindingType()
    {
        return (JDFSpanBindingType) getCreateElement_KElement(ElementName.BINDINGTYPE, null, 0);
    }





    /**
     * (29) append elementBindingType
     */
    public JDFSpanBindingType appendBindingType() throws JDFException
    {
        return (JDFSpanBindingType) appendElementN(ElementName.BINDINGTYPE, 1, null);
    }
    /**
     * (24) const get element ChannelBinding
     * @return JDFChannelBinding the element
     */
    public JDFChannelBinding getChannelBinding()
    {
        return (JDFChannelBinding) getElement(ElementName.CHANNELBINDING, null, 0);
    }



    /** (25) getCreateChannelBinding
     * 
     * @return JDFChannelBinding the element
     */
    public JDFChannelBinding getCreateChannelBinding()
    {
        return (JDFChannelBinding) getCreateElement_KElement(ElementName.CHANNELBINDING, null, 0);
    }





    /**
     * (29) append elementChannelBinding
     */
    public JDFChannelBinding appendChannelBinding() throws JDFException
    {
        return (JDFChannelBinding) appendElementN(ElementName.CHANNELBINDING, 1, null);
    }
    /**
     * (24) const get element CoilBinding
     * @return JDFCoilBinding the element
     */
    public JDFCoilBinding getCoilBinding()
    {
        return (JDFCoilBinding) getElement(ElementName.COILBINDING, null, 0);
    }



    /** (25) getCreateCoilBinding
     * 
     * @return JDFCoilBinding the element
     */
    public JDFCoilBinding getCreateCoilBinding()
    {
        return (JDFCoilBinding) getCreateElement_KElement(ElementName.COILBINDING, null, 0);
    }





    /**
     * (29) append elementCoilBinding
     */
    public JDFCoilBinding appendCoilBinding() throws JDFException
    {
        return (JDFCoilBinding) appendElementN(ElementName.COILBINDING, 1, null);
    }
    /**
     * (24) const get element EdgeGluing
     * @return JDFEdgeGluing the element
     */
    public JDFEdgeGluing getEdgeGluing()
    {
        return (JDFEdgeGluing) getElement(ElementName.EDGEGLUING, null, 0);
    }



    /** (25) getCreateEdgeGluing
     * 
     * @return JDFEdgeGluing the element
     */
    public JDFEdgeGluing getCreateEdgeGluing()
    {
        return (JDFEdgeGluing) getCreateElement_KElement(ElementName.EDGEGLUING, null, 0);
    }





    /**
     * (29) append elementEdgeGluing
     */
    public JDFEdgeGluing appendEdgeGluing() throws JDFException
    {
        return (JDFEdgeGluing) appendElementN(ElementName.EDGEGLUING, 1, null);
    }
    /**
     * (24) const get element HardCoverBinding
     * @return JDFHardCoverBinding the element
     */
    public JDFHardCoverBinding getHardCoverBinding()
    {
        return (JDFHardCoverBinding) getElement(ElementName.HARDCOVERBINDING, null, 0);
    }



    /** (25) getCreateHardCoverBinding
     * 
     * @return JDFHardCoverBinding the element
     */
    public JDFHardCoverBinding getCreateHardCoverBinding()
    {
        return (JDFHardCoverBinding) getCreateElement_KElement(ElementName.HARDCOVERBINDING, null, 0);
    }





    /**
     * (29) append elementHardCoverBinding
     */
    public JDFHardCoverBinding appendHardCoverBinding() throws JDFException
    {
        return (JDFHardCoverBinding) appendElementN(ElementName.HARDCOVERBINDING, 1, null);
    }
    /**
     * (24) const get element PlasticCombBinding
     * @return JDFPlasticCombBinding the element
     */
    public JDFPlasticCombBinding getPlasticCombBinding()
    {
        return (JDFPlasticCombBinding) getElement(ElementName.PLASTICCOMBBINDING, null, 0);
    }



    /** (25) getCreatePlasticCombBinding
     * 
     * @return JDFPlasticCombBinding the element
     */
    public JDFPlasticCombBinding getCreatePlasticCombBinding()
    {
        return (JDFPlasticCombBinding) getCreateElement_KElement(ElementName.PLASTICCOMBBINDING, null, 0);
    }





    /**
     * (29) append elementPlasticCombBinding
     */
    public JDFPlasticCombBinding appendPlasticCombBinding() throws JDFException
    {
        return (JDFPlasticCombBinding) appendElementN(ElementName.PLASTICCOMBBINDING, 1, null);
    }
    /**
     * (24) const get element RingBinding
     * @return JDFRingBinding the element
     */
    public JDFRingBinding getRingBinding()
    {
        return (JDFRingBinding) getElement(ElementName.RINGBINDING, null, 0);
    }



    /** (25) getCreateRingBinding
     * 
     * @return JDFRingBinding the element
     */
    public JDFRingBinding getCreateRingBinding()
    {
        return (JDFRingBinding) getCreateElement_KElement(ElementName.RINGBINDING, null, 0);
    }





    /**
     * (29) append elementRingBinding
     */
    public JDFRingBinding appendRingBinding() throws JDFException
    {
        return (JDFRingBinding) appendElementN(ElementName.RINGBINDING, 1, null);
    }
    /**
     * (24) const get element SaddleStitching
     * @return JDFSaddleStitching the element
     */
    public JDFSaddleStitching getSaddleStitching()
    {
        return (JDFSaddleStitching) getElement(ElementName.SADDLESTITCHING, null, 0);
    }



    /** (25) getCreateSaddleStitching
     * 
     * @return JDFSaddleStitching the element
     */
    public JDFSaddleStitching getCreateSaddleStitching()
    {
        return (JDFSaddleStitching) getCreateElement_KElement(ElementName.SADDLESTITCHING, null, 0);
    }





    /**
     * (29) append elementSaddleStitching
     */
    public JDFSaddleStitching appendSaddleStitching() throws JDFException
    {
        return (JDFSaddleStitching) appendElementN(ElementName.SADDLESTITCHING, 1, null);
    }
    /**
     * (24) const get element SideSewing
     * @return JDFSideSewing the element
     */
    public JDFSideSewing getSideSewing()
    {
        return (JDFSideSewing) getElement(ElementName.SIDESEWING, null, 0);
    }



    /** (25) getCreateSideSewing
     * 
     * @return JDFSideSewing the element
     */
    public JDFSideSewing getCreateSideSewing()
    {
        return (JDFSideSewing) getCreateElement_KElement(ElementName.SIDESEWING, null, 0);
    }





    /**
     * (29) append elementSideSewing
     */
    public JDFSideSewing appendSideSewing() throws JDFException
    {
        return (JDFSideSewing) appendElementN(ElementName.SIDESEWING, 1, null);
    }
    /**
     * (24) const get element SideStitching
     * @return JDFSideStitching the element
     */
    public JDFSideStitching getSideStitching()
    {
        return (JDFSideStitching) getElement(ElementName.SIDESTITCHING, null, 0);
    }



    /** (25) getCreateSideStitching
     * 
     * @return JDFSideStitching the element
     */
    public JDFSideStitching getCreateSideStitching()
    {
        return (JDFSideStitching) getCreateElement_KElement(ElementName.SIDESTITCHING, null, 0);
    }





    /**
     * (29) append elementSideStitching
     */
    public JDFSideStitching appendSideStitching() throws JDFException
    {
        return (JDFSideStitching) appendElementN(ElementName.SIDESTITCHING, 1, null);
    }
    /**
     * (24) const get element SoftCoverBinding
     * @return JDFSoftCoverBinding the element
     */
    public JDFSoftCoverBinding getSoftCoverBinding()
    {
        return (JDFSoftCoverBinding) getElement(ElementName.SOFTCOVERBINDING, null, 0);
    }



    /** (25) getCreateSoftCoverBinding
     * 
     * @return JDFSoftCoverBinding the element
     */
    public JDFSoftCoverBinding getCreateSoftCoverBinding()
    {
        return (JDFSoftCoverBinding) getCreateElement_KElement(ElementName.SOFTCOVERBINDING, null, 0);
    }





    /**
     * (29) append elementSoftCoverBinding
     */
    public JDFSoftCoverBinding appendSoftCoverBinding() throws JDFException
    {
        return (JDFSoftCoverBinding) appendElementN(ElementName.SOFTCOVERBINDING, 1, null);
    }
    /**
     * (24) const get element Tape
     * @return JDFTape the element
     */
    public JDFTape getTape()
    {
        return (JDFTape) getElement(ElementName.TAPE, null, 0);
    }



    /** (25) getCreateTape
     * 
     * @return JDFTape the element
     */
    public JDFTape getCreateTape()
    {
        return (JDFTape) getCreateElement_KElement(ElementName.TAPE, null, 0);
    }





    /**
     * (29) append elementTape
     */
    public JDFTape appendTape() throws JDFException
    {
        return (JDFTape) appendElementN(ElementName.TAPE, 1, null);
    }
    /**
     * (24) const get element Tabs
     * @return JDFTabs the element
     */
    public JDFTabs getTabs()
    {
        return (JDFTabs) getElement(ElementName.TABS, null, 0);
    }



    /** (25) getCreateTabs
     * 
     * @return JDFTabs the element
     */
    public JDFTabs getCreateTabs()
    {
        return (JDFTabs) getCreateElement_KElement(ElementName.TABS, null, 0);
    }





    /**
     * (29) append elementTabs
     */
    public JDFTabs appendTabs() throws JDFException
    {
        return (JDFTabs) appendElementN(ElementName.TABS, 1, null);
    }
    /**
     * (24) const get element ThreadSealing
     * @return JDFThreadSealing the element
     */
    public JDFThreadSealing getThreadSealing()
    {
        return (JDFThreadSealing) getElement(ElementName.THREADSEALING, null, 0);
    }



    /** (25) getCreateThreadSealing
     * 
     * @return JDFThreadSealing the element
     */
    public JDFThreadSealing getCreateThreadSealing()
    {
        return (JDFThreadSealing) getCreateElement_KElement(ElementName.THREADSEALING, null, 0);
    }





    /**
     * (29) append elementThreadSealing
     */
    public JDFThreadSealing appendThreadSealing() throws JDFException
    {
        return (JDFThreadSealing) appendElementN(ElementName.THREADSEALING, 1, null);
    }
    /**
     * (24) const get element ThreadSewing
     * @return JDFThreadSewing the element
     */
    public JDFThreadSewing getThreadSewing()
    {
        return (JDFThreadSewing) getElement(ElementName.THREADSEWING, null, 0);
    }



    /** (25) getCreateThreadSewing
     * 
     * @return JDFThreadSewing the element
     */
    public JDFThreadSewing getCreateThreadSewing()
    {
        return (JDFThreadSewing) getCreateElement_KElement(ElementName.THREADSEWING, null, 0);
    }





    /**
     * (29) append elementThreadSewing
     */
    public JDFThreadSewing appendThreadSewing() throws JDFException
    {
        return (JDFThreadSewing) appendElementN(ElementName.THREADSEWING, 1, null);
    }
    /**
     * (24) const get element StripBinding
     * @return JDFStripBinding the element
     */
    public JDFStripBinding getStripBinding()
    {
        return (JDFStripBinding) getElement(ElementName.STRIPBINDING, null, 0);
    }



    /** (25) getCreateStripBinding
     * 
     * @return JDFStripBinding the element
     */
    public JDFStripBinding getCreateStripBinding()
    {
        return (JDFStripBinding) getCreateElement_KElement(ElementName.STRIPBINDING, null, 0);
    }





    /**
     * (29) append elementStripBinding
     */
    public JDFStripBinding appendStripBinding() throws JDFException
    {
        return (JDFStripBinding) appendElementN(ElementName.STRIPBINDING, 1, null);
    }
    /**
     * (24) const get element WireCombBinding
     * @return JDFWireCombBinding the element
     */
    public JDFWireCombBinding getWireCombBinding()
    {
        return (JDFWireCombBinding) getElement(ElementName.WIRECOMBBINDING, null, 0);
    }



    /** (25) getCreateWireCombBinding
     * 
     * @return JDFWireCombBinding the element
     */
    public JDFWireCombBinding getCreateWireCombBinding()
    {
        return (JDFWireCombBinding) getCreateElement_KElement(ElementName.WIRECOMBBINDING, null, 0);
    }





    /**
     * (29) append elementWireCombBinding
     */
    public JDFWireCombBinding appendWireCombBinding() throws JDFException
    {
        return (JDFWireCombBinding) appendElementN(ElementName.WIRECOMBBINDING, 1, null);
    }
}// end namespace JDF
