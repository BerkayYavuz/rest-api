package com.example.movies.service;


import com.example.movies.dto.MovieResponseDTO;
import com.example.movies.model.Movies;
import com.example.movies.model.User;
import com.example.movies.repository.MoviesRepository;
import com.example.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    private final UserRepository userRepository;
    private final MoviesRepository moviesRepository;

    @Autowired
    public MoviesService(UserRepository userRepository, MoviesRepository moviesRepository) {
        this.userRepository = userRepository;
        this.moviesRepository = moviesRepository;
    }

    public List <Movies> getAllMovies() {
        return moviesRepository.findAll();
    }

    public Optional<Movies> getMovieById(Long id) {
        return moviesRepository.findById(id);
    }

    public MovieResponseDTO createMovie(Movies movie, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        movie.setCreatedBy(user);
        Movies saved = moviesRepository.save(movie);

        return new MovieResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDirector(),
                saved.getYear(),
                saved.getCreatedBy().getUsername()
        );
    }



    public Optional<Movies> updateMovie(Long id, Movies updatedMovie) {
        return moviesRepository.findById(id)
                .map(movies -> {
                    movies.setTitle(updatedMovie.getTitle());
                    movies.setDirector(updatedMovie.getDirector());
                    return moviesRepository.save(movies);
                });

    }

    public boolean deleteMovie(Long id) {
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
