package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Transaction;

public interface TransactionService {
    public void executeTransaction(Transaction transaction);
}
