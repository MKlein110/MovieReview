package com.perficient.movie_reviewmax.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.perficient.movie_reviewmax.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findAll();
}
