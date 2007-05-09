/**
 * 
 */
package org.cip4.jdflib.util;

import java.util.Iterator;
import java.util.Vector;

import org.cip4.jdflib.auto.JDFAutoNotification.EnumClass;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFConstants;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFException;
import org.cip4.jdflib.core.JDFPartAmount;
import org.cip4.jdflib.core.JDFPartStatus;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.JDFAudit.EnumAuditType;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFSpawned;
import org.cip4.jdflib.node.JDFNode.EnumCleanUpMerge;
import org.cip4.jdflib.pool.JDFAmountPool;
import org.cip4.jdflib.pool.JDFAncestorPool;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourceLinkPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.pool.JDFStatusPool;
import org.cip4.jdflib.resource.JDFMerged;
import org.cip4.jdflib.resource.JDFNotification;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.JDFResource.EnumAmountMerge;
import org.cip4.jdflib.resource.JDFResource.EnumPartUsage;
import org.cip4.jdflib.resource.JDFResource.EnumSpawnStatus;

/**
 * @author Rainer Prosi
 * This class is used when merging a JDF node it summarizes all 
 * merging routines the had been part of JDF Node
 */
public class JDFMerge
{

    private JDFNode m_ParentNode;
    /**
     * set this to true if you want to update the stati of the relevant parent nodes based on the new Stati of the merged node
     */
    public boolean bUpdateStati=false;
    /**
     * 
     * @param parentNode the parent node to merge into. MAY be the actual node to be replace or any Parent thereof
     */
    public JDFMerge (JDFNode parentNode)
    {
        m_ParentNode=parentNode;
    }

    /**
     * merge a previously spawned JDF into a node that is a child of, or this root 
     * <p>
     * default:  mergeJDF(toMerge, null, 
     *              JDFNode.EnumCleanUpMerge.None, JDFResource.EnumAmountMerge.None)
     * 
     * @param toMerge      the previously spawned jdf node
     * @param urlMerge     the url of the ???
     * @param cleanPolicy  policy how to clean up the spawn and merge audits after merging
     * @param amountPolicy policy how to clean up the Resource amounts after merging
     * @return JDFNode - the merged node in the new document<br>
     * note that the return value used to be boolean. The boolean value is now replaced by exceptions. This corresponds to <code>true</code> always.
     *
     * @throws JDFException if toMerge has already been merged 
     * @throws JDFException if toMerge was not spawned from this
     * @throws JDFException if toMerge has no AncestorPool
     * 
     * default:  mergeJDF(toMerge, null, 
     *              JDFNode.EnumCleanUpMerge.None, JDFResource.EnumAmountMerge.None)
     */


    public JDFNode mergeJDF(JDFNode toMerge, String urlMerge, EnumCleanUpMerge cleanPolicy, JDFResource.EnumAmountMerge amountPolicy)
    {
        if (!toMerge.hasParent(m_ParentNode))
        {
            throw new JDFException("JDFNode.MergeJDF no matching parent found");
        }

        final String idm      = toMerge.getID();
        JDFNode overWriteNode  = (JDFNode) m_ParentNode.getTarget(idm, AttributeName.ID);

        if (overWriteNode == null)
        {
            throw new JDFException("JDFNode.MergeJDF no Node with ID: " + idm);
        }

        //tbd multiple ancestor handling
        final JDFAncestorPool ancestorPool = toMerge.getAncestorPool();
        if (ancestorPool==null)
        {
            throw new JDFException("JDFNode.MergeJDF no Ancestor Pool in Node: " + idm);
        }
        final int numAncestors             = ancestorPool.numChildElements(ElementName.ANCESTOR, null);

        if (numAncestors <= 0)
        {
            throw new JDFException("JDFNode.MergeJDF no Ancestors in AncestorPool found. Node: " + idm);
        }

        String spawnID              = JDFConstants.EMPTYSTRING;
        boolean bSnafu              = true;
        JDFSpawned spawnAudit       = null;
        final VString previousMergeIDs    = new VString(); // list of merges in the ancestors
        int iFound                  = 0;

        for (int whereToLook = 1; whereToLook <= numAncestors; whereToLook++)
        {
            // the last ancestor has the id!
            final String idParent = ancestorPool.getAncestor(numAncestors - whereToLook).getNodeID();
            final KElement k = m_ParentNode.getTarget(idParent, AttributeName.ID);
            if (k == null)
            {
                break;
            }

            JDFNode op = (JDFNode)k;
            final JDFAuditPool ap = op.getCreateAuditPool();

            // find all ids of previous merge operations for reverse merge cleanup
            final VElement vMergeAudit = ap.getAudits(EnumAuditType.Merged, null,null);
            for (int nMerged = 0; nMerged < vMergeAudit.size(); nMerged++)
            {
                final JDFMerged merged = (JDFMerged)vMergeAudit.elementAt(nMerged);
                previousMergeIDs.appendUnique(merged.getMergeID());
            }

            if (iFound != 0) // we've already found a spawned Audit, just looping for previous merges
            {
                continue;
            }

            // get appropriate spawned element
            final VElement vSpawnAudit = ap.getChildrenByTagName(ElementName.SPAWNED, null, new JDFAttributeMap(AttributeName.JREF, idm), 
                    true, true, 0);
            spawnID = toMerge.getSpawnID(false);

            for (int isp = vSpawnAudit.size() - 1; isp >= 0; isp--)
            { // loop backwards because the latest is assumed correct
                JDFSpawned testSpawn = (JDFSpawned)vSpawnAudit.elementAt(isp);
                if (testSpawn.getNewSpawnID().equals(spawnID))
                {
                    // tbd check for matching merged...
                    spawnAudit = testSpawn;
                    final JDFMerged matchingMerged = 
                        (JDFMerged) ap.getChildWithAttribute(ElementName.MERGED, AttributeName.MERGEID, null, spawnID, 0, true);

                    if (matchingMerged != null)
                    {
                        throw new JDFException("JDFNode.MergeJDF Spawn Audit already merged, SpawnID: " + spawnID, JDFSpawn.exAlreadyMerged);
                    }
                    break;
                }
            }
            // found an audit that fits, 
            if (spawnAudit != null)
            {
                iFound = whereToLook;
            }
        }

        // if the spawn Audit is not found at the first attempt, something went badly wrong
        // we will insert a error audit later but continue limping along!
        bSnafu = iFound != 1;

        if (spawnAudit == null)
        {
            throw new JDFException("JDFNode.MergeJDF no matching Spawn Audit, SpawnID: " + spawnID);
        }

        // get parts from audit
        final VJDFAttributeMap parts = spawnAudit.getPartMapVector();

        // merge copied readOnly resources
        final VString vsRO = spawnAudit.getrRefsROCopied();
        final VString vsRW = spawnAudit.getrRefsRWCopied();

        String preSpawn = mergeCheckPrespawn(toMerge, spawnAudit, vsRO, vsRW);

        mergeLocalLinks(overWriteNode,toMerge, parts);

        cleanROResources(overWriteNode,toMerge, previousMergeIDs, vsRO, spawnID);
        mergeRWResources(overWriteNode,toMerge, previousMergeIDs, vsRW, spawnID, amountPolicy);

        mergeLocalNodes(overWriteNode,toMerge, previousMergeIDs, spawnID, amountPolicy, parts);
        JDFMerged mergeAudit=mergeMainPools(overWriteNode, toMerge, parts, vsRW, spawnID, preSpawn, urlMerge, bSnafu);
        // an empty spawnID should never happen here, but check just in case
        // since an empty spawnID in CleanUpMerge removes all Spawned audits
        if (spawnID != null)
        {
            JDFNode overWriteParent=mergeAudit.getParentJDF(); // since all links get screwed up, let's relink here
            cleanUpMerge(overWriteParent,cleanPolicy, spawnID, false);
        }

        // now burn it in!
        overWriteNode=(JDFNode)overWriteNode.replaceElement(toMerge);
        overWriteNode.eraseEmptyNodes(true);
        // update all stati (generally in NodeInfo) of the merged node and of the parents of the merged node
        if(bUpdateStati)
            overWriteNode.updatePartStatus(parts, true, true);
        return overWriteNode;
    }    

    /**
     * merge the audit pools
     * @param overWriteNode
     * @param toMerge       the source node of the audit pool to merge into this
     */
    private static void mergeAuditPool(JDFNode overWriteNode, JDFNode toMerge)
    {
        //      merge audit pool
        final JDFAuditPool overWriteAuditPool = overWriteNode.getAuditPool();
        final JDFAuditPool toMergeAuditPool = toMerge.getAuditPool();

        // the node that is overwritten has an audit pool that must be merged
        if (overWriteAuditPool != null)
        {
            // the overwriting node node is empty, just copy the previous pool
            if (toMergeAuditPool == null)
            {
                toMerge.copyElement(overWriteAuditPool, null);
            }
            else
            {
                // must merge the old node into the overwriting node
                overWriteAuditPool.appendUnique(toMergeAuditPool);
                toMergeAuditPool.replaceElement(overWriteAuditPool);
            }
        }
    }
    ///////////////////////////////////////////////////////////////////

    private String mergeCheckPrespawn(JDFNode toMerge, JDFSpawned spawnAudit, final VString vsRO, final VString vsRW)
    {
        String preSpawn = spawnAudit.getSpawnID();
        // check all recursive previous spawns
        while (preSpawn!=null && !preSpawn.equals(JDFConstants.EMPTYSTRING))
        {
            final JDFMerged preMerge = (JDFMerged)m_ParentNode.getTarget(preSpawn, AttributeName.MERGEID);
            if (preMerge != null)
            {
                final JDFSpawned preSpawnAudit = (JDFSpawned)m_ParentNode.getTarget(preSpawn, AttributeName.NEWSPAWNID);
                vsRO.appendUnique(preSpawnAudit.getrRefsROCopied());
                vsRW.appendUnique(preSpawnAudit.getrRefsRWCopied());
                preSpawn = preSpawnAudit.getSpawnID();
            }
            else
            {
                toMerge.setSpawnID(preSpawn);
                break;                
            }
        }
        return preSpawn;
    }
    //////////////////////////////////////////////////////////////////////

    private static void mergeComments(JDFNode overWriteNode, JDFNode toMerge){
        VElement v=overWriteNode.getChildElementVector(ElementName.COMMENT,null,null,false,0,false);
        VElement vToMerge=toMerge.getChildElementVector(ElementName.COMMENT,null,null,false,0,false);
        final int siz=vToMerge.size(); // size prior to appending
        vToMerge.appendUnique(v);
        for(int i=siz;i<vToMerge.size();i++){
            toMerge.moveElement((KElement)vToMerge.elementAt(i),null);
        }
    }

    static private void mergeLocalLinks(JDFNode overWriteNode, JDFNode toMerge, final VJDFAttributeMap parts)
    {
        int numParts = parts==null ? 0 : parts.size();
        final Vector vn = overWriteNode.getvJDFNode(null, null, false);
        final int size = vn.size();
        // merge local (internal) partitioned resource links
        for (int nod = 0; nod < size; nod++)
        {
            final JDFNode overwriteLocalNode  = (JDFNode)vn.elementAt(nod);
            final JDFNode toMergeLocalNode    = toMerge.getChildJDFNode(overwriteLocalNode.getID(), false);
            mergeResourceLinkPool(overwriteLocalNode,toMergeLocalNode, parts);

            final EnumVersion version = toMergeLocalNode.getVersion(true);
            if ((version!=null)&&(version.getValue() >= EnumVersion.Version_1_3.getValue()))
            {
                final JDFNode.EnumNodeStatus stat=toMergeLocalNode.getStatus();
                if (stat!=null && !stat.equals(JDFElement.EnumNodeStatus.Part) &&
                        !stat.equals(JDFElement.EnumNodeStatus.Pool) &&
                        numParts > 0)
                {
                    toMergeLocalNode.setPartStatus(parts,stat);
                }
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////

    private static void mergeLocalNodes(JDFNode overWriteNode, JDFNode toMerge, final VString previousMergeIDs, String spawnID, JDFResource.EnumAmountMerge amountPolicy, final VJDFAttributeMap parts)
    {
        // merge local (internal) partitioned resources
        final Vector vn = overWriteNode.getvJDFNode(null, null, false);
        for (int nod = 0; nod < vn.size(); nod++)
        {
            final JDFNode overwriteLocalNode = (JDFNode)vn.elementAt(nod);

            final JDFNode toMergeLocalNode = (JDFNode)toMerge.getTarget(overwriteLocalNode.getID(), AttributeName.ID);
            final JDFResourcePool poolOverWrite = overwriteLocalNode.getResourcePool();
            final JDFResourcePool poolToMerge = toMergeLocalNode.getResourcePool();

            if (poolOverWrite != null)
            {
                final VElement resOverWrite = 
                    poolOverWrite.getPoolChildren(null, null, null);

                final int size = resOverWrite.size();
                for (int i = 0; i < size; i++)
                {
                    final JDFResource res1 = (JDFResource)resOverWrite.elementAt(i);
                    mergeLocalResource(previousMergeIDs, spawnID, amountPolicy, poolToMerge, res1);
                }
            }

            //retain all other elements of the original (non spawned) JDF Node if the spawn is partitioned¬
            final JDFAncestorPool ancestorPool = toMerge.getAncestorPool();
            if (ancestorPool!=null && ancestorPool.isPartitioned())
            {
                final VElement localChildren = overwriteLocalNode.getChildElementVector(null, null, null, true, 0, false);

                final int siz = localChildren.size();
                for (int i = 0; i < siz; i++)
                {
                    final KElement e = (KElement) localChildren.elementAt(i);
                    //          skip all pools
                    final String nodeName = e.getLocalName();
                    if (nodeName.endsWith("Pool"))
                    {
                        if (nodeName.equals(ElementName.RESOURCELINKPOOL))
                        {
                            continue;
                        }
                        if (nodeName.equals(ElementName.RESOURCEPOOL))
                        {
                            continue;
                        }
                        if (nodeName.equals(ElementName.AUDITPOOL))
                        {
                            mergeAuditPool(overwriteLocalNode,toMergeLocalNode);
                            continue;
                        }
                        if (nodeName.equals(ElementName.STATUSPOOL))
                        {
                            mergeStatusPool(overwriteLocalNode,toMergeLocalNode,parts);
                            continue;
                        }
                        if (nodeName.equals(ElementName.ANCESTORPOOL))
                        {
                            continue;
                        }
                    }

                    // 131204 RP also skip all sub-JDF nodes!!!
                    if(nodeName.equals(ElementName.JDF))
                    {
                        continue;
                    }
                    //050708 RP special handling for comments
                    if(nodeName==ElementName.COMMENT){
                        mergeComments(overwriteLocalNode,toMergeLocalNode);
                        continue;
                    }

                    toMergeLocalNode.removeChildren(nodeName, null, null);
                    toMergeLocalNode.moveElement(e, null);

                    // repeat in case of multiple identical elements (e.g. comments)
                    for (int j = i + 1; j < siz; j++)
                    {
                        final JDFElement localChild = (JDFElement) localChildren.elementAt(j);
                        if ( localChild != null )
                        {
                            if ( localChild.getNodeName().equals(nodeName) )
                            {
                                toMergeLocalNode.moveElement(localChild, null);
                                localChildren.set(j, null);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Merges partitioned resources into this resource
     * uses PartIDKey to identify the correct resources
     *
     * @param resToMerge the resource leaf to merge into this
     * @param spawnID the spawnID of the spawning that will now be merged
     * @param amountPolicy how to clean up the Resource amounts after merging
     * @param bLocalResource must be true for the local resources in a spawned node and its subnodes, which default to RW
     *
     * @throws JDFException if here is an attempt to merge incompatible resources 
     * @throws JDFException if here is an attempt to merge incompatible partitions
     * 
     * @default mergePartition (resToMerge, spawnID, EnumAmountMerge.None, false);
     */
    /*
     * mergePartition will stay public, as long as deprecated JDFResource.mergePartition is not deleted
     */
    static public JDFResource mergePartition(JDFResource targetRes,JDFResource resToMerge, String spawnID, EnumAmountMerge amountPolicy, boolean bLocalResource)
    {
        if (!targetRes.getID().equals(resToMerge.getID()))
        {
            throw new JDFException("JDFResource.mergePartition  merging incompatible resources ID="
                    + targetRes.getID()+ " IDMerge=" + resToMerge.getID());
        }
        
        /// TBD RP SpawnStatus Handling!!!!
        final JDFResource toMerge  = resToMerge;
        JDFResource root=targetRes.getResourceRoot();
        final VString partIDKeys      = root.getPartIDKeys();
        final VString mergeIDKeys     = toMerge.getPartIDKeys();
        final VElement allChildren   = resToMerge.getNodesWithSpawnID(spawnID);
        
        // No spawntargets take all
        if (allChildren.isEmpty())
        {
            allChildren.addElement(toMerge);
        }
        
        boolean bTargetGone = false;
        
        for (int i = 0; i < allChildren.size(); i++)
        {
            final JDFResource src = (JDFResource) allChildren.elementAt(i);
            final JDFAttributeMap srcMap = src.getPartMap();
            JDFResource trg = targetRes.getPartition(srcMap, EnumPartUsage.Implicit);
            
            if (trg == null)
            {
                trg = targetRes;
            }
            JDFAttributeMap trgMap=trg.getPartMap();
            
            // RP 220605 - not puristic, but pragmatic
            // found only a root or high level partition for an rw resource partition
            // try to create the new partition and pray that it will not be subsequently completely overwritten
            // this is still better than throwing an exception or silently ignoring the rw resource
            if((src.getLocked()==false)&&(trgMap.getKeys().size()<srcMap.getKeys().size())){
                trg=targetRes.getCreatePartition(srcMap,partIDKeys);
                // fool the algorithm to think that the new partition is rw (which it probably was)
                trg.setSpawnStatus(EnumSpawnStatus.SpawnedRW);
                trgMap=trg.getPartMap(); // 061114 fix!
            }
            
            if (bLocalResource || trg.getSpawnStatus() == JDFResource.EnumSpawnStatus.SpawnedRW)
            {
                
                if (srcMap.equals(trgMap))
                {
                    if (trgMap.isEmpty())
                    { // we actually replaced the root nothing left to do
                        bTargetGone = true;
                        trg=(JDFResource)targetRes.replaceElement(src);
                        root=trg.getResourceRoot();
                    }
                    else
                    {
                        KElement copyElement = targetRes.getParentNode_KElement().copyElement(src, null);
                        trg = (JDFResource) trg.replaceElement(copyElement);                       
                    }
                }
                else if (srcMap.subMap(trgMap))
                {
                    // potential check for very deep src
                    trg.copyElement(src, null);
                }
                else
                { // oops
                    throw new JDFException("JDFResource.mergePartition attempting to merge incompatible partitions");
                }
            }
            //update the partitions amounts
            if ((amountPolicy != EnumAmountMerge.None) && targetRes.isPhysical())
            {
                JDFResource trgKeep=trg;
                trg = root.getPartition(srcMap, EnumPartUsage.Implicit); // must repeat since replaceelement does not modify itself
                if (trg == null)
                {
                    trg = trgKeep;
                }
                VElement vr = trg.getLeaves(true);
                for(int l = 0; l < vr.size(); l++)
                {
                    double amo = 0;
                    JDFResource r = (JDFResource)vr.elementAt(l);
                    if(amountPolicy != EnumAmountMerge.LinkOnly)
                    {
                        amo = r.getAmount();
                    }
                    r.updateAmounts(amo);
                }
            }
        }
        
        // some crap is left - remove it
        if (!bTargetGone)
        {
            toMerge.deleteNode();
        }
        
        partIDKeys.appendUnique(mergeIDKeys);
        
        if (partIDKeys.isEmpty())
        {
            root.removeAttribute(AttributeName.PARTIDKEYS);
        }
        else
        {
            root.setPartIDKeys(partIDKeys);
        }
        return root;
    }
        
    
    private static void mergeLocalResource(final VString previousMergeIDs, String spawnID, JDFResource.EnumAmountMerge amountPolicy, final JDFResourcePool poolToMerge, JDFResource res1)
    {
        final String resID = res1.getID();
        final JDFResource res2 = poolToMerge.getResourceByID(resID);

        if (res2 != null)
        {
            res2.mergeSpawnIDs(res1, previousMergeIDs);
            res1=mergePartition(res1,res2, spawnID, amountPolicy, true); // esp. deletes res2 from toMerge node
        }
        // copy resource from orig to spawned node
        poolToMerge.copyElement(res1, null);
        res1 = poolToMerge.getResourceByID(resID);
        final VElement resLeafsSpawned = res1.getNodesWithSpawnID(spawnID);
        for (int leaf = 0; leaf < resLeafsSpawned.size(); leaf++)
        {
            final JDFResource leafRes = (JDFResource)resLeafsSpawned.elementAt(leaf);
            leafRes.removeFromSpawnIDs(spawnID);
            final VString spawnIDs = leafRes.getSpawnIDs(false);
            spawnIDs.removeAll(previousMergeIDs);
            if(spawnIDs.isEmpty())
            {
                leafRes.removeAttribute(AttributeName.SPAWNIDS);
                leafRes.removeAttribute(AttributeName.SPAWNSTATUS);
                leafRes.removeAttribute(AttributeName.LOCKED);

            }
            else
            {
                leafRes.setSpawnIDs(spawnIDs);
            }
        }
    }
    /////////////////////////////////////////////////////////////////////

    private static JDFMerged mergeMainPools(JDFNode overWriteNode, JDFNode toMerge, final VJDFAttributeMap parts, final VString vsRW, String spawnID, String preSpawn, String urlMerge, boolean bSnafu)
    {
        // add the merged audit - maintain sychronicity of spawned and merged
        JDFNode overWriteParent=null;
        JDFAuditPool ap=toMerge.getAuditPool();
        JDFSpawned sa=null;

        if(ap!=null)
        {
            sa=(JDFSpawned) ap.getChildWithAttribute(ElementName.SPAWNED,AttributeName.NEWSPAWNID,null,spawnID,0,true);
            overWriteParent=overWriteNode;
        }

        if(sa==null)
        {
            overWriteParent=overWriteNode.getParentJDF();
            if(overWriteParent==null)
            {
                throw new JDFException("mergeMainPools - corrupt audit structure");
            }

            ap=overWriteParent.getAuditPool();
            if(ap!=null)
                sa=(JDFSpawned) ap.getChildWithAttribute(ElementName.SPAWNED,AttributeName.NEWSPAWNID,null,spawnID,0,true);
        }

        if(sa==null)
        {
            //????
            throw new JDFException("mergeMainPools - corrupt audit structure; no Spawn Audit found");
        }

        //      JDFNode overWriteParent=ap.getParentJDF();
        final JDFMerged mergeAudit = ap.addMerged(toMerge,vsRW, null, parts);

        if (urlMerge!=null && !urlMerge.equals(JDFConstants.EMPTYSTRING))
        {
            String url = urlMerge;
            // 300802 RP added check for preexisting file prefix
            if (url.indexOf("://") == -1)
            {
                url = "File://" + url;
            }
            mergeAudit.setURL(url);
        }
        mergeAudit.setMergeID(spawnID);

        // if something went wrong, also add a notification
        if (bSnafu)
        {
            final JDFNotification notification = ap.addNotification(EnumClass.Error, "JDFNode.MergeJDF ", parts);
            notification.setType("Error");
            notification.appendComment().appendText("The Ancestor list was incorrectly ordered for merging in the spawned JDF");
        }

        // cleanup
        toMerge.removeChild(ElementName.ANCESTORPOOL, null, 0);
        if (parts!=null && parts.size() >= 1){
            mergeStatusPool(overWriteNode,toMerge,parts);
            // handle ancestor pools only in partitioned spawns
            final JDFAncestorPool ancPool=overWriteParent.getAncestorPool();
            if(ancPool!=null){
                toMerge.copyElement(ancPool,null);
            }
        }

        final String jid = overWriteParent.getJobID(true);
        if (toMerge.getAttribute(AttributeName.JOBID, null, JDFConstants.EMPTYSTRING).equals(jid))
        {
            toMerge.removeAttribute(AttributeName.JOBID, null);
        }
        if(preSpawn==null || preSpawn.equals(JDFConstants.EMPTYSTRING))
        {
            toMerge.removeAttribute(AttributeName.SPAWNID, null);
            mergeAudit.removeAttribute(AttributeName.SPAWNID, null);
        }
        else
        {
            toMerge.setSpawnID(preSpawn);
        }
        return mergeAudit;
    }

    /**
     * merge the resource link pools
     * @param oMerge the source node of the status pool to merge into this
     * @param parts the partitions to merge
     */
    private static void mergeResourceLinkPool(JDFNode overWriteNode, JDFNode toMerge, VJDFAttributeMap parts)
    {
        final JDFResourceLinkPool resourceLinkPool = toMerge.getResourceLinkPool();
        expandLinkedResources(resourceLinkPool);

        if (parts!=null && !parts.isEmpty())
        {
            final JDFResourceLinkPool toMergeRLP   = resourceLinkPool;
            if (toMergeRLP == null)
                return; // nothing to do

            final JDFResourceLinkPool overWriteRLP = overWriteNode.getCreateResourceLinkPool();
            final VElement overWriteLinks = overWriteRLP.getPoolChildren(null, null, null);
            final VElement toMergeLinks   = toMergeRLP.getPoolChildren(null, null, null);

            if (toMergeLinks != null && overWriteLinks != null)
            {
                for (int rl = 0; rl < toMergeLinks.size(); rl++)
                {
                    JDFResourceLink overWriteLink = null;
                    final JDFResourceLink toMergeLink = 
                        (JDFResourceLink) toMergeLinks.elementAt(rl);
                    final String rRef = toMergeLink.getAttribute(AttributeName.RREF);

                    for (int k = 0; k < overWriteLinks.size(); k++)
                    {
                        if (((JDFResourceLink) overWriteLinks.elementAt(k)).getAttribute(
                                AttributeName.RREF).equals(rRef))
                        {
                            overWriteLink = (JDFResourceLink) overWriteLinks.elementAt(k);
                            overWriteLinks.remove(overWriteLinks.elementAt(k));
                            break;
                        }
                    }

                    if (overWriteLink != null)
                    {
                        if (toMergeLink.hasChildElement(ElementName.PART, null))
                        {
                            fixPartAmountAttributes(parts, overWriteLink, toMergeLink);
                        }
                        else
                        {
                            // blast the spawned link into the overWritePool completely
                            overWriteLink.replaceElement(toMergeLink);
                        }
                    }
                }
            }

            toMergeRLP.deleteNode();
            toMerge.copyElement(overWriteRLP, null);
        }
    }

    /**
     * @param parts
     * @param overWriteLink
     * @param toMergeLink
     * @param jdfAmountPool
     */
    private static void fixPartAmountAttributes(VJDFAttributeMap parts, JDFResourceLink overWriteLink, final JDFResourceLink toMergeLink)
    {
        final JDFAmountPool jdfAmountPool = toMergeLink.getAmountPool();

        final int partSize = parts.size();
        for (int i = 0; i < partSize ; i++)
        {
            final boolean hasAP = overWriteLink.hasChildElement(ElementName.AMOUNTPOOL, null);
            VElement vPartAmounts = null;
            if(jdfAmountPool!=null)
                vPartAmounts=jdfAmountPool.getMatchingPartAmountVector(parts.elementAt(i));

            if (vPartAmounts == null)
            {
                JDFAttributeMap mpaMap  = toMergeLink.getAttributeMap();
                // remove generic link stuff
                mpaMap.remove(AttributeName.COMBINEDPROCESSINDEX);
                mpaMap.remove(AttributeName.COMBINEDPROCESSTYPE);
                //tbd opa.RemoveAttribute(atr_PipePartIDKeys);
                mpaMap.remove(AttributeName.PIPEPROTOCOL);
                mpaMap.remove(AttributeName.PROCESSUSAGE);
                mpaMap.remove(AttributeName.RREF);
                mpaMap.remove(AttributeName.RSUBREF);
                mpaMap.remove(AttributeName.USAGE);
                if(!hasAP) // remove all entries that are identical in the merged and original link so that they are not modified
                {
                    final JDFAttributeMap opaMap=overWriteLink.getAttributeMap();
                    Iterator iter=opaMap.getKeyIterator();
                    while(iter.hasNext())
                    {
                        final String key=(String)iter.next();
                        if(opaMap.get(key).equals(mpaMap.get(key)))
                            mpaMap.remove(key);
                    }
                }
                // only add something if partmap contains real information
                if(hasAP || (!mpaMap.isEmpty()&&!mpaMap.equals(parts.elementAt(i))))
                {
                    JDFPartAmount opa=overWriteLink.getCreateAmountPool().getCreatePartAmount(parts.elementAt(i));
                    opa.setAttributes(mpaMap);
                    overWriteLink.removeAttributes(mpaMap.getKeys());
                }
            }
            else
            {
                // loop over all fitting part amounts and blast them in
                for(int j=0;j<vPartAmounts.size();j++)
                {
                    JDFPartAmount mpa= (JDFPartAmount)vPartAmounts.elementAt(j);                              
                    JDFAttributeMap amountMap=mpa.getAttributeMap();
                    if(!amountMap.isEmpty())
                    {
                        JDFAttributeMap partMap=mpa.getPartMap();
                        JDFPartAmount opa=overWriteLink.getCreateAmountPool().getCreatePartAmount(partMap);
                        opa.setAttributes(amountMap);
                        overWriteLink.removeAttributes(amountMap.getKeys());  
                    }
                }
            }

            // nothing has changed --> leave as is
        }
    }

    /**
     * @param resourceLinkPool the resourceLinkPool that contains the links to the resources to expand
     */
    private static void expandLinkedResources(final JDFResourceLinkPool resourceLinkPool)
    {
        if (resourceLinkPool != null)
        {
            final VElement links = resourceLinkPool.getPoolChildren(null, null, null);
            final int size = (links == null) ? 0 : links.size();
            for (int i = 0; i < size; i++)
            {
                JDFResourceLink rl = (JDFResourceLink) links.elementAt(i);
                rl.expandTarget(false);
            }
        }
    }

    /**
     * merge the RW resources of the main JDF
     * @param toMerge the source node of the status pool to merge into this
     * @param toMerge          the source node of the status pool to merge into this
     * @param previousMergeIDs SpawnIDs of previously merged 
     * @param vsRW             Resource IDs of non-local spawned resources 
     * @param spawnID          the original spawnID 
     * @param amountPolicy     policy how to clean up the Resource amounts after merging
     */
    private static void mergeRWResources(JDFNode overWriteNode, JDFNode toMerge, final VString previousMergeIDs, final VString vsRW, String spawnID, JDFResource.EnumAmountMerge amountPolicy)
    {
        // merge rw resources
        for (int i = 0; i < vsRW.size(); i++)
        {
            JDFResource oldRes = overWriteNode.getLinkRoot((String)vsRW.elementAt(i));
            if(oldRes == null) // also check in tree below
            {
                oldRes = overWriteNode.getTargetResource((String)vsRW.elementAt(i));
                if(oldRes == null) // also check in entire tree below root
                {
                    oldRes = overWriteNode.getTargetResource((String)vsRW.elementAt(i));
                }
            }
            if(oldRes == null)
            {
                continue;
            }


            final JDFResource newRes = toMerge.getTargetResource((String)vsRW.elementAt(i));

            // merge all potential new spawnIds from this to toMerge before merging them
            oldRes.mergeSpawnIDs(newRes, previousMergeIDs);
            // do both, since some leaves may be RO
            newRes.mergeSpawnIDs(oldRes, previousMergeIDs);

            try
            {
                // merge the resource from the spawned node into the lower level resourcepool
                oldRes=mergePartition(oldRes,newRes, spawnID, amountPolicy, false);
            }
            catch (Exception e)
            {
                throw new JDFException("JDFNode:mergeJDF, error in mergePartition: ID="+oldRes.getID()+" SpawnID="+spawnID);
            }

            final String oldID        = oldRes.getID();
            final JDFResource myRes   = (JDFResource)overWriteNode.getTarget(oldID, AttributeName.ID);
            if (myRes == null)
            {
                throw new JDFException("JDFNode.mergeJDF: Merged Resource not found! Cant remove SpawnIDs");
            }
            final VElement oldResLeafsSpawned = myRes.getNodesWithSpawnID(spawnID);
            for (int leaf = 0; leaf < oldResLeafsSpawned.size(); leaf++)
            {
                final JDFResource leafRes = (JDFResource)oldResLeafsSpawned.elementAt(leaf);
                leafRes.removeFromSpawnIDs(spawnID);
                final KElement leafElem = leafRes;
                if (!leafElem.hasAttribute(AttributeName.SPAWNIDS, null, false))
                {
                    leafRes.removeAttribute(AttributeName.SPAWNSTATUS, null);
                    leafRes.removeAttribute(AttributeName.LOCKED, null);
                }
            }
        }
    }

    /**
     * merge the status pools
     * @param toMerge the source node of the status pool to merge into this
     * @param parts   the partitions to merge
     */
    private static void mergeStatusPool(JDFNode overWriteNode, JDFNode toMerge, VJDFAttributeMap parts)
    {
        if (toMerge.hasChildElement(ElementName.STATUSPOOL, null) || 
                overWriteNode.hasChildElement(ElementName.STATUSPOOL, null))
        {
            final JDFStatusPool overWriteStatusPool = overWriteNode.getCreateStatusPool();
            if (!overWriteNode.getStatus().equals(JDFElement.EnumNodeStatus.Pool))
            {
                overWriteStatusPool.setStatus(overWriteNode.getStatus());
                overWriteNode.setStatus(JDFElement.EnumNodeStatus.Pool);
            }

            final JDFStatusPool toMergeStatusPool = toMerge.getStatusPool();
            if (toMerge.getStatus() == JDFElement.EnumNodeStatus.Pool)
            {
                for (int i = 0; i < parts.size(); i++)
                {
                    int j;
                    // clean up the pool to overwrite
                    final VElement vpso = overWriteStatusPool.getMatchingPartStatusVector(parts.elementAt(i));
                    for (j=0;j<vpso.size();j++)
                    {
                        // remove all matching partstatus elements in case they were expanded in the spawned node
                        ((JDFPartStatus) vpso.elementAt(j)).deleteNode(); 
                    }

                    // extract data from spawned node
                    final VElement vps=toMergeStatusPool.getMatchingPartStatusVector(parts.elementAt(i));
                    for (j=0; j<vps.size(); j++)
                    {
                        final JDFPartStatus ps = (JDFPartStatus) vps.elementAt(j);
                        final JDFAttributeMap m = ps.getPartMap();
                        overWriteStatusPool.setStatus(m, ps.getStatus(), ps.getStatusDetails());
                    }

                    //100305 RP the following lines cause problems with nested spawn and therefore are commented out
                    //                  final JDFPartStatus ps=overWriteStatusPool.getPartStatus(parts.elementAt(i));
                    // just in case someone updated detailed partstatus elements
                    //                  if (ps != null && ps.getStatus() == EnumNodeStatus.Spawned)
                    //                  ps.deleteNode();
                }
                toMergeStatusPool.replaceElement(overWriteStatusPool);
            }
            else
            {
                // this part of the program will probably never be executed, but...
                for (int i = 0; i < parts.size(); i++)
                    overWriteStatusPool.setStatus(parts.elementAt(i), 
                            toMerge.getStatus(), null);
                if (toMergeStatusPool != null)
                    toMergeStatusPool.deleteNode();
                toMerge.setStatus(JDFElement.EnumNodeStatus.Pool);
                toMerge.moveElement(overWriteStatusPool, null);
            }
        }

    }

    /**
     * merge the RO resources of the main JDF
     * @param toMerge          the source node of the status pool to merge into this
     * @param previousMergeIDs SpawnIDs of previously merged 
     * @param vsRO             Resource IDs of non-local spawned resources 
     * @param spawnID          the original spawnID 
     */
    private static void cleanROResources(JDFNode overWriteNode,JDFNode toMerge, final VString previousMergeIDs, final VString vsRO, String spawnID)
    {
        final int roSize = vsRO.size();
        for (int i = 0; i < roSize; i++)
        {
            final JDFResource newRes = toMerge.getTargetResource((String)vsRO.elementAt(i));
            final JDFResource oldRes = (JDFResource) overWriteNode.getTarget((String)vsRO.elementAt(i), AttributeName.ID);
            if(oldRes==null || newRes==null)
                continue; // snafu, lets just ignore the rest and limp along
            
            // merge all potential new spawnIds from toMerge to this
            oldRes.mergeSpawnIDs(newRes, previousMergeIDs);
            final VElement oldResLeafsSpawned = oldRes.getNodesWithSpawnID(spawnID);
            for (int leaf = 0; leaf < oldResLeafsSpawned.size(); leaf++)
            {
                final JDFResource leafRes = (JDFResource)oldResLeafsSpawned.elementAt(leaf);
                //  handle multiple spawns (reference count of spawned audits!)
                leafRes.removeFromSpawnIDs(spawnID);

                if (!leafRes.hasAttribute(AttributeName.SPAWNIDS, null, false))
                {
                    leafRes.removeAttribute(AttributeName.SPAWNSTATUS);
                }

            }
            if(!newRes.getParentJDF().getID().equals(oldRes.getParentJDF().getID()))
            {
                // this has been copied from lower down up and MUST be deleted...
                newRes.deleteNode();
            }
            else
            {
                // don't use a simple for because deleting a parent may invalidate later resources!
                VElement newResLeafsSpawned = newRes.getNodesWithSpawnID(spawnID);
                // just in case: if no SpawnID exists assume the whole thing 
                if (newResLeafsSpawned.size() == 0)
                {
                    newResLeafsSpawned.add(newRes);
                }
                while (newResLeafsSpawned.size() > 0)
                {
                    // use the last because it is potentially the root...
                    final JDFResource leafRes = (JDFResource)newResLeafsSpawned.elementAt(newResLeafsSpawned.size() - 1);
                    final boolean bZappRoot = leafRes.equals(newRes);
                    leafRes.deleteNode();
                    // we killed the root, nothing can be left...
                    if (bZappRoot)
                    {
                        break;
                    }
                    // regenerate the list
                    newResLeafsSpawned = newRes.getNodesWithSpawnID(spawnID);
                }
            }
        }
    }

    /**
     * clean up the spawn and merge audits in this Node
     * <p>
     * default: CleanUpMerge(EnumCleanUpMerge cleanPolicy, JDFConstants.EMPTYSTRING, false)
     * 
     * @param cleanPolicy policy how to clean up the spawn and merge audits after merging
     * @param spawnID     the SpawnID of the spawn and MergeID of the merge to clean up.<br>
     *                    If not specified all spawns will be cleaned up.
     * @param bRecurse    if true also recurse into all child JDF nodes; default=false
     */

    private static void cleanUpMerge(JDFNode overWriteNode, EnumCleanUpMerge cleanPolicy, String spawnID, boolean bRecurse)
    {
        if(!cleanPolicy.equals(EnumCleanUpMerge.None))
        {
            if(bRecurse)
            {
                final Vector v = overWriteNode.getvJDFNode(null, null, false);
                for(int i = v.size(); i >= 0; i--)
                {
                    cleanUpMerge((JDFNode)v.elementAt(i),cleanPolicy, spawnID, false);
                }
            }
            else
            {
                JDFAuditPool auditPool = overWriteNode.getAuditPool();
                if (auditPool != null)
                    auditPool.cleanUpMerge(cleanPolicy, spawnID);
            }
        }
    }



}
