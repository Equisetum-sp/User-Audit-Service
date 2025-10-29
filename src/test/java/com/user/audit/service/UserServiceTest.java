package com.user.audit.service;

import com.user.audit.model.CreateUserResponse;
import com.user.audit.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private KafkaTemplate<String, User> kafkaTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_success() throws Exception {
        when(kafkaTemplate.send(any(), any(User.class))).thenReturn(null);

        assertEquals(new CreateUserResponse("0", "Success"), userService.createUser(new User()));
    }

    @Test
    void createUser_kafkaThrowException() throws Exception {
        when(kafkaTemplate.send(any(), any(User.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.createUser(new User()));
    }
}