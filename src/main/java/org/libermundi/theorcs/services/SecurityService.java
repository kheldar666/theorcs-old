package org.libermundi.theorcs.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libermundi.theorcs.domain.Authority;
import org.libermundi.theorcs.domain.RememberMeToken;
import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.services.base.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService extends Service<Authority,Long> {
		
	/**
	 * Getter of the User attached to the current Session
	 * @return the current authenticated User
	 */
	User getCurrentUser();
	
	/**
	 * Gets the {@link UserDetails} in the {@link SecurityContext}. If no UserDetails is defined {@link UserDetails} for the "ANONYMOUS" user.
	 * @see {@link SecurityService#getAnonymousUserDetails()}
	 * @return {@link UserDetails}
	 */
	UserDetails getCurrentUserDetails();

	/**
	 * Getter of the Name of the User attached to the current Session
	 * @return the current authenticated User
	 */
	
	String getCurrentUsername();
	
	/**
	 * Check if current {@link User} is logged in
	 * @return true when current user is logged in
	 */
	boolean isLoggedIn();
	
	/**
	 * Set an {@link Authentication} in the {@link SecurityContext} for the provided {@link User}
	 * @param user
	 * @return corresponding {@link Authentication}
	 */
	Authentication authenticate(User user);
	
	/**
	 * Set an {@link Authentication} in the {@link SecurityContext} for the provided {@link User}. Allows to
	 * create a {@link RememberMeToken} if needed. 
	 * @param user
	 * @param rememberMe is true, will create a {@link RememberMeToken} and return the corresponding {@link Cookie}s
	 * @param request
	 * @param response
	 * @return {@link Authentication}
	 */	
	Authentication authenticate(User user, boolean rememberMe, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Load {@link Authority} stored in the Database based on the Role provided
	 * @param role
	 * @return Corresponding {@link Authority} or <code>null</code>
	 */
	Authority getAuthority(String role);
	
	/**
	 * Check if the {@link Authentication} provided is in the Collection of authorities either directly or through transitive relation 
	 * @param authorities
	 * @param authority
	 * @return true if 
	 * 
	 * @see RoleHierarchy#getReachableGrantedAuthorities(Collection)
	 */
	//boolean hasReachableAuthority(Collection<? extends GrantedAuthority> authorities, GrantedAuthority authority);
	
	/**
	 * @see SecurityService#hasReachableAuthority(Collection, GrantedAuthority) 
	 * @param user
	 * @param authority
	 * @return
	 */
	//boolean hasReachableAuthority(User user, GrantedAuthority authority);
	
	/**
	 * Return {@link UserDetails} for the "SYSTEM" user. Can be stored in the DB or Localy as a static resource
	 * @return "SYSTEM" {@link UserDetails}
	 */
	UserDetails getSystemUserDetails();
	
	/**
	 * Return {@link UserDetails} for the "ANONYMOUS" user. Can be stored in the DB or Localy as a static resource
	 * @return "SYSTEM" {@link UserDetails}
	 */
	UserDetails getAnonymousUserDetails();

	/**
	 * Allow to Switch from current {@link UserDetails} to the provided one. The {@link UserDetails} will be replace in
	 * the {@link SecurityContext} and the previous one will be kept in order to be restored later with {@link SecurityService#restoreUser()}
	 * @param switchDetails
	 */
	void switchUser(UserDetails switchDetails);

	/**
	 * Try to restore the previous User after a Switch has been done using {@link SecurityService#switchUser(UserDetails)}.
	 * Does nothing is no previous Switch have been done.
	 */
	void restoreUser();

	Authentication getCurrentAuthentication();	
	
	// ~ ACL MANAGEMENT -----------------------------------------------------------
	/**
	 * Basic Method to set ACL
	 * @param owner Owner of the Acl
	 * @param to Object on which ACL applies
	 * @param permission {@link BasePermission} applied
	 * @param granting set to true when the right is granted, false when revoked 
	 */
	//void setAcl(Object owner,Object to, Permission permission, boolean granting);
	
	/**
	 * Grant {@link BasePermission#ADMINISTRATION}, {@link BasePermission#WRITE} and {@link BasePermission#READ} to Owner
	 * @param owner
	 * @param to
	 */
	//void grantAdminAcl(Object owner,Object to);
	
	/**
	 * Grant {@link BasePermission#WRITE} and {@link BasePermission#READ} to Owner
	 * @param owner
	 * @param to
	 */
	//void grantReadWriteAcl(Object owner,Object to);
	
	/**
	 * Grant {@link BasePermission#READ} to Owner
	 * @param owner
	 * @param to
	 */
	//void grantReadAcl(Object owner,Object to);
	
	/**
	 * Check if the currently logged User as the given {@link Permission} on the {@link Object}
	 * @param obj
	 * @param permission
	 * @return true if User has.
	 */
	//boolean hasPermission(Object obj, Permission... permission);
	
	/**
	 * Assign a Role to the User. The Role is supposed to exist in the Database.
	 * @param user
	 * @param role
	 */
	void grantRole(User user, String role);

	/**
	 * The opposite of the previous one :)
	 * @param user
	 * @param role
	 */
	void removeRole(User user, String role);
}
