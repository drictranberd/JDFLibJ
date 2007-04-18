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
import java.util.Vector;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.jmf.JDFMessage;
    /*
    *****************************************************************************
    class JDFAutoCommand : public JDFMessage

    *****************************************************************************
    */

public abstract class JDFAutoCommand extends JDFMessage
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ACKNOWLEDGEFORMAT, 0x33333311, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.ACKNOWLEDGETEMPLATE, 0x33333311, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.ACKNOWLEDGEURL, 0x33333333, AttributeInfo.EnumAttributeType.URL, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.ACKNOWLEDGETYPE, 0x33333331, AttributeInfo.EnumAttributeType.enumerations, EnumAcknowledgeType.getEnum(0), "Completed");
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }



    /**
     * Constructor for JDFAutoCommand
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoCommand(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoCommand
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoCommand(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoCommand
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoCommand(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoCommand[  --> " + super.toString() + " ]";
    }


        /**
        * Enumeration strings for AcknowledgeType
        */

        public static class EnumAcknowledgeType extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumAcknowledgeType(String name)
            {
                super(name, m_startValue++);
            }

            public static EnumAcknowledgeType getEnum(String enumName)
            {
                return (EnumAcknowledgeType) getEnum(EnumAcknowledgeType.class, enumName);
            }

            public static EnumAcknowledgeType getEnum(int enumValue)
            {
                return (EnumAcknowledgeType) getEnum(EnumAcknowledgeType.class, enumValue);
            }

            public static Map getEnumMap()
            {
                return getEnumMap(EnumAcknowledgeType.class);
            }

            public static List getEnumList()
            {
                return getEnumList(EnumAcknowledgeType.class);
            }

            public static Iterator iterator()
            {
                return iterator(EnumAcknowledgeType.class);
            }

            public static final EnumAcknowledgeType Received = new EnumAcknowledgeType("Received");
            public static final EnumAcknowledgeType Applied = new EnumAcknowledgeType("Applied");
            public static final EnumAcknowledgeType Completed = new EnumAcknowledgeType("Completed");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute AcknowledgeFormat
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AcknowledgeFormat
          * @param value: the value to set the attribute to
          */
        public void setAcknowledgeFormat(String value)
        {
            setAttribute(AttributeName.ACKNOWLEDGEFORMAT, value, null);
        }

        /**
          * (23) get String attribute AcknowledgeFormat
          * @return the value of the attribute
          */
        public String getAcknowledgeFormat()
        {
            return getAttribute(AttributeName.ACKNOWLEDGEFORMAT, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute AcknowledgeTemplate
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AcknowledgeTemplate
          * @param value: the value to set the attribute to
          */
        public void setAcknowledgeTemplate(String value)
        {
            setAttribute(AttributeName.ACKNOWLEDGETEMPLATE, value, null);
        }

        /**
          * (23) get String attribute AcknowledgeTemplate
          * @return the value of the attribute
          */
        public String getAcknowledgeTemplate()
        {
            return getAttribute(AttributeName.ACKNOWLEDGETEMPLATE, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute AcknowledgeURL
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute AcknowledgeURL
          * @param value: the value to set the attribute to
          */
        public void setAcknowledgeURL(String value)
        {
            setAttribute(AttributeName.ACKNOWLEDGEURL, value, null);
        }

        /**
          * (23) get String attribute AcknowledgeURL
          * @return the value of the attribute
          */
        public String getAcknowledgeURL()
        {
            return getAttribute(AttributeName.ACKNOWLEDGEURL, null, JDFConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute AcknowledgeType
        --------------------------------------------------------------------- */
        /**
          * (5.2) set attribute AcknowledgeType
          * @param v vector of the enumeration values
          */
        public void setAcknowledgeType(Vector v)
        {
            setEnumerationsAttribute(AttributeName.ACKNOWLEDGETYPE, v, null);
        }

        /**
          * (9.2) get AcknowledgeType attribute AcknowledgeType
          * @return Vector of the enumerations
          */
        public Vector getAcknowledgeType()
        {
            return getEnumerationsAttribute(AttributeName.ACKNOWLEDGETYPE, null, EnumAcknowledgeType.Completed, false);
        }

}// end namespace JDF
