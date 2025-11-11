package com.yash.controller;

import com.yash.exception.UserException;
import com.yash.modal.User;
import com.yash.repository.UserRepository;
import com.yash.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private IUserService userService;
    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws Exception{

        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                           @PathVariable Long id) throws Exception {

        User updatedUser = userService.updateUser(id,user);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws Exception {

        userService.deleteUser(id);
        return new ResponseEntity<>("User Deleted",HttpStatus.ACCEPTED);
    }
}
