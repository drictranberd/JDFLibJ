/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2024 The International Cooperation for the Integration of
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
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.util.JDFDuration;

/**
 *****************************************************************************
 * class JDFAutoDevelopingParams : public JDFResource
 *****************************************************************************
 * 
 */

public abstract class JDFAutoDevelopingParams extends JDFResource
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[5];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.PREHEATTEMP, 0x3333333331l, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[1] = new AtrInfoTable(AttributeName.PREHEATTIME, 0x3333333331l, AttributeInfo.EnumAttributeType.duration, null, null);
		atrInfoTable[2] = new AtrInfoTable(AttributeName.POSTBAKETEMP, 0x3333333331l, AttributeInfo.EnumAttributeType.double_, null, null);
		atrInfoTable[3] = new AtrInfoTable(AttributeName.POSTBAKETIME, 0x3333333331l, AttributeInfo.EnumAttributeType.duration, null, null);
		atrInfoTable[4] = new AtrInfoTable(AttributeName.POSTEXPOSETIME, 0x3333333331l, AttributeInfo.EnumAttributeType.duration, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	/**
	 * Constructor for JDFAutoDevelopingParams
	 *
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoDevelopingParams(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoDevelopingParams
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoDevelopingParams(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoDevelopingParams
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoDevelopingParams(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * @return true if ok
	 */
	@Override
	public boolean init()
	{
		final boolean bRet = super.init();
		setResourceClass(JDFResource.EnumResourceClass.Parameter);
		return bRet;
	}

	/**
	 * @return the resource Class
	 */
	@Override
	public EnumResourceClass getValidClass()
	{
		return JDFResource.EnumResourceClass.Parameter;
	}

	/*
	 * ************************************************************************ Attribute getter / setter ************************************************************************
	 */

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PreHeatTemp ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute PreHeatTemp
	 *
	 * @param value the value to set the attribute to
	 */
	public void setPreHeatTemp(double value)
	{
		setAttribute(AttributeName.PREHEATTEMP, value, null);
	}

	/**
	 * (17) get double attribute PreHeatTemp
	 *
	 * @return double the value of the attribute
	 */
	public double getPreHeatTemp()
	{
		return getRealAttribute(AttributeName.PREHEATTEMP, null, 0.0);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PreHeatTime ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute PreHeatTime
	 *
	 * @param value the value to set the attribute to
	 */
	public void setPreHeatTime(JDFDuration value)
	{
		setAttribute(AttributeName.PREHEATTIME, value, null);
	}

	/**
	 * (20) get JDFDuration attribute PreHeatTime
	 *
	 * @return JDFDuration the value of the attribute, null if a the attribute value is not a valid to create a JDFDuration
	 */
	public JDFDuration getPreHeatTime()
	{
		final String strAttrName = getAttribute(AttributeName.PREHEATTIME, null, null);
		final JDFDuration nPlaceHolder = JDFDuration.createDuration(strAttrName);
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PostBakeTemp
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute PostBakeTemp
	 *
	 * @param value the value to set the attribute to
	 */
	public void setPostBakeTemp(double value)
	{
		setAttribute(AttributeName.POSTBAKETEMP, value, null);
	}

	/**
	 * (17) get double attribute PostBakeTemp
	 *
	 * @return double the value of the attribute
	 */
	public double getPostBakeTemp()
	{
		return getRealAttribute(AttributeName.POSTBAKETEMP, null, 0.0);
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PostBakeTime
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute PostBakeTime
	 *
	 * @param value the value to set the attribute to
	 */
	public void setPostBakeTime(JDFDuration value)
	{
		setAttribute(AttributeName.POSTBAKETIME, value, null);
	}

	/**
	 * (20) get JDFDuration attribute PostBakeTime
	 *
	 * @return JDFDuration the value of the attribute, null if a the attribute value is not a valid to create a JDFDuration
	 */
	public JDFDuration getPostBakeTime()
	{
		final String strAttrName = getAttribute(AttributeName.POSTBAKETIME, null, null);
		final JDFDuration nPlaceHolder = JDFDuration.createDuration(strAttrName);
		return nPlaceHolder;
	}

	/*
	 * --------------------------------------------------------------------- Methods for Attribute PostExposeTime
	 * ---------------------------------------------------------------------
	 */
	/**
	 * (36) set attribute PostExposeTime
	 *
	 * @param value the value to set the attribute to
	 */
	public void setPostExposeTime(JDFDuration value)
	{
		setAttribute(AttributeName.POSTEXPOSETIME, value, null);
	}

	/**
	 * (20) get JDFDuration attribute PostExposeTime
	 *
	 * @return JDFDuration the value of the attribute, null if a the attribute value is not a valid to create a JDFDuration
	 */
	public JDFDuration getPostExposeTime()
	{
		final String strAttrName = getAttribute(AttributeName.POSTEXPOSETIME, null, null);
		final JDFDuration nPlaceHolder = JDFDuration.createDuration(strAttrName);
		return nPlaceHolder;
	}

}
