package com.perficient.movie_reviewmax.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfTokenResponseHeaderBindingFilter extends OncePerRequestFilter {
    protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";
    protected static final String RESPONSE_HEADER_NAME = "X-CSRF-HEADER";
    protected static final String RESPONSE_PARAM_NAME = "X-CSRF-PARAM";
    protected static final String RESPONSE_TOKEN_NAME = "X-CSRF-TOKEN";
	private CsrfTokenRepository csrfTokenRepository = new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);


		this.csrfTokenRepository.saveToken(null, request, response);
	    request.setAttribute(HttpServletResponse.class.getName(), response);
		CsrfToken newToken = this.csrfTokenRepository.generateToken(request);
		if (newToken==null) {
			System.out.println("new token null");
		} else {
			System.out.println("Token is: " + newToken.getToken());
		}
		this.csrfTokenRepository.saveToken(newToken, request, response);
		request.setAttribute(CsrfToken.class.getName(), newToken);
		request.setAttribute(newToken.getParameterName(), newToken);
		this.logger.debug("Replaced CSRF Token");
		
    
		CsrfToken csrfToken = this.csrfTokenRepository.loadToken(request);
		System.out.println(csrfToken.getToken());
		
		
		if (token != null) {
            response.setHeader(RESPONSE_HEADER_NAME, token.getHeaderName());
            response.setHeader(RESPONSE_PARAM_NAME, token.getParameterName());
            response.setHeader(RESPONSE_TOKEN_NAME , token.getToken());
        }

        System.out.println("Custom filter");
        filterChain.doFilter(request, response);
    }
}
