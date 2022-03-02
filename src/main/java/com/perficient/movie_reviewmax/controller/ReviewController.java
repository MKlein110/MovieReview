package com.perficient.movie_reviewmax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.movie_reviewmax.entities.Review;
import com.perficient.movie_reviewmax.repo.MovieReviewServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewController {
	
	@Autowired
	private MovieReviewServiceImpl serviceRepo;
	
	@GetMapping("/review")
	public Iterable<Review> getAllReviews() {
		return serviceRepo.getAllReviews();
	}
	
	@PostMapping("/review")
	public Review createReview(@RequestBody Review review) {
		return serviceRepo.createReview(review);
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
