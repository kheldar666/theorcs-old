// [LICENCE-HEADER]
//

package org.libermundi.theorcs.domain.base;

/**
 * Interface marks class which can be active or inactive.
 * 
 */

public interface Activable {
    /**
     * Property which represents active flag.
     */
	static final String PROP_ACTIVE = "active";
    
    /**
     * Check if object is active.
     * 
     * @return true when object is active
     */
    boolean isActive();

    /**
     * Set object's active flag.
     * 
     * @param active
     *            value of active flag
     */
    void setActive(boolean active);
}
