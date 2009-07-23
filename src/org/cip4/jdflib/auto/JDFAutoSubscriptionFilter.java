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
import java.util.Vector;                            
import org.apache.xerces.dom.CoreDocumentImpl;      
import org.cip4.jdflib.core.*;                      
import org.cip4.jdflib.resource.*;

public abstract class JDFAutoSubscriptionFilter extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[8];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.CHANNELID, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.DEVICEID, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.FAMILIES, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.JOBID, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.JOBPARTID, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[5] = new AtrInfoTable(AttributeName.MESSAGETYPES, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
        atrInfoTable[6] = new AtrInfoTable(AttributeName.QUEUEENTRYID, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[7] = new AtrInfoTable(AttributeName.URL, 0x33333333, AttributeInfo.EnumAttributeType.URL, null, null);
    }
    
    @Override
	protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[1];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.PART, 0x33333333);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoSubscriptionFilter
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoSubscriptionFilter(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoSubscriptionFilter
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoSubscriptionFilter(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoSubscriptionFilter
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoSubscriptionFilter(
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
        return " JDFAutoSubscriptionFilter[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ChannelID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ChannelID
          * @param value: the value to set the attribute to
          */
        public void setChannelID(String value)
        {
            setAttribute(AttributeName.CHANNELID, value, null);
        }

        /**
          * (23) get String attribute ChannelID
          * @return the value of the attribute
          */
        public String getChannelID()
        {
            return getAttribute(AttributeName.CHANNELID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute DeviceID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute DeviceID
          * @param value: the value to set the attribute to
          */
        public void setDeviceID(String value)
        {
            setAttribute(AttributeName.DEVICEID, value, null);
        }

        /**
          * (23) get String attribute DeviceID
          * @return the value of the attribute
          */
        public String getDeviceID()
        {
            return getAttribute(AttributeName.DEVICEID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Families
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Families
          * @param value: the value to set the attribute to
          */
        public void setFamilies(VString value)
        {
            setAttribute(AttributeName.FAMILIES, value, null);
        }

        /**
          * (21) get VString attribute Families
          * @return VString the value of the attribute
          */
        public VString getFamilies()
        {
            VString vStrAttrib = new VString();
            String  s = getAttribute(AttributeName.FAMILIES, null, JDFConstants.EMPTYSTRING);
            vStrAttrib.setAllStrings(s, " ");
            return vStrAttrib;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute JobID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute JobID
          * @param value: the value to set the attribute to
          */
        public void setJobID(String value)
        {
            setAttribute(AttributeName.JOBID, value, null);
        }

        /**
          * (23) get String attribute JobID
          * @return the value of the attribute
          */
        public String getJobID()
        {
            return getAttribute(AttributeName.JOBID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute JobPartID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute JobPartID
          * @param value: the value to set the attribute to
          */
        public void setJobPartID(String value)
        {
            setAttribute(AttributeName.JOBPARTID, value, null);
        }

        /**
          * (23) get String attribute JobPartID
          * @return the value of the attribute
          */
        public String getJobPartID()
        {
            return getAttribute(AttributeName.JOBPARTID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MessageTypes
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute MessageTypes
          * @param value: the value to set the attribute to
          */
        public void setMessageTypes(VString value)
        {
            setAttribute(AttributeName.MESSAGETYPES, value, null);
        }

        /**
          * (21) get VString attribute MessageTypes
          * @return VString the value of the attribute
          */
        public VString getMessageTypes()
        {
            VString vStrAttrib = new VString();
            String  s = getAttribute(AttributeName.MESSAGETYPES, null, JDFConstants.EMPTYSTRING);
            vStrAttrib.setAllStrings(s, " ");
            return vStrAttrib;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute QueueEntryID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute QueueEntryID
          * @param value: the value to set the attribute to
          */
        public void setQueueEntryID(String value)
        {
            setAttribute(AttributeName.QUEUEENTRYID, value, null);
        }

        /**
          * (23) get String attribute QueueEntryID
          * @return the value of the attribute
          */
        public String getQueueEntryID()
        {
            return getAttribute(AttributeName.QUEUEENTRYID, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute URL
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute URL
          * @param value: the value to set the attribute to
          */
        public void setURL(String value)
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

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /** (26) getCreatePart
     * 
     * @param iSkip number of elements to skip
     * @return JDFPart the element
     */
    public JDFPart getCreatePart(int iSkip)
    {
        return (JDFPart)getCreateElement_KElement(ElementName.PART, null, iSkip);
    }

    /**
     * (27) const get element Part
     * @param iSkip number of elements to skip
     * @return JDFPart the element
     * default is getPart(0)     */
    public JDFPart getPart(int iSkip)
    {
        return (JDFPart) getElement(ElementName.PART, null, iSkip);
    }

    /**
     * Get all Part from the current element
     * 
     * @return Collection<JDFPart>, null if none are available
     */
    public Collection<JDFPart> getAllPart()
    {
        final VElement vc = getChildElementVector(ElementName.PART, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFPart> v = new Vector<JDFPart>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFPart) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element Part
     */
    public JDFPart appendPart() throws JDFException
    {
        return (JDFPart) appendElement(ElementName.PART, null);
    }

}// end namespace JDF
