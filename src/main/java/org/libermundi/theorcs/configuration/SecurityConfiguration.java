package org.libermundi.theorcs.configuration;
import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.libermundi.theorcs.repositories.impl.PersistentTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final String REMEMBER_ME_KEY = "tAKiB9sM1pNQTUbQysGltvDBXyb6Jp";

	@Autowired
	private RememberMeServices rememberMeServices;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
	        .headers()
	        .cacheControl();
	    
        httpSecurity
        	.authorizeRequests()
	        	.antMatchers("/*","/vendors/**","/js/**","/images/**","/css/**")
	        		.permitAll()
	        		.anyRequest()
	        		.authenticated()
	         .and()
		         .csrf()
		         	.csrfTokenRepository(csrfTokenRepository())
	         .and()
	        	.formLogin()
	        		.successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        		.loginPage("/manager/login")
	        		.failureUrl("/manager/login?error")
	        		.permitAll()
	        .and()
	        	.logout()
	        		.deleteCookies("remember-me")
	        		.logoutSuccessUrl("/manager/index?logout")
	        		.permitAll()
		 	.and()    		 
				.rememberMe()
					.key(REMEMBER_ME_KEY)
		        	.rememberMeServices(rememberMeServices)
					.tokenValiditySeconds(86400);
    }
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public static RememberMeServices persistentTokenBasedRememberMeServices(UserDetailsService userDetailsService, RememberMeTokenRepository rememberMeTokenRepository) {
		return new PersistentTokenBasedRememberMeServices(
				REMEMBER_ME_KEY,
				userDetailsService,
				new PersistentTokenRepositoryImpl(rememberMeTokenRepository)
			);
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}

	@Bean
    protected CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

}