package net.biyi.transactionservice.service;

import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.dto.TransactionRequest;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction createTransaction(TransactionRequest request);

    Transaction getTransactionById(UUID id);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByAccount(UUID accountId);
}