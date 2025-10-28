package com.user.audit.service;

import com.user.audit.model.CreateUserResponse;
import com.user.audit.model.User;
import com.user.audit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public UserService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public CreateUserResponse createUser(User user) throws Exception {
        // sent to kafka topic
        kafkaTemplate.send(Constant.KAFKA_TOPIC, user);

        return new CreateUserResponse("0", "Success");
    }
}
