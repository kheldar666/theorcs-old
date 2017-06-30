package org.libermundi.theorcs.controllers.manager;
import javax.validation.Valid;

import org.libermundi.theorcs.domain.SocialMediaService;
import org.libermundi.theorcs.domain.User;
import org.libermundi.theorcs.dto.RegistrationForm;
import org.libermundi.theorcs.exceptions.DuplicateEmailException;
import org.libermundi.theorcs.services.SecurityService;
import org.libermundi.theorcs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
 
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	public static final String VIEW_NAME_LOGIN_FORM = "manager/login";
	
	public static final String MODEL_NAME_REGISTRATION_DTO = "user";
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	
    @RequestMapping(value = "/manager/login", method = RequestMethod.GET)
    String index(WebRequest request, Model model) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Rendering the Login/registration page");
    	}
    	
    	Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        RegistrationForm registration = createRegistrationDTO(connection);
        
        if(logger.isDebugEnabled()) {
     		logger.debug("Rendering registration form with information: {}", registration);
     	}

        model.addAttribute(MODEL_NAME_REGISTRATION_DTO, registration);
    	
        return VIEW_NAME_LOGIN_FORM;
    }
    
    @RequestMapping(value ="/user/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
                                      BindingResult result,
                                      WebRequest request) throws DuplicateEmailException {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Registering user account with information: {}", userAccountData);
    	}
        
    	if (result.hasErrors()) {
    		if(logger.isDebugEnabled()) {
    			logger.debug("Validation errors found. Rendering form view.");
    		}
            return VIEW_NAME_LOGIN_FORM;
        }

    	if(logger.isDebugEnabled()) {
    		logger.debug("No validation errors found. Continuing registration process.");
    	}

        User registered = createUserAccount(userAccountData, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            logger.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_LOGIN_FORM;
        }

        if(logger.isDebugEnabled()) {
        	logger.debug("Registered user account with information: {}", registered);
        }

        //Logs the user in.
        securityService.authenticate(registered);
        
        if(logger.isDebugEnabled()) {
        	logger.debug("User {} has been signed in", registered);
        }
        //If the user is signing in by using a social provider, this method call stores
        //the connection to the UserConnection table. Otherwise, this method does not
        //do anything.
        providerSignInUtils.doPostSignUp(registered.getEmail(), request);

        return "redirect:/manager/index";
    }
    
    private RegistrationForm createRegistrationDTO(Connection<?> connection) {
        RegistrationForm dto = new RegistrationForm();

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            dto.setEmail(socialMediaProfile.getEmail());
            dto.setFirstName(socialMediaProfile.getFirstName());
            dto.setLastName(socialMediaProfile.getLastName());

            ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
        }

        return dto;
    }

    private User createUserAccount(RegistrationForm userAccountData, BindingResult result) {
        logger.debug("Creating user account with information: {}", userAccountData);
        User registered = userService.createNew();

        	registered.setEmail(userAccountData.getEmail());
        	//registered.setUsername(userAccountData.);
        
        try {
        	registered = userService.save(registered);
        }
        catch (Exception ex) {
            logger.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationForm.FIELD_NAME_EMAIL,
                    userAccountData.getEmail(),
                    "error.email.exists",
                    result);
        }

        return registered;
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue,  String errorCode, BindingResult result) {
        logger.debug(
                "Adding field error object's: {} field: {} with error code: {}",
                objectName,
                fieldName,
                errorCode
        );
        FieldError error = new FieldError(
                objectName,
                fieldName,
                fieldValue,
                false,
                new String[]{errorCode},
                new Object[]{},
                errorCode
        );

        result.addError(error);
        logger.debug("Added field error: {} to binding result: {}", error, result);
    }
}