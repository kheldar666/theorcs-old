package org.libermundi.theorcs.domain.listeners;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.libermundi.theorcs.domain.base.Timestampable;

public class TimestampListener {
	
	@PreUpdate
	public void setModifiedDate(Timestampable t) {
		Date modifiedDate = new Date();
		t.setModifiedDate(modifiedDate);		
	}
	
	@PrePersist
	public void setCreatedDate(Timestampable t) {
		Date createdDate = new Date();
		t.setCreatedDate(createdDate);
		t.setModifiedDate(createdDate);		
	}
}
