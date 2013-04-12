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
import org.cip4.jdflib.resource.JDFResource;
    /**
    *****************************************************************************
    class JDFAutoVarnishingParams : public JDFResource

    *****************************************************************************
    */

public abstract class JDFAutoVarnishingParams extends JDFResource
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.MODULEINDEX, 0x33331111, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[1] = new AtrInfoTable(AttributeName.MODULETYPE, 0x33331111, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.VARNISHAREA, 0x33331111, AttributeInfo.EnumAttributeType.enumeration, EnumVarnishArea.getEnum(0), null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.VARNISHMETHOD, 0x33331111, AttributeInfo.EnumAttributeType.enumeration, EnumVarnishMethod.getEnum(0), null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }



    /**
     * Constructor for JDFAutoVarnishingParams
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoVarnishingParams(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoVarnishingParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoVarnishingParams(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoVarnishingParams
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoVarnishingParams(
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
        return " JDFAutoVarnishingParams[  --> " + super.toString() + " ]";
    }


    /**
     * @return  true if ok
     */
    @Override
    public boolean  init()
    {
        boolean bRet = super.init();
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


        /**
        * Enumeration strings for VarnishArea
        */

        public static class EnumVarnishArea extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumVarnishArea(String name)
            {
                super(name, m_startValue++);
            }

    /**
     * @param enumName the string to convert
     * @return the enum
     */
            public static EnumVarnishArea getEnum(String enumName)
            {
                return (EnumVarnishArea) getEnum(EnumVarnishArea.class, enumName);
            }

    /**
     * @param enumValue the integer to convert
     * @return the enum
     */
            public static EnumVarnishArea getEnum(int enumValue)
            {
                return (EnumVarnishArea) getEnum(EnumVarnishArea.class, enumValue);
            }

    /**
     * @return the map of enums
     */
            public static Map getEnumMap()
            {
                return getEnumMap(EnumVarnishArea.class);
            }

    /**
     * @return the list of enums
     */
            public static List getEnumList()
            {
                return getEnumList(EnumVarnishArea.class);
            }

    /**
     * @return the iterator
     */
            public static Iterator iterator()
            {
                return iterator(EnumVarnishArea.class);
            }

            public static final EnumVarnishArea Full = new EnumVarnishArea("Full");
            public static final EnumVarnishArea Spot = new EnumVarnishArea("Spot");
        }      



        /**
        * Enumeration strings for VarnishMethod
        */

        public static class EnumVarnishMethod extends ValuedEnum
        {
            private static final long serialVersionUID = 1L;
            private static int m_startValue = 0;

            private EnumVarnishMethod(String name)
            {
                super(name, m_startValue++);
            }

    /**
     * @param enumName the string to convert
     * @return the enum
     */
            public static EnumVarnishMethod getEnum(String enumName)
            {
                return (EnumVarnishMethod) getEnum(EnumVarnishMethod.class, enumName);
            }

    /**
     * @param enumValue the integer to convert
     * @return the enum
     */
            public static EnumVarnishMethod getEnum(int enumValue)
            {
                return (EnumVarnishMethod) getEnum(EnumVarnishMethod.class, enumValue);
            }

    /**
     * @return the map of enums
     */
            public static Map getEnumMap()
            {
                return getEnumMap(EnumVarnishMethod.class);
            }

    /**
     * @return the list of enums
     */
            public static List getEnumList()
            {
                return getEnumList(EnumVarnishMethod.class);
            }

    /**
     * @return the iterator
     */
            public static Iterator iterator()
            {
                return iterator(EnumVarnishMethod.class);
            }

            public static final EnumVarnishMethod Blanket = new EnumVarnishMethod("Blanket");
            public static final EnumVarnishMethod Plate = new EnumVarnishMethod("Plate");
            public static final EnumVarnishMethod Method = new EnumVarnishMethod("Method");
        }      



/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute ModuleIndex
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ModuleIndex
          * @param value the value to set the attribute to
          */
        public void setModuleIndex(int value)
        {
            setAttribute(AttributeName.MODULEINDEX, value, null);
        }

        /**
          * (15) get int attribute ModuleIndex
          * @return int the value of the attribute
          */
        public int getModuleIndex()
        {
            return getIntAttribute(AttributeName.MODULEINDEX, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ModuleType
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ModuleType
          * @param value the value to set the attribute to
          */
        public void setModuleType(String value)
        {
            setAttribute(AttributeName.MODULETYPE, value, null);
        }

        /**
          * (23) get String attribute ModuleType
          * @return the value of the attribute
          */
        public String getModuleType()
        {
            return getAttribute(AttributeName.MODULETYPE, null, JDFCoreConstants.EMPTYSTRING);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute VarnishArea
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute VarnishArea
          * @param enumVar the enumVar to set the attribute to
          */
        public void setVarnishArea(EnumVarnishArea enumVar)
        {
            setAttribute(AttributeName.VARNISHAREA, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute VarnishArea
          * @return the value of the attribute
          */
        public EnumVarnishArea getVarnishArea()
        {
            return EnumVarnishArea.getEnum(getAttribute(AttributeName.VARNISHAREA, null, null));
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute VarnishMethod
        --------------------------------------------------------------------- */
        /**
          * (5) set attribute VarnishMethod
          * @param enumVar the enumVar to set the attribute to
          */
        public void setVarnishMethod(EnumVarnishMethod enumVar)
        {
            setAttribute(AttributeName.VARNISHMETHOD, enumVar==null ? null : enumVar.getName(), null);
        }

        /**
          * (9) get attribute VarnishMethod
          * @return the value of the attribute
          */
        public EnumVarnishMethod getVarnishMethod()
        {
            return EnumVarnishMethod.getEnum(getAttribute(AttributeName.VARNISHMETHOD, null, null));
        }

}// end namespace JDF
