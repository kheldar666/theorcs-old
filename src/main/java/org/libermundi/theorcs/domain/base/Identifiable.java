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

import java.io.Serializable;

/**
 * Interface marks class which can be persisted.
 * 
 * @param <I> type of primary key, it must be {@link Serializable}
 * 
 */

public interface Identifiable<I extends Serializable> extends Serializable {
    /**
     * Property which represents identify.
     */
    static final String PROP_ID = "id";
        
    /**
     * Get primary key.
     * 
     * @return primary key
     */
    I getId();

    /**
     * Set primary key.
     * 
     * @param id
     *            primary key
     */
    void setId(I id);
}
