package com.user.audit.controller;

import com.user.audit.model.CreateUserResponse;
import com.user.audit.model.User;
import com.user.audit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody User requestBody){
        //long startTime = System.currentTimeMillis();
        ResponseEntity<CreateUserResponse> response;

        try {
            response = new ResponseEntity<>(userService.createUser(requestBody), HttpStatus.OK);
        } catch (Exception e) {
            String msg = Objects.requireNonNullElse(e.getMessage(), "Failed");
            response = new ResponseEntity<>(new CreateUserResponse("1", msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
