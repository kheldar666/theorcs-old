package org.libermundi.theorcs.bootstrap;

import org.apache.log4j.Logger;
import org.libermundi.theorcs.services.AuthorityService;
import org.libermundi.theorcs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(ApplicationLoader.class);
	
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
