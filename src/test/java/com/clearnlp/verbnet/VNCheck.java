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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
public class VNCheck
{
//	@Test
	public void checkThematicRoleSequence()
	{
		VNMap vnMap = VNLib.getVerbNetMap("/Users/jdchoi/Desktop/verbnet/verbnet-3.2", true);
		List<String> ids = Lists.newArrayList(vnMap.keySet());
		Map<String,Set<String>> map;
		Collections.sort(ids);
		String roles, preds;
		Set<String> set;
		int total = 0;
		VNClass vn;
		
		for (String id : ids)
		{
			vn  = vnMap.get(id);
			map = Maps.newHashMap();
			
			for (VNFrame frame : vn.getFrameList())
			{
				roles = "["+frame.getSyntax().toString(", ",true)+"]";
				preds = frame.getSemantics().toString();
				set   = map.get(roles);
				
				if (set == null)
					map.put(roles, Sets.newHashSet(preds));
				else
				{
					if (!set.contains(preds))
					{
						System.out.println(vn.getLemma()+"-"+id+": "+roles);
						set.add(preds);
					}
				}
				
				total++;
			}
		}
		
		System.out.println(total);
	}
}