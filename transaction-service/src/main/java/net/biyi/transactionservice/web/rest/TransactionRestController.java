package net.biyi.transactionservice.web.rest;

import lombok.RequiredArgsConstructor;

import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.dto.TransactionRequest;
import net.biyi.transactionservice.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody TransactionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(request));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(
                transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                transactionService.getTransactionById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(
            @PathVariable UUID accountId) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByAccount(accountId));
    }
}