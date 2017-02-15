package org.libermundi.theorcs.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.libermundi.theorcs.domain.base.BasicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authority Security Model Object
 * 
 */
@Entity(name="Authority")
@Table(name="tbl_authority")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Authority extends BasicEntity implements GrantedAuthority {
	public static final String PROP_AUTHORITY="authority";

	
	private static final long serialVersionUID	= 2708517882549523163L;
	private static final Logger logger = LoggerFactory.getLogger(Authority.class);	
	private String _authority;
	private Set<User> _users = new HashSet<>(0);
	
	public Authority() {
	}
	
	public Authority(String authority) {
		setAuthority(authority);
	}

	/**
	 * @return authority
	 */
	@Override
	@Basic
	@Column(name=Authority.PROP_AUTHORITY,length=30,nullable=false)
    public String getAuthority() {
        return this._authority;
    }

    public void setAuthority(String authority) {
        this._authority = authority;
    }
    
    @ManyToMany(mappedBy="roles",fetch=FetchType.LAZY)
	public Set<User> getUsers() {
    	return _users;
    }
    
    public void setUsers(Set<User> users) {
    	_users = users;
    }

	@Override
	public String toString() {
		return _authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((_authority == null) ? 0 : _authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = Boolean.FALSE;
		if (this == obj) {
			result = Boolean.TRUE;
		}
		if(obj instanceof String) {
			result = getAuthority().equals(obj);
		}
		if((obj instanceof GrantedAuthority)) {
			GrantedAuthority other = (GrantedAuthority) obj;
			result = Boolean.valueOf(getAuthority().equals(other.getAuthority()));
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Testing equality between : '{}' and '{}'",this,obj);
			logger.debug("Result : {}",result);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object otherAuthority) {
		return _authority.compareTo(((GrantedAuthority)otherAuthority).getAuthority());
	}
}
