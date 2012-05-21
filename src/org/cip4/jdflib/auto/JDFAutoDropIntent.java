/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2012 The International Cooperation for the Integration of
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
import java.util.Vector;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.resource.intent.JDFDropItemIntent;
import org.cip4.jdflib.resource.intent.JDFPricing;
import org.cip4.jdflib.resource.process.JDFCompany;
import org.cip4.jdflib.resource.process.JDFContact;
import org.cip4.jdflib.span.JDFDurationSpan;
import org.cip4.jdflib.span.JDFNameSpan;
import org.cip4.jdflib.span.JDFSpanSurplusHandling;
import org.cip4.jdflib.span.JDFSpanTransfer;
import org.cip4.jdflib.span.JDFStringSpan;
import org.cip4.jdflib.span.JDFTimeSpan;
    /**
    *****************************************************************************
    class JDFAutoDropIntent : public JDFElement

    *****************************************************************************
    */

public abstract class JDFAutoDropIntent extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[3];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ADDITIONALAMOUNT, 0x44444311, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.BUYERACCOUNT, 0x33333311, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.PICKUP, 0x44444443, AttributeInfo.EnumAttributeType.boolean_, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[13];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.EARLIEST, 0x66666666);
        elemInfoTable[1] = new ElemInfoTable(ElementName.EARLIESTDURATION, 0x66666666);
        elemInfoTable[2] = new ElemInfoTable(ElementName.METHOD, 0x66666666);
        elemInfoTable[3] = new ElemInfoTable(ElementName.REQUIRED, 0x66666666);
        elemInfoTable[4] = new ElemInfoTable(ElementName.REQUIREDDURATION, 0x66666666);
        elemInfoTable[5] = new ElemInfoTable(ElementName.RETURNMETHOD, 0x66666661);
        elemInfoTable[6] = new ElemInfoTable(ElementName.SERVICELEVEL, 0x66666611);
        elemInfoTable[7] = new ElemInfoTable(ElementName.SURPLUSHANDLING, 0x66666661);
        elemInfoTable[8] = new ElemInfoTable(ElementName.TRANSFER, 0x66666661);
        elemInfoTable[9] = new ElemInfoTable(ElementName.COMPANY, 0x77777776);
        elemInfoTable[10] = new ElemInfoTable(ElementName.CONTACT, 0x33333331);
        elemInfoTable[11] = new ElemInfoTable(ElementName.DROPITEMINTENT, 0x22222222);
        elemInfoTable[12] = new ElemInfoTable(ElementName.PRICING, 0x77777666);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoDropIntent
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoDropIntent(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDropIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoDropIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDropIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoDropIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    /**
     * @return  the string representation
     */
    @Override
    public String toString()
    {
        return " JDFAutoDropIntent[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute AdditionalAmount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AdditionalAmount
          * @param value the value to set the attribute to
          */
        public void setAdditionalAmount(int value)
        {
            setAttribute(AttributeName.ADDITIONALAMOUNT, value, null);
        }

        /**
          * (15) get int attribute AdditionalAmount
          * @return int the value of the attribute
          */
        public int getAdditionalAmount()
        {
            return getIntAttribute(AttributeName.ADDITIONALAMOUNT, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute BuyerAccount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute BuyerAccount
          * @param value the value to set the attribute to
          */
        public void setBuyerAccount(String value)
        {
            setAttribute(AttributeName.BUYERACCOUNT, value, null);
        }

        /**
          * (23) get String attribute BuyerAccount
          * @return the value of the attribute
          */
        public String getBuyerAccount()
        {
            return getAttribute(AttributeName.BUYERACCOUNT, null, JDFCoreConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Pickup
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Pickup
          * @param value the value to set the attribute to
          */
        public void setPickup(boolean value)
        {
            setAttribute(AttributeName.PICKUP, value, null);
        }

        /**
          * (18) get boolean attribute Pickup
          * @return boolean the value of the attribute
          */
        public boolean getPickup()
        {
            return getBoolAttribute(AttributeName.PICKUP, null, false);
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element Earliest
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getEarliest()
    {
        return (JDFTimeSpan) getElement(ElementName.EARLIEST, null, 0);
    }

    /** (25) getCreateEarliest
     * 
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getCreateEarliest()
    {
        return (JDFTimeSpan) getCreateElement_KElement(ElementName.EARLIEST, null, 0);
    }

    /**
     * (29) append element Earliest
     * @return JDFTimeSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFTimeSpan appendEarliest() throws JDFException
    {
        return (JDFTimeSpan) appendElementN(ElementName.EARLIEST, 1, null);
    }

    /**
     * (24) const get element EarliestDuration
     * @return JDFDurationSpan the element
     */
    public JDFDurationSpan getEarliestDuration()
    {
        return (JDFDurationSpan) getElement(ElementName.EARLIESTDURATION, null, 0);
    }

    /** (25) getCreateEarliestDuration
     * 
     * @return JDFDurationSpan the element
     */
    public JDFDurationSpan getCreateEarliestDuration()
    {
        return (JDFDurationSpan) getCreateElement_KElement(ElementName.EARLIESTDURATION, null, 0);
    }

    /**
     * (29) append element EarliestDuration
     * @return JDFDurationSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFDurationSpan appendEarliestDuration() throws JDFException
    {
        return (JDFDurationSpan) appendElementN(ElementName.EARLIESTDURATION, 1, null);
    }

    /**
     * (24) const get element Method
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getMethod()
    {
        return (JDFNameSpan) getElement(ElementName.METHOD, null, 0);
    }

    /** (25) getCreateMethod
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateMethod()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.METHOD, null, 0);
    }

    /**
     * (29) append element Method
     * @return JDFNameSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFNameSpan appendMethod() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.METHOD, 1, null);
    }

    /**
     * (24) const get element Required
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getRequired()
    {
        return (JDFTimeSpan) getElement(ElementName.REQUIRED, null, 0);
    }

    /** (25) getCreateRequired
     * 
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getCreateRequired()
    {
        return (JDFTimeSpan) getCreateElement_KElement(ElementName.REQUIRED, null, 0);
    }

    /**
     * (29) append element Required
     * @return JDFTimeSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFTimeSpan appendRequired() throws JDFException
    {
        return (JDFTimeSpan) appendElementN(ElementName.REQUIRED, 1, null);
    }

    /**
     * (24) const get element RequiredDuration
     * @return JDFDurationSpan the element
     */
    public JDFDurationSpan getRequiredDuration()
    {
        return (JDFDurationSpan) getElement(ElementName.REQUIREDDURATION, null, 0);
    }

    /** (25) getCreateRequiredDuration
     * 
     * @return JDFDurationSpan the element
     */
    public JDFDurationSpan getCreateRequiredDuration()
    {
        return (JDFDurationSpan) getCreateElement_KElement(ElementName.REQUIREDDURATION, null, 0);
    }

    /**
     * (29) append element RequiredDuration
     * @return JDFDurationSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFDurationSpan appendRequiredDuration() throws JDFException
    {
        return (JDFDurationSpan) appendElementN(ElementName.REQUIREDDURATION, 1, null);
    }

    /**
     * (24) const get element ReturnMethod
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getReturnMethod()
    {
        return (JDFNameSpan) getElement(ElementName.RETURNMETHOD, null, 0);
    }

    /** (25) getCreateReturnMethod
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateReturnMethod()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.RETURNMETHOD, null, 0);
    }

    /**
     * (29) append element ReturnMethod
     * @return JDFNameSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFNameSpan appendReturnMethod() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.RETURNMETHOD, 1, null);
    }

    /**
     * (24) const get element ServiceLevel
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getServiceLevel()
    {
        return (JDFStringSpan) getElement(ElementName.SERVICELEVEL, null, 0);
    }

    /** (25) getCreateServiceLevel
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateServiceLevel()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.SERVICELEVEL, null, 0);
    }

    /**
     * (29) append element ServiceLevel
     * @return JDFStringSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFStringSpan appendServiceLevel() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.SERVICELEVEL, 1, null);
    }

    /**
     * (24) const get element SurplusHandling
     * @return JDFSpanSurplusHandling the element
     */
    public JDFSpanSurplusHandling getSurplusHandling()
    {
        return (JDFSpanSurplusHandling) getElement(ElementName.SURPLUSHANDLING, null, 0);
    }

    /** (25) getCreateSurplusHandling
     * 
     * @return JDFSpanSurplusHandling the element
     */
    public JDFSpanSurplusHandling getCreateSurplusHandling()
    {
        return (JDFSpanSurplusHandling) getCreateElement_KElement(ElementName.SURPLUSHANDLING, null, 0);
    }

    /**
     * (29) append element SurplusHandling
     * @return JDFSpanSurplusHandling the element
     * @throws JDFException if the element already exists
     */
    public JDFSpanSurplusHandling appendSurplusHandling() throws JDFException
    {
        return (JDFSpanSurplusHandling) appendElementN(ElementName.SURPLUSHANDLING, 1, null);
    }

    /**
     * (24) const get element Transfer
     * @return JDFSpanTransfer the element
     */
    public JDFSpanTransfer getTransfer()
    {
        return (JDFSpanTransfer) getElement(ElementName.TRANSFER, null, 0);
    }

    /** (25) getCreateTransfer
     * 
     * @return JDFSpanTransfer the element
     */
    public JDFSpanTransfer getCreateTransfer()
    {
        return (JDFSpanTransfer) getCreateElement_KElement(ElementName.TRANSFER, null, 0);
    }

    /**
     * (29) append element Transfer
     * @return JDFSpanTransfer the element
     * @throws JDFException if the element already exists
     */
    public JDFSpanTransfer appendTransfer() throws JDFException
    {
        return (JDFSpanTransfer) appendElementN(ElementName.TRANSFER, 1, null);
    }

    /**
     * (24) const get element Company
     * @return JDFCompany the element
     */
    public JDFCompany getCompany()
    {
        return (JDFCompany) getElement(ElementName.COMPANY, null, 0);
    }

    /** (25) getCreateCompany
     * 
     * @return JDFCompany the element
     */
    public JDFCompany getCreateCompany()
    {
        return (JDFCompany) getCreateElement_KElement(ElementName.COMPANY, null, 0);
    }

    /**
     * (29) append element Company
     * @return JDFCompany the element
     * @throws JDFException if the element already exists
     */
    public JDFCompany appendCompany() throws JDFException
    {
        return (JDFCompany) appendElementN(ElementName.COMPANY, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refCompany(JDFCompany refTarget)
    {
        refElement(refTarget);
    }

    /** (26) getCreateContact
     * 
     * @param iSkip number of elements to skip
     * @return JDFContact the element
     */
    public JDFContact getCreateContact(int iSkip)
    {
        return (JDFContact)getCreateElement_KElement(ElementName.CONTACT, null, iSkip);
    }

    /**
     * (27) const get element Contact
     * @param iSkip number of elements to skip
     * @return JDFContact the element
     * default is getContact(0)     */
    public JDFContact getContact(int iSkip)
    {
        return (JDFContact) getElement(ElementName.CONTACT, null, iSkip);
    }

    /**
     * Get all Contact from the current element
     * 
     * @return Collection<JDFContact>, null if none are available
     */
    public Collection<JDFContact> getAllContact()
    {
        final VElement vc = getChildElementVector(ElementName.CONTACT, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFContact> v = new Vector<JDFContact>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFContact) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element Contact
     * @return JDFContact the element
     */
    public JDFContact appendContact()
    {
        return (JDFContact) appendElement(ElementName.CONTACT, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refContact(JDFContact refTarget)
    {
        refElement(refTarget);
    }

    /** (26) getCreateDropItemIntent
     * 
     * @param iSkip number of elements to skip
     * @return JDFDropItemIntent the element
     */
    public JDFDropItemIntent getCreateDropItemIntent(int iSkip)
    {
        return (JDFDropItemIntent)getCreateElement_KElement(ElementName.DROPITEMINTENT, null, iSkip);
    }

    /**
     * (27) const get element DropItemIntent
     * @param iSkip number of elements to skip
     * @return JDFDropItemIntent the element
     * default is getDropItemIntent(0)     */
    public JDFDropItemIntent getDropItemIntent(int iSkip)
    {
        return (JDFDropItemIntent) getElement(ElementName.DROPITEMINTENT, null, iSkip);
    }

    /**
     * Get all DropItemIntent from the current element
     * 
     * @return Collection<JDFDropItemIntent>, null if none are available
     */
    public Collection<JDFDropItemIntent> getAllDropItemIntent()
    {
        final VElement vc = getChildElementVector(ElementName.DROPITEMINTENT, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFDropItemIntent> v = new Vector<JDFDropItemIntent>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFDropItemIntent) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element DropItemIntent
     * @return JDFDropItemIntent the element
     */
    public JDFDropItemIntent appendDropItemIntent()
    {
        return (JDFDropItemIntent) appendElement(ElementName.DROPITEMINTENT, null);
    }

    /**
     * (24) const get element Pricing
     * @return JDFPricing the element
     */
    public JDFPricing getPricing()
    {
        return (JDFPricing) getElement(ElementName.PRICING, null, 0);
    }

    /** (25) getCreatePricing
     * 
     * @return JDFPricing the element
     */
    public JDFPricing getCreatePricing()
    {
        return (JDFPricing) getCreateElement_KElement(ElementName.PRICING, null, 0);
    }

    /**
     * (29) append element Pricing
     * @return JDFPricing the element
     * @throws JDFException if the element already exists
     */
    public JDFPricing appendPricing() throws JDFException
    {
        return (JDFPricing) appendElementN(ElementName.PRICING, 1, null);
    }

}// end namespace JDF
