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
import org.cip4.jdflib.resource.process.*;

public abstract class JDFAutoLongitudinalRibbonOperationParams extends JDFResource
{

    private static final long serialVersionUID = 1L;

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[4];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.LONGFOLD, 0x44444443);
        elemInfoTable[1] = new ElemInfoTable(ElementName.LONGGLUE, 0x44444443);
        elemInfoTable[2] = new ElemInfoTable(ElementName.LONGPERFORATE, 0x44444443);
        elemInfoTable[3] = new ElemInfoTable(ElementName.LONGSLIT, 0x44444443);
    }
    
    @Override
	protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }



    /**
     * Constructor for JDFAutoLongitudinalRibbonOperationParams
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoLongitudinalRibbonOperationParams(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoLongitudinalRibbonOperationParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoLongitudinalRibbonOperationParams(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoLongitudinalRibbonOperationParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoLongitudinalRibbonOperationParams(
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
        return " JDFAutoLongitudinalRibbonOperationParams[  --> " + super.toString() + " ]";
    }


    @Override
	public boolean  init()
    {
        boolean bRet = super.init();
        setResourceClass(JDFResource.EnumResourceClass.Parameter);
        return bRet;
    }


    @Override
	public EnumResourceClass getValidClass()
    {
        return JDFResource.EnumResourceClass.Parameter;
    }


/* ***********************************************************************
 * Element getter / setter
 * ***********************************************************************
 */

    /** (26) getCreateLongFold
     * 
     * @param iSkip number of elements to skip
     * @return JDFLongFold the element
     */
    public JDFLongFold getCreateLongFold(int iSkip)
    {
        return (JDFLongFold)getCreateElement_KElement(ElementName.LONGFOLD, null, iSkip);
    }

    /**
     * (27) const get element LongFold
     * @param iSkip number of elements to skip
     * @return JDFLongFold the element
     * default is getLongFold(0)     */
    public JDFLongFold getLongFold(int iSkip)
    {
        return (JDFLongFold) getElement(ElementName.LONGFOLD, null, iSkip);
    }

    /**
     * Get all LongFold from the current element
     * 
     * @return Collection<JDFLongFold>, null if none are available
     */
    public Collection<JDFLongFold> getAllLongFold()
    {
        final VElement vc = getChildElementVector(ElementName.LONGFOLD, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFLongFold> v = new Vector<JDFLongFold>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFLongFold) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element LongFold
     */
    public JDFLongFold appendLongFold() throws JDFException
    {
        return (JDFLongFold) appendElement(ElementName.LONGFOLD, null);
    }

    /** (26) getCreateLongGlue
     * 
     * @param iSkip number of elements to skip
     * @return JDFLongGlue the element
     */
    public JDFLongGlue getCreateLongGlue(int iSkip)
    {
        return (JDFLongGlue)getCreateElement_KElement(ElementName.LONGGLUE, null, iSkip);
    }

    /**
     * (27) const get element LongGlue
     * @param iSkip number of elements to skip
     * @return JDFLongGlue the element
     * default is getLongGlue(0)     */
    public JDFLongGlue getLongGlue(int iSkip)
    {
        return (JDFLongGlue) getElement(ElementName.LONGGLUE, null, iSkip);
    }

    /**
     * Get all LongGlue from the current element
     * 
     * @return Collection<JDFLongGlue>, null if none are available
     */
    public Collection<JDFLongGlue> getAllLongGlue()
    {
        final VElement vc = getChildElementVector(ElementName.LONGGLUE, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFLongGlue> v = new Vector<JDFLongGlue>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFLongGlue) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element LongGlue
     */
    public JDFLongGlue appendLongGlue() throws JDFException
    {
        return (JDFLongGlue) appendElement(ElementName.LONGGLUE, null);
    }

    /** (26) getCreateLongPerforate
     * 
     * @param iSkip number of elements to skip
     * @return JDFLongPerforate the element
     */
    public JDFLongPerforate getCreateLongPerforate(int iSkip)
    {
        return (JDFLongPerforate)getCreateElement_KElement(ElementName.LONGPERFORATE, null, iSkip);
    }

    /**
     * (27) const get element LongPerforate
     * @param iSkip number of elements to skip
     * @return JDFLongPerforate the element
     * default is getLongPerforate(0)     */
    public JDFLongPerforate getLongPerforate(int iSkip)
    {
        return (JDFLongPerforate) getElement(ElementName.LONGPERFORATE, null, iSkip);
    }

    /**
     * Get all LongPerforate from the current element
     * 
     * @return Collection<JDFLongPerforate>, null if none are available
     */
    public Collection<JDFLongPerforate> getAllLongPerforate()
    {
        final VElement vc = getChildElementVector(ElementName.LONGPERFORATE, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFLongPerforate> v = new Vector<JDFLongPerforate>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFLongPerforate) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element LongPerforate
     */
    public JDFLongPerforate appendLongPerforate() throws JDFException
    {
        return (JDFLongPerforate) appendElement(ElementName.LONGPERFORATE, null);
    }

    /** (26) getCreateLongSlit
     * 
     * @param iSkip number of elements to skip
     * @return JDFLongSlit the element
     */
    public JDFLongSlit getCreateLongSlit(int iSkip)
    {
        return (JDFLongSlit)getCreateElement_KElement(ElementName.LONGSLIT, null, iSkip);
    }

    /**
     * (27) const get element LongSlit
     * @param iSkip number of elements to skip
     * @return JDFLongSlit the element
     * default is getLongSlit(0)     */
    public JDFLongSlit getLongSlit(int iSkip)
    {
        return (JDFLongSlit) getElement(ElementName.LONGSLIT, null, iSkip);
    }

    /**
     * Get all LongSlit from the current element
     * 
     * @return Collection<JDFLongSlit>, null if none are available
     */
    public Collection<JDFLongSlit> getAllLongSlit()
    {
        final VElement vc = getChildElementVector(ElementName.LONGSLIT, null);
        if (vc == null || vc.size() == 0)
        {
            return null;
        }

        final Vector<JDFLongSlit> v = new Vector<JDFLongSlit>();
        for (int i = 0; i < vc.size(); i++)
        {
            v.add((JDFLongSlit) vc.get(i));
        }

        return v;
    }

    /**
     * (30) append element LongSlit
     */
    public JDFLongSlit appendLongSlit() throws JDFException
    {
        return (JDFLongSlit) appendElement(ElementName.LONGSLIT, null);
    }

}// end namespace JDF
