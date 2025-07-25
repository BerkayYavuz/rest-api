package com.example.movies.controller;


import com.example.movies.model.User;
import com.example.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Kullanıcı Silindi";
        }else{
            return "Kullanıcı YOK!";
        }
    }
}
