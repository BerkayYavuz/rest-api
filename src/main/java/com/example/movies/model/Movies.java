package com.example.movies.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movies {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    @Column(name = "release_year")
    private String year;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_movies",
    joinColumns = @JoinColumn(name = "movies_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

}
