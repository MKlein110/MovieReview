package com.perficient.movie_reviewmax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.repo.MovieReviewServiceImpl;
import java.util.List;

@SpringBootTest
class MovieReviewMaxApplicationTests {

	@Autowired 
	private MovieReviewServiceImpl serviceRepo;
	
	
	@Test
	void testGetMovie() {
		RestTemplate restTemplate = new RestTemplate();
		Movie receivedMovie = restTemplate.getForObject("http://localhost:8080/moviereview/movie/3", Movie.class);
		assertNotNull(receivedMovie);
		assertEquals("Killing of a Sacred Deer", receivedMovie.getTitle());
	}

	/*
	@Test
	void testCreateMovie() {
		RestTemplate restTemplate = new RestTemplate();
		Movie movie = new Movie();
		movie.setTitle("James Bond: No Time to Die");
		movie.setDirector("Cary Joji Fukunaga");
		Movie newMovie = restTemplate.postForObject("http://localhost:8080/moviereview/movie", movie, Movie.class);
		assertNotNull(newMovie);
		assertNotNull(newMovie.getId());
		assertEquals("James Bond: No Time to Die", newMovie.getTitle());
	}
	*/
	@Test
	void testDeleteMovie() {
		RestTemplate restTemplate = new RestTemplate();
		Movie movieToDelete = restTemplate.getForObject("http://localhost:8080/moviereview/movie/3", Movie.class);
		long id = movieToDelete.getId();
		System.out.println("http://localhost:8080/moviereview/movie/" + id);
		restTemplate.delete("http://localhost:8080/moviereview/movie/" + id);
	}
	/*
	@Test
	void testAverageMovieRating() {
		RestTemplate restTemplate = new RestTemplate();
		Movie receivedMovie = restTemplate.getForObject("http://localhost:8080/moviereview/movie/Prisoners", Movie.class);
		receivedMovie.setAverageRating(receivedMovie.calculateAvgRating());
		System.out.println(receivedMovie.getAverageRating());
		assertEquals(8.5, receivedMovie.getAverageRating());
	}
	/*
	@Test
	void testAverageUpdate() {
		RestTemplate restTemplate = new RestTemplate();
		Movie receivedMovie = restTemplate.getForObject("http://localhost:8080/moviereview/movie/Prisoners", Movie.class);
		
	}
	*/
}
