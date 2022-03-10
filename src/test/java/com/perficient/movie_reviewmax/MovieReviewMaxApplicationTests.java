package com.perficient.movie_reviewmax;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.entities.Review;
import com.perficient.movie_reviewmax.repo.MovieRepository;
import com.perficient.movie_reviewmax.service.MovieReviewServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class MovieReviewMaxApplicationTests {
	
	@Mock
	MovieRepository mockMovieRepo;
	
	@InjectMocks 
	private MovieReviewServiceImpl serviceRepo;

	@Test
	void getMovieTitle() {
		Movie testMovie = new Movie("Prisoners", "Denis Villeneuve");
		assertEquals("Prisoners", testMovie.getTitle());
	}
	
	@Test
	void testGetMovie() {
		Movie receivedMovie = new Movie();
		receivedMovie.setTitle("Killing of a Sacred Deer");
		
		assertNotNull(receivedMovie);
		assertEquals("Killing of a Sacred Deer", receivedMovie.getTitle());
	}
	
	@Test 
	void setMovieDirector() {
		Movie testMovie = new Movie();
		testMovie.setDirector("Bill");
		assertEquals("Bill", testMovie.getDirector());
	}
	
	@Test
	void testCreateMockMovie() {
		Movie mockMovie = mock(Movie.class);
		when(mockMovie.getTitle()).thenReturn("Batman");
		assertEquals("Batman", mockMovie.getTitle());
	}
	
	@Test
	void testGetReviewWriteUp() {
		Review testReview = new Review();
		testReview.setWriteUp("Good");
		assertEquals("Good", testReview.getWriteUp());
	}

	@Test
	void testGetAllMovies() {
		Movie movieOne = new Movie();
		Movie movieTwo = new Movie();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movieOne);
		movieList.add(movieTwo);
		System.out.println(movieList.size());
		MovieRepository mockMovieRepo = mock(MovieRepository.class);
		when(mockMovieRepo.findAll()).thenReturn(movieList);
		List<Movie> results = serviceRepo.getAllMovies();
		System.out.println("Results: " + results);
		System.out.println(serviceRepo.getAllMovies().size());
		assertEquals(2, serviceRepo.getAllMovies().size());
	}
	
	@Test
	void testCalculateAverageRating() {
		Review mockReviewOne = mock(Review.class);
		Review mockReviewTwo = mock(Review.class);
		
		List<Review> reviewList = new ArrayList<>();
		reviewList.add(mockReviewTwo);
		reviewList.add(mockReviewOne);
		
		when(mockReviewOne.getUserRating()).thenReturn((long) 8);
		when(mockReviewTwo.getUserRating()).thenReturn((long) 10);
		
		Movie testMovie = new Movie();
		testMovie.setReviews(reviewList);
		testMovie.calculateAvgRating();
		assertEquals(9, testMovie.getAverageRating());
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

	
	@Test
	void testDeleteMovie() {
		RestTemplate restTemplate = new RestTemplate();
		Movie movieToDelete = restTemplate.getForObject("http://localhost:8080/moviereview/movie/4", Movie.class);
		long id = movieToDelete.getId();
		restTemplate.delete("http://localhost:8080/moviereview/movie/" + id);
	}
	*/

}
