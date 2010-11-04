/*
 * The CIP4 Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2010 The International Cooperation for the Integration of
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
 *    Alternately, this acknowledgment mrSubRefay appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior writtenrestartProcesses()
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
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIrSubRefAL DAMAGES (INCLUDING, BUT NOT
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
 * originally based on software restartProcesses()
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG
 * copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *
 */
package org.cip4.jdflib.extensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.cip4.jdflib.auto.JDFAutoGeneralID.EnumDataType;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFAudit;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.JDFNodeInfo;
import org.cip4.jdflib.core.JDFPartAmount;
import org.cip4.jdflib.core.JDFRefElement;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.core.JDFSeparationList;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.elementwalker.BaseElementWalker;
import org.cip4.jdflib.elementwalker.BaseWalker;
import org.cip4.jdflib.elementwalker.BaseWalkerFactory;
import org.cip4.jdflib.elementwalker.FixVersion;
import org.cip4.jdflib.jmf.JDFJMF;
import org.cip4.jdflib.jmf.JDFResourceInfo;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.node.JDFSpawned;
import org.cip4.jdflib.pool.JDFAmountPool;
import org.cip4.jdflib.pool.JDFAncestorPool;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourceLinkPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFCreasingParams;
import org.cip4.jdflib.resource.JDFCuttingParams;
import org.cip4.jdflib.resource.JDFInterpretingParams;
import org.cip4.jdflib.resource.JDFMarkObject;
import org.cip4.jdflib.resource.JDFMerged;
import org.cip4.jdflib.resource.JDFPart;
import org.cip4.jdflib.resource.JDFPhaseTime;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumResStatus;
import org.cip4.jdflib.resource.JDFResource.EnumResourceClass;
import org.cip4.jdflib.resource.JDFResourceAudit;
import org.cip4.jdflib.resource.JDFStrippingParams;
import org.cip4.jdflib.resource.process.JDFColor;
import org.cip4.jdflib.resource.process.JDFColorControlStrip;
import org.cip4.jdflib.resource.process.JDFComponent;
import org.cip4.jdflib.resource.process.JDFContainer;
import org.cip4.jdflib.resource.process.JDFContentObject;
import org.cip4.jdflib.resource.process.JDFDependencies;
import org.cip4.jdflib.resource.process.JDFEmployee;
import org.cip4.jdflib.resource.process.JDFFileSpec;
import org.cip4.jdflib.resource.process.JDFGeneralID;
import org.cip4.jdflib.resource.process.JDFLayout;
import org.cip4.jdflib.resource.process.JDFLayoutElement;
import org.cip4.jdflib.resource.process.JDFRunList;
import org.cip4.jdflib.resource.process.JDFSeparationSpec;
import org.cip4.jdflib.resource.process.postpress.JDFFoldingParams;
import org.cip4.jdflib.span.JDFSpanBase;
import org.cip4.jdflib.util.StringUtil;

/**
 * @author Dr. Rainer Prosi, Heidelberger Druckmaschinen AG <br/>
 * conversion class to convert JDF 1.x to the experimental JDF 2.0<br/>
 * very experimental and subject to change without notice
 * 
 * 15.01.2009
 */
public class XJDF20 extends BaseElementWalker
{

	/**
	 * 
	 */
	public XJDF20()
	{
		super(new BaseWalkerFactory());
		KElement.uniqueID(-1000); // don't start at zero to avoid collisions in short ID scenarios
		trackAudits = true;
		init();
	}

	public void setTrackAudits(boolean trackAudits)
	{
		this.trackAudits = trackAudits;
	}

	/**
	 * the root node name
	 */
	public final static String rootName = "XJDF";
	/**
	 * the root JMF name
	 */
	public final static String rootJMF = "JMF";

	private final String m_spawnInfo = "SpawnInfo";
	private boolean trackAudits;
	protected VString resAttribs;
	protected KElement newRoot = null;
	protected JDFNode oldRoot = null;
	protected boolean walkingProduct = false;
	protected Set<String> first = new HashSet<String>();
	/**
	 * if true merge stripping and layout
	 */
	public boolean bMergeLayout = true;
	/**
	 * if true merge stripping and layout
	 */
	public boolean bMergeLayoutPrep = true;
	/**
	 * if true clean up runlist/LayoutElement
	 */
	public boolean bMergeRunList = true;
	/**
	 * set to retain spawn information
	 */
	public boolean bRetainSpawnInfo = false;
	/**
	 * set to update version stamps
	 */
	public boolean bSingleNode = true;
	/**
	 * set to update version stamps
	 */
	public boolean bUpdateVersion = true;
	/**
	 * if true, spans are made to a simple attribute rather than retained as span
	 */
	public boolean bSpanAsAttribute = true;
	/**
	 * if true, Intents are partitioned
	 */
	public boolean bIntentPartition = false;

	/**
	 * if true add an htmlcolor attribute to color elements for xsl display purposes
	 */
	public boolean bHTMLColor = false;

	/**
	 * @param jmf the jmf to transform
	 * @return the root of the XJDF document
	 */
	public KElement makeNewJMF(final JDFJMF jmf)
	{
		final KElement root = jmf.getOwnerDocument_JDFElement().clone().getJMFRoot();
		prepareNewDoc(true);
		walkTree(root, newRoot);
		newRoot.eraseEmptyNodes(true);
		return newRoot;
	}

	/**
	 * @param node the node to transform
	 * @param vMap the partmap to transform, null if all
	 * @return the root of the XJDF document
	 */
	public KElement makeNewJDF(final JDFNode node, final VJDFAttributeMap vMap)
	{
		final JDFNode root = node.getOwnerDocument_JDFElement().clone().getJDFRoot();
		if (trackAudits)
			root.getCreateAuditPool().addModified("XJDF Converter", null);
		FixVersion vers = new FixVersion(EnumVersion.Version_1_4);
		vers.setLayoutPrepToStripping(bMergeLayoutPrep);
		vers.walkTree(root, null);

		oldRoot = (JDFNode) root.getChildWithAttribute(null, "ID", null, node.getID(), 0, false);
		if (oldRoot == null)
		{
			oldRoot = root;
		}
		walkingProduct = false;
		prepareNewDoc(false);

		loopNodes(oldRoot);

		walkingProduct = true;
		final KElement productList = newRoot.appendElement("ProductList");

		final JDFNode rootIn = node.getJDFRoot();
		walkTree(rootIn, productList);
		if (productList.getElement("Product") == null)
		{
			productList.deleteNode();
		}
		walkingProduct = false;

		PostXJDFWalker pw = new PostXJDFWalker((JDFElement) newRoot);
		pw.mergeLayout = bMergeLayout;
		pw.bIntentPartition = bIntentPartition;
		pw.walkTreeKidsFirst(newRoot);

		newRoot.eraseEmptyNodes(true);
		newRoot.getOwnerDocument_KElement().setBodyPart(node.getOwnerDocument_KElement().getBodyPart());
		return newRoot;
	}

	/**
	 * @param node
	 */
	private void loopNodes(final JDFNode node)
	{
		// the loop is implicit due to the break condition in JDFWalker
		walkTree(node, newRoot);
	}

	/**
	 * @param bJMF if true, create a jmf
	 * 
	 */
	private void prepareNewDoc(boolean bJMF)
	{
		final JDFDoc newDoc = new JDFDoc(bJMF ? rootJMF : rootName);
		newDoc.setInitOnCreate(false);
		newRoot = newDoc.getRoot();
		first = new HashSet<String>();
	}

	/**
	 * 
	 */
	private void init()
	{
		final JDFResourcePool dummyResPool = (JDFResourcePool) new JDFDoc("ResourcePool").getRoot();
		final JDFResource intRes = dummyResPool.appendResource("intent", EnumResourceClass.Intent, null);
		final JDFResource physRes = dummyResPool.appendResource("physical", EnumResourceClass.Consumable, null);
		final JDFResource paramRes = dummyResPool.appendResource("param", EnumResourceClass.Parameter, null);
		final JDFPart part = (JDFPart) dummyResPool.appendElement(ElementName.PART);
		resAttribs = paramRes.knownAttributes();
		resAttribs.appendUnique(physRes.knownAttributes());
		resAttribs.appendUnique(intRes.knownAttributes());
		resAttribs.appendUnique(part.knownAttributes());
	}

	String getClassName(final JDFResource r)
	{
		if (r == null)
		{
			return null;
		}
		EnumResourceClass resourceClass = r.getResourceClass();
		if (resourceClass == null)
		{
			KElement r2 = new JDFDoc(r.getLocalName()).getRoot();
			if (r2 instanceof JDFResource)
			{
				r2.init();
				resourceClass = ((JDFResource) r2).getResourceClass();
			}
		}
		if (resourceClass == null)
			return "Parameter"; // assume parameter if unknown 3rd party stuff

		String className = "Resource";
		if (resourceClass.equals(EnumResourceClass.Parameter) || resourceClass.equals(EnumResourceClass.Intent))
		{
			className = resourceClass.getName();
		}
		if (resourceClass.equals(EnumResourceClass.PlaceHolder))
		{
			return null;
		}
		return className;
	}

	/**
	 * calculate a file extension name based of rootName
	 * @return String
	 */
	public static String getExtension()
	{
		return rootName.toLowerCase();
	}

	/**
	 * @param fileName the filename of the zip file to save to
	 * @param rootNode the root jdf to save
	 * @param replace if true, overwrite existing files
	 */
	public void saveZip(final String fileName, final JDFNode rootNode, final boolean replace)
	{
		final File file = new File(fileName);
		if (file.canRead())
		{
			if (replace)
			{
				file.delete();
			}
			else
			{
				throw new JDFException("output file exists: " + file.getPath());
			}
		}
		// file.createNewFile(fileName);

		try
		{
			final VElement v = rootNode.getvJDFNode(null, null, false);
			final FileOutputStream fos = new FileOutputStream(fileName);
			final ZipOutputStream zos = new ZipOutputStream(fos);
			for (int i = 0; i < v.size(); i++)
			{
				final JDFNode n = (JDFNode) v.elementAt(i);
				String nam = n.getJobPartID(false);
				if (nam == "")
				{
					nam = "Node" + i;
				}
				try
				{
					nam += "." + rootName;
					final ZipEntry ze = new ZipEntry(nam);
					zos.putNextEntry(ze);
					final KElement newRootL = makeNewJDF(n, null);
					newRootL.getOwnerDocument_KElement().write2Stream(zos, 2, true);
					zos.closeEntry();

				}
				catch (final ZipException x)
				{
					// TODO Auto-generated catch block
					x.printStackTrace();
				}
				catch (final IOException x)
				{
					// TODO Auto-generated catch block
					x.printStackTrace();
				}
			}
			zos.close();
		}
		catch (final IOException x)
		{
			x.printStackTrace();
		}
	}

	void setAmountPool(final JDFElement rl, final KElement newLeaf, final JDFAttributeMap partMap)
	{
		if (rl == null)
		{
			return;
		}
		JDFAmountPool ap = (JDFAmountPool) rl.getElement(ElementName.AMOUNTPOOL);
		if (ap == null)
		{
			if (rl.hasAttribute(AttributeName.AMOUNT) || rl.hasAttribute(AttributeName.ACTUALAMOUNT) || rl.hasAttribute(AttributeName.MAXAMOUNT))
			{
				newLeaf.setAttribute(AttributeName.AMOUNT + "Good", rl.getAttribute(AttributeName.AMOUNT, null, null));
				newLeaf.setAttribute(AttributeName.ACTUALAMOUNT + "Good", rl.getAttribute(AttributeName.ACTUALAMOUNT, null, null));
				newLeaf.setAttribute(AttributeName.MAXAMOUNT + "Good", rl.getAttribute(AttributeName.MAXAMOUNT, null, null));
			}
		}
		else
		{
			final VElement vPartAmounts = ap.getMatchingPartAmountVector(partMap);
			if (vPartAmounts != null)
			{
				for (int i = 0; i < vPartAmounts.size(); i++)
				{
					JDFPartAmount pa = (JDFPartAmount) vPartAmounts.item(i);
					final JDFAttributeMap map = pa.getPartMap();
					if (partMap != null)
					{
						map.removeKeys(partMap.keySet());
					}
					if (map.isEmpty()) // no further subdevision - simply blast into leaf
					{
						newLeaf.setAttribute(AttributeName.AMOUNT + "Good", pa.getAttribute(AttributeName.AMOUNT, null, null));
						newLeaf.setAttribute(AttributeName.ACTUALAMOUNT + "Good", pa.getAttribute(AttributeName.ACTUALAMOUNT, null, null));
						newLeaf.setAttribute(AttributeName.MAXAMOUNT + "Good", pa.getAttribute(AttributeName.MAXAMOUNT, null, null));
					}
					else if (map.size() == 1 && map.containsKey(AttributeName.CONDITION))
					{
						final JDFAttributeMap attMap = pa.getAttributeMap();
						final Iterator<String> it = attMap.getKeyIterator();
						final String condition = map.get(AttributeName.CONDITION);
						while (it.hasNext())
						{
							final String key = it.next();
							if (key.indexOf(AttributeName.AMOUNT) > 0)
							{
								// TODO rethink AmountGood, AmountWaste
								newLeaf.setAttribute(key + condition, attMap.get(key));
							}
							else
							{
								newLeaf.setAttribute(key, attMap.get(key));
							}
						}
					}
					else
					// retain ap
					{
						// TODO special handling for virtual parts
						final KElement amountPool = newLeaf.getCreateElement("AmountPool");
						pa = (JDFPartAmount) amountPool.copyElement(pa, null);
						pa.setPartMap(map);
					}
				}
			}
		}
	}

	/**
	 * @param rl 
	 * @param r 
	 * @param xjdfSet 
	 * @return 
	 * 
	 */
	private KElement setBaseResource(final JDFElement rl, final JDFResource r, final KElement xjdfSet)
	{
		final JDFAttributeMap map = r.getPartMap();
		SetHelper sh = new SetHelper(xjdfSet);
		KElement newLeaf = sh.getCreatePartition(map, false).getPartition();
		setLeafAttributes(r, rl, newLeaf);
		return newLeaf;
	}

	/**
	 * @param leaf
	 * @param rl 
	 * @param newLeaf
	 */
	private void setLeafAttributes(final JDFResource leaf, final JDFElement rl, final KElement newLeaf)
	{
		final JDFAttributeMap partMap = leaf.getPartMap();
		// JDFAttributeMap attMap=leaf.getAttributeMap();
		// attMap.remove("ID");
		setAmountPool(rl, newLeaf, partMap);

		// retain spawn information
		if (bRetainSpawnInfo && leaf.hasAttribute(AttributeName.SPAWNIDS))
		{
			final KElement spawnInfo = newLeaf.getDocRoot().getCreateElement(m_spawnInfo, null, 0);
			final KElement spawnID = spawnInfo.appendElement("SpawnID");
			spawnID.moveAttribute(AttributeName.SPAWNIDS, newLeaf, null, null, null);
			spawnID.moveAttribute(AttributeName.SPAWNSTATUS, newLeaf, null, null, null);
			spawnID.copyAttribute(AttributeName.RESOURCEID, newLeaf, AttributeName.ID, null, null);
		}
	}

	/**
	 * set the attributes of the set based on the resource and resourcelink
	 * 
	 * @param resourceSet
	 * @param rl
	 * @param linkRoot
	 */
	private void setSetAttributes(final KElement resourceSet, final KElement rl, final JDFResource linkRoot)
	{
		resourceSet.setAttribute("Name", linkRoot.getNodeName());
		resourceSet.setAttributes(rl);
		//TODO orientation + coordinate system stuff
		resourceSet.removeAttribute(AttributeName.RREF);
		resourceSet.removeAttribute(AttributeName.RSUBREF);
		resourceSet.removeAttribute(AttributeName.AMOUNT);
		resourceSet.removeAttribute(AttributeName.AMOUNTPRODUCED);
		resourceSet.removeAttribute(AttributeName.MAXAMOUNT);
		resourceSet.removeAttribute(AttributeName.ACTUALAMOUNT);

		if (rl instanceof JDFResourceLink)
		{
			final JDFResourceLink resLink = (JDFResourceLink) rl;
			final JDFNode rootIn = resLink.getJDFRoot();

			final JDFResource resInRoot = rootIn == null ? linkRoot : (JDFResource) rootIn.getChildWithAttribute(null, "ID", null, resLink.getrRef(), 0, false);
			if (resInRoot != null)
			{
				final VElement vCreators = resInRoot.getCreator(EnumUsage.Input.equals(resLink.getUsage()));
				if (vCreators != null)
				{
					final int size = vCreators.size();
					for (int i = 0; i < size; i++)
					{
						final JDFNode depNode = (JDFNode) vCreators.elementAt(i);
						final KElement dependent = resourceSet.appendElement("Dependent");
						dependent.setAttribute(AttributeName.JOBID, depNode.getJobID(true));
						dependent.copyAttribute(AttributeName.JMFURL, depNode, null, null, null);
						dependent.copyAttribute(AttributeName.JOBPARTID, depNode, null, null, null);
					}
				}
			}
		}
	}

	/**
	 * @param rl
	 * @param linkTarget
	 * @param xRoot
	 * @return the vector of partitions
	 */
	protected VElement setResource(final JDFElement rl, final JDFResource linkTarget, final KElement xRoot)
	{
		final VElement v = new VElement();
		final String className = getClassName(linkTarget);
		if (className == null)
		{
			return null;
		}
		linkTarget.expand(false);
		final String resID = linkTarget.getAttribute("ID");

		KElement resourceSet = xRoot.getChildWithAttribute(className + "Set", "ID", null, resID, 0, true);
		if (resourceSet == null)
		{
			resourceSet = xRoot.appendElement(className + "Set");
			resourceSet.setAttribute("ID", linkTarget.getID());
		}
		// TODO what if he have resources used as in and out in the same node?
		setSetAttributes(resourceSet, rl, linkTarget);
		int nLeaves = resourceSet.numChildElements(className, null);
		final VElement vRes = (rl instanceof JDFResourceLink) ? ((JDFResourceLink) rl).getTargetVector(0) : linkTarget.getLeaves(false);
		for (int j = 0; j < vRes.size(); j++)
		{
			final JDFResource r = (JDFResource) vRes.elementAt(j);
			final VElement vLeaves = r.getLeaves(false);
			for (int k = 0; k < vLeaves.size(); k++)
			{
				final JDFResource leaf = (JDFResource) vLeaves.elementAt(k);
				final KElement newBaseRes = setBaseResource(rl, leaf, resourceSet);
				final int nn = resourceSet.numChildElements(className, null);
				if (nn > nLeaves)
				{
					nLeaves = nn;
					walkTree(leaf, newBaseRes);
				}
				v.add(newBaseRes);
			}
		}
		return v;
	}

	// //////////////////////////////////////////////////////////////////////////////
	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkResource extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkResource()
		{
			super();
		}

		/**
		 * @param jdf
		 * @param xjdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFResource r = (JDFResource) jdf;
			final KElement newResLeaf = super.walk(jdf, xjdf);

			if (newResLeaf != null)
			{
				newResLeaf.removeAttribute(AttributeName.ID);
				moveAttribsToBase(xjdf, newResLeaf);
				removeDeprecatedResourceAttribs(r, newResLeaf);
				removeDeprecatedResourceAttribs(r, xjdf);
			}
			return newResLeaf;
		}

		/**
		 * @param xjdf
		 * @param newResLeaf
		 */
		protected void moveAttribsToBase(final KElement xjdf, final KElement newResLeaf)
		{
			final String localName = xjdf.getLocalName();
			final boolean bRoot = "Intent".equals(localName) || "Parameter".equals(localName) || "Resource".equals(localName);
			for (int i = 0; i < resAttribs.size(); i++)
			{
				if (newResLeaf.hasAttribute(resAttribs.stringAt(i)))
				{
					if (bRoot)
					{
						xjdf.moveAttribute(resAttribs.stringAt(i), newResLeaf, null, null, null);
					}
					else
					{
						newResLeaf.removeAttribute(resAttribs.stringAt(i));
					}
				}
			}
		}

		/**
		 * @param r
		 * @param newResLeaf
		 */
		private void removeDeprecatedResourceAttribs(final JDFResource r, final KElement newResLeaf)
		{
			newResLeaf.removeAttributes(r.getPartIDKeys());
			newResLeaf.removeAttribute(AttributeName.CLASS);
			newResLeaf.removeAttribute(AttributeName.PARTUSAGE);
			newResLeaf.removeAttribute(AttributeName.LOCKED);
			newResLeaf.removeAttribute(AttributeName.NOOP);
			newResLeaf.removeAttribute(AttributeName.SPAWNSTATUS);
			newResLeaf.removeAttribute(AttributeName.SPAWNIDS);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResource;
		}
	} // //////////////////////////////////////////////////////////////////////////////

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkJDFElement extends WalkElement
	{
		/**
		 * 
		 */
		public WalkJDFElement()
		{
			super();
		}

		/**
		 * @param jdf
		 * @param xjdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFElement je = (JDFElement) jdf;
			makeRefElements(je);
			return super.walk(jdf, xjdf);
		}

		/**
		 * make all inline resources to refelements
		 * @param je
		 */
		private void makeRefElements(final JDFElement je)
		{
			final VElement v = je.getChildElementVector_KElement(null, null, null, true, 0);
			for (int i = 0; i < v.size(); i++)
			{
				final KElement e = v.get(i);
				if (e instanceof JDFResource)
				{
					final JDFResource r = (JDFResource) e;
					if (!mustInline(r.getLocalName()))
					{
						cleanRefs(je, r);
					}
				}
			}
		}

		/**
		 * @param je
		 * @param r
		 */
		private void cleanRefs(final JDFElement je, JDFResource r)
		{
			final JDFNode parentJDF = je.getParentJDF();
			if (parentJDF != null)
			{
				r = r.makeRootResource(null, parentJDF, false);
				r.setResStatus(EnumResStatus.Available, true);
				final JDFResourcePool prevPool = parentJDF.getResourcePool();
				if (prevPool != null)
				{
					r = removeDuplicateRefs(r, prevPool);
				}
				je.refElement(r);
			}
			else if (je.getJMFRoot() != null)
			{
				final JDFResource resourceRoot = r.getResourceRoot();
				final JDFElement parent = (JDFElement) (resourceRoot == null ? null : resourceRoot.getParentNode_KElement());
				r = r.makeRootResource(null, parent, false);
				r.setResStatus(EnumResStatus.Available, true);
				je.refElement(r);
			}
		}

		/**
		 * @param r
		 * @param prevPool
		 * @return
		 */
		private JDFResource removeDuplicateRefs(JDFResource r, final JDFResourcePool prevPool)
		{
			final JDFAttributeMap m = r.getAttributeMap();
			m.remove("ID");
			final VElement prevs = prevPool.getChildrenByTagName(r.getNodeName(), null, m, true, true, 0);
			if (prevs != null)
			{
				for (int j = 0; j < prevs.size(); j++)
				{
					final JDFResource prev = (JDFResource) prevs.get(j);
					if (r == prev)
					{
						continue;
					}
					final String pid = prev.getID();
					final String rid = r.getID();
					prev.removeAttribute("ID"); // for comparing
					r.removeAttribute("ID");
					if (r.isEqual(prev)) // found duplicate - remove and ref the original
					{
						r.deleteNode();
						r = prev;
						prev.setID(pid); // better put it back...
						break;
					}
					else
					{
						r.setID(rid);
						prev.setID(pid); // better put it back...
					}
				}
			}
			return r;
		}

		/**
		 * get the name for the attribute to become a reference - may add a "Refs" rather than ref for 
		 * @param re the refelement to name
		 * @return the name
		 */
		protected String getRefName(final JDFRefElement re)
		{
			return re.getLocalName();
		}

		/**
		 * @param re
		 * @return true if must inline re
		 */
		protected boolean mustInline(final JDFRefElement re)
		{
			return mustInline(re.getRefLocalName());
		}

		/**
		 * @param refLocalName
		 * @return true if must inline refLocalName
		 */
		protected boolean mustInline(final String refLocalName)
		{
			return ElementName.OBJECTRESOLUTION.equals(refLocalName) || ElementName.BARCODECOMPPARAMS.equals(refLocalName) || ElementName.BARCODEREPROPARAMS.equals(refLocalName)
					|| ElementName.COMCHANNEL.equals(refLocalName) || ElementName.INTERPRETEDPDLDATA.equals(refLocalName) || ElementName.BYTEMAP.equals(refLocalName)
					|| ElementName.COMPANY.equals(refLocalName) || ElementName.COSTCENTER.equals(refLocalName) || ElementName.ADDRESS.equals(refLocalName)
					|| ElementName.PERSON.equals(refLocalName) || ElementName.DEVICE.equals(refLocalName) || ElementName.DEVICENSPACE.equals(refLocalName)
					|| ElementName.COLORANTALIAS.equals(refLocalName) || ElementName.GLUELINE.equals(refLocalName) || ElementName.GLUEAPPLICATION.equals(refLocalName)
					|| ElementName.CIELABMEASURINGFIELD.equals(refLocalName) || ElementName.REGISTERMARK.equals(refLocalName) || ElementName.FITPOLICY.equals(refLocalName)
					|| ElementName.CUTBLOCK.equals(refLocalName) || ElementName.EMPLOYEE.equals(refLocalName) || ElementName.ELEMENTCOLORPARAMS.equals(refLocalName)
					|| ElementName.CUT.equals(refLocalName) || ElementName.PDLRESOURCEALIAS.equals(refLocalName) || ElementName.HOLELIST.equals(refLocalName)
					|| ElementName.HOLE.equals(refLocalName) || ElementName.MISDETAILS.equals(refLocalName) || ElementName.HOLELINE.equals(refLocalName)
					|| ElementName.JOBFIELD.equals(refLocalName) || ElementName.OBJECTRESOLUTION.equals(refLocalName) || ElementName.AUTOMATEDOVERPRINTPARAMS.equals(refLocalName)
					|| ElementName.EXTERNALIMPOSITIONTEMPLATE.equals(refLocalName) || ElementName.PRODUCTIONPATH.equals(refLocalName) || ElementName.SHAPE.equals(refLocalName)
					|| ElementName.SCAVENGERAREA.equals(refLocalName) || ElementName.SCAVENGERAREA.equals(refLocalName) || ElementName.TRAPREGION.equals(refLocalName)
					|| ElementName.TRANSFERCURVE.equals(refLocalName) || ElementName.COLORCONTROLSTRIP.equals(refLocalName) || ElementName.LAYERLIST.equals(refLocalName)
					|| ElementName.PAGECONDITION.equals(refLocalName) || ElementName.CONTENTOBJECT.equals(refLocalName) || ElementName.MARKOBJECT.equals(refLocalName)
					|| ElementName.FILESPEC.equals(refLocalName) || ElementName.LAYERDETAILS.equals(refLocalName) || ElementName.BINDERYSIGNATURE.equals(refLocalName);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFElement;
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkElement#removeUnused(org.cip4.jdflib.core.KElement)
		 * @param newRootP
		*/
		@Override
		protected void removeUnused(KElement newRootP)
		{
			newRootP.removeAttribute(AttributeName.SPAWNID);
			super.removeUnused(newRootP);
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkResLink extends WalkJDFElement
	{

		/**
		 * @param jdf
		 * @param xjdf
		 * @return the created resource in this case just remove the pool
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFResourceLink rl = (JDFResourceLink) jdf;
			final JDFResource linkTarget = rl.getLinkRoot();
			if (linkTarget == null)
			{
				return null;
			}
			// final boolean bCustomerInfo = linkTarget instanceof JDFCustomerInfo;
			if (walkingProduct)
			{
				// if (!bCustomerInfo && !EnumResourceClass.Intent.equals(linkTarget.getResourceClass()))
				if (!EnumResourceClass.Intent.equals(linkTarget.getResourceClass()))
				{
					return null;
				}
				setResource(rl, linkTarget, xjdf);
			}
			else
			{
				// if (bCustomerInfo || EnumResourceClass.Intent.equals(linkTarget.getResourceClass()))
				if (EnumResourceClass.Intent.equals(linkTarget.getResourceClass()))
				{
					return null;
				}
				setResource(rl, linkTarget, newRoot);
				if (!bSingleNode)
					setProcess(rl);
			}
			return null;
		}

		/**
		 * @param rl
		 */
		private void setProcess(JDFResourceLink rl)
		{
			if (rl == null)
				return;

			KElement process = getProcess(rl);
			setLink(process, rl);

		}

		/**
		 * @param process
		 * @param rl
		 */
		private void setLink(KElement process, JDFResourceLink rl)
		{
			if (rl == null)
				return;
			EnumUsage usage = rl.getUsage();
			if (usage == null)
				return;
			String attName = usage.getName();
			if (attName != null)
			{
				process.appendAttribute(attName, rl.getrRef(), null, " ", true);
			}
		}

		/**
		 * @param rl
		 * @return 
		 */
		private KElement getProcess(JDFResourceLink rl)
		{
			JDFNode parent = rl.getParentJDF();
			String jobPartID = getJobPartID(parent);
			if (jobPartID == null)
				return null;

			KElement processList = newRoot.getCreateElement("ProcessList", null, 0);
			KElement process = processList.getChildWithAttribute("Process", AttributeName.JOBPARTID, null, jobPartID, 0, true);
			if (process == null)
			{
				process = processList.appendElement("Process");
				process.setAttribute(AttributeName.JOBPARTID, jobPartID);
				JDFNode grandparent = parent.getParentJDF();

				if (parent.hasAttribute(AttributeName.TYPES))
				{
					process.copyAttribute("Types", parent);
				}
				else
				{
					process.copyAttribute("Types", parent, "Type", null, null);
				}

				if (grandparent != null)
					process.setAttribute("Parent", getJobPartID(grandparent));

			}
			return process;
		}

		/**
		 * @param parent
		 * @return
		 */
		private String getJobPartID(JDFNode parent)
		{
			String jobPartID = StringUtil.getNonEmpty(parent.getJobPartID(false));
			if (jobPartID == null)
				jobPartID = StringUtil.getNonEmpty(parent.getID());
			return jobPartID;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceLink;
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkRefElement extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkRefElement()
		{
			super();
		}

		/**
		 * @param jdf
		 * @param xjdf
		 * @return the created resource in this case just remove the pool
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFRefElement refElem = (JDFRefElement) jdf;
			if (mustInline(refElem))
			{
				final JDFElement e = refElem.inlineRef();
				walkTree(e, xjdf);
				return null;
			}
			else
			{
				makeRefAttribute(refElem, xjdf);
				return null;
			}

		}

		/**
		 * @param re
		 * @param xjdf 
		 */
		protected void makeRefAttribute(final JDFRefElement re, final KElement xjdf)
		{
			final JDFResource target = re.getTarget();
			final JDFResourceLink rl = getLinkForRef(re, target);
			final VElement v = setResource(rl, target, getRefRoot(xjdf));
			if (v != null)
			{
				final String attName = getRefName(re);
				for (int i = 0; i < v.size(); i++)
				{
					final KElement ref = v.get(i);
					xjdf.appendAttribute(attName, ref.getAttribute("ID"), null, " ", true);
				}
			}
			re.deleteNode();
		}

		/**
		 * @param re
		 * @param target
		 * @return
		 */
		private JDFResourceLink getLinkForRef(final JDFRefElement re, final JDFResource target)
		{
			JDFResourceLink rl = null;
			if (oldRoot != null)
			{
				final JDFResourceLinkPool resourceLinkPool = oldRoot.getResourceLinkPool();
				rl = resourceLinkPool != null ? resourceLinkPool.getLink(target, null, null) : null;
			}
			return rl;
		}

		/**
		 * @param xjdf
		 * @return
		 */
		private KElement getRefRoot(final KElement xjdf)
		{
			KElement ret = null;
			if (xjdf != null)
			{
				ret = xjdf.getDeepParent(ElementName.RESOURCEINFO, 0);
			}
			return ret == null ? newRoot : ret;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFRefElement;
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkResLinkPool extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkResLinkPool()
		{
			super();
		}

		/**
		 * @param resLinkPool
		 * @param xjdf
		 * @return the created resource in this case just remove the pool
		 */
		@Override
		public KElement walk(final KElement resLinkPool, final KElement xjdf)
		{
			getLinksFromAncestorPool(resLinkPool);
			return xjdf;
		}

		/**
		 * copy the closest ancestorpool link to here, if none exists
		 * @param resLinkPool 
		 */
		private void getLinksFromAncestorPool(KElement resLinkPool)
		{
			KElement parent = resLinkPool.getParentNode_KElement();
			if (!(parent instanceof JDFNode))
				return;
			JDFNode n = (JDFNode) parent;
			JDFAncestorPool ap = n.getAncestorPool();
			if (ap == null)
				return;
			String[] v = { ElementName.NODEINFO, ElementName.CUSTOMERINFO };
			for (String s : v)
			{
				JDFResource ni = n.getResource(s, null, 0);
				if (ni == null)
				{
					JDFNodeInfo nia = (JDFNodeInfo) ap.getAncestorElement(s, null, null);
					n.linkResource(nia, EnumUsage.Input, null);
				}
			}
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceLinkPool;
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkJDF extends WalkJDFElement
	{
		/**
		 * @param jdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			if (first.contains(jdf.getID()) || bSingleNode && first.size() > 0)
			{
				return null;
			}
			first.add(jdf.getID());
			final JDFNode node = (JDFNode) jdf;
			setRootAttributes(node, xjdf);
			return xjdf;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return !walkingProduct && (toCheck instanceof JDFNode);
		}

		/**
		 * @param node
		 * @param newRootP
		 */
		private void setRootAttributes(final JDFNode node, final KElement newRootP)
		{
			newRootP.appendXMLComment("Very preliminary experimental prototype trial version: using: " + JDFAudit.getStaticAgentName() + " " + JDFAudit.getStaticAgentVersion(), null);
			newRootP.setAttribute(AttributeName.JOBID, node.getJobID(true));

			final JDFNodeInfo ni = node.getCreateNodeInfo();
			final VElement niLeaves = ni.getLeaves(false);
			for (int i = 0; i < niLeaves.size(); i++)
			{
				final JDFNodeInfo niLeaf = (JDFNodeInfo) niLeaves.get(i);
				final JDFAttributeMap map = niLeaf.getPartMap();
				niLeaf.setNodeStatus(node.getPartStatus(map, 0));
				niLeaf.setNodeStatusDetails(StringUtil.getNonEmpty(node.getPartStatusDetails(map)));
			}
			String types = newRootP.getAttribute(AttributeName.TYPES, null, null);
			newRootP.setAttributes(node);

			removeUnused(newRootP);

			if (bUpdateVersion)
			{
				newRootP.setAttribute("Version", "2.0");
				newRootP.setAttribute("MaxVersion", "2.0");
			}

			updateTypes(newRootP, types);
			namedFeaturesToGeneralID(node, newRootP);
			updateSpawnInfo(node, newRootP);
		}

		/**
		 * @param node
		 * @param newRootP
		 */
		private void namedFeaturesToGeneralID(final JDFNode node, final KElement newRootP)
		{
			if (node.hasAttribute(AttributeName.NAMEDFEATURES))
			{
				final VString vnf = node.getNamedFeatures();
				for (int i = 0; i < vnf.size(); i += 2)
				{
					final JDFGeneralID gi = (JDFGeneralID) newRootP.appendElement(ElementName.GENERALID);
					gi.setIDUsage(vnf.get(i));
					gi.setIDValue(vnf.get(i + 1));
					gi.setDataType(EnumDataType.NMTOKEN);
					gi.setDescriptiveName("Copy from NamedFeatures");
				}
				newRootP.removeAttribute(AttributeName.NAMEDFEATURES);
			}
		}

		/**
		 * @param newRootP
		 */
		@Override
		protected void removeUnused(final KElement newRootP)
		{
			// status is set only in the NodeInfo
			newRootP.removeAttribute(AttributeName.STATUS);
			newRootP.removeAttribute(AttributeName.STATUSDETAILS);
			newRootP.removeAttribute(AttributeName.ACTIVATION);
			newRootP.removeAttribute(AttributeName.TEMPLATE);
			super.removeUnused(newRootP);
		}

		/**
		 * @param newRootP
		 * @param types 
		 */
		private void updateTypes(final KElement newRootP, String types)
		{
			if (!newRootP.hasAttribute(AttributeName.TYPES))
			{
				newRootP.renameAttribute("Type", "Types", null, null);
			}
			else
			{
				newRootP.removeAttribute("Type");
			}
			VString t1 = StringUtil.tokenize(types, null, false);
			VString t2 = StringUtil.tokenize(newRootP.getAttribute(AttributeName.TYPES), null, false);
			t1.appendUnique(t2);
			t1.removeStrings("ProcessGroup", 0);
			t1.removeStrings("Combined", 0);
			newRootP.setAttribute(AttributeName.TYPES, t1, null);
		}

		/**
		 * @param node
		 * @param newRootP
		 */
		private void updateSpawnInfo(final JDFNode node, final KElement newRootP)
		{
			if (m_spawnInfo != null && newRootP.hasAttribute(AttributeName.SPAWNID))
			{
				final KElement spawnInfo = newRootP.appendElement(m_spawnInfo, "www.cip4.org/SpawnInfo");
				spawnInfo.moveAttribute(AttributeName.SPAWNID, newRootP, null, null, null);
				final JDFAncestorPool ancestorPool = node.getAncestorPool();
				if (ancestorPool != null)
				{
					final VJDFAttributeMap vParts = ancestorPool.getPartMapVector();
					if (vParts != null)
					{
						final int size = vParts.size();
						for (int i = 0; i < size; i++)
						{
							spawnInfo.appendElement(ElementName.PART).setAttributes(vParts.elementAt(i));
						}
					}
				}
			}
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkProduct extends WalkJDFElement
	{
		/**
		 * @param jdf
		 * @param xjdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final KElement pList = "Product".equals(xjdf.getLocalName()) ? xjdf.getParentNode_KElement() : xjdf;
			final JDFNode node = (JDFNode) jdf;
			if (!EnumType.Product.equals(node.getEnumType()))
			{
				return null;
			}
			final KElement prod = pList.appendElement("Product");
			prod.setAttributes(jdf);
			prod.removeAttribute(AttributeName.TYPE);
			prod.removeAttribute(AttributeName.ACTIVATION);
			prod.removeAttribute(AttributeName.VERSION);
			prod.removeAttribute(AttributeName.MAXVERSION);
			prod.removeAttribute(AttributeName.ICSVERSIONS);
			prod.removeAttribute(AttributeName.STATUS);
			prod.removeAttribute(AttributeName.STATUSDETAILS);
			prod.removeAttribute(AttributeName.XMLNS);
			prod.removeAttribute(AttributeName.XSITYPE);
			prod.removeAttribute(AttributeName.JOBID);
			prod.renameAttribute(AttributeName.JOBPARTID, AttributeName.PRODUCTID, null, null);
			prod.removeAttribute("xmlns:xsi");
			calcChildren(node, prod);
			readComponent(node, prod);
			return prod;
		}

		/**
		 * @param node
		 * @param prod
		 */
		private void readComponent(final JDFNode node, final KElement prod)
		{
			final JDFResourceLink cOut = node.getLink(0, "ComponentLink", new JDFAttributeMap("Usage", "Output"), null);
			if (cOut == null)
				return;
			setAmountPool(cOut, prod, null);
			prod.renameAttribute("AmountGood", "Amount", null, null);
			prod.removeAttribute("AmountWaste");

			JDFComponent component = (JDFComponent) cOut.getTarget();
			prod.copyAttribute(AttributeName.PRODUCTTYPE, component);
			prod.copyAttribute(AttributeName.PRODUCTTYPEDETAILS, component);
		}

		/**
		 * @param node
		 * @param prod 
		 */
		private void calcChildren(final JDFNode node, final KElement prod)
		{
			final VString kids = new VString();
			final VElement vComp = node.getPredecessors(true, true);
			if (vComp != null)
			{
				final int siz = vComp.size();
				for (int i = 0; i < siz; i++)
				{
					final JDFNode nPre = (JDFNode) vComp.get(i);
					if (EnumType.Product.equals(nPre.getEnumType()))
					{
						kids.add(nPre.getID());
					}
				}
			}

			if (kids.size() > 0)
			{
				for (int i = 0; i < kids.size(); i++)
				{
					final KElement sub = prod.appendElement("ChildProduct");
					sub.setAttribute("ChildRef", kids.get(i), null);
					// TODO add processusage from input / output resources
				}
			}
			else
			{
				final KElement list = prod.getParentNode_KElement();
				list.appendAttribute("RootProducts", node.getID(), null, null, true);
			}
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return walkingProduct && toCheck instanceof JDFNode;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkElement extends BaseWalker
	{
		boolean bMerge;

		@SuppressWarnings("synthetic-access")
		public WalkElement()
		{
			super(getFactory());
			bMerge = false;
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final KElement eNew = bMerge ? xjdf : xjdf.appendElement(jdf.getNodeName(), jdf.getNamespaceURI());

			eNew.setAttributes(jdf);
			eNew.setText(jdf.getText());
			removeUnused(eNew);
			return eNew;
		}

		protected void removeUnused(final KElement newRootP)
		{
			newRootP.removeAttribute(AttributeName.XSITYPE);
		}

	}

	/**
	 * any matching class will be removed with extreme prejudice...
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkIgnore extends WalkJDFElement
	{

		public WalkIgnore()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			return null;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFAncestorPool || toCheck instanceof JDFResourcePool || toCheck instanceof JDFSpawned || toCheck instanceof JDFMerged;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkAuditPool extends WalkJDFElement
	{

		public WalkAuditPool()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			if (walkingProduct)
			{
				return null;
			}
			else if (newRoot.getElement(ElementName.AUDITPOOL) != null)
			{
				return newRoot.getElement(ElementName.AUDITPOOL);
			}
			return super.walk(jdf, xjdf);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFAuditPool;
		}

	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen at this point only a dummy since we have a specific WalkResourceAudit child
	 */
	protected class WalkAudit extends WalkJDFElement
	{

		public WalkAudit()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFAudit;
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkJDFElement#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 * @param jdf
		 * @param xjdf
		 * @return
		*/
		@Override
		public KElement walk(KElement jdf, KElement xjdf)
		{
			KElement e = super.walk(jdf, xjdf);
			if (!bSingleNode && e != null)
			{
				JDFNode n = ((JDFAudit) jdf).getParentJDF();
				e.copyAttribute(AttributeName.JOBPARTID, n);
			}
			return e;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 * at this point only a dummy since we have a specific WalkResourceAudit child
	 * 
	 * TODO how should resource consumption be tracked?
	 */
	protected class WalkResourceAudit extends WalkAudit
	{
		VJDFAttributeMap partMap = null;

		public WalkResourceAudit()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final KElement raNew = super.walk(jdf, xjdf);
			final JDFResourceAudit ra = (JDFResourceAudit) jdf;
			partMap = ra.getPartMapVector();
			copyLinkValues(raNew, ra.getNewLink(), "NewRef");
			copyLinkValues(raNew, ra.getOldLink(), "OldRef");

			return null; // don't walk the links!
		}

		/**
		 * @param raNew
		 * @param rl
		 * @param val
		 */
		protected void copyLinkValues(final KElement raNew, final JDFResourceLink rl, final String val)
		{
			final JDFResource rlRoot = rl == null ? null : rl.getLinkRoot();
			if (rlRoot != null && rl != null)
			{
				final VElement v = setResource(null, rlRoot, newRoot);
				if (v != null)
				{
					for (final KElement kElem : v)
					{
						KElement resAmount = raNew.appendElement("ResourceAmount");
						resAmount.setAttribute("Type", val);
						resAmount.setAttribute("rRef", kElem.getAttribute(AttributeName.ID));
						if (partMap == null || partMap.size() == 0)
						{
							setAmountPool(rl, resAmount, null);
						}
						else
						{
							for (int i = 0; i < partMap.size(); i++)
							{
								JDFAttributeMap partMap2 = partMap.get(i);
								setAmountPool(rl, resAmount, partMap2);
								resAmount.appendElement("Part").setAttributes(partMap2);
							}
						}
					}
				}
			}
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceAudit;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 * at this point only a dummy since we have a specific WalkResourceAudit child
	 * 
	 * TODO how should resource consumption be tracked?
	 */
	protected class WalkPhaseTimeAudit extends WalkAudit
	{
		public WalkPhaseTimeAudit()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFPhaseTime pt = (JDFPhaseTime) jdf;
			final VElement vL = pt.getLinkVector();
			final VElement phaseAmount = new VElement();
			if (vL != null)
			{
				for (int i = 0; i < vL.size(); i++)
				{
					final JDFResourceLink rl = (JDFResourceLink) vL.get(i);
					final VElement vR = setResource(null, rl.getLinkRoot(), newRoot);
					final KElement pA = xjdf.appendElement("PhaseAmount");
					for (int j = 0; j < vR.size(); j++)
					{
						pA.appendAttribute("rRef", vR.get(j).getAttribute(AttributeName.ID), null, " ", true);
						pA.copyElement(rl.getAmountPool(), null);
						rl.deleteNode();
					}
					phaseAmount.add(pA);
				}
			}
			final KElement x2 = super.walk(jdf, xjdf); // copy anything but the links (see deleteNode above...)
			if (x2 != null)
			{
				for (int i = 0; i < phaseAmount.size(); i++)
				{
					x2.moveElement(phaseAmount.get(i), null);
				}
			}
			return x2;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFPhaseTime;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkSpan extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkSpan()
		{
			super();
		}

		/**
		 * depending on the value of bSpanAsAttribute either <br/>
		 * 		invert XXXSpan/@Datatype=foo to FooSpan/@Name=Datatype
		 *      create an Attribute with the name of the span
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final KElement ret;
			final JDFSpanBase span = (JDFSpanBase) jdf;

			if (bSpanAsAttribute)
			{
				ret = spanToAttribute(span, xjdf);
			}
			else
			{
				ret = invertSpan(span, xjdf);
			}
			return ret;
		}

		/**
		 * @param span
		 * @param xjdf
		 * @return
		 */
		private KElement spanToAttribute(JDFSpanBase span, KElement xjdf)
		{
			String name = span.getLocalName();
			String val = span.guessActual();
			if (val != null)
				xjdf.setAttribute(name, val);
			return null;
		}

		/**
		 * @param span
		 * @param xjdf
		 * @return
		 */
		private KElement invertSpan(final JDFSpanBase span, final KElement xjdf)
		{
			span.inlineRefElements(null, null, false);
			org.cip4.jdflib.span.JDFSpanBase.EnumDataType dataType = span.getDataType();
			if (dataType == null)
				return null; // broken!
			final KElement eNew = xjdf.appendElement(dataType.getName());
			eNew.setAttributes(span);
			eNew.removeAttribute(AttributeName.DATATYPE);
			eNew.setAttribute(AttributeName.NAME, span.getLocalName());
			return eNew;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFSpanBase;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkSeparationList extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkSeparationList()
		{
			super();
		}

		/**
		 * replace separationspec elements with their respective values
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFSeparationList je = (JDFSeparationList) jdf;
			final String name = jdf.getLocalName();
			final VString cols = je.getSeparations();
			if (cols != null)
			{
				for (int i = 0; i < cols.size(); i++)
				{
					String col = cols.get(i);
					cols.set(i, StringUtil.replaceChar(col, ' ', "_", 0));
				}
			}
			xjdf.setAttribute(name, cols, null);
			return null; // done
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFSeparationList;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkSeparationSpec extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkSeparationSpec()
		{
			super();
		}

		/**
		 * replace separationspec elements with their respective values
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFSeparationSpec ss = (JDFSeparationSpec) jdf;
			String name = ss.getName();
			name = StringUtil.replaceChar(name, ' ', "_", 0);
			xjdf.appendAttribute("SeparationSpec", name, null, " ", false);
			return null;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFSeparationSpec;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkLayout extends WalkMediaRefByType
	{
		/**
		 * 
		 */
		public WalkLayout()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFLayout;
		}
	}

	/**
	 * take a container/FileSpec(Ref) and convert it into a ContainerRef
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkContainer extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkContainer()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFContainer;
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkJDFElement#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFContainer cont = (JDFContainer) jdf;
			final JDFFileSpec fs = cont.getFileSpec();
			if (fs != null)
			{
				fs.makeRootResource(null, null, true);
				final VElement v = setResource(null, fs, newRoot);
				if (v != null && v.size() == 1)
				{
					xjdf.setAttribute("ContainerRef", v.get(0).getAttribute("ID"));
				}
			}
			return null;
		}
	}

	protected class WalkDependencies extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkDependencies()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFDependencies;
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkJDFElement#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFDependencies dep = (JDFDependencies) jdf;
			final VElement v = dep.getChildElementVector(ElementName.LAYOUTELEMENT, null);
			if (v != null)
			{
				for (int i = 0; i < v.size(); i++)
				{
					final JDFLayoutElement leDep = (JDFLayoutElement) v.get(i);
					leDep.makeRootResource(null, null, true);
					final VElement v2 = setResource(null, leDep, newRoot);
					if (v2 != null)
					{
						for (int j = 0; j < v2.size(); j++)
						{
							xjdf.appendAttribute("Dependencies", v2.get(j).getAttribute("ID"), null, " ", true);
						}
					}
				}
			}
			return null;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkInlineAllElement extends WalkJDFElement
	{
		/**
		 * 
		 */
		public WalkInlineAllElement()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFContentObject) || (toCheck instanceof JDFMarkObject);
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkJDFElement#mustInline(java.lang.String)
		 */
		@Override
		protected boolean mustInline(final String refLocalName)
		{
			return true;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkInlineAllRes extends WalkResource
	{
		/**
		 * 
		 */
		public WalkInlineAllRes()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFColorControlStrip) || (toCheck instanceof JDFCuttingParams) || (toCheck instanceof JDFCreasingParams)
					|| (toCheck instanceof JDFFoldingParams);
		}

		/**
		 * @see org.cip4.jdflib.extensions.XJDF20.WalkJDFElement#mustInline(java.lang.String)
		 */
		@Override
		protected boolean mustInline(final String refLocalName)
		{
			return true;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkColorPoolLink extends WalkResLink
	{
		/**
		 * 
		 */
		public WalkColorPoolLink()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceLink && "ColorPoolLink".equals(toCheck.getLocalName());
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFResourceLink rl = (JDFResourceLink) jdf;
			final JDFResource r = rl.getLinkRoot();
			if (r != null)
			{
				final VElement v = r.getChildElementVector(ElementName.COLOR, null);
				for (int i = 0; i < v.size(); i++)
				{
					v.get(i).renameAttribute("Name", "Separation", null, null);
				}
				//				r.renameElement("Color", null);
				KElement cNew = r.getParentNode_KElement().appendElement(ElementName.COLOR);
				cNew.copyInto(r, true);
				r.deleteNode();
				cNew.setAttribute(AttributeName.PARTIDKEYS, "Separation");
				rl.renameElement("ColorLink", null);
			}
			return super.walk(jdf, xjdf);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkColorPoolRef extends WalkRefElement
	{
		/**
		 * 
		 */
		public WalkColorPoolRef()
		{
			super();
		}

		/**
		 * @param re
		 */
		@Override
		protected void makeRefAttribute(final JDFRefElement re, final KElement xjdf)
		{
			final String attName = getRefName(re);
			final VElement v = setResource(null, re.getTarget(), newRoot);
			// we want a ref to the set rather than the standard ref to the list of elements
			if (v != null && v.size() > 0)
			{
				final KElement ref = v.get(0).getParentNode_KElement();
				xjdf.setAttribute(attName, ref.getAttribute("ID"));
			}
			re.deleteNode();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFRefElement && "ColorPoolRef".equals(toCheck.getLocalName());
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFRefElement rl = (JDFRefElement) jdf;
			final JDFResource r = rl.getTargetRoot();
			if (r != null)
			{
				final VElement v = r.getChildElementVector(ElementName.COLOR, null);
				for (int i = 0; i < v.size(); i++)
				{
					v.get(i).renameAttribute("Name", "Separation", null, null);
				}
				KElement cNew = r.getParentNode_KElement().appendElement(ElementName.COLOR);
				cNew.copyInto(r, true);
				r.deleteNode();
				cNew.setAttribute(AttributeName.PARTIDKEYS, "Separation");
				rl.renameElement("ColorRef", null);
			}
			return super.walk(jdf, xjdf);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkMediaLink extends WalkResLink
	{
		/**
		 * 
		 */
		public WalkMediaLink()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceLink && "MediaLink".equals(toCheck.getLocalName());
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			// TODO create component and ref the media
			final JDFResourceLink rl = (JDFResourceLink) jdf;
			final JDFResource r = rl.getLinkRoot();
			if (r != null)
			{
				//
			}
			return super.walk(jdf, xjdf);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkInlineResource extends WalkResource
	{
		/**
		 * 
		 */
		public WalkInlineResource()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFEmployee;
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			String descName = jdf.getAttribute(AttributeName.DESCRIPTIVENAME, null, null);
			KElement e = super.walk(jdf, xjdf);
			if (e != null)
			{
				e.setAttribute(AttributeName.DESCRIPTIVENAME, descName);
			}
			return e;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkColor extends WalkResource
	{
		/**
		 * 
		 */
		public WalkColor()
		{
			super();
		}

		/**
		 * invert XXXSpan/@Datatype=foo to FooSpan/@Name=Datatype
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFColor k = (JDFColor) super.walk(jdf, xjdf);
			if (bHTMLColor)
			{
				k.setAttribute("HTMLColor", ((JDFColor) jdf).getHTMLColor());
			}
			k.setActualColorName(jdf.getAttribute("Separation", null, null));
			return k;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFColor;
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkMediaRefByType extends WalkResource
	{

		/**
		 * 
		 */
		public WalkMediaRefByType()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			// TODO mediaref -> plateref etc.
			return super.walk(jdf, xjdf);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFInterpretingParams) || (toCheck instanceof JDFLayout) || (toCheck instanceof JDFStrippingParams); // TODO|| (toCheck
			// instanceof
			// JDFRasterReadingParams);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkRunList extends WalkInlineAllRes
	{
		/**
		 * 
		 */
		public WalkRunList()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			return super.walk(jdf, xjdf);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFRunList);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkStrippingParams extends WalkMediaRefByType
	{
		/**
		 * 
		 */
		public WalkStrippingParams()
		{
			super();
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			KElement e = super.walk(jdf, xjdf);
			return e;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFStrippingParams);
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkLayoutElement extends WalkInlineAllRes
	{
		/**
		 * 
		 */
		public WalkLayoutElement()
		{
			super();
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFLayoutElement);
		}

		/**
		 * @param xjdf
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			KElement parent = jdf.getParentNode_KElement();
			boolean bInRunList = parent instanceof JDFRunList;
			if (bInRunList)
				bMerge = bMergeRunList;
			else
				bMerge = false;
			KElement ret = super.walk(jdf, xjdf);
			if (!bInRunList && bMergeRunList)
			{
				KElement retPar = ret.getDeepParent("ParameterSet", 0);
				if (retPar != null)
					retPar.setAttribute("Name", "RunList");
				ret.renameElement("RunList", null);
			}
			return ret;
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen <br/>
	 * walker for the various resource sets
	 */
	public class WalkJMF extends WalkJDFElement
	{
		/**
		 * @param jdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			if (first.contains(jdf.getID()))
			{
				return null;
			}
			first.add(jdf.getID());
			final JDFJMF jmf = (JDFJMF) jdf;
			setRootAttributes(jmf, xjdf);
			return xjdf;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return (toCheck instanceof JDFJMF);
		}

		/**
		 * @param jmf
		 * @param newRootP
		 */
		private void setRootAttributes(final JDFJMF jmf, final KElement newRootP)
		{
			newRootP.appendXMLComment("Very preliminary experimental prototype trial version: using: " + JDFAudit.getStaticAgentName() + " " + JDFAudit.getStaticAgentVersion(), null);
			if (bUpdateVersion)
			{
				newRootP.setAttribute("Version", "2.0");
				newRootP.setAttribute("MaxVersion", "2.0");
			}
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkResourceInfo extends WalkJDFElement
	{

		/**
		 * @param jdf
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement jdf, final KElement xjdf)
		{
			final JDFResourceInfo ri = (JDFResourceInfo) jdf;
			final KElement eNew = xjdf.copyElement(jdf, null);
			eNew.removeChildren(null, null, null);
			final VElement vr = ((JDFResourceInfo) jdf).getChildElementVector(null, null);
			int nRes = 0;
			for (int i = 0; i < vr.size(); i++)
			{
				if (vr.get(i) instanceof JDFResource)
				{
					final JDFResource r = (JDFResource) vr.get(i);
					if (nRes == 0)
					{
						setResource(ri, r, eNew);
					}
					else
					{
						setResource(null, r, eNew);
					}
					r.deleteNode();
					nRes++;
				}
			}

			return eNew;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFResourceInfo;
		}
	}

	/**
	 * @param res
	 * @return omaMaps
	 */
	protected static VJDFAttributeMap getPartMapVector(final KElement res)
	{
		VJDFAttributeMap omaMaps = null;
		final VElement parts = res.getChildElementVector(ElementName.PART, null, null, true, 0, false);
		if (parts != null && parts.size() > 0)
		{
			omaMaps = new VJDFAttributeMap();
			for (int i = 0; i < parts.size(); i++)
			{
				omaMaps.add(((JDFPart) parts.get(i)).getPartMap());
			}
		}
		return omaMaps;
	}

}
