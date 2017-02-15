package org.libermundi.theorcs.configuration;
import java.util.UUID;

import org.libermundi.theorcs.repositories.RememberMeTokenRepository;
import org.libermundi.theorcs.repositories.impl.PersistentTokenRepositoryImpl;
import org.libermundi.theorcs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/*","/vendors/**","/js/**","/images/**","/css/**","/h2-console/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin().loginPage("/manager/login").permitAll()
	        .and()
	        .logout().permitAll();

        
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
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and().withUser("user").password("user").roles("USER");
    }
}