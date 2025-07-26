package com.example.movies.controller;


import com.example.movies.model.Movies;
import com.example.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public List<Movies> getAllMovies() {
        return moviesService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movies getMovieById(@PathVariable Long id) {
        Optional<Movies> movie = moviesService.getMovieById(id);
        return movie.orElse(null);
    }

    @PostMapping
    public Movies createMovie(@RequestBody Movies movie, Principal principal) {
        return moviesService.createMovie(movie, principal.getName());
    }


    @PutMapping("/{id}")
    public Movies updateMovie(@PathVariable Long id, @RequestBody Movies updatedMovie) {
        return moviesService.updateMovie(id, updatedMovie).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        boolean deleted = moviesService.deleteMovie(id);
        return deleted ? "Film Silindi" : "Filmi Silemedin";
    }
}
