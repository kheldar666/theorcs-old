package org.libermundi.theorcs.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.libermundi.theorcs.domain.base.StatefulEntity;

@Entity
public class User extends StatefulEntity {
	private static final long serialVersionUID = 1148915595029084574L;

	public final static String PROP_NAME="name";
    
	@Basic
	@Column(name = User.PROP_NAME, length=50, nullable=false)
    private String name;
	
    
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


}
