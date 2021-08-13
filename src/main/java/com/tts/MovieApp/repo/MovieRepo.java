package com.tts.MovieApp.repo;

import com.tts.MovieApp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {
    List<Movie> findByPublished(boolean published);
    List<Movie> findByTitleContaining(String title);
}

// handles how application works. Reference type in Java. Methods are abstract by default.