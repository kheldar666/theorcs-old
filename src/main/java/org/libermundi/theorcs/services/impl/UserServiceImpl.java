package org.libermundi.theorcs.services.impl;

import java.util.UUID;

import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.repositories.UserRepository;
import org.libermundi.theorcs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserManager
 *
 */
@Service(UserService.SERVICE_ID)
@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
public class UserServiceImpl extends AbstractServiceImpl<User,Long> implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		setRepository(userRepository);
	}
	
	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.main.services.UserManager#getUser()
	 */
	@Override
	public User getUser() {
		User user = new User();
		user.setUid(UUID.randomUUID().toString());
		user.setActive(Boolean.FALSE);
		return user;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username) {
		return ((UserRepository)getRepository()).findByUsername(username);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#findByNickname(java.lang.String)
	 */
	@Override
	public User findByNickName(String nickname) {
		return ((UserRepository)getRepository()).findByNickName(nickname);
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#findByUID(java.lang.String)
	 */
	@Override
	public User findByUID(String uid) {
		return ((UserRepository)getRepository()).findByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#isUsernameAvailable(java.lang.String)
	 */
	@Override
	public boolean isUsernameAvailable(String username) {
		if(findByUsername(username) != null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;		
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#isNickNameAvailable(java.lang.String)
	 */
	@Override
	public boolean isNickNameAvailable(String nickname) {
		if(findByNickName(nickname) != null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.UserManager#isEmailAvailable(java.lang.String)
	 */
	@Override
	public boolean isEmailAvailable(String email) {
		if(findByEmail(email) != null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.UserManager#getUniqueNickName(java.lang.String)
	 */
	@Override
	public String getUniqueNickName(String nickname) {
		String testNickName = nickname;
		int i = 1;
		while(!isNickNameAvailable(testNickName)){
			testNickName = nickname + "_" + i;
			i++;
		}
		return testNickName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public User findByEmail(String email) {
		return ((UserRepository)getRepository()).findByEmail(email);		
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#createNew()
	 */
	@Override
	public User createNew() {
		User user = new User();
		user.setUid(UUID.randomUUID().toString());
		user.setActive(Boolean.FALSE);
		return user;
	}
	
}
