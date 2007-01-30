/**
 * JDFXYPairRangeListTest.java
 *
 * @author Elena Skobchenko
 * 
 * Copyright (c) 2001-2004 The International Cooperation for the Integration 
 * of Processes in  Prepress, Press and Postpress (CIP4).  All rights reserved.
 */
package org.cip4.jdflib.datatypes;

import java.util.zip.DataFormatException;

import junit.framework.TestCase;

import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.node.JDFNode;


public class JDFXYPairTest extends TestCase
{
    public final void testSetString()
    {
        JDFDoc doc=new JDFDoc("JDF");
        JDFNode n=doc.getJDFRoot();
        
        JDFXYPair xy = new JDFXYPair();
        
        try
        {
            xy = new JDFXYPair("1 2");
        }
        catch (DataFormatException dfe)
        {
            assertFalse("exception caught",false);
            System.out.println(dfe.toString());
        }
        n.setAttribute("test",xy,null);
        
        try
        {
            xy = new JDFXYPair("1.1 2.2");
        }
        catch (DataFormatException dfe)
        {
            assertFalse("exception caught",false);
            System.out.println(dfe.toString());
        }
        n.setAttribute("test2",xy,null);
        assertEquals("double double",n.getAttribute("test2",null,""),"1.1 2.2");
        
        try
        {
            new JDFXYPair("1 2 3");
        }
        catch (DataFormatException dfe)
        {
            assertTrue("exception 123 caught", true);
        }
    }

    public final void testIsGreaterOrEqual()
    {
        JDFXYPair xy = new JDFXYPair();
        
        JDFXYPair ab = new JDFXYPair(1.0, 2.0);
        xy = new JDFXYPair(1.0+JDFBaseDataTypes.EPSILON/2.0, 2.0+JDFBaseDataTypes.EPSILON/2.0);
        
        assertTrue(ab.equals(xy));
        assertTrue(ab.isLessOrEqual(xy));
        assertTrue(ab.isGreaterOrEqual(xy));
    }

    public final void testIsLessOrEqual()
    {
        JDFXYPair xy = new JDFXYPair();
        
        JDFXYPair ab = new JDFXYPair(1.0, 2.0);
        xy = new JDFXYPair(1.0-JDFBaseDataTypes.EPSILON/2.0, 2.0-JDFBaseDataTypes.EPSILON/2.0);
        
        assertTrue(ab.equals(xy));
        assertTrue(ab.isLessOrEqual(xy));
        assertTrue(ab.isGreaterOrEqual(xy));
    }
}
