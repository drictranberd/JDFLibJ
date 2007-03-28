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
 * JDFDeviceCap.java
 *
 * @author Elena Skobchenko
 *
 */
package org.cip4.jdflib.resource.devicecapability;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoDeviceCap;
import org.cip4.jdflib.auto.JDFAutoDevCaps.EnumContext;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElemInfoTable;
import org.cip4.jdflib.core.ElementInfo;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFBaseDataTypes.EnumFitsValue;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage;
import org.cip4.jdflib.jmf.JDFMessageService;
import org.cip4.jdflib.jmf.JDFResponse;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumProcessUsage;
import org.cip4.jdflib.util.StringUtil;

public class JDFDeviceCap extends JDFAutoDeviceCap 
{
    private static final long serialVersionUID = 1L;
    private boolean ignoreExtensions=false;
    /**
     * Constructor for JDFDeviceCap
     * @param myOwnerDocument
     * @param qualifiedName
     */
    public JDFDeviceCap(CoreDocumentImpl myOwnerDocument,String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }

    /**
     * Constructor for JDFDeviceCap
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     */
    public JDFDeviceCap(
            CoreDocumentImpl myOwnerDocument,
            String myNamespaceURI,
            String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFDeviceCap
     * @param myOwnerDocument
     * @param myNamespaceURI
     * @param qualifiedName
     * @param myLocalName
     */
    public JDFDeviceCap(
            CoreDocumentImpl myOwnerDocument,
            String myNamespaceURI,
            String qualifiedName,
            String myLocalName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName, myLocalName);
    }
    private static ElemInfoTable[] elemInfoTable = new ElemInfoTable[13];
    static
    {
        elemInfoTable[0] = new ElemInfoTable(ElementName.BOOLEANSTATE, 0x33333111);
        elemInfoTable[1] = new ElemInfoTable(ElementName.DATETIMESTATE, 0x33333111);
        elemInfoTable[2] = new ElemInfoTable(ElementName.DURATIONSTATE, 0x33333111);
        elemInfoTable[3] = new ElemInfoTable(ElementName.ENUMERATIONSTATE, 0x33333111);
        elemInfoTable[4] = new ElemInfoTable(ElementName.INTEGERSTATE, 0x33333111);
        elemInfoTable[5] = new ElemInfoTable(ElementName.MATRIXSTATE, 0x33333111);
        elemInfoTable[6] = new ElemInfoTable(ElementName.NAMESTATE, 0x33333111);
        elemInfoTable[7] = new ElemInfoTable(ElementName.NUMBERSTATE, 0x33333111);
        elemInfoTable[8] = new ElemInfoTable(ElementName.PDFPATHSTATE, 0x33333111);
        elemInfoTable[9] = new ElemInfoTable(ElementName.RECTANGLESTATE, 0x33333111);
        elemInfoTable[10] = new ElemInfoTable(ElementName.SHAPESTATE, 0x33333111);
        elemInfoTable[11] = new ElemInfoTable(ElementName.STRINGSTATE, 0x33333111);
        elemInfoTable[12] = new ElemInfoTable(ElementName.XYPAIRSTATE, 0x33333111);
    }
    
    protected ElementInfo getTheElementInfo()
    {
        return super.getTheElementInfo().updateReplace(elemInfoTable);
    }
    
    //**************************************** Methods *********************************************
    /**
     * toString - StringRepresentation of JDFNode
     *
     * @return String
     */
    public String toString()
    {
        return "JDFDeviceCap[  --> " + super.toString() + " ]";
    }
    
    /**
     * Gets of this string attribute <code>TypeExpression</code> if it exists,
     * otherwise returns the literal string defined in Types
     *
     * @return String - TypeExpression attribute value
     */
    public String getTypeExpression()
    {
        if (hasAttribute(AttributeName.TYPEEXPRESSION)) 
        {
            return super.getTypeExpression();
        }
        return getAttribute(AttributeName.TYPES);
    }
    
    /**
     * (9.2) get CombinedMethod attribute <code>CombinedMethod</code>
     * @return Vector of the enumerations
     */
   public Vector getCombinedMethod()
   {
       Vector v=getEnumerationsAttribute(AttributeName.COMBINEDMETHOD, null, EnumCombinedMethod.None, false);
       if(v==null)
       {
           v=new Vector();
           v.add(EnumCombinedMethod.None);
       }
       return v;
   }

    
    /* ******************************************************
     // FitsValue Methods
      **************************************************************** */
    
    
    /**
     * Gets of jdfRoot a vector of all executable nodes  
     * (jdf root or children nodes that this Device may execute)
     *
     * @param jdfRoot   the node we test
     * @param testlists testlists that are specified for the State elements 
     *                  (FitsValue_Allowed or FitsValue_Present)<br>
     *                  Will be used in fitsValue method of the State class.
     * @param level     validation level
     * @return VElement - vector of executable JDFNodes, null if none found
     */
   public final VElement getExecutableJDF(final JDFNode jdfRoot, EnumFitsValue testlists, EnumValidationLevel level)
   {
       VElement execNodes = new VElement();
       EnumExecutionPolicy execPolicy = getExecutionPolicy();
      
       // here vNodes is jdfRoot + all children
       VElement vNodes = null;
       if(execPolicy.equals(EnumExecutionPolicy.RootNode))
       {
           vNodes=new VElement();
           vNodes.add(jdfRoot);
       }
       else
       {
           vNodes=jdfRoot.getvJDFNode(null, null,false);
       }
       XMLDoc d=new XMLDoc("dummy",null);
       for (int i=0; i<vNodes.size(); i++) 
       {
           JDFNode n = (JDFNode)vNodes.elementAt(i);
           final KElement root = d.getRoot();

           try 
           {
               KElement nOutput = report(n,testlists,level,root); // if report throws exception - n is non-executable Node
               if (nOutput == null)
               {
                   execNodes.addElement(n);
               }
           }
           catch (JDFException jdfe)
           {
               // nop
           } 
       }
       return execNodes.isEmpty() ? null : execNodes;
   }
    
    
   /**
    * Composes a BugReport in XML form for the given JDFNode 'jdfRoot'. 
    * Gives a list of error messages for 'jdfRoot' and every child rejected Node.<br> 
    * Returns <code>null</code> if there are no errors.
    *
    * @param jdfRoot   the node to test
    * @param testlists testlists that are specified for the State elements 
    *                  (FitsValue_Allowed or FitsValue_Present)<br>
    *                  Will be used in fitsValue method of the State class.
    * @param level     validation level
    * @return XMLDoc - XMLDoc output of the error messages. If XMLDoc is null there are no errors.
    */
   public final XMLDoc getBadJDFInfo(final JDFNode jdfRoot, final EnumFitsValue testlists, final EnumValidationLevel level)
   {
       XMLDoc bugReport = new XMLDoc("BugReport", null);
       KElement outputRoot = bugReport.getRoot();        
       VElement vNodes = jdfRoot.getvJDFNode(null, null, false);
       
       final int size = vNodes.size();
       for (int i=0; i < size; i++) 
       {
           JDFNode n = (JDFNode)vNodes.elementAt(i);
           KElement report=null;
           try
           {
               report = report(n, testlists, level,outputRoot);
           }
           catch (JDFException jdfe)
           {
               report = outputRoot.appendElement("RejectedNode");
               report.setAttribute("CaughtException", jdfe.getMessage());
               report.setAttribute("ID", n.getID());
               report.setAttribute("XPath", n.buildXPath(null));
           }
       }
       
       if (!outputRoot.hasChildNodes())
           bugReport = null;
       
       return bugReport;
   }
   /**
    * Composes a BugReport in XML form for the given JDFNode 'jdfRoot'. 
    * Gives a list of error messages for 'jdfRoot' and every child rejected Node.<br> 
    * Returns <code>null</code> if there are no errors.
    *
    * @param jdfRoot   the node to test
    * @param testlists testlists that are specified for the State elements 
    *                  (FitsValue_Allowed or FitsValue_Present)<br>
    *                  Will be used in fitsValue method of the State class.
    * @param level     validation level
    * @return XMLDoc - XMLDoc output of the error messages. If XMLDoc is null there are no errors.
    */
   static public XMLDoc getJMFInfo(final JDFJMF jmfRoot, final JDFResponse knownMessagesResp, final EnumFitsValue testlists, final EnumValidationLevel level,boolean ignoreExtensions)
   {
       XMLDoc bugReport = new XMLDoc("JMFReport", null);
       KElement parentRoot = bugReport.getRoot();        

       int nBad=0;
       if (!jmfRoot.isValid(level)) 
       {
           parentRoot.setAttribute("IsValid", false, null);
       }
       VElement messages=jmfRoot.getMessageVector(null,null);

       for(int i=0;i<messages.size();i++)
       {
           KElement messageReport=parentRoot.appendElement("InvalidMessage");
           JDFMessage m=(JDFMessage) messages.elementAt(i);
           String typeJMF = m.getType();
           messageReport.setAttribute("MessageType",typeJMF);
           messageReport.setAttribute("XPath", m.buildXPath(null));
           messageReport.setAttribute("ID", m.getID());
           JDFMessageService ms=getMessageServiceForJMFType(m,knownMessagesResp);
           if(ms!=null)
           {
               messageReport.setAttribute("FitsType", true, null);
               invalidDevCaps(ms,m, testlists, level, parentRoot,ignoreExtensions);
           }
           else
           {
               messageReport.setAttribute("FitsType", false, null);
//             TODO           root.setAttribute("CapsType",typeExp);
               messageReport.setAttribute("Message","JMF  Type: "+typeJMF+" does not match capabilities type: ");
           }


           if (!messageReport.hasChildElements() && messageReport.getBoolAttribute("FitsType",null,true))
           {
               messageReport.renameElement("ValidMessage", null);
           }
           else
           {
               nBad++;
           }
       }
       if(nBad==0)
           parentRoot.setAttribute("IsValid", "true");
        return bugReport;
   }



   /**
 * @param m
 * @param knownMessagesResp
 * @return
 */
public static JDFMessageService getMessageServiceForJMFType(JDFMessage m, JDFResponse knownMessagesResp)
{
   if(knownMessagesResp==null || !knownMessagesResp.getType().equals("KnownMessages") || m==null || m.getType().equals("") )
       return null;
   JDFMessageService ms=(JDFMessageService)knownMessagesResp.getChildWithAttribute(ElementName.MESSAGESERVICE, AttributeName.TYPE, null, m.getType(), 0, true);
   return ms;
  
}

/**
     * Checks if Device can execute the given JDFNode 'jdfRoot'.<br>
     * First validates 'jdfRoot' and checks if its Type/Types attributes  
     * fit the values of DeviceCap/@Types and DeviceCap/@CombinedMethod.
     * If Node is invalid or Type/Types don't fit it doesn't check it more detailed.<br>
     * If Type/Types fit, the whole JDFNode - all elements and attributes - will 
     * be tested iot check if a Device can accept it.<br>
     * This method composes a detailed report of the found errors in XML form, if jdfRoot is rejected.<br>
     * If XMLDoc is null, there are no errors and 'jdfRoot' is accepted
     * 
     * @param jdfRoot   the node to test
     * @param fitsValue testlists that are specified for the State elements 
     *                  (FitsValue_Allowed or FitsValue_Present)<br>
     *                  Will be used in fitsValue method of the State class.
     * @param level     validation level
     * @return XMLDoc - XMLDoc output of the error messages.
     * If XMLDoc is <code>null</code> there are no errors, 'jdfRoot' is accepted
     * 
     * @throws JDFException if DeviceCapabilities file is invalid: illegal value of Types(TypeExpression) attribute
     * (if CombinedMethod is None and Types contains more than 1 process)
     * @throws JDFException if DeviceCapabilities file is invalid: illegal value of CombinedMethod attribute
     */
    private final KElement report(final JDFNode jdfRoot, EnumFitsValue fitsValue, EnumValidationLevel level, KElement parentRoot)
    {
        KElement root = parentRoot.appendElement("RejectedNode");
        root.setAttribute("XPath", jdfRoot.buildXPath(null));
        root.setAttribute("ID", jdfRoot.getID());
        String typeExp = getTypeExpression();
       
        if (!jdfRoot.isValid(level)) 
        {
            root.setAttribute("IsValid", false, null);
        }
        if(!matchesType(jdfRoot,false))
        {
            String typeNode = jdfRoot.getType();
            reportTypeMatch(root,false,typeNode,typeExp);
            return root;            
        }

        root = groupReport(jdfRoot, fitsValue, level,root);
        //TODO ???
        if (!root.hasChildElements() && root.getBoolAttribute("FitsType",null,true))
        {
            root.deleteNode();
            root= null;
        }
        return root;
    }
    
//////////////////////////////////////////////////////////////////////////    
    
    /**
     * test whether a given node has the corect Types and Type Attribute
     * 
     * @param testRoot the JDF or JMF to test
     * @param bLocal   if true, only check the root of this, else check children as well
     * 
     * @return boolean - true if this DeviceCaps TypeExpression fits testRoot/@Type and testRoot/@Types
     * 
     */
    public boolean matchesType(JDFNode testRoot, boolean bLocal)
    {
        VElement v=getMatchingTypeNodeVector(testRoot);
        if(v==null)
            return false;
        if(bLocal)
            return v.contains(testRoot);
        return true;        
    }
    
    /**
     * test whether a given node has the corect Types and Type Attribute
     * 
     * @param testRoot the JDF or JMF to test
     * 
     * @return VElement - the list of matching JDF nodes, null if none found
     * 
     */
    public VElement getMatchingTypeNodeVector(JDFNode testRoot)
    {
        VElement v=new VElement();
        String typeNode = testRoot.getType();

        Vector vCombMethod = getCombinedMethod();
        final String typeExp = getTypeExpression();
        for(int j=0;j<vCombMethod.size();j++)
        {
            EnumCombinedMethod combMethod = (EnumCombinedMethod)vCombMethod.elementAt(j);

            if (combMethod.equals(EnumCombinedMethod.None))  // node is an individual process
            {            
                if (StringUtil.matches(typeNode,typeExp))
                {
                    v.add(testRoot);
                }
            }
            else if (combMethod.equals(EnumCombinedMethod.Combined) ||
                    combMethod.equals(EnumCombinedMethod.CombinedProcessGroup)&&typeNode.equals("Combined"))
            {
                if (fitsTypes(testRoot.getAllTypes(),false))
                {
                    v.add(testRoot);
                }
            }
            else if (combMethod.equals(EnumCombinedMethod.GrayBox)|| 
                    combMethod.equals(EnumCombinedMethod.CombinedProcessGroup)&&typeNode.equals("ProcessGroup")&&!testRoot.isGroupNode())
            {
                if (fitsTypes(testRoot.getAllTypes(),true))
                {
                    v.add(testRoot);
                }
            }
            else if (combMethod.equals(EnumCombinedMethod.ProcessGroup)||
                    combMethod.equals(EnumCombinedMethod.CombinedProcessGroup)&&typeNode.equals("ProcessGroup"))           
            {
                VElement vNodes=testRoot.getvJDFNode(null,null,false);
                final int size = vNodes.size();
                for(int i=0;i<size-1;i++) // note the 1 which skips this
                {
                    JDFNode node=(JDFNode) vNodes.elementAt(i);
                    if(node.isGroupNode())
                    {
                        final VElement matchingTypeNodeVector = getMatchingTypeNodeVector(node);
                        if(matchingTypeNodeVector!=null)
                            v.addAll(matchingTypeNodeVector);
                    }
                    else if (fitsTypes(node.getAllTypes(),true))
                    {
                        v.add(node);
                    }
                } 
                if(v.size()>0)
                    v.add(testRoot);
            }
            else 
            {
                throw new JDFException ("JDFDeviceCap.report: Invalid DeviceCap: illegal value of CombinedMethod attribute"); 
            }
        }
        v.unify();
        return v.size()==0 ? null : v;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static void reportTypeMatch(KElement report, boolean matches, String typeNode, String typeExp)
    {
        report.setAttribute("FitsType", matches, null);
        report.setAttribute("NodeType",typeNode);
        report.setAttribute("CapsType",typeExp);
        if(!matches)
            report.setAttribute("Message","Node Type: "+typeNode+" does not match capabilities type: "+typeExp);
     }


    /**
     * Tests JDFNode/@Types (or its equivalent of Types in the ProcessGroupNodes - 
     * the concatenated string of all Type attributes in the children Nodes) 
     * iot check whether it matches DeviceCap/@Types or DeviceCap/@TypeExpression  
     *
     * @param typesNode attribute Types of the tested JDFNode
     * @param bSubset if true, a match is sufficient if a subset is specified
     * @return boolean - true if JDFNode/@Types fits DeviceCap/@Types or DeviceCap/@TypeExpression
     * @throws JDFException if DeviceCap is invalid: both @Types and @TypeExpression are missing
     */
    private final boolean fitsTypes(VString typesNode, boolean bSubset)
    {
        if(typesNode==null || typesNode.isEmpty())
            return false;
        if(!bSubset)
        {
            if (hasAttribute(AttributeName.TYPEEXPRESSION)) 
            {
                final String typeExp = getTypeExpression();
                final String typesNodeStr = StringUtil.setvString(typesNode,JDFConstants.BLANK,null,null);
                return StringUtil.matches(typesNodeStr,typeExp);
            }
            return typesNode.equals(getTypes());
        }

        final VString dcTypes = getTypes();
        for (int i=0; i < typesNode.size(); i++) 
        {
            final String type = typesNode.stringAt(i);                
            if (!dcTypes.contains(type)) 
                return false;
        }
        return true;
    }


    /**
     * Checks whether a device can execute the given ProcessGroup JDFNode 'jdfRoot' (JDFNode/@Type=ProcessGroup).
     * If JDFNode/@Types fits DeviceCap/@Types, the whole JDFNode - all elements and attributes - is tested
     * iot check whether a device can accept it.<br>
     * Composes a detailed report of the found errors in XML form, if JDFNode is rejected. 
     *
     * @param jdfRoot   the node to test
     * @param testlists testlists that are specified for the State elements 
     *                  (FitsValue_Allowed or FitsValue_Present)<br>
     *                  Will be used in fitsValue method of the State class.
     * @param level     validation level
     * 
     * @return XMLDoc - XMLDoc output of the error messages. <br>
     * If XMLDoc is <code>null</code> there are no errors, 'jdfRoot' is accepted 
     */
    private final KElement groupReport(final JDFNode jdfRoot, EnumFitsValue testlists, EnumValidationLevel level, KElement parentRoot)
    {
        parentRoot.setAttribute("XPath", jdfRoot.buildXPath(null));
        parentRoot.setAttribute("ID", jdfRoot.getID());
                
        VElement vNodes= getMatchingTypeNodeVector(jdfRoot);
        if (vNodes==null)  
        {
            parentRoot.setAttribute("FitsType", false, null);
        }
        else 
        {
            parentRoot.setAttribute("FitsType", true, null);
            
            // check the status of all child nodes
            for (int i=0; i < vNodes.size()-1; i++) 
            {
                JDFNode n = (JDFNode)vNodes.elementAt(i);
                KElement childRoot = devCapsReport(n, testlists, level,parentRoot);
                if (childRoot != null) 
                {
                    childRoot.setAttribute("XPath", n.buildXPath(null));
                    childRoot.setAttribute("ID", n.getID());
                }
            }
            devCapsReport(jdfRoot,testlists,level,parentRoot);
        }
        return parentRoot;        
    }
    
    
    
    /**
     * devCapsReport - searches in JDFNode for appropriate elements for every DevCaps element 
     * that DeviceCap consists of, and tests them.<br>
     * Composes a detailed report of the found errors in XML form.<br> 
     * If XMLDoc is <code>null</code> there are no errors
     * 
     * @param jdfRoot   the node we test
     * @param testlists testlists that are specified for the State elements 
     *                  (FitsValue_Allowed or FitsValue_Present)<br>
     *                  Will be used in fitsValue method of the State class.
     * @param level     validation level
     * @return XMLDoc - XMLDoc output of the error messages. 
     *                  If XMLDoc is <code>null</code> there are no errors, 'jdfRoot' is accepted 
     */
    private final KElement devCapsReport(final JDFNode jdfRoot, EnumFitsValue testlists, EnumValidationLevel level, KElement parentRoot)
    {        
        // first test if there are in the JDFNode any ResourceLink or NodeInfo/CustomerInfo 
        // that are not described by DevCaps
        KElement root = parentRoot.appendElement("RejectedChildNode");

        if(!ignoreExtensions)
            noFoundDevCaps(jdfRoot,root);
            
        // if all resourceLinks and NodeInfo/CustomerInfo elements (optional) 
        // are specified as DevCaps, we may test them. 
        invalidDevCaps(this,jdfRoot, testlists, level, root,ignoreExtensions);
        actionPoolReport(jdfRoot,root);
        if(!root.hasChildElements())
        {
            root.deleteNode();
            root=null;
        }
        return root;
    }
    
    
    
    
    /**
     * invalidDevCaps - tests if there are any invalid or missing Resources 
     * or NodeInfo/CustomerInfo elements in the JDFNode.<br> 
     * Composes a detailed report of the found errors in XML form. 
     * If XMLDoc is <code>null</code> there are no errors.
     * 
     * @param jdfRoot node we test
     * @return boolean - true if invalid devcaps were found 
     * @throws JDFException if DeviceCap is invalid: has a wrong attribute Context value 
     */
    private static boolean invalidDevCaps(KElement parent, final KElement jdfRoot, EnumFitsValue testlists, EnumValidationLevel level, KElement parentReport, boolean ignoreExtensions)
    {
        KElement mrp = parentReport.appendElement("MissingResources");
        KElement irp = parentReport.appendElement("InvalidResources");
        KElement badElem=null;
        if(parent instanceof JDFDeviceCap)
        {
            badElem = ((JDFNode)jdfRoot).getResourceLinkPool();
        } 
        else if(parent instanceof JDFMessageService)
        {
            badElem=jdfRoot;
        }
        else
        {
            throw new JDFException("illegal arguments in invaliddevcaps");
        }
        VElement vDevCaps = parent.getChildElementVector(ElementName.DEVCAPS, null, null, true, 0, false);
        final int size = vDevCaps.size();
        HashSet goodElems=new HashSet();
        HashMap badElems=new HashMap();
        
        for (int i=0; i < size; i++) 
        {
            JDFDevCaps devCaps = (JDFDevCaps) vDevCaps.elementAt(i);
            badElem = devCaps.analyzeDevCaps(jdfRoot, testlists, level, mrp, irp, badElem, goodElems, badElems, ignoreExtensions);
        }
        
 
        boolean bRet=mrp.hasChildElements() || irp.hasChildElements();
        if (!mrp.hasChildElements())
            mrp.deleteNode();
        
        if (!irp.hasChildElements())
            irp.deleteNode();
        
        return bRet;
    }

    /**
     * missingDevCaps - tests if there are any Resources or NodeInfo/CustomerInfo elements 
     * in the JDFNode, which are not described by DevCaps.<br>
     * If missing DevCaps are found, jdfRoot has elements unknown for this Device resources or elements.<br>
     * Composes a detailed report of the found errors in XML form. If XMLDoc is <code>null</code>  there are no errors.
     * 
     * @param jdfRoot node to test
     * @return XMLDoc - XMLDoc output of the error messages. 
     *         If XMLDoc is <code>null</code> there are no errors 
     */
    private final KElement noFoundDevCaps(final JDFNode jdfRoot, KElement parentReport)
    {
        KElement root = parentReport.appendElement("UnknownResources");
        VElement vLinks = jdfRoot.getResourceLinks(null);
        
        final int linkSize = vLinks==null ? 0 : vLinks.size() ;
        for (int j=0; j < linkSize; j++) 
        {
            JDFResourceLink link = (JDFResourceLink) vLinks.elementAt(j);
            final String resName = link.getLinkedResourceName();
            final String processUsage=link.getProcessUsage();

            JDFAttributeMap map = new JDFAttributeMap(AttributeName.NAME,resName);
            VElement vDevCaps = getChildElementVector(ElementName.DEVCAPS,
                    null, map, true, 0, false);

            boolean bFound=false;
            final int size = vDevCaps.size();
            for (int k=0; k < size && !bFound; k++) 
            {
                JDFDevCaps dc = (JDFDevCaps) vDevCaps.elementAt(k);
                if ((!dc.hasAttribute(AttributeName.LINKUSAGE)||
                        dc.getLinkUsage().getName().equals(link.getUsage().getName()))
                        &&(dc.getProcessUsage().equals(processUsage)))
                {
                    bFound=true;
                }
            }
            if (!bFound) 
            { // no DevCaps with Name=resName and the corresponding LinkUsage were found
                KElement r = root.appendElement("UnknownResource");
                r.setAttribute("XPath", link.buildXPath(null));
                r.setAttribute("Name", resName);
                if (link.hasAttribute(AttributeName.USAGE, null, false) 
                        && !link.getUsage().getName().equals("Unknown"))
                {
                    r.setAttribute("Usage", link.getUsage().getName());
                }
                r.setAttribute("Message", "Found no DevCaps description for this resource");
            }
        }

        checkNodeInfoCustomerInfo(jdfRoot, root, ElementName.NODEINFO);
        checkNodeInfoCustomerInfo(jdfRoot, root, ElementName.CUSTOMERINFO);
        checkNodeInfoCustomerInfo(jdfRoot, root, ElementName.STATUSPOOL);
//      checkNodeInfoCustomerInfo(jdfRoot, root, ElementName.AUDITPOOL);

        if (!root.hasChildElements())
        {
            root.deleteNode();
            root= null;
        }
        
        return root;
    }
    
    /**
     * checkNodeInfoCustomerInfo - tests if there are JDFNode/NodeInfo or JDFNode/CustomerInfo 
     * elements that are not described by DevCaps.
     * If missing DevCaps are found, jdfRoot has elements unknown for this Device resources or elements
     * 
     * @param jdfRoot      node to test
     * @param root         root of the XMLDoc output
     * @param elementName "NodeInfo" or "CustomerInfo" or "StatusPool"
     */
    private final void checkNodeInfoCustomerInfo(final JDFNode jdfRoot, KElement root, String elementName)
    {
        JDFAttributeMap map=new JDFAttributeMap();
        map.put(AttributeName.CONTEXT,EnumContext.Element.getName());
        map.put(AttributeName.NAME,elementName);
        final KElement devCaps = getChildByTagName(ElementName.DEVCAPS,null,0,map,true, true);
        if ((jdfRoot.getElement(elementName, null, 0) != null) && 
            (devCaps  == null)) 
        {
            KElement ue = root.appendElement("UnknownElement");
            ue.setAttribute("XPath", jdfRoot.getElement(elementName, null, 0).buildXPath(null,true));
            ue.setAttribute("Name", elementName);
            ue.setAttribute("Message", "Found no DevCaps description with Context=\"Element\" for: "+elementName);
        }
        return;
    } 
    
    
    
    /**
     * actionPoolReport - tests if the JDFNode fits Actions from ActionPool of this DeviceCap.<br>
     * Composes a detailed report of the found errors in XML form. If XMLDoc is <code>null</code> - there are no errors 
     *
     * @param jdfRoot node to test
     * @return KElement - KElement output of the error messages. 
     *         If KElement is <code>null</code> there are no errors, 
     *         JDFNode fits the ActionPool of this DeviceCap and will be accepted by the device.
     * @throws JDFException if DeviceCap is invalid: ActionPool refers to the non-existent TestPool
     * @throws JDFException if DeviceCap is invalid: Action refers to the non-existent Test
     */
    public final KElement actionPoolReport(final JDFNode jdfRoot, KElement parentReport)
    {
        KElement root = parentReport.appendElement("ActionPoolReport");    
        JDFActionPool actionPool = getActionPool();
        if (actionPool != null) 
        {
            JDFTestPool testPool = getTestPool();
            if (testPool == null) 
            {
                throw new JDFException("JDFDeviceCap.actionPoolReport: TestPool is required but was not found. Attempt to operate on a null element");
            }
            VElement vActions = actionPool.getChildElementVector(ElementName.ACTION, null, null, true, 0, false);
            VElement allElms=jdfRoot.getChildrenByTagName(null,null,null,false,true,0);
            allElms.add(jdfRoot); // needed for local JDF test
            final int elmSize = allElms.size();
            final int actionSize = vActions.size();
            for(int i=0;i<elmSize;i++)
            {
                KElement e=allElms.item(i);
                for (int j=0; j < actionSize; j++) 
                {
                    JDFAction action = (JDFAction) vActions.elementAt(j); 
                    JDFTest test = action.getTest();
                    if (test==null) 
                    {
                        continue;
                        // TODO add report of snafu
//                      throw new JDFException("JDFDeviceCap.actionPoolReport: Test with ID=" + action.getTestRef() + " was not found. Attempt to operate on a null element");
                    }
                    // loop to check whether the test even applies
                    if(!test.fitsContext(e))
                        continue;
                    
                    KElement ar = root.appendElement("ActionReport");
                    if (test.fitsJDF(e,ar)) // If the Test referenced by TestRef evaluates to �true� the combination 
                    {                           // of processes and attribute values described is not allowed
                        KElement arl = root.getChildWithAttribute("ActionReportList","ID",null,action.getID(),0,true);
                        if(arl==null)
                        {
                            arl=root.appendElement("ActionReportList");
                            arl.setAttribute("ID", action.getID());
                            arl.setAttribute("Severity", action.getSeverity().getName());                            
                        }
                        
                        arl.moveElement(ar,null);
                        ar.setAttribute("XPath",e.buildXPath(null,true));
                        
                        // __Lena__ TBD choose Loc element according to the language settings
                        final JDFLoc loc = action.getLoc(0);
                        if(loc!=null)
                        {
                            ar.setAttribute("Message", loc.getValue());                       
                            String helpText = loc.getHelpText();
                            if (helpText.length()!=0) 
                            {
                                ar.setAttribute("Help", helpText);
                            }
                        }                   
                    }
                    else
                    {
                        ar.deleteNode(); // zapp it
                    }
                }
            }
        }
        root=cleanActionPoolReport(root);        
        return root;
    }
    
    /**
     * remove duplicate entries that are parents of lower level entries
     * @param testResult XMLDoc to clean
     * @return XMLDoc - the cleaned doc
     */ 
    private KElement cleanActionPoolReport(KElement actionPoolReport)
    {
        if (actionPoolReport != null)
        {
            VElement vARL = actionPoolReport.getChildElementVector("ActionReportList", null, null, true, 0, false);
            for (int i = 0; i < vARL.size(); i++)
            {
                VElement actionReportList = vARL.item(i).getChildElementVector("ActionReport", null, null, true, 0, false);
                for (int j = 1; j < actionReportList.size(); j++)
                {
                    KElement e1 = actionReportList.item(j);
                    for (int k = 0; k < j; k++)
                    {
                        KElement e2 = actionReportList.item(k);
                        if (e2 == null)
                            continue;
                        if (e2.getAttribute("XPath").startsWith(e1.getAttribute("XPath")))
                        {
                            e1.deleteNode();
                            actionReportList.setElementAt(null, j);
                            break;
                        }
                        else if (e1.getAttribute("XPath").startsWith(e2.getAttribute("XPath")))
                        {
                            e2.deleteNode();
                            actionReportList.setElementAt(null, k);
                        }
                    }
                }
            }

            if (!actionPoolReport.hasChildElements())
            {
                actionPoolReport.deleteNode();
                actionPoolReport=null;
            }
        }
        return actionPoolReport;
    }

   ////////////////////////////////////////////////////
    
    /**
     * set the defaults of node to the values defined in the child DevCap and State elements
     * @param node   the JDFNode in which to set defaults
     * @param bLocal if true, set only in the local node, else recurse children
     */
    public boolean setDefaultsFromCaps(JDFNode node, boolean bLocal)
    {
        boolean success=false;
        if(bLocal==false)
        {
            VElement vNode=node.getvJDFNode(null,null,false);
            for(int i=0;i<vNode.size();i++)
            {
                JDFNode nod=(JDFNode)vNode.elementAt(i);
                success = setDefaultsFromCaps(nod,true) || success;
            }
            return success;
        }
        if(!matchesType(node,true))
            return false;
        addResourcesFromDevCaps(node);
        int i;
        VElement vDevCaps=getChildElementVector(ElementName.DEVCAPS,null,null,true,99999,false);
//      step 1, create all missing resources etc
        final int size = vDevCaps.size();
        for(i=0;i<size;i++)
        {
            JDFDevCaps dcs=(JDFDevCaps)vDevCaps.elementAt(i);
            success =  dcs.setDefaultsFromCaps(node) || success;
        }

        return success;
   }


    /**
     * add any missing resources, links or elements that are described by devcaps elements
     * @param node
     */
    private void addResourcesFromDevCaps(JDFNode node)
    {
        VElement vDevCaps=getChildElementVector(ElementName.DEVCAPS,null,null,true,99999,false);
// step 1, create all missing resources etc
        final int size = vDevCaps.size();
        for(int i=0;i<size;i++)
        {
            JDFDevCaps dcs=(JDFDevCaps)vDevCaps.elementAt(i);
            dcs.appendMatchingElementsToNode(node);
        }
    }


    /**
     * get a DevCaps element by name and further restrictions.
     * If an Enumerative restriction is null, the restriction is not checked.
     * 
     * @param devCapsName  the Name attribute of the DevCaps
     * @param context      the Context attribute of the DevCaps
     * @param linkUsage    the LinkUsage attribute of the DevCaps
     * @param processUsage the ProcessUsage attribute of the DevCaps
     * @param iSkip        the iSkip'th matching DevCaps
     * @return JDFDevCaps the matching DevCaps, null if not there
     */
    public JDFDevCaps getDevCapsByName(String devCapsName, EnumContext context, EnumUsage linkUsage, EnumProcessUsage processUsage, int iSkip)
    {
        JDFAttributeMap map=new JDFAttributeMap(AttributeName.NAME,devCapsName);
        if(context!=null)
            map.put(AttributeName.CONTEXT,context.getName());
        if(linkUsage!=null)
            map.put(AttributeName.LINKUSAGE,linkUsage.getName());
        if(processUsage!=null)
            map.put(AttributeName.PROCESSUSAGE,processUsage.getName());
        return (JDFDevCaps)getChildByTagName(ElementName.DEVCAPS,null,iSkip,map,true,true);
    }
    
    /**
     * set attribute <code>CombinedMethod</code> to an individual method
     * 
     * @param method the individual combined method to set
     */
    public void setCombinedMethod(EnumCombinedMethod method)
    {
        setAttribute(AttributeName.COMBINEDMETHOD, method.getName(), null);
    }

    /**
     * set attribute <code>CombinedMethod</code> to an individual method
     * 
     * @param method the individual combined method to set
     */
    public void setCombinedMethod(Vector vMethod)
    {
        setEnumerationsAttribute(AttributeName.COMBINEDMETHOD, vMethod, null);
    }

    /**
     * @return the ignoreExtensions
     */
    public boolean isIgnoreExtensions()
    {
        return ignoreExtensions;
    }


    /**
     * @param ignoreExtensions the ignoreExtensions to set
     */
    public void setIgnoreExtensions(boolean _ignoreExtensions)
    {
        this.ignoreExtensions = _ignoreExtensions;
    }
    
}
