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
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.resource.process.JDFArgumentValue;
import org.cip4.jdflib.resource.process.JDFStringListValue;

public abstract class JDFAutoPRGroupOccurrence extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[2];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.STRINGLISTVALUE, 0x33333333);
        elemInfoTable[1] = new ElemInfoTable(ElementName.ARGUMENTVALUE, 0x33333333);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoPRGroupOccurrence
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoPRGroupOccurrence(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPRGroupOccurrence
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoPRGroupOccurrence(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPRGroupOccurrence
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoPRGroupOccurrence(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoPRGroupOccurrence[  --> " + super.toString() + " ]";
    }


/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /** (26) getCreateStringListValue
     * 
     * @param iSkip number of elements to skip
     * @return JDFStringListValue the element
     */
    public JDFStringListValue getCreateStringListValue(int iSkip)
    {
        return (JDFStringListValue)getCreateElement_KElement(ElementName.STRINGLISTVALUE, null, iSkip);
    }

    /**
     * (27) const get element StringListValue
     * @param iSkip number of elements to skip
     * @return JDFStringListValue the element
     * default is getStringListValue(0)     */
    public JDFStringListValue getStringListValue(int iSkip)
    {
        return (JDFStringListValue) getElement(ElementName.STRINGLISTVALUE, null, iSkip);
    }

    /**
     * Get all StringListValue from the current element
     * 
     * @return Collection<JDFStringListValue>, null if none are available
     */
    public Collection<JDFStringListValue> getAllStringListValue()
    {
        final VElement vc = getChildElementVector(ElementName.STRINGLISTVALUE, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFStringListValue> v = new Vector<JDFStringListValue>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFStringListValue) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element StringListValue
     */
    public JDFStringListValue appendStringListValue() throws JDFException
    {
        return (JDFStringListValue) appendElement(ElementName.STRINGLISTVALUE, null);
    }

    /** (26) getCreateArgumentValue
     * 
     * @param iSkip number of elements to skip
     * @return JDFArgumentValue the element
     */
    public JDFArgumentValue getCreateArgumentValue(int iSkip)
    {
        return (JDFArgumentValue)getCreateElement_KElement(ElementName.ARGUMENTVALUE, null, iSkip);
    }

    /**
     * (27) const get element ArgumentValue
     * @param iSkip number of elements to skip
     * @return JDFArgumentValue the element
     * default is getArgumentValue(0)     */
    public JDFArgumentValue getArgumentValue(int iSkip)
    {
        return (JDFArgumentValue) getElement(ElementName.ARGUMENTVALUE, null, iSkip);
    }

    /**
     * Get all ArgumentValue from the current element
     * 
     * @return Collection<JDFArgumentValue>, null if none are available
     */
    public Collection<JDFArgumentValue> getAllArgumentValue()
    {
        final VElement vc = getChildElementVector(ElementName.ARGUMENTVALUE, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFArgumentValue> v = new Vector<JDFArgumentValue>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFArgumentValue) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element ArgumentValue
     */
    public JDFArgumentValue appendArgumentValue() throws JDFException
    {
        return (JDFArgumentValue) appendElement(ElementName.ARGUMENTVALUE, null);
    }

}// end namespace JDF
