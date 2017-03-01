package org.libermundi.theorcs.configuration;
import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.libermundi.theorcs.repositories.impl.PersistentTokenRepositoryImpl;
import org.libermundi.theorcs.services.impl.SocialUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private static final String REMEMBER_ME_KEY = "tAKiB9sM1pNQTUbQysGltvDBXyb6Jp";
	
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
	        	.antMatchers("/*","/connect/**")
	        		.permitAll()
	        	.anyRequest()
	        		.authenticated()
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
	        		.logoutSuccessUrl("/manager/index?logout")
	        		.permitAll()
	        .and()    		 
        		.rememberMe()
					.key(REMEMBER_ME_KEY)
		        	.rememberMeServices(rememberMeServices)
					.tokenValiditySeconds(86400)
			.and()
				.apply(new SpringSocialConfigurer());
        
        if (!Strings.isNullOrEmpty(env) && env.equals("dev")) {
        	logger.warn("****************************************************");
        	logger.warn("Application in Development Mode. Enabling H2Console.");
        	logger.warn("****************************************************");
        	httpSecurity
        		.csrf()
        			.disable();
        	
        	httpSecurity
        		.authorizeRequests()
		        	.antMatchers("/h2-console/**")
		        		.permitAll();
        	
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
	public static RememberMeServices persistentTokenBasedRememberMeServices(UserDetailsService userDetailsService, RememberMeTokenRepository rememberMeTokenRepository) {
		logger.info("Creating RememberMeServices Bean");
		return new PersistentTokenBasedRememberMeServices(
				REMEMBER_ME_KEY,
				userDetailsService,
				new PersistentTokenRepositoryImpl(rememberMeTokenRepository)
			);
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		logger.info("Creating SavedRequestAwareAuthenticationSuccessHandler Bean");
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
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

}