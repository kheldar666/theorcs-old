package org.libermundi.theorcs.services.impl;

import java.util.UUID;

import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {
 
    @Autowired
    private UserService userService;
 
    @Override
    public String execute(Connection<?> connection) {
        
    	Facebook facebook = (Facebook)connection.getApi();
    	String [] fields = { "id", "email",  "first_name", "last_name" };
    	org.springframework.social.facebook.api.User fbUser = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
    	
    	User user = userService.createNew();
        
        user.setUsername(fbUser.getId());
        user.setPassword(UUID.randomUUID().toString());
        user.setFirstName(fbUser.getFirstName());
        user.setLastName(fbUser.getLastName());
        user.setNickName(user.getFullName());
        user.setEmail(fbUser.getEmail());
        user.setDeleted(Boolean.FALSE);
        user.setEnabled(Boolean.TRUE);
        user.setAccountNonLocked(Boolean.TRUE);
        user.setAccountNonExpired(Boolean.TRUE);
        user.setCredentialsNonExpired(Boolean.TRUE);

        userService.save(user);
        
        return user.getUsername();
    }
}