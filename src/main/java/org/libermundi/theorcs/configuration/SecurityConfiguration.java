package org.libermundi.theorcs.configuration;
import javax.sql.DataSource;

import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.libermundi.theorcs.repositories.impl.PersistentTokenRepositoryImpl;
import org.libermundi.theorcs.security.impl.DatabaseSocialConfigurer;
import org.libermundi.theorcs.services.impl.SocialUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.google.common.base.Strings;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value("${theorcs.security.rememberme.key}")
	private String rememberMeKey;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Autowired
	private RememberMeServices rememberMeServices;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
	   	logger.info("Configuring AuthenticationManagerBuilder");
	    auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }
	
    @Override
	public void configure(WebSecurity web) throws Exception {
    	logger.info("Configuring WebSecurity");
        web
			.ignoring()
				.antMatchers("/vendors/**","/js/**","/images/**","/css/**");
	}

	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.info("Configuring HttpSecurity");
        httpSecurity
	        .headers()
	        .cacheControl();
	    
        httpSecurity
        	.authorizeRequests()
        		.expressionHandler(securityExpressionHandler())
	        	.antMatchers("/*","/connect/**","/h2-console/**")
	        		.permitAll()
	        	.anyRequest()
	        		.hasRole("USER")
	         .and()
	        	.formLogin()
	        		.successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        		.loginPage("/manager/login")
	        		.failureUrl("/manager/login?error")
	        		.permitAll()
	        .and()
	        	.logout()
	        		.logoutUrl("/manager/logout")
	        		.deleteCookies("remember-me")
	        		.deleteCookies("JSESSIONID")
	        		.invalidateHttpSession(true)
	  //      		.logoutSuccessUrl("/manager/index?logout")
	        		.permitAll()
	        .and()    		 
        		.rememberMe()
					.key(rememberMeKey)
		        	.rememberMeServices(rememberMeServices)
					.tokenValiditySeconds(86400)
			.and()
				.apply(
					new SpringSocialConfigurer()
					.signupUrl("/manager/login")
					.postLoginUrl("/manager/index")
				);
        
        if (!Strings.isNullOrEmpty(env) && env.equals("dev")) {
        	logger.warn("****************************************************");
        	logger.warn("Application in Development Mode. Enabling H2Console.");
        	logger.warn("****************************************************");
        	httpSecurity
        		.csrf()
        			.disable();
        	
        	httpSecurity
        		.headers()
        			.frameOptions().disable();
        } else {
        	httpSecurity
	        	.csrf()
	        		.csrfTokenRepository(csrfTokenRepository());
        }

    }
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		logger.info("Creating PasswordEncoder Bean");
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public RememberMeServices persistentTokenBasedRememberMeServices(UserDetailsService userDetailsService, RememberMeTokenRepository rememberMeTokenRepository) {
		logger.info("Creating RememberMeServices Bean with Key : " + rememberMeKey);
		return new PersistentTokenBasedRememberMeServices(
				rememberMeKey,
				userDetailsService,
				new PersistentTokenRepositoryImpl(rememberMeTokenRepository)
			);
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		logger.info("Creating SavedRequestAwareAuthenticationSuccessHandler Bean");
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		auth.setDefaultTargetUrl("/manager/index");
		return auth;
	}

	@Bean
    protected CsrfTokenRepository csrfTokenRepository() {
		logger.info("Creating CsrfTokenRepository Bean");
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
	
	@Bean
    public SocialUserDetailsService socialUserDetailsService() {
		logger.info("Creating SocialUserDetailsService Bean");
        return new SocialUserDetailsServiceImpl(userDetailsService());
    }

	@Bean
	public DatabaseSocialConfigurer databaseSocialConfigurer(DataSource dataSource) {
		return new DatabaseSocialConfigurer(dataSource);
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
	    roleHierarchy.setHierarchy("ROLE_ROOT > ROLE_ADMIN ROLE_ADMIN > ROLE_USER ROLE_USER > ROLE_ANONYMOUS");
	    return roleHierarchy;
	}
	
	private SecurityExpressionHandler<FilterInvocation> securityExpressionHandler() {
	    DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
	    defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
	    return defaultWebSecurityExpressionHandler;
	}
}