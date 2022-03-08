package com.perficient.movie_reviewmax.entities;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movie", schema = "movie_db")
public class Movie implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_movie")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "title_movie")
	private String title;
	@Column(name = "director_movie")
	private String director;
	@OneToMany
	@JoinColumn(name = "movie_id")
	private List<Review> reviews;
	@Column(name = "avg_rating")
	private double averageRating;
	@Column(name = "genre_movie")
	private String genre;
	@Column(name = "year_movie")
	private int releaseDate;
	@Column(name = "writer_movie")
	private String writer;
	
	public Movie() {
		
	}
	
	public Movie(String title, String director) {
		this.title = title;
		this.director = director;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	
	public void calculateAvgRating() {
		double averageRating = 0;
		for(Review review: this.reviews) {
			averageRating += review.getUserRating();
		}
		averageRating = Math.round((averageRating / this.reviews.size() * 100)) / 100.0;
		this.setAverageRating(averageRating);
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}