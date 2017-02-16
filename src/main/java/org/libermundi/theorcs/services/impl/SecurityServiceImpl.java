package org.libermundi.theorcs.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libermundi.theorcs.domain.Authority;
import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.repositories.AuthorityRepository;
import org.libermundi.theorcs.security.SecurityConstants;
import org.libermundi.theorcs.services.SecurityService;
import org.libermundi.theorcs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Martin Papy
 *
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
public class SecurityServiceImpl extends AbstractServiceImpl<Authority,Long> implements SecurityService {
	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Autowired
	private UserService _userService;
	
	@Autowired
	private RememberMeServices _rememberMeServices;
	
	//@Autowired
	//private MutableAclService _aclServices;
	
	//@Autowired
	//private AclPermissionEvaluator _aclPermissionEvaluator;
	
	//@Autowired
	//private RoleHierarchy _roleHierarchy;
	
	@Autowired
	public SecurityServiceImpl(AuthorityRepository authorityRepository) {
		super();
		setRepository(authorityRepository);
	}

	public UserService getUserManager() {
		return _userService;
	}

	public void setUserManager(UserService userService) {
		_userService = userService;
	}

	/* (non-Javadoc)
	 * @see org.libermundi.security.services.SecurityManager#getCurrentUserDetails()
	 */
	@Override
	public UserDetails getCurrentUserDetails() {
		if (!(getCurrentAuthentication().getPrincipal() instanceof UserDetails)) {
			return ANONYMOUS_USERDETAILS;
		}
		return (UserDetails)getCurrentAuthentication().getPrincipal();
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		UserDetails ud = getCurrentUserDetails();
		if(ud instanceof User) {
			return (User)ud;
		}
		return null;
	}		
	
	/* (non-Javadoc)
	 * @see org.libermundi.security.services.SecurityManager#getCurrentUsername()
	 */
	@Override
	public String getCurrentUsername() {
		if(isLoggedIn()) {
			return getCurrentUserDetails().getUsername();
		}
		return ANONYMOUS_USERDETAILS.getUsername();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if(logger.isInfoEnabled()) {
			logger.info("loadUserByUsername({})", username);
		}
		User user = _userService.findByUsername(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException("There is no User with login/username : " + username);
		}
		
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		if(getCurrentAuthentication() == null) {
			return false;
		} else if(getCurrentAuthentication().isAuthenticated() 
				&& getCurrentAuthentication().getPrincipal() instanceof UserDetails) {
			return true; 
		} else {
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#authenticate(org.libermundi.theorcs.security.model.User)
	 */
	@Override
	public Authentication authenticate(User user) {
		Authentication auth = new PreAuthenticatedAuthenticationToken(user, user.getUsername(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#authenticate(org.libermundi.theorcs.security.model.User, boolean, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication authenticate(User user,boolean rememberMe, HttpServletRequest request, HttpServletResponse response ) {
		Authentication auth = authenticate(user);
		if(rememberMe) {
			_rememberMeServices.loginSuccess(request, response, auth);
			if(logger.isDebugEnabled()) {
				logger.debug("Set RememberMe Token");
			}
		}
		return auth;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#getAuthority(java.lang.String)
	 */
	@Override
	public Authority getAuthority(String role) {
		return ((AuthorityRepository)getRepository()).findByAuthority(role);
	}
	
	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#hasReachableAuthority(java.util.Collection, org.springframework.security.core.GrantedAuthority)
	 */
	/*
	@Override
	public boolean hasReachableAuthority(
			Collection<? extends GrantedAuthority> authorities, GrantedAuthority authority) {
		return _roleHierarchy.getReachableGrantedAuthorities(authorities).contains(authority);
	}
	*/
	
	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#hasReachableAuthority(org.libermundi.theorcs.security.model.User, org.springframework.security.core.GrantedAuthority)
	 */
	/*
	@Override
	public boolean hasReachableAuthority(User user, GrantedAuthority authority) {
		return hasReachableAuthority(user.getAuthorities(),authority);
	}
	*/
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#switchUser(org.springframework.security.core.userdetails.UserDetails)
	 */
	@Override
	public void switchUser(UserDetails switchDetails) {
		Authentication oldAuth = getCurrentAuthentication();

		logger.warn("Switching User Authentication : from [ " + getCurrentUsername() + " ] to [ " + switchDetails.getUsername() +" ]");
		
		GrantedAuthority switchedAuth = new SwitchUserGrantedAuthority(ROLE_PREVIOUS_USER, oldAuth);
		ArrayList<GrantedAuthority> newAuthorities = new ArrayList<>(switchDetails.getAuthorities());
		newAuthorities.add(switchedAuth);
		
		Authentication auth2Switch = buildAuthentication(switchDetails, newAuthorities);
				
		SecurityContextHolder.getContext().setAuthentication(auth2Switch);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#restoreUser()
	 */
	@Override
	public void restoreUser() {
		Authentication switchedAuth = getCurrentAuthentication();
		
		GrantedAuthority[] switchedGA = switchedAuth.getAuthorities().toArray(new GrantedAuthority[switchedAuth.getAuthorities().size()]);
		for (int i = 0; i < switchedGA.length; i++) {
			GrantedAuthority ga = switchedGA[i];
			if(ga instanceof SwitchUserGrantedAuthority) {
				Authentication origAuth = ((SwitchUserGrantedAuthority)ga).getSource();
				logger.warn("Restoring User Authentication : from [ " + getCurrentUsername() + " ] to [ " + ((UserDetails)origAuth.getPrincipal()).getUsername() +" ]");
				SecurityContextHolder.getContext().setAuthentication(origAuth);
				return;
			}
		}
	
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#getCurrentAuthentication()
	 */
	@Override
	public Authentication getCurrentAuthentication() {
		Authentication authz = SecurityContextHolder.getContext().getAuthentication();
		if(authz == null){
			authz = new AnonymousAuthenticationToken(UUID.randomUUID().toString(), ANONYMOUS_USERDETAILS, ANONYMOUS_USERDETAILS.getAuthorities());
		}
		return authz;
	}

	// ~ ACL Management ------------------------------------------------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#setAcl(java.lang.Object, java.lang.Object, org.springframework.security.acls.model.Permission, boolean)
	 */
	/*
	@Override
	public void setAcl(Object owner, Object to, Permission permission, boolean granting) {
		ObjectIdentity oi = new ObjectIdentityImpl(to);
		Sid sid;
		if(owner instanceof User) {
			sid = new PrincipalSid(((User)owner).getUsername()); 
		} else {
			sid = new GrantedAuthoritySid(((Authority)owner).getAuthority()); 
		}

		MutableAcl acl;
		try {
			acl = (MutableAcl)_aclServices.readAclById(oi);
		} catch (NotFoundException e) {
			acl = _aclServices.createAcl(oi);
		}
		
		acl.insertAce(acl.getEntries().size(), permission, sid, granting);
		_aclServices.updateAcl(acl);
	}	
	*/
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#grantAdminAcl(java.lang.Object, java.lang.Object)
	 */
	/*
	@Override
	public void grantAdminAcl(Object owner, Object to) {
		this.setAcl(owner, to, BasePermission.ADMINISTRATION, Boolean.TRUE);
		grantReadWriteAcl(owner,to);
	}
	*/
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#grantReadWriteAcl(java.lang.Object, java.lang.Object)
	 */
	/*
	@Override
	public void grantReadWriteAcl(Object owner, Object to) {
		this.setAcl(owner, to, BasePermission.WRITE, Boolean.TRUE);
		grantReadAcl(owner, to);
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#grantReadAcl(java.lang.Object, java.lang.Object)
	 */
	/*
	@Override
	public void grantReadAcl(Object owner, Object to) {
		this.setAcl(owner, to, BasePermission.READ, Boolean.TRUE);
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#hasPermission(java.lang.Object, org.springframework.security.acls.model.Permission[])
	 */
	/*
	@Override
	public boolean hasPermission(Object obj, Permission... permission) {
		return _aclPermissionEvaluator.hasPermission(getCurrentAuthentication(), obj, permission);
	}
	*/
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#grantRole(org.libermundi.theorcs.security.model.User, java.lang.String)
	 */
	@Override
	public void grantRole(User user, String role) {
		Authority newRole = getAuthority(role);
		if( newRole == null) {
			throw new RuntimeException("The role [ " + role + " ] does not exist in the Database");
		}
		Set<Authority> roles = user.getRoles();
		if(!roles.contains(newRole)){
			roles.add(newRole);
			_userService.save(user);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#removeRole(org.libermundi.theorcs.security.model.User, java.lang.String)
	 */
	@Override
	public void removeRole(User user, String role) {
		Authority oldRole = getAuthority(role);
		Set<Authority> roles = user.getRoles();
		if(roles.contains(oldRole)){
			roles.remove(oldRole);
			_userService.save(user);
		}
		
	}

	// ~ ----------------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#getSystemUserDetails()
	 */
	@Override
	public UserDetails getSystemUserDetails() {
		return SYSTEM_USERDETAILS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.security.services.SecurityManager#getAnonymousUserDetails()
	 */
	@Override
	public UserDetails getAnonymousUserDetails() {
		return ANONYMOUS_USERDETAILS;
	}
	
	private static final UserDetails ANONYMOUS_USERDETAILS=new UserDetails() {
		private static final long serialVersionUID = 4018460955903946952L;
		private final List<GrantedAuthority> _roles = initRoles();

		@Override
		public boolean isEnabled() {
			return Boolean.TRUE;
		}
		
		private List<GrantedAuthority> initRoles() {
			List<GrantedAuthority> roles = new ArrayList<>(1);
			roles.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_ANONYMOUS));			
			return roles;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return Boolean.TRUE;
		}
		
		@Override
		public boolean isAccountNonLocked() {
			return Boolean.TRUE;
		}
		
		@Override
		public boolean isAccountNonExpired() {
			return Boolean.TRUE;
		}
		
		@Override
		public String getUsername() {
			return SecurityConstants.USERNAME_ANONYMOUS;
		}
		
		@Override
		public String getPassword() {
			return null;
		}
		
		@Override
		public Collection<GrantedAuthority> getAuthorities() {
			return _roles;
		}
		
	};			
		
	private static final UserDetails SYSTEM_USERDETAILS=new UserDetails() {
		private static final long serialVersionUID = -6974753744857317615L;
		private final List<GrantedAuthority> _roles = initRoles();

		@Override
		public boolean isEnabled() {
			return Boolean.TRUE;
		}
		
		private List<GrantedAuthority> initRoles() {
			List<GrantedAuthority> roles = new ArrayList<>(1);
			roles.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_SYSTEM));			
			return roles;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return Boolean.TRUE;
		}
		
		@Override
		public boolean isAccountNonLocked() {
			return Boolean.TRUE;
		}
		
		@Override
		public boolean isAccountNonExpired() {
			return Boolean.TRUE;
		}
		
		@Override
		public String getUsername() {
			return SecurityConstants.USERNAME_SYSTEM;
		}
		
		@Override
		public String getPassword() {
			return null;
		}
		
		@Override
		public Collection<GrantedAuthority> getAuthorities() {
			return _roles;
		}
	};		

	private static Authentication buildAuthentication(UserDetails userdetails, Collection<GrantedAuthority> newAuthorites) {
		return new PreAuthenticatedAuthenticationToken(userdetails, userdetails.getUsername(), newAuthorites);
	}
	
	private static final String ROLE_PREVIOUS_USER = "ROLE_PREVIOUS_USER";

	@Override
	public Authority createNew() {
		return new Authority();
	}

	@Override
	public void initData() {
		//Nothing to do		
	}

}
