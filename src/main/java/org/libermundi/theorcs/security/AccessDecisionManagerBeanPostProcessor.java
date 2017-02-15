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
package org.libermundi.theorcs.security;

import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.security.access.AccessDecisionVoter;

import com.google.common.collect.Lists;

/**
 * @author Martin Papy
 *
 */
public class AccessDecisionManagerBeanPostProcessor implements BeanFactoryPostProcessor, AccessDecisionManagerPostProcessor {
	
    private static final Logger logger = LoggerFactory.getLogger(AccessDecisionManagerBeanPostProcessor.class);
    
    private static final String decisionManagersPostProcessors = "accessDecisionManagerPostProcessors"; 	
	
	private List<AccessDecisionVoter<MethodInvocation>> _voters;

	public void setVoters(List<AccessDecisionVoter<MethodInvocation>> voters){
		_voters = voters;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition accessDecisionManagerFactoryDefinition = beanFactory.getBeanDefinition("accessDecisionManager");
        PropertyValue propertyValue = accessDecisionManagerFactoryDefinition.getPropertyValues().getPropertyValue(decisionManagersPostProcessors);
        
        List<AccessDecisionManagerPostProcessor> admpp = Lists.newArrayList();

        if (propertyValue != null) {
        	AccessDecisionManagerPostProcessor[] currentPp = 
            		(AccessDecisionManagerPostProcessor[]) accessDecisionManagerFactoryDefinition.getPropertyValues().getPropertyValue(decisionManagersPostProcessors).getValue();

        	admpp.addAll(Arrays.asList(currentPp));
        }

        admpp.add(this);
        propertyValue = new PropertyValue(decisionManagersPostProcessors,admpp.toArray(new AccessDecisionManagerPostProcessor[admpp.size()]));
        accessDecisionManagerFactoryDefinition.getPropertyValues().addPropertyValue(propertyValue);

        if(logger.isDebugEnabled()){
        	logger.debug("AccessDecisionManagerPostProcessor added to AccessDecisionManager");
        }
	}

	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.security.AccessDecisionManagerPostProcessor#postProcessAccessDecisionManager(java.util.List)
	 */
	@Override
	public void postProcessAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> adv) {
        for (AccessDecisionVoter<MethodInvocation> voter : _voters) {
        	if(logger.isDebugEnabled()){
        		logger.debug("AccessDecisionManagerPostProcessor added voter :" + voter.toString());
        	}
        	adv.add(voter);
        }
		
	}

}
