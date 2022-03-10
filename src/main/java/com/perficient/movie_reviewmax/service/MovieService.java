package com.perficient.movie_reviewmax.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.perficient.movie_reviewmax.entities.Movie;

@Service
public interface MovieService {
	List<Movie> searchForMovies(String entry);
	Movie getById(long id);
	List<Movie> orderMovieByRating();
}
