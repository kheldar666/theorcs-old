// [LICENCE-HEADER]
//

package org.libermundi.theorcs.domain.base;

/**
  * Interface marks class which is inheritable. There is unlimited number of
 * levels. Parent must have the same type as child.
 *
 * @param <T> parent's type, must be the same as child's type
 *
 */

public interface Inheritable<T> {
    /**
     * Property which represents parent.
     */
	static final String PROP_PARENT = "parent";
    
    /**
     * Get object's parent.
     *
     * @return parent
     */
    T getParent();

    /**
     * Set object's parent. Null means root level object (no parent).
     *
     * @param parent parent
     */
    void setParent(T parent);
}
