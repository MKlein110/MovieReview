package com.perficient.movie_reviewmax.filter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JWTAuthenticationFilter extends AbstractAuthenticationToken {

	public JWTAuthenticationFilter(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Autowired 
	private AuthenticationManager authenticationManager;

	@Override
	public Object getCredentials() {
		
		return null;
	}

	@Override
	public Object getPrincipal() {
		
		return null;
	}
}
