package com.example.movies.controller;


import com.example.movies.dto.MovieResponseDTO;
import com.example.movies.model.Movies;
import com.example.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public List<MovieResponseDTO> getAllMovies() {
        return moviesService.getAllMovies()
                .stream()
                .map(movie -> new MovieResponseDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDirector(),
                        movie.getYear(),
                        movie.getCreatedBy().getUsername()
                ))
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public Movies getMovieById(@PathVariable Long id) {
        Optional<Movies> movie = moviesService.getMovieById(id);
        return movie.orElse(null);
    }

    @PostMapping
    public MovieResponseDTO createMovie(@RequestBody Movies movie, Principal principal) {
        return moviesService.createMovie(movie, principal.getName());
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movies updatedMovie, Principal principal) {
        return moviesService.updateMovie(id, updatedMovie, principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        boolean deleted = moviesService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.ok(" Film silindi.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Film bulunamadı.");
        }
    }

    @GetMapping("/search")
    public List<MovieResponseDTO> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String createdBy
    ) {
        return moviesService.searchMovies(title, year, director, createdBy);
    }


}
