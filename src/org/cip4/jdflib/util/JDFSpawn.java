/*
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
/**
 * 
 */
package org.cip4.jdflib.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

import org.apache.commons.lang.enums.ValuedEnum;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFAudit;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.JDFRefElement;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.AttributeInfo.EnumAttributeType;
import org.cip4.jdflib.core.JDFElement.EnumNodeStatus;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.node.JDFAncestor;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFSpawned;
import org.cip4.jdflib.node.JDFNode.EnumActivation;
import org.cip4.jdflib.pool.JDFAncestorPool;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFPart;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumPartUsage;
import org.cip4.jdflib.resource.JDFResource.EnumSpawnStatus;
import org.cip4.jdflib.resource.process.JDFIdentical;

/**
 * @author Rainer Prosi This class is used when spawning a JDF node it summarizes all spawning routines the had been part of JDF Node
 */
public class JDFSpawn
{

	private JDFNode node;

	/**
	 * if true, reduce read only partitions, else retain entire resource
	 */
	public boolean bSpawnROPartsOnly = true;

	/**
	 * if true, allow multiple rw spawning of resources note that this feature causes race conditions when merging
	 */
	public boolean bSpawnRWPartsMultiple = false;

	/**
	 * if true, copy node info
	 */
	public boolean bCopyNodeInfo = true;

	/**
	 * if true, copy customer info
	 */
	public boolean bCopyCustomerInfo = true;

	/**
	 * if true, copy comments
	 */
	public boolean bCopyComments = false;

	/**
	 * if true, ensure sufficient partitioning of rw resources, else do not add missing partitions
	 */
	public boolean bFixResources = true;
	public String parentURL = null;
	public String spawnURL = null;
	/**
	 * list of all resources to copy rw
	 */
	public VString vRWResources_in = null;
	/**
	 * list of partitions to spawn
	 */
	public VJDFAttributeMap vSpawnParts = null;

	/**
	 * exception id for multiple merge attempt
	 */
	public static final int exAlreadyMerged = 10001;
	/**
	 * exception id for multiple rw spawns
	 */
	public static final int exMultiSpawnRW = 10002;

	/**
	 * 
	 * @param nodeToSpawn the node to be spawned
	 */
	public JDFSpawn(final JDFNode nodeToSpawn)
	{
		node = nodeToSpawn;
	}

	/**
	 * spawn a node; url is the file name of the new node, vRWResourceUsage is the vector of Resources Usages (or Names if no usage exists for the process) that
	 * are spawned RW, all others are spawned read only; vParts is the vector of part maps that are to be spawned, defaults to no part, i.e. the whole thing
	 * 
	 * @param parentURL
	 * @param spawnURL : URL of the spawned JDF file
	 * @param vRWResources : vector of resource names and Usage / ProcessUsage that are spawned as rw <br>
	 * the format is one of:<br>
	 * ResName:Input<br>
	 * ResName:Output<br>
	 * ResName:ProcessUsage<br>
	 * @param VJDFAttributeMap vSpawnParts: vector of mAttributes that describe the parts to spawn, only valid PartIDKeys are allowed
	 * @param bSpawnROPartsOnly if true, only the parts of RO resources that are specified in vParts are spawned, else the complete resource is spawned
	 * @param bCopyNodeInfo copy the NodeInfo elements into the Ancestors
	 * @param bCopyCustomerInfo copy the CustomerInfo elements into the Ancestors
	 * @param bCopyComments copy the Comment elements into the Ancestors
	 * 
	 * @return The spawned node
	 * @since 050831 added bCopyComments @ tbd enhance nested spawning of partitioned nodes
	 * 
	 * default: spawn(parentURL, null, null, null, false, false, false, false)
	 */
	public JDFNode spawn()
	{
		// need copy in order to fix up 1.3 NodeInfo spawn
		final VString vRWResources = vRWResources_in == null ? new VString() : vRWResources_in;

		if (!bSpawnRWPartsMultiple)
		{
			checkMultipleRWRes();
		}

		// create a new jdf document that contains the node to be spawned
		final JDFDoc docOut = new JDFDoc(ElementName.JDF);
		final JDFNode rootOut = (JDFNode) docOut.getRoot();

		// prepare the nodeinfos in main prior to spawning
		prepareNodeInfos();

		// merge this node into it
		rootOut.mergeNode(node, false); // "copy" this node into the new created
		// document
		final String spawnID = "Sp" + JDFElement.uniqueID(-666); // create a spawn
		// id for this transaction
		rootOut.setSpawnID(spawnID);
		rootOut.setVersion(node.getVersion(true));

		// need copy in order to fix up 1.3 NodeInfo spawn
		final String nodeInfoNonAncestor = "NodeInfo:Input"; // named process
		// usage
		if (!vRWResources.contains(nodeInfoNonAncestor))
		{
			vRWResources.addElement(nodeInfoNonAncestor); // the local nodeinfo
			// MUST always be rw
		}

		JDFNode spawnParentNode = null;
		// we want to spawn a partition
		if (vSpawnParts != null && !vSpawnParts.isEmpty())
		{
			spawnParentNode = node;
			// don't copy the whole history along
			rootOut.getAuditPool().flush();

			// The AncestorPool of the original JDF contains the appropriate
			// Part elements
			final JDFAncestorPool ancpool = docOut.getJDFRoot().getAncestorPool();
			VJDFAttributeMap preSpawnedParts = new VJDFAttributeMap();

			if (ancpool != null)
			{
				preSpawnedParts = ancpool.getPartMapVector();
			}
			// 150102 RP add AncestorPool pre spawn part handling
			if (!preSpawnedParts.isEmpty())
			{
				// allParts is the vector of all parts that the spawned
				// AncestorPool will contain
				final VJDFAttributeMap allParts = new VJDFAttributeMap();

				for (int psp = 0; psp < preSpawnedParts.size(); psp++)
				{
					final Vector<VJDFAttributeMap> vAttrib = vSpawnParts.getVector();
					final VJDFAttributeMap tmpParts = new VJDFAttributeMap(vAttrib);
					tmpParts.overlapMap(preSpawnedParts.elementAt(psp));
					allParts.appendUnique(tmpParts);
				}
				vSpawnParts = allParts;
			}
			// we arrived at a null vector of parts - that ain't no good
			if (vSpawnParts.isEmpty())
			{
				throw new JDFException("JDFNode.Spawn attempting to spawn incompatible partitions");
			}
			// Spawn a complete node -> no partition handling
		}
		else
		// Spawn a complete node -> no partition handling
		{
			spawnParentNode = node.getParentJDF();

			if (spawnParentNode == null)
			{
				throw new JDFException("JDFNode.Spawn cannot spawn unpartitioned root node");
			}
		}

		// setup the ancestor nodes
		setSpawnParent(rootOut, spawnParentNode);

		final JDFSpawned spawnAudit = createSpawnAudit(rootOut, spawnID, spawnParentNode);

		// find resources that must be copied
		addSpawnedResources(rootOut, spawnAudit);
		finalizeSpawn(rootOut, spawnAudit);

		// return the spawned node
		return rootOut;
	}

	/**
	 * prepare all nodeInfos of node for partitioned spawn
	 */
	private void prepareNodeInfos()
	{
		if (vSpawnParts == null)
		{
			return;
		}
		final VElement vn = node.getvJDFNode(null, null, false);
		final int size = vn.size();
		// fill all resources and all links of all children into vResPool and
		// links
		for (int i = 0; i < size; i++)
		{
			final JDFNode vnNode_i = (JDFNode) vn.elementAt(i);
			vnNode_i.prepareNodeInfo(vSpawnParts); // make sure we have a
			// nodeinfo in all spawned
			// nodes of main in case we
			// have to merge stati
		}
	}

	/**
	 * check for multiple rw resources and throw a JDFException if an rw resource
	 */
	private void checkMultipleRWRes()
	{
		// only check if not explicitly requested not to check
		final Collection vCheck = checkSpawnedResources();
		if (vCheck != null)
		{
			String strIDs = "JDFNode.spawn: multiply spawned rw resources: ";
			final VString vBad = new VString();
			final Iterator<JDFResource> iterCheck = vCheck.iterator();
			while (iterCheck.hasNext())
			{
				vBad.appendUnique((iterCheck.next()).getAttribute(AttributeName.ID));
			}
			strIDs += StringUtil.setvString(vBad, JDFConstants.BLANK, null, null);
			throw new JDFException(strIDs, exMultiSpawnRW);
		}
	}

	/**
	 * return the resources that would be spawned RW multiple times
	 * 
	 * @param VString vRWResourceUsage: vector of resource names and Usage / ProcessUsage that are spawned as rw <br>
	 * the format is one of:<br>
	 * ResName<br>
	 * ResName:Input<br>
	 * ResName:Output<br>
	 * ResName:ProcessUsage<br>
	 * ID<br>
	 * @param VJDFAttributeMap vParts: vector of JDFAttributeMaps that describe the parts to spawn
	 * 
	 * @return Collection: set of resources or resource partitions that would be spawned rw multiple times null if all is well
	 */
	public Collection checkSpawnedResources()
	{
		final VString vRWResources = new VString(vRWResources_in);
		final HashSet v = new LinkedHashSet();
		// grab the root node and all it's children

		final HashSet vRootLinks = node.getAllRefs(null, true);
		final Iterator<JDFElement> iter = vRootLinks.iterator();
		while (iter.hasNext())
		{
			final JDFElement liRoot = iter.next();
			JDFResource r = null;
			boolean bResRW = false;
			if (liRoot instanceof JDFResourceLink)
			{
				bResRW = linkFitsRWRes((JDFResourceLink) liRoot, vRWResources);
				if (bResRW)
				{
					final JDFResourceLink rl = (JDFResourceLink) liRoot;
					r = rl.getTarget();
				}
			}
			else if (liRoot instanceof JDFRefElement)
			{
				final JDFRefElement re = (JDFRefElement) liRoot;
				r = re.getTarget();
				if (r != null)
				{
					bResRW = resFitsRWRes(r, vRWResources);
				}
			}
			if (bResRW && r != null)
			{
				VElement vRes = new VElement();
				if (vSpawnParts == null || vSpawnParts.isEmpty())
				{
					vRes = r.getLeaves(false);
				}
				else
				{
					for (int j = 0; j < vSpawnParts.size(); j++)
					{
						vRes.appendUnique(r.getPartitionVector(vSpawnParts.elementAt(j), null));
					}
				}
				for (int k = 0; k < vRes.size(); k++)
				{
					final JDFResource rTarget = (JDFResource) vRes.elementAt(k);
					if (rTarget.getSpawnStatus() == JDFResource.EnumSpawnStatus.SpawnedRW)
					{
						if (!v.contains(rTarget))
						{
							v.add(rTarget);
						}
					}
				}
			}
		}
		// empty if all is well
		return v.isEmpty() ? null : v;
	}

	// ///////////////////////////////////////////////////////////////////////

	private VElement prepareSpawnLinks(final JDFNode rootOut)
	{
		final VElement vn = rootOut.getvJDFNode(null, null, false);
		final int size = vn.size();
		final VElement outLinks = new VElement();

		// fill all links of all children of rootOut into vOutRes and outLinks
		for (int i = 0; i < size; i++)
		{
			final JDFNode vnNode_i = (JDFNode) vn.elementAt(i);
			outLinks.appendUnique(vnNode_i.getResourceLinks(null));
		}
		return outLinks;
	}

	/**
	 * 
	 * @param parent
	 * @param url
	 * @param vSpawnParts
	 * @param bCopyNodeInfo
	 * @param bCopyCustomerInfo
	 * @param bCopyComments
	 */
	private void setSpawnParent(final JDFNode rootOut, final JDFNode parent)
	{
		final VString vs = parent.getAncestorIDs();
		JDFAncestorPool ancestorPool = parent.getJDFRoot().getAncestorPool();
		String lastAncestorID = JDFConstants.EMPTYSTRING;

		if (!(parent.equals(node))) // only do this if we are not spawning
		// parallel
		{
			rootOut.removeChild(ElementName.ANCESTORPOOL, null, 0); // just in
			// case
			if (parent.getJDFRoot().hasChildElement(ElementName.ANCESTORPOOL, null))
			{
				ancestorPool = (JDFAncestorPool) rootOut.copyElement(ancestorPool, null);
				final int numAncestors = ancestorPool.numChildElements(ElementName.ANCESTOR, null);
				if (numAncestors > 0)
				{
					lastAncestorID = ancestorPool.getAncestor(numAncestors - 1).getNodeID();
				}
			}
		}
		ancestorPool = rootOut.getCreateAncestorPool();
		ancestorPool.setPartMapVector(vSpawnParts);

		// avoid double counting of this node's root element in case of
		// partitioned spawning
		int startAncestorLoop = 0;
		if ((vs.size() > 0) && ((vs.elementAt(0)).equals(lastAncestorID)))
		{
			startAncestorLoop = 1;
		}

		// 010702 RP reversed in getAncestorIDs: the last in the list is the
		// actual
		for (int i = startAncestorLoop; i < vs.size(); i++)
		{
			final JDFAncestor ancestor = ancestorPool.appendAncestor();
			ancestor.setNodeID(vs.elementAt(i));
			if (i == 0)
			{ // first in list is the parent
				if (parentURL != null && !parentURL.equals(JDFConstants.EMPTYSTRING))
				{
					ancestor.setFileName(parentURL);
				}
			}
		}
		rootOut.setJobID(parent.getJobID(true));

		// 180502 RP added line
		ancestorPool.copyNodeData(parent, bCopyNodeInfo, bCopyCustomerInfo, bCopyComments);
	}

	// ///////////////////////////////////////////////////////////////////////

	private JDFSpawned createSpawnAudit(final JDFNode rootOut, final String spawnID, final JDFNode spawnParentNode)
	{
		// throw in the audits
		final JDFAuditPool p = spawnParentNode.getCreateAuditPool();
		final JDFSpawned spawnAudit = p.addSpawned(rootOut, null, null, null, null);

		// 210302 RP added if statement
		if (spawnURL != null && !spawnURL.equals(JDFConstants.EMPTYSTRING))
		{
			if (spawnURL.indexOf("://") == -1)
			{
				spawnAudit.setURL("file://" + spawnURL);
			}
			else
			{
				spawnAudit.setURL(spawnURL);
			}
		}
		spawnAudit.setNewSpawnID(spawnID);
		return spawnAudit;
	}

	/**
	 * add any resources that live in ancestor nodes to this node
	 * 
	 * @param spawnAudit :
	 * @param root :
	 * @param vRWresources :XXX
	 * @param vParts :
	 * @param bSpawnROPartsOnly if true, only the parts of RO resources that are specified in vParts are spawned, else the complete resource is spawned
	 * @return int: number of resources added to the spawned node
	 */
	private int addSpawnedResources(final JDFNode rootOut, final JDFSpawned spawnAudit)
	{
		final VString vRWResources = new VString(vRWResources_in);
		int nSpawned = 0;
		final JDFResourcePool rPool = rootOut.getCreateResourcePool();

		// must copy the ap to the nood to have a decent hook on ap referenced
		// resources
		JDFAncestorPool ap = rootOut.getAncestorPool();
		if (ap != null)
		{
			ap = (JDFAncestorPool) node.copyElement(ap, null);
		}

		// grab the root node and all it's children

		final HashSet vRootLinks = node.getAllRefs(null, false);

		// create a HashSet with all IDs of the newly created Node
		final HashSet<String> allIDsCopied = rootOut.fillHashSet(AttributeName.ID, null);

		final String spawnID = spawnAudit.getNewSpawnID();

		// first check only read only resources, since there may be a collision
		for (int loopRORW = 0; loopRORW < 2; loopRORW++)
		{
			// loop over all links
			final Iterator<JDFElement> iter = vRootLinks.iterator();
			while (iter.hasNext())
			{
				final JDFElement liRoot = iter.next();

				// test for direct children of resourcepool - these will be
				// added later
				if (liRoot.getDeepParent(ElementName.RESOURCEPOOL, 0) != null)
				{
					continue;
				}

				final String refID = liRoot.getAttribute(AttributeName.RREF); // must be either refelem or link, therefore will have rref

				boolean bResRW = false;
				JDFResource rRoot = null;
				if (liRoot instanceof JDFResourceLink)
				{
					bResRW = linkFitsRWRes((JDFResourceLink) liRoot, vRWResources);
				}
				else if (liRoot instanceof JDFRefElement)
				{
					rRoot = (JDFResource) node.getTarget(refID, AttributeName.ID);
					bResRW = resFitsRWRes(rRoot, vRWResources);
				}

				// do only RO on the first loop and only RW on the second
				if (bResRW != (loopRORW != 0))
				{
					continue;
				}

				// 091204 RP bug fix for multiple deep copies
				// don't copy if it is already there!
				final boolean isThereAlready = allIDsCopied.contains(refID);
				final JDFResource.EnumSpawnStatus copyStatus = bResRW ? JDFResource.EnumSpawnStatus.SpawnedRW : JDFResource.EnumSpawnStatus.SpawnedRO;

				final HashSet vvRO = new LinkedHashSet();
				final HashSet vvRW = new LinkedHashSet();
				if (rRoot == null)
				{
					rRoot = (JDFResource) node.getTarget(refID, AttributeName.ID);
				}

				// check for null and throw an exception in picky mode
				if (rRoot == null)
				{
					continue;
				}
				// 080505 must always check existing resources, otherwise we can
				// lose references
				// if(!isThereAlready)
				// {
				// copy any missing linked resources, just in case
				// the root is in the original jdf and can be used as a hook to
				// the original document
				// get a list of all resources referenced by this link
				// always do a copyresource in case some dangling rRefs are
				// waiting
				copySpawnedResource(rPool, rRoot, copyStatus, spawnID, vRWResources, vvRW, vvRO, allIDsCopied);
				nSpawned += vvRO.size() + vvRW.size();
				// }
				// else
				// {
				if (isThereAlready && bResRW)
				{
					vvRW.add(rRoot.getID());
				}
				// }
				VString rRefsRW = spawnAudit.getrRefsRWCopied();
				VString rRefsRO = spawnAudit.getrRefsROCopied();
				Iterator<String> iterRefs = vvRW.iterator();
				while (iterRefs.hasNext())
				{
					final String s = iterRefs.next();
					rRefsRW.add(s);
					final int ind = rRefsRO.index(s);
					if (ind >= 0)
					{
						rRefsRO.remove(ind);
					}
				}
				iterRefs = vvRO.iterator();
				while (iterRefs.hasNext())
				{
					final String s = iterRefs.next();
					rRefsRO.add(s);
				}
				rRefsRO.unify();
				rRefsRW.unify();
				if (rRefsRO.isEmpty())
				{
					rRefsRO = null;
				}
				if (rRefsRW.isEmpty())
				{
					rRefsRW = null;
				}

				spawnAudit.setrRefsROCopied(rRefsRO);
				spawnAudit.setrRefsRWCopied(rRefsRW);
				// get the effected resources
				VElement vRes = new VElement();
				VElement vResRoot = new VElement();
				if (liRoot instanceof JDFResourceLink)
				{
					final JDFResourceLink liRootLink = (JDFResourceLink) liRoot;
					final VJDFAttributeMap vLinkMap = liRootLink.getPartMapVector();
					// make sure that spawned resources are sufficiently
					// partitioned if spawning rw so that no merge conflicts
					// arise
					// create a temporary dummy copy of the link so that we have
					// a guaranteed copy that behaves the same as the original
					final JDFResourceLink dummy = (JDFResourceLink) rootOut.getCreateResourceLinkPool().copyElement(liRoot, null);
					fixResLinks(rootOut, bResRW, liRootLink, dummy);
					// reduce partitions in main so that the links remain
					// consistent
					liRootLink.setPartMapVector(vSpawnParts);
					dummy.setPartMapVector(vSpawnParts);

					vResRoot = ((JDFResourceLink) liRoot).getTargetVector(-1);

					vRes = dummy.getTargetVector(-1);
					dummy.deleteNode();
					// reset partitions in main
					liRootLink.setPartMapVector(vLinkMap);

				}
				else if (liRoot instanceof JDFRefElement)
				{
					vResRoot.add(((JDFRefElement) liRoot).getTarget());

					// create a temporary dummy copy of the link so that we have
					// a gauranteed copy that behaves the same as the original
					final JDFRefElement dummy = (JDFRefElement) rootOut.copyElement(liRoot, null);
					vRes.add(dummy.getTarget());
					dummy.deleteNode();
				}
				else
				{
					continue; // snafu - should never get here
				}
				addIdentical(vResRoot);
				addIdentical(vRes);

				// fixed not to crash with corrupt jdfs.
				// Now just continue and ignore the corrupt resource
				final int siz = vRes.size() < vResRoot.size() ? vRes.size() : vResRoot.size();

				// loop over all partitions
				for (int resParts = 0; resParts < siz; resParts++)
				{
					final JDFResource r = (JDFResource) vRes.elementAt(resParts);
					final JDFResource rRoot1 = (JDFResource) vResRoot.elementAt(resParts);

					spawnPart(rRoot1, spawnID, copyStatus, true);
					spawnPart(r, spawnID, copyStatus, false);

					if (vSpawnParts != null && vSpawnParts.size() != 0 && (bResRW || bSpawnROPartsOnly))
					{
						// reduce partitions of all RW resources and of RO
						// resources if requested
						// r.getResourceRoot().reducePartitions(vSpawnParts);
						reducePartitions(r.getResourceRoot());
					}
				}
			}
		}

		// must remove ap after use
		if (ap != null)
		{
			ap.deleteNode();
		}

		return nSpawned;
	}

	/**
	 * @param res
	 */
	private void addIdentical(final VElement vRes)
	{
		if (vRes == null || vRes.size() == 0 || vSpawnParts == null || vSpawnParts.size() == 0)
		{
			return;
		}
		final JDFResource root = ((JDFResource) vRes.get(0)).getResourceRoot();
		final VElement identicals = root.getChildrenByTagName(ElementName.IDENTICAL, null, null, false, true, -1, false);
		if (identicals == null || identicals.size() == 0)
		{
			return;
		}

		for (int i = 0; i < identicals.size(); i++)
		{
			final JDFIdentical ident = (JDFIdentical) identicals.get(i);
			final JDFResource identParent = (JDFResource) ident.getParentNode_KElement();
			final JDFAttributeMap identMap = identParent.getPartMap();
			for (int j = 0; j < vSpawnParts.size(); j++)
			{
				final JDFAttributeMap mapPart = vSpawnParts.elementAt(j);
				if (mapPart.subMap(identMap))
				{
					vRes.add(identParent);
				}
			}
		}

	}

	/**
	 * @param rootOut
	 * @param bResRW
	 * @param liRootLink
	 * @param dummy
	 */
	private void fixResLinks(final JDFNode rootOut, final boolean bResRW, final JDFResourceLink liRootLink, final JDFResourceLink dummy)
	{
		if (bFixResources && vSpawnParts != null && vSpawnParts.size() != 0 && bResRW)
		{
			final VString rootPartIDKeys = rootOut.getJDFRoot().getPartIDKeys(vSpawnParts.elementAt(0));
			final JDFResource linkRoot = liRootLink.getLinkRoot();
			if (linkRoot != null)
			{
				try
				{
					linkRoot.createPartitions(vSpawnParts, rootPartIDKeys);
				}
				catch (final JDFException x)
				{
					for (int i = 0; i < vSpawnParts.size(); i++)
					{
						fixSpawnPartitions(linkRoot.getPartition(vSpawnParts.elementAt(i), null), rootPartIDKeys);
					}
				}
			}
			final JDFResource dummyRoot = dummy.getLinkRoot();
			if (dummyRoot != null)
			{
				try
				{
					dummyRoot.createPartitions(vSpawnParts, rootPartIDKeys);
				}
				catch (final JDFException x)
				{
					for (int i = 0; i < vSpawnParts.size(); i++)
					{
						fixSpawnPartitions(dummyRoot.getPartition(vSpawnParts.elementAt(i), null), rootPartIDKeys);
					}
				}
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////

	private void finalizeSpawn(final JDFNode rootOut, final JDFSpawned spawnAudit)
	{
		final VElement outLinks = prepareSpawnLinks(rootOut);
		final VElement mainLinks = prepareSpawnLinks(node);
		final int mainLinkLen = mainLinks.size();

		final String spawnID = spawnAudit.getNewSpawnID();
		// add parts to resource links if necessary
		if (vSpawnParts != null && !vSpawnParts.isEmpty())
		{
			final int outLinkSize = outLinks.size();
			for (int i = 0; i < outLinkSize; i++)
			{
				JDFResourceLink link = (JDFResourceLink) outLinks.elementAt(i);
				final JDFResource r = link.getLinkRoot();

				// 2005-03-11 KM if the link is null continue, the JDF ist
				// invalid but in
				// the best case only an audit is missing and the JDF is still
				// operable
				// in the worst caste the spawned JDF is not executable at all
				if (r != null)
				{
					final VJDFAttributeMap vPartMap = getSpawnLinkMap(vSpawnParts, r);
					if (!vPartMap.isEmpty())
					{
						final VJDFAttributeMap vNewMap = getSpawnedLinkPartMap(link, vPartMap);
						link.setPartMapVector(vNewMap);
						updateSpawnIDs(spawnID, link);
						final String id = link.getrRef();
						if (id != null)
						{
							link = (JDFResourceLink) mainLinks.elementAt(i);

							if (id.equals(link.getrRef()))
							{
								updateSpawnIDsInMain(spawnID, link, vPartMap);
							}
							else
							// the sequence of links changed - must search,
							// hopefully we never get here
							{
								for (int ii = 0; ii < mainLinkLen; ii++)
								{
									link = (JDFResourceLink) mainLinks.elementAt(ii);
									if (id.equals(link.getrRef()))
									{
										updateSpawnIDsInMain(spawnID, link, vPartMap);
									}
								}
							}
						}
					}
				}
			}
		}
		finalizeStatusAndAudits(spawnAudit);
	}

	/**
	 * update only local resources
	 * 
	 * @param spawnID
	 * @param link
	 * @param vPartMap
	 */
	private void updateSpawnIDsInMain(final String spawnID, final JDFResourceLink link, final VJDFAttributeMap vPartMap)
	{
		final JDFResource rMain = link.getLinkRoot();
		for (int k = 0; k < vPartMap.size(); k++)
		{
			final VElement vMainPart = rMain.getPartitionVector(vPartMap.elementAt(k), null);
			for (int kk = 0; kk < vMainPart.size(); kk++)
			{
				final JDFResource rMainPart = (JDFResource) vMainPart.elementAt(kk);
				if (rMainPart == null)
				{
					continue;
				}

				final VElement leaves = rMainPart.getLeaves(true);
				boolean bSpawnID = false;

				// if any child node or leaf has this spawnID we need not do
				// anything
				for (int kkk = 0; kkk < leaves.size(); kkk++)
				{
					final JDFResource rMainLeaf = (JDFResource) leaves.elementAt(kkk);
					bSpawnID = rMainLeaf.includesMatchingAttribute(AttributeName.SPAWNIDS, spawnID, EnumAttributeType.NMTOKENS);
					if (bSpawnID)
					{
						break;
					}
				}
				if (!bSpawnID)
				{
					rMainPart.appendSpawnIDs(spawnID);
					rMainPart.setLocked(true);
					rMainPart.setSpawnStatus(EnumSpawnStatus.SpawnedRW);
				}
			}
		}
	}

	/**
	 * @param link
	 * @param vPartMap
	 * @return
	 */
	private VJDFAttributeMap getSpawnedLinkPartMap(final JDFResourceLink link, final VJDFAttributeMap vPartMap)
	{
		final VJDFAttributeMap vLinkMap = link.getPartMapVector();
		VJDFAttributeMap vNewMap = new VJDFAttributeMap();

		if (vLinkMap == null)
		{
			vNewMap = vPartMap;
		}
		else
		{
			for (int l = 0; l < vLinkMap.size(); l++)
			{
				for (int k = 0; k < vPartMap.size(); k++)
				{
					JDFAttributeMap m = new JDFAttributeMap(vPartMap.elementAt(k));
					m = m.orMap(vLinkMap.elementAt(l));

					if (!m.isEmpty())
					{
						vNewMap.appendUnique(m);
					}
				}
			}
		}
		return vNewMap;
	}

	/**
	 * @param spawnID
	 * @param link
	 */
	private void updateSpawnIDs(final String spawnID, final JDFResourceLink link)
	{
		final VElement vRes = link.getTargetVector(-1);
		for (int t = 0; t < vRes.size(); t++)
		{
			final JDFResource res = (JDFResource) vRes.elementAt(t);
			// only fix those local resources that haven't been fixed along the
			// way...
			if (!res.includesMatchingAttribute(AttributeName.SPAWNIDS, spawnID, EnumAttributeType.NMTOKENS))
			{
				res.appendSpawnIDs(spawnID);
				res.setLocked(false);
			}
		}
	}

	/**
	 * @param vLocalSpawnParts
	 * @param spawnAudit
	 */
	private void finalizeStatusAndAudits(final JDFSpawned spawnAudit)
	{
		// add partition information to the audits and StatusPool or NodeInfo
		// 050906 RP move to the back so that it occurs after any global
		// resources have been copied
		if (vSpawnParts != null && !vSpawnParts.isEmpty())
		{
			spawnAudit.setPartMapVector(vSpawnParts);

			final EnumNodeStatus partStatus = node.getPartStatus(vSpawnParts.elementAt(0));
			if (partStatus != null)
			{
				spawnAudit.setStatus(partStatus);
			}
			node.setPartStatus(vSpawnParts, EnumNodeStatus.Spawned, null);
		}
		else
		// No partitioning - set Audit + Status globally
		{
			final EnumNodeStatus status = node.getStatus();
			if (status != null)
			{
				spawnAudit.setStatus(status);
			}
			node.setStatus(JDFElement.EnumNodeStatus.Spawned);
		}
	}

	/**
	 * @param vLocalSpawnParts
	 * @param r
	 * @return
	 */
	private VJDFAttributeMap getSpawnLinkMap(final VJDFAttributeMap vLocalSpawnParts, final JDFResource r)
	{
		final VJDFAttributeMap vPartMap = new VJDFAttributeMap(vLocalSpawnParts);

		// 160802 RP leave implied resource link parts if PartUsage=implicit
		if (!r.getPartUsage().equals(JDFResource.EnumPartUsage.Implicit))
		{
			final VString vPartKeys = r.getPartIDKeys();
			final Vector<ValuedEnum> vImplicitPartitions = r.getImplicitPartitions();
			if (vImplicitPartitions != null)
			{
				for (int ii = 0; ii < vImplicitPartitions.size(); ii++)
				{
					final JDFResource.EnumPartIDKey e = (JDFResource.EnumPartIDKey) vImplicitPartitions.elementAt(ii);
					vPartKeys.add(e.getName());
				}
			}
			vPartMap.reduceMap(vPartKeys.getSet());
		}
		return vPartMap;
	}

	/**
	 * calculate whether a link should be RW or RO
	 * 
	 * @param JDFResourceLink li the link to check
	 * @param VString vRWResources the list of resource ids, process usages, usages, names. If any match, the referenced resource is considered rw
	 * @return boolean true if rw
	 */
	private boolean linkFitsRWRes(final JDFResourceLink li, final VString vRWResources)
	{
		boolean bResRW = vRWResources.contains(li.getNamedProcessUsage());
		// 200602 RP added fix
		if (!bResRW)
		{
			bResRW = vRWResources.contains(li.getLinkedResourceName());
		}

		// 230802 RP added check for ID in vRWResources
		if (!bResRW)
		{
			bResRW = vRWResources.contains(li.getrRef());
		}

		// 040902 RP added check for Usage in vRWResources
		if (!bResRW)
		{
			bResRW = vRWResources.contains(li.getAttribute(AttributeName.USAGE));
		}
		return bResRW;
	}

	/**
	 * Reduces partition so that only the parts that overlap with vResources remain
	 * @param r
	 * @param nodeName
	 * @param nsURI
	 * @param partIDKeys
	 * @param partIDPos
	 * @param parentMap
	 * @param identical
	 * @return the reduced partitions
	 */
	private VElement reducePartitions(final JDFResource r, final String nodeName, final String nsURI, final VString partIDKeys, final int partIDPos, final JDFAttributeMap parentMap,
			final VElement identical)
	{
		final VElement bad = new VElement();
		final VElement children = r.getChildElementVector_KElement(nodeName, nsURI, null, true, -1);
		if (children != null)
		{
			final int kidSize = children.size();
			for (int i = 0; i < kidSize; i++)
			{
				final JDFResource child = (JDFResource) children.elementAt(i);
				final String key = partIDKeys.get(partIDPos);
				if (key != null)
				{
					final String val = child.getAttribute_KElement(key, null, null);
					if (val != null)
					{
						final JDFAttributeMap testMap = new JDFAttributeMap(parentMap);
						testMap.put(key, val);
						if (overlapsPartMap(testMap))
						{
							final JDFIdentical id = child.getIdentical();
							if (id != null)
							{
								final JDFResource resourceRoot = r.getResourceRoot();
								JDFResource partition = resourceRoot.getPartition(testMap, null);
								while (partition != resourceRoot)
								{
									identical.add(partition);
									partition = (JDFResource) partition.getParentNode_KElement();
								}
							}
							if (partIDPos + 1 < partIDKeys.size())
							{
								bad.appendUnique(reducePartitions(child, nodeName, nsURI, partIDKeys, partIDPos + 1, testMap, identical));
							}
						}
						else
						{
							bad.add(child);
						}
					}
				}
			}
		}
		return bad;
	}

	/**
	 * @param testMap
	 * @return
	 */
	private boolean overlapsPartMap(final JDFAttributeMap testMap)
	{
		if (vSpawnParts == null || testMap == null)
		{
			return true;
		}
		final VString keys = testMap.getKeys();
		final int ks = keys.size();

		for (int i = 0; i < vSpawnParts.size(); i++)
		{
			boolean bOK = true;
			final JDFAttributeMap map = vSpawnParts.elementAt(i);
			for (int j = 0; j < ks; j++)
			{
				final String key = keys.stringAt(j);
				final String linkValue = map.get(key);
				if (linkValue != null && !JDFPart.matchesPart(key, testMap.get(key), linkValue))
				{
					bOK = false;
					break;
				}
			}
			if (bOK)
			{
				return true;
			}
		}
		return ks <= 0;
	}

	private void reducePartitions(final JDFResource r)
	{
		if (r == null || vSpawnParts == null || vSpawnParts.size() == 0)
		{
			return;
		}

		final VString partIDKeys = r.getPartIDKeys();
		if (partIDKeys == null || partIDKeys.size() == 0)
		{
			return;
		}

		// in case we spawn lower than, only remove partitions that are parallel to our spawning
		int nMax = 999;
		for (int i = 0; i < vSpawnParts.size(); i++)
		{
			final VElement vSubParts = r.getPartitionVector(vSpawnParts.elementAt(i), EnumPartUsage.Implicit);
			for (int j = 0; j < vSubParts.size(); j++)
			{
				final JDFResource rr = (JDFResource) vSubParts.get(j);
				final JDFAttributeMap partMap = rr.getPartMap();
				final int mapSize = partMap == null ? 0 : partMap.size();
				if (mapSize < nMax)
				{
					nMax = mapSize;
					if (nMax == 0)
					{
						return; // nothing left to do
					}
				}
			}
		}
		for (int i = partIDKeys.size() - 1; i >= nMax; i--)
		{
			partIDKeys.remove(i);
		}

		final String nodeName = r.getLocalName();
		final String nsURI = r.getNamespaceURI();
		final VElement identical = new VElement();
		final VElement vBad = reducePartitions(r, nodeName, nsURI, partIDKeys, 0, new JDFAttributeMap(), identical);
		vBad.removeAll(identical);
		for (int i = 0; i < vBad.size(); i++)
		{
			vBad.get(i).deleteNode();
		}

	}

	/**
	 * calculate whether a link should be RW or RO
	 * 
	 * @param JDFResourceLink li the link to check
	 * @param VString vRWResources the list of resource ids, process usages, usages, names. If any match, the referenced resource is considered rw
	 * @return boolean true if rw
	 */
	private boolean resFitsRWRes(final JDFResource r, final VString vRWResources)
	{
		if (r == null)
		{
			return false;
		}
		boolean bResRW = vRWResources.contains(r.getLocalName());
		// 200602 RP added fix
		if (!bResRW)
		{
			bResRW = vRWResources.contains(JDFConstants.STAR);
		}

		// 230802 RP added check for ID in vRWResources
		if (!bResRW)
		{
			bResRW = vRWResources.contains(r.getID());
		}

		return bResRW;
	}

	/**
	 * copies a resource recursively and optionally fixes status flags and locks in the source resource
	 * 
	 * @param p the pool to copy r into
	 * @param r the resource to copy
	 * @param copyStatus rw or ro
	 * @param vParts part map vector of the partitions to copy
	 * @param spawnID the spawnID of the spawning that initiated the copy
	 * @param resInPool internal recursion checker
	 * @param vRWResources write resources
	 * 
	 * @return VString vector of resource names that have been copied
	 */
	private void copySpawnedResource(final JDFResourcePool p, final JDFResource r, final JDFResource.EnumSpawnStatus copyStatus, final String spawnID, final VString vRWResources,
			final HashSet vRWIDs, final HashSet vROIDs, final HashSet allIDsCopied)
	{
		JDFResource.EnumSpawnStatus copyStatusLocal = copyStatus;

		if (r == null)
		{
			return;
		}

		// r is not yet here copy r
		final boolean bRW = copyStatusLocal == JDFResource.EnumSpawnStatus.SpawnedRW;
		final String rID = r.getID();
		if (!allIDsCopied.contains(rID))
		{
			final JDFResource rNew = (JDFResource) p.copyElement(r, null);
			// if spawning, fix stati and locks

			if (bRW || bSpawnROPartsOnly)
			{
				reducePartitions(rNew);
			}

			spawnPart(r, spawnID, copyStatusLocal, true);
			spawnPart(rNew, spawnID, copyStatusLocal, false);

			if (bRW)
			{
				vRWIDs.add(rID);
			}
			else
			{
				vROIDs.add(rID);
			}

			allIDsCopied.add(rID);
		}

		final VString vs = r.getHRefs(new VString(), false, false);
		// add recursively copied resource references
		final int size = vs.size();
		for (int i = 0; i < size; i++)
		{
			final String id = vs.elementAt(i);

			// the referenced resource is already in this pool - continue
			if (!allIDsCopied.contains(id))
			{
				// 071101 RP added r is by definition in the original document
				// which also contains the rrefs elements
				final JDFResource next = ((JDFNode) r.getDocRoot()).getTargetResource(id);

				// and now all those interlinked resources
				if (next != null)
				{
					// only copy refelements rw if they are explicitly in the
					// list
					if (bRW)
					{
						copyStatusLocal = resFitsRWRes(next, vRWResources) ? JDFResource.EnumSpawnStatus.SpawnedRW : JDFResource.EnumSpawnStatus.SpawnedRO;
					}
					// recurse into refelements
					copySpawnedResource(p, next, copyStatusLocal, spawnID, vRWResources, vRWIDs, vROIDs, allIDsCopied);
				}
			}
		}

		return;
	}

	/**
	 * @param r
	 * @param spawnID
	 * @param copyStatus
	 * @param vParts
	 */
	private void spawnPart(final JDFResource r, final String spawnID, final JDFResource.EnumSpawnStatus copyStatus, final boolean bStayinMain)
	{
		if (vSpawnParts != null && vSpawnParts.size() > 0)
		{
			final int size = vSpawnParts.size();
			// loop over all part maps to get best matching resource
			for (int j = 0; j < size; j++)
			{
				final VElement vSubParts = r.getPartitionVector(vSpawnParts.elementAt(j), EnumPartUsage.Implicit);
				// always implicit - in the worst case some partitions may be multiply spawned
				for (int k = 0; k < vSubParts.size(); k++)
				{
					final JDFResource pLeaf = (JDFResource) vSubParts.item(k);
					if (pLeaf != null)
					{
						// set the lock of the leaf to true if it is RO, else
						// unlock it
						if (bStayinMain)
						{
							if ((copyStatus == EnumSpawnStatus.SpawnedRW) || (pLeaf.getSpawnStatus() != EnumSpawnStatus.SpawnedRW))
							{
								pLeaf.setSpawnStatus(copyStatus);
								pLeaf.setLocked(copyStatus == EnumSpawnStatus.SpawnedRW);
							}
							pLeaf.appendSpawnIDs(spawnID);
						}
						else
						{
							pLeaf.setLocked(copyStatus != EnumSpawnStatus.SpawnedRW);
							pLeaf.setSpawnIDs(new VString(spawnID, null));
						}
					}
				}
			}
		}
		else
		// no partitions
		{
			if (bStayinMain)
			{
				if ((copyStatus == EnumSpawnStatus.SpawnedRW) || (r.getSpawnStatus() != EnumSpawnStatus.SpawnedRW))
				{
					r.setSpawnStatus(copyStatus);
					r.setLocked(copyStatus == EnumSpawnStatus.SpawnedRW);
				}
			}
			else
			{
				r.setLocked(copyStatus != EnumSpawnStatus.SpawnedRW);
			}

			if (bStayinMain)
			{
				r.appendSpawnIDs(spawnID);
			}
			else
			{
				r.setSpawnIDs(new VString(spawnID, null));
			}
		}
	}

	private void fixSpawnPartitions(final JDFResource r, final VString rootPartIDKeys)
	{
		if (r == null)
		{
			return;
		}
		final VString oldParts = r.getPartIDKeys();
		if (oldParts.containsAny(rootPartIDKeys))
		{
			throw new JDFException("fixSpawnPartitions - adding incompatible resources");
		}

		// move away all preexisting partititons and dump them int the new nodes
		final VElement ve = r.getChildElementVector_KElement(r.getNodeName(), r.getNamespaceURI(), null, true, 9999999);
		final KElement[] tmp = new KElement[ve.size()];
		for (int i = 0; i < ve.size(); i++)
		{
			tmp[i] = ve.item(i).deleteNode();
		}
		r.removeAttribute(AttributeName.PARTIDKEYS);
		final VElement vNew = r.getResourceRoot().createPartitions(vSpawnParts, rootPartIDKeys);

		// copy initial leaves into the newly created stuff
		for (int i = 0; i < vNew.size(); i++)
		{
			for (int j = 0; j < tmp.length; j++)
			{
				vNew.item(i).copyElement(tmp[j], null);
			}
		}

		// fix partidkeys
		final VString partIDKeys = r.getPartIDKeys();
		partIDKeys.appendUnique(oldParts);
		r.setPartIDKeys(partIDKeys);
	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * spawn a node in informative mode without modifying the root JDF; url is the file name of the new node, the parameters except for the list of rw
	 * resources, which are by definition empty, are identical to those of Spawn
	 * 
	 * vRWResourceUsage is the vector of Resources Usages, Resource Names or Resource IDs that are spawned RW, all others are spawned read only; vParts is the
	 * vector of part maps that are to be spawned, defaults to no part, i.e. the whole thing
	 * 
	 * @return JDFDoc: The spawned node's owner document.
	 * 
	 * 
	 */
	public JDFNode spawnInformative()
	{
		final JDFDoc docNew = new JDFDoc(ElementName.JDF);
		JDFNode rootOut = (JDFNode) docNew.getRoot();
		final JDFNode thisRoot = node.getJDFRoot();
		// merge this node into it
		rootOut.mergeNode(thisRoot, false);
		final JDFNode copyOfThis = rootOut.getChildJDFNode(node.getID(), false);
		final JDFNode tmp = node;
		node = copyOfThis;
		JDFNode nodeNew = null;
		final VString vRWTmp = vRWResources_in;
		final boolean spawnMultKeep = bSpawnRWPartsMultiple;
		bSpawnRWPartsMultiple = true; // never check multiple resources when
		// spawning informative - who cares
		nodeNew = spawn();
		bSpawnRWPartsMultiple = spawnMultKeep;
		rootOut = nodeNew.getRoot();
		rootOut.setActivation(EnumActivation.Informative);
		node = tmp;

		vRWResources_in = vRWTmp;

		return nodeNew;
	}

	/**
	 * spawn a node; url is the file name of the new node, vRWResourceUsage is the vector of Resources Usages (or Names if no usage exists for the process) that
	 * are spawned RW, all others are spawned read only; vParts is the vector of part maps that are to be spawned, defaults to no part, i.e. the whole thing
	 * 
	 * @param parentURL
	 * @param spawnURL : URL of the spawned JDF file
	 * @param vRWResources : vector of resource names and Usage / ProcessUsage that are spawned as rw <br>
	 * the format is one of:<br>
	 * ResName:Input<br>
	 * ResName:Output<br>
	 * ResName:ProcessUsage<br>
	 * @param VJDFAttributeMap vSpawnParts: vector of mAttributes that describe the parts to spawn, only valid PartIDKeys are allowed
	 * @param bSpawnROPartsOnly if true, only the parts of RO resources that are specified in vParts are spawned, else the complete resource is spawned
	 * @param bCopyNodeInfo copy the NodeInfo elements into the Ancestors
	 * @param bCopyCustomerInfo copy the CustomerInfo elements into the Ancestors
	 * @param bCopyComments copy the Comment elements into the Ancestors
	 * 
	 * @return The spawned node
	 * @since 050831 added bCopyComments @ tbd enhance nested spawning of partitioned nodes default: spawn(parentURL, null, null, null, false, false, false,
	 * false)
	 */
	public JDFNode spawn(final String _parentURL, final String _spawnURL, final Vector _vRWResources_in, final VJDFAttributeMap _vSpawnParts, final boolean _bSpawnROPartsOnly,
			final boolean _bCopyNodeInfo, final boolean _bCopyCustomerInfo, final boolean _bCopyComments)
	{
		bCopyComments = _bCopyComments;
		bCopyCustomerInfo = _bCopyCustomerInfo;
		bCopyNodeInfo = _bCopyNodeInfo;
		bSpawnROPartsOnly = _bSpawnROPartsOnly;
		vSpawnParts = _vSpawnParts;
		vRWResources_in = new VString(_vRWResources_in);
		spawnURL = _spawnURL;
		parentURL = _parentURL;
		return spawn();

	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * spawn a node in informative mode without modifying the root JDF; url is the file name of the new node, the parameters except for the list of rw
	 * resources, which are by definition empty, are identical to those of Spawn
	 * 
	 * vRWResourceUsage is the vector of Resources Usages, Resource Names or Resource IDs that are spawned RW, all others are spawned read only; vParts is the
	 * vector of part maps that are to be spawned, defaults to no part, i.e. the whole thing
	 * 
	 * @param spawnURL : URL of the spawned JDF file
	 * @param vParts : vector of mAttributes that describe the parts to spawn
	 * @param bSpawnROPartsOnly if true, only the parts of RO resources that are specified in vParts are spawned, else the complete resource is spawned
	 * @param bCopyNodeInfo copy the NodeInfo elements into the Ancestors
	 * @param bCopyCustomerInfo copy the CustomerInfo elements into the Ancestors
	 * @param bCopyComments copy the Comment elements into the Ancestors
	 * @return JDFDoc: The spawned node's owner document.
	 * 
	 * @default spawnInformative(parentURL, null, null, false, false, false, false);
	 * 
	 */
	public JDFNode spawnInformative(final String _parentURL, final String _spawnURL, final VJDFAttributeMap _vSpawnParts, final boolean _bSpawnROPartsOnly, final boolean _bCopyNodeInfo,
			final boolean _bCopyCustomerInfo, final boolean _bCopyComments)
	{
		bCopyComments = _bCopyComments;
		bCopyCustomerInfo = _bCopyCustomerInfo;
		bCopyNodeInfo = _bCopyNodeInfo;
		bSpawnROPartsOnly = _bSpawnROPartsOnly;
		vSpawnParts = _vSpawnParts;
		vRWResources_in = null;
		spawnURL = _spawnURL;
		parentURL = _parentURL;
		return spawnInformative();
	}

	// /////////////////////////////////////////////////////////////////

	/**
	 * Method unSpawn. undo a spawn, removing any and all bookkeeping of that spawning
	 * 
	 * @param spawnID spawnID of the spawn to undo
	 * @return the fixed unspawned node
	 */
	public JDFNode unSpawn(final String spawnID)
	{
		if (spawnID == null || spawnID.equals(JDFConstants.EMPTYSTRING))
		{
			return null;
		}

		final JDFNode nodeParent = findUnSpawnNode(node, spawnID);
		return unSpawnNode(nodeParent, spawnID);

	}

	/**
	 * @param spawnID
	 * @param mapSpawn
	 */
	private JDFNode findUnSpawnNode(final JDFNode myNode, final String spawnID)
	{
		final VElement vJDFNodes = myNode.getvJDFNode(null, null, false);
		final JDFAttributeMap mapSpawn = new JDFAttributeMap(AttributeName.NEWSPAWNID, spawnID);

		int i = 0;
		final int size = vJDFNodes.size();
		// loop over all
		for (i = 0; i < size; i++)
		{
			final JDFNode nodeParent = (JDFNode) vJDFNodes.elementAt(i);

			final JDFAuditPool auditPool = nodeParent.getAuditPool();
			if (auditPool != null)
			{
				final JDFAudit spawnAudit = auditPool.getAudit(0, JDFAudit.EnumAuditType.Spawned, mapSpawn, null);
				// we have a matching spawned audit -> n is the parent node that
				// spawned spawnID
				// let n fix the rest!
				if (spawnAudit != null)
				{
					// loop over all
					// look into the audit pool and search something, which was
					// merged
					final JDFAttributeMap mapMerge = new JDFAttributeMap(JDFConstants.MERGEID, spawnID);

					final JDFAudit mergedAudit = auditPool.getAudit(0, JDFAudit.EnumAuditType.Merged, mapMerge, null);

					if (mergedAudit == null)
					{
						return nodeParent;
					}
				}
			}
		}
		final JDFNode parent = myNode.getParentJDF();
		return parent == null ? null : findUnSpawnNode(parent, spawnID);
	}

	/**
	 * unSpawnNode - undo a spawn of a node hier muss noch nachportiert werden - es gibt jetzt in JDFRoot eine Methode gleichen Namens, bei der man komfortabler
	 * das undo aufrufen kann. die Methode hier in JDFNode wird dann umbenannt (protected) und aus JDFRoot heraus aufgerufen.
	 * 
	 * @param String - strSpawnID id of the node, which was spawned before
	 * 
	 * @return the fixed unspawned node
	 */
	private JDFNode unSpawnNode(final JDFNode parent, final String strSpawnID)
	{
		if (parent == null)
		{
			return null;
		}
		JDFSpawned spawnAudit = null;

		JDFNode localNode = null;
		if (strSpawnID != null && !strSpawnID.equals(JDFConstants.EMPTYSTRING))
		{
			final JDFAuditPool auditPool = parent.getAuditPool();
			if (auditPool != null)
			{
				// look into the audit pool and search something, which was
				// spawned
				final JDFAttributeMap mapSpawn = new JDFAttributeMap(JDFConstants.NEWSPAWNID, strSpawnID);
				spawnAudit = (JDFSpawned) auditPool.getAudit(0, JDFAudit.EnumAuditType.Spawned, mapSpawn, null);
				if (spawnAudit == null)
				{
					return null; // nothing was spawned so we can undo nothing
				}

				// get parts from audit
				final VJDFAttributeMap parts = spawnAudit.getPartMapVector();
				final VString vs = spawnAudit.getrRefsROCopied();

				int i = 0;
				for (i = 0; i < vs.size(); i++)
				{
					final JDFResource oldRes = (JDFResource) parent.getTarget(vs.elementAt(i), AttributeName.ID);
					if (oldRes != null)
					{
						oldRes.unSpawnPart(strSpawnID, JDFResource.EnumSpawnStatus.SpawnedRO);
					}
				}

				// merge rw resources
				final VString vRWCopied = spawnAudit.getrRefsRWCopied();

				for (i = 0; i < vRWCopied.size(); i++)
				{
					final JDFResource oldRes = (JDFResource) parent.getTarget(vRWCopied.elementAt(i), AttributeName.ID);
					if (oldRes != null)
					{
						oldRes.unSpawnPart(strSpawnID, JDFResource.EnumSpawnStatus.SpawnedRW);
					}
				}

				localNode = (JDFNode) parent.getTarget(spawnAudit.getjRef(), AttributeName.ID);
				final VElement vn = localNode.getvJDFNode(null, null, false);
				// in C++ Vector is VJDFNode, which is typesafe

				// loop over all child nodes of the spawned node to be unspawned
				for (int nod = 0; nod < vn.size(); nod++)
				{
					final JDFNode deepNode = (JDFNode) vn.elementAt(nod);
					final JDFResourcePool resPool = deepNode.getResourcePool();

					if (resPool != null)
					{
						final VElement vRes = resPool.getPoolChildren(null, null, null);

						for (i = 0; i < vRes.size(); i++)
						{
							final JDFResource res1 = (JDFResource) vRes.elementAt(i);
							res1.unSpawnPart(strSpawnID, JDFResource.EnumSpawnStatus.SpawnedRW);
						}
					}
				}

				EnumNodeStatus status = JDFElement.EnumNodeStatus.Waiting;
				final boolean fHasAuditStatus = spawnAudit.hasAttribute(AttributeName.STATUS);
				if (fHasAuditStatus)
				{
					status = spawnAudit.getStatus();
				}

				if (parts != null)
				{
					final EnumNodeStatus parentStatus = parent.getStatus();
					if (JDFElement.EnumNodeStatus.Pool.equals(parentStatus) || JDFElement.EnumNodeStatus.Part.equals(parentStatus))
					{
						for (i = 0; i < parts.size(); i++)
						{
							if ((parent.getPartStatus(parts.elementAt(i)).equals(JDFElement.EnumNodeStatus.Spawned)) || fHasAuditStatus)
							{
								parent.setPartStatus(parts.elementAt(i), status, null);
							}
						}
					}
					else if (JDFElement.EnumNodeStatus.Spawned.equals(parentStatus) || spawnAudit.hasAttribute(AttributeName.STATUS))
					{
						parent.setStatus(status);
					}
				}
				else
				{
					// we either must overwrite because it is now definitely not
					// spawned
					// or had an explicit correct status in the spawned audit
					if (JDFElement.EnumNodeStatus.Spawned.equals(localNode.getStatus()) || spawnAudit.hasAttribute(AttributeName.STATUS))
					{
						localNode.setStatus(status);
					}
				}
			}

			if (spawnAudit != null)
			{
				spawnAudit.deleteNode();
			}
		}

		return localNode;
	}

}
