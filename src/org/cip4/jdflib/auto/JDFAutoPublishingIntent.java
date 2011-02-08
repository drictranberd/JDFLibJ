/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2010 The International Cooperation for the Integration of
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
import org.w3c.dom.Element;                         
import org.apache.xerces.dom.CoreDocumentImpl;      
import org.cip4.jdflib.*;                           
import org.cip4.jdflib.auto.*;                      
import org.cip4.jdflib.core.*;                      
import org.cip4.jdflib.core.ElementInfo;                      
import org.cip4.jdflib.span.*;                      
import org.cip4.jdflib.node.*;                      
import org.cip4.jdflib.pool.*;                      
import org.cip4.jdflib.jmf.*;                       
import org.cip4.jdflib.datatypes.*;                 
import org.cip4.jdflib.resource.*;                  
import org.cip4.jdflib.resource.devicecapability.*; 
import org.cip4.jdflib.resource.intent.*;           
import org.cip4.jdflib.resource.process.*;          
import org.cip4.jdflib.resource.process.postpress.*;
import org.cip4.jdflib.resource.process.press.*;    
import org.cip4.jdflib.resource.process.prepress.*; 
import org.cip4.jdflib.util.*;           
    /**
    *****************************************************************************
    class JDFAutoPublishingIntent : public JDFIntentResource

    *****************************************************************************
    */

public abstract class JDFAutoPublishingIntent extends JDFIntentResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[1];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.CONTENTDATAREFS, 0x33331111, AttributeInfo.EnumAttributeType.IDREFS, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }


    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[5];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.ISSUEDATE, 0x55555111);
        elemInfoTable[1] = new ElemInfoTable(ElementName.ISSUENAME, 0x55555111);
        elemInfoTable[2] = new ElemInfoTable(ElementName.ISSUETYPE, 0x55555111);
        elemInfoTable[3] = new ElemInfoTable(ElementName.CIRCULATION, 0x66666111);
        elemInfoTable[4] = new ElemInfoTable(ElementName.CONTENTLIST, 0x66661111);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoPublishingIntent
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoPublishingIntent(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPublishingIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoPublishingIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPublishingIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoPublishingIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoPublishingIntent[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ContentDataRefs
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ContentDataRefs
          * @param value: the value to set the attribute to
          */
        public void setContentDataRefs(VString value)
        {
            setAttribute(AttributeName.CONTENTDATAREFS, value, null);
        }

        /**
          * (21) get VString attribute ContentDataRefs
          * @return VString the value of the attribute
          */
        public VString getContentDataRefs()
        {
            VString vStrAttrib = new VString();
            String  s = getAttribute(AttributeName.CONTENTDATAREFS, null, JDFConstants.EMPTYSTRING);
            vStrAttrib.setAllStrings(s, " ");
            return vStrAttrib;
        }

/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element IssueDate
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getIssueDate()
    {
        return (JDFTimeSpan) getElement(ElementName.ISSUEDATE, null, 0);
    }

    /** (25) getCreateIssueDate
     * 
     * @return JDFTimeSpan the element
     */
    public JDFTimeSpan getCreateIssueDate()
    {
        return (JDFTimeSpan) getCreateElement_KElement(ElementName.ISSUEDATE, null, 0);
    }

    /**
     * (29) append element IssueDate
     */
    public JDFTimeSpan appendIssueDate() throws JDFException
    {
        return (JDFTimeSpan) appendElementN(ElementName.ISSUEDATE, 1, null);
    }

    /**
     * (24) const get element IssueName
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getIssueName()
    {
        return (JDFStringSpan) getElement(ElementName.ISSUENAME, null, 0);
    }

    /** (25) getCreateIssueName
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateIssueName()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.ISSUENAME, null, 0);
    }

    /**
     * (29) append element IssueName
     */
    public JDFStringSpan appendIssueName() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.ISSUENAME, 1, null);
    }

    /**
     * (24) const get element IssueType
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getIssueType()
    {
        return (JDFNameSpan) getElement(ElementName.ISSUETYPE, null, 0);
    }

    /** (25) getCreateIssueType
     * 
     * @return JDFNameSpan the element
     */
    public JDFNameSpan getCreateIssueType()
    {
        return (JDFNameSpan) getCreateElement_KElement(ElementName.ISSUETYPE, null, 0);
    }

    /**
     * (29) append element IssueType
     */
    public JDFNameSpan appendIssueType() throws JDFException
    {
        return (JDFNameSpan) appendElementN(ElementName.ISSUETYPE, 1, null);
    }

    /**
     * (24) const get element Circulation
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getCirculation()
    {
        return (JDFIntegerSpan) getElement(ElementName.CIRCULATION, null, 0);
    }

    /** (25) getCreateCirculation
     * 
     * @return JDFIntegerSpan the element
     */
    public JDFIntegerSpan getCreateCirculation()
    {
        return (JDFIntegerSpan) getCreateElement_KElement(ElementName.CIRCULATION, null, 0);
    }

    /**
     * (29) append element Circulation
     */
    public JDFIntegerSpan appendCirculation() throws JDFException
    {
        return (JDFIntegerSpan) appendElementN(ElementName.CIRCULATION, 1, null);
    }

    /**
     * (24) const get element ContentList
     * @return JDFContentList the element
     */
    public JDFContentList getContentList()
    {
        return (JDFContentList) getElement(ElementName.CONTENTLIST, null, 0);
    }

    /** (25) getCreateContentList
     * 
     * @return JDFContentList the element
     */
    public JDFContentList getCreateContentList()
    {
        return (JDFContentList) getCreateElement_KElement(ElementName.CONTENTLIST, null, 0);
    }

    /**
     * (29) append element ContentList
     */
    public JDFContentList appendContentList() throws JDFException
    {
        return (JDFContentList) appendElementN(ElementName.CONTENTLIST, 1, null);
    }

    /**
      * (31) create inter-resource link to refTarget
      * @param refTarget the element that is referenced
      */
    public void refContentList(JDFContentList refTarget)
    {
        refElement(refTarget);
    }

}// end namespace JDF
