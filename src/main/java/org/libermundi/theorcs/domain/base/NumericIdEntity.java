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
package org.libermundi.theorcs.domain.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Entity with ID is Long type
 * 
 */
@MappedSuperclass
public class NumericIdEntity extends AbstractEntity<Long> {
    private static final long serialVersionUID = 1L;
    
    private Long _id;
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Identifiable#getId()
     */
    @Override
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name=Identifiable.PROP_ID)
    public Long getId() {
        return _id;
    }

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.frostgrave.domain.base.Identifiable#setId(java.io.Serializable)
	 */
    @Override
	public void setId(Long id) {
        _id = id;
    }    
}
