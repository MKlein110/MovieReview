package com.perficient.movie_reviewmax.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.perficient.movie_reviewmax.entities.User;
import com.perficient.movie_reviewmax.repo.UserRepository;
import com.perficient.movie_reviewmax.service.CustomOidcUserService;

/*
 * Used to handle successful user authentication. 
 * 
 * Control navigation to pages and destination once a user has logged
 * in using redirects. 
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OAuth2AuthorizedClientService clientService;
	
	// home page: destination 3000
	private String homeUrl = "http://localhost:3000/";

	// logger definition
	Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	/*
	 * Method: onAuthenticationSuccess()
	 * 
	 * Parameters: request - request information for Http servlets from client
	 * response - response information for Http servlets to access headers and
	 * cookies authentication - token for auth request for an authenticated
	 * principal.
	 * 
	 * Return: void
	 * 
	 * Description: Handle subsequent actions and behaviors once a user has logged
	 * in. Save the user attributes and form token to create a redirect.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// return if a response has been given already; no further action needed
		if (response.isCommitted()) {
			logger.info("Response already exists");
			return;
		}
		logger.info("Attempting google authentication");
		String xsrfToken = parseToken(response);
		// get redirect url from user token
		String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl).queryParam("auth_token", xsrfToken)
				.build().toUriString();
		// redirect to saved url
		getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
		logger.info("Authentication successful, token is" + xsrfToken);
	}
	
	/*
	 * Method: parseToken(HttpServletResponse response)
	 * 
	 * Parameters: Takes in response received from Google after being successfully authenticated
	 * 
	 * Return: Returns the XSRF-TOKEN received from the Google response
	 * 
	 * Description: Goes through the response headers and parses out the XSRF TOKEN from the Set-Cookie header received from the 
	 * Google authentication response.
	 *
	 */
	private String parseToken(HttpServletResponse response) {
		Collection<String> setCookieHeaders = response.getHeaders("Set-Cookie");
		String xsrfToken = "";
		for (String values: setCookieHeaders) {
			if (values.contains("XSRF-TOKEN")) {
				xsrfToken = values;
				int startParseIndex = values.indexOf("=");
				int endParseIndex = values.indexOf(";");
				xsrfToken = xsrfToken.substring(startParseIndex+1, endParseIndex);
			}
		}
		return xsrfToken;
	}

}