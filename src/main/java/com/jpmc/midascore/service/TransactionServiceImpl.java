package com.jpmc.midascore.service;

import com.jpmc.midascore.dto.IncentiveDto;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Value("${external.incentive}")
    private String incentiveUrl;

    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void executeTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
//        validate
        if (sender == null || recipient == null) return;
        if (sender.getBalance() < transaction.getAmount()) return;
//        incentivize
        float incentive = callIncentiveApi(transaction);
//        execute
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentive);
//        save
        userRepository.save(sender);
        userRepository.save(recipient);
        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), incentive);
        transactionRepository.save(transactionRecord);
    }

    private float callIncentiveApi(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            IncentiveDto incentive = restTemplate.postForObject(incentiveUrl, transaction, IncentiveDto.class);
            assert incentive != null;
            return incentive.getAmount();
        } catch (RestClientException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
