/*
 *
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2016 The International Cooperation for the Integration of
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
package org.cip4.jdflib.resource.intent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.resource.JDFResource.EnumPartIDKey;
import org.cip4.jdflib.resource.process.JDFComponent;
import org.cip4.jdflib.span.JDFNumberSpan;
import org.cip4.jdflib.span.JDFTimeSpan;
import org.cip4.jdflib.util.JDFDate;
import org.junit.Test;

/**
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class JDFDeliveryIntentTest extends JDFTestCaseBase
{

	/**
	 *
	 */
	@Test
	public void testAppendOverage()
	{
		JDFDeliveryIntent di = (JDFDeliveryIntent) new JDFDoc(ElementName.DELIVERYINTENT).getRoot();
		JDFNumberSpan ns = di.appendOverage();
		assertNotNull(ns);
	}

	/**
	 *
	 */
	@Test
	public void testGetDropItemForComponent()
	{
		JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		JDFDeliveryIntent di = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Output);
		JDFDropIntent drop = di.appendDropIntent();
		assertNull(drop.getDropItemWithComponent(c));
		JDFDropItemIntent dropItemIntent = drop.appendDropItemIntent();
		dropItemIntent.refComponent(c);
		assertEquals(dropItemIntent, drop.getDropItemWithComponent(c));
	}

	/**
	 *
	 */
	@Test
	public void testGetDropItemForComponentPart()
	{
		JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		JDFDeliveryIntent di = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Output);
		JDFComponent c1 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S1");
		JDFComponent c2 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S2");
		JDFDropIntent drop = di.appendDropIntent();
		assertNull(drop.getDropItemWithComponent(c1));
		JDFDropItemIntent dropItemIntent = drop.appendDropItemIntent();
		dropItemIntent.refComponent(c1);
		assertEquals(dropItemIntent, drop.getDropItemWithComponent(c1));
		assertNull(drop.getDropItemWithComponent(c2));
		assertNull(drop.getDropItemWithComponent(c));
	}

	/**
	 *
	 */
	@Test
	public void testGetDropDropItemForComponentPart()
	{
		JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		JDFDeliveryIntent di = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Output);
		JDFComponent c1 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S1");
		JDFComponent c2 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S2");
		JDFDropIntent drop = di.appendDropIntent();
		assertNull(di.getDropItemWithComponent(c1));
		JDFDropItemIntent dropItemIntent = drop.appendDropItemIntent();
		dropItemIntent.refComponent(c1);
		assertEquals(dropItemIntent, di.getDropItemWithComponent(c1));
		assertNull(di.getDropItemWithComponent(c2));
		assertNull(di.getDropItemWithComponent(c));
	}

	/**
	 *
	 */
	@Test
	public void testGetCreateDropItemForComponent()
	{
		JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		JDFDeliveryIntent di = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Output);
		JDFDropIntent drop = di.appendDropIntent();
		assertNull(drop.getDropItemWithComponent(c));
		JDFDropItemIntent dropItemIntent = drop.getCreateDropItemWithComponent(c);
		assertEquals(dropItemIntent, drop.getDropItemWithComponent(c));
	}

	/**
	 *
	 */
	@Test
	public void testGetCreateDropItemForComponentPart()
	{
		JDFNode n = new JDFDoc(ElementName.JDF).getJDFRoot();
		JDFDeliveryIntent di = (JDFDeliveryIntent) n.addResource(ElementName.DELIVERYINTENT, EnumUsage.Input);
		JDFComponent c = (JDFComponent) n.addResource(ElementName.COMPONENT, EnumUsage.Output);
		JDFComponent c1 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S1");
		JDFComponent c2 = (JDFComponent) c.addPartition(EnumPartIDKey.SheetName, "S2");
		JDFDropIntent drop = di.appendDropIntent();
		assertNull(drop.getDropItemWithComponent(c1));
		JDFDropItemIntent dropItemIntent = drop.getCreateDropItemWithComponent(c1);
		assertEquals(dropItemIntent, drop.getDropItemWithComponent(c1));
		assertNull(drop.getDropItemWithComponent(c));
		assertNull(drop.getDropItemWithComponent(c2));
		dropItemIntent = drop.getCreateDropItemWithComponent(c2);
		assertEquals(dropItemIntent, drop.getDropItemWithComponent(c2));
	}

	/**
	*
	*/
	@Test
	public void testAppendRequired()
	{
		JDFDeliveryIntent di = (JDFDeliveryIntent) new JDFDoc(ElementName.DELIVERYINTENT).getRoot();
		JDFTimeSpan req = di.appendRequired();
		assertNull(req.getActual());
		assertNull(req.getRange());
		JDFDate date = new JDFDate();
		req.setActual(date);
		assertEquals(req.getActual(), date);
	}
}
