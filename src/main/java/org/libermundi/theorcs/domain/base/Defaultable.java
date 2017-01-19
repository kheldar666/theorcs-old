// [LICENCE-HEADER]
//

package org.libermundi.theorcs.domain.base;

import java.io.Serializable;

/**
 * Interface marks class which have default flag. DAO will check if there is only
 * one default object using <code>getExample()</code> method.
 *
 */

public interface Defaultable<I extends Serializable> extends Identifiable<Serializable> {
    /**
     * Property which represents default flag.
     */
    static final String PROP_DEFAULT = "default";
        
    /**
     * Check if object is default one.
     *
     * @return true when object is default one
     */
    boolean isDefault();

    /**
     * Set object as default one.
     *
     * @param default value of default flag
     * @see #getExample()
     */
    void setDefault(boolean defaulz);
}
