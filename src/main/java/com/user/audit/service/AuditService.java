package com.user.audit.service;

import com.user.audit.model.User;
import com.user.audit.model.entity.UserEntity;
import com.user.audit.repository.UserRepository;
import com.user.audit.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserRepository userRepository;

    @Autowired
    public AuditService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO: handle exception for serialization error

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            kafkaTemplate = "kafkaTemplate",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = Constant.KAFKA_TOPIC, groupId = "user-audit")
    public void createUserListener(User user){
        LOGGER.info("Received user: {}", user);

        userRepository.save(new UserEntity(UUID.randomUUID(), user.getName(), user.getEmail(), user.getPassword()));
    }

    @DltHandler
    public void dltHandler(User user,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        LOGGER.warn("DLT on topic {} with message {}", topic, user);
    }
}
