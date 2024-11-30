package com.jpmc.midascore.messaging;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ForageKafkaListener {

    private final TransactionService transactionService;

    public ForageKafkaListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "forage-midas")
    public void listen(Transaction transaction) {
        transactionService.executeTransaction(transaction);
    }

}
