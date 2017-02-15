package org.libermundi.theorcs.repositories.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.libermundi.theorcs.domain.CreatedOrModifiedBy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CreatedOrModifiedByListener {
	
	@PrePersist
	public void setCreatedBy(CreatedOrModifiedBy c) {
		String username = getCurrentUsername();
		c.setCreatedBy(username);
		c.setModifiedBy(username);
	}

	@PreUpdate
	public void setModifiedBy(CreatedOrModifiedBy c) {
		String username = getCurrentUsername();
		c.setModifiedBy(username);
	}

	/**
	 * Try to get the current logged in user. If he cannot get logged in user
	 * in any context, he will return <b>System</b> as default
	 * 
	 * @return the login name of logged in user.
	 * */

	private static String getCurrentUsername() {

		UserDetails userDetails = getUserDetails(SecurityContextHolder.getContext());
		
		if (userDetails == null || userDetails.getUsername().isEmpty()) {
			return "System"; // return System as default.
		}
		
		return userDetails.getUsername();
	}

	private static UserDetails getUserDetails(SecurityContext context) {
		try {
			Object principle = context.getAuthentication().getPrincipal();
			if (principle != null && principle instanceof UserDetails) {
				return (UserDetails) principle;
			}
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
