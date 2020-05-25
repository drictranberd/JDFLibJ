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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.resource.process.JDFContact;

/**
 *****************************************************************************
 * class JDFAutoApprovalPerson : public JDFElement
 *****************************************************************************
 *
 */

public abstract class JDFAutoApprovalPerson extends JDFElement
{

	private static final long serialVersionUID = 1L;

	private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[3];
	static
	{
		atrInfoTable[0] = new AtrInfoTable(AttributeName.OBLIGATED, 0x44444433, AttributeInfo.EnumAttributeType.boolean_, null, null);
		atrInfoTable[1] = new AtrInfoTable(AttributeName.APPROVALROLE, 0x33333311, AttributeInfo.EnumAttributeType.enumeration, EnumApprovalRole.getEnum(0), "Obligated");
		atrInfoTable[2] = new AtrInfoTable(AttributeName.APPROVALROLEDETAILS, 0x33333111, AttributeInfo.EnumAttributeType.string, null, null);
	}

	@Override
	protected AttributeInfo getTheAttributeInfo()
	{
		return super.getTheAttributeInfo().updateReplace(atrInfoTable);
	}

	private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[1];
	static
	{
		elemInfoTable[0] = new ElemInfoTable(ElementName.CONTACT, 0x55555555);
	}

	@Override
	protected ElementInfo getTheElementInfo()
	{
		return super.getTheElementInfo().updateReplace(elemInfoTable);
	}

	/**
	 * Constructor for JDFAutoApprovalPerson
	 *
	 * @param myOwnerDocument
	 * @param qualifiedName
	 */
	protected JDFAutoApprovalPerson(CoreDocumentImpl myOwnerDocument, String qualifiedName)
	{
		super(myOwnerDocument, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoApprovalPerson
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 */
	protected JDFAutoApprovalPerson(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName);
	}

	/**
	 * Constructor for JDFAutoApprovalPerson
	 *
	 * @param myOwnerDocument
	 * @param myNamespaceURI
	 * @param qualifiedName
	 * @param myLocalName
	 */
	protected JDFAutoApprovalPerson(CoreDocumentImpl myOwnerDocument, String myNamespaceURI, String qualifiedName, String myLocalName)
	{
		super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
	}

	/**
	 * Enumeration strings for ApprovalRole
	 */

	@SuppressWarnings("rawtypes")
	public static class EnumApprovalRole extends ValuedEnum
	{
		private static final long serialVersionUID = 1L;
		private static int m_startValue = 0;

		private EnumApprovalRole(String name)
		{
			super(name, m_startValue++);
		}

		/**
		 * @param enumName the string to convert
		 * @return the enum
		 */
		public static EnumApprovalRole getEnum(String enumName)
		{
			return (EnumApprovalRole) getEnum(EnumApprovalRole.class, enumName);
		}

		/**
		 * @param enumValue the integer to convert
		 * @return the enum
		 */
		public static EnumApprovalRole getEnum(int enumValue)
		{
			return (EnumApprovalRole) getEnum(EnumApprovalRole.class, enumValue);
		}

		/**
		 * @return the map of enums
		 */
		public static Map getEnumMap()
		{
			return getEnumMap(EnumApprovalRole.class);
		}

		/**
		 * @return the list of enums
		 */
		public static List getEnumList()
		{
			return getEnumList(EnumApprovalRole.class);
		}

		/**
		 * @return the iterator
		 */
		public static Iterator iterator()
		{
			return iterator(EnumApprovalRole.class);
		}

		/**  */
		public static final EnumApprovalRole Approvinator = new EnumApprovalRole("Approvinator");
		/**  */
		public static final EnumApprovalRole Group = new EnumApprovalRole("Group");
		/**  */
		public static final EnumApprovalRole Informative = new EnumApprovalRole("Informative");
		/**  */
		public static final EnumApprovalRole Obligated = new EnumApprovalRole("Obligated");
	}

	/* ************************************************************************
	 * Attribute getter / setter
	 * ************************************************************************
	 */

	/* ---------------------------------------------------------------------
	Methods for Attribute Obligated
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute Obligated
	 *
	 * @param value the value to set the attribute to
	 */
	public void setObligated(boolean value)
	{
		setAttribute(AttributeName.OBLIGATED, value, null);
	}

	/**
	 * (18) get boolean attribute Obligated
	 *
	 * @return boolean the value of the attribute
	 */
	public boolean getObligated()
	{
		return getBoolAttribute(AttributeName.OBLIGATED, null, false);
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute ApprovalRole
	--------------------------------------------------------------------- */
	/**
	 * (5) set attribute ApprovalRole
	 *
	 * @param enumVar the enumVar to set the attribute to
	 */
	public void setApprovalRole(EnumApprovalRole enumVar)
	{
		setAttribute(AttributeName.APPROVALROLE, enumVar == null ? null : enumVar.getName(), null);
	}

	/**
	 * (9) get attribute ApprovalRole
	 *
	 * @return the value of the attribute
	 */
	public EnumApprovalRole getApprovalRole()
	{
		return EnumApprovalRole.getEnum(getAttribute(AttributeName.APPROVALROLE, null, "Obligated"));
	}

	/* ---------------------------------------------------------------------
	Methods for Attribute ApprovalRoleDetails
	--------------------------------------------------------------------- */
	/**
	 * (36) set attribute ApprovalRoleDetails
	 *
	 * @param value the value to set the attribute to
	 */
	public void setApprovalRoleDetails(String value)
	{
		setAttribute(AttributeName.APPROVALROLEDETAILS, value, null);
	}

	/**
	 * (23) get String attribute ApprovalRoleDetails
	 *
	 * @return the value of the attribute
	 */
	public String getApprovalRoleDetails()
	{
		return getAttribute(AttributeName.APPROVALROLEDETAILS, null, JDFCoreConstants.EMPTYSTRING);
	}

	/* ***********************************************************************
	 * Element getter / setter
	 * ***********************************************************************
	 */

	/**
	 * (24) const get element Contact
	 *
	 * @return JDFContact the element
	 */
	public JDFContact getContact()
	{
		return (JDFContact) getElement(ElementName.CONTACT, null, 0);
	}

	/**
	 * (25) getCreateContact
	 *
	 * @return JDFContact the element
	 */
	public JDFContact getCreateContact()
	{
		return (JDFContact) getCreateElement_JDFElement(ElementName.CONTACT, null, 0);
	}

	/**
	 * (29) append element Contact
	 *
	 * @return JDFContact the element @ if the element already exists
	 */
	public JDFContact appendContact()
	{
		return (JDFContact) appendElementN(ElementName.CONTACT, 1, null);
	}

	/**
	 * (31) create inter-resource link to refTarget
	 *
	 * @param refTarget the element that is referenced
	 */
	public void refContact(JDFContact refTarget)
	{
		refElement(refTarget);
	}

}
