package org.libermundi.theorcs.bootstrap;

import org.apache.log4j.Logger;
import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent> {
	private UserService userService;
	
	private Logger logger = Logger.getLogger(UserLoader.class);
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(logger.isDebugEnabled()){
			logger.debug("Initializing User DB");
		}
		User rootUser = userService.createNew();
		rootUser.setUsername("root");
		rootUser.setPassword("root");
		rootUser.setNickName("Super Admin");
		rootUser.setFirstName("Super Administrator");
		rootUser.setLastName("");
		rootUser.setEmail("root@localhost");
		rootUser.setDeleted(Boolean.FALSE);
		rootUser.setActive(Boolean.TRUE);
		rootUser.setAccountNonLocked(Boolean.TRUE);
		rootUser.setAccountNonExpired(Boolean.TRUE);
		rootUser.setCredentialsNonExpired(Boolean.TRUE);
		userService.save(rootUser);				
	
	User adminUser = userService.createNew();
		adminUser.setUsername("admin");
		adminUser.setPassword("admin");
		adminUser.setNickName("Admin");
		adminUser.setFirstName("Administrator");
		adminUser.setLastName("");
		adminUser.setEmail("admin@localhost");
		adminUser.setDeleted(Boolean.FALSE);
		adminUser.setActive(Boolean.TRUE);
		adminUser.setAccountNonLocked(Boolean.TRUE);
		adminUser.setAccountNonExpired(Boolean.TRUE);
		adminUser.setCredentialsNonExpired(Boolean.TRUE);
		userService.save(adminUser);
	
	User stdUser1 = userService.createNew();
		stdUser1.setUsername("user1");
		stdUser1.setPassword("password");
		stdUser1.setNickName("User 1");
		stdUser1.setFirstName("John");
		stdUser1.setLastName("Doe");
		stdUser1.setEmail("user1@localhost");
		stdUser1.setDeleted(Boolean.FALSE);
		stdUser1.setActive(Boolean.TRUE);
		stdUser1.setAccountNonLocked(Boolean.TRUE);
		stdUser1.setAccountNonExpired(Boolean.TRUE);
		stdUser1.setCredentialsNonExpired(Boolean.TRUE);
		userService.save(stdUser1);
	
	User stdUser2 = userService.createNew();
		stdUser2.setUsername("user2");
		stdUser2.setPassword("password");
		stdUser2.setNickName("User 2");
		stdUser2.setFirstName("John");
		stdUser2.setLastName("Smith");
		stdUser2.setEmail("user2@localhost");
		stdUser2.setDeleted(Boolean.FALSE);
		stdUser2.setActive(Boolean.TRUE);
		stdUser2.setAccountNonLocked(Boolean.TRUE);
		stdUser2.setAccountNonExpired(Boolean.TRUE);
		stdUser2.setCredentialsNonExpired(Boolean.TRUE);
		userService.save(stdUser2);	
	}

}
