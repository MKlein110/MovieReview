package com.perficient.movie_reviewmax.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.perficient.movie_reviewmax.entities.GoogleOAuth2UserInfo;
import com.perficient.movie_reviewmax.entities.User;
import com.perficient.movie_reviewmax.entities.UserType;
import com.perficient.movie_reviewmax.repo.UserRepository;

import java.util.Map;

/*
 * Load and update/add user based off of user info.
 * Update or add user to database once logged in.
 */
@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;
    
    //logger definition for CustomOidcUserService class
    Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    /*
     * Method: 
     * 		loadUser()
     * 
     * Parameters: 
     * 		userRequest - request to user info end point
     * 
     * Return: 
     * 		oidcUser - user object with set attributes  
     * 
     * Description: 
     * 		 Create oidcUser object and set name, id, and email. 
     * 		 Add or update user to the database and return user 
     * 		 object. 
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    	
    	logger.info("Check database if a user exists");
    	
    	//oidcUser object - set attributes
        OidcUser oidcUser = super.loadUser(userRequest);
        
        //map of attributes for a user 
        Map<String, Object> attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setName((String) attributes.get("name"));
        
        logger.info("Extracted user info from Google login");
        
        //call helper method to add/update user
        updateUser(userInfo);
        logger.info("user name" + userInfo.getName());
        return oidcUser;
    }

    /*
     * Method: 
     * 		updateUser()
     * 
     * Parameters: 
     * 		userInfo - contains properties of a user 
     * 
     * Return: 
     * 		void  
     * 
     * Description: 
     * 		Add the user, or update if does not exist, 
     * 		to the database.  
     */
    private void updateUser(GoogleOAuth2UserInfo userInfo) {
    	
    	logger.info("Check for possible existing user");
    	
    	//check if user exists
        User user = userRepository.findByEmail(userInfo.getEmail());
        
        //create new user 
        if(user == null) {
        	logger.info("Adding new user to database");
            user = new User();
        }
        
        logger.info("Setting user attributes");
        
        //set new or updated user properties
        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setUserType(UserType.google);
        
        //save user to database
		userRepository.save(user);
    }
}

