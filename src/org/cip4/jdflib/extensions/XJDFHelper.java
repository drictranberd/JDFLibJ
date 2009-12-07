/**
 * 
 */
package org.cip4.jdflib.extensions;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.core.XMLDoc;

/**
  * @author Rainer Prosi, Heidelberger Druckmaschinen *
 */
public class XJDFHelper
{
	/**
	 * @param xjdf
	 */
	public XJDFHelper(KElement xjdf)
	{
		super();
		this.theXJDF = xjdf == null ? new XMLDoc(XJDF20.rootName, null).getRoot() : xjdf;
	}

	/**
	 * @return the vector of parametersets and resourcesets
	 */
	public VElement getSets()
	{
		VElement v = new VElement();
		KElement e = theXJDF.getFirstChildElement();
		while (e != null)
		{
			if (e.getLocalName().endsWith("Set"))
				v.add(e);
			e = e.getNextSiblingElement();
		}
		return v.size() == 0 ? null : v;

	}

	/**
	 * 
	 * @return
	 */
	public KElement getRoot()
	{
		return theXJDF;
	}

	/**
	 * @param name 
	 * @param iSkip 
	 * @return the vector of parametersets and resourcesets
	 */
	public KElement getSet(String name, int iSkip)
	{
		KElement e = theXJDF.getFirstChildElement();
		int n = 0;
		while (e != null)
		{
			if (e.getLocalName().endsWith("Set") && (name == null || name.equals(e.getAttribute("Name", null, null))))
			{
				if (n++ == iSkip)
				{
					return e;
				}
			}
			e = e.getNextSiblingElement();
		}
		return null;
	}

	/**
	 * @param family 
	 * @param name 
	 * @return a new set element
	 */
	public SetHelper appendSet(String family, String name)
	{
		KElement newSet = theXJDF.appendElement(family + "Set");
		newSet.setAttribute("Name", name);
		return new SetHelper(newSet);
	}

	/**
	 * @param name 
	 * @return a new set element
	 */
	public SetHelper appendParameter(String name)
	{
		return appendSet("Parameter", name);
	}

	/**
	 * @param name 
	 * @return a new set element
	 */
	public SetHelper appendResource(String name)
	{
		return appendSet("Resource", name);
	}

	protected KElement theXJDF;

	/**
	 * @see java.lang.Object#toString()
	 * @return
	*/
	@Override
	public String toString()
	{
		return "XJDFHelper: " + theXJDF;
	}
}
