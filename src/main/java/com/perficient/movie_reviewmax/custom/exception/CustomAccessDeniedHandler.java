package com.perficient.movie_reviewmax.custom.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	public CustomAccessDeniedHandler() {
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.out.println("Access denied");
		accessDeniedException.printStackTrace();
		System.out.println(response.getHeader("XSRF-TOKEN"));
		// TODO Auto-generated method stub
		
	}
}
