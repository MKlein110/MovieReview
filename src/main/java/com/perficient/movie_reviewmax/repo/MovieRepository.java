package com.perficient.movie_reviewmax.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perficient.movie_reviewmax.entities.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>{
	@Query("FROM Movie WHERE title_movie LIKE CONCAT (:entry,'%') OR director_movie LIKE CONCAT (:entry,'%')")
	List<Movie> searchForMovies(@Param("entry") String title);
	
	@Query("FROM Movie WHERE id_movie = :id")
	Movie getById(@Param("id") long id);
	
	@Query("FROM Movie ORDER BY avg_rating DESC, director_movie ASC")
	List<Movie> orderMovieByRating();
	
	List<Movie> findAll();
}