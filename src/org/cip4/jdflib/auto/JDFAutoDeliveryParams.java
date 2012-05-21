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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.process.JDFCompany;
import org.cip4.jdflib.resource.process.JDFContact;
import org.cip4.jdflib.resource.process.JDFDrop;
import org.cip4.jdflib.util.JDFDate;
    /**
    *****************************************************************************
    class JDFAutoDeliveryParams : public JDFResource

    *****************************************************************************
    */

public abstract class JDFAutoDeliveryParams extends JDFResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[6];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.EARLIEST, 0x33333333, AttributeInfo.EnumAttributeType.dateTime, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.METHOD, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.PICKUP, 0x44444433, AttributeInfo.EnumAttributeType.boolean_, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.REQUIRED, 0x33333333, AttributeInfo.EnumAttributeType.dateTime, null, null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.SERVICELEVEL, 0x33333311, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[5] = new AtrInfoTable(AttributeName.TRANSFER, 0x33333311, AttributeInfo.EnumAttributeType.enumeration, EnumTransfer.getEnum(0), null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[3];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.COMPANY, 0x77777776);
        elemInfoTable[1] = new ElemInfoTable(ElementName.CONTACT, 0x33333331);
        elemInfoTable[2] = new ElemInfoTable(ElementName.DROP, 0x22222222);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoDeliveryParams
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoDeliveryParams(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDeliveryParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoDeliveryParams(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoDeliveryParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoDeliveryParams(
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
        return " JDFAutoDeliveryParams[  --> " + super.toString() + " ]";
    }


    /**
     * @return  true if ok
     */
    @Override
    public boolean  init()
    {
        boolean bRet = super.init();
        setResourceClass(JDFResource.EnumResourceClass.Parameter);
        return bRet;
    }


    /**
     * @return the resource Class
     */
    @Override
    public EnumResourceClass getValidClass()
    {
        return JDFResource.EnumResourceClass.Parameter;
    }


        /**
        * Enumeration strings for Transfer
        */

        public static class EnumTransfer extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumTransfer(String name)
            {
                super(name, m_startValue++);
            }

    /**
     * @param enumName the string to convert
     * @return the enum
     */
            public static EnumTransfer getEnum(String enumName)
            {
                return (EnumTransfer) getEnum(EnumTransfer.class, enumName);
            }

    /**
     * @param enumValue the integer to convert
     * @return the enum
     */
            public static EnumTransfer getEnum(int enumValue)
            {
                return (EnumTransfer) getEnum(EnumTransfer.class, enumValue);
            }

    /**
     * @return the map of enums
     */
            public static Map getEnumMap()
            {
                return getEnumMap(EnumTransfer.class);
            }

    /**
     * @return the list of enums
     */
            public static List getEnumList()
            {
                return getEnumList(EnumTransfer.class);
            }

    /**
     * @return the iterator
     */
            public static Iterator iterator()
            {
                return iterator(EnumTransfer.class);
            }

            public static final EnumTransfer BuyerToPrinterDeliver = new EnumTransfer("BuyerToPrinterDeliver");
            public static final EnumTransfer BuyerToPrinterPickup = new EnumTransfer("BuyerToPrinterPickup");
            public static final EnumTransfer PrinterToBuyerDeliver = new EnumTransfer("PrinterToBuyerDeliver");
            public static final EnumTransfer PrinterToBuyerPickup = new EnumTransfer("PrinterToBuyerPickup");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute Earliest
        --------------------------------------------------------------------- */
        /**
          * (11) set attribute Earliest
          * @param value the value to set the attribute to or null
          */
        public void setEarliest(JDFDate value)
        {
            JDFDate date = value;
            if (date == null) date = new JDFDate();
            setAttribute(AttributeName.EARLIEST, date.getDateTimeISO(), null);
        }

        /**
          * (12) get JDFDate attribute Earliest
          * @return JDFDate the value of the attribute
          */
        public JDFDate getEarliest()
        {
            String str = getAttribute(AttributeName.EARLIEST, null, null);
                    JDFDate ret = JDFDate.createDate(str);
            return ret;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Method
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Method
          * @param value the value to set the attribute to
          */
        public void setMethod(String value)
        {
            setAttribute(AttributeName.METHOD, value, null);
        }

        /**
          * (23) get String attribute Method
          * @return the value of the attribute
          */
        public String getMethod()
        {
            return getAttribute(AttributeName.METHOD, null, JDFCoreConstants.EMPTYSTRING);
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

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Required
        --------------------------------------------------------------------- */
        /**
          * (11) set attribute Required
          * @param value the value to set the attribute to or null
          */
        public void setRequired(JDFDate value)
        {
            JDFDate date = value;
            if (date == null) date = new JDFDate();
            setAttribute(AttributeName.REQUIRED, date.getDateTimeISO(), null);
        }

        /**
          * (12) get JDFDate attribute Required
          * @return JDFDate the value of the attribute
          */
        public JDFDate getRequired()
        {
            String str = getAttribute(AttributeName.REQUIRED, null, null);
                    JDFDate ret = JDFDate.createDate(str);
            return ret;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ServiceLevel
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ServiceLevel
          * @param value the value to set the attribute to
          */
        public void setServiceLevel(String value)
        {
            setAttribute(AttributeName.SERVICELEVEL, value, null);
        }

        /**
          * (23) get String attribute ServiceLevel
          * @return the value of the attribute
          */
        public String getServiceLevel()
        {
            return getAttribute(AttributeName.SERVICELEVEL, null, JDFCoreConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Transfer
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Transfer
          * @param enumVar the enumVar to set the attribute to
          */
        public void setTransfer(EnumTransfer enumVar)
        {
            setAttribute(AttributeName.TRANSFER, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Transfer
          * @return the value of the attribute
          */
        public EnumTransfer getTransfer()
        {
            return EnumTransfer.getEnum(getAttribute(AttributeName.TRANSFER, null, null));
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

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

    /** (26) getCreateDrop
     * 
     * @param iSkip number of elements to skip
     * @return JDFDrop the element
     */
    public JDFDrop getCreateDrop(int iSkip)
    {
        return (JDFDrop)getCreateElement_KElement(ElementName.DROP, null, iSkip);
    }

    /**
     * (27) const get element Drop
     * @param iSkip number of elements to skip
     * @return JDFDrop the element
     * default is getDrop(0)     */
    public JDFDrop getDrop(int iSkip)
    {
        return (JDFDrop) getElement(ElementName.DROP, null, iSkip);
    }

    /**
     * Get all Drop from the current element
     * 
     * @return Collection<JDFDrop>, null if none are available
     */
    public Collection<JDFDrop> getAllDrop()
    {
        final VElement vc = getChildElementVector(ElementName.DROP, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFDrop> v = new Vector<JDFDrop>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFDrop) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element Drop
     * @return JDFDrop the element
     */
    public JDFDrop appendDrop()
    {
        return (JDFDrop) appendElement(ElementName.DROP, null);
    }

}// end namespace JDF
