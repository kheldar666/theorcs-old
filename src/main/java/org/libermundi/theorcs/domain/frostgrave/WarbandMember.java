package org.libermundi.theorcs.domain.frostgrave;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.libermundi.theorcs.domain.base.BasicEntity;

@Entity
public class WarbandMember extends BasicEntity {

	private static final long serialVersionUID = -5697281399446267065L;
	
	private WarbandMemberType type;

	@Enumerated(EnumType.STRING)
	public WarbandMemberType getType() {
		return type;
	}

	public void setType(WarbandMemberType type) {
		this.type = type;
	}
}
