package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found id: " + id));
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            logger.error("Not found id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Not found username: " + username);
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
            logger.info("Username {} already exists!", createUserRequest.getUsername());
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);

        if (createUserRequest.getPassword().length() < 7 || !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            logger.debug("Error - Either length is less than 7 or pass and conf pass do not match. Unable to create", createUserRequest.getUsername());
            return ResponseEntity.badRequest().body("Error - Either length is less than 7 or pass and conf pass do not match. Unable to create");
        }
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        userRepository.save(user);
        logger.info("User created successfully!");
        return ResponseEntity.ok(user);
    }

}
