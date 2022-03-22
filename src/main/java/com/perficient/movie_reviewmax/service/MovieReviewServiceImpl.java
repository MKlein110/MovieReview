package com.perficient.movie_reviewmax.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perficient.movie_reviewmax.custom.exception.BusinessException;
import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.entities.Review;
import com.perficient.movie_reviewmax.repo.MovieRepository;
import com.perficient.movie_reviewmax.repo.ReviewRepository;

@Service
public class MovieReviewServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private ReviewRepository reviewRepo;

	private Logger logger = LoggerFactory.getLogger(MovieReviewServiceImpl.class);
	
	@Transactional(readOnly = true)
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public List<Movie> getAllMovies() {
		try {
			List<Movie> movieList = movieRepo.findAll();
			if (movieList.isEmpty()) {
				logger.info("Movie list is empty. Nothing to return");
				throw new BusinessException("501", "MovieList is empty, nothing to return");
			}
			return movieList;
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(),
					"Something went wrong in service layer: " + e.getErrorMessage());
		}

	}

	@Override
	@Cacheable("movie-cache")
	public List<Movie> searchForMovies(String entry) {
		try {
			return movieRepo.searchForMovies(entry);
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(), e.getErrorMessage());
		}
	}

	@Override
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Movie getById(long id) throws BusinessException {
		try {
			Movie movie = movieRepo.getById(id);
			if (movie == null) {
				logger.error("Movie doesn't exist with id: " + id);
				throw new BusinessException("502", "Given movie id is not present in database");
			}
			movie.calculateAvgRating();
			return movieRepo.save(movie);

		} catch (IllegalArgumentException e) {
			throw new BusinessException("503", "given movie is null");
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(),
					"Something went wrong in business layer: " + e.getErrorMessage());
		}
	}

	@Transactional(readOnly = false)
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteMovieById(long id) {
		try {
			Movie deletedMovie = movieRepo.getById(id);
			if (deletedMovie == null) {
				logger.error("Movie doesn't exist with id: " + id + ". Cannot delete.");
				throw new BusinessException("504", "Movie doesn't exist");
			}
			movieRepo.deleteById(id);
			logger.info("Deleted movie: " + deletedMovie.getTitle());
		} catch (IllegalArgumentException e) {
			throw new BusinessException("505", "No parameter passed in");
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(),
					"Something went wrong in the Business layer: " + e.getErrorMessage());
		}
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Movie createMovie(Movie movie) {
		try {
			if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
				logger.error("User entered movie without title or director");
				throw new BusinessException("506", "Missing title or director entry");
			}
			logger.info("Movie with title: " + movie.getTitle() + " successfully added");
			return movieRepo.save(movie);
		} catch (IllegalArgumentException e) {
			throw new BusinessException("507", "Given movie is null " + e.getMessage());
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(), "Something went wrong in the Service layer: " + e.getErrorMessage());
		} catch (Exception e) {
			throw new BusinessException("508", "Something went wrong in the Service layer: " + e.getMessage());
		}
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public void deleteAllMovies() {
		try {
			List<Movie> movieList = movieRepo.findAll();
			if (movieList.isEmpty())
				throw new BusinessException("509", "No movies to delete");
			reviewRepo.deleteAll();
			movieRepo.deleteAll();
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(), "Something went wrong in Business Layer: " + e.getErrorMessage());
		}

	}
	
	@Override
	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public List<Movie> deleteFilms(List<Long> film_ids) {

		//no film ids to delete; return empty list
		if(film_ids.size() == 0) return Collections.emptyList();
		
		//return list 
		List<Movie> deletedFilms = new ArrayList<Movie>();
		
		//delete film ids
		logger.info("number of films to delete: " + film_ids.size());
		for(long id: film_ids) {
			Movie deletedFilm = movieRepo.getById(id);	
			try {
				if(deletedFilm == null) {
					logger.info("failed on " + id);
					throw new BusinessException("509", "movie not found");
				}
				else {
					deletedFilms.add(deletedFilm);
					movieRepo.deleteById(id); 
					logger.info("deleted movie: " + deletedFilm.getTitle());
				}
			} catch (Exception e) {
				throw e;
			}
		}
		return deletedFilms;
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Review createReview(Review review) {
		return reviewRepo.save(review);
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
	public Review updateReview(Review review) {
		return reviewRepo.save(review);
	}

	@CacheEvict(value = { "movie-cache", "ordered-movie-cache" }, allEntries = true)
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
