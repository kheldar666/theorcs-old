package org.libermundi.theorcs.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.libermundi.theorcs.domain.SocialMediaService;
import org.libermundi.theorcs.validation.ValidPassword;

/**
 * @author Petri Kainulainen
 */

public class RegistrationForm {

    public static final String FIELD_NAME_EMAIL = "email";

    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String userName;

    
    private String password;

    private String passwordVerification;

    private SocialMediaService signInProvider;

    public RegistrationForm() {

    }

    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return signInProvider != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("userName", userName)
                .append("signInProvider", signInProvider)
                .toString();
    }
}
