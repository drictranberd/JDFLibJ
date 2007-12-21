/*
 *
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
 * KElementTest.java
 * 
 * @author Dietrich Mucha
 *
 * Copyright (C) 2002 Heidelberger Druckmaschinen AG. All Rights Reserved.
 */
package org.cip4.jdflib.core;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoPart.EnumSide;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement.EnumValidationLevel;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.resource.process.JDFExposedMedia;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.StringUtil;
import org.w3c.dom.Element;

public class KElementTest extends JDFTestCaseBase
{

    public void testEnumValid()
    {
        EnumValidationLevel level=EnumValidationLevel.RecursiveComplete;
        assertEquals(EnumValidationLevel.NoWarnComplete, EnumValidationLevel.setNoWarning(level, true));
        assertEquals(EnumValidationLevel.RecursiveComplete, EnumValidationLevel.setNoWarning(level, false));
        level=EnumValidationLevel.RecursiveIncomplete;
        assertEquals(EnumValidationLevel.NoWarnIncomplete, EnumValidationLevel.setNoWarning(level, true));
        assertEquals(EnumValidationLevel.RecursiveIncomplete, EnumValidationLevel.setNoWarning(level, false));
    }
    /*
     * Test for void RemoveAttribute(String, String) - PR-AKMP-000001
     */
    public void testRemoveAttributeStringString()
    {
        JDFParser p = new JDFParser();
        JDFDoc jdfDoc = p.parseFile(sm_dirTestData+"emptyAuthorAttribute.jdf");

        JDFNode root   = (JDFNode) jdfDoc.getRoot();
        KElement kElem = root.getChildByTagName("Created", null, 0, null, false, true);

        boolean before = kElem.hasAttribute("Author", null, false);
        assertTrue("The Attribute 'Author' does not exist", before);

        if (before) 
        {
            kElem.removeAttribute("Author", "");
            boolean after = kElem.hasAttribute("Author", "", false);

            assertFalse("The Attribute 'Author' was not removed", after);
        }
    }

    ///////////////////////////////////////////////////////////

    public void testRenameElement()
    {
        XMLDoc d = new XMLDoc("root","www.root.com");
        KElement root=d.getRoot();
        String nsUri=root.getNamespaceURI();
        root.renameElement("foo",null);
        assertEquals(nsUri,root.getNamespaceURI());
        assertEquals(root.getNodeName(),"foo");
        root.renameElement("bar","www.bar.com");
        assertEquals("www.bar.com",root.getNamespaceURI());
        String s=d.write2String(2); 
        assertTrue(s.indexOf("www.root.com")<0);
        assertTrue(s.indexOf("www.bar.com")>0);
    }

    ///////////////////////////////////////////////////////////

    public void testGetElementsWithMultipleID()
    {
        XMLDoc d=new XMLDoc("e1",null);
        KElement e1=d.getRoot();
        assertNull(e1.getMultipleIDs("ID"));
        e1.setXPathAttribute("./e2[2]/e3/@ID", "i1");
        e1.setXPathAttribute("./e2[3]/e3/@ID", "i2");
        assertNull(e1.getMultipleIDs("ID"));
        e1.setXPathAttribute("./e2[4]/e3/@ID", "i1");
        assertEquals(e1.getMultipleIDs("ID").stringAt(0),"i1");
        assertEquals(e1.getMultipleIDs("ID").size(),1);
        e1.setAttribute("ID", "i2");
        assertEquals(e1.getMultipleIDs("ID").size(),2);
        assertTrue(e1.getMultipleIDs("ID").contains("i1"));
        assertTrue(e1.getMultipleIDs("ID").contains("i2"));

    }
///////////////////////////////////////////////////////////

    public void testGetElementById()
    {
        String xmlString =   "<JDF ID=\"Link20704459_000351\">" +
        "<ELEM2 ID=\"Link20704459_000352\">" + 
        "<ELEM3 ID=\"Link20704459_000353\">" +
        "<Comment/>" +
        "</ELEM3>" + 
        "</ELEM2>" +
        "</JDF>";

        for(int i=0;i<2;i++)
        {
            final JDFParser parser = new JDFParser();
            if(i==1) // test both with and withot schema
                parser.m_SchemaLocation = new File(sm_dirTestSchema + "JDF.xsd").toURI().toString();
            JDFDoc jdfDoc = parser.parseString(xmlString);
            KElement root = jdfDoc.getRoot();

            KElement kElem, kElem2;

            // alt funktioniert
            kElem2 = root.getTarget("Link20704459_000351", AttributeName.ID);
            assertNotNull(kElem2);

            // neu funktioniert nicht - http://mail-archives.apache.org/mod_mbox/xerces-j-users/200410.mbox/%3c4178694B.40708@metalab.unc.edu%3e
            // http://www.stylusstudio.com/xmldev/200012/post80000.html
            kElem = (KElement) jdfDoc.getElementById("Link20704459_000351");
            assertNotNull(kElem);

            // alt funktioniert
            kElem2 = root.getTarget("Link20704459_000352", AttributeName.ID);
            assertNotNull(kElem2);

            // neu funktioniert nicht
            kElem = (KElement) jdfDoc.getElementById("Link20704459_000352");
            assertNotNull(kElem);

            // alt funktioniert
            kElem2 = root.getTarget("Link20704459_000353", AttributeName.ID);
            assertNotNull(kElem2);

            // neu funktioniert nicht
            kElem = (KElement) jdfDoc.getElementById("Link20704459_000353");
            assertNotNull(kElem);
        }
    }

    public void testReplaceElementRoot()
    {
        XMLDoc d = new XMLDoc("root","www.root.com");
        XMLDoc d2 = new XMLDoc("root2","www.root2.com");
        KElement e=d.getRoot();
        e.appendElement("c1");

        KElement e2=d2.getRoot();
        e2.replaceElement(e);
        assertEquals("copied root",d2.getRoot(),e2);
        assertTrue("same contents",e2.isEqualNode(e));
    }

    public void testReplaceElement()
    {
        XMLDoc d = new XMLDoc("root","www.root.com");
        XMLDoc d2 = new XMLDoc("root2","www.root2.com");
        KElement e=d.getRoot();
        KElement ec1=e.appendElement("c1");
        KElement ec2=e.appendElement("c2");
        ec2.setAttribute("foo", "ec2");
        KElement ec4=e.appendElement("c4");

        KElement ec3=ec1.replaceElement(ec2);
        assertEquals("c1=c2",ec3,ec2);
        assertEquals("c4 is next",ec3,ec4.getPreviousSibling());
        assertEquals("c4 is next",ec3.getNextSibling(),ec4);
        assertNull("no sibling",ec1.getNextSibling());
        assertNull("no sibling",ec1.getPreviousSibling());
        assertEquals("parent ok",ec2.getParentNode_KElement(),e);
        assertNull("ec1 no parent",ec1.getParentNode());

        KElement ec33=ec3.replaceElement(ec3);
        assertEquals("replace of this is a nop",ec3,ec33);

        // now cross document
        KElement e2=d2.getRoot();
        e2.appendElement("e22");

        ec1=ec3.replaceElement(e2);
        assertNull("ec3 no parent",ec3.getParentNode());
        assertEquals("parent ok",ec1.getParentNode_KElement(),e);
        assertEquals("c4 is next",ec1,ec4.getPreviousSibling());
        assertEquals("c4 is next",ec1.getNextSibling(),ec4);
        assertEquals("root",ec1.getParentNode(),e);

        KElement eNew=e.replaceElement(e2);
        assertTrue(eNew.isEqual(e2));
        assertTrue(e.isEqual(e2));

    }

    ////////////////////////////////////////////////////////////////

    public void testSortChildren()
    {
        XMLDoc d=new JDFDoc("parent");
        KElement e=d.getRoot();
        KElement b=e.appendElement("b");
        KElement a=e.appendElement("a");
        KElement c=e.appendElement("c");
        e.sortChildren();
        assertEquals(e.getFirstChildElement(), a);
        assertEquals(a.getNextSiblingElement(), b);
        assertEquals(b.getNextSiblingElement(), c);
        KElement a3=e.appendElement("a");
        a3.setAttribute("ID", "z1");
        KElement a2=e.appendElement("a");
        a2.setAttribute("ID", "a1");
        e.sortChildren();
        assertEquals(e.getFirstChildElement(), a);
        assertEquals(a.getNextSiblingElement(), a2);
        assertEquals(a2.getNextSiblingElement(), a3);
        assertEquals(a3.getNextSiblingElement(), b);
        assertEquals(b.getNextSiblingElement(), c);

    }

    ////////////////////////////////////////////////////////////////

    public void testRemoveFromAttribute()
    {
        XMLDoc d=new JDFDoc("Foo");
        KElement e=d.getRoot();
        e.setAttribute("a", "1 b 2");
        e.removeFromAttribute("a", "b", null, " ", 333);
        assertEquals(e.getAttribute("a"), "1 2");
        e.setAttribute("a", "c");
        e.removeFromAttribute("a", "c", null, " ", 333);
        assertNull(e.getAttribute("a",null,null));
        e.removeFromAttribute("a", "c", null, " ", 333);
        assertNull(e.getAttribute("a",null,null));
    }

    ////////////////////////////////////////////////////////////////

    public void testRemoveExtensions()
    {
        KElement e=new XMLDoc("e","a.com").getRoot();
        final KElement b = e.appendElement("b","b.com");
        b.setAttribute("c:at", "cc","c.com");
        KElement c=e.appendElement("c", "c.com");
        assertNotNull(b.getAttribute("at", "c.com",null));
        assertNotNull(e.getElement("c", "c.com", 0));
        e.removeExtensions("c.com");
        assertNull(b.getAttribute("at", "c.com",null));
        assertNull(e.getElement("c", "c.com", 0));
        
    }
        ////////////////////////////////////////////////////////////////

        public void testRemoveEmptyAttributes()
        {
        JDFDoc d=new JDFDoc("JDF");
        KElement e=d.getJDFRoot();

        e.setAttribute("foo","bar",null);
        e.setAttribute("foo2","",null);

        assertTrue("has foo",e.hasAttribute("foo"));
        assertTrue("has foo2",e.hasAttribute("foo2"));

        KElement e2=e.appendElement("e2");
        e2.setAttribute("foo","bar",null);
        e2.setAttribute("foo2","",null);

        e.eraseEmptyAttributes(false);
        assertTrue("has foo",e.hasAttribute("foo"));
        assertFalse("has foo2",e.hasAttribute("foo2"));
        assertTrue("has foo",e2.hasAttribute("foo"));
        assertTrue("has foo2",e2.hasAttribute("foo2"));

        e.eraseEmptyAttributes(true);
        assertTrue("has foo",e.hasAttribute("foo"));
        assertFalse("has foo2",e.hasAttribute("foo2"));
        assertTrue("has foo",e2.hasAttribute("foo"));
        assertFalse("has foo2",e2.hasAttribute("foo2"));
    }

    public void testRemoveAttribute()
    {
        JDFDoc d=new JDFDoc("JDF");
        KElement e=d.getJDFRoot();
        e.setAttribute("foo","bar",null);
        assertTrue("has foo",e.hasAttribute("foo"));
        assertTrue("has foo",e.hasAttribute("foo",null,false));
        e.removeAttribute("foo",null);
        assertFalse("has foo",e.hasAttribute("foo"));
        assertFalse("has foo",e.hasAttribute("foo",null,false));
        e.removeAttribute("foo",null);
        e.removeAttribute("foo"); // make sure we have no npe for removing non existing attrbutes
        e.removeAttribute(""); // make sure we have no npe for removing non existing attrbutes

        e.setAttribute("foo","bar","");
        assertTrue("has foo",e.hasAttribute("foo"));
        assertTrue("has foo",e.hasAttribute("foo","",false));
        e.removeAttribute("foo","");
        assertFalse("has foo",e.hasAttribute("foo"));
        assertFalse("has foo",e.hasAttribute("foo","",false));


        e.setAttribute("foo","bar",JDFConstants.JDFNAMESPACE);
        assertTrue("has foo",e.hasAttribute("foo"));
        assertTrue("has foo",e.hasAttribute("foo",JDFConstants.JDFNAMESPACE,false));
        e.removeAttribute("foo",JDFConstants.JDFNAMESPACE);
        assertFalse("has foo",e.hasAttribute("foo"));
        assertFalse("has foo",e.hasAttribute("foo",JDFConstants.JDFNAMESPACE,false));

        e.setAttribute("JDF:foo","bar",JDFConstants.JDFNAMESPACE);
        assertTrue("has foo",e.hasAttribute("JDF:foo"));
        assertTrue("has foo",e.hasAttribute("foo",JDFConstants.JDFNAMESPACE,false));
        e.removeAttribute("foo",JDFConstants.JDFNAMESPACE);
        assertFalse("has foo",e.hasAttribute("JDF:foo"));
        assertFalse("has foo",e.hasAttribute("foo",JDFConstants.JDFNAMESPACE,false));

    }
    public void testMatchesPath()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement a=root.appendElement("a");
        root.appendElement("b");
        KElement a2=root.appendElement("a");
        KElement a3=root.appendElement("a");
        KElement c=root.appendElement("ns:c","www.c.com");
        c.setAttribute("att", "41");
        a.setAttribute("att", "42");
        assertTrue(a.matchesPath("//a", false));
        assertTrue(a.matchesPath("/Test/a", false));
        assertTrue(a.matchesPath("/Test/a[1]", false));
        assertTrue(a.matchesPath("/Test/a[@att=\"42\"]", false));
        assertTrue(a2.matchesPath("/Test/a[2]", false));
        assertTrue(a3.matchesPath("/Test/a[3]", false));
        assertFalse(a3.matchesPath("/Test/a[@att=\"*\"]", false));
        assertTrue(a.matchesPath("/Test/a[@att=\"*\"]", false));
        assertTrue(c.matchesPath("/Test/ns:c", false));
        assertTrue(c.matchesPath("/Test/ns:c[1]", false));
        assertTrue(c.matchesPath("/Test/ns:c[@att=\"*\"]", false));
        assertTrue(c.matchesPath("/Test/ns:c[@att=\"41\"]", false));
    }

    public void testMergeElement()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement t2=root.appendElement("foo");
        t2.setAttribute("a", "1");
        t2.setAttribute("b", "1");

        XMLDoc doc2      = new XMLDoc("Test","www.test.com");
        KElement root2=doc2.getRoot();
        KElement t3=root2.appendElement("foo");
        t3.setAttribute("a", "2");
        t3.setAttribute("c", "2");

        t2.mergeElement(t3, false);
        assertEquals(t2.getAttribute("a"), "2");
        assertEquals(t2.getAttribute("b"), "1");
        assertEquals(t2.getAttribute("c"), "2");       
    }
    public void testMergeElementElements()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement t2=root.appendElement("foo");
        KElement t22=t2.appendElement("bar");
        t22.setAttribute("a", "1");
        t22.setAttribute("b", "1");

        XMLDoc doc2      = new XMLDoc("Test","www.test.com");
        KElement root2=doc2.getRoot();
        KElement t3=root2.appendElement("foo");
        KElement t32=t3.appendElement("bar");
        t32.setAttribute("a", "2");
        t32.setAttribute("c", "2");

        t2.mergeElement(t3, false);
        assertEquals(t2.getElement("bar",null,0), t22);
        assertEquals(t22.getAttribute("a"), "2");
        assertEquals(t22.getAttribute("b"), "1");
        assertEquals(t22.getAttribute("c"), "2");       

    }

    public void testMoveMe()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement a=root.appendElement("a");
        KElement b=root.appendElement("b");
        KElement c=b.appendElement("c");
        assertNull(root.moveMe(a));
        assertNull(b.moveMe(c));
        assertEquals(b.getPreviousSibling(),a);
        b=b.moveMe(a);
        assertEquals(b.getNextSibling(),a);
        b=b.moveMe(b);
        assertEquals(b.getNextSibling(),a);
        b=b.moveMe(null);
        assertEquals(a.getNextSibling(),b);

    }

    public void testMoveAttribute()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement a=root.appendElement("a");
        KElement b=root.appendElement("b");
        a.setAttribute("att", "42");
        b.moveAttribute("att", a, null, null, null);
        assertEquals(b.getAttribute("att"),"42");
        assertNull(a.getAttribute("att", null, null));
        b.moveAttribute("noThere", a, null, null, null);
        assertNull(b.getAttribute("noThere",null,null));
        assertNull(a.getAttribute("noThere", null, null));
        a.setAttribute("foo", "a");
        b.moveAttribute("bar", a, "foo", null, null);
        assertEquals(b.getAttribute("bar"),"a");
        assertNull(a.getAttribute("bar", null, null));
        assertNull(b.getAttribute("foo", null, null));
        assertNull(a.getAttribute("foo", null, null));
    }
    public void testCopyElement()
    {
        XMLDoc d=new XMLDoc("d1",null);
        KElement e=d.getRoot();
        XMLDoc d2=new XMLDoc("d2",null);
        KElement e2=d2.getRoot();
        KElement e3=e.copyElement(e2,null);
        assertNull(e3.getNamespaceURI());
        assertFalse(d.toString().indexOf("xmlns=\"\"")>=0);

    }
    public void testCopyElementNS()
    {
        XMLDoc d=new XMLDoc("d1",null);
        KElement e=d.getRoot();
        XMLDoc d2=new XMLDoc("d2",null);
        KElement e2=d2.getRoot();
        e2.addNameSpace("foo", "www.foo.com");
        e2.setAttribute("foo:bar", "blub");
        KElement e3=e.copyElement(e2,null);
        assertNull(e3.getNamespaceURI());
        assertTrue(d.toString().indexOf("xmlns:foo=\"www.foo.com\"")>0);
    }

    public void testCopyAttribute()
    {
        XMLDoc doc      = new XMLDoc("Test","www.test.com");
        KElement root=doc.getRoot();
        KElement a=root.appendElement("a");
        KElement b=root.appendElement("b");
        a.setAttribute("att", "42");
        b.copyAttribute("att", a, null, null, null);
        assertEquals(a.getAttribute("att"),"42");
        assertEquals(b.getAttribute("att"),"42");
        b.copyAttribute("noThere", a, null, null, null);
        assertNull(b.getAttribute("noThere",null,null));
        assertNull(a.getAttribute("noThere", null, null));
        b.setAttribute("noThereA","b");
        b.copyAttribute("noThereA", a, null, null, null);
        assertNull("the existing attribute was removed ",b.getAttribute("noThereA",null,null));
        assertNull(a.getAttribute("noThereA", null, null));
        
        a.setAttribute("foo", "a");
        b.copyAttribute("bar", a, "foo", null, null);
        assertEquals(b.getAttribute("bar"),"a");
        assertEquals(a.getAttribute("foo"),"a");
        assertNull(a.getAttribute("bar", null, null));
        assertNull(b.getAttribute("foo", null, null));

    }

    public void testNameSpace()
    {
        JDFDoc doc      = new JDFDoc(ElementName.JDF);
        KElement root = doc.getRoot();

        root.setAttribute("xmlns","http://www.CIP4.org/JDFSchema_1_1");



        String docNS    = "http://www.cip4.org/test/";
        String myPrefix = "MyPrefix";


        // add the namespace, this is mandatory for java xerces (contrary to c++ xerces )
        root.addNameSpace(myPrefix, docNS);


        // add an element with a (predefined) prefix and no namespace
        KElement kElem9 = root.appendElement(
                myPrefix+JDFConstants.COLON+"MyElementLevel_2", "");
        assertTrue(kElem9.getNamespaceURI().equals(docNS));
        assertTrue(kElem9.getPrefix().equals(myPrefix));

        KElement kElem1 = root.appendElement(
                "MyElementLevel_1", docNS);
        assertTrue(kElem1.getNamespaceURI().equals(docNS));

//      kElem1.setAttributeNS(docNS, myPrefix+":att", "attval");
        kElem1.setAttributeNS(docNS, "att1", "attval1");


        // add an element in a namespace
        KElement kElem = root.appendElement(
                myPrefix+JDFConstants.COLON+"MyElement", docNS);
        assertTrue(kElem.getNamespaceURI().equals(docNS));
        assertTrue(kElem.getPrefix().equals(myPrefix));


        // add an attribute  and its value in a namespace
        kElem.setAttributeNS(
                docNS, myPrefix+JDFConstants.COLON+"MyAttribute", "MyValue");


        // How to get the element, Version 1
        KElement kElem2 = root.getElement_KElement("MyElement", docNS, 0);

        String attr = kElem2.getAttribute_KElement("MyAttribute", docNS, "MyDefault");
        assertTrue(attr.equals("MyValue"));

        // this is pretty invalid but the ns url takes precedence
        attr = kElem2.getAttribute_KElement(myPrefix+JDFConstants.COLON+"MyAttribute", docNS, "MyDefault");
        assertTrue(attr.equals("MyValue"));

        // this is even more invalid but the ns url takes precedence
        attr = kElem2.getAttribute_KElement("fnarf"+JDFConstants.COLON+"MyAttribute", docNS, "MyDefault");
        assertTrue(attr.equals("MyValue"));


        // How to get the element, Version 2
        KElement kElem3 = root.getElement_KElement(myPrefix+JDFConstants.COLON+"MyElement", docNS, 0);

        attr = kElem3.getAttribute_KElement("MyAttribute", docNS, "MyDefault");
        assertTrue(attr.equals("MyValue"));

        attr = kElem3.getAttribute_KElement(myPrefix+JDFConstants.COLON+"MyAttribute", docNS, "MyDefault");
        assertTrue(attr.equals("MyValue"));


        DocumentJDFImpl doc0 = (DocumentJDFImpl) root.getOwnerDocument();

        Element newChild=doc0.createElementNS(docNS,myPrefix+JDFConstants.COLON+ElementName.RESOURCELINKPOOL);
        root.appendChild(newChild);

        doc.write2File(sm_dirTestDataTemp+"NameSpace.jdf", 0, true);
    }

    public void testNameSpaceInElements()
    {
        JDFDoc doc    = new JDFDoc(ElementName.JDF);
        KElement root = doc.getRoot();

        String cip4NameSpaceURI = root.getNamespaceURI();   // "http://www.CIP4.org/JDFSchema_1_1";
        assertEquals(cip4NameSpaceURI, JDFConstants.JDFNAMESPACE);

        // adding cip4NameSpaceURI a second time as default namespace is ignored (using addNameSpace or setAttribute)
        root.addNameSpace(JDFConstants.EMPTYSTRING, cip4NameSpaceURI);
        root.setAttribute(JDFConstants.XMLNS, cip4NameSpaceURI);

        // adding cip4NameSpaceURI with different prefixes using addNameSpace is ignored
        String cip4Prefix1 = "JDF";
        String cip4Prefix2 = "jdf";
        String cip4Prefix3 = "JDFS";
        root.addNameSpace(cip4Prefix1, cip4NameSpaceURI);
        root.addNameSpace(cip4Prefix2, cip4NameSpaceURI);
        root.addNameSpace(cip4Prefix3, cip4NameSpaceURI);

        // adding cip4NameSpaceURI with different prefixes using setAttribute is allowed
        root.setAttribute(JDFConstants.XMLNS+JDFConstants.COLON+cip4Prefix1, cip4NameSpaceURI);
        root.setAttribute(JDFConstants.XMLNS+JDFConstants.COLON+cip4Prefix2, cip4NameSpaceURI);
        root.setAttribute(JDFConstants.XMLNS+JDFConstants.COLON+cip4Prefix3, cip4NameSpaceURI);

        // append an element without prefix with null NameSpaceURI or cip4NameSpaceURI
        KElement kElement0 = root.appendElement("kElement0", null);
        assertTrue(kElement0.getNamespaceURI().equals(cip4NameSpaceURI));
        assertNull(kElement0.getPrefix());

        KElement kElement1 = root.appendElement("kElement1", cip4NameSpaceURI);
        assertTrue(kElement1.getNamespaceURI().equals(cip4NameSpaceURI));
        assertNull(kElement1.getPrefix());


        // append an element with prefix with null NameSpaceURI or cip4NameSpaceURI
        KElement kElement2 = root.appendElement(cip4Prefix1+JDFConstants.COLON+"kElement2", null);
        assertTrue(kElement2.getNamespaceURI().equals(cip4NameSpaceURI));
        assertTrue(kElement2.getPrefix().equals(cip4Prefix1));

        KElement kElement3 = root.appendElement(cip4Prefix1+JDFConstants.COLON+"kElement3", cip4NameSpaceURI);
        assertTrue(kElement3.getNamespaceURI().equals(cip4NameSpaceURI));
        assertTrue(kElement3.getPrefix().equals(cip4Prefix1));


        String jdfDocString = "<JDF ID=\"n051221_021145422_000005\" Version=\"1.3\" " +
        "xmlns=\"http://www.CIP4.org/JDFSchema_1_1\" " +
        "xmlns:JDF=\"http://www.CIP4.org/JDFSchema_1_1\" " +
        "xmlns:JDFS=\"http://www.CIP4.org/JDFSchema_1_1\" " +
        "xmlns:jdf=\"http://www.CIP4.org/JDFSchema_1_1\">" +
        "<kElement0/>" +
        "<JDF:kElement1/>" +
        "<JDFS:kElement2/>" +
        "<jdf:kElement3/>" +
        "</JDF>";

        JDFParser p = new JDFParser();
        JDFDoc jdfDoc = p.parseString(jdfDocString);
        KElement root1 = jdfDoc.getRoot();

        // How to get the element, uri = null or cip4NameSpaceURI

        // empty prefix is ok
        KElement kElemGet1 = root1.getElement("kElement1", null, 0);
        KElement kElemGet2 = root1.getElement("kElement1", cip4NameSpaceURI, 0);
        assertEquals(kElemGet1, kElemGet2);

        // correct prefix is ok
        KElement kElemGet3 = root1.getElement(cip4Prefix1+JDFConstants.COLON+"kElement1", null, 0);
        KElement kElemGet4 = root1.getElement(cip4Prefix1+JDFConstants.COLON+"kElement1", cip4NameSpaceURI, 0);
        assertEquals(kElemGet3, kElemGet4);
        assertEquals(kElemGet2, kElemGet4);

        // wrong prefix
        KElement kElemGet5 = root1.getElement(cip4Prefix2+JDFConstants.COLON+"kElement1", null, 0);
        KElement kElemGet6 = root1.getElement(cip4Prefix2+JDFConstants.COLON+"kElement1", cip4NameSpaceURI, 0);
        assertNull(kElemGet5);
        assertNull(kElemGet6);
    }


    public void testNameSpace1()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFElement root = (JDFElement)doc.getRoot();

        String docNS1 = "www1";
        String docNS2 = "www2";
        String myPrefix = "HDM";

        try
        {
            // add an element in a namespace
            KElement kElem1 = root.appendElement(myPrefix + JDFConstants.COLON + "Foo_1", docNS1);
            assertTrue(kElem1.getNamespaceURI().equals(docNS1));
            assertTrue(kElem1.getPrefix().equals(myPrefix));

            kElem1.setAttribute(myPrefix + JDFConstants.COLON + "Foo_1", "attval1", docNS1);
            kElem1.setAttribute(myPrefix + JDFConstants.COLON + "Foo_2", "attval2", docNS2);
            fail("Called KElement.setAttribute with same prefix but different namespaces ?!");
        }
        catch (JDFException expected)
        {
            String partOfErrorMessage = "KElement.setAttribute:";
            assertTrue("Exception message doesn't even mention '"+partOfErrorMessage+"'?!",
                    expected.getMessage().indexOf(partOfErrorMessage) >= 0);
        }
    }

    public void testNameSpaceInAttributes()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFElement root = (JDFElement)doc.getRoot();
        root.addNameSpace("foo","www.foo.com");
        assertEquals("ns",root.getAttribute("xmlns:foo"),"www.foo.com");
        KElement child = root.appendElement("abc");
        child.addNameSpace("foo","www.foo.com");
        assertFalse("ns 2",child.hasAttribute("xmlns:foo"));

        child.setAttribute("foo:bar","a1");
        assertEquals("dom1",child.getAttribute("foo:bar"),"a1");
        child.setAttribute("foo:bar","a2","www.foo.com");
        child.setAttribute("foo:barNs","ns","www.foo.com");
        assertEquals("dom1",child.getAttribute("foo:bar"),"a2");
        assertEquals("dom2",child.getAttribute("bar","www.foo.com",null),"a2");
        child.setAttribute("foo:bar","a3");
        assertEquals("dom1",child.getAttribute("foo:bar"),"a3");
        assertEquals("dom2",child.getAttribute("bar","www.foo.com",null),"a3");
        child.setAttribute("bar:bar","b3","www.bar.com");
        assertEquals("dom1",child.getAttribute("bar:bar"),"b3");
        assertEquals("dom2",child.getAttribute("bar","www.bar.com",null),"b3");
        child.setAttribute("bar:bar","b2");
        assertEquals("dom1",child.getAttribute("bar:bar"),"b2");
        assertEquals("dom2",child.getAttribute("bar","www.bar.com",null),"b2");
        child.setAttribute("bar:bar","b4","www.bar.com");
        assertEquals("dom1",child.getAttribute("bar:bar"),"b4");
        assertEquals("dom2",child.getAttribute("bar","www.bar.com",null),"b4");
    }

///////////////////////////////////////////////////////////////////////

    public void testGetPrefix()
    {
        JDFDoc jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode myRoot = (JDFNode) jdfDoc.getRoot();
        myRoot.addNameSpace("foo","www.foo.com");
        KElement e=myRoot.appendElement("foo:bar",null);
        assertEquals(e.getPrefix(),"foo");
        myRoot.removeAttribute("xmlns:foo");
        assertEquals(e.getPrefix(),"foo");

    }
///////////////////////////////////////////////////////////////////////

    public void testGetLocalName()
    {
        JDFDoc jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode myRoot = (JDFNode) jdfDoc.getRoot();
        String docNS    = "http://www.cip4.org/test/";
        String myPrefix = "MyPrefix";

        // append an element with a different default namespace
        myRoot.appendElement("Foo", docNS);
        // get your element back
        KElement k = myRoot.getElement_JDFElement("Foo", "", 0);
        // test your method on this element
        String s = k.getLocalName();
        //gotcha First test ready
        assertEquals(
                "LocalName 'Foo' is not equal the original written name",
                s,"Foo");

        // add the namespace, this is mandatory for java xerces (contrary to c++ xerces )
        myRoot.addNameSpace(myPrefix, docNS);

        // append another element with a prefix, namespace is equal to default namespace
        myRoot.appendElement(myPrefix+":Faa", null);
        // get your element back
        k = myRoot.getElement_JDFElement("Faa", null, 0);
        // no null pointer handling...this is a test. The element HAS TO be there...otherwise test failed
        s = k.getLocalName();
        //
        assertEquals("LocalName 'Faa' is not equal the original written name", s, "Faa");

        jdfDoc.write2File(sm_dirTestDataTemp+"GetLocalNameStatic.jdf", 0, true);
    }

    ///////////////////////////////////////////////////////////////////

    /**
     * tests whether the correct virtual call hierarch is followed in getCreateElement
     */
    public void testGetCreateElement()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setType(JDFNode.EnumType.Imposition.getName(), false);
        JDFRunList rl = (JDFRunList) root.appendMatchingResource(ElementName.RUNLIST, JDFNode.EnumProcessUsage.Document, null);
        rl.appendLayoutElement();
        JDFRunList leaf = (JDFRunList) rl.getCreatePartition(JDFResource.EnumPartIDKey.Run, "Run1", new VString(JDFResource.EnumPartIDKey.Run.getName(), " "));

        KElement el1 = rl.getCreateElement_KElement(ElementName.LAYOUTELEMENT, null, 0);
        KElement el2 = leaf.getCreateElement_KElement(ElementName.LAYOUTELEMENT, null, 0);
        assertNotSame(el1, el2);
    }

    ///////////////////////////////////////////////////////////////////

    public void testGetMatchesPath()
    {
        XMLDoc d=new XMLDoc("a",null);
        KElement r=d.getRoot();
        KElement b=r.getCreateXPathElement("b/c/d");
        assertTrue(b.matchesPath("d",true));
        assertTrue(b.matchesPath("c/d",true));
        assertTrue(b.matchesPath("/a/b/c/d",true));
        assertTrue(b.matchesPath("a/b/c/d",true));
        assertFalse(b.matchesPath("a/a/b/c/d",true));
    }
    ///////////////////////////////////////////////////////////////////

    public void testGetDefaultAttributeMap()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();
        JDFAttributeMap defs=root.getDefaultAttributeMap();
        assertEquals("Template is defaulted",defs.get("Template"),"false");
        assertNull("ID is not defaulted",defs.get("ID"));
    }

    ///////////////////////////////////////////////////////////////////

    public void testEraseDefaultAttributeMap()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();
        root.setTemplate(false);
        assertTrue(root.hasAttribute(AttributeName.TEMPLATE));
        root.eraseDefaultAttributes(true);
        assertFalse("Template is defaulted",root.hasAttribute(AttributeName.TEMPLATE));
        assertTrue("ID is not defaulted",root.hasAttribute(AttributeName.ID));
    }
    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void testGetAttributeMap()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();
        assertFalse("Template is defaulted",root.getAttributeMap().containsKey("Template"));
    }
    ///////////////////////////////////////////////////////////////////

    public void testEraseEmptyNodes()
    {
        JDFParser p=new JDFParser();
        final String inFile = sm_dirTestData+File.separator+"BigWhite.jdf";
        JDFDoc  jdfDoc = p.parseFile(inFile);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();
        root.eraseEmptyNodes(true);
        final String outFile = sm_dirTestDataTemp+File.separator+"SmallWhite.jdf";
        jdfDoc.write2File(outFile,0, false);
        File f=new File(inFile);
        File f2=new File(outFile);
        assertTrue(f.length()>1.1*f2.length());
    }

    ///////////////////////////////////////////////////////////////////
    public void testRemoveXPathElement()
    {
        XMLDoc d=new XMLDoc("doc",null);
        KElement root=d.getRoot();
        root.setXPathAttribute("a/b[2]/@att", "foo");
        assertEquals(root.getXPathAttribute("a/b[2]/@att", null), "foo");
        assertEquals(root.getXPathAttribute("a/b[@att=\"foo\"]/@att", null), "foo");
        assertTrue(root.hasXPathNode("a/b[@att=\"foo\"]/@att"));
        assertTrue(root.hasXPathNode("a/b[@att=\"foo\"]"));
        root.removeXPathAttribute("a/b[2]/@att");
        assertFalse(root.hasXPathNode("a/b[@att=\"foo\"]/@att"));
        assertFalse(root.hasXPathNode("a/b[@att=\"foo\"]"));
        assertTrue(root.hasXPathNode("a/b[2]"));
        root.setXPathAttribute("a/b[2]/@att", "foo");
        root.removeXPathAttribute("a/b[@att=\"foo\"]/@att");
        assertFalse(root.hasXPathNode("a/b[@att=\"foo\"]/@att"));
        assertFalse(root.hasXPathNode("a/b[@att=\"foo\"]"));
        assertTrue(root.hasXPathNode("a/b[2]"));
    }
    ///////////////////////////////////////////////////////////////////

    public void testGetXPathAttributeMap()
    {
        XMLDoc  jdfDoc = new XMLDoc("a",null);
        KElement root   = jdfDoc.getRoot();
        root.setXPathAttribute("b/c[3]/d/@foo", "bar3");
        root.setXPathAttribute("b/c[5]/d/@foo", "bar5");
        Map m=root.getXPathAttributeMap("//*/@foo");
        assertEquals(m.size(), 2);
        m=root.getXPathAttributeMap("//@foo");
        assertEquals(m.size(), 2);
        Iterator it=m.keySet().iterator();
        assertEquals(root.getXPathAttribute((String)it.next(), null), "bar3");
        assertEquals(root.getXPathAttribute((String)it.next(), null), "bar5");

    }
    ///////////////////////////////////////////////////////////////////

    public void testGetXPathElementVector()
    {
        XMLDoc  jdfDoc = new XMLDoc("a",null);
        KElement root   = jdfDoc.getRoot();
        VElement va=new VElement();
        va.add(root);
        assertEquals(va,root.getXPathElementVector("//a", 0));
        assertEquals(va,root.getXPathElementVector("/a", 0));
        assertEquals(va,root.getXPathElementVector(".", 0));
        va.clear();
        va.add(root.appendElement("b"));
        va.add(root.appendElement("b"));
        assertEquals(va,root.getXPathElementVector("b", 0));
        assertEquals(va,root.getXPathElementVector("//b", 0));
        va.clear();
        va.add(root.getCreateXPathElement("./b/c"));
        va.add(root.getCreateXPathElement("./c"));
        root.getCreateXPathElement("./c/d");
        assertEquals(va,root.getXPathElementVector("//c", 0));
        root.getCreateXPathElement("./c/d");
        assertEquals(va,root.getXPathElementVector("//c", 0));
        assertEquals(1,root.getXPathElementVector("//d", 0).size());
        assertEquals(root.getXPathElementVector("//d", 0),root.getXPathElementVector("//c/d", 0));
        assertTrue(root.getXPathElementVector("//*", 0).contains(va.elementAt(0)));
        assertTrue(root.getXPathElementVector("//*", 0).contains(root));
        root.getCreateXPathElement("./c/d[2]");
        assertEquals(2,root.getXPathElementVector("//d", 0).size());
        assertEquals(2,root.getXPathElementVector("/a/c/d", 0).size());
        assertEquals("d",root.getXPathElementVector("/a/c/d", 0).elementAt(0).getNodeName());
    }
    
    ///////////////////////////////////////////////////////////////////

    public void testGetXPathElement()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();

        root.setXPathAttribute("/JDF/a[2]/@foo", "v2");
        root.setXPathAttribute("/JDF/a[3]/@foo", "v3");
        assertEquals(root.getXPathElement("/JDF/a[2]"), root.getXPathElement("/JDF/a[@foo=\"v2\"]"));
        assertEquals(root.getXPathElement("/JDF/a[3]"), root.getXPathElement("/JDF/a[@foo=\"v3\"]"));

        String nodeName = "Created";
        KElement kElem = root.getXPathElement("AuditPool/"+nodeName);
        assertEquals(kElem.getNodeName(),nodeName);
        assertTrue(kElem.matchesPath("Created",false));
        assertTrue(kElem.matchesPath("/JDF/AuditPool/Created",false));
        assertTrue(kElem.matchesPath("JDF/AuditPool/Created",false));
        assertFalse(kElem.matchesPath("/Created",false));

        nodeName = "notFound";
        kElem    = root.getXPathElement("AuditPool/"+nodeName);
        assertNull(kElem);
        XMLDoc d2=new XMLDoc("doc",null);
        KElement root2=d2.getRoot();
        for(int i=0;i<10;i++)
        {
            KElement e=root2.appendElement("e");
            assertEquals(root2.getXPathElement("e["+(i+1)+"]"), e);
            assertEquals(root2, e.getXPathElement("../"));
            assertEquals(root2, e.getXPathElement(".."));
            assertEquals(root2, e.getXPathElement(".././."));
        }
        KElement e=root2.getCreateElement("foo.bar");
        assertEquals(e.getNodeName(), "foo.bar");
        assertEquals(root2.getXPathElement("foo.bar"), e);
        assertEquals(root2.getCreateXPathElement("foo.bar"), e);
        root.setXPathAttribute("/JDF/ee[2]/@a", "2");
        root.setXPathAttribute("/JDF/ee[1]/@a", "2");
        root.setXPathAttribute("/JDF/ee[2]/ff/@b", "3");
        assertEquals(root.getXPathAttribute("/JDF/ee/ff/@b",null), "3");
        assertEquals(root.getXPathAttribute("/JDF/ee[@a=\"2\"]/ff/@b",null), "3");
        assertNull(root.getXPathAttribute("/JDF/ee[1]/ff/@b",null));


    }
    ///////////////////////////////////////////////////////////////////

//  public void testGetXPathNode()
//  {
//  JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
//  JDFNode root   = (JDFNode) jdfDoc.getRoot();

//  String nodeName = "Created";
//  KElement kElem = (KElement)root.getXPathNode("AuditPool/"+nodeName);
//  assertEquals(kElem.getNodeName(),nodeName);
//  assertTrue(kElem.matchesPath("Created",false));
//  assertTrue(kElem.matchesPath("/JDF/AuditPool/Created",false));
//  assertTrue(kElem.matchesPath("JDF/AuditPool/Created",false));
//  assertFalse(kElem.matchesPath("/Created",false));

//  nodeName = "notFound";
//  kElem    = (KElement)root.getXPathNode("AuditPool/"+nodeName);
//  assertNull(kElem);
//  }

    public void testGetCreateXPathElement()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();

        String nodeName = "Created";
        KElement kElem  = root.getCreateXPathElement("AuditPool/"+nodeName);
        assertTrue("", kElem != null && kElem.getNodeName().equals(nodeName));

        nodeName = "newElement";
        kElem    = root.getCreateXPathElement("AuditPool/"+nodeName);
        assertTrue("", kElem != null && kElem.getNodeName().equals(nodeName));

        KElement e=root.getCreateXPathElement("./foo/bar[2]/fnarf[3]");
        assertNotNull("e",e);
        assertEquals("",e,root.getCreateXPathElement("./foo/bar[2]/fnarf[3]"));
        assertEquals("",e,root.getXPathElement("./foo/bar[2]/fnarf[3]"));
        root.setXPathAttribute("./foo/bar[2]@blub", "b1");

        assertEquals("1 foo",root.numChildElements("foo",null),1);
        assertNotNull("get",root.getXPathElement("./foo/bar[2]/fnarf[3]"));
        assertEquals("",root.getElement("foo").numChildElements("bar",null),2);
        assertEquals("",root.getElement("foo").getElement("bar").numChildElements("fnarf",null),0);
        assertEquals("",root.getElement("foo").getElement("bar").getNextSiblingElement("bar",null).numChildElements("fnarf",null),3);
        assertEquals("",root.getCreateXPathElement("."),root);

        assertEquals("",e,root.getXPathElement("./foo/bar[@blub=\"b1\"]/fnarf[3]"));
        assertEquals("",e,root.getCreateXPathElement("./foo/bar[@blub=\"b1\"]/fnarf[3]"));
        assertNotSame("",e,root.getCreateXPathElement("./foo/bar[@blub=\"b1\"]/fnarf[5]"));
        assertEquals("",root.getElement("foo").getElement("bar").numChildElements("fnarf",null),0);
        assertEquals("",root.getElement("foo").getElement("bar").getNextSiblingElement("bar",null).numChildElements("fnarf",null),5);
        try
        {
            root.getCreateXPathElement("./foo/bar[@blub=\"b1\"]/fnarf[@a=\"b\"]");
            fail("cannot create by attribute value");
        }
        catch(IllegalArgumentException x)
        {
            /* */
        }
    }

    public void testBuildXPath()
    {
        XMLDoc d=new XMLDoc("d",null);
        KElement root=d.getRoot();
        assertEquals(root.buildXPath(null,1), "/d");
        assertEquals(root.buildXPath("/d",1), ".");
        assertEquals(root.buildXPath(null,0), "/d");
        assertEquals(root.buildXPath("/d",0), ".");
        root.appendElement("e");
        KElement e=root.appendElement("e");
        e.setAttribute("ID", "i");
        root.setAttribute("ID", "r");
        assertEquals(e.buildXPath(null,1), "/d/e[2]");
        assertEquals(e.buildXPath(null,3), "/d[@ID=\"r\"]/e[@ID=\"i\"]");
        assertEquals(e.buildXPath("/d",3), "./e[@ID=\"i\"]");
        assertEquals(e.buildXPath(null,0), "/d/e");
        assertEquals(e.buildXPath("/d",1), "./e[2]");
        assertEquals(e.buildXPath("/d",1), e.buildXPath("/d",2));
        assertEquals(e.buildXPath("/d",0), "./e");

    }

    ///////////////////////////////////////////////////

    public void testGetXPathAttribute()
    {
        JDFAudit.setStaticAuthor(JDFAudit.software());
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();

        String nodeName  = "Created";
        String attribute = "Author";
        String attValue  = root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, "dummydefault");
        assertEquals( attValue,JDFAudit.software());

        attribute = "notExistingAttribute";
        attValue  = root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, "dummydefault");
        assertEquals( attValue,"dummydefault");        
        assertNull(root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, null));     
        root.setXPathAttribute("foo/bar[2]/@a","b2");
        root.setXPathAttribute("foo/bar[2]/sub/@c","d2");
        assertEquals(root.getXPathAttribute("foo/bar[@a=\"b2\"]/sub/@c", null), "d2");
        try
        {
            root.getXPathAttribute("foo/bar[0]/sub/@c", null);
            fail("index must be >0");
        }
        catch(IllegalArgumentException x)
        {
            // nop
        }

    }

    public void testGetDOMAttr()
    {
        XMLDoc xd=new XMLDoc("a",null);
        KElement root=xd.getRoot();
        root.setAttribute("at","b");
        assertEquals("",root.getAttribute("at"),root.getDOMAttr("at",null,false).getNodeValue() );
        KElement child=root.appendElement("child");
        assertNull("",child.getDOMAttr("at",null,false));
        assertNull("",child.getDOMAttr("at_notther",null,true));
        assertNotNull("",child.getDOMAttr("at",null,true));
    }

    public void testRemoveXPathAttribute()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();

        String nodeName  = "Created";
        String attribute = "Author";
        root.removeXPathAttribute("AuditPool/"+nodeName+"@"+attribute);
        String attValue  = root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, null);
        assertNull("", attValue);

        attribute = "notExistingAttribute";
        root.removeXPathAttribute("AuditPool/"+nodeName+"@"+attribute);
        attValue  = root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, "dummydefault");
        assertTrue("", attValue.equals("dummydefault"));        
    }

    public void testSetXPathAttribute()
    {
        JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root   = (JDFNode) jdfDoc.getRoot();

        String nodeName  = "Created";
        String attribute = "Author";
        root.setXPathAttribute("AuditPool/"+nodeName+"@"+attribute, "newAttributeValue");
        root.setXPathAttribute("new/@foo","bar");
        assertEquals("", root.getXPathAttribute("new/@foo",null),"bar");
        assertEquals("", root.getXPathAttribute("new@foo",null),"bar");
        root.setXPathAttribute("new@foo2","bar2");
        assertEquals("", root.getXPathAttribute("new/@foo2",null),"bar2");
        assertEquals("", root.getXPathAttribute("new@foo2",null),"bar2");
        String attValue  = root.getXPathAttribute("AuditPool/"+nodeName+"@"+attribute, "dummydefault");
        assertEquals("", attValue,"newAttributeValue");
    }
    /**
     * Method testGetDeepParentChild.
     * @throws Exception
     */
    public void testGetDeepParentChild()
    {
        XMLDoc  jdfDoc = new XMLDoc("Test","www.test.com");
        KElement e   = jdfDoc.getRoot();
        KElement foo = e.appendElement("foo");
        KElement bar = foo.appendElement("bar");
        assertNull(bar.getDeepParentChild("fnarf"));
        assertEquals(bar.getDeepParentChild("Test"),foo);
        assertEquals(bar.getDeepParentChild("foo"),bar);
        KElement foo2 = e.appendElement("foo:foo","www.foo.com");
        KElement bar2 = foo2.appendElement("bar:bar","www.bar.com");
        assertEquals(bar2.getDeepParentChild("foo:foo"),bar2);
        KElement bar3 = bar2.appendElement("bar:fnarf","www.bar.com");
        assertEquals(bar3.getDeepParentChild("foo:foo"),bar2);
    }

    /**
     * Method testGetDeepParentNotName.
     * @throws Exception
     */
    public void testGetDeepParentNotName()
    {
        XMLDoc  jdfDoc = new XMLDoc("Test","www.test.com");
        KElement e   = jdfDoc.getRoot();
        KElement foo = e.appendElement("foo");
        KElement bar = foo.appendElement("bar");
        assertEquals(bar.getDeepParentNotName("bar"),foo);
        KElement bar2=bar.appendElement("bar","www.bar.com");
        assertEquals(bar2.getDeepParentNotName("bar"),foo);
        KElement bar3=(KElement)jdfDoc.createElement("bar");
        assertNull(bar3.getDeepParentNotName("bar"));
        KElement bar4=(KElement)jdfDoc.createElementNS("www.bar.com","bar");
        assertNull(bar4.getDeepParentNotName("bar"));
    }


    /**
     * Method testGetElementByID.
     * @throws Exception
     */
    public void testGetDeepElementByID ()
    {
        String xmlFile = "bookintent.jdf";

        JDFParser p = new JDFParser();
        JDFDoc jdfDoc = p.parseFile(sm_dirTestData+xmlFile);

        JDFNode jdfRoot = (JDFNode) jdfDoc.getRoot();
        XMLDocUserData ud=jdfRoot.getXMLDocUserData();

        // first try
        KElement kelem1 = jdfRoot.getDeepElementByID("ID", "n0006", null,ud);
        assertNotNull ("kelem1==null", kelem1);
        assertEquals("id",kelem1.getAttribute("ID"),"n0006");

        // second try
        KElement kelem2 = jdfRoot.getDeepElementByID("Preferred", "198", null,null);
        assertTrue ("kelem2==null", kelem2!=null);
        if (kelem2 == null) return;     // soothe findbugs ;)
        String strAtrib2 = kelem2.getAttribute("Preferred", "", "");
        assertTrue ("Preferred!=198", strAtrib2.equals("198"));
    }

    public void testInsertBefore()
    {
        XMLDoc  jdfDoc = new XMLDoc("Test","www.test.com");
        KElement e   = jdfDoc.getRoot();
        KElement k1=(KElement)jdfDoc.createElement("second");
        KElement k2=(KElement)jdfDoc.createElement("first");
        KElement k01=(KElement) e.insertBefore(k1, null);
        KElement k02=(KElement) e.insertBefore(k2, k1);
        assertEquals(k1, k01);
        assertEquals(k2, k02);
        assertEquals(k2.getNextSiblingElement(), k1);

    }

    public void testHasAttributeNS()
    {
        XMLDoc  jdfDoc = new XMLDoc("a:Test","www.a.com");
        KElement e=jdfDoc.getRoot();
        e.setAttribute("a:foo", "bar");
        e.setAttribute("bar", "bar2");
        assertTrue(e.hasAttribute("foo"));
        assertTrue("",e.hasAttribute("a:foo"));
        assertTrue(e.hasAttribute("bar"));
        assertTrue("",e.hasAttribute("a:bar"));
    }

    public void testInfinity()
    {
        XMLDoc  jdfDoc = new XMLDoc("Test","www.test.com");
        KElement e   = jdfDoc.getRoot();
        e.setAttribute("inf",Integer.MAX_VALUE,null);
        e.setAttribute("minf",Integer.MIN_VALUE,null);
        assertEquals("inf",e.getAttribute("inf",null,null),JDFConstants.POSINF);
        assertEquals("minf",e.getAttribute("minf",null,null),JDFConstants.NEGINF);
        assertEquals("inf",e.getIntAttribute("inf",null,0),Integer.MAX_VALUE);
        assertEquals("minf",e.getIntAttribute("minf",null,0),Integer.MIN_VALUE);
        // now double
        e.setAttribute("inf",Double.MAX_VALUE,null);
        e.setAttribute("minf",-Double.MAX_VALUE,null);
        assertEquals("inf",e.getAttribute("inf",null,null),JDFConstants.POSINF);
        assertEquals("minf",e.getAttribute("minf",null,null),JDFConstants.NEGINF);
        assertEquals("inf",e.getRealAttribute("inf",null,0),Double.MAX_VALUE,0.0);
        assertEquals("minf",e.getRealAttribute("minf",null,0),-Double.MAX_VALUE,0.0);
    }

    public void testSetAttribute_LongAttValue()
    {
        JDFDoc jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root  = (JDFNode) jdfDoc.getRoot();
        String longString="";
        for(int i=0;i<13;i++)
            longString+=longString+"0 123456789abcdefghijklmnopqrstuvwxyz";

        root.setAttribute("long", longString);
        assertEquals(root.getAttribute("long"), longString);
        jdfDoc.write2File(sm_dirTestDataTemp+"longAtt.jdf", 2, false);
        jdfDoc=new JDFDoc();
        jdfDoc=JDFDoc.parseFile(sm_dirTestDataTemp+"longAtt.jdf");
        root=jdfDoc.getJDFRoot();
        assertEquals(root.getAttribute("long"), longString);        
    }

    //////////////////////////////////////////////////////////////////////////////

    public void testSetAttributes() throws Exception
    {
        XMLDoc jdfDoc = new XMLDoc("Foo",null);
        KElement root=jdfDoc.getRoot();
        KElement a=root.appendElement("a");
        a.setAttribute("a", "1",null);
        a.setAttribute("b:b", "2","www.b.com");
        XMLDoc jdfDoc2 = new XMLDoc("Foo",null);
        KElement root2=jdfDoc2.getRoot();
        KElement a2=root2.appendElement("a");
        a2.setAttributes(a);
        assertEquals(a2.getAttribute("a"), "1");
        assertEquals(a2.getAttribute("b","www.b.com",null), "2");        
    }  

    /////////////////////////////////////////////////////////////////////////////////////

    public void testSetAttributesResource() throws Exception
    {
        JDFDoc doc=new JDFDoc("JDF");
        JDFNode n=doc.getJDFRoot();
        JDFExposedMedia x=(JDFExposedMedia) n.addResource("ExposedMedia", EnumUsage.Input);
        x.setAgentName("a1");
        JDFExposedMedia x2=(JDFExposedMedia) x.addPartition(EnumPartIDKey.SignatureName, "S1");
        KElement e2=n.appendElement("foo");
        e2.setAttributes(x2);
        assertEquals("root resource attributes not copied",e2.getAttribute("AgentName"), "a1");
        assertEquals("leaf resource attributes not copied",e2.getAttribute("SignatureName"), "S1");

    }


    public void testSetAttribute() throws Exception
    {
        JDFDoc jdfDoc = new JDFDoc(ElementName.JDF);
        JDFNode root  = (JDFNode) jdfDoc.getRoot();

        String nodeName = "Created";
        KElement kElem  = root.getXPathElement("AuditPool/" + nodeName);
        assertTrue("", kElem.getNodeName().equals(nodeName));

        // does setAttribute really set an empty value?
        kElem.setAttribute("Author", "");
        assertTrue("", kElem.getAttribute("Author", null, null).equals(""));

        assertTrue("", kElem.hasAttribute("Author", "", false));
        assertFalse("", kElem.hasAttribute("NewAttribute", "", false));

        kElem.setAttribute("Author", "", AttributeName.XMLNSURI);
        kElem.setAttribute("NewAttribute", "");
        assertTrue("", kElem.getAttribute("NewAttribute", null, null).equals(""));
        kElem.setAttribute("foo","���\"\'");
        assertEquals("special characters", kElem.getAttribute("foo", null, null), "���\"\'");
    }
    public void testSetAttributeNS() throws Exception
    {
        XMLDoc doc = new XMLDoc("a",null);
        KElement root = doc.getRoot();
        root.setAttribute("n:b", "1", "www.n.com");
        assertEquals(root.getAttribute("n:b"), "1");
        assertEquals(root.getAttribute("n:b","www.n.com",null), "1");
        assertEquals(root.getAttribute("b","www.n.com",null), "1");
        root.setAttribute("n:b", (String)null, "www.n.com");
        assertNull(root.getAttribute("n:b",null,null));
        assertNull(root.getAttribute("n:b","www.n.com",null));
        assertNull(root.getAttribute("b","www.n.com",null));
    }

    public void testCache()
    {
        XMLDoc d1=new XMLDoc("d1",null);
        XMLDoc d2=new XMLDoc("d2",null);
        assertNotNull(d1.getXMLDocUserData());
        assertNotNull(d2.getXMLDocUserData());
        assertTrue(d1.getXMLDocUserData().getIDCache());
        KElement e1=d1.getRoot();
        KElement e2=d2.getRoot();
        for(int i=0;i<4;i++){
            e1.setXPathAttribute("e2/e3"+String.valueOf(i)+"/@ID","i1"+String.valueOf(i));
            e2.setXPathAttribute("e2/e3"+String.valueOf(i)+"/@ID","i2"+String.valueOf(i));            
        }
        KElement e13=e2.getTarget("i13","ID");
        assertNull(e13);
        e13=e1.getTarget("i13","ID");
        assertNotNull(e13);
        assertEquals(d1,e1.getOwnerDocument_KElement());
        KElement e23=e2.getTarget("i23","ID");
        assertNotNull(e23);
        assertEquals(d2,e2.getOwnerDocument_KElement());
        e1.moveElement(e23,null);
        e23=e2.getTarget("i23","ID");
        assertNull(e23);
        e23=e1.getTarget("i23","ID");
        assertNotNull(e23);
        assertEquals(d1,e23.getOwnerDocument_KElement());
        e23.deleteNode();
        e23=e1.getTarget("i23","ID");
        assertNull(e23);

        e23=e2.getTarget("i22","ID");
        assertNotNull(e23);
        KElement e24=e23.renameElement("fnarf",null);
        assertEquals(e24,e23);
        assertEquals(e24.getNodeName(),"fnarf");
        assertEquals(e24.getLocalName(),"fnarf");
        assertEquals(e24,e2.getTarget("i22","ID"));

    }

    /**
     * 
     *
     */
    public void testGetElementHashMap()
    {
        XMLDoc d=new XMLDoc("root",null);
        KElement root=d.getRoot();
        for(int i=0;i<1000;i++)
        {
            KElement c=root.appendElement("child1");
            c.setAttribute("ID","id1_"+String.valueOf(i));
            c.setAttribute("II","abc");
            c=root.appendElement("ns:child2","myns");
            c.setAttribute("ID","id2_"+JDFElement.uniqueID(0));
        }
        assertEquals("",root.getElementHashMap(null,null,"ID").size(),2000);
        assertEquals("",root.getElementHashMap(null,"myns","ID").size(),1000);
        assertEquals("",root.getElementHashMap(null,null,"ID").get("id1_50"),root.getChildByTagName("child1",null,0,new JDFAttributeMap("ID","id1_50"),true,true));
    }

    /**
     * 
     *
     */
    public void testGetElement_KElement()
    {
        XMLDoc d=new XMLDoc("JDF",null);
        KElement root=d.getRoot();
        KElement c1=root.appendElement("c");
        KElement c2=root.appendElement("c");
        KElement b1=root.appendElement("b");
        KElement c3=root.appendElement("c");
        c3.setAttribute("ID", "i1");
        KElement ref=root.appendElement("dRef");

        assertEquals(root.getElement("c"), c1);
        assertEquals(root.getElement(null), root.getFirstChild());
        assertEquals(root.getElement("dRef"), ref);

        assertNull(root.getElement("d"));
        assertEquals(root.getElement("b"), b1);
        assertEquals(root.getElement_KElement("c",null,0), c1);
        assertEquals(root.getElement_KElement("b",null,-1), b1);
        assertEquals(root.getElement_KElement("c",null,-3), c1);
        assertEquals(root.getElement_KElement("c",null,-1), c3);
        assertEquals(root.getElement_KElement("c",null,1), c2);
        assertEquals(root.getElement_KElement("c",null,-2), c2);
        assertEquals(root.getElement_KElement(null,null,-5), c1);
        assertEquals(root.getElement_KElement(null,null,3), c3);
        assertNull(root.getElement_KElement("c",null,-4));
        assertNull(root.getElement_KElement("c",null,3));
    }
    ///////////////////////////////////////////////////////////////////////////////


    public void testGetChildrenByTagName()
    {
        String xmlFile = "getChildrenByTagNameTest.jdf";

        JDFParser p = new JDFParser();
        JDFDoc jdfDoc = p.parseFile(sm_dirTestData+xmlFile);

        JDFNode jdfRoot = (JDFNode) jdfDoc.getRoot();
        JDFResourcePool jdfPool = jdfRoot.getResourcePool();
        VElement v = jdfPool.getChildrenByTagName("RunList", null,null, false, true, 0);
        assertEquals("Wrong number of child elements found", v.size() ,10);
        v = jdfPool.getChildrenByTagName("RunList", null,null, false, true, -1);
        assertEquals("Wrong number of child elements found", v.size() ,10);
        v = jdfPool.getChildrenByTagName("RunList", null,null, false, true, 5);
        assertEquals("Wrong number of child elements found", v.size() ,5);
    }
    ///////////////////////////////////////////////////////////////////////////////

    public void testGetChildrenFromList()
    {
        XMLDoc doc=new XMLDoc("Foo",null);
        KElement root=doc.getRoot();
        KElement a=root.appendElement("a");
        KElement b=a.appendElement("b");
        KElement b2=a.appendElement("b:b","s");
        assertTrue(root.getChildrenFromList(new VString("b"," "), null, false, null).contains(b));
        assertTrue(root.getChildrenFromList(new VString("b"," "), null, false, null).contains(b2));
        assertTrue(root.getChildrenFromList(new VString("b:b"," "), null, false, null).contains(b2));
        assertFalse(root.getChildrenFromList(new VString("b:b"," "), null, false, null).contains(b));
    }

    ///////////////////////////////////////////////////////////////////////////////

    public void testGetChildWithAttribute()
    {
        XMLDoc doc=new XMLDoc("Foo",null);
        KElement root=doc.getRoot();
        assertEquals(root.getChildElementArray().length, 0);
        root.appendElement("bar:bar","www.bar.com");
        KElement bar2=root.appendElement("bar2");
        bar2.setAttribute("foo", "1");
        bar2.setAttribute("ID", "id2");
        KElement bar3=bar2.appendElement("bar3");
        bar3.setAttribute("foo", "1");
        bar3.setAttribute("foo2", "2");
        bar3.setAttribute("ID", "id3");
        assertEquals(root.getChildWithAttribute(null, "foo2", null, null, 0,false), bar3);
        assertEquals(root.getChildWithAttribute(null, "foo", null, null, 0,false), bar2);
        assertEquals(root.getChildWithAttribute(null, "foo", null, null, 1,false), bar3);
        assertEquals(root.getChildWithAttribute(null, "foo", null, null, 0,true), bar2);
        assertEquals(root.getChildWithAttribute(null, "foo", null, "1", 0,true), bar2);
        assertEquals(root.getChildWithAttribute(null, "ID", null, "id2", 0,true), bar2);
        assertNull(root.getChildWithAttribute(null, "ID", null, "id3", 0,true));

        XMLDoc doc2=new XMLDoc("Foo",null);
        KElement root2=doc2.getRoot();
        KElement bar22=root2.appendElement("bar2");
        bar22.setAttribute("ID", "id22");
        assertEquals(root2.getChildWithAttribute(null, "ID", null, "id22", 0,true), bar22);
        assertNull(root.getChildWithAttribute(null, "ID", null, "id22", 0,true));
        bar3.moveElement(bar22, null);
        assertNull(root2.getChildWithAttribute(null, "ID", null, "id22", 0,true));

    }


    public void testGetChildElementVector_KElement()
    {
        JDFDoc doc=new JDFDoc("JDF");
        JDFNode n=doc.getJDFRoot();
        JDFResource r=n.addResource(ElementName.EXPOSEDMEDIA, null, null, null, null, null, null);
        JDFResource rp=r.addPartition(EnumPartIDKey.Side, EnumSide.Front.getName());
        VElement v = r.getChildElementVector_KElement(ElementName.EXPOSEDMEDIA, null, null, true, 0);
        assertEquals(v.elementAt(0), rp);
        assertEquals(v.size(),1);
        JDFResource r2=rp.addPartition(EnumPartIDKey.SheetName, "s2");
        v = r.getChildElementVector_KElement(ElementName.EXPOSEDMEDIA, null, null, true, 0);
        assertEquals(v.elementAt(0), rp);
        assertEquals(v.size(),1);
        JDFResource r3=rp.addPartition(EnumPartIDKey.SheetName, "s3");
        JDFAttributeMap map=new JDFAttributeMap(AttributeName.SHEETNAME,"s2");
        v = rp.getChildElementVector_KElement(ElementName.EXPOSEDMEDIA, null, map, true, 0);
        assertTrue(v.contains(r2));
        assertFalse(v.contains(r3));        
    }

    public void testGetChildElementArray()
    {
        XMLDoc doc=new XMLDoc("Foo",null);
        KElement root=doc.getRoot();
        assertEquals(root.getChildElementArray().length, 0);
        root.appendElement("bar:bar","www.bar.com");
        root.appendElement("bar2");
        assertEquals(root.getChildElementArray().length, 2);
        assertEquals(root.getChildElementArray()[0], root.getElement("bar:bar"));
        assertEquals(root.getChildElementArray()[1], root.getElement("bar2"));
    }
    ///////////////////////////////////////////////////////////////////////////////

    public void testAttributeInfo()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        VString s=n.getNamesVector("Status");
        assertTrue("Status enum",s.contains("InProgress"));
    }


    public void testPushUp()
    {
        {//defines a logical test block
            //pushup from 4 to 1
            JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
            JDFNode root   = (JDFNode) jdfDoc.getRoot();
            KElement e = root;
            for(int i = 0; i < 5; i++)
            {
                e = e.appendElement("Test" + i, null);
            }
            e.pushUp("Test1");
            KElement k = root.getXPathElement("Test0/Test1");
            VElement v = k.getChildElementVector("Test4", null, new JDFAttributeMap(), true, 99999,false);
            assertTrue("pushUp does not work", v.size() == 1);
        }

        {//defines a logical test block
            //pushup with emptystring
            JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
            JDFNode root   = (JDFNode) jdfDoc.getRoot();
            KElement e = root;
            for(int i = 0; i < 5; i++)
            {
                e = e.appendElement("Test" + i, null);
            }
            e.pushUp("");
            KElement k = root.getXPathElement("Test0/Test1/Test2");
            VElement v = k.getChildElementVector("Test4", null, new JDFAttributeMap(), true, 99999,false);
            assertTrue("pushUp does not work", v.size() == 1);
        }

        {//defines a logical test block
            //pushup and force parentNode == null
            JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
            JDFNode root   = (JDFNode) jdfDoc.getRoot();
            KElement e = root;
            for(int i = 0; i < 5; i++)
            {
                e = e.appendElement("Test" + i, null);
            }
            KElement k = e.pushUp("Foo");
            assertTrue(k == null);
        }
    }

    public void testSetAttribute_DoubleAtt() throws Exception
    {
        JDFParser p=new JDFParser();
        {
            XMLDoc doc=new XMLDoc("d",null);
            KElement root=doc.getRoot();
            root.setAttribute("a:b", "a1");
            String s2=doc.write2String(0);
            XMLDoc doc2=p.parseString(s2);
            assertNull("invalid ns stuff",doc2);
            root.setAttributeNS("www.a.com","a:b", "a2");
            s2=doc.write2String(0);
            doc2=p.parseString(s2);
            assertNotNull("invalid ns stuff",doc2);
            KElement root2=doc2.getRoot();            
            root2.setAttribute("a:b", "a2","www.a.com");
            root.setAttribute("a:b", "a2","www.a.com");

            String s=doc.write2String(0);
            p.parseString(s);
            s2=doc.write2String(0);
            p.parseString(s2);
        }
        {
            XMLDoc doc=new XMLDoc("d",null);
            KElement root=doc.getRoot();
            root.setAttribute("a:b", "a2","www.a.com");
            root.setAttribute("a:b", "a1");
            String s=doc.write2String(0);
            doc=p.parseString(s);
            root=doc.getRoot();
            root.setAttribute("a:b", "a3");
            s=doc.write2String(0);
            doc=p.parseString(s);

        }

    }

    public void testSetAttribute_NameSpaceHandling()
    {
        for(int dd=0;dd<2;dd++)
        {
            JDFDoc  jdfDoc = new JDFDoc(ElementName.JDF);
            JDFNode root   = (JDFNode) jdfDoc.getRoot();
            KElement e     = root;


            root.setAttribute("xmlns:Kai", "foo");
            KElement appended = e.appendElement("Kai:Test1", "foo");
            appended.setAttribute("Kai:Test1", "1", "foo");
            //no try to change the namespace and append
            e.setAttribute("xmlns:Kai", "faa");
            appended.setAttribute("Kai:Test1", "2", null);
            root.setAttribute("aa","bb");
            root.setAttribute("aa","bb",null);
            assertTrue(root.hasAttribute("aa"));

            KElement c=root.appendElement("Comment",null);
            assertTrue("ns append ok",c.getNamespaceURI().equals(root.getNamespaceURI()));
            KElement f = root.insertBefore("fnarf",c,null);
            assertTrue("ns insert ok",f.getNamespaceURI().equals(root.getNamespaceURI()));
            assertTrue("ns  ok",f.getNamespaceURI()!=null);
            assertTrue("ns  ok",f.getNamespaceURI().equals(JDFConstants.JDFNAMESPACE));
            KElement f2 = root.insertBefore("fnarf:fnarf",c,"www.fnarf");
            assertTrue("ns  ok",f2.getNamespaceURI().equals("www.fnarf"));

            //try to add an element into an unspecified namespace MUST FAIL
            try
            {
                e.appendElement("Kai:Test1");
//              assume that the namespace will be added later            fail("snafu");
            }
            catch(JDFException jdfe)
            {
                //do nothing
            }

            String testIt=jdfDoc.write2String(0);
            JDFParser p=new JDFParser();
            JDFDoc d2=p.parseString(testIt);
            root   = (JDFNode) d2.getRoot();
//          root.setAttribute("aa","cc");
            root.setAttribute("aa","nsns",root.getNamespaceURI());
            assertFalse("no ns1",root.hasAttribute("ns1:aa"));
            assertEquals("no ns1",root.getAttribute("aa",root.getNamespaceURI(),null),"nsns");
            root.setAttribute("bb:aa","nsnt",root.getNamespaceURI());
            assertTrue("ns1 default",root.hasAttribute("aa"));
            assertTrue("no ns1",root.hasAttribute("bb:aa"));
            assertEquals("no ns1",root.getAttribute("aa",root.getNamespaceURI(),null),"nsnt");
            assertEquals("no ns1",root.getAttribute("aa",null,null),"nsnt");
        }
    }

    public void testXMLNameSpace()
    {
        assertNull("no ns", KElement.xmlnsPrefix("abc"));
        assertNull("no ns", KElement.xmlnsPrefix(":abc"));
        assertEquals("ns", "ns",KElement.xmlnsPrefix("ns:abc"));
        assertEquals("abc", "abc",KElement.xmlnsLocalName("ns:abc"));
        assertNull("no local name",  KElement.xmlnsLocalName("abc:"));
        assertNull("no local name",  KElement.xmlnsLocalName(null));
    }

    public void testAppendChild()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot();
        e.setAttribute("xmlns:foo", "www.foo.com");
        KElement e2=(KElement) d.createElement("e2");
        e.appendChild(e2);
        assertEquals(e.getFirstChild(), e2);
        KElement e3=(KElement) d.createElement("foo:e3");
        assertNull(e3.getNamespaceURI());                  
        e.appendChild(e3);
        assertEquals(e2.getNextSibling(), e3);                  
        assertEquals(e3.getNamespaceURI(), "www.foo.com");                  
    }
    public void testAppendCData() throws Exception
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot();
        XMLDoc d2=new XMLDoc("e2",null);
        KElement e2=d2.getRoot();
        e.appendCData(e2);
        assertTrue(e.toString().indexOf("<e2")>0);
        e.appendCData(e);
        JDFParser p=new JDFParser();
        JDFDoc d3=p.parseString(d.write2String(2));
        assertNotNull(d3);

    }
    public void testParseAppendChild()
    {
        String s="<e xmlns=\"a\" xmlns:foo=\"www.foo.com\"><e2/></e>";
        JDFParser p=new JDFParser();
        p.bKElementOnly=true;
        p.ignoreNSDefault=true;

        XMLDoc d=p.parseString(s);
        KElement e=d.getRoot();
        KElement e3=(KElement) d.createElement("foo:e3");
        assertNull(e3.getNamespaceURI());                  
        e.appendChild(e3);
        KElement e2 = (KElement) e.getFirstChild();
        assertEquals(e2.getNextSibling(), e3);                  
        assertNull(e3.getNamespaceURI());                  
        KElement e4=(KElement) d.createElement("foo:e3");
        assertNull(e4.getNamespaceURI());                  
        e.appendChild(e4);
        assertNull(e4.getNamespaceURI());                  
    }

    public void testSetXMLComment()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement root=d.getRoot();
        root.setXMLComment("foo");
        assertEquals(d.getDocumentElement().getParentNode().getFirstChild().getNodeValue(),"foo");
        root.setXMLComment("bar");
        assertEquals(d.getDocumentElement().getParentNode().getFirstChild().getNodeValue(),"bar");
        KElement e2=root.appendElement("e2");
        e2.setXMLComment("foobar");
        assertEquals(root.getFirstChild().getNodeValue(),"foobar");
        e2.setXMLComment("foobar2");
        assertEquals(root.getFirstChild().getNodeValue(),"foobar2");
        assertEquals(root.getFirstChild().getNextSibling(),e2);
        assertNull(root.getFirstChild().getNextSibling().getNextSibling());

    }

    public void testAppendElement()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot();
        assertNull(e.getNamespaceURI());
        KElement foo=e.appendElement("pt:foo", "www.pt.com");
        assertEquals(foo.getNamespaceURI(), "www.pt.com");
        KElement bar=foo.appendElement("bar");
        assertNull(bar.getNamespaceURI());
        bar.setAttribute("xmlns", "www.bar.com");

        KElement bar2=bar.appendElement("bar");
        assertEquals(bar2.getNamespaceURI(), "www.bar.com");              

        KElement foo2=bar.appendElement("pt:foo", "www.pt.com");
        assertEquals(foo2.getNamespaceURI(), "www.pt.com"); 

        d.getMemberDocument().setIgnoreNSDefault(true);
        KElement bar3=bar.appendElement("bar");
        assertNull(bar3.getNamespaceURI());              
        KElement bar4=bar2.appendElement("bar");
        assertNull(bar4.getNamespaceURI());              
    }

    public void testAppendElement_NSAtt()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot();
        assertNull(e.getNamespaceURI());
        e.setAttribute("xmlns:pt", "www.pt.com");
        KElement foo=e.appendElement("foo", null);
        assertNull(foo.getNamespaceURI());
        KElement bar=foo.appendElement("bar");
        assertNull(bar.getNamespaceURI());
        KElement bar2=foo.appendElement("pt:bar");
        assertEquals(bar2.getNamespaceURI(), "www.pt.com");              
    }

    public void testAppendElement_NSAttJDFDoc()
    {
        JDFDoc d=new JDFDoc("e");
        d.getMemberDocument().setIgnoreNSDefault(true);
        final String url = JDFElement.getSchemaURL();
        {
            KElement e=d.getRoot();
            assertEquals(e.getNamespaceURI(),url);
            e.setAttribute("xmlns:pt", "www.pt.com");
            KElement foo=e.appendElement("foo", null);
            assertEquals(foo.getNamespaceURI(),url);
            KElement bar=foo.appendElement("bar");
            assertEquals(bar.getNamespaceURI(),url);
            KElement bar2=foo.appendElement("pt:bar");
            assertEquals(bar2.getNamespaceURI(), "www.pt.com");     
        }
        String s=d.write2String(0);

        // now check for parsed document with no default xmlns declaration
        JDFParser p=new JDFParser();
        int pos=s.indexOf(url);
        s=s.substring(0, pos-7)+s.substring(pos+url.length()+1); // +/- for xmlns=" and "
        d=p.parseString(s);
        d.getMemberDocument().setIgnoreNSDefault(true);
        {
            KElement e=d.getRoot();
            assertNull(e.getNamespaceURI());
            e.setAttribute("xmlns:pt", "www.pt.com");
            KElement foo=e.appendElement("foo", null);
            assertNull(foo.getNamespaceURI());
            KElement bar=foo.appendElement("bar");
            assertNull(bar.getNamespaceURI());
            KElement bar2=foo.appendElement("pt:bar");
            assertEquals(bar2.getNamespaceURI(), "www.pt.com");     
        }
    }

    public void testAppendElement_SingleNS()
    {
        for(int i=0;i<2;i++)
        {
            final String wwwECom = "www.e.com";
            final XMLDoc d =i==0 ? new XMLDoc() : new JDFDoc();
            d.setRoot("e", wwwECom);
            KElement e=d.getRoot();
            e.addNameSpace(null, wwwECom);
            assertEquals(e.getNamespaceURI(),wwwECom);
            KElement foo=e.appendElement("f", null);
            assertEquals(foo.getNamespaceURI(), wwwECom); 
            foo=e.appendElement("f", "");
            assertEquals(foo.getNamespaceURI(), wwwECom); 
        }

    }

    public void testCreateElement_NoNS()
    {
        for(int i=0;i<2;i++)
        {
            final String wwwECom = "www.e.com";
            final XMLDoc d =i==0 ? new XMLDoc() : new JDFDoc();
            d.setRoot("e", wwwECom);
            KElement e=d.getRoot();
            assertEquals(e.getNamespaceURI(),wwwECom);
            Element eFoo=d.createElement("f");
            e.appendChild(eFoo);
            Element eBar=d.createElement("b");
            eFoo.appendChild(eBar);
            assertEquals(eBar.getNamespaceURI(), wwwECom); 
            assertEquals(eFoo.getNamespaceURI(), wwwECom); 
            eFoo=d.createElementNS(wwwECom,"f");
            e.appendChild(eFoo);
            assertEquals(eFoo.getNamespaceURI(), wwwECom); 
        }
    }

    public void testParse_SingleNS()
    {
        final String wwwECom = "www.e.com";
        XMLDoc d=new XMLDoc("e",wwwECom);
        KElement e=d.getRoot();
        assertEquals(e.getNamespaceURI(),wwwECom);
        KElement foo=e.appendElement("f", null);
        assertEquals(foo.getNamespaceURI(), wwwECom);  
        String s=d.write2String(2);
        JDFParser p=new JDFParser();
        JDFDoc d2=p.parseString(s);
        KElement e2=d2.getRoot();
        assertEquals(e2.getNamespaceURI(),wwwECom);
        KElement foo2=e.appendElement("f", null);
        assertEquals(foo2.getNamespaceURI(), wwwECom);  
        assertEquals(-1,d2.write2String(2).indexOf("jdf"));   
    }

    public void testAppendXMLComment()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot(); 
        VString v=new VString("a . - . -- . -->.<!--",".");
        for(int i=0;i<v.size();i++)
        {
            String s = v.stringAt(i);
            e.appendXMLComment(s, null);
            d.write2File(sm_dirTestDataTemp+"xmlComment.jdf", 2, false);
            XMLDoc d2=new JDFParser().parseFile(sm_dirTestDataTemp+"xmlComment.jdf");
            KElement e2=d2.getRoot();
            s=StringUtil.replaceString(s, "--", "__");
            assertEquals(e.getXMLComment(i),s);
            assertEquals(e2.getXMLComment(i),s);
        }
    }


    public void testAppendAttribute()
    {
        XMLDoc d=new XMLDoc("e",null);
        KElement e=d.getRoot();
        e.appendAttribute("at","a",null," ",false);
        e.appendAttribute("at","b",null," ",false);
        e.appendAttribute("at","c",null," ",false);
        assertEquals("a b c", e.getAttribute("at"),"a b c");
        e.appendAttribute("at","c",null," ",true);
        assertEquals("a b c", e.getAttribute("at"),"a b c");
        e.appendAttribute("at","c",null," ",false);
        assertEquals("a b c", e.getAttribute("at"),"a b c c");
        e.appendAttribute("at","a a b c",null,null,true);
        assertEquals("a b c", e.getAttribute("at"),"a b c c a a b c");
        e.appendAttribute("ns:key","na","www.ns.com"," ",true);
        assertEquals("ns a", e.getAttribute("key","www.ns.com",""),"na");
        e.appendAttribute("ns:key","nb",null," ",true);
        assertEquals("ns a", e.getAttribute("ns:key"),"na nb");
        e.appendAttribute("ns:key","nc","www.ns.com"," ",true);
        assertEquals("ns a", e.getAttribute("key","www.ns.com",""),"na nb nc");
        assertEquals("ns a", e.getAttribute("ns:key"),"na nb nc");

    }

    ////////////////////////////////////////////////////////////////////////////

    public void testTypeInfo() // commented out due to Java 1.4 1.5 package incompatibilities
    {
//      XMLDoc d = new XMLDoc("doc", null);
//      KElement root = d.getRoot();
//      TypeInfo ti=root.getSchemaTypeInfo();
//      assertNotNull(ti);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void testTextMethods()
    {
        XMLDoc d = new XMLDoc("doc", null);
        KElement root = d.getRoot();
        KElement e1 = root.appendElement("e1");
        e1.setAttribute("a", "b");

        e1.setText("foo");
        assertEquals("foo", e1.getText());
        assertTrue(e1.hasChildText());

        e1.setText("bar");
        assertEquals("bar", e1.getText());
        assertTrue(e1.hasChildText());

        e1.removeAllText();
        assertNull(e1.getText());
        assertFalse(e1.hasChildText());

        e1.appendText("foo");
        assertEquals("foo", e1.getText());
        assertTrue(e1.hasChildText());

        e1.appendText("bar");
        assertEquals("foobar", e1.getText());
        assertTrue(e1.hasChildText());

        assertEquals(e1.getNumChildText(), 2);
        assertEquals("foo", e1.getText(0));
        assertEquals("bar", e1.getText(1));
        assertTrue(e1.hasChildText());

        e1.removeChildText(1);
        assertEquals(e1.getNumChildText(), 1);
        assertEquals("foo", e1.getText(0));
        assertTrue(e1.hasChildText());

        e1.removeChildText(0);
        assertEquals(e1.getNumChildText(), 0);
        assertEquals(null, e1.getText(0));                      // getText(i) can return null
        assertNull(e1.getText());   // getText() can return null !!!
        assertFalse(e1.hasChildText());

        e1.removeAllText();
        assertFalse(e1.hasChildText());

        KElement e2 = root.appendTextElement("e2", "text");
        assertEquals(e2.getNumChildText(), 1);
        assertEquals("text", e2.getText(0));
    }

    public void testFillHashSet()
    {
        XMLDoc d = new XMLDoc("doc", null);
        KElement root = d.getRoot();
        KElement e1 = root.appendElement("e1");
        e1.setAttribute("a", "b");
        e1.setAttribute("a2", "b");
        root.setAttribute("a","aa");

        e1.setXPathAttribute("./e2/e3@a","c");
        e1.setXPathAttribute("./e3/e4@a","d");
        HashSet h=new HashSet();
        root.fillHashSet("a",null,h);

        assertTrue("",h.contains("aa"));
        assertTrue("",h.contains("b"));
        assertTrue("",h.contains("c"));
        assertTrue("",h.contains("d"));
        assertFalse("",h.contains("a2"));

        h.clear();
        e1.fillHashSet("a",null,h);

        assertFalse("",h.contains("aa"));
        assertTrue("",h.contains("b"));
        assertTrue("",h.contains("c"));
        assertTrue("",h.contains("d"));
        assertFalse("",h.contains("a2"));



    }


}
