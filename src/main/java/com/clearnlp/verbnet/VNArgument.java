/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clearnlp.verbnet;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.clearnlp.util.StringUtils;
import com.clearnlp.util.XmlUtils;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
public class VNArgument implements Serializable
{
	private static final long serialVersionUID = -8449681337356314249L;
	
	private String s_type;
	private String s_value;
	
	public VNArgument(Element eArgument)
	{
		init(eArgument);
	}
	
	private void init(Element eArgument)
	{
		setType (XmlUtils.getTrimmedAttribute(eArgument, VNXml.A_TYPE));
		setValue(XmlUtils.getTrimmedAttribute(eArgument, VNXml.A_VALUE));
		
		if (isType(VNXml.ARG_TYPE_THEM_ROLE) || isType(VNXml.ARG_TYPE_VERB_SPECIFIC))
			s_value = StringUtils.toLowerCase(s_value);
	}
	
	public String getType()
	{
		return s_type;
	}
	
	public String getValue()
	{
		return s_value;
	}
	
	public void setType(String type)
	{
		s_type = type;
	}
	
	public void setValue(String value)
	{
		s_value = value;
	}
	
	public boolean isType(String type)
	{
		return s_type.equals(type);
	}
}