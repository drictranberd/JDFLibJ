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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFCoreConstants;
import org.cip4.jdflib.core.JDFElement;
    /**
    *****************************************************************************
    class JDFAutoLot : public JDFElement

    *****************************************************************************
    */

public abstract class JDFAutoLot extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.ACTUALAMOUNT, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.AMOUNT, 0x33333333, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.LOTID, 0x22222222, AttributeInfo.EnumAttributeType.string, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.CONSUMPTION, 0x33333333, AttributeInfo.EnumAttributeType.enumeration, EnumConsumption.getEnum(0), null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }



    /**
     * Constructor for JDFAutoLot
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoLot(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoLot
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoLot(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoLot
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoLot(
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
        return " JDFAutoLot[  --> " + super.toString() + " ]";
    }


        /**
        * Enumeration strings for Consumption
        */

        public static class EnumConsumption extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumConsumption(String name)
            {
                super(name, m_startValue++);
            }

    /**
     * @param enumName the string to convert
     * @return the enum
     */
            public static EnumConsumption getEnum(String enumName)
            {
                return (EnumConsumption) getEnum(EnumConsumption.class, enumName);
            }

    /**
     * @param enumValue the integer to convert
     * @return the enum
     */
            public static EnumConsumption getEnum(int enumValue)
            {
                return (EnumConsumption) getEnum(EnumConsumption.class, enumValue);
            }

    /**
     * @return the map of enums
     */
            public static Map getEnumMap()
            {
                return getEnumMap(EnumConsumption.class);
            }

    /**
     * @return the list of enums
     */
            public static List getEnumList()
            {
                return getEnumList(EnumConsumption.class);
            }

    /**
     * @return the iterator
     */
            public static Iterator iterator()
            {
                return iterator(EnumConsumption.class);
            }

            public static final EnumConsumption Full = new EnumConsumption("Full");
            public static final EnumConsumption Partial = new EnumConsumption("Partial");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ActualAmount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ActualAmount
          * @param value the value to set the attribute to
          */
        public void setActualAmount(double value)
        {
            setAttribute(AttributeName.ACTUALAMOUNT, value, null);
        }

        /**
          * (17) get double attribute ActualAmount
          * @return double the value of the attribute
          */
        public double getActualAmount()
        {
            return getRealAttribute(AttributeName.ACTUALAMOUNT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Amount
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute Amount
          * @param value the value to set the attribute to
          */
        public void setAmount(double value)
        {
            setAttribute(AttributeName.AMOUNT, value, null);
        }

        /**
          * (17) get double attribute Amount
          * @return double the value of the attribute
          */
        public double getAmount()
        {
            return getRealAttribute(AttributeName.AMOUNT, null, 0.0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute LotID
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute LotID
          * @param value the value to set the attribute to
          */
        public void setLotID(String value)
        {
            setAttribute(AttributeName.LOTID, value, null);
        }

        /**
          * (23) get String attribute LotID
          * @return the value of the attribute
          */
        public String getLotID()
        {
            return getAttribute(AttributeName.LOTID, null, JDFCoreConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute Consumption
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute Consumption
          * @param enumVar the enumVar to set the attribute to
          */
        public void setConsumption(EnumConsumption enumVar)
        {
            setAttribute(AttributeName.CONSUMPTION, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute Consumption
          * @return the value of the attribute
          */
        public EnumConsumption getConsumption()
        {
            return EnumConsumption.getEnum(getAttribute(AttributeName.CONSUMPTION, null, null));
        }

}// end namespace JDF
