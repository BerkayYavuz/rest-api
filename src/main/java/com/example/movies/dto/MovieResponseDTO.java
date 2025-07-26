package com.example.movies.dto;

public class MovieResponseDTO {
    private Long id;
    private String title;
    private String director;
    private String year;
    private String createdBy;

    public MovieResponseDTO(Long id, String title, String director, String year, String createdBy) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.createdBy = createdBy;
    }

    // getter'lar
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public String getYear() { return year; }
    public String getCreatedBy() { return createdBy; }
}
