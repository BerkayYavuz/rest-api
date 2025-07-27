package com.example.movies.service;


import com.example.movies.dto.MovieResponseDTO;
import com.example.movies.model.Movies;
import com.example.movies.model.User;
import com.example.movies.repository.MoviesRepository;
import com.example.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


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

        writeMovieToJsonFile(saved);

        return new MovieResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDirector(),
                saved.getYear(),
                saved.getCreatedBy().getUsername()
        );
    }




    public Optional<MovieResponseDTO> updateMovie(Long id, Movies updatedMovie, String username) {
        return moviesRepository.findById(id).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setDirector(updatedMovie.getDirector());
            movie.setYear(updatedMovie.getYear());

            Movies saved = moviesRepository.save(movie);
            return new MovieResponseDTO(
                    saved.getId(),
                    saved.getTitle(),
                    saved.getDirector(),
                    saved.getYear(),
                    saved.getCreatedBy().getUsername()
            );
        });
    }


    public boolean deleteMovie(Long id) {
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<MovieResponseDTO> searchMovies(String title, String year, String director, String createdBy) {
        List<Movies> all = moviesRepository.findAll();

        return all.stream()
                .filter(m -> title == null || m.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(m -> year == null || m.getYear().equals(year))
                .filter(m -> director == null || m.getDirector().toLowerCase().contains(director.toLowerCase()))
                .filter(m -> createdBy == null || m.getCreatedBy().getUsername().equalsIgnoreCase(createdBy))
                .map(m -> new MovieResponseDTO(
                        m.getId(),
                        m.getTitle(),
                        m.getDirector(),
                        m.getYear(),
                        m.getCreatedBy().getUsername()
                ))
                .toList();
    }


    private void writeMovieToJsonFile(Movies movie) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("movies.json");

        try (FileWriter writer = new FileWriter(file, true)) {
            String json = mapper.writeValueAsString(movie);
            writer.write(json + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
