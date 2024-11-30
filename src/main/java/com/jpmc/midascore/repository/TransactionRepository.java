package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionRecord, Long> {
    TransactionRecord findById(long id);
    List<TransactionRecord> findAllBySenderId(long id);
    List<TransactionRecord> findAllByRecipientId(long id);
    List<TransactionRecord> findAllBySenderIdAndRecipientId(long sender, long recipient);
}
