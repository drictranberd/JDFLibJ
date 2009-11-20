/**
 * 
 */
package org.cip4.jdflib.extensions.xjdfwalker;

import java.util.Map;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFResourceLink;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.JDFResourceLink.EnumUsage;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.datatypes.VJDFAttributeMap;
import org.cip4.jdflib.elementwalker.BaseElementWalker;
import org.cip4.jdflib.elementwalker.BaseWalker;
import org.cip4.jdflib.elementwalker.BaseWalkerFactory;
import org.cip4.jdflib.extensions.XJDF20;
import org.cip4.jdflib.extensions.xjdfwalker.IDFinder.IDPart;
import org.cip4.jdflib.node.JDFNode;
import org.cip4.jdflib.node.JDFNode.EnumType;
import org.cip4.jdflib.pool.JDFAuditPool;
import org.cip4.jdflib.pool.JDFResourcePool;
import org.cip4.jdflib.resource.JDFPart;
import org.cip4.jdflib.resource.JDFResource;
import org.cip4.jdflib.resource.process.JDFMedia;
import org.cip4.jdflib.util.StringUtil;

/**
 * @author Rainer Prosi, Heidelberger Druckmaschinen
 * 
 */
public class XJDFToJDFConverter extends BaseElementWalker
{
	JDFDoc jdfDoc;
	// JDFNode theNode;
	Map<String, IDPart> idMap;
	boolean firstConvert;
	/**
	 * if true, create the product, else ignore it
	 */
	public boolean createProduct = true;

	/**
	 * @param template the jdfdoc to fill this into
	 * 
	 */
	public XJDFToJDFConverter(final JDFDoc template)
	{
		super(new BaseWalkerFactory());
		firstConvert = true;
		jdfDoc = template == null ? null : template.clone();
		// theNode = null;
		idMap = null;
	}

	/**
	 * @param xjdf
	 * @return the converted jdf
	 */
	public JDFDoc convert(final KElement xjdf)
	{
		if (jdfDoc == null)
		{
			jdfDoc = new JDFDoc("JDF");
		}
		if (!firstConvert)
		{
			JDFNode root = jdfDoc.getJDFRoot();
			if (!"Product".equals(root.getType()))
			{
				root = createProductRoot(root);
			}
		}
		final JDFNode theNode = findNode(xjdf, true);
		if (theNode == null)
		{
			return null;
		}

		idMap = new IDFinder().getMap(xjdf);
		walkTree(xjdf, theNode);
		final JDFNode root = jdfDoc.getJDFRoot();
		if ("Product".equals(root.getType()))
		{
			mergeProductLinks(theNode, root);
		}

		firstConvert = false;
		return jdfDoc;
	}

	/**
	 * @param xjdf
	 * @return true if the element can be converted
	 */
	public boolean canConvert(final KElement xjdf)
	{
		return xjdf == null ? false : XJDF20.rootName.equals(xjdf.getLocalName());
	}

	/**
	 * find and optionally create the appropriate node
	 * @param xjdf
	 * @param create if true, creat the new node
	 * @return the node
	 */
	private JDFNode findNode(KElement xjdf, final boolean create)
	{
		if (xjdf != null)
		{
			xjdf = xjdf.clone();
		}
		final JDFNode root = jdfDoc.getJDFRoot();
		final String jpID = xjdf.getAttribute(AttributeName.JOBPARTID, null, null);
		JDFNode n = root.getJobPart(jpID, null);
		if (n == null)
		{
			if (!root.hasAttribute(AttributeName.TYPE))
			{
				return root;
			}
			if (jpID == null)
			{
				final VElement nodes = root.getvJDFNode(null, null, false);
				final VString xTypes = StringUtil.tokenize(xjdf.getAttribute(AttributeName.TYPES), null, false);
				for (int i = 0; i < nodes.size(); i++)
				{
					final JDFNode n2 = (JDFNode) nodes.get(i);
					final VString vtypes = n2.getAllTypes();
					if (vtypes.containsAll(xTypes))
					{
						return n2;
					}
				}
			}
		}
		if (n == null && create)
		{
			n = root.addProcessGroup(new VString(xjdf.getAttribute(AttributeName.TYPES), null));
		}
		return n;
	}

	/**
	 * @param toCheck
	 * @return
	 */
	boolean isXResourceElement(final KElement toCheck)
	{
		boolean bReturn = false;

		if (toCheck != null)
		{
			KElement parent = toCheck.getParentNode_KElement();
			if (parent == null)
			{
				return bReturn;
			}

			parent = parent.getParentNode_KElement();
			if (parent == null)
			{
				return bReturn;
			}

			final boolean bL1 = parent.getLocalName().endsWith("Set");
			bReturn = bL1 && toCheck.getLocalName().equals(parent.getAttribute("Name"));
		}

		return bReturn;
	}

	/**
	 * @param toCheck
	 * @return
	 */
	boolean isXResource(final KElement toCheck)
	{
		final KElement parent = toCheck.getParentNode_KElement();
		if (parent == null)
		{
			return false;
		}

		final boolean bL1 = parent.getLocalName().endsWith("Set");
		return bL1 && parent.hasAttribute(AttributeName.NAME);
	}

	/**
	 * make sure we have a product in case we have multiple nodes
	 * @param theNode
	 * @return
	 */
	protected JDFNode createProductRoot(JDFNode theNode)
	{
		jdfDoc = new JDFDoc("JDF");
		final JDFNode parent = jdfDoc.getJDFRoot();
		parent.setType(EnumType.Product);
		theNode = (JDFNode) parent.copyElement(theNode, null);
		parent.moveAttribute(AttributeName.JOBID, theNode);
		mergeProductLinks(theNode, parent);
		return parent;
	}

	/**
	 * @param theNode
	 * @param parent
	 */
	private void mergeProductLinks(final JDFNode theNode, final JDFNode parent)
	{
		mergeProductLink(theNode, parent, ElementName.CUSTOMERINFO, EnumUsage.Input);
		mergeProductLink(theNode, parent, ElementName.NODEINFO, EnumUsage.Input);
		mergeProductLink(theNode, parent, ElementName.COMPONENT, EnumUsage.Output);
	}

	/**
	 * @param theNode
	 * @param parent
	 * @param resName
	 * @param enumUsage
	 */
	private void mergeProductLink(final JDFNode theNode, final JDFNode parent, final String resName, final EnumUsage enumUsage)
	{
		final JDFResource r = parent.getResource(resName, enumUsage, 0);
		if (r == null)
		{
			final JDFResourceLink link = theNode.getLink(0, resName, new JDFAttributeMap("Usage", enumUsage), null);
			if (link != null)
			{
				parent.ensureLink(link.getLinkRoot(), enumUsage, null);
			}
		}
	}

	/**
	 * 
	 * @author Rainer Prosi, Heidelberger Druckmaschinen
	 * 
	 */
	protected class WalkXElement extends BaseWalker
	{

		/**
		 *  
		 */
		public WalkXElement()
		{
			super(getFactory());
		}

		/**
		 * @param e
		 * @return element to continue with if must continue
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			cleanRefs(e, trackElem);
			final KElement e2 = trackElem.copyElement(e, null);
			e2.removeChildren(null, null, null); // will be copied later
			return e2;
		}

		/**
		 * @param e
		 */
		protected void cleanRefs(final KElement e, final KElement trackElem)
		{
			final JDFAttributeMap map = e.getAttributeMap();
			if (map == null)
			{
				return;
			}
			final VString keys = map.getKeys();
			if (keys != null)
			{
				final int keySize = keys.size();
				for (int i = 0; i < keySize; i++)
				{
					final String val = keys.get(i);
					if (val.endsWith("Ref") && !val.equals("rRef"))
					{
						final String value = map.get(val);
						final IDPart p = idMap.get(value);
						if (p != null)
						{
							final KElement refOld = trackElem != null ? trackElem.getElement(val) : null;
							final KElement ref = e.appendElement(val);
							ref.setAttribute("rRef", p.getID());

							final VJDFAttributeMap vpartmap = p.getPartMap();
							if (vpartmap != null)
							{
								for (int j = 0; j < vpartmap.size(); j++)
								{
									ref.appendElement(ElementName.PART).setAttributes(vpartmap.get(j));
								}
							}
							// we've been here already
							if (ref.isEqual(refOld))
							{
								ref.deleteNode();
							}
							e.removeAttribute(val);
						}
					}
				}
			}
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the xjdf root
	 */
	public class WalkXJDF extends WalkXElement
	{
		// ///////////////////////////////////////////////////////////////////////////////
		/**
		 * @param e
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final JDFNode theNode = (JDFNode) trackElem;
			theNode.setAttributes(e);
			theNode.setType(EnumType.ProcessGroup);
			return theNode;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && "XJDF".equals(toCheck.getLocalName());
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the xjdf root
	 */
	public class WalkSpan extends WalkXElement
	{
		// ///////////////////////////////////////////////////////////////////////////////
		/**
		 * invert XXXSpan/@Datatype=foo to FooSpan/@Name=Datatype
		 * @param e
		 * @return true if must continue
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final KElement eNew = trackElem.appendElement(e.getAttribute("Name"));
			eNew.setAttributes(e);
			eNew.removeAttribute(AttributeName.NAME);
			eNew.setAttribute(AttributeName.DATATYPE, e.getLocalName());
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
			return toCheck.getLocalName().endsWith("Span");
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkSet extends WalkXElement
	{
		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final JDFNode parent = (JDFNode) trackElem;
			final JDFNode root = parent.getJDFRoot();
			final EnumUsage inOut = EnumUsage.getEnum(e.getAttribute(AttributeName.USAGE));
			String id = e.getAttribute(AttributeName.ID, null, null);
			if (id == null)
			{
				// we need an id to copy. technically no id is invalid, but... whatever
				id = "r" + JDFElement.uniqueID(0);
				e.setAttribute(AttributeName.ID, id);
			}
			JDFResource r = null;
			final KElement idElem = parent.getCreateResourcePool().getChildWithAttribute(null, "ID", null, id, 0, true);
			if (idElem instanceof JDFResource)
			{
				r = (JDFResource) idElem;
			}
			else
			{
				r = (JDFResource) root.getChildWithAttribute(null, "ID", null, id, 0, false);
				if (r != null)
				{
					final JDFResourcePool rp = root.getCreateResourcePool();
					if (!rp.equals(r.getParentNode_KElement()))
					{
						rp.moveElement(r, null);
					}
				}
			}
			if (r == null)
			{
				r = parent.addResource(e.getAttribute("Name"), null);
			}
			if (r == null)
			{
				return null;
			}
			r.setAttributes(e);
			if (inOut != null)
			{
				final JDFResourceLink rl = parent.ensureLink(r, inOut, null);
				if (rl != null)
				{
					if (id != null)
					{
						rl.setrRef(id);
					}
					r.removeAttribute(AttributeName.USAGE);
					rl.moveAttribute(AttributeName.PROCESSUSAGE, r);
					rl.moveAttribute(AttributeName.AMOUNT, r);
					rl.moveAttribute(AttributeName.ACTUALAMOUNT, r);
					rl.moveAttribute(AttributeName.MAXAMOUNT, r);
					rl.moveAttribute(AttributeName.MINAMOUNT, r);
				}
			}
			r.removeAttribute(AttributeName.NAME);
			r.removeAttribute(AttributeName.USAGE);
			return r;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			final KElement parent = toCheck.getParentNode_KElement();
			final boolean bL1 = parent != null && (parent.getLocalName().equals("XJDF") || parent.getLocalName().equals("Product"));
			return bL1 && super.matches(toCheck) && toCheck.getLocalName().endsWith("Set") && toCheck.hasAttribute(AttributeName.NAME);
		}

	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkResource extends WalkXElement
	{
		/**
		 * @param e
		 * @return thr created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			cleanRefs(e, trackElem);
			e.removeAttribute("Class");
			trackElem.setAttributes(e);
			return trackElem;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			// test on grandparent
			return super.matches(toCheck) && isXResourceElement(toCheck);
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkReplace extends WalkXElement
	{
		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			trackElem.removeChildren(e.getNodeName(), null, null);
			return super.walk(e, trackElem);
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && (toCheck instanceof JDFAuditPool);
		}

	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkProduct extends WalkXElement
	{
		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			JDFNode theNode = (JDFNode) trackElem;
			if ("Product".equals(theNode.getType()))
			{
				theNode = theNode.addProduct();
			}
			else
			{
				theNode = createProductRoot(theNode);
			}

			theNode.setAttributes(e);
			return theNode;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && (toCheck.getLocalName().equals("Product"));
		}

	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkProductList extends WalkXElement
	{
		/**
		 * @param e
		 * @return the root, else null if we are in a second pass
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			e.deleteNode();
			// only convert products in the first pass
			// TODO rethink product conversion switch
			return createProduct && firstConvert ? jdfDoc.getJDFRoot() : null;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && (toCheck.getLocalName().equals("ProductList"));
		}

	}

	/**
	 * continue walking on these withot copying e
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkContinue extends WalkXElement
	{
		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			return trackElem;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			// final String name = toCheck.getLocalName();
			return false;
		}

	}

	/**
	 * simply stop walking on these
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkIgnore extends WalkXElement
	{
		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
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
			return super.matches(toCheck) && (toCheck instanceof JDFPart) && isXResource(toCheck.getParentNode_KElement());
		}

	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkXJDFColorResource extends WalkXJDFResource
	{
		/**
		 * @param e
		 * @param trackElem
		 * @return
		 */
		@Override
		protected JDFResource createPartition(final KElement e, final KElement trackElem, final JDFPart part)
		{
			final JDFNode theNode = ((JDFElement) trackElem).getParentJDF();
			final JDFResource r = (JDFResource) trackElem;
			final String sep = part.getSeparation();
			final KElement col = r.getChildWithAttribute("Color", "Name", null, sep, 0, true);
			if (col != null)
			{
				return null; // been here already
			}
			final JDFResource rPart = r.getCreatePartition(part.getPartMap(), part.guessPartIDKeys());
			final JDFResourceLink rll = theNode.getLink(r, null);
			if (rll != null)
			{
				rll.removeChildren(ElementName.PART, null, null);
			}
			rPart.renameElement(ElementName.COLOR, null);
			rPart.renameAttribute(AttributeName.SEPARATION, AttributeName.NAME, null, null);
			r.removeFromAttribute(AttributeName.PARTIDKEYS, AttributeName.SEPARATION, null, " ", -1);
			return rPart;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			final KElement parent = toCheck.getParentNode_KElement();
			if (parent == null)
			{
				return false;
			}

			final boolean bL1 = parent.getLocalName().endsWith("Set");
			return bL1 && super.matches(toCheck) && ElementName.COLOR.equals(parent.getAttribute(AttributeName.NAME));
		}

		/**
		 * @see org.cip4.jdflib.extensions.xjdfwalker.XJDFToJDFConverter.WalkXJDFResource#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final KElement rPart = super.walk(e, trackElem);
			if (rPart != null)
			{
				rPart.removeAttribute(AttributeName.STATUS);
			}
			return rPart;

		}

	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkXJDFResource extends WalkXElement
	{
		private JDFResourceLink rl = null;

		/**
		 * @param e
		 * @return the created resource
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final JDFNode theNode = ((JDFElement) trackElem).getParentJDF();
			JDFResource newPartition;
			final JDFPart part = (JDFPart) e.getElement(ElementName.PART);
			JDFAttributeMap partmap = null;
			if (part != null)
			{
				newPartition = createPartition(e, trackElem, part);
				partmap = part.getPartMap();
			}
			else
			{
				newPartition = (JDFResource) trackElem;
			}
			if (newPartition == null)
			{
				return null;
			}

			final JDFAttributeMap map = e.getAttributeMap();
			map.remove(AttributeName.ID);
			rl = theNode.getLink(newPartition, null);
			if (rl != null)
			{
				moveToLink(partmap, map, AttributeName.AMOUNT);
				moveToLink(partmap, map, AttributeName.ACTUALAMOUNT);
				moveToLink(partmap, map, AttributeName.MAXAMOUNT);

			}
			newPartition.setAttributes(map);

			return newPartition;
		}

		/**
		 * @param partmap
		 * @param map
		 * @param a
		 */
		private void moveToLink(final JDFAttributeMap partmap, final JDFAttributeMap map, final String a)
		{
			final VString vGW = new VString("Good Waste", null);
			for (int i = 0; i < vGW.size(); i++)
			{
				final String gw = vGW.get(i);
				final JDFAttributeMap pm = new JDFAttributeMap(partmap);
				pm.put("Condition", gw);
				if (map.get(a + gw) != null)
				{
					rl.setAmountPoolAttribute(a, map.get(a + gw), null, pm);
					map.remove(a + gw);
				}
			}
		}

		/**
		 * @param e
		 * @param trackElem
		 * @param part
		 * @return
		 */
		protected JDFResource createPartition(final KElement e, final KElement trackElem, final JDFPart part)
		{
			final JDFNode theNode = ((JDFElement) trackElem).getParentJDF();
			final JDFResource r = (JDFResource) trackElem;
			final JDFResource rPart = r.getCreatePartition(part.getPartMap(), part.guessPartIDKeys());
			final JDFResourceLink rll = theNode.getLink(r, null);
			if (rll != null)
			{
				rll.moveElement(part, null);
			}
			return rPart;
		}

		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && isXResource(toCheck);
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for the various resource sets
	 */
	public class WalkXJDFColorSet extends WalkSet
	{
		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return super.matches(toCheck) && ElementName.COLOR.equals(toCheck.getAttribute(AttributeName.NAME));
		}

		/**
		 * @see org.cip4.jdflib.extensions.xjdfwalker.XJDFToJDFConverter.WalkXElement#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final JDFNode theNode = (JDFNode) trackElem;
			final KElement k2 = super.walk(e, trackElem);
			final JDFResource r = (JDFResource) k2;
			final JDFResourceLink rl = theNode.getLink(r, null);
			r.renameElement(ElementName.COLORPOOL, null);
			rl.renameElement("ColorPoolLink", null);
			return k2;
		}
	}

	/**
	 * @author Rainer Prosi, Heidelberger Druckmaschinen walker for Media elements
	 */
	public class WalkMedia extends WalkResource
	{
		/**
		 * @see org.cip4.jdflib.elementwalker.BaseWalker#matches(org.cip4.jdflib.core.KElement)
		 * @param toCheck
		 * @return true if it matches
		 */
		@Override
		public boolean matches(final KElement toCheck)
		{
			return toCheck instanceof JDFMedia;
		}

		/**
		 * @see org.cip4.jdflib.extensions.xjdfwalker.XJDFToJDFConverter.WalkXJDFResource#walk(org.cip4.jdflib.core.KElement, org.cip4.jdflib.core.KElement)
		 */
		@Override
		public KElement walk(final KElement e, final KElement trackElem)
		{
			final KElement rPart = super.walk(e, trackElem);
			if (rPart != null)
			{
				final JDFResource root = ((JDFResource) rPart).getResourceRoot();
				if (root != null && root != rPart && !root.hasAttribute(AttributeName.MEDIATYPE))
				{
					root.copyAttribute(AttributeName.MEDIATYPE, rPart);
				}
			}
			return rPart;

		}
	}
}
