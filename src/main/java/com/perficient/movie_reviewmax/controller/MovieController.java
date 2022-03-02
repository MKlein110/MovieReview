package com.perficient.movie_reviewmax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.repo.MovieReviewServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MovieController {
	
	@Autowired
	private MovieReviewServiceImpl serviceRepo;
	
	@GetMapping("/movies")
	public Iterable<Movie> getMovies() {
		return serviceRepo.getAllMovies();
	}
	
	@GetMapping("/search")
	public List<Movie> searchForMovies(@RequestParam String entry) {
		return serviceRepo.searchForMovies(entry);
	}
	
	@GetMapping("/movie-by-rating")
	public List<Movie> orderMovieByRating() {
		return serviceRepo.orderMovieByRating();
	}
	@GetMapping("/movie/{id}")
	public Movie getMovieById(@PathVariable("id") long id) {
		return serviceRepo.getById(id);
	}
	
	@PostMapping("/movie")
	public Movie createMovie(@RequestBody Movie movie) {
		return serviceRepo.createMovie(movie);
	}
	
	@DeleteMapping("/movie/{id}")
	public void deleteMovieById(@PathVariable("id") long id) {
		serviceRepo.deleteMovieById(id);
	}
	
	@DeleteMapping("/delete-all-movies")
	public void deleteAllMovies() {
		serviceRepo.deleteAllMovies();
	}
}