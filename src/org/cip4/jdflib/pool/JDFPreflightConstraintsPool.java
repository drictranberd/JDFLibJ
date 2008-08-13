package org.cip4.jdflib.pool;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.resource.process.prepress.JDFPreflightConstraint;

/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2002 The International Cooperation for the Integration of 
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

//EndCopyRight
/*
 *
 *
 * COPYRIGHT Heidelberger Druckmaschinen AG, 1999-2001
 *      ALL RIGHTS RESERVED 
 *
 *  Author: Dr. Rainer Prosi using JDFSchema code generator 
 * 
 * Warning! preliminary version. Interface subject to change without prior notice!
 * Revision history:
 * created 2001-12-17
 * 030902 RP IsValid() removed erroneous check for maxOccurs=1 of  PreflightConstraint
 *
 * based on JDF Schema version JDFCore_1_0_0.xsd
 *
 */

public class JDFPreflightConstraintsPool extends JDFPool
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for JDFPreflightConstraintsPool
	 * 
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	public JDFPreflightConstraintsPool(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFPreflightConstraintsPool
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	public JDFPreflightConstraintsPool(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFPreflightConstraintsPool
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	public JDFPreflightConstraintsPool(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/*
	 * // Attribute Getter / Setter
	 */

	/*
	 * // Element Getter / Setter
	 */

	/**
	 * Get Element PreflightConstraint
	 * <p>
	 * default: GetCreatePreflightConstraint(0)
	 * 
	 * @param iSkip number of elements to skip
	 * @return JDFPreflightConstraint: the element
	 */
	public JDFPreflightConstraint getCreatePreflightConstraint(int iSkip)
	{
		return (JDFPreflightConstraint) getCreateElement_KElement("PreflightConstraint", JDFConstants.EMPTYSTRING, iSkip);
	}

	/**
	 * Append element PreflightConstraint
	 * 
	 * @return JDFPreflightConstraint: the element
	 */
	public JDFPreflightConstraint appendPreflightConstraint()
	{
		return (JDFPreflightConstraint) appendElement("PreflightConstraint", JDFConstants.EMPTYSTRING);
	}

	/**
	 * const get element PreflightConstraint
	 * 
	 * @param int iSkip number of elements to skip
	 * @return JDFPreflightConstraint: the element
	 */
	public JDFPreflightConstraint getPreflightConstraint(int iSkip)
	{
		return (JDFPreflightConstraint) getElement("PreflightConstraint", JDFConstants.EMPTYSTRING, iSkip);
	}

} // endJDFPreflightConstraintsPool
