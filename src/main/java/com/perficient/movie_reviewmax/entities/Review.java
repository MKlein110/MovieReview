package com.perficient.movie_reviewmax.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "movie_reviews", schema = "movie_db")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_review")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "review_movie")
	private String writeUp;
	@Column(name = "author_review")
	private String author;
	@Column(name = "movie_id")
	private int movieId;

	@Column(name = "user_rating")
	private long userRating;
	
	public Review() {
		
	}
	
	public Review(long id, String writeUp, String author, int movieId, long userRating) {
		super();
		this.id = id;
		this.writeUp = writeUp;
		this.author = author;
		this.movieId = movieId;
		this.userRating = userRating;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWriteUp() {
		return writeUp;
	}

	public void setWriteUp(String writeUp) {
		this.writeUp = writeUp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public long getUserRating() {
		return userRating;
	}

	public void setUserRating(long userRating) {
		this.userRating = userRating;
	}

	
	
}