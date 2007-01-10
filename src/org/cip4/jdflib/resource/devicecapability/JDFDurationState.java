/*
*
* The CIP4 Software License, Version 1.0
*
*
* Copyright (c) 2001-2004 The International Cooperation for the Integration of 
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
 * JDFDurationState.java
 *
 */

package org.cip4.jdflib.resource.devicecapability;

import java.util.zip.DataFormatException;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoBasicPreflightTest.EnumListType;
import org.cip4.jdflib.core.AtrInfoTable;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.datatypes.JDFDurationRangeList;
import org.cip4.jdflib.util.JDFDate;

public class JDFDurationState extends JDFAbstractState
{
	private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[4];
    static 
    {
        atrInfoTable[0]  = new AtrInfoTable(AttributeName.ALLOWEDVALUELIST,  0x33333311, AttributeInfo.EnumAttributeType.DurationRangeList, null, null);
        atrInfoTable[1]  = new AtrInfoTable(AttributeName.CURRENTVALUE,      0x33333311, AttributeInfo.EnumAttributeType.duration, null, null);
        atrInfoTable[2]  = new AtrInfoTable(AttributeName.DEFAULTVALUE,      0x33333311, AttributeInfo.EnumAttributeType.duration, null, null);
        atrInfoTable[3]  = new AtrInfoTable(AttributeName.PRESENTVALUELIST,  0x33333311, AttributeInfo.EnumAttributeType.DurationRangeList, null, null);
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
     * constructor for JDFDurationState
     * @param ownerDocument
     * @param qualifiedName
     */
    public JDFDurationState(CoreDocumentImpl myOwnerDocument, String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * constructor for JDFDurationState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     */
    public JDFDurationState(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * constructor for JDFDurationState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     * @param localName
     */
    public JDFDurationState(
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
        return "JDFDurationState[ --> " + super.toString() + " ]";
    }
    
	/* ******************************************************
	// Attribute getter/ Setter
	**************************************************************** */	

    public void setCurrentValue(JDFDate value)
    {
        setAttribute(AttributeName.CURRENTVALUE, value.getDateTimeISO(), JDFConstants.EMPTYSTRING);
    }

    public JDFDate getCurrentValue()
    {
        JDFDate nMyDate = null;
        String str = JDFConstants.EMPTYSTRING;
        str = getAttribute(AttributeName.CURRENTVALUE, null, JDFConstants.EMPTYSTRING);
        if (!JDFConstants.EMPTYSTRING.equals(str))
        {
            try
            {
                nMyDate = new JDFDate(str);
            }
            catch(DataFormatException dfe)
            {
                throw new JDFException("not a valid date string. Malformed JDF");
            }
        }
        return nMyDate;
    }
   

    public void setDefaultValue(JDFDate value)
    {
        setAttribute(AttributeName.DEFAULTVALUE, value.getDateTimeISO(), JDFConstants.EMPTYSTRING);
    }

   
    public JDFDate getDefaultValue()
    {
        JDFDate nMyDate = null;
        String str = JDFConstants.EMPTYSTRING;
        str = getAttribute(AttributeName.DEFAULTVALUE, null, JDFConstants.EMPTYSTRING);
        if (!JDFConstants.EMPTYSTRING.equals(str))
        {
            try
            {
                nMyDate = new JDFDate(str);
            }
            catch(DataFormatException dfe)
            {
                throw new JDFException("not a valid date string. Malformed JDF");
            }
        }
        return nMyDate;
    }
    
   
    public void setAllowedValueList( JDFDurationRangeList value)
    {
		setAttribute(AttributeName.ALLOWEDVALUELIST, value.toString());
	}
    
 
    public JDFDurationRangeList getAllowedValueList()
    {
        try
        {
        	final String attribute = getAttribute(AttributeName.ALLOWEDVALUELIST);
            if(attribute!=null)
                return new JDFDurationRangeList(attribute);
        }
        catch(DataFormatException dfe)
        {
            // nop 
        }
        return null;
	}
    
   
    public void setPresentValueList( JDFDurationRangeList value)
    {
		setAttribute(AttributeName.PRESENTVALUELIST, value.toString());
	}
	
    
	public JDFDurationRangeList getPresentValueList()
    {
		if (hasAttribute(AttributeName.PRESENTVALUELIST)) 
        {
            JDFDurationRangeList r = null;
            try
            {
            	r = new JDFDurationRangeList(getAttribute(AttributeName.PRESENTVALUELIST));
            }
            catch(DataFormatException dfe)
            {
            	return null;
            }
            return r;
		}
		return getAllowedValueList();
	}

    
	/* ******************************************************
	// Element getter / Setter
	**************************************************************** */

    
    /* ******************************************************
    // FitsValue Methods
    **************************************************************** */
    
    /**
     * fitsValue - tests, if the defined value matches Allowed test lists or Present test lists,
     * specified for this State
     *
     * @param String value - value to test
     * @param EnumFitsValue tetlists - test lists, that the value has to match.
     * In this State the test list is ValueList. 
     * Choose one of two values: FitsValue_Allowed or FitsValue_Present. (Defaults to Allowed)
     * 
     * @return boolean - true, if the value matches test list or if AllowedValueList is not specified
     */
    public final boolean fitsValue(String value, EnumFitsValue testlists)
    {
        if (fitsListType(value))
        {
            JDFDurationRangeList rangelist = null;
            try 
            {
                rangelist = new JDFDurationRangeList(value);
            }
            catch (DataFormatException dfe)
            {
                return false;
            }
            return (fitsValueList(rangelist,testlists));

        }
        return false; // the value doesn't fit ListType attribute of this State
    }
    
    
    /**
     * fitsValueList - tests, if the defined 'rangelist' matches 
     * the AllowedValueList or in the PresentValueList, specified for this State
     *
     * @param JDFDurationRangeList rangelist - range list to test
     * @param EnumFitsValue valuelist - Switches between AllowedValueList and PresentValueList.
     * 
     * @return boolean - true, if 'rangelist' matches the valuelist or if AllowedValueList is not specified
     */
    private final boolean fitsValueList(JDFDurationRangeList rangelist, EnumFitsValue valuelist)
    {
        JDFDurationRangeList list;
        if (valuelist.equals(EnumFitsValue.Allowed))
        {
            list = getAllowedValueList();
        } 
        else 
        {
            list = getPresentValueList();
        }
        if (list == null)
            return true;
        
        EnumListType listType=getListType();
        if (listType.equals(EnumListType.CompleteList))
        {
            return fitsCompleteList(rangelist,list);
        }
        else if (listType.equals(EnumListType.CompleteOrderedList))
        { 
            return fitsCompleteOrderedList(rangelist,list);
        }
        else if (listType.equals(EnumListType.ContainedList))
        {
            return fitsContainedList(rangelist,list);
        }

        return (list.isPartOfRange(rangelist));

    }
    
   
    /**
     * fitsCompleteList - tests for the case, when ListType=CompleteList,
     * if the defined 'value' matches AllowedValueList or PresentValueList,
     * specified for this State
     *
     * @param JDFDurationRangeList value - value to test
     * @param JDFDurationRangeList list - testlist are either AllowedValueList or PresentValueList.
     * 
     * @return boolean - true, if 'value' matches testlist
     */
    private final boolean fitsCompleteList(JDFDurationRangeList value, JDFDurationRangeList list)
    {
        int v_size=value.size();
        int l_size=list.size();
        
        if (v_size!=l_size) 
            return false; 
        
        if (!value.isUnique()) 
            return false;
     
        JDFDurationRangeList valueList = new JDFDurationRangeList(value);

        boolean bFound;
        for (int i=l_size-1; i>=0; i--)
        {
            bFound = false;
            for (int j=valueList.size()-1; j>=0; j--)       
            {
                if (list.at(i).equals(valueList.at(j)))
                {
                    valueList.erase(j);
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
    * @param JDFDurationRangeList value - value to test
    * @param JDFDurationRangeList list - testlist are either AllowedValueList or PresentValueList.
    * 
    * @return boolean - true, if 'value' matches testlist
    */
    private final boolean fitsCompleteOrderedList(JDFDurationRangeList value, JDFDurationRangeList list)
    {
        int v_size = value.size();
        int l_size = list.size();
        
        if ( v_size != l_size ) 
             return false; 

        if (!value.isUnique()) 
            return false;

        for (int i=0; i<l_size; i++)
        {
            if (!list.at(i).equals(value.at(i))) 
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
    * @param JDFDurationRangeList value - value to test
    * @param JDFDurationRangeList list - testlist are either AllowedValueList or PresentValueList.
    * 
    * @return boolean - true, if 'value' matches testlist
    */
    private final boolean fitsContainedList(JDFDurationRangeList value, JDFDurationRangeList list)
    {
        int v_size = value.size();
        int l_size = list.size();
        
        for (int i=0; i<v_size; i++)
        {
            for (int j=0; j<l_size; j++)
            {
                if (value.at(i).equals(list.at(j))) 
                {
                    return true;
                }
            }
        }
        return false;
    }
}