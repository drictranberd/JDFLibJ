
//Titel:        JDF TestApplication
//Version:
//Copyright:    Copyright (c) 2002
//Autor:        Sabine Jonas, sjonas@topmail.de, Dietrich Mucha
//Firma:        BU/GH Wuppertal
//Beschreibung: first Applications using the JDFLibrary
//package testNew;

package org.cip4.jdflib;

import java.io.File;

import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.node.JDFNode;

public class TestJDF
{
    private static final String SEPARATOR = File.separator; // "/"; //
    static protected final String sm_dirTestSchema   = ".." + SEPARATOR + "schema" + SEPARATOR + "Version_1_3" + SEPARATOR;
    static protected final String sm_dirTestData     = "O:\\jdflibj\\test" + SEPARATOR + "data" + SEPARATOR;
    static protected final String sm_dirTestDataTemp = sm_dirTestData + "temp" + SEPARATOR;


    public static void main(String[] argv)
    {         
         JDFDoc jdfDoc = new JDFDoc("JDF");

         JDFNode n=jdfDoc.getJDFRoot();
         String s="";
         for(int i=40;i<255;i++)
             s+=(char)i;
         n.setDescriptiveName(s);
         jdfDoc.write2File("bad.jdf", 0);
          
        
  // mpart ist leer, warum ???
    }
}