/*
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
/*
 * MediaColorTest.java
 * @author Dietrich Mucha
 * 
 * Copyright (C) 2004 Heidelberger Druckmaschinen AG. All Rights Reserved.
 */
package org.cip4.jdflib.devicecapability;

import java.util.Vector;

import junit.framework.TestCase;

import org.cip4.jdflib.auto.JDFAutoBasicPreflightTest.EnumListType;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.JDFElement.EnumBoolean;
import org.cip4.jdflib.datatypes.JDFIntegerRangeList;
import org.cip4.jdflib.datatypes.JDFNumberRangeList;
import org.cip4.jdflib.datatypes.JDFBaseDataTypes.EnumFitsValue;
import org.cip4.jdflib.resource.devicecapability.JDFBooleanState;
import org.cip4.jdflib.resource.devicecapability.JDFDateTimeState;
import org.cip4.jdflib.resource.devicecapability.JDFDevCap;
import org.cip4.jdflib.resource.devicecapability.JDFDurationState;
import org.cip4.jdflib.resource.devicecapability.JDFEnumerationState;
import org.cip4.jdflib.resource.devicecapability.JDFIntegerState;
import org.cip4.jdflib.resource.devicecapability.JDFMatrixState;
import org.cip4.jdflib.resource.devicecapability.JDFNameState;
import org.cip4.jdflib.resource.devicecapability.JDFNumberState;
import org.cip4.jdflib.resource.devicecapability.JDFPDFPathState;
import org.cip4.jdflib.resource.devicecapability.JDFRectangleState;
import org.cip4.jdflib.resource.devicecapability.JDFShapeState;
import org.cip4.jdflib.resource.devicecapability.JDFStringState;
import org.cip4.jdflib.resource.devicecapability.JDFXYPairState;


public class JDFDevCapTest extends TestCase
{
    public void testBooleanState()
    {
        JDFDoc d=new JDFDoc("BooleanState");
        JDFBooleanState bs=(JDFBooleanState) d.getRoot();
        Vector v=new Vector();
        v.add(EnumBoolean.False);
        bs.setAllowedValueList(v);
        assertTrue(bs.fitsValue("false", EnumFitsValue.Allowed));
        assertFalse(bs.fitsValue("fnarf", EnumFitsValue.Allowed));
        assertFalse(bs.fitsValue("true", EnumFitsValue.Allowed));     
        v.add(EnumBoolean.True);
        bs.setAllowedValueList(v);
        assertTrue(bs.fitsValue("true", EnumFitsValue.Allowed));        
    }
    
    public void testIntegerState() throws Exception
    {
        JDFDoc d=new JDFDoc("IntegerState");
        JDFIntegerState is=(JDFIntegerState) d.getRoot();
        JDFIntegerRangeList irl = new JDFIntegerRangeList("12~15");
        is.setAllowedValueList(irl);
        assertTrue(is.fitsValue("12~15", EnumFitsValue.Allowed));
        assertFalse(is.fitsValue("19~33", EnumFitsValue.Allowed));
        irl = new JDFIntegerRangeList("12~15 19~33");
        is.setAllowedValueList(irl);
        assertTrue(is.fitsValue("12~15", EnumFitsValue.Allowed));
        assertTrue(is.fitsValue("19~33", EnumFitsValue.Allowed));
    }
    
    public void testNumberState() throws Exception
    {
        JDFDoc d=new JDFDoc("NumberState");
        JDFNumberState ns=(JDFNumberState) d.getRoot();
        JDFNumberRangeList nrl = new JDFNumberRangeList("12.45~15.88");
        ns.setAllowedValueList(nrl);
        assertTrue(ns.fitsValue("12.45~15.88", EnumFitsValue.Allowed));
        assertFalse(ns.fitsValue("19.0~33.234", EnumFitsValue.Allowed));
        nrl = new JDFNumberRangeList("12.45~15.88 19.0~33.234");
        ns.setAllowedValueList(nrl);
        assertTrue(ns.fitsValue("12.45", EnumFitsValue.Allowed));
        assertTrue(ns.fitsValue("19.0~33.234", EnumFitsValue.Allowed));
        assertFalse(ns.fitsValue("16.01", EnumFitsValue.Allowed));
    }
     
    public void testEnumerationState()
    {
        JDFDoc d=new JDFDoc("EnumerationState");
        JDFEnumerationState es=(JDFEnumerationState) d.getRoot();
        VString v=new VString();
        v.add("foo");
        v.add("bar");
        
        es.setAllowedValueList(v);
        assertTrue(es.fitsValue("foo", EnumFitsValue.Allowed));
        assertTrue(es.fitsValue("bar", EnumFitsValue.Allowed));
        assertFalse(es.fitsValue("fnarf", EnumFitsValue.Allowed));  
        
        es.setListType(EnumListType.List);
        assertTrue(es.fitsValue("foo", EnumFitsValue.Allowed));
        assertTrue(es.fitsValue("foo bar", EnumFitsValue.Allowed));
        assertTrue(es.fitsValue("foo bar foo", EnumFitsValue.Allowed));
        assertFalse(es.fitsValue("foo bar fnarf", EnumFitsValue.Allowed));
        
        es.setListType(EnumListType.CompleteList);
        assertFalse(es.fitsValue("foo", EnumFitsValue.Allowed));
        assertTrue(es.fitsValue("foo bar", EnumFitsValue.Allowed));
        assertTrue(es.fitsValue("bar foo", EnumFitsValue.Allowed));
        assertFalse(es.fitsValue("foo bar foo", EnumFitsValue.Allowed));
        assertFalse(es.fitsValue("foo bar fnarf", EnumFitsValue.Allowed));
//TODO implement more list types        
//        es.setListType(EnumListType.OrderedList);
//        assertFalse(es.fitsValue("foo", EnumFitsValue.Allowed));
//        assertTrue(es.fitsValue("foo bar", EnumFitsValue.Allowed));
//        assertFalse(es.fitsValue("bar foo", EnumFitsValue.Allowed));
//        assertFalse(es.fitsValue("foo bar foo", EnumFitsValue.Allowed));
//        assertFalse(es.fitsValue("foo bar fnarf", EnumFitsValue.Allowed));
    }
    
    public void testNameState() throws Exception
    {
        JDFDoc d=new JDFDoc("NameState");
        JDFNameState ns=(JDFNameState) d.getRoot();
        VString nl = new VString();
        nl.add("anna~berta");
        ns.setAllowedValueList(nl);
        assertTrue(ns.fitsValue("anna~berta", EnumFitsValue.Allowed));
        assertFalse(ns.fitsValue("hans~otto", EnumFitsValue.Allowed));
        nl.add("anna~berta hans~otto");
        ns.setAllowedValueList(nl);
        assertTrue(ns.fitsValue("anna~berta", EnumFitsValue.Allowed));
        assertTrue(ns.fitsValue("hans~otto", EnumFitsValue.Allowed));
    }
    
    
    // test "getXxxState" /////////////////////////////////////////
    
    /* test "getXxxState" ********************************************/
    public void testGetBooleanState()
    {
    	JDFDoc d = new JDFDoc("DevCap");
    	JDFDevCap dc = (JDFDevCap) d.getRoot();
    	JDFBooleanState bs = dc.appendBooleanState("foo");
    	assertEquals(bs.getName(), "foo");
    	bs=dc.getBooleanState("bar");
    	assertNull(bs);
    	bs=dc.getCreateBooleanState("bar");
    	assertNotNull(bs);
    	assertEquals(bs.getName(), "bar");
    	bs=dc.getBooleanState("bar");
    	assertNotNull(bs);
    	assertEquals(bs.getName(), "bar");
    	
    }
    
    public void testGetIntegerState()
    {
    	JDFDoc d = new JDFDoc("DevCap");
    	JDFDevCap dc = (JDFDevCap) d.getRoot();
    	JDFIntegerState is = dc.appendIntegerState("foo");
    	assertEquals(is.getName(), "foo");
    	is=dc.getIntegerState("bar");
    	assertNull(is);
    	is=dc.getCreateIntegerState("bar");
    	assertNotNull(is);
    	assertEquals(is.getName(), "bar");
    	is=dc.getIntegerState("bar");
    	assertNotNull(is);
    	assertEquals(is.getName(), "bar");
    	
    }
           
    public void testGetNumberState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFNumberState ns=dc.appendNumberState("foo");
        assertEquals(ns.getName(), "foo");
        ns=dc.getNumberState("bar");
        assertNull(ns);
        ns=dc.getCreateNumberState("bar");
        assertNotNull(ns);
        assertEquals(ns.getName(), "bar");
        ns=dc.getNumberState("bar");
        assertNotNull(ns);
        assertEquals(ns.getName(), "bar");
       
    }
    
    public void testGetEnumerationState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFEnumerationState es=dc.appendEnumerationState("foo");
        assertEquals(es.getName(), "foo");
        es=dc.getEnumerationState("bar");
        assertNull(es);
        es=dc.getCreateEnumerationState("bar");
        assertNotNull(es);
        assertEquals(es.getName(), "bar");
        es=dc.getEnumerationState("bar");
        assertNotNull(es);
        assertEquals(es.getName(), "bar");
       
    }
        
    public void testGetNameState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFNameState ns=dc.appendNameState("foo");
        assertEquals(ns.getName(), "foo");
        ns=dc.getNameState("bar");
        assertNull(ns);
        ns=dc.getCreateNameState("bar");
        assertNotNull(ns);
        assertEquals(ns.getName(), "bar");
        ns=dc.getNameState("bar");
        assertNotNull(ns);
        assertEquals(ns.getName(), "bar");
       
    }
    
    public void testGetStringState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFStringState ss=dc.appendStringState("foo");
        assertEquals(ss.getName(), "foo");
        ss=dc.getStringState("bar");
        assertNull(ss);
        ss=dc.getCreateStringState("bar");
        assertNotNull(ss);
        assertEquals(ss.getName(), "bar");
        ss=dc.getStringState("bar");
        assertNotNull(ss);
        assertEquals(ss.getName(), "bar");
       
    }
    
    public void testGetXYPairState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFXYPairState xy=dc.appendXYPairState("foo");
        assertEquals(xy.getName(), "foo");
        xy=dc.getXYPairState("bar");
        assertNull(xy);
        xy=dc.getCreateXYPairState("bar");
        assertNotNull(xy);
        assertEquals(xy.getName(), "bar");
        xy=dc.getXYPairState("bar");
        assertNotNull(xy);
        assertEquals(xy.getName(), "bar");
       
    }
    
    public void testGetShapeState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFShapeState ss=dc.appendShapeState("foo");
        assertEquals(ss.getName(), "foo");
        ss=dc.getShapeState("bar");
        assertNull(ss);
        ss=dc.getCreateShapeState("bar");
        assertNotNull(ss);
        assertEquals(ss.getName(), "bar");
        ss=dc.getShapeState("bar");
        assertNotNull(ss);
        assertEquals(ss.getName(), "bar");
       
    }
    
    public void testGetMatrixState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFMatrixState ms=dc.appendMatrixState("foo");
        assertEquals(ms.getName(), "foo");
        ms=dc.getMatrixState("bar");
        assertNull(ms);
        ms=dc.getCreateMatrixState("bar");
        assertNotNull(ms);
        assertEquals(ms.getName(), "bar");
        ms=dc.getMatrixState("bar");
        assertNotNull(ms);
        assertEquals(ms.getName(), "bar");
       
    }
    
    public void testGetDateTimeState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFDateTimeState dts=dc.appendDateTimeState("foo");
        assertEquals(dts.getName(), "foo");
        dts=dc.getDateTimeState("bar");
        assertNull(dts);
        dts=dc.getCreateDateTimeState("bar");
        assertNotNull(dts);
        assertEquals(dts.getName(), "bar");
        dts=dc.getDateTimeState("bar");
        assertNotNull(dts);
        assertEquals(dts.getName(), "bar");
       
    }
    
    public void testGetDurationState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFDurationState ds=dc.appendDurationState("foo");
        assertEquals(ds.getName(), "foo");
        ds=dc.getDurationState("bar");
        assertNull(ds);
        ds=dc.getCreateDurationState("bar");
        assertNotNull(ds);
        assertEquals(ds.getName(), "bar");
        ds=dc.getDurationState("bar");
        assertNotNull(ds);
        assertEquals(ds.getName(), "bar");
       
    }
    
    public void testGetPDFPathState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFPDFPathState pps=dc.appendPDFPathState("foo");
        assertEquals(pps.getName(), "foo");
        pps=dc.getPDFPathState("bar");
        assertNull(pps);
        pps=dc.getCreatePDFPathState("bar");
        assertNotNull(pps);
        assertEquals(pps.getName(), "bar");
        pps=dc.getPDFPathState("bar");
        assertNotNull(pps);
        assertEquals(pps.getName(), "bar");
       
    }
    
    public void testGetRectangleState()
    {
        JDFDoc d=new JDFDoc("DevCap");
        JDFDevCap dc=(JDFDevCap) d.getRoot();
        JDFRectangleState rs=dc.appendRectangleState("foo");
        assertEquals(rs.getName(), "foo");
        rs=dc.getRectangleState("bar");
        assertNull(rs);
        rs=dc.getCreateRectangleState("bar");
        assertNotNull(rs);
        assertEquals(rs.getName(), "bar");
        rs=dc.getRectangleState("bar");
        assertNotNull(rs);
        assertEquals(rs.getName(), "bar");
       
    }
}
