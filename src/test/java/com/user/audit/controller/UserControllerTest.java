package com.user.audit.controller;

import com.user.audit.model.CreateUserResponse;
import com.user.audit.model.User;
import com.user.audit.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser = new User("name", "test@email.com", "pw");

    @Test
    void createUser_success() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(new CreateUserResponse("0", "Success"));

        ResponseEntity<CreateUserResponse> expected = new ResponseEntity<>(new CreateUserResponse("0", "Success"), HttpStatus.OK);
        assertEquals(expected.getStatusCode(), userController.createUser(testUser).getStatusCode());
        assertEquals(expected.getBody(), userController.createUser(testUser).getBody());
    }

    @Test
    void createUser_throwException() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException());

        ResponseEntity<CreateUserResponse> expected = new ResponseEntity<>(new CreateUserResponse("1", "Failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(expected.getStatusCode(), userController.createUser(testUser).getStatusCode());
        assertEquals(expected.getBody(), userController.createUser(testUser).getBody());
    }
}