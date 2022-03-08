package com.perficient.movie_reviewmax.entities;

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
@Table(name = "user_", schema = "movie_db")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_email")
	private String email;
	@Column(name = "user_name")
	private String name;
	@Column(name = "user_password")
	private String password;
	
	//private List<String> favoriteGenres;
	
	@OneToMany
	@JoinColumn(name = "id_movie")
	private List<Movie> favoriteMovies;

	public User() {
		
	}
	
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public List<String> getFavoriteGenres() {
//		return favoriteGenres;
//	}
//
//	public void setFavoriteGenres(List<String> favoriteGenres) {
//		this.favoriteGenres = favoriteGenres;
//	}

	public List<Movie> getFavoriteMovies() {
		return favoriteMovies;
	}

	public void setFavoriteMovies(List<Movie> favoriteMovies) {
		this.favoriteMovies = favoriteMovies;
	}

}
