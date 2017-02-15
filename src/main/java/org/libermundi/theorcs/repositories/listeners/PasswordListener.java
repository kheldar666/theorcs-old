package org.libermundi.theorcs.repositories.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.utils.SecurityUtils;

public class PasswordListener {
	@PrePersist
	@PreUpdate
	public void setPassword(User user) {
		String password = user.getPassword();
		if(!SecurityUtils.isValid(password)) {
			user.setPassword(SecurityUtils.encodePassword(password));
		}
	}
}
