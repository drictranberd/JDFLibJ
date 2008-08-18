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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement;

public abstract class JDFAutoErrorData extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ERRORTYPE, 0x22222222, AttributeInfo.EnumAttributeType.enumeration, EnumErrorType.getEnum(0), null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.ERRORURL, 0x33333333, AttributeInfo.EnumAttributeType.URI, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.FIXEXPRESSION, 0x33333333, AttributeInfo.EnumAttributeType.Any, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.PATH, 0x33333333, AttributeInfo.EnumAttributeType.XPath, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }



    /**
     * Constructor for JDFAutoErrorData
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoErrorData(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoErrorData
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoErrorData(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoErrorData
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoErrorData(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoErrorData[  --> " + super.toString() + " ]";
    }


        /**
        * Enumeration strings for ErrorType
        */

        public static class EnumErrorType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumErrorType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumErrorType getEnum(String enumName)
            {
                return (EnumErrorType) getEnum(EnumErrorType.class, enumName);
            }

            public static EnumErrorType getEnum(int enumValue)
            {
                return (EnumErrorType) getEnum(EnumErrorType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumErrorType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumErrorType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumErrorType.class);
            }

            public static final EnumErrorType Invalid = new EnumErrorType("Invalid");
            public static final EnumErrorType Missing = new EnumErrorType("Missing");
            public static final EnumErrorType Unsupported = new EnumErrorType("Unsupported");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ErrorType
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute ErrorType
          * @param enumVar: the enumVar to set the attribute to
          */
        public void setErrorType(EnumErrorType enumVar)
        {
            setAttribute(AttributeName.ERRORTYPE, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute ErrorType
          * @return the value of the attribute
          */
        public EnumErrorType getErrorType()
        {
            return EnumErrorType.getEnum(getAttribute(AttributeName.ERRORTYPE, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ErrorURL
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ErrorURL
          * @param value: the value to set the attribute to
          */
        public void setErrorURL(String value)
        {
            setAttribute(AttributeName.ERRORURL, value, null);
        }

        /**
          * (23) get String attribute ErrorURL
          * @return the value of the attribute
          */
        public String getErrorURL()
        {
            return getAttribute(AttributeName.ERRORURL, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute FixExpression
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute FixExpression
          * @param value: the value to set the attribute to
          */
        public void setFixExpression(String value)
        {
            setAttribute(AttributeName.FIXEXPRESSION, value, null);
        }

        /**
          * (23) get String attribute FixExpression
          * @return the value of the attribute
          */
        public String getFixExpression()
        {
            return getAttribute(AttributeName.FIXEXPRESSION, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Path
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Path
          * @param value: the value to set the attribute to
          */
        public void setPath(String value)
        {
            setAttribute(AttributeName.PATH, value, null);
        }

        /**
          * (23) get String attribute Path
          * @return the value of the attribute
          */
        public String getPath()
        {
            return getAttribute(AttributeName.PATH, null, JDFConstants.EMPTYSTRING);
        }

}// end namespace JDF
