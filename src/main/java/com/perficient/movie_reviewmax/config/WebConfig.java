package com.perficient.movie_reviewmax.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.perficient.movie_reviewmax.repo.UserRepository;

//@EnableWebSecurity
//@Configuration
//public class WebConfig extends WebSecurityConfigurerAdapter{
//	
//	@Autowired
//	private UserRepository userRepo;
//	
//
//	
//	public WebConfig(UserRepository userRepo) {
//		this.userRepo = userRepo;
//	}
//	
////	@Override
////	protected void configure(AuthenticationManagerBuilder authenticator) throws Exception {
////		System.out.println("Find by email");
////		authenticator.userDetailsService(userEmail -> userRepo
////				.loadByUsername(userEmail));
////	}
//
//
//}