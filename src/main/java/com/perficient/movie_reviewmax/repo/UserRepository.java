package com.perficient.movie_reviewmax.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perficient.movie_reviewmax.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query("FROM User WHERE user_email = :user_email")
	User findByEmail(@Param("user_email") String user_email);
	
	List<User> findAll();
}
