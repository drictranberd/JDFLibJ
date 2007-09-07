/*
 * JDFExampleDocTest.java
 * 
 * @author muchadie
 */
package org.cip4.jdflib.examples;

import java.io.File;
import java.util.zip.DataFormatException;

import org.cip4.jdflib.auto.JDFAutoDeviceInfo.EnumDeviceStatus;
import org.cip4.jdflib.auto.JDFAutoLayoutElement.EnumElementType;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFComment;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.JDFElement.EnumNodeStatus;
import org.cip4.jdflib.datatypes.JDFMatrix;
import org.cip4.jdflib.datatypes.JDFXYPair;
import org.cip4.jdflib.node.JDFNode.EnumProcessUsage;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.resource.devicecapability.JDFActionPool;
import org.cip4.jdflib.resource.process.JDFLayoutElement;
import org.cip4.jdflib.resource.process.JDFLayoutElementPart;
import org.cip4.jdflib.resource.process.JDFLayoutElementProductionParams;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.util.StatusUtil;


public class ContentCreationTest extends PreflightTest
{
    /**
     * test iteration
     * @return
     */
    public void testLayoutElementPositioning() throws Exception
    {
        // TBD: Fuzzy, Sizes, literal text via comments
        JDFElement.setLongID(false);
        JDFDoc d=new JDFDoc("JDF");
        n=d.getJDFRoot();
        n.setType(EnumType.LayoutElementProduction);

        JDFRunList outRun=(JDFRunList)n.appendMatchingResource(ElementName.RUNLIST,EnumProcessUsage.AnyOutput,null);
        outRun.setFileURL("output.pdf");

        JDFLayoutElementProductionParams lep=(JDFLayoutElementProductionParams) n.appendMatchingResource(ElementName.LAYOUTELEMENTPRODUCTIONPARAMS,EnumProcessUsage.AnyInput,null);
        lep.appendXMLComment("This is a \"well placed\" CTM defined mark\nThe anchor defines the 0,0 point to be transformed\nThe element to be placed is referenced by LayoutElement/FileSpec/URL", null);
        JDFLayoutElementPart lePart=lep.appendLayoutElementPart();
        KElement positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "0");
        setNextAnchor(positionObj,null, "LowLeft","0 0",null,"Parent",0);
        positionObj.setAttribute("Anchor", "LowLeft");
        positionObj.setAttribute("PositionPolicy", "Exact");
        final JDFLayoutElement bkg = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        bkg.setMimeURL("bkg.pdf");

        lep.appendXMLComment("This is a \"roughly placed\" reservation in the middle of the page", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "0");
        //TODO discuss individual positions
        setNextAnchor(positionObj,null, "CenterCenter",null,null,"Parent",0);
        positionObj.setAttribute("Anchor", "CenterCenter");
        positionObj.setAttribute("PositionPolicy", "Free");
        String id=lePart.appendAnchor(null);

        JDFLayoutElement image = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        image.setElementType(EnumElementType.Image);
        image.appendComment().setText("Please add an image of a palm tree on a beach here!");

        lep.appendXMLComment("This is a \"roughly placed\" reservation 36 points below the previous image;\n NextPosition points from Anchor on this to NextAnchor on next,\n i.e. a positive vector specifies that next is shifted in the positive direction in the parent (in this case page) coordinate system", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "0");
        positionObj.setAttribute("Anchor", "TopCenter");
        positionObj.setAttribute("PositionPolicy", "Free");
        setNextAnchor(positionObj,id, "BottomCenter","0 36",null,"Sibling",0);
    
        image = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        image.setElementType(EnumElementType.Image);
        image.appendComment().setText("Please add an image of a beach ball below the palm tree!");



        lep.appendXMLComment("This is a \"well placed\" CTM defined mark\nThe anchor defines the 0,0 point to be transformed", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "0");
        setNextAnchor(positionObj,null, "BottomLeft","2 3",null,"Parent",0);
        positionObj.setAttribute("Anchor", "LowLeft");
        positionObj.setAttribute("PositionPolicy", "Exact");
        lePart.appendBarcodeProductionParams().appendXMLComment("barcode details here", null);

        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "0");
        setNextAnchor(positionObj,null, "TopRight",null,null,"Parent",0);
        positionObj.setAttribute("Anchor", "TopRight");
        positionObj.appendXMLComment("This is a \"roughly placed\"  mark\nThe anchor at top right is placed at the right (=1.0) top(=1.0) position of the page.\nNo rotation is specified", null);
        positionObj.setAttribute("PositionPolicy", "Exact");
        lePart.appendBarcodeProductionParams().appendXMLComment("barcode details here", null);

        lep.appendXMLComment("This is a \"roughly placed\"  container for marks\nThe anchor at top left is defined in the !Unrotated! orientation.\n It is placed at the left (=0.0) bottom(=0.0) position of the page.\nThe text flows bottom to top (=Rotate 90 = counterclockwise)\n do we need margins?", null);
        lePart=lep.appendLayoutElementPart();
        String idParent=lePart.appendAnchor(null);
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "1");
        positionObj.setAttribute("Anchor", "TopLeft");
        positionObj.setAttribute("PositionPolicy", "Free");
        setNextAnchor(positionObj,null, "BottomCenter","0 0",null,"Parent",90);

        lep.appendXMLComment("This is a  barcode inside the previous container\nThe anchor at bottom left is defined in the !Unrotated! orientation.\n It is placed at the left (=0.0) bottom(=0.0) position of the container.", null);
        lePart=lep.appendLayoutElementPart();
        id=lePart.appendAnchor(null);
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("Anchor", "BottomLeft");
        setNextAnchor(positionObj,idParent, "BottomLeft","0 0",null,"Parent",0);
        lePart.appendBarcodeProductionParams().appendXMLComment("barcode details here", null);

        lep.appendXMLComment("This is a disclaimer text inside the previous container\nThe anchor at top left is defined in the !Unrotated! orientation.\n The barcode and text are justified with their top margins and spaced by 72 points\n which corresponds to the left of the page because the container is rotated 90�\n"+
                "AbsoluteSize specifies the size of the object in points", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        setNextAnchor(positionObj,id, "TopRight","-72 0",null,"Sibling",0);
  
        positionObj.setAttribute("Anchor", "TopLeft");
//        positionObj.setAttribute("ParentRef", idParent);
        positionObj.setAttribute("AbsoluteSize", "300 200");
        JDFLayoutElement text = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        text.setElementType(EnumElementType.Text);
        text.setMimeURL("file://myServer/disclaimers/de/aspirin.txt");


        lep.appendXMLComment("This is a \"VERY roughly placed\" piece of text somewhere on pages 2-3\n"+
                "RelativeSize specifies the size of the object as a ratio of the size of the container", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "1 ~ 2");
        positionObj.setAttribute("RelativeSize", "0.8 0.5");
        text = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        text.setElementType(EnumElementType.Text);
        final JDFComment instructionComment = text.appendComment();
        instructionComment.setName("Instructions");
        instructionComment.setText("Please add some text about the image of a palm tree on a beach here!");

        lep.appendXMLComment("This is another \"VERY roughly placed\" piece of text somewhere on pages 2-3; the text source is the JDF", null);
        lePart=lep.appendLayoutElementPart();
        positionObj=lePart.appendElement("PositionObject");
        positionObj.setAttribute("PageRange", "1 ~ 2");
        text = (JDFLayoutElement)lePart.appendElement("LayoutElement");
        text.setElementType(EnumElementType.Text);

        JDFComment textSrc=text.appendComment();
        textSrc.setName("TextInput");
        textSrc.setText("Laurum Ipsum Blah blah blah!\n btw. this is unformatted plain text and nothing else!");


        d.write2File(sm_dirTestDataTemp+File.separator+"LayoutPositionObj.jdf",2,false);
    }

    /**
     * @param sm2_2
     * @param idAnchor
     * @throws DataFormatException 
     */
    private static void setNextAnchor(KElement sm2_2, String idAnchor, String anchor, String absolutePosition,String xmlComment, String anchorType, double rotation) throws DataFormatException
    {
        KElement nextAnchor=sm2_2.appendElement("RefAnchor");
        nextAnchor.setAttribute("Anchor",anchor);
        JDFMatrix m=new JDFMatrix("1 0 0 1 0 0");
        JDFXYPair xy=absolutePosition==null ? null : new JDFXYPair(absolutePosition);
        m.shift(xy);
        m.rotate(rotation);
        if(xy!=null || rotation!=0)
            sm2_2.setAttribute("CTM", m.toString());
        nextAnchor.setAttribute("rRef",idAnchor);  
        nextAnchor.setAttribute("AnchorType",anchorType);  
        nextAnchor.setXMLComment(xmlComment);
    }

    /**
     * test preflight concepts in layoutelementproduction
     * @throws Exception
     */
    public void testLayoutPreflight() throws Exception
    {
        JDFElement.setLongID(false);
        JDFDoc d=new JDFDoc("JDF");
        n=d.getJDFRoot();
        n.setType(EnumType.LayoutElementProduction);

        JDFRunList outRun=(JDFRunList)n.appendMatchingResource(ElementName.RUNLIST,EnumProcessUsage.AnyOutput,null);
        outRun.setFileURL("output.pdf");

        JDFLayoutElementProductionParams lep=(JDFLayoutElementProductionParams) n.appendMatchingResource(ElementName.LAYOUTELEMENTPRODUCTIONPARAMS,EnumProcessUsage.AnyInput,null);
        JDFComment com=lep.appendComment();
        com.setName("Instruction");
        com.setText("Add any human readable instructions here");

        // new
        aPool=(JDFActionPool) lep.appendElement(ElementName.ACTIONPOOL);

        // now some simple tests...
        appendNumPagesAction();
        appendSeparationAction();
        appendBWSeparationAction();
        appendTrimBoxAction();
        appendResolutionAction();
		StatusUtil su=new StatusUtil(n,null,null);
		su.setPhase(EnumNodeStatus.InProgress, "Creative Work", EnumDeviceStatus.Running, null,null);

        su.getDocJMFPhaseTime();
        Thread.sleep(1000);
		su=new StatusUtil(n,null,null);
		su.setPhase(EnumNodeStatus.InProgress, "Creative Work", EnumDeviceStatus.Running, null,null);
        su.getDocJMFPhaseTime();
        Thread.sleep(1000);
		su=new StatusUtil(n,null,null);
		su.setPhase(EnumNodeStatus.Completed, "done", EnumDeviceStatus.Idle, null,null);
        su.getDocJMFPhaseTime();
        d.write2File(sm_dirTestDataTemp+File.separator+"LayoutPreflight.jdf",2,false);


    }
}
