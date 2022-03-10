package com.perficient.movie_reviewmax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.perficient.movie_reviewmax.entities.User;
import com.perficient.movie_reviewmax.repo.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User loadUserByUsername(String userEmail) {
		
		System.out.println("finding by email: " + userEmail);
		User user = userRepo.findByEmail(userEmail);
		if (user == null) {
			System.out.println("Error");
			throw new UsernameNotFoundException(userEmail);
		}
		return user;
	}
}
