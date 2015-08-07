/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2015 The International Cooperation for the Integration of 
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
package org.cip4.jdflib.extensions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.util.ContainerUtil;

/**
 *  
 * @author rainer prosi
 * @date Feb 17, 2012
 */
public abstract class BaseXJDFHelper
{
	private static Log log;

	protected BaseXJDFHelper()
	{
		super();
		if (log == null)
		{
			log = LogFactory.getLog(BaseXJDFHelper.class);
		}
	}

	/**
	 *  
	 * @param xpath
	 * @return
	 */
	public String getXPathValue(String xpath)
	{
		return theElement == null ? null : theElement.getXPathAttribute(xpath, null);
	}

	/**
	 * reorder elements in their canonical order - usually nop
	 */
	public void reorder()
	{
		return;
	}

	/**
	 * 
	 *  
	 * @param xpath
	 * @param value
	 */
	public void setXPathValue(String xpath, String value)
	{
		if (theElement != null)
		{
			theElement.setXPathValue(xpath, value);
		}
	}

	/**
	 * 
	 *generic cleanup routine
	 */
	public abstract void cleanUp();

	protected KElement theElement;

	/**
	 * 
	 * @return the xjdf root element
	 */
	public KElement getRoot()
	{
		return theElement;
	}

	/**
	 * 
	 * @return
	 */
	public KElement deleteNode()
	{
		KElement ret = theElement;
		if (theElement != null)
		{
			theElement.deleteNode();
			theElement = null;
		}
		return ret;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEqual(BaseXJDFHelper other)
	{
		if (theElement == null)
			return other == null || other.theElement == null;
		return theElement.isEqual(other.theElement);
	}

	/**
	 * get the ID from the generic Parameter or Resource element
	 * @return the ID , may be null in case this is connected to a null element
	 */
	public String getID()
	{
		return getXPathValue("@ID");
	}

	/**
	 * 
	 * @param newID
	 */
	public void setID(String newID)
	{
		setXPathValue("@ID", newID);
	}

	/**
	 * equals and hash are based on the xml element that this helper represents
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 42;
		result = prime * result + ((theElement == null) ? 0 : theElement.hashCode());
		return result;
	}

	/**
	 * equals and hash are based on the xml element that this helper represents
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseXJDFHelper other = (BaseXJDFHelper) obj;
		return ContainerUtil.equals(theElement, other.theElement);
	}

}
