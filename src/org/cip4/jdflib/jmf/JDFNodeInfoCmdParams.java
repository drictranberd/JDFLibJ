/**
 *
 * Copyright (c) 2001 Heidelberger Druckmaschinen AG, All Rights Reserved.
 *
 * JDFNodeInfoCmdParams.java
 *
 * Last changes
 *
 * 2002-07-02 JG - init() Also call super::init()
 *
 */
package org.cip4.jdflib.jmf;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cip4.jdflib.auto.JDFAutoNodeInfoCmdParams;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;

/**
 *
 */
public class JDFNodeInfoCmdParams extends JDFAutoNodeInfoCmdParams
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for JDFNodeInfoCmdParams
     * @param ownerDocument
     * @param qualifiedName
     */
     public JDFNodeInfoCmdParams(
        CoreDocumentImpl myOwnerDocument,
        String qualifiedName)
    {
        super(myOwnerDocument, qualifiedName);
    }


    /**
     * Constructor for JDFNodeInfoCmdParams
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     */
    public JDFNodeInfoCmdParams(
        CoreDocumentImpl myOwnerDocument,
        String myNamespaceURI,
        String qualifiedName)
    {
        super(myOwnerDocument, myNamespaceURI, qualifiedName);
    }

    /**
     * Constructor for JDFNodeInfoCmdParams
     * @param ownerDocument
     * @param namespaceURI
     * @param qualifiedName
     * @param localName
     */
    public JDFNodeInfoCmdParams(
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
     *
     * @return String
     */
    public String toString()
    {
        return "JDFNodeInfoCmdParams[  --> " + super.toString() + " ]";
    }
    
   
   /**
    * get part map vector
    * @return VJDFAttributeMap: vector of attribute maps, one for each part
    */
   public VJDFAttributeMap getPartMapVector()
   {
       return super.getPartMapVector();
   }

   /**
    * set all parts to those define in vParts
    * @param VJDFAttributeMap vParts: vector of attribute maps for the parts
    */
   public void setPartMapVector(VJDFAttributeMap vParts)
   {
       super.setPartMapVector(vParts);
   }

   /**
    * set all parts to those define in vParts
    * @param JDFAttributeMap mPart: attribute map for the part to set
    */
   public void setPartMap(JDFAttributeMap mPart)
   {
       super.setPartMap(mPart);
   }

   /**
    * remove the part defined in mPart
    * @param JDFAttributeMap mPart: attribute map for the part to remove
    */
   public void removePartMap(JDFAttributeMap mPart)
   {
       super.removePartMap(mPart);
   }

   /**
    * check whether the part defined in mPart is included
    * @param JDFAttributeMap mPart: attribute map for the part to remove
    * @return boolean - returns true if the part exists
    */
   public boolean hasPartMap(JDFAttributeMap mPart)
   {
       return super.hasPartMap(mPart);
   }
}
