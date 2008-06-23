/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2008 The International Cooperation for the Integration of 
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
package org.cip4.jdflib.goldenticket;

import java.io.File;
import java.net.HttpURLConnection;

import javax.mail.Multipart;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.JDFAudit;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.KElement.EnumValidationLevel;
import org.cip4.jdflib.jmf.JDFCommand;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFMessage;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.util.MimeUtil;
import org.cip4.jdflib.util.UrlUtil;
import org.cip4.jdflib.util.MimeUtil.MIMEDetails;


public class GoldenTicketTest extends JDFTestCaseBase
{
    ProductGoldenTicket pgt;
    String agentName;
    /////////////////////////////////////////////////////////////////////////////
    public void testBase()
    {
        BaseGoldenTicket bgt=new BaseGoldenTicket(1,null);
        bgt.assign(null);
        final JDFNode node = bgt.getNode();
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"GoldenTicket_Base_1.jdf", 2, false);
        assertTrue(node.getICSVersions(false).contains("Base_L1-1.3"));
        node.setType(JDFNode.EnumType.ProcessGroup);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    /////////////////////////////////////////////////////////////////////////////
    public void testJMF()
    {
        JMFGoldenTicket bgt=new JMFGoldenTicket(1,null);
        bgt.assign(null);
        final JDFNode node = bgt.getNode();
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"GoldenTicket_JMF_1.jdf", 2, false);
        assertTrue(node.getICSVersions(false).contains("Base_L2-1.3"));
        assertTrue(node.getICSVersions(false).contains("JMF_L1-1.3"));
        node.setType(JDFNode.EnumType.ProcessGroup);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    /////////////////////////////////////////////////////////////////////////////
    public void testMIS()
    {
        MISGoldenTicket bgt=new MISGoldenTicket(2,null,2);
        bgt.assign(null);
        final JDFNode node = bgt.getNode();
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"GoldenTicket_MIS_2.jdf", 2, false);
        assertTrue(node.getICSVersions(false).contains("Base_L2-1.3"));
        assertTrue(node.getICSVersions(false).contains("JMF_L2-1.3"));
        assertTrue(node.getICSVersions(false).contains("MIS_L2-1.3"));
        node.setType(JDFNode.EnumType.ProcessGroup);
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"MISGT.jdf", 2, false);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
   }
    public void testProductCreatePostCards() throws Exception
    {

        ProductGoldenTicket pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createPostCards();
        final JDFNode node = pgt.getNode();
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"postcard.jdf", 2, false);
        node.setJobID("6913");
        assertTrue(node.isValid(EnumValidationLevel.Complete));
        
    }
    public void testProductCreateAddressBook() throws Exception
    {

        pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createAddressBook();
        final JDFNode node = pgt.getNode();
        node.setJobID("6914");
       node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"adressBook.jdf", 2, false);
        assertTrue(node.isValid(EnumValidationLevel.Complete));

    }
    public void testProductCreateWatches() throws Exception
    {

        pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createWatches();
        final JDFNode node = pgt.getNode();
        node.setJobID("6915");
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"watches.jdf", 2, false);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    
    public void testProductMultiLabel() throws Exception
    {

        pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createMultiLabels();
        final JDFNode node = pgt.getNode();
        node.setJobID("6915");
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"multiLabel.jdf", 2, false);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    public void testProductCreateHarley() throws Exception
    {
        
        pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createHarley();
        final JDFNode node = pgt.getNode();
        node.setJobID("6916");
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"harley.jdf", 2, false);
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    public void testProductCreateHDCity() throws Exception
    {

        pgt=new ProductGoldenTicket(0,EnumVersion.Version_1_3,0,0);
        pgt.assign(null);
        pgt.createHDCity();
        final JDFNode node = pgt.getNode();
        node.getOwnerDocument_JDFElement().write2File(sm_dirTestDataTemp+"HDCity.jdf", 2, false);
        node.setJobID("6917");
        assertTrue(node.isValid(EnumValidationLevel.Complete));
    }
    /////////////////////////////////////////////////////////////////////////////
    /* (non-Javadoc)
     * @see org.cip4.jdflib.JDFTestCaseBase#setUp()
     */
    protected void setUp() throws Exception
    {
        agentName=JDFAudit.getStaticAgentName();
        JDFElement.setLongID(false);
        JDFAudit.setStaticAgentName("JDF golden ticket generator");
        super.setUp();
    }
    /////////////////////////////////////////////////////////////////////////////
    /* (non-Javadoc)
     * @see org.cip4.jdflib.JDFTestCaseBase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        JDFAudit.setStaticAgentName(agentName);
        JDFElement.setLongID(true);
        super.tearDown();
    }
    public static void submitMimeToHDM(JDFNode n) {
        // build SubmitQueueEntry
        JDFDoc docJMF=new JDFDoc("JMF");
        JDFJMF jmf=docJMF.getJMFRoot();
        JDFCommand com = (JDFCommand)jmf.appendMessageElement(JDFMessage.EnumFamily.Command,JDFMessage.EnumType.SubmitQueueEntry);
        com.appendQueueSubmissionParams().setURL("dummy");
        Multipart mp = MimeUtil.buildMimePackage(docJMF, n.getOwnerDocument_JDFElement(), true);
    
        try {
            MIMEDetails md=new MIMEDetails();
            md.transferEncoding=MimeUtil.BASE64;
            md.httpDetails.chunkSize=-1;
            HttpURLConnection response = MimeUtil.writeToURL( mp,"http://192.168.40.71:8889/jmfportal",md );
            assertEquals( 200,response.getResponseCode() );
            MimeUtil.writeToURL( mp,UrlUtil.fileToUrl(new File("C:\\data\\test.mim"), false),md );
        } catch (Exception e) {
            fail( e.getMessage() ); // fail on exception
        }
    }

}