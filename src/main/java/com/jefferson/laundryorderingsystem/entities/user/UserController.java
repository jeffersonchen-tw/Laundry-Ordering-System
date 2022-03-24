package com.jefferson.laundryorderingsystem.entities.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", produces = "applications/json")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    // testing
    @GetMapping(value = "/test")
    public String test() {
        return "Hello";
    }

    // create User
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody User newUser) {
        Optional<User> user = userRepo.findById(newUser.getId());
        if (user.isEmpty()) {
            userRepo.save(newUser);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully create user.");
        }
        return ResponseEntity.badRequest().body("User already exist.");
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> loginUser(@Valid @RequestBody User user) {
        Optional<User> userInDB = userRepo.findById(user.getId());
        if (userInDB.isPresent() && user.equals(userInDB.get())) {
            user.setIsLogin(true);
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.badRequest().body(-1);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestBody User user) {
        Optional<User> userInDB = userRepo.findById(user.getId());
        if (userInDB.isPresent() && user.equals(userInDB.get())) {
            user.setIsLogin(false);
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully logout.");
        }
        return ResponseEntity.badRequest().body("Unable to logout.");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody User user) {
        Optional<User> userInDB = userRepo.findById(user.getId());
        if (userInDB.isPresent()) {
            userRepo.delete(userInDB.get());
            return ResponseEntity.status(HttpStatus.OK).body("Successfully to delete user.");
        }
        return ResponseEntity.badRequest().body("Unable to delete user.");
    }

    @PostMapping("/set-credit")
    public ResponseEntity<String> addCredit(@Valid @RequestParam int id, @RequestParam String password, @RequestParam int credit) {
        Optional<User> optUserInDB = userRepo.findById(id);
        if (optUserInDB.isPresent()) {
            User userInDB = optUserInDB.get();
            if (userInDB.getPassword().equals(password)) {
                userInDB.setCredit(credit);
                userRepo.save(userInDB);
                return ResponseEntity.status(HttpStatus.OK).body("Credit set!");
            }
        }
        return ResponseEntity.badRequest().body("Unable correctly to set credit.");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAllUsers() {
        userRepo.deleteAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request accepted.");
    }
}
