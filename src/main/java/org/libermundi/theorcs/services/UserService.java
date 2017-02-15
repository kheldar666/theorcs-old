package org.libermundi.theorcs.services;

import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.services.base.Service;

/**
 * Interface of the User Manager Service
 *
 */
public interface UserService extends Service<User,Long> {
	public static final String SERVICE_ID = "userManager";
	
	/**
	 * Getter of User
	 * @return User
	 */
	User getUser();
	
	/**
	 * Getter of User by his Username
	 * @param login
	 * @return User
	 */
	User findByUsername(String username);
	
	
	/**
	 * Getter of User by his Nickname
	 * @param login
	 * @return User
	 */
	User findByNickName(String nickname);
	
	
	/**
	 * Get a user by his UID
	 * @param uid
	 * @return
	 */
	User findByUID(String uid);
	
	boolean isUsernameAvailable(String username);

	boolean isNickNameAvailable(String nickname);
	
	boolean isEmailAvailable(String email);
	
	String getUniqueNickName(String nickname);

	/**
	 * Get a user by his Email adress
	 * @param email
	 * @return User
	 */
	User findByEmail(String email);
	
}
