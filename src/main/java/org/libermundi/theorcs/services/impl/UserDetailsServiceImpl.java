package org.libermundi.theorcs.services.impl;
import javax.transaction.Transactional;

import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
 
	@Autowired
    private UserService userService;
 
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if(logger.isInfoEnabled()) {
			logger.info("loadUserByUsername({})", username);
		}
		User user = userService.findByUsername(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException("There is no User with login/username : " + username);
		} else {
			if(logger.isInfoEnabled()) {
				logger.info("Found User : {})", user);
			}
		}
		
		return user;
	}
}