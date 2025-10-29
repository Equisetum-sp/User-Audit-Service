package com.user.audit.service;

import com.user.audit.model.User;
import com.user.audit.model.entity.UserEntity;
import com.user.audit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuditService auditService;

    private User testUser = new User("name", "test@email.com", "pw");

    @Test
    void receiveUserListener_success() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(null);
        assertDoesNotThrow(() -> auditService.receiveUserListener(testUser));
    }

    @Test
    void receiveUserListener_throwException() {
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> auditService.receiveUserListener(testUser));
    }

    @Test
    void dltHandler() {
        assertDoesNotThrow(() -> auditService.dltHandler(testUser, "topic", "msg"));
    }
}