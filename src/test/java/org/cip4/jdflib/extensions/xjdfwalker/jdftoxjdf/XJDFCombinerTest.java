/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in
 * Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the
 * following acknowledgment: "This product includes software developed by the The International
 * Cooperation for the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)"
 * Alternately, this acknowledgment may appear in the software itself, if and wherever such
 * third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in
 * Prepress, Press and Postpress" must not be used to endorse or promote products derived from this
 * software without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their
 * name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please
 * consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN
 * PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The
 * International Cooperation for the Integration of Processes in Prepress, Press and Postpress and
 * was originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in
 * Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.jdflib.extensions.xjdfwalker.jdftoxjdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Vector;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.extensions.SetHelper;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 *
 */
public class XJDFCombinerTest extends JDFTestCaseBase
{

	@Test
	public void testCombineTypes()
	{
		final Vector<XJDFHelper> vh = new Vector<>();
		for (int i = 1; i < 3; i++)
		{
			final XJDFHelper h = new XJDFHelper("j1", "p1", new VJDFAttributeMap(new JDFAttributeMap(AttributeName.SHEETNAME, "S" + i)));
			h.addType(EnumType.Product);
			vh.add(h);
		}
		final XJDFCombiner c = new XJDFCombiner(vh.get(0), vh.get(1));
		final int[] ct = c.combineTypes();
		assertEquals(1, ct.length);
		assertEquals(0, ct[0]);
	}

	/**
	 *
	 */
	@Test
	public void testGetMainSet()
	{
		final Vector<XJDFHelper> vh = new Vector<>();
		for (int i = 1; i < 3; i++)
		{
			final XJDFHelper h = new XJDFHelper("j1", "p1", new VJDFAttributeMap(new JDFAttributeMap(AttributeName.SHEETNAME, "S" + i)));
			h.addType(EnumType.Product);
			vh.add(h);
		}
		final XJDFHelper h0 = vh.get(0);
		final XJDFHelper h1 = vh.get(1);
		final XJDFCombiner c = new XJDFCombiner(h0, h1);
		final SetHelper mainSet = c.getMainSet(h1.getSet(ElementName.NODEINFO, 0));
		assertEquals(h0.getSet(ElementName.NODEINFO, 0), mainSet);
	}

	/**
	 *
	 */
	@Test
	public void testMergeSet()
	{
		final Vector<XJDFHelper> vh = new Vector<>();
		for (int i = 1; i < 3; i++)
		{
			final XJDFHelper h = new XJDFHelper("j1", "p1", new VJDFAttributeMap(new JDFAttributeMap(AttributeName.SHEETNAME, "S" + i)));
			h.addType(EnumType.Product);
			vh.add(h);
		}
		final XJDFHelper h0 = vh.get(0);
		final XJDFHelper h1 = vh.get(1);
		final XJDFCombiner c = new XJDFCombiner(h0, h1);
		c.mergeSet(h0.getSet(ElementName.NODEINFO, 0), h1.getSet(ElementName.NODEINFO, 0));

		assertEquals(2, h0.getSet(ElementName.NODEINFO, 0).getPartitions().size());
	}

	/**
	 *
	 */
	@Test
	public void testMergeSetSame()
	{
		final Vector<XJDFHelper> vh = new Vector<>();
		for (int i = 1; i < 3; i++)
		{
			final XJDFHelper h = new XJDFHelper("j1", "p1", new VJDFAttributeMap(new JDFAttributeMap(AttributeName.SHEETNAME, "S1")));
			h.addType(EnumType.Product);
			vh.add(h);
		}
		final XJDFHelper h0 = vh.get(0);
		final XJDFHelper h1 = vh.get(1);
		final XJDFCombiner c = new XJDFCombiner(h0, h1);
		c.mergeSet(h0.getSet(ElementName.NODEINFO, 0), h1.getSet(ElementName.NODEINFO, 0));

		assertEquals(1, h0.getSet(ElementName.NODEINFO, 0).getPartitions().size());
	}

	/**
	 *
	 */
	@Test
	public void testgetCombinedComplex()
	{
		final JDFNode product = JDFNode.parseFile(sm_dirTestData + "SampleFiles/MISPrepress-ICS-Complex.jdf");
		final JDFNode cp1 = product.getJobPart("PCS_CVP1058430001");
		final JDFNode cp2 = product.getJobPart("PCS_CVP1058460009");
		final XJDFHelper h1 = new XJDFHelper(new JDFToXJDF().convert(cp1));
		final XJDFHelper h2 = new XJDFHelper(new JDFToXJDF().convert(cp2));
		final XJDFCombiner c = new XJDFCombiner(h1, h2);

		c.combine();

		assertNull(h1.getSet(ElementName.CONVENTIONALPRINTINGPARAMS, 1));
	}

}
