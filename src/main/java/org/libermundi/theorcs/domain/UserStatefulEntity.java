package org.libermundi.theorcs.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.libermundi.theorcs.domain.base.StatefulEntity;
import org.libermundi.theorcs.repositories.listeners.CreatedOrModifiedByListener;

@MappedSuperclass
@EntityListeners({CreatedOrModifiedByListener.class})
public class UserStatefulEntity extends StatefulEntity implements CreatedOrModifiedBy {
	private static final long serialVersionUID = -4034951542864595065L;
	
    private String _createdBy;
    private String _modifiedBy;

	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.model.CreateOrModifiedBy#getCreatedBy()
	 */
    @Override
    @Column(name = CreatedOrModifiedBy.PROP_CREATED_BY,length=30)
    public String getCreatedBy() {
        return _createdBy;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.theorcs.security.model.CreateOrModifiedBy#setCreatedBy(java.lang.String)
     */
    @Override
    public void setCreatedBy(String createdBy) {
        _createdBy = createdBy;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.theorcs.security.model.CreateOrModifiedBy#getModifiedBy()
     */
    
    @Override
    @Column(name = CreatedOrModifiedBy.PROP_MODIFIED_BY,length=30)
    public String getModifiedBy() {
        return _modifiedBy;
    }
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.theorcs.security.model.CreateOrModifiedBy#setModifiedBy(java.lang.String)
     */
    
    @Override
    public void setModifiedBy(String modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    
    /*
     * (non-Javadoc)
     * @see org.libermundi.theorcs.core.model.base.StatefulEntity#toString()
     */
    
    @Override    
    public String toString() {
        String className = getClass().getSimpleName();
        return String.format("%s{id: %s; active: %s; createdDate: %s; createdBy: %s; updatedDate: %s; modifiedBy: %s; hidden: %s}", 
                        className, getId(), isActive(), getCreatedDate(), getCreatedBy(), getModifiedDate(), getModifiedBy(), isDeleted());
    }

}
