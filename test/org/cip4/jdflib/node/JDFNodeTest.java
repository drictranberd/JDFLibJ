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
package org.cip4.jdflib.node;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.auto.JDFAutoComponent.EnumComponentType;
import org.cip4.jdflib.auto.JDFAutoDeviceInfo.EnumDeviceStatus;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFCustomerInfo;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.JDFNodeInfo;
import org.cip4.jdflib.core.JDFParser;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.core.JDFAudit.EnumAuditType;
import org.cip4.jdflib.core.JDFElement.EnumNodeStatus;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.KElement.EnumValidationLevel;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.JDFIntegerList;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.node.JDFNode.EnumActivation;
import org.cip4.jdflib.node.JDFNode.EnumCleanUpMerge;
import org.cip4.jdflib.node.JDFNode.EnumProcessUsage;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourceLinkPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFDevice;
import org.cip4.jdflib.resource.JDFPhaseTime;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResourceAudit;
import org.cip4.jdflib.resource.JDFResourceTest;
import org.cip4.jdflib.resource.JDFResource.EnumAmountMerge;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.resource.JDFResource.EnumPartUsage;
import org.cip4.jdflib.resource.JDFResource.EnumResStatus;
import org.cip4.jdflib.resource.JDFResource.EnumResourceClass;
import org.cip4.jdflib.resource.devicecapability.JDFModule;
import org.cip4.jdflib.resource.process.JDFComponent;
import org.cip4.jdflib.resource.process.JDFContact;
import org.cip4.jdflib.resource.process.JDFConventionalPrintingParams;
import org.cip4.jdflib.resource.process.JDFEmployee;
import org.cip4.jdflib.resource.process.JDFExposedMedia;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.JDFMerge;


public class JDFNodeTest extends JDFTestCaseBase       
{

    public void testBigSortChildren()
    {
        final String strJDFName = "000023_Test_PR3.0.jdf";
        JDFParser p=new JDFParser();
        JDFDoc d=p.parseFile(sm_dirTestData+strJDFName);
        JDFNode n=d.getJDFRoot();
        n.sortChildren();
    }

    ///////////////////////////////////////////////////////
    public void testGetPredecessors()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.ProcessGroup);
        JDFNode n2=n.addJDFNode("ImageSetting");
        JDFResource r=n2.addResource("ExposedMedia", null, EnumUsage.Output, null, n, null, null);
        JDFNode n3=n.addJDFNode("ConventionalPrinting");
        n3.linkResource(r, EnumUsage.Input, null);
        assertTrue(n3.getPredecessors(true, true).contains(n2));
        assertTrue(n3.getPredecessors(true, false).contains(n2));
        assertTrue(n2.getPredecessors(false, true).contains(n3));
        assertTrue(n2.getPredecessors(false, false).contains(n3));
    }

    ///////////////////////////////////////////////////////
    
    public void testSortChildren()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.ProcessGroup);
        JDFResourcePool rp=n.appendResourcePool();
        KElement m2=rp.appendElement("Media");
        m2.setAttribute("ID","m2");
        KElement xm=rp.appendElement("ExposedMedia");
        xm.setAttribute("ID","xm2");
        KElement m1=rp.appendElement("Media");
        m1.setAttribute("ID","m1");
        JDFNode n2=n.addJDFNode("foo");
        n2.setID("n2");
        JDFNode n1=n.addJDFNode("foo");
        n1.setID("n1");
        JDFResourcePool rp2=n2.appendResourcePool();
        KElement m22=rp2.appendElement("Media");
        m22.setAttribute("ID","m2");
        KElement xm2=rp2.appendElement("ExposedMedia");
        xm2.setAttribute("ID","xm2");
        KElement m21=rp2.appendElement("Media");
        m21.setAttribute("ID","m1");
        
        n.sortChildren();
        assertEquals("reordered sub elements",
                n.getChildWithAttribute("JDF", "ID", null, "n1", 0, true).getNextSiblingElement(null, null), 
                n.getChildWithAttribute("JDF", "ID", null, "n2", 0, true));
       
        assertEquals(rp2.getFirstChildElement(), xm2);
        assertEquals(xm2.getNextSiblingElement(), m21);
        assertEquals(m21.getNextSiblingElement(), m22);


    }
    public void testSetType()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        assertTrue("good Type",n.setType(JDFNode.EnumType.ConventionalPrinting.getName(),true));
        assertEquals("xsiType",n.getXSIType(),JDFNode.EnumType.ConventionalPrinting.getName());
        try
        {
            n.setType("badTypeName",true);
            assertFalse("bad type",true);
        }
        catch(JDFException e)
        {
            assertNotNull("bad type",e);
        }
        n.setType("foo:bar",false);
        assertEquals(n.getType(),"foo:bar");
        assertNull(n.getXSIType());
    }

    public void testLinkResourceNS()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        JDFResource rl= n.addResource("NS:RunList", EnumResourceClass.Parameter, null, null, null, "www.ns.com", null);
        assertEquals("res ns","www.ns.com",rl.getNamespaceURI());
        JDFResourceLink rll=n.linkResource(rl,EnumUsage.Input ,null);
        assertEquals("res ns","www.ns.com",rll.getNamespaceURI());
        assertFalse(rll.hasAttribute(AttributeName.COMBINEDPROCESSINDEX));
    }

    /**
     * test whether combinedprocessIndex is automagically and correctly assigned
     *
     */
    public void testInsertTypeInTypes()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.Combined);
        n.insertTypeInTypes(EnumType.Combine,999);
        assertEquals(n.getAttribute("Types"),"Combine");
        n.insertTypeInTypes(EnumType.Imposition,999);
        assertEquals(n.getAttribute("Types"),"Combine Imposition");
        n.insertTypeInTypes(EnumType.Stripping,1);
        assertEquals(n.getAttribute("Types"),"Combine Stripping Imposition");
        n.insertTypeInTypes(EnumType.DigitalDelivery,0);
        assertEquals(n.getAttribute("Types"),"DigitalDelivery Combine Stripping Imposition");
        n.insertTypeInTypes(EnumType.Imposition,99999);
        assertEquals(n.getAttribute("Types"),"DigitalDelivery Combine Stripping Imposition Imposition");

    }

    /**
     * test whether combinedprocessIndex is automagically and correctly assigned
     *
     */
    public void testLinkNamesCombined() throws Exception
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setCombined(new VString("Interpreting Trapping Rendering"," "));
        VString e=n.linkNames();
        assertTrue(e.contains(ElementName.CUSTOMERINFO));
        assertTrue(e.contains(ElementName.RENDERINGPARAMS));
    }    

    /**
     * test whether combinedprocessIndex is automagically and correctly assigned
     *
     */
    public void testLinkNamesProduct() throws Exception
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.Product);
        VString e=n.linkNames();
        assertTrue(e.contains(ElementName.CUSTOMERINFO));
        assertTrue(e.contains(ElementName.MEDIAINTENT));
        assertTrue(e.contains(ElementName.COMPONENT));
    }    

    /**
     * test whether combinedprocessIndex is automagically and correctly assigned
     *
     */
    public void testLinkResourceSimple() throws Exception
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.Folding);
        JDFResource foPa= n.addResource(ElementName.FOLDINGPARAMS, null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rlfoPa=n.getLink(foPa,null);
        assertFalse(rlfoPa.hasAttribute(AttributeName.COMBINEDPROCESSINDEX));
    }

    /**
     * test whether combinedprocessIndex is automagically and correctly assigned
     *
     */
    public void testLinkResourceCombined() throws Exception
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.Combined);
        VString v=new VString("Folding Cutting Foo:Bar Folding"," ");
        n.setTypes(v);
        JDFResource foPa= n.addResource(ElementName.FOLDINGPARAMS, null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rlfoPa=n.getLink(foPa,null);
        assertEquals("folding is 0 and 2",rlfoPa.getCombinedProcessIndex().toString(),"0 3");
        
        JDFResource cuPa= n.addResource(ElementName.CUTTINGPARAMS, null, null, null, null, null, null);
        JDFResourceLink rlCuPa=n.linkResource(cuPa,EnumUsage.Input ,null);
        assertEquals("cutting is 1",rlCuPa.getCombinedProcessIndex().toString(),"1");

        JDFResource pePa= n.addResource(ElementName.PERFORATINGPARAMS, null, null, null, null, null, null);
        JDFResourceLink rlPePa=n.linkResource(pePa, EnumUsage.Input,null);
        assertNull("no perforating",rlPePa.getCombinedProcessIndex());

        n.insertTypeInTypes(EnumType.AdhesiveBinding,999);
        assertEquals("folding is 0 and 2",rlfoPa.getCombinedProcessIndex().toString(),"0 3");
        assertEquals("cutting is 1",rlCuPa.getCombinedProcessIndex().toString(),"1");
        v=n.getTypes();
        assertEquals("appended one type",v.elementAt(4),EnumType.AdhesiveBinding.getName());
        assertEquals("appended one type",v.size(),5);

        n.insertTypeInTypes(EnumType.Bundling,-2);
        assertEquals("folding is 0 and 3",rlfoPa.getCombinedProcessIndex().toString(),"0 4");
        assertEquals("cutting is 1",rlCuPa.getCombinedProcessIndex().toString(),"1");
        v=n.getTypes();
        assertEquals("appended one type",v.elementAt(3),EnumType.Bundling.getName());
        assertEquals("appended one type",v.size(),6);

        n.insertTypeInTypes(EnumType.BoxPacking,0);
        assertEquals("folding is 1 and 5",rlfoPa.getCombinedProcessIndex().toString(),"1 5");
        assertEquals("cutting is 2",rlCuPa.getCombinedProcessIndex().toString(),"2");
        v=n.getTypes();
        assertEquals("appended one type",v.elementAt(0),EnumType.BoxPacking.getName());
        assertEquals("appended one type",v.elementAt(1),EnumType.Folding.getName());
        assertEquals("appended one type",v.size(),7);


        JDFResource comp= n.addResource(ElementName.COMPONENT, null, null, null, null, null, null);
        JDFResourceLink rlcomp=n.linkResource(comp,EnumUsage.Output,null);
        assertEquals("cpi output",rlcomp.getCombinedProcessIndex(),new JDFIntegerList("2 6"));

        JDFResource compIn= n.addResource(ElementName.COMPONENT, null, null, null, null, null, null);
        JDFResourceLink rlcompIn=n.linkResource(compIn,EnumUsage.Input,null);
        assertEquals("cpi output",rlcompIn.getCombinedProcessIndex(),new JDFIntegerList("0 4"));

    }

    ///////////////////////////////////////////////////////////////////////////


    public void testAddResource()
    {
        JDFDoc doc=new JDFDoc("JDF");
        JDFNode mainNode = doc.getJDFRoot();
        mainNode.setType("Product",true);
        JDFComponent myComponent = (JDFComponent) mainNode.addResource(ElementName.COMPONENT, JDFResource.EnumResourceClass.Quantity, EnumUsage.Output, null, mainNode, null, null);
        myComponent.setDescriptiveName("descriptive_name");
        assertNotNull("",mainNode.getMatchingResource(ElementName.COMPONENT,EnumProcessUsage.AnyOutput,null,0));
        JDFResource myRes =  mainNode.addResource("whatever:foo", JDFResource.EnumResourceClass.Quantity, EnumUsage.Output, null, mainNode, "www.whatever.com", null);
        myRes.setDescriptiveName("descriptive_name");
    }   
    ///////////////////////////////////////////////////////////////

    public void testAddInternalPipe()
    {
        JDFDoc doc=new JDFDoc("JDF");
        JDFNode mainNode = doc.getJDFRoot();
        mainNode.setType("Combined",true);
        mainNode.setTypes(new VString("Cutting Folding Folding Inserting"," "));
        JDFComponent myComponent = (JDFComponent) mainNode.addInternalPipe(ElementName.COMPONENT,3,0);
        assertNotNull(myComponent);
        assertEquals(myComponent.getPipeProtocol(),"Internal");
        VElement vE=myComponent.getLinks(myComponent.getLinkString(),null);
        assertEquals(vE.size(),2);
        assertEquals(((JDFResourceLink)vE.elementAt(0)).getPipeProtocol(),"Internal");
        assertEquals(((JDFResourceLink)vE.elementAt(1)).getPipeProtocol(),"Internal");
    }   

    /////////////////////////////////////////////////////////

    public void testCloneResourceToModify()
    {
        JDFDoc d=JDFResourceTest.creatXMDoc();
        JDFNode n=d.getJDFRoot();
        JDFResourceLink rl = n.getMatchingLink(ElementName.EXPOSEDMEDIA,EnumProcessUsage.AnyInput,0);
        JDFAttributeMap m =new JDFAttributeMap();
        m.put("SignatureName","Sig1");
        rl.setPartMap(m);
        rl.setAmount(42,m);
        JDFResourceAudit ra=n.cloneResourceToModify(rl);
        assertTrue("link",ra.getNewLink().hasChildElement("Part",null));
        assertTrue("link",ra.getOldLink().hasChildElement("Part",null));
        assertTrue("link",ra.getNewLink().hasChildElement("AmountPool",null));
        assertTrue("link",ra.getOldLink().hasChildElement("AmountPool",null));       
    }

    public void testEraseEmptyAttributes()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        JDFResource r = n.addResource("fooRes", EnumResourceClass.Consumable, EnumUsage.Input, EnumProcessUsage.AnyInput, null, "", null);
        r.appendAttribute("bar", "", "", "", false);
        n.eraseEmptyAttributes(true);
        assertFalse(n.hasAttribute("bar"));
    }


    public void testEraseEmptyNodes()
    {
        // note: when using JDFParser.parse(string), empty nodes are removed by default
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        JDFResource r = n.addResource("_foo", EnumResourceClass.Consumable, EnumUsage.Input, EnumProcessUsage.AnyInput, null, "", null);
        n.removeResource("_foo", 0);
        n.eraseEmptyNodes(true);       
        assertNotNull(n.getResourcePool());
        assertNotNull(n.getResourceLinkPool());
    }  
    
    public void testEraseUnlinkedResources()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        JDFResource r=n.addResource("Component", null, null, null, null, null, null);
        JDFResourcePool rp=n.getResourcePool();
        assertTrue(r instanceof JDFComponent);
        assertFalse(n.hasChildElement("ResourceLinkPool",null));
        JDFResourceLinkPool rlp=n.getCreateResourceLinkPool();
        assertEquals(rp.getUnlinkedResources().elementAt(0), r);

        JDFResourceLink rl=rlp.linkResource(r,EnumUsage.Input,EnumProcessUsage.BookBlock);
        assertNotNull(rl);
        assertNull(rp.getUnlinkedResources());
        JDFResource rx=n.addResource("ExposedMedia", null, null, null, null, null, null);
        assertEquals(rp.getUnlinkedResources().elementAt(0), rx);
        
        n.setVersion(EnumVersion.Version_1_2);
        JDFCustomerInfo ci=n.appendCustomerInfo();
        JDFContact co=ci.appendContact();
        co=(JDFContact)co.makeRootResource(null, null, true);
        assertEquals(rp.getUnlinkedResources().elementAt(0), rx);
        assertEquals(rp.getUnlinkedResources().size(), 1);
        n.eraseUnlinkedResources();
        assertNull(rp.getUnlinkedResources());
        assertNull("didn't zapp unlinked xm",rp.getElement("ExposedMedia"));
        assertEquals(rp.getElement("Contact"), co);
        
        ci.deleteNode();
        assertEquals("referenced contact accidentally tzapped",rp.getUnlinkedResources().elementAt(0), co);
        n.eraseUnlinkedResources();
        assertNull("didn't zapp unlinked co",rp.getElement("Contact"));
    }

    //////////////////////////////////////////////////////////

    public void testResourceAudit()
    {
        JDFDoc gd = new JDFDoc("JDF");
        JDFNode n=gd.getJDFRoot();

        JDFRunList rl=(JDFRunList)n.addResource("RunList", null, EnumUsage.Input, null, null, null, null);
        n.setType("Product",false);
        n.setStatus(EnumNodeStatus.Waiting);
        JDFResourceLink rli=n.getLink(rl,null);
        JDFResourceAudit ra=n.cloneResourceToModify(rli);
        // messy comment insertion to test getNewOldLink with Comments
        ra.insertBefore(ElementName.COMMENT,ra.getOldLink(),null);
        ra.appendComment();
        assertTrue("Comment after NewLink",ra.getComment(0)==ra.getNewLink().getNextSiblingElement());
        assertTrue("Comment before OldLink",ra.getComment(0).getNextSiblingElement()==ra.getOldLink());
        assertTrue("Comment after OldLink",ra.getComment(1)==ra.getOldLink().getNextSiblingElement());


        assertTrue("resaudit children=2",ra.numChildElements("RunListLink",null)==2);
        assertTrue("respool children=2",n.getResourcePool().numChildElements("RunList",null)==2);
        assertTrue("reslink children=1",n.getResourceLinkPool().numChildElements("RunListLink",null)==1);
        assertTrue("reslink audit1=pool",((JDFResourceLink)n.getResourceLinkPool().getElement("RunListLink",null,0)).getrRef().equals(ra.getNewLink().getrRef()));
        assertTrue("id names",(ra.getNewLink().getrRef()+"_old_001").equals(ra.getOldLink().getrRef()));
        assertTrue("old lock",(ra.getOldLink().getTarget()).getLocked());
        JDFResourceAudit ra2=n.getAuditPool().addResourceAudit("foo");
        ra2.addNewOldLink(true,rl,EnumUsage.Input);
        ra2.appendComment();
        ra2.addNewOldLink(false,rl,EnumUsage.Input).setDescriptiveName("foo");
        assertTrue("addnewlink",ra2.getOldLink().getDescriptiveName().equals("foo"));
        assertTrue("audit valid with Link",ra2.isValid(EnumValidationLevel.Complete));
        ra2.copyElement(rl,null);
        assertFalse("audit invalid with resource",ra2.isValid(EnumValidationLevel.Complete));
    }

    /**
     * test setPartStatus with null maps
     *
     */
    public void testSetPartStatusNull() throws Exception
    {
        JDFDoc doc   = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setVersion(JDFElement.EnumVersion.Version_1_3);
        root.setPartStatus((JDFAttributeMap)null, EnumNodeStatus.Waiting);
        JDFNodeInfo ni=root.getNodeInfo();
        assertNull(ni);
        assertNull(root.getStatusPool());
        assertEquals(root.getStatus(), EnumNodeStatus.Waiting);

        root.setVersion(JDFElement.EnumVersion.Version_1_2);
        root.setPartStatus((JDFAttributeMap)null, EnumNodeStatus.Completed);
        ni=root.getNodeInfo();
        assertNull(ni);
        assertEquals(root.getStatus(), EnumNodeStatus.Completed);
        assertNull(root.getStatusPool());
    }   
    /**
     * 
     *
     */
    public void testSetPartStatusNotNull() throws Exception
    {
        JDFDoc doc   = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setVersion(JDFElement.EnumVersion.Version_1_3);
        final JDFAttributeMap pm = new JDFAttributeMap(EnumPartIDKey.SheetName.getName(),"s1");
        root.setPartStatus(pm, EnumNodeStatus.Waiting);
        JDFNodeInfo ni=(JDFNodeInfo) root.getNodeInfo().getPartition(pm, null);
        assertNotNull(ni);
        assertNull(root.getStatusPool());
        assertEquals(root.getStatus(), EnumNodeStatus.Part);
        assertEquals(root.getNodeInfo().getNodeStatus(), EnumNodeStatus.Waiting);
        assertEquals(ni.getNodeStatus(), EnumNodeStatus.Waiting);

        root.setVersion(JDFElement.EnumVersion.Version_1_2);
        root.setPartStatus(pm, EnumNodeStatus.Completed);
        ni=root.getNodeInfo();
        assertEquals(root.getStatus(), EnumNodeStatus.Pool);
        assertNotNull(root.getStatusPool());
        assertNotNull(root.getStatusPool().getPartStatus(pm));
        assertEquals(root.getStatusPool().getPartStatus(pm).getStatus(), EnumNodeStatus.Completed);
    }
    /**
     * 
     *
     */
    public void testSetPhase() throws Exception
    {
        JDFDoc doc   = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setVersion(JDFElement.EnumVersion.Version_1_3);
        root.setJobID("jID");
        root.setJobPartID("jpID");
        JDFNodeInfo nodeInfo = root.getCreateNodeInfo();
        nodeInfo.setAgentName("myAgent");
        JDFDoc docJMF=root.setPhase(EnumNodeStatus.Waiting,"Queued",EnumDeviceStatus.Idle,null,null);
        JDFJMF jmf = docJMF.getJMFRoot();
        assertNotNull(jmf);
        assertEquals(jmf.numChildElements(ElementName.SIGNAL,null),1);
        JDFAuditPool ap=root.getAuditPool();
        JDFPhaseTime pt= ap.getLastPhase(null);
        assertEquals(pt.getStatus(),EnumNodeStatus.Waiting);
        assertEquals(root.getPartStatus(null),EnumNodeStatus.Waiting);
        JDFAttributeMap map=new JDFAttributeMap("SheetName","S1");
        VJDFAttributeMap vMap=new VJDFAttributeMap();
        vMap.add(map);
        docJMF.write2File(sm_dirTestDataTemp+File.separator+"queued.jmf",2,true);
        Thread.sleep(1000);

        docJMF=root.setPhase(EnumNodeStatus.Setup,"Setup",EnumDeviceStatus.Setup,null,vMap);
        pt= ap.getLastPhase(vMap);
        assertEquals(pt.getStatus(),EnumNodeStatus.Setup);
        assertEquals(root.getPartStatus(map),EnumNodeStatus.Setup);
        assertEquals(pt.getPartMapVector(),vMap);
        jmf = docJMF.getJMFRoot();
        assertNotNull(jmf);
        assertEquals(jmf.numChildElements(ElementName.SIGNAL,null),1);
        docJMF.write2File(sm_dirTestDataTemp+File.separator+"setup.jmf",2,true);        
        Thread.sleep(1000);

        docJMF=root.setPhase(EnumNodeStatus.InProgress,"Run",EnumDeviceStatus.Running,null,vMap);
        pt=(JDFPhaseTime) ap.getAudit(-1, EnumAuditType.PhaseTime, null,null);
        assertEquals(pt.getStatus(),EnumNodeStatus.InProgress);
        assertEquals(root.getPartStatus(map),EnumNodeStatus.InProgress);
        assertEquals(pt.getPartMapVector(),vMap);
        jmf = docJMF.getJMFRoot();
        assertNotNull(jmf);
        assertEquals(jmf.numChildElements(ElementName.SIGNAL,null),2);        docJMF.write2File(sm_dirTestDataTemp+File.separator+"inprogress.jmf",2,true);
        Thread.sleep(1000);

        docJMF=root.setPhase(EnumNodeStatus.InProgress,"Run",EnumDeviceStatus.Running,null,vMap);
        pt=(JDFPhaseTime) ap.getAudit(-1, EnumAuditType.PhaseTime, null,null);
        assertEquals(pt.getStatus(),EnumNodeStatus.InProgress);
        assertEquals(root.getPartStatus(map),EnumNodeStatus.InProgress);
        assertEquals(pt.getPartMapVector(),vMap);
        jmf = docJMF.getJMFRoot();
        assertNotNull(jmf);
        assertEquals(jmf.numChildElements(ElementName.SIGNAL,null),1);        docJMF.write2File(sm_dirTestDataTemp+File.separator+"inprogress2.jmf",2,true);

        root.getCreateAncestorPool().setPartMapVector(vMap);
        docJMF=root.setPhase(EnumNodeStatus.InProgress,"Run",EnumDeviceStatus.Running,null,null);
        pt=(JDFPhaseTime) ap.getAudit(-1, EnumAuditType.PhaseTime, null,null);
        assertEquals(pt.getStatus(),EnumNodeStatus.InProgress);
        assertEquals(root.getPartStatus(map),EnumNodeStatus.InProgress);
        assertEquals(pt.getPartMapVector(),vMap);
        jmf = docJMF.getJMFRoot();
        assertNotNull(jmf);
        assertEquals(jmf.numChildElements(ElementName.SIGNAL,null),1);        docJMF.write2File(sm_dirTestDataTemp+File.separator+"inprogress2.jmf",2,true);

    }
    /**
     * 
     *
     */
    public void testGetCreateNodeInfo()
    {
        JDFDoc doc   = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setVersion(JDFElement.EnumVersion.Version_1_3);
        JDFNodeInfo nodeInfo = root.getCreateNodeInfo();
        JDFNodeInfo nodeInfo2 = root.getCreateNodeInfo();
        assertTrue (nodeInfo==nodeInfo2);


        root.setType(JDFNode.EnumType.Product.getName(), false);

        JDFNode nodeVer3 = root.addJDFNode("Version 1.3");
        JDFNode nodeVer1 = root.addJDFNode("Version 1.1");
        nodeVer1.setType("ProcessGroup",false);
        nodeVer3.setType("ProcessGroup",false);

        nodeVer3.setVersion(JDFElement.EnumVersion.Version_1_3);
        nodeVer1.setVersion(JDFElement.EnumVersion.Version_1_1);

        root.setVersion(EnumVersion.Version_1_3); 
        //append some NodeInfos

        //try to append the other NodeInfo (only 1 is valid)
        nodeVer1.appendNodeInfo();
        nodeVer3.appendNodeInfo();
        JDFNode subNodeVer1=nodeVer1.addJDFNode("Folding");
        JDFNode subNodeVer3=nodeVer3.addJDFNode("Folding");

        boolean cat=false;
        try
        {
            nodeVer1.appendNodeInfo();
        }
        catch(JDFException e)
        {
            cat=true;
            //nodeinfo has a cardinality of 1 in V1.1
            //if we try to append another NodeInfo
            //
        }
        assertTrue(cat);
        cat=false;
        try
        {
            nodeVer3.appendNodeInfo();
        }
        catch(JDFException e)
        {
            //nodeinfo has a cardinality of 1 in V1.1
            //if we try to append another NodeInfo
            cat=true;
        }
        assertTrue(cat);
        cat=false;
        try
        {
            nodeVer3.appendNodeInfo();
        }
        catch(JDFException e)
        {
            //nodeinfo has a cardinality of 1 in V1.1
            //if we try to append another NodeInfo
            cat=true;
        }

        assertTrue(cat);
        assertNotNull(nodeVer1.getNodeInfo());
        assertNotNull(nodeVer3.getNodeInfo());

        assertNull(subNodeVer1.getNodeInfo());
        assertNull(subNodeVer3.getNodeInfo());

        assertNotNull(subNodeVer1.getInheritedNodeInfo(null));
        assertNotNull(subNodeVer3.getInheritedNodeInfo(null));

        //remove them all
        nodeVer3.removeNodeInfo();
        assertNull(nodeVer3.getNodeInfo());
        nodeVer1.removeNodeInfo();
        assertNull(nodeVer1.getNodeInfo());


        doc.write2File(sm_dirTestDataTemp+"getCreateNodeInfo.xml", 0, true);
    }

    /**
     * Method testGetExecutablePartitionsPreflightImport
     * 
     * @throws Exception
     */

    public void testGetExecutablePartitionsPreflightImport ()
    throws Exception
    {
        String strJDFName = "PreflightImport1.jdf";

        JDFParser parser = new JDFParser ();

        JDFDoc jdfDoc = parser.parseFile (sm_dirTestData+strJDFName);

        JDFNode nodeProc = jdfDoc.getJDFRoot ().getJobPart ("Qua0.P", JDFConstants.EMPTYSTRING);

        final JDFResourceLinkPool linkPool = nodeProc.getResourceLinkPool ();

        final VElement vRunListLinks = linkPool.getPoolChildren("RunListLink", null, null);
        if (vRunListLinks != null)
        {
            JDFResourceLink link = (JDFResourceLink) vRunListLinks.get (0);
    
            JDFResource res = link.getLinkRoot ();
    
            VJDFAttributeMap vamExec = nodeProc.getExecutablePartitions (link, res, JDFResource.EnumResStatus.Draft);
    
            assertTrue ("Size of vamExec must be 2", vamExec.size() == 2);
    
            JDFAttributeMap am0 = vamExec.elementAt (0);
    
            assertTrue ("Size of vamExec[0] must be 1", am0.size() == 1);
            assertTrue (am0.containsKey   ("Run"));
            assertTrue (am0.containsValue ("Chf06181149500001"));
    
            JDFAttributeMap am1 = vamExec.elementAt (1);
    
            assertTrue ("Size of vamExec[1] must be 1", am1.size() == 1);
            assertTrue (am1.containsKey   ("Run"));
            assertTrue (am1.containsValue ("Chf06181154470000"));
        }
    }

    /**
     * Method testGetExecutablePartitionsNormalizer
     * 
     * @throws Exception
     */

    public void testGetExecutablePartitionsNormalizer ()
    throws Exception
    {
        String strJDFName = "Normalizer.jdf";

        JDFParser parser = new JDFParser ();

        JDFDoc jdfDoc = parser.parseFile (sm_dirTestData+strJDFName);

        JDFNode nodeProc = jdfDoc.getJDFRoot ().getJobPart ("Qua0.N", JDFConstants.EMPTYSTRING);

        final JDFResourceLinkPool linkPool = nodeProc.getResourceLinkPool ();

        final VElement vRunListLinks = linkPool.getPoolChildren("RunListLink", null, null);
        if (vRunListLinks != null)
        {
            JDFResourceLink link = (JDFResourceLink) vRunListLinks.get (0);
    
            JDFResource res = link.getLinkRoot ();
    
            VJDFAttributeMap vamExec = nodeProc.getExecutablePartitions (link, res, JDFResource.EnumResStatus.Draft);
    
            assertTrue ("Size of vamExec must be 2", vamExec.size() == 2);
    
            JDFAttributeMap am0 = vamExec.elementAt (0);
    
            assertTrue ("Size of vamExec[0] must be 1", am0.size() == 1);
            assertTrue (am0.containsKey   ("Run"));
            assertTrue (am0.containsValue ("Run93379_000255"));
    
            JDFAttributeMap am1 = vamExec.elementAt (1);
    
            assertTrue ("Size of vamExec[1] must be 0", am1.size() == 0);
        }
    }


    public void testNullPointerException()
    {
        List LcleanUpMerge = JDFNode.EnumCleanUpMerge.getEnumList();
        List LamountMerge  = JDFResource.EnumAmountMerge.getEnumList();

        for(int i = 0; i < LcleanUpMerge.size(); i ++)
        {
            EnumCleanUpMerge cleanUp = (EnumCleanUpMerge)LcleanUpMerge.get(i); 

            for(int j = 0; j < LcleanUpMerge.size(); j ++)
            {
                EnumAmountMerge amountMerge = (EnumAmountMerge)LamountMerge.get(j);

                String xmlFile1 = "km4444.jdf";
                String xmlFile2 = "Link33458670_000214km4444Qua0.NSp33486069_000371_37_out.jdf";
                String outFile  = "km4444_merged.xml";

                JDFParser p = new JDFParser();
                JDFDoc m_jdfDoc = p.parseFile(sm_dirTestData+xmlFile1);
                m_jdfDoc.getCreateXMLDocUserData();
                JDFParser p2 = new JDFParser();
                JDFDoc m_jdfDoc2 = p2.parseFile(sm_dirTestData+xmlFile2);
                m_jdfDoc2.getCreateXMLDocUserData();

                JDFNode root = (JDFNode) m_jdfDoc.getRoot();
                assertNotNull(root);
                JDFNode root2 = (JDFNode) m_jdfDoc2.getRoot();
                assertNotNull(root2);
                if (root == null)
                    return; // soothe findbugs ;)
                new JDFMerge(root).mergeJDF(root2, sm_dirTestData+xmlFile2, cleanUp, amountMerge);

                m_jdfDoc.write2File(sm_dirTestDataTemp + outFile + i + j, 2, true);
            }
        }
    }

    //////////////////////////////////////////////

    public void testInit()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        assertNotNull(node.getStatus());
        assertFalse(node.getID().equals(""));
        node.init();
        node.init();
        JDFAuditPool ap=node.getAuditPool();
        assertNotNull(ap);
        assertEquals(ap.numChildElements(ElementName.CREATED, null), 1);
    }

    //////////////////////////////////////////////

    public void testIsValid()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        node.setType(EnumType.ProcessGroup);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
        node.removeAttribute(AttributeName.JOBPARTID,null);
        assertTrue("isvalid does not require jpid",node.isValid(EnumValidationLevel.Complete));
    }

    //////////////////////////////////////////////

    public void testIsValidCombined()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        node.setType(EnumType.Combined);
        assertFalse("need typed for combined",node.isValid(EnumValidationLevel.Complete));
    }

    public void testIsGroupNode()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        node.setType(EnumType.Product);
        assertTrue(node.isGroupNode());
        node.setType(EnumType.ProcessGroup);
        assertTrue(node.isGroupNode());
        node.setTypes(new VString("foo"," "));
        assertFalse(node.isGroupNode());
        node.setType(EnumType.Combined);
        assertFalse(node.isGroupNode());
        node.setType(EnumType.ConventionalPrinting);
        assertFalse(node.isGroupNode());
     }    
    
    //////////////////////////////////////////////

    public void testIsExecutable()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        node.setType("ConventionalPrinting",true);
        node.setVersion(JDFElement.EnumVersion.Version_1_3);
        node.setStatus(EnumNodeStatus.Ready);

        // simple non-partitioned case
        JDFNodeInfo n=node.appendNodeInfo();
        assertTrue("ni resource",n.hasAttribute(AttributeName.CLASS));
        JDFConventionalPrintingParams convPrintParams=(JDFConventionalPrintingParams) node.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,EnumProcessUsage.AnyInput,null);
        convPrintParams.setResStatus(EnumResStatus.Available,true);
        JDFComponent outComp= (JDFComponent) node.appendMatchingResource(ElementName.COMPONENT,EnumProcessUsage.AnyOutput,null);
        outComp.setResStatus(EnumResStatus.Unavailable,true);
        JDFExposedMedia xm= (JDFExposedMedia) node.appendMatchingResource(ElementName.EXPOSEDMEDIA,EnumProcessUsage.AnyInput,null);
        xm.setResStatus(EnumResStatus.Unavailable,true);
        JDFMedia media= (JDFMedia) node.appendMatchingResource(ElementName.MEDIA,EnumProcessUsage.AnyInput,null);
        media.setResStatus(EnumResStatus.Available,true);
        boolean exec=node.isExecutable(null,false);
        assertFalse("not exec",exec);
        xm.setResStatus(EnumResStatus.Available,true);
        exec=node.isExecutable(null,false);
        assertTrue("exec",exec);
        node.setStatus(EnumNodeStatus.Completed);
        exec=node.isExecutable(null,false);
        assertFalse("exec",exec);

        // now a partition
        convPrintParams.setPartUsage(EnumPartUsage.Implicit);
        media.setPartUsage(EnumPartUsage.Implicit);
        xm=(JDFExposedMedia) xm.addPartition(EnumPartIDKey.SignatureName,"sig1");
        xm.setResStatus(EnumResStatus.Unavailable,true);
        exec=node.isExecutable(null,false);
        assertFalse("part not exec",exec);
        xm.setResStatus(EnumResStatus.Available,true);
        exec=node.isExecutable(null,false);
        assertFalse("part exec",exec);
        final JDFAttributeMap partMap = new JDFAttributeMap("SignatureName","sig1");
        node.setPartStatus(partMap,EnumNodeStatus.Waiting);

        outComp.addPartition(EnumPartIDKey.SignatureName,"sig1");
        exec=node.isExecutable(partMap,false);
        assertTrue("part exec",exec);


        // the root is set to completed --> must fail
        exec=node.isExecutable(null,false);
        assertFalse("part exec",exec);

        // now try a non existing partition - should fail
        partMap.put("SignatureName","sig2");
        exec=node.isExecutable(partMap,false);

        assertFalse("part exec",exec);

    }

    /////////////////////////////////////////////////////

    public void testGetInheritedNodeInfo()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        JDFNodeInfo n = node.appendNodeInfo();
        try
        {
            node.appendNodeInfo();
            fail("one ni is enough");
        }
        catch (JDFException ex)
        {
            // nop
        }
        node.setType("ProcessGroup",true);
        JDFNode node2=node.addProcessGroup(null);
        JDFNode node3=node2.addJDFNode(JDFNode.EnumType.CaseMaking);
        JDFAncestor an=node.appendAncestorPool().appendAncestor();
        JDFCustomerInfo ciAN=an.appendCustomerInfo();
        JDFNodeInfo niAN=an.appendNodeInfo();
        JDFJMF jmf=niAN.appendJMF();
        VString vs=new VString();
        vs.add("ICS_Foo");
        jmf.setICSVersions(vs);

        assertNull(node2.getNodeInfo());
        assertNull(node2.getInheritedNodeInfo("MISDetails"));
        assertNull(node2.getInheritedNodeInfo("JMF/@DeviceID"));
        assertEquals(node2.getInheritedNodeInfo("JMF/@ICSVersions"),niAN);
        assertEquals(node.getInheritedNodeInfo(null),n);
        assertEquals(node2.getInheritedNodeInfo(null),n);
        assertEquals(node3.getInheritedNodeInfo(null),n);
        assertEquals(node3.getInheritedCustomerInfo(null),ciAN);

    }

    //////////////////////////////////////////////////////////////////////

    public void testCreateNodeInfo()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode node = doc.getJDFRoot();
        JDFNodeInfo n = node.appendNodeInfo();
        try
        {
            node.appendNodeInfo();
            fail("one ni is enough");
        }
        catch (JDFException ex)
        {
            // nop
        }
//      System.out.println(n.version());
        assertTrue("nodeinfo is resource",n.getResourceClass()==EnumResourceClass.Parameter);
        doc.write2File(sm_dirTestDataTemp + "createNodeInfoTest.xml", 0, true);
        JDFCustomerInfo myCustInfo = node.getCreateCustomerInfo(); 
        JDFContact myContact =myCustInfo.appendContact();
        assertTrue("contact is res", myContact.getResourceClass()==EnumResourceClass.Parameter);
        assertNotNull(node.getNodeInfo());
        node.removeNodeInfo();
        assertNull(node.getNodeInfo());

    }
    ///////////////////////////////////////////////////////////////

    public void testGetvJDFNode()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType(EnumType.ProcessGroup);
        JDFNode n1=n.addProcessGroup(null);
        JDFNode n2=n.addProcessGroup(null);
        JDFNode n11=n1.addProcessGroup(null);
        assertEquals(n.getvJDFNode(null,null,true).size(),2);
        assertEquals(n1.getvJDFNode(null,null,true).size(),1);
        assertEquals(n2.getvJDFNode(null,null,false).size(),1);
        assertEquals(n1.getvJDFNode(null,null,false).size(),2);
        assertEquals(n.getvJDFNode(null,null,true).elementAt(0),n1);
        assertEquals(n.getvJDFNode(null,null,false).elementAt(3),n);
        assertEquals(n1.getvJDFNode(null,null,true).elementAt(0),n11);
        assertEquals(n1.getvJDFNode(null,null,false).elementAt(1),n1);

    }
    ///////////////////////////////////////////////////////////////

    public void testGetPartStatus()
    {
        JDFDoc doc = JDFResourceTest.creatXMDoc();
        JDFNode node = doc.getJDFRoot();
        JDFNodeInfo ni=node.getNodeInfo();
        assertTrue("nodeinfo is resource",ni.getResourceClass()==EnumResourceClass.Parameter);
        node.setPartStatus((JDFAttributeMap)null,EnumNodeStatus.Waiting);
        assertTrue("ni root waiting",node.getPartStatus(null)==EnumNodeStatus.Waiting);
        JDFAttributeMap m=new JDFAttributeMap("SignatureName","Sig1");
        node.setPartStatus(m,EnumNodeStatus.Completed);
        assertTrue("ni sig1 completed",node.getPartStatus(m)==EnumNodeStatus.Completed);
        assertNull("ni root mixed",node.getPartStatus(null));
        JDFAttributeMap m3=new JDFAttributeMap("SignatureName","Sig2");
        assertTrue("ni sig2 waiting",node.getPartStatus(m3)==EnumNodeStatus.Waiting);

        m.put("SheetName","S1");
        m.put("Side","Front");  
        node.setPartStatus(m,EnumNodeStatus.Waiting);
        assertTrue("ni sig1 waiting",node.getPartStatus(m)==EnumNodeStatus.Waiting);

        JDFAttributeMap m2=new JDFAttributeMap("SignatureName","Sig1");
        assertNull("ni sig1 mixed",node.getPartStatus(m2));

        JDFAttributeMap m4=new JDFAttributeMap("SignatureName","Sig3");
        m4.put("SheetName","S1");
        VJDFAttributeMap v=new VJDFAttributeMap();
        v.add(m4);
        node.prepareNodeInfo(v);
        assertTrue("ni sig3 waiting",node.getPartStatus(m4)==EnumNodeStatus.Waiting);
        assertNotNull("explicit m4",ni.getPartition(m4,EnumPartUsage.Explicit));
    }

    //////////////////////////////////////////////////////////

    public void testGenericResources()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType("ConventionalPrinting",true);
        JDFDevice dev=(JDFDevice)n.appendMatchingResource(ElementName.DEVICE,EnumProcessUsage.AnyInput,null);
        dev.setDeviceID("mydev");
        assertTrue("valid device",dev.isValid(EnumValidationLevel.Complete));
        JDFModule m=dev.appendModule();
        m.setModuleID("Foo");
        JDFEmployee emp=(JDFEmployee) n.appendMatchingResource(ElementName.EMPLOYEE, EnumProcessUsage.AnyInput,null);
        emp.setPersonalID("emp1");
        assertTrue("valid module",m.isValid(EnumValidationLevel.Complete));
        n.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,EnumProcessUsage.AnyInput,null);
        n.appendMatchingResource(ElementName.COMPONENT,EnumProcessUsage.AnyOutput,null);
        n.appendMatchingResource(ElementName.EXPOSEDMEDIA,EnumProcessUsage.Plate,null);
        
        assertTrue("valid node",n.isValid(EnumValidationLevel.Incomplete));
    }   

    //////////////////////////////////////////////////////////

    public void testProductValidation()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType("Product",true);
        JDFDevice dev=(JDFDevice)n.appendMatchingResource(ElementName.DEVICE,EnumProcessUsage.AnyInput,null);
        dev.setDeviceID("mydev");
        assertTrue("valid device",dev.isValid(EnumValidationLevel.Complete));
        JDFModule m=dev.appendModule();
        m.setModuleID("Foo");
        JDFEmployee emp=(JDFEmployee) n.appendMatchingResource(ElementName.EMPLOYEE, EnumProcessUsage.AnyInput,null);
        emp.setPersonalID("emp1");
        assertTrue("valid module",m.isValid(EnumValidationLevel.Complete));
    }   
    //////////////////////////////////////////////////////////

    public void testGrayBox()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setType("ProcessGroup",true);
        VString v=new VString();
        v.add("Interpreting");
        v.add("Rendering");
        n.setTypes(v);
        v=n.getInsertLinkVector(9999);
        assertTrue("interpretingParams",v.contains("InterpretingParamsLink:AnyInput"));
    }   


    public void testAppendMatchingResourceProduct()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setStatus(EnumNodeStatus.Ready);
        n.setType("Product",true);
        JDFComponent co=(JDFComponent) n.appendMatchingResource("Component",EnumProcessUsage.Cover,null);
        Vector vType=new Vector();
        vType.add(EnumComponentType.FinalProduct);
        vType.add(EnumComponentType.Block);
        co.setComponentType(vType);
        co=(JDFComponent)n.appendMatchingResource("Component",EnumProcessUsage.Cover,null);
        co.setComponentType(vType);
        co=(JDFComponent)n.appendMatchingResource("Component",EnumProcessUsage.AnyOutput,null);
        co.setComponentType(vType);
        assertTrue(n.isValid(EnumValidationLevel.Complete));

    }
    ///////////////////////////////////////////////////////////////////////////

    public void testAppendMatchingResourcePrivate()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setCombined(new VString("ConventionalPrinting fnarf ConventionalPrinting",null));
        JDFResource r=n.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,null,null);
        JDFResourceLink rl=n.getLink(r,null);
        assertNotNull("rl 1",rl);
        r=n.appendMatchingResource("FnarfParams",null,null);
        rl=n.getLink(r,null);
        assertNotNull("rl fnarf",rl);
        rl=n.getMatchingLink("FnarfParams",null,0);
        assertNotNull("rl fnarf",rl);
        assertEquals(n.numMatchingLinks("FnarfParams",true,null),1);
    }
    ///////////////////////////////////////////////////////////////////////////

    public void testAppendMatchingResource()
    {
        JDFDoc d=new JDFDoc("JDF");
        JDFNode n=d.getJDFRoot();
        n.setCombined(new VString("ConventionalPrinting ConventionalPrinting",null));
        JDFResource r=n.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,null,null);
        JDFResourceLink rl=n.getLink(r,null);
        assertNotNull("rl 1",rl);

        r=n.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,null,null);
        assertNotNull("r 2",r);
        rl=n.getLink(r,null);
        assertNotNull("rl 2",rl);
        assertEquals("rl usage",rl.getUsage(),EnumUsage.Input);

        try
        {
            r=n.appendMatchingResource(ElementName.CONVENTIONALPRINTINGPARAMS,null,null);
            fail("exception 3");
        }
        catch (JDFException e)
        {
            // nop
        }

        r=n.appendMatchingResource(ElementName.COMPONENT,null,null);
        assertNotNull("comp 1",r);
        rl=n.getLink(r,null);
        assertNull("complink 1",rl);
        rl=n.linkMatchingResource(r,EnumProcessUsage.Input,null);
        assertNotNull("rl 2",rl);
        assertEquals("rl usage",rl.getUsage(),EnumUsage.Input);
        assertEquals(rl.getProcessUsage(),"Input");

        r=n.appendMatchingResource(ElementName.COMPONENT,EnumProcessUsage.AnyOutput,null);
        assertNotNull("comp 2",r);
        rl=n.getLink(r,null);
        assertNotNull("complink 2",rl);
        assertEquals(rl.getProcessUsage(),"");

        n.setCombined(new VString("Collecting ConventionalPrinting",null));
        for(int cLoop=0;cLoop<5;cLoop++)
        {
            r=n.appendMatchingResource(ElementName.COMPONENT,EnumProcessUsage.AnyInput,null);
            assertNotNull("comp loop",r);
            rl=n.getLink(r,null);
            assertNotNull("complink 2",rl);
            assertEquals(rl.getProcessUsage(),"");
        }
    }    

    ///////////////////////////////////////////////////////////////

    public void testCheckSpawnedResources()
    throws Exception
    {
        final String strJDFName = "000023_Test_PR3.0.jdf";
        String strJDFPath = sm_dirTestData +  strJDFName;
        JDFParser parser = new JDFParser ();
        JDFDoc jdfDoc = parser.parseFile(strJDFPath);
//      jdfDoc.getCreateXMLDocUserData().setDirtyPolicy(EnumDirtyPolicy.None);
        VJDFAttributeMap vamParts = new VJDFAttributeMap ();
        JDFAttributeMap amParts0 = new JDFAttributeMap ();
        amParts0.put ("Side", "Front");
        amParts0.put ("SignatureName", "Sig002");
        amParts0.put ("SheetName", "FB 002");
        vamParts.add (amParts0);

        JDFAttributeMap amParts1 = new JDFAttributeMap ();
        amParts1.put ("Side", "Back");
        amParts1.put ("SignatureName", "Sig002");
        amParts1.put ("SheetName", "FB 002");
        vamParts.add (amParts1);

        VString vsRWResourceIDs = new VString ();
        vsRWResourceIDs.add ("Link84847227_000309");
        vsRWResourceIDs.add ("r85326439_027691");

        JDFNode nodeProc = jdfDoc.getJDFRoot ().getJobPart ("IPD0.I", JDFConstants.EMPTYSTRING);

        final Collection vSpawned = nodeProc.checkSpawnedResources (vsRWResourceIDs, vamParts);
        assertNull (vSpawned);
    }

    //////////////////////////////////////////////////////////////
    public void testGetParentJDF()
    {
        {
            JDFDoc d=new JDFDoc("JDF");
            JDFNode n=d.getJDFRoot();
            assertNull(n.getParentJDF());
            JDFNode n2=(JDFNode)n.appendElement("JDF");
            assertEquals(n,n2.getParentJDF());
        }
        {
            XMLDoc d=new XMLDoc("ns:foo","ns");
            KElement e=d.getRoot();
            JDFNode n=(JDFNode)e.appendElement("JDF");
            assertNull(n.getParentJDF());
            JDFNode n2=(JDFNode)n.appendElement("JDF");
            assertEquals(n,n2.getParentJDF());
        }        
    }

    //////////////////////////////////////////////////////////////
    public void testGetEnumTypes()
    {
        JDFDoc doc = new JDFDoc("JDF");
        JDFNode root=doc.getJDFRoot();
        root.setType("fnarf",false);
        assertNull(root.getEnumTypes());

        root.setType("ProcessGroup",true);

        root.setTypes(new VString("InkZoneCalculation ConventionalPrinting",null));
        assertEquals(root.getEnumTypes().elementAt(0),EnumType.InkZoneCalculation);
        assertEquals(root.getEnumTypes().elementAt(1),EnumType.ConventionalPrinting);
        assertEquals(root.getEnumTypes().size(),2);

        root.setTypes(new VString("InkZoneCalculation xyz:fnarf ConventionalPrinting",null));
        assertNull("contains extension",root.getEnumTypes());

    }
    //////////////////////////////////////////////////////////////
    public void testGetActivation()
    {
        JDFDoc doc = new JDFDoc("JDF");
        JDFNode root=doc.getJDFRoot();
        root.setType(EnumType.ProcessGroup);
        assertEquals(root.getActivation(true), EnumActivation.Active);
        assertNull(root.getActivation(false));
        VString types=new VString();
        types.add("foo");
        types.add("bar");
  
        JDFNode n2 =root.addCombined(types);
        assertEquals(n2.getActivation(true), EnumActivation.Active);
        assertNull(n2.getActivation(false));

        root.setActivation(EnumActivation.Inactive);
        assertEquals(root.getActivation(true), EnumActivation.Inactive);
        assertEquals(root.getActivation(false), EnumActivation.Inactive);
        assertEquals(n2.getActivation(true), EnumActivation.Inactive);
        assertNull(n2.getActivation(false));
        
        n2.setActivation(EnumActivation.Active);
        assertEquals(n2.getActivation(true), EnumActivation.Inactive);
        assertEquals(n2.getActivation(false), EnumActivation.Active);
    }
    
    //////////////////////////////////////////////////////////////
    
    public void testGetAllTypes()
    {
        JDFDoc doc = new JDFDoc("JDF");
        JDFNode root=doc.getJDFRoot();
        root.setType("fnarf",false);
        assertEquals(root.getAllTypes().stringAt(0),"fnarf");
        assertEquals(root.getAllTypes().size(),1);

        root.setType("Product",false);
        assertEquals(root.getAllTypes().stringAt(0),"Product");
        assertEquals(root.getAllTypes().size(),1);

        root.setType("ProcessGroup",false);
        assertNull(root.getAllTypes());

        VString types=new VString();
        types.add("foo");
        types.add("bar");
        root.setTypes(types);

        assertEquals(root.getAllTypes(),types);
        root.removeAttribute("Types");

        JDFNode n2 =root.addCombined(types);
        n2.setTypes(types);

        assertEquals(types,root.getAllTypes());
        assertEquals(types,n2.getAllTypes());

        root.addJDFNode("foobar");

        assertEquals(types,n2.getAllTypes());
        types.add("foobar");
        assertEquals(types,root.getAllTypes());

    }
    //////////////////////////////////////////////////////////////

    public void testGetMatchingResource()
    {
        JDFParser pars= new JDFParser();
        JDFDoc doc= pars.parseFile(sm_dirTestData+File.separator+"testMatchRes.jdf");
        JDFNode root = doc.getJDFRoot();
        Vector v= root.getvJDFNode("ProcessGroup",null,false);
        JDFNode ppnode= null;
        for(int i=0;i<v.size();i++)
        {
            JDFNode p= (JDFNode) v.elementAt(i);
            if (p.getCategory().equals("ContentCreation")) {
                ppnode=p;
                break;
            }
        } 
        assertNotNull(ppnode);
        assertTrue(ppnode.getTypes().contains(EnumType.LayoutElementProduction.getName()));
        JDFResource res= ppnode.getMatchingResource("RunList",JDFNode.EnumProcessUsage.AnyInput,null,0);
        assertEquals(res.getNodeName(),ElementName.RUNLIST);
        res= ppnode.getMatchingResource("LayoutElementProductionParams",JDFNode.EnumProcessUsage.AnyInput,null,0);
        assertEquals(res.getNodeName(),ElementName.LAYOUTELEMENTPRODUCTIONPARAMS);
    }    
    public void testGetJobPart()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.Product);
        root.setJobPartID("p0");
        JDFNode p1=root.addJDFNode(EnumType.Product);
        assertEquals(root.getJobPart("p0", null), root);
        assertEquals(root.getJobPart("p0.1", null), p1);
        JDFNode p11=p1.addJDFNode(EnumType.Product);
        assertEquals(root.getJobPart("p0.1.1", null), p11);
        assertEquals(p1.getJobPart("p0.1.1", null), p11);
     }
    
    public void testGetChildJDFNode()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.Product);
        root.setJobPartID("p0");
        root.setID("I1");
        JDFNode p1=root.addJDFNode(EnumType.Product);
        p1.setID("I2");
        assertEquals(root.getChildJDFNode("I1", false), root);
        assertEquals(root.getChildJDFNode("I2", false), p1);
        assertEquals(root.getChildJDFNode("I2", true), p1);
        JDFNode p11=p1.addJDFNode(EnumType.Product);
        p11.setID("I11");
        assertEquals(root.getChildJDFNode("I11", false), p11);
        assertNull(root.getChildJDFNode("I11", true));
        assertEquals(root.getChildJDFNode("I11", false), p11);
        assertEquals(p1.getChildJDFNode("I11", true), p11);
        assertEquals(p1.getChildJDFNode("I11", false), p11);
     }

    public void testGetLink()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.ImageSetting);
        {
        JDFResource r=root.addResource("foo:res", EnumResourceClass.Parameter, EnumUsage.Input, null, null, "www.foo.com", null);
        JDFResourceLink rl=root.getLink(r, null);
        assertNotNull(rl);
        }
        {
        JDFResource r=root.addResource(ElementName.MEDIA, null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rl=root.getLink(r, null);
        assertNotNull(rl);
        }
    }
   //////////////////////////////////////////////////////////////
    public void testGetResourceLinks()
    {
        JDFDoc doc = new JDFDoc(ElementName.JDF);
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.ImageSetting);
        VElement v=root.getResourceLinks(null);
        assertNull(v);
        JDFResource r=root.addResource("foo:res", EnumResourceClass.Parameter, EnumUsage.Input, null, null, "www.foo.com", null);
        v=root.getResourceLinks(null);
        assertEquals(v.size(),1);
        assertEquals(root.getResourceLinkPool().getElement(null), v.elementAt(0));
    }
   //////////////////////////////////////////////////////////////

    public void testGetLinksForType()
    {
        JDFDoc doc= new JDFDoc("JDF");
        JDFNode root = doc.getJDFRoot();

        root.setType(EnumType.Combined);
        root.setTypes(new VString("Cutting Folding Cutting"," "));


        JDFResource r1=root.addResource("CuttingParams", null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rl1=root.getLink(r1,null);
        rl1.setCombinedProcessIndex(new JDFIntegerList(0));

        JDFResource r2=root.addResource("FoldingParams", null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rl2=root.getLink(r2,null);
        rl2.setCombinedProcessIndex(new JDFIntegerList(1));

        JDFResource r3=root.addResource("CuttingParams", null, EnumUsage.Input, null, null, null, null);
        JDFResourceLink rl3=root.getLink(r3,null);
        rl3.setCombinedProcessIndex(new JDFIntegerList(2));

        JDFResource r4=root.addResource("Component", null, EnumUsage.Output, null, null, null, null);
        JDFResourceLink rl4=root.getLink(r4,null);

        VElement ve=root.getLinksForType(EnumType.Cutting,0);
        assertEquals(ve.size(),1);
        assertTrue(ve.contains(rl1));
        assertFalse(ve.contains(rl4));

        ve=root.getLinksForType(EnumType.Cutting,1);
        assertEquals(ve.size(),2);
        assertTrue(ve.contains(rl3));
        assertTrue(ve.contains(rl4));

        ve=root.getLinksForType(EnumType.Cutting,-1);
        assertEquals(ve.size(),3);
        assertTrue(ve.contains(rl1));
        assertTrue(ve.contains(rl3));
        assertTrue(ve.contains(rl4));

        ve=root.getLinksForType(EnumType.Folding,0);
        assertEquals(ve.size(),1);
        assertTrue(ve.contains(rl2));
        assertFalse(ve.contains(rl4));

        ve=root.getLinksForCombinedProcessIndex(0);
        assertEquals(ve.size(),1);
        assertTrue(ve.contains(rl1));
        assertFalse(ve.contains(rl4));

        ve=root.getLinksForCombinedProcessIndex(1);
        assertEquals(ve.size(),1);
        assertTrue(ve.contains(rl2));
        assertFalse(ve.contains(rl4));

        // now check whether this works with no cpi
        rl4.removeAttribute(AttributeName.COMBINEDPROCESSINDEX);
        ve=root.getLinksForType(EnumType.Folding,0);
        assertEquals(ve.size(),2);
        assertTrue(ve.contains(rl2));
        assertTrue(ve.contains(rl4));

        ve=root.getLinksForCombinedProcessIndex(0);
        assertEquals(ve.size(),2);
        assertTrue(ve.contains(rl1));
        assertTrue(ve.contains(rl4));

        ve=root.getLinksForCombinedProcessIndex(1);
        assertEquals(ve.size(),2);
        assertTrue(ve.contains(rl2));
        assertTrue(ve.contains(rl4));

    }
    //////////////////////////////////////////////////////////////

    public void testGetMatchingResourceStar()
    {
        JDFDoc doc= new JDFDoc("JDF");
        JDFNode root = doc.getJDFRoot();
        root.setType((EnumType.Combine));

        for(int i=0;i<5;i++)
        {
            JDFResource res= root.appendMatchingResource("RunList",JDFNode.EnumProcessUsage.AnyInput,null);
            assertNotNull(res);
            assertEquals(res.getNodeName(),ElementName.RUNLIST);
            JDFResource resa= root.getMatchingResource("RunList",JDFNode.EnumProcessUsage.AnyInput,null,i);
            assertEquals(res,resa);
            JDFResourceLink rlb= root.getMatchingLink("RunList",JDFNode.EnumProcessUsage.AnyInput,i);
            assertEquals(rlb.getTarget(),res);

        }
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////

    public void testGetMissingLinks()
    {
        JDFDoc doc= new JDFDoc("JDF");
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.ConventionalPrinting);
        VString vs=root.getMissingLinkVector(999);
        assertTrue(vs.contains(ElementName.CONVENTIONALPRINTINGPARAMS+"Link:AnyInput"));
        assertTrue(vs.contains(ElementName.COMPONENT+"Link:AnyOutput"));

        VString vsc=new VString();
        vsc.add(EnumType.InkZoneCalculation);
        vsc.add(EnumType.ConventionalPrinting);
        root.setCombined(vsc);
        vs=root.getMissingLinkVector(999);
        assertTrue(vs.contains(ElementName.PREVIEW+"Link:AnyInput"));
        assertTrue(vs.contains(ElementName.CONVENTIONALPRINTINGPARAMS+"Link:AnyInput"));
        assertTrue(vs.contains(ElementName.COMPONENT+"Link:AnyOutput"));
    }
    //////////////////////////////////////////////////////////////
    public void testGetMissingLinksProduct()
    {
        JDFDoc doc= new JDFDoc("JDF");
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.Product);
        VString vs=root.getMissingLinkVector(999);
        assertTrue(vs.contains(ElementName.COMPONENT+"Link:AnyOutput"));
        
        root.addJDFNode(EnumType.ProcessGroup);
        root.appendMatchingResource("Employee", EnumProcessUsage.AnyInput, null);
        vs=root.getMissingLinkVector(999);
        assertTrue("product with sub element still requires an output component",vs.contains(ElementName.COMPONENT+"Link:AnyOutput"));
    }
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////

    public void testGetUnknownLinks()
    {
        JDFDoc doc= new JDFDoc("JDF");
        JDFNode root = doc.getJDFRoot();
        root.setType(EnumType.ConventionalPrinting);
        VElement vs=root.getUnknownLinkVector(null,999);
        assertNull(vs);

        root.addResource(ElementName.FOLDINGPARAMS, null, EnumUsage.Input, null, null, null, null);
        
        vs=root.getUnknownLinkVector(null,999);
        assertTrue(vs.elementAt(0) instanceof JDFResourceLink);
        assertEquals(((JDFResourceLink)vs.elementAt(0)).getLocalName(),"FoldingParamsLink");

        root.addResource("foo:barRes", EnumResourceClass.Parameter, EnumUsage.Input, null, null, "www.foo.com", null);

        vs=root.getUnknownLinkVector(null,999);
        assertEquals(vs.size(),2);
        assertTrue(vs.elementAt(0) instanceof JDFResourceLink);
        assertEquals(((JDFResourceLink)vs.elementAt(0)).getLocalName(),"FoldingParamsLink");
        assertEquals(((JDFResourceLink)vs.elementAt(1)).getNodeName(),"foo:barResLink");

        VString vsc=new VString();
        vsc.add(EnumType.InkZoneCalculation);
        vsc.add(EnumType.ConventionalPrinting);
        root.setCombined(vsc);
        vs=root.getUnknownLinkVector(null,999);
        assertTrue(vs.elementAt(0) instanceof JDFResourceLink);
        assertEquals(((JDFResourceLink)vs.elementAt(0)).getLocalName(),"FoldingParamsLink");
    }
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////



}