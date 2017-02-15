package org.libermundi.theorcs.repositories.listeners;

import java.util.UUID;

import javax.persistence.PrePersist;

import org.libermundi.theorcs.domain.base.Uid;

public class UidListener {
	
	@PrePersist
	public void setUid(Uid uid) {
		if(uid.getUid() == null || uid.getUid().isEmpty()){
			uid.setUid(UUID.randomUUID().toString());
		}
	}
}
