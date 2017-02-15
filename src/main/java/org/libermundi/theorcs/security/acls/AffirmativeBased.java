/*
 * Copyright (c) 2012 the original author or authors.
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
package org.libermundi.theorcs.security.acls;

import java.util.List;

import org.libermundi.theorcs.security.AccessDecisionManagerPostProcessor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.util.Assert;

/**
 * @author Martin Papy
 *
 */
public class AffirmativeBased extends org.springframework.security.access.vote.AffirmativeBased {
	
	AccessDecisionManagerPostProcessor[] _postProcessors;

	public AffirmativeBased(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}
    
    @Override
	public void afterPropertiesSet() throws Exception {
    	prepareVoters(getDecisionVoters());
        Assert.notEmpty(getDecisionVoters(), "A list of AccessDecisionVoters is required");
        Assert.notNull(this.messages, "A message source must be set");
    }
    
    public void setAccessDecisionManagerPostProcessors(AccessDecisionManagerPostProcessor... postProcessors){
    	_postProcessors = postProcessors;
    }
    
	public AccessDecisionManagerPostProcessor[] getAccessDecisionManagerPostProcessors() {
		return _postProcessors;
	}    

	/**
	 * @param list 
	 * 
	 */
	protected void prepareVoters(List<AccessDecisionVoter<? extends Object>> list) {
		AccessDecisionManagerPostProcessor[] postProcessors = getAccessDecisionManagerPostProcessors();
		if (postProcessors != null) {
			for (AccessDecisionManagerPostProcessor postProcessor : postProcessors) {
				postProcessor.postProcessAccessDecisionManager(list);
			}
		}
	}    
}
