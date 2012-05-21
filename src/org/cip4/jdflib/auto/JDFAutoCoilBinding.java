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

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.resource.process.postpress.JDFHoleList;
import org.cip4.jdflib.span.JDFSpanCoilMaterial;
import org.cip4.jdflib.span.JDFStringSpan;
    /**
    *****************************************************************************
    class JDFAutoCoilBinding : public JDFElement

    *****************************************************************************
    */

public abstract class JDFAutoCoilBinding extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[3];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.COILBRAND, 0x66666111);
        elemInfoTable[1] = new ElemInfoTable(ElementName.COILMATERIAL, 0x66666666);
        elemInfoTable[2] = new ElemInfoTable(ElementName.HOLELIST, 0x66666611);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoCoilBinding
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoCoilBinding(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoCoilBinding
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoCoilBinding(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoCoilBinding
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoCoilBinding(
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
        return " JDFAutoCoilBinding[  --> " + super.toString() + " ]";
    }


/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /**
     * (24) const get element CoilBrand
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCoilBrand()
    {
        return (JDFStringSpan) getElement(ElementName.COILBRAND, null, 0);
    }

    /** (25) getCreateCoilBrand
     * 
     * @return JDFStringSpan the element
     */
    public JDFStringSpan getCreateCoilBrand()
    {
        return (JDFStringSpan) getCreateElement_KElement(ElementName.COILBRAND, null, 0);
    }

    /**
     * (29) append element CoilBrand
     * @return JDFStringSpan the element
     * @throws JDFException if the element already exists
     */
    public JDFStringSpan appendCoilBrand() throws JDFException
    {
        return (JDFStringSpan) appendElementN(ElementName.COILBRAND, 1, null);
    }

    /**
     * (24) const get element CoilMaterial
     * @return JDFSpanCoilMaterial the element
     */
    public JDFSpanCoilMaterial getCoilMaterial()
    {
        return (JDFSpanCoilMaterial) getElement(ElementName.COILMATERIAL, null, 0);
    }

    /** (25) getCreateCoilMaterial
     * 
     * @return JDFSpanCoilMaterial the element
     */
    public JDFSpanCoilMaterial getCreateCoilMaterial()
    {
        return (JDFSpanCoilMaterial) getCreateElement_KElement(ElementName.COILMATERIAL, null, 0);
    }

    /**
     * (29) append element CoilMaterial
     * @return JDFSpanCoilMaterial the element
     * @throws JDFException if the element already exists
     */
    public JDFSpanCoilMaterial appendCoilMaterial() throws JDFException
    {
        return (JDFSpanCoilMaterial) appendElementN(ElementName.COILMATERIAL, 1, null);
    }

    /**
     * (24) const get element HoleList
     * @return JDFHoleList the element
     */
    public JDFHoleList getHoleList()
    {
        return (JDFHoleList) getElement(ElementName.HOLELIST, null, 0);
    }

    /** (25) getCreateHoleList
     * 
     * @return JDFHoleList the element
     */
    public JDFHoleList getCreateHoleList()
    {
        return (JDFHoleList) getCreateElement_KElement(ElementName.HOLELIST, null, 0);
    }

    /**
     * (29) append element HoleList
     * @return JDFHoleList the element
     * @throws JDFException if the element already exists
     */
    public JDFHoleList appendHoleList() throws JDFException
    {
        return (JDFHoleList) appendElementN(ElementName.HOLELIST, 1, null);
    }

}// end namespace JDF
