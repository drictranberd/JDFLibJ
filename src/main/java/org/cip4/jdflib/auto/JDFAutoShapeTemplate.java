/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment may appear in the software itself, if and wherever such third-party acknowledgments
 * normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */

package org.cip4.jdflib.auto;

import java.util.Collection;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.datatypes.JDFShape;
import org.cip4.jdflib.resource.process.JDFFileSpec;

/**
 *****************************************************************************
 * class JDFAutoShapeTemplate : public JDFElement
 *****************************************************************************
 * 
 */

public abstract class JDFAutoShapeTemplate extends JDFElement
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[3];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.INNERDIMENSIONS, 0x33333333, AttributeInfo.EnumAttributeType.shape, null, null);
		atrInfoTable[1] = new AtrInfoTable(AttributeName.NAME, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
		atrInfoTable[2] = new AtrInfoTable(AttributeName.STANDARD, 0x33333333, AttributeInfo.EnumAttributeType.string, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[1];
	static
	{
		elemInfoTable[0] = new ElemInfoTable(ElementName.FILESPEC, 0x33333333);
	}

	@Override
	protected ElementInfo getTheElementInfo()
	{
		return super.getTheElementInfo().updateReplace(elemInfoTable);
	}

	/**
	 * Constructor for JDFAutoShapeTemplate
	 * 
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoShapeTemplate(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoShapeTemplate
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoShapeTemplate(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoShapeTemplate
	 * 
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoShapeTemplate(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * @return the string representation
	 */
	@Override
	public String toString()
	{
		return " JDFAutoShapeTemplate[  --> " + super.toString() + " ]";
	}

	/*
	 * ************************************************************************ Attribute getter / setter ************************************************************************
	 */

	/*
	 * --------------------------------------------------------------------- Methods for Attribute InnerDimensions ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute InnerDimensions
	 * 
	 * @param value the value to set the attribute to
	 */
	public void setInnerDimensions(JDFShape value)
	{
		setAttribute(AttributeName.INNERDIMENSIONS, value, null);
	}

	/**
	 * (20) get JDFShape attribute InnerDimensions
	 * 
	 * @return JDFShape the value of the attribute, null if a the attribute value is not a valid to create a JDFShape
	 */
	public JDFShape getInnerDimensions()
	{
		final String strAttrName = getAttribute(AttributeName.INNERDIMENSIONS, null, null);
		final JDFShape nPlaceHolder = JDFShape.createShape(strAttrName);
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Name ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Name
	 * 
	 * @param value the value to set the attribute to
	 */
	public void setName(String value)
	{
		setAttribute(AttributeName.NAME, value, null);
	}

	/**
	 * (23) get String attribute Name
	 * 
	 * @return the value of the attribute
	 */
	public String getName()
	{
		return getAttribute(AttributeName.NAME, null, JDFCoreConstants.EMPTYSTRING);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute Standard ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute Standard
	 * 
	 * @param value the value to set the attribute to
	 */
	public void setStandard(String value)
	{
		setAttribute(AttributeName.STANDARD, value, null);
	}

	/**
	 * (23) get String attribute Standard
	 * 
	 * @return the value of the attribute
	 */
	public String getStandard()
	{
		return getAttribute(AttributeName.STANDARD, null, JDFCoreConstants.EMPTYSTRING);
	}

	/*
	 * *********************************************************************** Element getter / setter ***********************************************************************
	 */

	/**
	 * (26) getCreateFileSpec
	 * 
	 * @param iSkip number of elements to skip
	 * @return JDFFileSpec the element
	 */
	public JDFFileSpec getCreateFileSpec(int iSkip)
	{
		return (JDFFileSpec) getCreateElement(ElementName.FILESPEC, null, iSkip);
	}

	/**
	 * (27) const get element FileSpec
	 * 
	 * @param iSkip number of elements to skip
	 * @return JDFFileSpec the element default is getFileSpec(0)
	 */
	public JDFFileSpec getFileSpec(int iSkip)
	{
		return (JDFFileSpec) getElement(ElementName.FILESPEC, null, iSkip);
	}

	/**
	 * Get all FileSpec from the current element
	 * 
	 * @return Collection<JDFFileSpec>, null if none are available
	 */
	public Collection<JDFFileSpec> getAllFileSpec()
	{
		return getChildrenByClass(JDFFileSpec.class, false, 0);
	}

	/**
	 * (30) append element FileSpec
	 * 
	 * @return JDFFileSpec the element
	 */
	public JDFFileSpec appendFileSpec()
	{
		return (JDFFileSpec) appendElement(ElementName.FILESPEC, null);
	}

	/**
	 * (31) create inter-resource link to refTarget
	 * 
	 * @param refTarget the element that is referenced
	 */
	public void refFileSpec(JDFFileSpec refTarget)
	{
		refElement(refTarget);
	}

}// end namespace JDF
