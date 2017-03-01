// [LICENCE-HEADER]
//

package org.libermundi.theorcs.domain.base;

/**
 * Interface marks class which can be active or inactive.
 * 
 */

public interface Enabled {
    /**
     * Property which represents active flag.
     */
	static final String PROP_ACTIVE = "enabled";
    
    /**
     * Check if object is enabled.
     * 
     * @return true when object is enabled
     */
    boolean isEnabled();

    /**
     * Set object's enabled flag.
     * 
     * @param enaled
     *            value of enabled flag
     */
    void setEnabled(boolean enabled);
}
