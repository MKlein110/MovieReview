package com.perficient.movie_reviewmax.service;

import org.springframework.stereotype.Service;

import com.perficient.movie_reviewmax.entities.User;

@Service
public interface UserService {
	User findByEmail(String userEmail);
}
