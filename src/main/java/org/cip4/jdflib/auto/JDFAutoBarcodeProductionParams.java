/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2020 The International Cooperation for the Integration of
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
import org.cip4.jdflib.resource.process.JDFBarcodeReproParams;
import org.cip4.jdflib.resource.process.JDFIdentificationField;

/**
 *****************************************************************************
 * class JDFAutoBarcodeProductionParams : public JDFElement
 *****************************************************************************
 *
 */

public abstract class JDFAutoBarcodeProductionParams extends JDFElement
{

	private static final long serialVersionUID = 1L;

	private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[2];
	static
	{
		elemInfoTable[0] = new ElemInfoTable(ElementName.BARCODEREPROPARAMS, 0x66666111);
		elemInfoTable[1] = new ElemInfoTable(ElementName.IDENTIFICATIONFIELD, 0x55555111);
	}

	@Override
	protected ElementInfo getTheElementInfo()
	{
		return super.getTheElementInfo().updateReplace(elemInfoTable);
	}

	/**
	 * Constructor for JDFAutoBarcodeProductionParams
	 *
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoBarcodeProductionParams(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoBarcodeProductionParams
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoBarcodeProductionParams(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoBarcodeProductionParams
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoBarcodeProductionParams(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/* ***********************************************************************
	 * Element getter / setter
	 * ***********************************************************************
	 */

	/**
	 * (24) const get element BarcodeReproParams
	 *
	 * @return JDFBarcodeReproParams the element
	 */
	public JDFBarcodeReproParams getBarcodeReproParams()
	{
		return (JDFBarcodeReproParams) getElement(ElementName.BARCODEREPROPARAMS, null, 0);
	}

	/**
	 * (25) getCreateBarcodeReproParams
	 *
	 * @return JDFBarcodeReproParams the element
	 */
	public JDFBarcodeReproParams getCreateBarcodeReproParams()
	{
		return (JDFBarcodeReproParams) getCreateElement_JDFElement(ElementName.BARCODEREPROPARAMS, null, 0);
	}

	/**
	 * (29) append element BarcodeReproParams
	 *
	 * @return JDFBarcodeReproParams the element @ if the element already exists
	 */
	public JDFBarcodeReproParams appendBarcodeReproParams()
	{
		return (JDFBarcodeReproParams) appendElementN(ElementName.BARCODEREPROPARAMS, 1, null);
	}

	/**
	 * (24) const get element IdentificationField
	 *
	 * @return JDFIdentificationField the element
	 */
	public JDFIdentificationField getIdentificationField()
	{
		return (JDFIdentificationField) getElement(ElementName.IDENTIFICATIONFIELD, null, 0);
	}

	/**
	 * (25) getCreateIdentificationField
	 *
	 * @return JDFIdentificationField the element
	 */
	public JDFIdentificationField getCreateIdentificationField()
	{
		return (JDFIdentificationField) getCreateElement_JDFElement(ElementName.IDENTIFICATIONFIELD, null, 0);
	}

	/**
	 * (29) append element IdentificationField
	 *
	 * @return JDFIdentificationField the element @ if the element already exists
	 */
	public JDFIdentificationField appendIdentificationField()
	{
		return (JDFIdentificationField) appendElementN(ElementName.IDENTIFICATIONFIELD, 1, null);
	}

	/**
	 * (31) create inter-resource link to refTarget
	 *
	 * @param refTarget the element that is referenced
	 */
	public void refIdentificationField(JDFIdentificationField refTarget)
	{
		refElement(refTarget);
	}

}
