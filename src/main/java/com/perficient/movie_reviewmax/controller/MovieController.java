package com.perficient.movie_reviewmax.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.movie_reviewmax.custom.exception.BusinessException;
import com.perficient.movie_reviewmax.custom.exception.ControllerException;
import com.perficient.movie_reviewmax.entities.Movie;
import com.perficient.movie_reviewmax.entities.User;
import com.perficient.movie_reviewmax.security.JwtTokenUtil;
import com.perficient.movie_reviewmax.service.MovieReviewServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MovieController {

	@Autowired
	private MovieReviewServiceImpl serviceRepo;

//	@RequestMapping("/error")
//	public String error() {
//		return "error";
//	}
//	
//	public String getErrorPath() {
//		return "/error";
//	}
//	@Autowired
//	private AuthenticationManager authenticationManager;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(MovieReviewServiceImpl.class);

	@GetMapping("/user")
    public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
//		Collection<? extends GrantedAuthority> data = principal.getAuthorities();
//		for (GrantedAuthority auth: data) {
//			System.out.println(auth.getAuthority());
//		}
		return principal;
	}
//		System.out.println("Entering");
//		String name = principal.getAttribute("name");
//		String email = principal.getAttribute("email");
//		System.out.println("Name is printing" + name);
//		System.out.println("Email is printing" + email);
//		try {
//			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, email));
//		
//			System.out.println(principal);
//			System.out.println(authenticate);
//			User user = (User) authenticate.getPrincipal();
//	
//		
//			return (ResponseEntity<?>) ResponseEntity.ok()
//					.header(HttpHeaders.AUTHORIZATION, JwtTokenUtil.generateAccessToken(user));
//    
//		} catch(Exception ce) {
//			System.out.println("fail" + ce);
//			return new ResponseEntity<ControllerException>(HttpStatus.BAD_REQUEST) ;

//		}

//    }

	// @CrossOrigin(origins = "*")
	@GetMapping("/movies")
	public ResponseEntity<?> getMovies() {
		try {
			logger.info("Returning all movies");
			return new ResponseEntity<List<Movie>>(serviceRepo.getAllMovies(), HttpStatus.ACCEPTED);
		} catch (BusinessException e) {
			logger.error("Tried to receive movies, but no movies in database");
			return controllerExceptionHelper(e);
		} catch (Exception e) {
			logger.error("Tried to receive all movies, but something went wrong in the controller");
			ControllerException ce = new ControllerException("601", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search")
	public List<Movie> searchForMovies(@RequestParam String entry) {
		logger.info("Searching for entries");
		return serviceRepo.searchForMovies(entry);
	}

	@GetMapping("/movie-by-rating")
	public List<Movie> orderMovieByRating() {
		logger.info("Ordering movies by Rating");
		return serviceRepo.orderMovieByRating();
	}

	@GetMapping("/movie/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable("id") long id) throws BusinessException {
		try {
			logger.info("finding movie by id: " + id);
			Movie movie = serviceRepo.getById(id);
			return new ResponseEntity<Movie>(movie, HttpStatus.ACCEPTED);
		} catch (BusinessException e) {
			logger.error("finding movie by id: " + id + ". No movie in database");
			return controllerExceptionHelper(e);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("602", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/movie")
	public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
		try {
			return new ResponseEntity<Movie>(serviceRepo.createMovie(movie), HttpStatus.ACCEPTED);
		} catch (BusinessException e) {
			return controllerExceptionHelper(e);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("603", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/movie/{id}")
	public ResponseEntity<?> deleteMovieById(@PathVariable("id") long id) {
		try {
			Movie deletedMovie = serviceRepo.getById(id);
			serviceRepo.deleteMovieById(id);
			return new ResponseEntity<Movie>(deletedMovie, HttpStatus.ACCEPTED);
		} catch (BusinessException e) {
			return controllerExceptionHelper(e);
		} catch (IllegalArgumentException e) {
			ControllerException ce = new ControllerException("604", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("605", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteFilms/{film_ids}")
	public ResponseEntity<List<Movie>> deleteFilms(@PathVariable("film_ids") List<Long> film_ids) throws Exception{
		List<Movie> movie = null;
		logger.info("Deleting multiple movies");
		
		try {
			movie = serviceRepo.deleteFilms(film_ids);
		} catch(Exception e) {
			e.getMessage();
		}
		
		return new ResponseEntity<List<Movie>>(movie, HttpStatus.OK);
	}

	@DeleteMapping("/delete-all-movies")
	public ResponseEntity<?> deleteAllMovies() {

		try {
			List<Movie> deletedMovies = serviceRepo.getAllMovies();
			serviceRepo.deleteAllMovies();
			System.out.println(deletedMovies);
			return new ResponseEntity<List<Movie>>(deletedMovies, HttpStatus.ACCEPTED);
		} catch (BusinessException e) {
			return controllerExceptionHelper(e);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("606", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/access-denied")
	public void getAccessDenied() {
		throw new AccessDeniedException("accessDenied");
	}

	public ResponseEntity<ControllerException> controllerExceptionHelper(BusinessException e) {
		ControllerException ce = new ControllerException("Controller: " + e.getErrorCode(), e.getErrorMessage());
		return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
	}
}