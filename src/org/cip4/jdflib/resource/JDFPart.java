/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2007 The International Cooperation for the Integration of
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
 *    Alternately, this acknowledgment mrSubRefay appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior writtenrestartProcesses()
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
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIrSubRefAL DAMAGES (INCLUDING, BUT NOT
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
 * originally based on software restartProcesses()
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *
 */
/**
 *========================================================================== class JDFPart extends JDFAutoPart
 * created 2001-09-06T10:02:57GMT+02:00 ==========================================================================
 *          @COPYRIGHT Heidelberger Druckmaschinen AG, 1999-2001 ALL RIGHTS RESERVED
 *              @Author: sabjon@topmail.de   using a code generator
 * Warning! very preliminary test version. Interface subject to change without prior notice! Revision history:   ...
 */

package org.cip4.jdflib.resource;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoPart;
import org.cip4.jdflib.core.AttributeInfo;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.util.StringUtil;


public class JDFPart extends JDFAutoPart
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for JDFPart
     * @param myOwnerDocument
     * @param qualifiedName
     */
    public JDFPart(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFPart
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    public JDFPart(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFPart
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    public JDFPart(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName,
        String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }

    /**
     * toString()
     * @return String
     */
    public String toString()
    {
        return "JDFPart[  --> " + super.toString() + " ]";
    }
    
    /**
     * gets a map of all Partition key value pairs, empty if no partition keys exist
     * @return JDFAttributeMap
     */
    public JDFAttributeMap getPartMap()
    {
        JDFAttributeMap am=getAttributeMap();
        Iterator it=am.getKeyIterator();
        JDFAttributeMap retMap=new JDFAttributeMap();
        while(it.hasNext())
        {
            final String key=(String) it.next();
            if(EnumPartIDKey.getEnum(key)!=null)
                retMap.put(key,am.get(key));
        }
        return retMap;
    }
    
    /**
     * sets the attributes of this to partmap
     * removes all other attributes
     * @param mPart attribute map for the part to set
     */
    public void setPartMap(JDFAttributeMap mPart)
    {
        removeAttributes(null);
        setAttributes(mPart);
    }

    /**
     * check whether the partition values match
     * 
     * @param key           the partition key
     * @param resourceValue the value of key in the resource
     * @param linkValue     the value of key in the part element or ref
     * @return boolean: true if linkValue matches the value or list in resourceValue
     * 
     */
    public static boolean matchesPart(String key, String resourceValue, String linkValue)
    {
        EnumPartIDKey eKey=EnumPartIDKey.getEnum(key);
        if(eKey==null)
            return resourceValue.equals(linkValue);
        boolean b;

        if(EnumPartIDKey.PartVersion.equals(eKey)
                || EnumPartIDKey.DocTags.equals(eKey)
                || EnumPartIDKey.ItemNames.equals(eKey)
                || EnumPartIDKey.PageTags.equals(eKey)
                || EnumPartIDKey.RunTags.equals(eKey)
                || EnumPartIDKey.SetTags.equals(eKey)
        )
        {
            b=StringUtil.matchesAttribute(linkValue,resourceValue,AttributeInfo.EnumAttributeType.NMTOKENS);
        }
        
        else if(EnumPartIDKey.DocCopies.equals(eKey)
                || EnumPartIDKey.DocIndex.equals(eKey)
                || EnumPartIDKey.DocRunIndex.equals(eKey)
                || EnumPartIDKey.DocSheetIndex.equals(eKey)
                || EnumPartIDKey.LayerIDs.equals(eKey)
                || EnumPartIDKey.PageNumber.equals(eKey)
                || EnumPartIDKey.RunIndex.equals(eKey)
                || EnumPartIDKey.SectionIndex.equals(eKey)
                || EnumPartIDKey.SetIndex.equals(eKey)
                || EnumPartIDKey.SetRunIndex.equals(eKey)
                || EnumPartIDKey.SetSheetIndex.equals(eKey)
                || EnumPartIDKey.SheetIndex.equals(eKey)
        )
        {
            b=StringUtil.matchesAttribute(linkValue,resourceValue,AttributeInfo.EnumAttributeType.IntegerRangeList);
        }
        
        else
        {
            b=resourceValue.equals(linkValue);
        }
        return b;
    }
    
    /**
     * overlapMap - identical keys must have the same values in both maps<br>
     * similar to JDFAttribute.overlapMap, but uses matchesPart instead of equals for the comparison
     * @param subMap the map to compare
     *
     * @return boolean: true if identical keys have the same values in both maps
     */
    public static boolean overlapPartMap(JDFAttributeMap resourceMap,JDFAttributeMap linkMap)
    {
        if((resourceMap==null)||(linkMap==null))
            return true; // null always overlaps with anything
        
        Enumeration subMapEnum = linkMap.keys();
        while (subMapEnum.hasMoreElements())
        {
            String key    = (String)subMapEnum.nextElement();
            String resVal = resourceMap.get(key);

            if (resVal != null)
            {
                String linkVal    = linkMap.get(key);
                if (!matchesPart(key,resVal,linkVal))
                {
                    return false;
                }
            }
        }
        return true;
    }

     
} // class JDFPart
