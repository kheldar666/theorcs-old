// [LICENCE-HEADER]
//
package org.libermundi.theorcs.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.libermundi.theorcs.domain.listeners.TimestampListener;


/**
 * Entity with full status: Identifiable, Activable, Undeletable and Timestampable
 *
 */
@MappedSuperclass
@EntityListeners(TimestampListener.class)
public class StatefulEntity extends BasicEntity implements Activable, Timestampable {
    private static final long serialVersionUID = 1L;
    
    private boolean _active = Boolean.TRUE;
    private Date _createdDate;
    private Date _modifiedDate;

    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Activable#isActive()
     */
    
    @Override
    @Column(name = Activable.PROP_ACTIVE)
    public boolean isActive() {
        return _active;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Activable#setActive(boolean)
     */
    
    @Override
    public void setActive(boolean active) {
        _active = active;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Timestampable#getCreatedDate()
     */
    
    @Override
    @Column(name = Timestampable.PROP_CREATED_DATE)
    public Date getCreatedDate() {
        return _createdDate;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Timestampable#setCreatedDate(java.util.Date)
     */
    
    @Override
    public void setCreatedDate(Date createdDate) {
    	if(createdDate != null)
    		_createdDate = new Date(createdDate.getTime());
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Timestampable#getModifiedDate()
     */
    
    @Override
    @Column(name = Timestampable.PROP_MODIFIED_DATE)
    public Date getModifiedDate() {
        return _modifiedDate;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.Timestampable#setModifiedDate(java.util.Date)
     */
    
    @Override
    public void setModifiedDate(Date modifiedDate) {
        _modifiedDate = new Date(modifiedDate.getTime());
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.frostgrave.domain.base.BasicEntity#toString()
     */
    
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return String.format("%s{id: %s; active: %s; createdDate: %s; updatedDate: %s; hidden: %s}", 
                        className, getId(), isActive(), getCreatedDate(), getModifiedDate(), isDeleted());
    }
}
