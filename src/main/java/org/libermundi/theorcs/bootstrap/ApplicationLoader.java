package org.libermundi.theorcs.bootstrap;

import org.apache.log4j.Logger;
import org.libermundi.theorcs.services.AuthorityService;
import org.libermundi.theorcs.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoader implements ApplicationListener<ContextRefreshedEvent> {

	private AuthorityService authorityService;
	
	private UserService userService;
	
	private Logger logger = Logger.getLogger(ApplicationLoader.class);

	public ApplicationLoader(AuthorityService authorityService, UserService userService) {
		this.authorityService = authorityService;
		this.userService = userService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(userService.count() == 0) {
			if(logger.isDebugEnabled()){
				logger.debug("Initializing Data for First launch of TheORCS");
			}

			initData();
		}
	}

	private void initData() {
		userService.initData();
		authorityService.initData();
	}

}
