// [LICENCE-HEADER]
//

package org.libermundi.theorcs.domain.base;

/**
 * Interface marks class which cannot be deleted. If someone calls one of DAO's delete
 * methods object will be hidden instead of deleted.
 *
 */

public interface Undeletable {
    /**
     * Property which represents deleted flag.
     */
	static final String PROP_DELETED = "deleted";
        
    /**
     * Check if object is deleted.
     *
     * @return true when object is deleted
     */
    boolean isDeleted();

    /**
     * Set object as default one.
     *
     * @param deleted value of deleted flag
     */
    void setDeleted(boolean deleted);
}
