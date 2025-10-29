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

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 5000, multiplier = 2),
            kafkaTemplate = "kafkaTemplate",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = Constant.KAFKA_TOPIC, groupId = "user-audit", containerFactory = "kafkaListenerContainerFactory")
    public void receiveUserListener(User user){
        long startTime = System.currentTimeMillis();
        LOGGER.info("----- Start Receive User Listener -----");

        LOGGER.info("Received user: {}", user);

        try {
            userRepository.save(new UserEntity(UUID.randomUUID(), user.getName(), user.getEmail(), user.getPassword()));
        } catch (Exception e){
            LOGGER.error("Exception: ", e);
            throw e;
        } finally {
            LOGGER.info("----- End Receive User Listener in {} ms -----", System.currentTimeMillis() - startTime);
        }
    }

    @DltHandler
    public void dltHandler(User user,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMsg) {
        LOGGER.warn("DLT on topic {} with message {} due to {}", topic, user, exceptionMsg);
    }
}
