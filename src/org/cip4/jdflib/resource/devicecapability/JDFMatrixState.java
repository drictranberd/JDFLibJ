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
 * JDFMatrixState.java
 *
 */

package org.cip4.jdflib.resource.devicecapability;

import java.util.Vector;
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
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFMatrix;
import org.cip4.jdflib.datatypes.JDFRectangle;
import org.cip4.jdflib.resource.JDFValue;
import org.cip4.jdflib.resource.JDFValue.EnumValueUsage;

public class JDFMatrixState extends JDFAbstractState
{
    private static final long serialVersionUID = 1L;

    private static AtrInfoTable[] atrInfoTable = new AtrInfoTable[8];
    static 
    {
        atrInfoTable[0]  = new AtrInfoTable(AttributeName.ALLOWEDROTATEMOD,  0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[1]  = new AtrInfoTable(AttributeName.ALLOWEDSHIFT,      0x33333311, AttributeInfo.EnumAttributeType.NumberList, null, null);
        atrInfoTable[2]  = new AtrInfoTable(AttributeName.ALLOWEDTRANSFORMS, 0x33333311, AttributeInfo.EnumAttributeType.enumerations, EnumOrientation.getEnum(0), null);
        atrInfoTable[3]  = new AtrInfoTable(AttributeName.CURRENTVALUE,      0x33333331, AttributeInfo.EnumAttributeType.matrix, null, null);
        atrInfoTable[4]  = new AtrInfoTable(AttributeName.DEFAULTVALUE,      0x33333331, AttributeInfo.EnumAttributeType.matrix, null, null);
        atrInfoTable[5]  = new AtrInfoTable(AttributeName.PRESENTROTATEMOD,  0x33333311, AttributeInfo.EnumAttributeType.double_, null, null);
        atrInfoTable[6]  = new AtrInfoTable(AttributeName.PRESENTSHIFT,      0x33333311, AttributeInfo.EnumAttributeType.NumberList, null, null);
        atrInfoTable[7]  = new AtrInfoTable(AttributeName.PRESENTTRANSFORMS, 0x44444431, AttributeInfo.EnumAttributeType.enumerations, EnumOrientation.getEnum(0), null);
    }

    protected AttributeInfo getTheAttributeInfo() 
    {
        return super.getTheAttributeInfo().updateReplace(atrInfoTable);
    }
    

    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[1];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.VALUE, 0x33333311);
    }

    protected ElementInfo getTheElementInfo()
    {
        return new ElementInfo(super.getTheElementInfo(), elemInfoTable);
    }


    /**
     * constructor for JDFMatrixState
     * @param ownerDocument
     * @param qualifiedName
     */
    public JDFMatrixState(CoreDocumentImpl myOwnerDocument, String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * constructor for JDFMatrixState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     */
    public JDFMatrixState(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * constructor for JDFMatrixState
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     * @param localName
     */
    public JDFMatrixState(
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
        return "JDFMatrixState[ --> " + super.toString() + " ]";
    }
    
    public void setCurrentValue(JDFMatrix value)
    {
       setAttribute(AttributeName.CURRENTVALUE, value.toString(), null);
    }

    
    public JDFMatrix getCurrentValue()
    {
       try
       {
           return new JDFMatrix(getAttribute(AttributeName.CURRENTVALUE));
       }
       catch (DataFormatException e)
       {
           throw new JDFException("JDFMatrixState.getCurrentValue: Attribute CURRENTVALUE is not capable to create JDFMatrix");
       }
    }

    
    public void setDefaultValue(JDFMatrix value)
    {
       setAttribute(AttributeName.DEFAULTVALUE, value.toString(), null);
    }

    
    public JDFMatrix getDefaultValue()
    {
       try
       {
           return new JDFMatrix(getAttribute(AttributeName.DEFAULTVALUE));
       }
       catch (DataFormatException e)
       {
           throw new JDFException("JDFMatrixState.getDefaultValue: Attribute DEFAULTVALUE is not capable to create JDFMatrix");
       }
    }
        
    
    public void setAllowedRotateMod(double value)
    {
        setAttribute(AttributeName.ALLOWEDROTATEMOD, value, null);
    }

    
    public double getAllowedRotateMod()
    {
        return getRealAttribute(AttributeName.ALLOWEDROTATEMOD, null, 0.0);
    }

    
    public void setPresentRotateMod(double value)
    {
        setAttribute(AttributeName.PRESENTROTATEMOD, value, null);
    }

    
    public double getPresentRotateMod()
    {
        if (hasAttribute(AttributeName.PRESENTROTATEMOD))
        {
            return getRealAttribute(AttributeName.PRESENTROTATEMOD, null, 0.0);
        }
        return getAllowedRotateMod();
    }

    
    public void setAllowedShift(JDFRectangle value)
    {
       setAttribute(AttributeName.ALLOWEDSHIFT, value.toString());
    }

    
    public JDFRectangle getAllowedShift()
    {

        try
        {
            return new JDFRectangle(
                            getAttribute(
                               AttributeName.ALLOWEDSHIFT,
                               null,
                               JDFConstants.EMPTYSTRING));
        }
        catch (DataFormatException e)
        {
            throw new JDFException("JDFMatrixState.getAllowedShift: AttributeValue not capable to create JDFRectangle");        
        }
    }
    

    public void setPresentShift(JDFRectangle value)
    {
        setAttribute(AttributeName.PRESENTSHIFT, value.toString());
    }
   

    public JDFRectangle getPresentShift()
    {
        if (hasAttribute(AttributeName.PRESENTSHIFT))
        {
            try
            {
                return new JDFRectangle(getAttribute(
                                            AttributeName.PRESENTSHIFT,
                                            null,
                                            JDFConstants.EMPTYSTRING));
            }
            catch (DataFormatException e)
            {
               throw new JDFException("JDFMatrixState.getPresentShift: AttributeValue not capable to create JDFRectangle");
            }
        }
        return getAllowedShift();
    }

   
    public Vector getAllowedTransforms()
    {
        return getEnumerationsAttribute(
                AttributeName.ALLOWEDTRANSFORMS, null, EnumOrientation.getEnum(0), false);
    }

   

    public void setAllowedTransforms(Vector value)
    {
        setEnumerationsAttribute(AttributeName.ALLOWEDTRANSFORMS, value,null);
    }

    
    public Vector getPresentTransforms()
    {
        if (hasAttribute(AttributeName.PRESENTTRANSFORMS))
        {
            return getEnumerationsAttribute(
                AttributeName.PRESENTTRANSFORMS,
                null, EnumOrientation.getEnum(0),false);
         }
        return getAllowedTransforms();
    }

    
    public void setPresentTransforms(Vector value)
    {
        setEnumerationsAttribute(AttributeName.PRESENTTRANSFORMS, value, null);
    }


    /* ******************************************************
    // Element getter / setter
    **************************************************************** */


    public JDFValue getValue(int iSkip)
    {
        JDFValue e = (JDFValue)getElement(ElementName.VALUE, JDFConstants.EMPTYSTRING, iSkip);
        return e;
    }


    public JDFValue appendValue()
    {
        return (JDFValue) appendElement(ElementName.VALUE, null);
    }
    
    /**
    * Appends element Loc to the end of the i-th subelement Value
    *
    * @param int iSkip: number of Value elements to skip 
    * ( iSkip=0 - first Value element)
    * @return JDFLoc: newly created Loc element
    */
    public JDFLoc appendValueLocLoc(int iSkip)
    {
        JDFValue val = getValue(iSkip);
        if(val==null)
            return null;
        return val.appendLoc();
     }

    /* ******************************************************
    // Subelement attribute and element Getter / Setter
    **************************************************************** */

    /** 
     * Sets the AllowedValue attribute of the i-th subelement Value
     *
     * @param int iSkip: the number of Value elements to skip
     * @param JDFMatrix value: value to set the attribute to
     */
    public void setValueAllowedValue(int iSkip, JDFMatrix value)
    {
        JDFValue e = (JDFValue) getElement(ElementName.VALUE,null,iSkip);
        e.setAllowedValue(value.toString());
    }
    
    /**
     * Gets the AllowedValue attribute of the i-th subelement Value
     *
     * @param int iSkip: the number of Value elements to skip
     * @return JDFMatrix: the attribute value
     */
    public final JDFMatrix getValueAllowedValue(int iSkip) 
    {
        JDFValue e = (JDFValue) getElement(ElementName.VALUE,null,iSkip);
        
        try
        {
            return new JDFMatrix(e.getAllowedValue());
        }
        catch (DataFormatException dfe)
        {
            throw new JDFException("JDFMatrixState.getValueAllowedValue: AttributeValue not capable to create JDFMatrix");
        }
        
    }
    
    
    /**
    * Sets the ValueUsage attribute of the i-th subelement Value
    *
    * @param int iSkip: the number of Value elements to skip
    * @param EnumFitsValue value: value to set the attribute to
    */
    public void setValueValueUsage(int iSkip, EnumFitsValue value)
    {
        JDFValue e = (JDFValue) getElement(ElementName.VALUE,null,iSkip);
        e.setValueUsage(EnumValueUsage.getEnum(value.getName()) );
    }
    
    /**
    * Gets the value of attribute ValueUsage of the i-th subelement Value
    *
    * @param int iSkip: the number of Value elements to skip
    * @return EnumFitsValue: the attribute value
    */
    public final EnumFitsValue getValueValueUsage(int iSkip) 
    {
        JDFValue e = (JDFValue) getElement(ElementName.VALUE,null,iSkip);
        return EnumFitsValue.getEnum(e.getValueUsage().getName());
    }

    
    /* ******************************************************
    // FitsValue Methods
    **************************************************************** */
    
    /**
     * fitsValue - tests, if the defined value matches Allowed test lists or Present test lists,
     * specified for this State
     *
     * @param String value - value to test
     * @param EnumFitsValue tetlists - test lists, that the value has to match.
     * In this State the test lists are RotateMod, Shift, Transforms and ValueElem. 
     * Choose one of two values: FitsValue_Allowed or FitsValue_Present. (Defaults to Allowed)
     * 
     * @return boolean - true, if the value matches all test lists or if Allowed test lists are not specified
     */
    public boolean fitsValue(String value, EnumFitsValue testlists)
    {
        VString vs = new VString(value, JDFConstants.BLANK);
        int siz = vs.size();
        if (siz%6!=0) {
            return false;
        }
        VString matrixList = new VString();
        int i=0;
        StringBuffer sb = new StringBuffer(250);
        sb.append((String) vs.elementAt(i));
        while ((i+1)<siz)
        {
            do  {
                sb.append(JDFConstants.BLANK);
                i++;
                sb.append((String) vs.elementAt(i));
            }
            while ((i+1)%6!=0);
            matrixList.add(sb.toString());
            if ((i+1)<siz)
            { 
                i++;
                sb = new StringBuffer(250);
                sb.append((String) vs.elementAt(i));
            }
        }

        if (fitsListType(matrixList))
        {
            for (int k=0; k<matrixList.size(); k++) 
            {
                String str = (String) matrixList.elementAt(k);
                JDFMatrix matrix;
                try {
                    matrix = new JDFMatrix(str);
                }
                catch (DataFormatException dfe)
                {
                    return false;
                }
                if (( fitsRotateMod(matrix,testlists)   && 
                      fitsShift(matrix,testlists)       &&
                      fitsTransforms(matrix,testlists)  &&
                      fitsValueElem (matrix,testlists)) == false)
                      return false;
            }
            return true;    
        }
        return false;        
    }
        
    /**
     * fitsListType - tests, if the defined 'value' matches value of ListType attribute,
     * specified for this State
     *
     * @param VString value - vector of matrices to test
     * 
     * @return boolean - true, if 'value' matches specified ListType
     */
    private final boolean fitsListType(VString matrixList)
    {
        EnumListType listType=getListType();

        int size = matrixList.size();
        for (int i=0; i<size; i++)
        {
            try
            {
                new JDFMatrix((String) matrixList.elementAt(i));
            }
            catch (JDFException e)
            {
                return false;
            }
            catch (DataFormatException dfe)
            {
                return false;
            }
        }
        
        if (listType.equals(EnumListType.SingleValue) || listType.equals(EnumListType.getEnum(0))) 
        {// default ListType = SingleValue
            return (size==1);
        }    
        else if ( listType.equals(EnumListType.List))
        {
            return true; 
        }
        else if (listType.equals(EnumListType.UniqueList))
        {
            for (int i=0; i<size; i++)
            {
                for (int j=0; j<size; j++)
                {
                    if ( j!=i ) 
                    {
                        String mi =(String)matrixList.elementAt(i);
                        String mj =(String)matrixList.elementAt(j);
                        if (mi.compareTo(mj)==0) 
                            return false;
                    }
                }
            }
            return true;
        }
        else 
        {
            throw new JDFException ("JDFMatrixState.fitsListType illegal ListType attribute"); 
        }
    }
    
    
    /**
     * fitsValueElem - tests, if the defined JDFMatrix 'matrix' matches 
     * subelement Value, specified for this State
     *
     * @param JDFMatrix matrix - JDFMatrix to test
     * @param EnumFitsValue valueusage - Switches between 
     * Allowed and Present configuration in subelement Value.
     * 
     * @return boolean - true, if 'matrix' matches subelement Value 
     */
    private final boolean fitsValueElem(JDFMatrix matrix, EnumFitsValue valuelist)
    {
        
        VElement v = getChildElementVector(ElementName.VALUE, null, null, true, 0,false);
        int siz=v.size();
        boolean hasValue=false;
        for (int i=0; i<siz; i++)
        {
            JDFValue elm = (JDFValue) v.elementAt(i);
            if (elm.hasAttribute(AttributeName.VALUEUSAGE))
            {
                EnumFitsValue valueUsage = getValueValueUsage(i); 
                if (valuelist.equals(valueUsage))
                {
                    hasValue=true;
                    JDFMatrix value = getValueAllowedValue(i);
                    if (value.equals(matrix))
                        return true; // we have found it
                }               
            }
            else 
            {
                hasValue=true;
                JDFMatrix value = getValueAllowedValue(i);
                if (value.equals(matrix))
                    return true; // we have found it
            }
        }
        return !hasValue; // if no matching, there was no filter
    }
    
    /**
     * fitsRotateMod - tests, if the defined 'matrix' matches 
     * the AllowedRotateMod or PresentRotateMod, specified for this State
     *
     * @param JDFMatrix matrix - matrix to test
     * @param EnumFitsValue rotatemod - Switches between AllowedRotateMod and PresentRotateMod.
     * @return boolean - true, if 'matrix' matches the RotateMod or if AllowedRotateMod is not specified
     */
    private final boolean fitsRotateMod(JDFMatrix matrix, EnumFitsValue rotatemod)
    {
        if (rotatemod.equals(EnumFitsValue.Allowed)) 
        {
            if(!hasAttribute(AttributeName.ALLOWEDROTATEMOD))
                return true;
        } 
        else 
        {
            if(!hasAttribute(AttributeName.ALLOWEDROTATEMOD) &&
               !hasAttribute(AttributeName.PRESENTROTATEMOD))
                return true;
        }
        
        double rm;
        if (rotatemod.equals(EnumFitsValue.Allowed)) 
        {
            rm = getAllowedRotateMod();
        } 
        else 
        {
            rm = getPresentRotateMod();
        }
                
        double a = matrix.getA();
        double b = matrix.getB();
        double c = matrix.getC();
        double d = matrix.getD();

        if ((a*d-b*c)==0)
            return false;

        double param = a/java.lang.Math.sqrt(java.lang.Math.abs(a*d-b*c));

        if (((param-EPSILON)>1) || ((param+EPSILON)<-1)) {
            return false;
        }
        if (param>1) {
            param=param-EPSILON;
        }
        if (param<-1) {
            param=param+EPSILON;
        }

        double fi = java.lang.Math.acos(param)*180/java.lang.Math.PI; // 0~180

        double result = (fi+EPSILON) - (rm * (int) ((fi+EPSILON)/rm) );
        double result180 = (fi+180+EPSILON) - (rm * (int) ((fi+180+EPSILON)/rm) );
        
        return ((java.lang.Math.abs(result) <= 2*EPSILON) || 
                (java.lang.Math.abs(result180) <= 2*EPSILON) ) ;

    }
    

    /**
     * fitsShift - tests, if the defined 'matrix' matches 
     * the AllowedShift or PresentShift, specified for this State
     *
     * @param JDFMatrix matrix - matrix to test
     * @param EnumFitsValue shift - Switches between AllowedShift and PresentShift.
     * @return boolean - true, if 'matrix' matches the Shift or if AllowedShift is not specified
     */
    private final boolean fitsShift(JDFMatrix matrix, EnumFitsValue shift)
    {
        
        if (shift.equals(EnumFitsValue.Allowed)) 
        {
            if(!hasAttribute(AttributeName.ALLOWEDSHIFT))
                return true;
        } 
        else 
        {
            if(!hasAttribute(AttributeName.ALLOWEDSHIFT)&&
               !hasAttribute(AttributeName.PRESENTSHIFT))
                return true;
        }

        JDFRectangle shiftValue;
        if (shift.equals(EnumFitsValue.Allowed)) 
        {
            shiftValue = new JDFRectangle(getAllowedShift());
        } 
        else 
        {
            shiftValue = new JDFRectangle(getPresentShift());
        }
        
        double minTx = shiftValue.getLlx();
        double minTy = shiftValue.getLly();
        double maxTx = shiftValue.getUrx();
        double maxTy = shiftValue.getUry();

        double Tx = matrix.getTx();
        double Ty = matrix.getTy();

        return ((Tx>=minTx)&&(Tx<=maxTx)&&(Ty>=minTy)&&(Ty<=maxTy));
    }
        
    //////////////////////////////////////////////////////////////////////
    
    /**
     * fitsTransforms - tests, if the defined 'matrix' matches 
     * the AllowedTransforms or PresentTransforms, specified for this State
     *
     * @param JDFMatrix matrix - matrix to test
     * @param EnumFitsValue transforms - Switches between AllowedTransforms and PresentTransforms.
     * @return boolean - true, if 'matrix' matches the Transforms or if AllowedTransforms is not specified
     */
    private final boolean fitsTransforms(JDFMatrix matrix, EnumFitsValue transforms)
    {
        if (transforms.equals(EnumFitsValue.Allowed)) 
        {
            if(!hasAttribute(AttributeName.ALLOWEDTRANSFORMS))
                return true;
        } 
        else 
        {
            if(!hasAttribute(AttributeName.ALLOWEDTRANSFORMS) &&
               !hasAttribute(AttributeName.PRESENTTRANSFORMS))
                return true;
        }

        double nT = EPSILON; // negative tolerance
        double pT = EPSILON; // positive tolerance
        
        double a = matrix.getA();
        double b = matrix.getB();
        double c = matrix.getC();
        double d = matrix.getD(); 
        
        double det=(a*d-b*c);
        
        if (det==0)
            return false;
        
        a=a/java.lang.Math.sqrt(java.lang.Math.abs(det));
        b=b/java.lang.Math.sqrt(java.lang.Math.abs(det));
        c=c/java.lang.Math.sqrt(java.lang.Math.abs(det));
        d=d/java.lang.Math.sqrt(java.lang.Math.abs(det));
        
        Vector vTransf;
        if (transforms.equals(EnumFitsValue.Allowed)) 
        {
            vTransf = getAllowedTransforms();
        } 
        else 
        {
            vTransf = getPresentTransforms();
        }
        int siz=vTransf.size();
        for (int i=0; i<siz; i++)
        {
            EnumOrientation orientation = (EnumOrientation) vTransf.elementAt(i);
           
            if (orientation.equals(EnumOrientation.Flip0)) // a=1 b=0 c=0 d=-1
            {
                if ((a-1<pT)&&(a-1>-nT)&&(b<pT)&&(b>-nT)&&(c<pT)&&(c>-nT)&&(d+1<pT)&&(d+1>-nT)) 
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Flip90)) // a=0 b=-1 c=-1 d=0
            {
                if ((a<pT)&&(a>-nT)&&(b+1<pT)&&(b+1>-nT)&&(c+1<pT)&&(c+1>-nT)&&(d<pT)&&(d>-nT))
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Flip180)) // a=-1 b=0 c=0 d=1
            {
                if ((a+1<pT)&&(a+1>-nT)&&(b<pT)&&(b>-nT)&&(c<pT)&&(c>-nT)&&(d-1<pT)&&(d-1>-nT))
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Flip270)) // a=0 b=1 c=1 d=0
            {
                if ((a<pT)&&(a>-nT)&&(b-1<pT)&&(b-1>-nT)&&(c-1<pT)&&(c-1>-nT)&&(d<pT)&&(d>-nT))
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Rotate0)) // a=1 b=0 c=0 d=1
            {
                if ((a-1<pT)&&(a-1>-nT)&&(b<pT)&&(b>-nT)&&(c<pT)&&(c>-nT)&&(d-1<pT)&&(d-1>-nT))
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Rotate90)) // a=0 b=1 c=-1 d=0
            {
                if ((a<pT)&&(a>-nT)&&(b-1<pT)&&(b-1>-nT)&&(c+1<pT)&&(c+1>-nT)&&(d<pT)&&(d>-nT))
                    return true;
                continue;
            } 
            else if (orientation.equals(EnumOrientation.Rotate180)) // a=-1 b=0 c=0 d=-1
            {
                if ((a+1<pT)&&(a+1>-nT)&&(b<pT)&&(b>-nT)&&(c<pT)&&(c>-nT)&&(d+1<pT)&&(d+1>-nT))
                    return true;
                continue;
            }
            else if (orientation.equals(EnumOrientation.Rotate270)) // a=0 b=-1 c=1 d=0
            {
                if ((a<pT)&&(a>-nT)&&(b+1<pT)&&(b+1>-nT)&&(c-1<pT)&&(c-1>-nT)&&(d<pT)&&(d>-nT))
                    return true;
                continue;
            }
            else
            {
                return true; 
            }
        }
        return false;
    }
}
    
    
    
