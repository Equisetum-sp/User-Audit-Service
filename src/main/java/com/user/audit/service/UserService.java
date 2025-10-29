package com.user.audit.service;

import com.user.audit.model.CreateUserResponse;
import com.user.audit.model.User;
import com.user.audit.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public UserService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public CreateUserResponse createUser(User user) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("----- Start Create User Service -----");

        // produce to kafka topic
        LOGGER.info("Produce to topic {}: {}", Constant.KAFKA_TOPIC, user);
        kafkaTemplate.send(Constant.KAFKA_TOPIC, user);

        LOGGER.info("----- End Create User Service in {} ms -----", System.currentTimeMillis() - startTime);
        return new CreateUserResponse("0", "Success");
    }
}
