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
    class JDFAutoNumberingIntent : public JDFIntentResource

    *****************************************************************************
    */

public abstract class JDFAutoNumberingIntent extends JDFIntentResource
{

    private static final long serialVersionUID = 1L;

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[4];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.COLORNAME, 0x66666666);
        elemInfoTable[1] = new ElemInfoTable(ElementName.COLORNAMEDETAILS, 0x33331111);
        elemInfoTable[2] = new ElemInfoTable(ElementName.COLORPOOL, 0x66666666);
        elemInfoTable[3] = new ElemInfoTable(ElementName.NUMBERITEM, 0x22222222);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoNumberingIntent
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoNumberingIntent(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoNumberingIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoNumberingIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoNumberingIntent
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoNumberingIntent(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoNumberingIntent[  --> " + super.toString() + " ]";
    }


/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element ColorName
     * @return JDFSpanNamedColor the element
     */
    public JDFSpanNamedColor getColorName()
    {
        return (JDFSpanNamedColor) getElement(ElementName.COLORNAME, null, 0);
    }

    /** (25) getCreateColorName
     * 
     * @return JDFSpanNamedColor the element
     */
    public JDFSpanNamedColor getCreateColorName()
    {
        return (JDFSpanNamedColor) getCreateElement_KElement(ElementName.COLORNAME, null, 0);
    }

    /**
     * (29) append element ColorName
     */
    public JDFSpanNamedColor appendColorName() throws JDFException
    {
        return (JDFSpanNamedColor) appendElementN(ElementName.COLORNAME, 1, null);
    }

    /** (26) getCreateColorNameDetails
     * 
     * @param iSkip number of elements to skip
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateColorNameDetails(int iSkip)
    {
        return (JDFStringSpan)getCreateElement_KElement(ElementName.COLORNAMEDETAILS, null, iSkip);
    }

    /**
     * (27) const get element ColorNameDetails
     * @param iSkip number of elements to skip
     * @return JDFStringSpan the element
     * default is getColorNameDetails(0)     */
    public JDFStringSpan getColorNameDetails(int iSkip)
    {
        return (JDFStringSpan) getElement(ElementName.COLORNAMEDETAILS, null, iSkip);
    }

    /**
     * Get all ColorNameDetails from the current element
     * 
     * @return Collection<JDFStringSpan>, null if none are available
     */
    public Collection<JDFStringSpan> getAllColorNameDetails()
    {
        final VElement vc = getChildElementVector(ElementName.COLORNAMEDETAILS, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFStringSpan> v = new Vector<JDFStringSpan>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFStringSpan) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element ColorNameDetails
     */
    public JDFStringSpan appendColorNameDetails() throws JDFException
    {
        return (JDFStringSpan) appendElement(ElementName.COLORNAMEDETAILS, null);
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

    /** (26) getCreateNumberItem
     * 
     * @param iSkip number of elements to skip
     * @return JDFNumberItem the element
     */
    public JDFNumberItem getCreateNumberItem(int iSkip)
    {
        return (JDFNumberItem)getCreateElement_KElement(ElementName.NUMBERITEM, null, iSkip);
    }

    /**
     * (27) const get element NumberItem
     * @param iSkip number of elements to skip
     * @return JDFNumberItem the element
     * default is getNumberItem(0)     */
    public JDFNumberItem getNumberItem(int iSkip)
    {
        return (JDFNumberItem) getElement(ElementName.NUMBERITEM, null, iSkip);
    }

    /**
     * Get all NumberItem from the current element
     * 
     * @return Collection<JDFNumberItem>, null if none are available
     */
    public Collection<JDFNumberItem> getAllNumberItem()
    {
        final VElement vc = getChildElementVector(ElementName.NUMBERITEM, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFNumberItem> v = new Vector<JDFNumberItem>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFNumberItem) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element NumberItem
     */
    public JDFNumberItem appendNumberItem() throws JDFException
    {
        return (JDFNumberItem) appendElement(ElementName.NUMBERITEM, null);
    }

}// end namespace JDF
