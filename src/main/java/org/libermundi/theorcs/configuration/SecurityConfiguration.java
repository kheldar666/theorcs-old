package org.libermundi.theorcs.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
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
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and().withUser("user").password("user").roles("USER");
    }
}