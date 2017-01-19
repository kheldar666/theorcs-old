// [LICENCE-HEADER]
//
package org.libermundi.theorcs.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * A simple Entity that have an Id and is undeletable
 *
 */
@MappedSuperclass
public class BasicEntity extends NumericIdEntity implements Undeletable {
    private static final long serialVersionUID = 1L;
    
    private boolean _deleted=Boolean.FALSE;
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Undeletable#isDeleted()
     */
    @Override
    @Column(name = Undeletable.PROP_DELETED)
    public boolean isDeleted() {
        return _deleted;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Undeletable#setDeleted(boolean)
     */

    @Override
    public void setDeleted(boolean deleted) {
        _deleted = deleted;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.AbstractEntity#toString()
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return String.format("%s{id: %s; deleted: %s}", className, getId(), isDeleted());
    }
}
