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
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFRangeList.java
 *
 */
package org.cip4.jdflib.datatypes;

import java.util.Vector;

import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.util.HashUtil;


/**
 * This abstract class is the representation of a range list. 
 * Intern these object are collected in a vector and there are several methods 
 * to provide an access to the data.
 * A range has the following format : "1~3.4"
 * The class member Vector rangeList contains for example "1~3.4" , "7~5"
 */
public abstract class JDFRangeList implements JDFBaseDataTypes
{
    //**************************************** Constructors ****************************************
    protected Vector rangeList = new Vector();
    
    //**************************************** Constructors ****************************************
    /**
     * constructor
     */
    public JDFRangeList()
    {
        //default super constructor
    }

    //**************************************** Methods *********************************************
 

    /**
     * getElementAt - returns the element at the ith position
     *
     * @return Object - the range object at the given position
     */
    public final Object elementAt(int i)
    {
        return rangeList.elementAt(i);
    }


    /**
     * remove - removes an object from the vector
     *
     * @param int i - the index of the element to remove
     */
    public final void remove(int i)
    {
        rangeList.remove(i);
    }

 
    /**
    * isPartOfRange - check whether range 'x' is in the range defined by 'this'
    * 
    * @param JDFDateTimeRange x - JDFDateTimeRange to test
    * @return boolean - true if 'x' is in the range defined by 'this'
    */
    public final boolean isPartOfRange(JDFRange x)
    {
        int sz = rangeList.size();
        for (int i = 0; i < sz; i++)
        {
            JDFRange r = (JDFRange)rangeList.elementAt(i);
    
            if (r.isPartOfRange(x))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * isPartOfRange - check whether DateTimeRangeList 'x' is within this range list
     * 
     * @param JDFDateTimeRangeList x - the range list to test
     * @return boolean - true if range list 'x' is within 'this' range list, else false
     */
    public final boolean isPartOfRange(JDFRangeList x)
    {
        int sz=x.size();
        for(int i=0;i<sz;i++)
        {
            JDFRange range = x.at(i);
            if(isPartOfRange(range)) 
                return true;
        }
        return false;
    }

    /**
     * getString - serialize to string
     * 
     * @return String - a list of ranges in the format PT30M30S~PT35M 
     * (duration (JDFDate) has a format=P1Y2M3DT12H30M30S)
     * @deprecated 060418 use toString
     */
    public final String getString()
    {
         return toString();
     }
    /**
     * getString - serialize to string
     * 
     * @return String - a list of ranges in the format PT30M30S~PT35M 
     * (duration (JDFDuration) has a format=P1Y2M3DT12H30M30S)  
     */
     public final String toString()
     {
         String s = JDFConstants.EMPTYSTRING;
         int sz = rangeList.size();
         for (int i = 0; i < sz; i++)
         {
             s += rangeList.elementAt(i).toString();
             if (i < (sz - 1))
             {    
                 s += JDFConstants.BLANK; 
             } 
         }
         return s;
     }

    /**
    * number of Duration range elements
    * @return int the number of Duration ranges in the list
    */
    public final int size()
    {
        return rangeList.size();
    }

    /**
    * cleanup and empty the internal storge
    */
    public final void clear()
    {
        rangeList.clear();
    }

    /**
    * at(i) - get the i-th range element
    * 
    * @param int i - index of the range to retrieve
    * 
    * @return JDFDateTimeRange - JDFDateTimeRange at the position i
    */
    public final JDFRange at(int i)
    {
       if((i<0)||(i>=rangeList.size())) 
            throw new JDFException("JDFDateTimeRangeList.at(i): illegal index" + i);
       return (JDFRange) rangeList.elementAt(i);
    }

    /**
    * begin() - returns the first JDFDateTimeRange of the JDFDateTimeRangeList
    *
    * @return JDFDateTimeRange: the first JDFDateTimeRange of the JDFDateTimeRangeList
    */
    public final JDFRange begin()
    {
        return (JDFRange)rangeList.firstElement();
    }

    /**
    * begin() - returns the last JDFDateTimeRange of the JDFDateTimeRangeList
    *
    * @return JDFDateTimeRange: the last JDFDateTimeRange of the JDFDateTimeRangeList
    */
    public final JDFRange end()
    {
        return (JDFRange)rangeList.lastElement();
    }

    /**
     * erase(i) - Removes the i-th element of the range list
     * 
     * @param int i: index of element (range) to remove
     */
    public final void erase(int i)
    {
         rangeList.removeElementAt(i);
     }

    /**
     * isList - tests if 'this' is a List 
     * 
     * @return boolean - true if 'this' contains no ranges
     */
    public final boolean isList()
    {
        int sz = rangeList.size();
        for (int i = 0; i < sz; i++)
        {
            JDFRange range =  at(i);
            if (!range.getLeftObject().equals(range.getRightObject()))
                return false;
        }
        return true; // if we are here 'this' is a List
    }
    
    /**
     * equals - returns true if both JDFNumberRangeList are equal otherwise false
     *
     * @return boolean - true if equal otherwise false
     */
    final public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other == null)
        {
            return false;
        }
        if (!other.getClass().equals(getClass()))
        {
            return false;
        }
        
        int size = rangeList.size();
        final JDFRangeList rangeListOther = (JDFRangeList)other;
        int sizeOther = rangeListOther.rangeList.size();
        if (size != sizeOther)
        {
            return false;
        }
        for (int i = 0; i < size; i++)
        {
            final JDFRange range = at(i);
            final JDFRange rangeOther = rangeListOther.at(i);

            if (!range.equals(rangeOther))
            {
                return false;
            }
        }
        return true;
        
    }
    
    /**
     * hashCode complements equals() to fulfill the equals/hashCode contract
     */
    final public int hashCode()
    {
        return HashUtil.hashCode(0, this.toString());
    }

    public abstract boolean isUniqueOrdered();
    /**
     * isUnique - tests if 'this' has only unique values
     * 
     * @return boolean: true if 'this' is a unique range list
     */
    public final boolean isUnique()
    {
        int sz = rangeList.size();
        for (int i = 0; i < sz; i++)
        {
            JDFRange range = at(i);
            for (int j = 0; j < sz; j++)
            {
                if (j != i)
                {
                    JDFRange other =at(j);
                    // even if one of the range deliminators belongs to any other range - return false (range is not unique)
                    if ((other.inObjectRange(range.getLeftObject())) || (other.inObjectRange(range.getRightObject())))
                        return false;
                }
            }
        }
        return true;
    }
    
    public abstract boolean isOrdered();
}
