package com.perficient.movie_reviewmax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.movie_reviewmax.entities.User;
import com.perficient.movie_reviewmax.repo.UserRepository;
import com.perficient.movie_reviewmax.service.UserServiceImpl;

@RestController
public class UserController {
	@Autowired
	private UserServiceImpl userRepo;
	
	@GetMapping("/users")
	public User getUsers(String email) {
		return userRepo.loadUserByUsername(email);
	}
}
