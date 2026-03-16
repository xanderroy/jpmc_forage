package com.jpmc.midascore;

import com.jpmc.midascore.component.TransactionHandler;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableKafka
public class MidasCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(MidasCoreApplication.class, args);
    }
}

@Component
class Listener {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final TransactionHandler transactionHandler;

    Listener(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    @KafkaListener(id = "listener", topics = "${kafka.topic.name}")
    public void listen(Transaction transaction) {
        log.info("Received: {}", transaction);
        transactionHandler.handle(transaction);
    }
}