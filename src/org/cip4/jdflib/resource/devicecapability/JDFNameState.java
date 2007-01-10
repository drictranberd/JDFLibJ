/*
*
* The CIP4 Software License, Version 1.0
*
*
* Copyright (c) 2001-2006 The International Cooperation for the Integration of 
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

/**
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 * 
 * @author Elena Skobchenko
 *
 * JDFNameState.java
 *
 */

package org.cip4.jdflib.resource.devicecapability;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoBasicPreflightTest.EnumListType;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.util.StringUtil;

public class JDFNameState extends JDFAbstractState
{
    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[6];
    static 
    {
        atrInfoTable[0]  = new AtrInfoTable(AttributeName.ALLOWEDREGEXP,     0x33333311, AttributeInfo.EnumAttributeType.RegExp, null, null);
        atrInfoTable[1]  = new AtrInfoTable(AttributeName.ALLOWEDVALUELIST,  0x33333331, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
        atrInfoTable[2]  = new AtrInfoTable(AttributeName.CURRENTVALUE,      0x33333331, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[3]  = new AtrInfoTable(AttributeName.DEFAULTVALUE,      0x33333331, AttributeInfo.EnumAttributeType.NMTOKEN, null, null);
        atrInfoTable[4]  = new AtrInfoTable(AttributeName.PRESENTREGEXP,     0x33333311, AttributeInfo.EnumAttributeType.RegExp, null, null);
        atrInfoTable[5]  = new AtrInfoTable(AttributeName.PRESENTVALUELIST,  0x33333331, AttributeInfo.EnumAttributeType.NMTOKENS, null, null);
    }

    protected AttributeInfo getTheAttributeInfo() 
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }
    

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[1];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.VALUELOC, 0x33333311);
    }

    protected ElementInfo getTheElementInfo()
    {
        return new ElementInfo(super.getTheElementInfo(), elemInfoTable);
    }


    /**
     * constructor for JDFNameState
     * @param ownerDocument
     * @param qualifiedName
     */
    public JDFNameState(CoreDocumentImpl myOwnerDocument, String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * constructor for JDFNameState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     */
    public JDFNameState(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * constructor for JDFNameState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     * @param localName
     */
    public JDFNameState(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }

    //**************************************** Methods *********************************************

    /**
     * toString
     * @return String
     */
    public String toString()
    {
        return "JDFNameState[ --> " + super.toString() + " ]";
    }
    
    /* ******************************************************
    // Attribute getter/ setter
    **************************************************************** */

    public void setCurrentValue(String value)
    {
        setAttribute(AttributeName.CURRENTVALUE, value);
    }
    
    public String getCurrentValue()
    {
        return getAttribute(AttributeName.CURRENTVALUE, null, JDFConstants.EMPTYSTRING);
    }
    
    public void setDefaultValue(String value)
    {
        setAttribute(AttributeName.DEFAULTVALUE, value);
    }

    public String getDefaultValue()
    {
        return getAttribute(AttributeName.DEFAULTVALUE, null, JDFConstants.EMPTYSTRING);
    }
         
    public VString getAllowedValueList()
    {
        final String attribute = getAttribute(AttributeName.ALLOWEDVALUELIST, null, null);
        return attribute==null ? null : new VString(attribute, null);
    }

    public void setAllowedValueList(VString vs)
    {
        setAttribute(AttributeName.ALLOWEDVALUELIST, vs.getString(" ",null,null), null);
    }

    public VString getPresentValueList()
    {
        if (hasAttribute(AttributeName.PRESENTVALUELIST))
        {
            return new VString(getAttribute(AttributeName.PRESENTVALUELIST, null, JDFConstants.EMPTYSTRING), null);
        }
        return getAllowedValueList();
    }

    public void setPresentValueList(VString vs)
    {
        setAttribute(AttributeName.PRESENTVALUELIST, vs.getString(" ",null,null), null);
    }

    public void setAllowedRegExp(String value)
    {
        setAttribute(AttributeName.ALLOWEDREGEXP, value);
    }

    public String getAllowedRegExp()
    {
        return getAttribute(AttributeName.ALLOWEDREGEXP, null, JDFConstants.EMPTYSTRING);
    }

    public void setPresentRegExp(String value)
    {
        setAttribute(AttributeName.PRESENTREGEXP, value);
    }

    public String getPresentRegExp()
    {
        if (hasAttribute(AttributeName.PRESENTREGEXP))
        {
            return getAttribute(AttributeName.PRESENTREGEXP);
        }
        return getAllowedRegExp();
    }

 
    /* ******************************************************
    // Element getter / setter
    **************************************************************** */

    /**
     * fitsValue - tests, if the defined value matches Allowed test lists or
     * Present test lists, specified for this State
     *
     * @param String value - value to test
     * @param EnumFitsValue tetlists - test lists, that the value has to match.
     * In this State the test lists are ValueList, RegExp. 
     * Choose one of two values: FitsValue_Allowed or FitsValue_Present. (Defaults to Allowed)
     * 
     * @return boolean - true, if the value matches all test lists or if Allowed test lists are not specified
     */
    public boolean fitsValue(String value, EnumFitsValue testlists)
    {
        if (fitsListType(value))
            return (fitsValueList(value,testlists) && fitsRegExp(value,testlists));
        return false;
    }
    
    /**
     * fitsValueList - tests, if the defined 'value' matches 
     * the AllowedValueList or the PresentValueList, specified for this State
     *
     * @param String value - nmtokens to test
     * @param EnumFitsValue valuelist - Switches between AllowedValueList and PresentValueList.
     * @return boolean - true, if 'value' matches valuelist or if AllowedValueList is not specified
     */
    private final boolean fitsValueList(String value, EnumFitsValue valuelist)
    {
        VString vs = new VString(value, null);
        
        VString list;
        if (valuelist.equals(EnumFitsValue.Allowed)) 
        {
            list = getAllowedValueList();
        } 
        else  
        {
            list = getPresentValueList();
        }
        if (list==null)
            return true;
        
        EnumListType listType = getListType();
        if (listType.equals(EnumListType.CompleteList))
        {
            return fitsCompleteList(vs,list);
        }
        else if (listType.equals(EnumListType.CompleteOrderedList))
        { 
            return fitsCompleteOrderedList(vs,list);
        }
        else if (listType.equals(EnumListType.ContainedList))
        {
            return fitsContainedList(vs,list);
        }

        int v_size = vs.size();
        int l_size = list.size();
        
        for (int i=0; i<v_size; i++) // test every token, that 'value' consists of
        {
            boolean bFound = false;
            for (int j=0; j<l_size; j++)
            {
                String ve = (String)vs.elementAt(i);
                String le = (String)list.elementAt(j);
                if (ve.compareTo(le)==0) 
                {
                    bFound = true;  
                    break;
                }
            }
            if (!bFound)
                return false; // no such value in the 'list'
        }
        return true;
    }
    
    /**
     * fitsRegExp - tests, if the defined string 'str' matches
     * AllowedRegExp or the PresentRegExp, specified for this State
     *
     * @param String str - string to test
     * @param EnumFitsValue regexp - Switches between AllowedRegExp and PresentRegExp.
     * @return boolean - true, if 'str' matches RegExp or if AllowedRegExp is not specified
     */
    private final boolean fitsRegExp(String str, EnumFitsValue regexp) 
    {
        String rExp;
        if (regexp.equals(EnumFitsValue.Allowed)) 
        {
            rExp = getAllowedRegExp();
        } 
        else 
        {
            rExp = getPresentRegExp();
        }
        if(rExp.length()==0)
            return true; // if AllowedRegExp is not specified return true 

        VString v = new VString(str, null);
        int size=v.size();

        for (int i=0; i<size; i++) // tests every token, that 'value' consists of
        {
            if (!StringUtil.matches(v.stringAt(i),rExp))
                return false;
        }
        return true;
    }
    
    /**
     * fitsCompleteList - tests for the case, when ListType=CompleteList,
     * if the defined 'value' matches AllowedValueList or PresentValueList,
     * specified for this State
     *
     * @param JDFRectangleRangeList value - value to test
     * @param JDFRectangleRangeList list - testlist are either AllowedValueList or PresentValueList.
     * 
     * @return boolean - true, if 'value' matches testlist
     */
    private final boolean fitsCompleteList(VString value, VString list)
    {
        int v_size=value.size();
        int l_size=list.size();
        
        if (v_size!=l_size) 
            return false; 
        
        if (!isUnique(value)) 
            return false;
     
        VString valueList = new VString(value);

        boolean bFound;
        for (int i=l_size-1; i>=0; i--)
        {
            bFound = false;
            for (int j=valueList.size()-1; j>=0; j--)       
            {
                if (list.elementAt(i).equals(valueList.elementAt(j)))
                {
                    valueList.remove(j);
                    bFound = true;
                    break;
                }
            }
            if (!bFound) 
            {
                return false;
            }
        }
        return true;
    }

    
    /**
     * fitsCompleteOrderedList - tests for the case, when ListType=CompleteOrderedList,
     * if the defined 'value' matches AllowedValueList or PresentValueList,
     * specified for this State
     *
     * @param VString value - value to test
     * @param VString list - testlist are either AllowedValueList or PresentValueList.
     * 
     * @return boolean - true, if 'value' matches testlist
     */
     private final boolean fitsCompleteOrderedList(VString value, VString list)
     {
         int v_size = value.size();
         int l_size = list.size();
         
         if ( v_size != l_size ) 
              return false; 

         if (!isUnique(value)) 
             return false;

         for (int i=0; i<l_size; i++)
         {
             if (!list.elementAt(i).equals(value.elementAt(i))) 
             {
                 return false;
             }
         }
         return true;        
     }
     
    /**
     * fitsContainedList - tests for the case, when ListType=ContainedList,
     * if the defined 'value' matches AllowedValueList or PresentValueList,
     * specified for this State
     *
     * @param VString value - value to test
     * @param VString list - testlist is either AllowedValueList or PresentValueList.
     * 
     * @return boolean - true, if 'value' matches testlist
     */
     private final boolean fitsContainedList(VString value, VString list)
     {
         int v_size = value.size();
         int l_size = list.size();
         
         for (int i=0; i<v_size; i++)
         {
             for (int j=0; j<l_size; j++)
             {
                 if (value.elementAt(i).equals(list.elementAt(j))) 
                 {
                     return true;
                 }
             }
         }
         return false;
     }
    
    /**
     * isUnique - tests, if 'value' string has only unique tokens
     *
     * @param VString value - value to test
     * @return boolean - true, if 'value' has only unique tokens
     */ 
    private final boolean isUnique(VString v)
    {
        int size = v.size();
        for (int i=0; i<size; i++)
        {
            for (int j=0; j<size; j++)
            {
                if ( j!=i ) 
                {
                    String si =(String)v.elementAt(i);
                    String sj =(String)v.elementAt(j);
                    if (si.compareTo(sj)==0) 
                        return false;
                }
            }
        }
        return true;
    }
    

}