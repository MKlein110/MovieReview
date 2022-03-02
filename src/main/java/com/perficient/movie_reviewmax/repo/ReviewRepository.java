package com.perficient.movie_reviewmax.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.perficient.movie_reviewmax.entities.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}