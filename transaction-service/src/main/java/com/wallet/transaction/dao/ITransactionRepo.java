package com.wallet.transaction.dao;

import com.wallet.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepo extends JpaRepository<Transaction,Long> {
    public Transaction findByTransactionId(String transactionId);
}
