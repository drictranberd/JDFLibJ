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

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.VString;
    /*
    *****************************************************************************
    class JDFAutoPRRuleAttr : public JDFElement

    *****************************************************************************
    */

public abstract class JDFAutoPRRuleAttr extends JDFElement
{

    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[5];
    static
    {
        atrInfoTable[0] = new AtrInfoTable(AttributeName.GROUPBY, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKENS, null, "Tested");
        atrInfoTable[1] = new AtrInfoTable(AttributeName.REPORTATTR, 0x33333333, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
        atrInfoTable[2] = new AtrInfoTable(AttributeName.LOGERRORS, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[3] = new AtrInfoTable(AttributeName.MAXGROUPS, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
        atrInfoTable[4] = new AtrInfoTable(AttributeName.MAXPERGROUP, 0x33333333, AttributeInfo.EnumAttributeType.integer, null, null);
    }
    
    protected AttributeInfo getTheAttributeInfo()
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }



    /**
     * Constructor for JDFAutoPRRuleAttr
     * @param myOwnerDocument
     * @param qualifiedName
     */
    protected JDFAutoPRRuleAttr(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPRRuleAttr
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    protected JDFAutoPRRuleAttr(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFAutoPRRuleAttr
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    protected JDFAutoPRRuleAttr(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }


    public String toString()
    {
        return " JDFAutoPRRuleAttr[  --> " + super.toString() + " ]";
    }


/* ************************************************************************
 * Attribute getter / setter
 * ************************************************************************
 */
        
        /* ---------------------------------------------------------------------
        Methods for Attribute GroupBy
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute GroupBy
          * @param value: the value to set the attribute to
          */
        public void setGroupBy(VString value)
        {
            setAttribute(AttributeName.GROUPBY, value, null);
        }

        /**
          * (21) get VString attribute GroupBy
          * @return VString the value of the attribute
          */
        public VString getGroupBy()
        {
            VString vStrAttrib = new VString();
            String  s = getAttribute(AttributeName.GROUPBY, null, "Tested");
            vStrAttrib.setAllStrings(s, " ");
            return vStrAttrib;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute ReportAttr
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute ReportAttr
          * @param value: the value to set the attribute to
          */
        public void setReportAttr(VString value)
        {
            setAttribute(AttributeName.REPORTATTR, value, null);
        }

        /**
          * (21) get VString attribute ReportAttr
          * @return VString the value of the attribute
          */
        public VString getReportAttr()
        {
            VString vStrAttrib = new VString();
            String  s = getAttribute(AttributeName.REPORTATTR, null, JDFConstants.EMPTYSTRING);
            vStrAttrib.setAllStrings(s, " ");
            return vStrAttrib;
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute LogErrors
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute LogErrors
          * @param value: the value to set the attribute to
          */
        public void setLogErrors(int value)
        {
            setAttribute(AttributeName.LOGERRORS, value, null);
        }

        /**
          * (15) get int attribute LogErrors
          * @return int the value of the attribute
          */
        public int getLogErrors()
        {
            return getIntAttribute(AttributeName.LOGERRORS, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MaxGroups
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute MaxGroups
          * @param value: the value to set the attribute to
          */
        public void setMaxGroups(int value)
        {
            setAttribute(AttributeName.MAXGROUPS, value, null);
        }

        /**
          * (15) get int attribute MaxGroups
          * @return int the value of the attribute
          */
        public int getMaxGroups()
        {
            return getIntAttribute(AttributeName.MAXGROUPS, null, 0);
        }

        
        /* ---------------------------------------------------------------------
        Methods for Attribute MaxPerGroup
        --------------------------------------------------------------------- */
        /**
          * (36) set attribute MaxPerGroup
          * @param value: the value to set the attribute to
          */
        public void setMaxPerGroup(int value)
        {
            setAttribute(AttributeName.MAXPERGROUP, value, null);
        }

        /**
          * (15) get int attribute MaxPerGroup
          * @return int the value of the attribute
          */
        public int getMaxPerGroup()
        {
            return getIntAttribute(AttributeName.MAXPERGROUP, null, 0);
        }

}// end namespace JDF
