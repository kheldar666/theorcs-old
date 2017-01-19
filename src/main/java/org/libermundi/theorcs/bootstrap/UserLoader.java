package org.libermundi.theorcs.bootstrap;

import org.apache.log4j.Logger;
import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent> {
	private UserRepository playerRepository;
	
	private Logger log = Logger.getLogger(UserLoader.class);
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.playerRepository = userRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		User player = new User();
			player.setName("Administrator");
			
		playerRepository.save(player);
		
		log.info("Saved Player - ID " + player.getId());
	}

}
