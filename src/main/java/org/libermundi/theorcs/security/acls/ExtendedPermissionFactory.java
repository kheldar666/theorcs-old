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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.model.Permission;

/**
 * @author Martin Papy
 *
 */
public class ExtendedPermissionFactory extends DefaultPermissionFactory {
	public final static String BEAN_ID="extendedPermissionFactory";
	private final static Logger logger = LoggerFactory.getLogger(ExtendedPermissionFactory.class);
	
	public ExtendedPermissionFactory() {
		super(); //Register the BasePermission
		if(logger.isDebugEnabled()){
			logger.debug("Instanciate the ExtendedPermissionFactory using Default Constructor");
		}
	}

	public ExtendedPermissionFactory(Class<? extends Permission> permissionClass) {
		super(permissionClass);
	}

	public ExtendedPermissionFactory(Map<String, ? extends Permission> namedPermissions) {
		super(namedPermissions);
	}
	
	public void registerPermission(Class<? extends Permission> permissionClass) {
		registerPublicPermissions(permissionClass);
	}

}
