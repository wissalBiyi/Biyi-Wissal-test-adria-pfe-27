package net.biyi.transactionservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import net.biyi.transactionservice.client.AccountClient;
import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.domain.TransactionStatus;
import net.biyi.transactionservice.dto.AccountResponse;
import net.biyi.transactionservice.dto.DebitRequest;
import net.biyi.transactionservice.dto.TransactionRequest;
import net.biyi.transactionservice.repository.TransactionRepository;
import net.biyi.transactionservice.service.TransactionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    @Override
    public Transaction createTransaction(TransactionRequest request) {

        // 1. Vérifier le montant
        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // 2. Vérifier le compte
        AccountResponse account =
                accountClient.getAccount(request.getAccountId());

        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }

        // 3. Vérifier le statut du compte
        if (!"ACTIVE".equals(account.getStatus())) {
            throw new IllegalStateException("Account is blocked");
        }

        // 4. Vérifier la devise
        if (!account.getCurrency().equalsIgnoreCase(request.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }

        // 5. Vérifier le solde
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        // 6. Débiter le compte
        accountClient.debitAccount(
                request.getAccountId(),
                new DebitRequest(request.getAmount())
        );

        // 7. Enregistrer la transaction
        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .merchantCategory(request.getMerchantCategory())
                .country(request.getCountry())
                .createdAt(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getTransactionById(UUID id) {

        return transactionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Transaction not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccount(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}