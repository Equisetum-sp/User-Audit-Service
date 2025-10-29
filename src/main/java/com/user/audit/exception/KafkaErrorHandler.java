package com.user.audit.exception;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

public class KafkaErrorHandler implements CommonErrorHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean handleOne(Exception e, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(e, consumer);
        return true;
    }

    @Override
    public void handleOtherException(Exception e, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(e, consumer);
    }

    private void handle(Exception e, Consumer<?, ?> consumer) {
        LOGGER.error("Exception thrown ", e);
        if (e instanceof RecordDeserializationException ex) {
            LOGGER.warn("Skipping unserializable message");
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
        }
    }
}
