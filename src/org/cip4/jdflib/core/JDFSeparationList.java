/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2006 The International Cooperation for the Integration of
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
/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFSeparationList
 *
 * Last changes
 *
 * 2002-07-02 JG - newly created
 */

package org.cip4.jdflib.core;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoSeparationList;
import org.cip4.jdflib.resource.process.JDFSeparationSpec;

/**
 * This class represents a list of SeparationSpec elements 
 * it allows high level string manipulation of the separation names 
 * by hiding the fact that the separations are written in SeparationSpec/@Name
 */
public class JDFSeparationList extends JDFAutoSeparationList
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor for JDFSeparationList
     * @param ownerDocument
     * @param qualifiedName
     */
    public JDFSeparationList(
            CoreDocumentImpl myOwnerDocument,
            String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }
    
    /**
     * Constructor for JDFSeparationList
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     */
    public JDFSeparationList(
            CoreDocumentImpl myOwnerDocument,
            String myNamespaceURI,
            String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }
    
    /**
     * Constructor for JDFSeparationList
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     * @param localName
     */
    public JDFSeparationList(
            CoreDocumentImpl myOwnerDocument,
            String myNamespaceURI,
            String qualifiedName,
            String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }
    
    //**************************************** Methods *********************************************
    /**
     * toString
     *
     * @return String
     */
    public String toString()
    {
        return "JDFSeparationList[ -->" + super.toString() + "]";
    }
    
    
    
    /**
     * Get a list of all separation names in the SeparationSpec elements
     * @return the vector of separation names
     */
    public VString getSeparations() 
    {
        VString vName=new VString();
        VElement v=getChildElementVector(ElementName.SEPARATIONSPEC,null,null,false,0,false);
        int nSep=v.size();
        for(int i=0;i<nSep;i++)
        {
            JDFSeparationSpec sep=(JDFSeparationSpec) v.elementAt(i);
            String sepName=sep.getName();
            vName.appendUnique(sepName);
        }
        return vName;
    }  
    
    /**
     * set all separation names in the SeparationSpec elements
     * remove any prior elements
     * 
     * @param the vector of separation names to set
     */
    public void setSeparations(VString vSeps) 
    {
        removeChildren(ElementName.SEPARATIONSPEC,null,null);
        if(vSeps==null)
            return;
        
        for(int i=0;i<vSeps.size();i++)
        {
            appendSeparation(vSeps.stringAt(i));
        }
    }
    
    /**
     * append a separationspec with a given name to this
     * @param sep the separation name
     */
    public void appendSeparation(String sep)
    {
        appendSeparationSpec().setName(sep);
    }   
    
    /**
     * remove a separationspec with a given name from this
     * 
     * @param sep the separation name
     * @return int the index of the removed separation; -1 if none found
     */
    public int removeSeparation(String sep)
    {
        VString vs=getSeparations();
        final int index=vs.index(sep);
        if (index>=0)
            getSeparationSpec(index).deleteNode();
        return index;
    }   
    /**
     * Get the n'th separation name in the SeparationSpec elements
     * @param iSkip the index of the SeparationSpec
     * @return separation names, null if iSkip > nSeparations
     */
    public String getSeparation(int iSkip) 
    {
        JDFSeparationSpec ss=getSeparationSpec(iSkip);
        if(ss==null)
            return null;
        return ss.getName();        
    }  
    
    
}
