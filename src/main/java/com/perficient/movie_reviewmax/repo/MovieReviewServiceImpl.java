package com.perficient.movie_reviewmax.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.entities.Review;

@Service
public class MovieReviewServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Transactional(readOnly = true)
	@Cacheable("movie-cache")
	public Iterable<Movie> getAllMovies() {
		return movieRepo.findAll();
	}
	
	@Override
	@Cacheable("movie-cache")
	public List<Movie> searchForMovies(String entry) {
		return movieRepo.searchForMovies(entry);
	}
	
	@Override
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Movie getById(long id) {
		Movie movie = movieRepo.getById(id);
		movie.calculateAvgRating();
		return movieRepo.save(movie);
	}
	
	@Transactional(readOnly = false)
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteMovieById(long id) {
		movieRepo.deleteById(id);
	}
	
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Movie createMovie(Movie movie) {
		return movieRepo.save(movie);
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteAllReviewsForMovie(Movie movie) {
		Iterable<Review> reviews = movie.getReviews();
		for(Review review: reviews) {
			reviewRepo.deleteById(review.getId());
		}
	}
	
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteAllMovies() {
		reviewRepo.deleteAll();
		movieRepo.deleteAll();
	}
	
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Review createReview(Review review) {
		return reviewRepo.save(review);
	}
	
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Review updateReview(Review review) {
		return reviewRepo.save(review);
	}
	

	@Cacheable("movie-cache")
	public Iterable<Review> getAllReviews() {
		return reviewRepo.findAll();
	}
	
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteReviewById(long id) {
		reviewRepo.deleteById(id);
	}

	@Override
	@Cacheable(value = "ordered-movie-cache")
	public List<Movie> orderMovieByRating() {
		return movieRepo.orderMovieByRating();
	}

}
