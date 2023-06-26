package com.example.kafkaMRI.kafka;

import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Producer {

    private static final Logger kafkaLogger = LoggerFactory.getLogger(Producer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String amount){
        kafkaLogger.info(String.format("U wilde %s getallen", amount));
        kafkaLogger.info(String.format("U wilde %s getallen", amount));
        kafkaTemplate.send("mriTopic", amount);
    }
}
