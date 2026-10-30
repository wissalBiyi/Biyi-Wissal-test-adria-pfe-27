package net.biyi.transactionservice.web.graphql;

import lombok.RequiredArgsConstructor;

import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.dto.TransactionRequest;
import net.biyi.transactionservice.service.TransactionService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TransactionGraphQLController {

    private final TransactionService transactionService;

    @QueryMapping
    public List<Transaction> transactions() {
        return transactionService.getAllTransactions();
    }

    @QueryMapping
    public Transaction transaction(@Argument String id) {
        return transactionService.getTransactionById(UUID.fromString(id));
    }

    @QueryMapping
    public List<Transaction> transactionsByAccount(
            @Argument String accountId) {

        return transactionService.getTransactionsByAccount(
                UUID.fromString(accountId));
    }

    @MutationMapping
    public Transaction createTransaction(
            @Argument TransactionInput input) {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.fromString(input.accountId()));
        request.setAmount(input.amount());
        request.setCurrency(input.currency());
        request.setMerchantCategory(input.merchantCategory());
        request.setCountry(input.country());

        return transactionService.createTransaction(request);
    }

    public record TransactionInput(
            String accountId,
            BigDecimal amount,
            String currency,
            String merchantCategory,
            String country
    ) {}
}