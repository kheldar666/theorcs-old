package org.libermundi.theorcs.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.libermundi.theorcs.domain.base.Uid;
import org.libermundi.theorcs.repositories.listeners.UidListener;

@MappedSuperclass
@EntityListeners(UidListener.class)
public class UidUserStatefulEntity extends UserStatefulEntity implements Uid {
	private static final long serialVersionUID = -5773318507226695986L;
	private String _uid;
    
    /*
     * (non-Javadoc)
     * @see org.libermundi.theorcs.security.model.Uid#getUid()
     */
    @Override
	@Basic
    @Column(name = Uid.PROP_UID,length=36)
	public String getUid() {
		return _uid;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.model.Uid#setUid(java.lang.String)
	 */
    @Override
	public void setUid(String uid) {
		_uid=uid;
	}
    
}
