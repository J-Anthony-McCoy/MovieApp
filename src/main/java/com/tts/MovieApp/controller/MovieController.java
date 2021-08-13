package com.tts.MovieApp.controller;

import com.tts.MovieApp.model.Movie;
import com.tts.MovieApp.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @GetMapping(value = "/movies")
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title){
        try{
            List<Movie> movies = new ArrayList<>();

            if(title == null){
                movies.addAll(movieRepo.findAll());
            } else {
                movies.addAll(movieRepo.findByTitleContaining(title));
            }
            if(movies.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id){
        Optional<Movie> movieData = movieRepo.findById(id);

        if(movieData.isPresent()){
            return new ResponseEntity<>(movieData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        try{
            Movie _movie = movieRepo
                    .save(new Movie(movie.getTitle(), movie.getDescription(), false));
            return new ResponseEntity<>(_movie, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

