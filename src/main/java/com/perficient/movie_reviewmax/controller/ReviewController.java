package com.perficient.movie_reviewmax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.movie_reviewmax.custom.exception.ControllerException;
import com.perficient.movie_reviewmax.entities.Review;
import com.perficient.movie_reviewmax.service.MovieReviewServiceImpl;

//@CrossOrigin(origins = {"http://localhost:3000", "localhost"}, methods = { RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PUT}, allowedHeaders = "content-type")

@RestController
public class ReviewController {
	
	@Autowired
	private MovieReviewServiceImpl serviceRepo;
	

	
	@GetMapping("/review")
	public Iterable<Review> getAllReviews() {
		return serviceRepo.getAllReviews();
	}
	
	@PostMapping("/review")
	public ResponseEntity<?> createReview(@RequestBody Review review) {
		try {
			return new ResponseEntity<Review>(serviceRepo.createReview(review), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("700", "Something went wrong in the controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/review")
	public Review updateReview(@RequestBody Review review) {
		return serviceRepo.updateReview(review);
	}
	
	@DeleteMapping("/review/{id}")
	public void deleteReviewById(@PathVariable("id") long id) {
		serviceRepo.deleteReviewById(id);
	}
	
}
