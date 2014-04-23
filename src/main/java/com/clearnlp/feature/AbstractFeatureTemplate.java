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
package com.clearnlp.feature;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.clearnlp.collection.list.SortedArrayList;
import com.clearnlp.util.XmlUtils;
import com.clearnlp.util.pair.ObjectIntPair;
import com.clearnlp.util.regex.Splitter;
import com.google.common.collect.Lists;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
abstract public class AbstractFeatureTemplate<FeatreTokenType> implements Serializable, FeatureXml
{
	private static final long serialVersionUID = 6926688863000363869L;
	private static final Pattern FIELD = Pattern.compile("^f(\\d+)$");
	
	private List<FeatreTokenType> l_tokens;
	private boolean b_visible;
	private String  s_type;
	
	public AbstractFeatureTemplate(Element eFeature)
	{
		init(eFeature);
	}
	
	private void init(Element eFeature)
	{
		String tmp = XmlUtils.getTrimmedAttribute(eFeature, A_VISIBLE);
		setVisible(tmp.isEmpty() || Boolean.parseBoolean(tmp));
		setType(XmlUtils.getTrimmedAttribute(eFeature, A_TYPE));
		l_tokens = Lists.newArrayList();
		
		for (ObjectIntPair<String> p : getFields(eFeature))
			l_tokens.add(getFeatureToken(p.o));
	}
	
	public List<FeatreTokenType> getFeatureTokens()
	{
		return l_tokens;
	}
	
	public String getType()
	{
		return s_type;
	}
	
	public void addFeatureToken(FeatreTokenType token)
	{
		l_tokens.add(token);
	}

	public void setVisible(boolean visible)
	{
		b_visible = visible;
	}
	
	public void setType(String type)
	{
		s_type = type;
	}
	
	public boolean isVisible()
	{
		return b_visible;
	}

	private List<ObjectIntPair<String>> getFields(Element element)
	{
		List<ObjectIntPair<String>> attributes = new SortedArrayList<>();
		NamedNodeMap nodes = element.getAttributes();
		int i, size = nodes.getLength();
		Matcher m;
		Node node;
		
		for (i=0; i<size; i++)
		{
		    node = nodes.item(i);
		    m = FIELD.matcher(node.getNodeName());
		    
		    if (m.find())
		    	attributes.add(new ObjectIntPair<String>(node.getNodeValue(), Integer.parseInt(m.group(1))));
		}

		return attributes;
	}
	
	private FeatreTokenType getFeatureToken(String str)
	{
		String[] t0 = Splitter.splitColons(str);		// "l-1_hd:p" -> {"l-1_hd", "p"}
		String[] t1 = Splitter.splitUnderscore(t0[0]);	// "l-1_hd"   -> {"l-1", "hd"} 
		String   s  = t1[0];
		
		String source = s.substring(0, 1);
		int    offset = 0;
		
		if (s.length() >= 2)
			offset = (s.charAt(1) == '+') ? Integer.parseInt(s.substring(2)) : Integer.parseInt(s.substring(1));
		
		String relation = (t1.length > 1) ? t1[1] : null;
		String field = t0[1];

		return createFeatureToken(source, relation, field, offset);
	}
	
	abstract protected FeatreTokenType createFeatureToken(String source, String relation, String field, int offset);
}