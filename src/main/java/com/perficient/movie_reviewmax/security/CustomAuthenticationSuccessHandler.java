package com.perficient.movie_reviewmax.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

		// user object in spring made for OAuth2
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
//		Enumeration<String> headers = request.getHeaderNames();
//		while (headers.hasMoreElements()) {
//			System.out.println(request.getHeader(headers.nextElement()));
//		}
		// Map to store user attributes
		Map attributes = oidcUser.getAttributes();
		String email = (String) attributes.get("email");
		User user = userRepository.findByEmail(email);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) securityContext.getAuthentication();
		OAuth2AuthorizedClient client = clientService
				.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

		String accessToken = client.getAccessToken().getTokenValue();
		
//        String token = JwtTokenUtil.generateAccessToken(user);

		// get redirect url from user token
		String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl).queryParam("auth_token", accessToken)
				.build().toUriString();

		// redirect to saved url

		getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
		Collection<String> headers = response.getHeaders(redirectionUrl);
		System.out.println(headers.size());
		for (String header: headers) {
			System.out.println(header + ": " + response.getHeader(header));
		}
		logger.info("token is" + accessToken);
	}

}