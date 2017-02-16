package org.libermundi.theorcs.configuration;
import java.util.UUID;

import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.libermundi.theorcs.repositories.impl.PersistentTokenRepositoryImpl;
import org.libermundi.theorcs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private RememberMeTokenRepository rememberMeTokenRepository;
	
	@Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/*","/vendors/**","/js/**","/images/**","/css/**","/h2-console/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        	.formLogin()
	        		.successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        		.loginPage("/manager/login")
	        		.failureUrl("/manager/login?error")
	        		.permitAll()
	        .and()
	        	.logout()
	        		.logoutSuccessUrl("/?logout")
	        		.permitAll();

        httpSecurity.rememberMe()
        	.tokenRepository(persistentTokenRepository(rememberMeTokenRepository))
        	.tokenValiditySeconds(1209600);
       
        //TODO: Remove when going to Production !
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public static PersistentTokenRepository persistentTokenRepository(RememberMeTokenRepository rememberMeTokenRepository) {
		return new PersistentTokenRepositoryImpl(rememberMeTokenRepository);
	}
	
	@Bean
	public static RememberMeServices persistentTokenBasedRememberMeServices(SecurityService userDetailsService, PersistentTokenRepository persistentTokenRepository ) {
		return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService, persistentTokenRepository);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
	    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
	    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
	    return daoAuthenticationProvider;
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
	
}