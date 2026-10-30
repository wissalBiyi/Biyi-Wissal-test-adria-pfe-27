package net.biyi.transactionservice.mcp;

import lombok.RequiredArgsConstructor;

import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.dto.TransactionRequest;
import net.biyi.transactionservice.service.TransactionService;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionMcpTools {

    private final TransactionService transactionService;

    @Tool(description = "Get all bank transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @Tool(description = "Get a transaction by its ID")
    public Transaction getTransaction(
            @ToolParam(description = "Transaction UUID") String id) {

        return transactionService.getTransactionById(UUID.fromString(id));
    }

    @Tool(description = "Get all transactions for a bank account")
    public List<Transaction> getTransactionsByAccount(
            @ToolParam(description = "Account UUID") String accountId) {

        return transactionService.getTransactionsByAccount(
                UUID.fromString(accountId));
    }

    @Tool(description = "Create a bank transaction")
    public Transaction createTransaction(
            @ToolParam(description = "Account UUID") String accountId,
            @ToolParam(description = "Transaction amount") BigDecimal amount,
            @ToolParam(description = "Currency") String currency,
            @ToolParam(description = "Merchant category") String merchantCategory,
            @ToolParam(description = "Country") String country) {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.fromString(accountId));
        request.setAmount(amount);
        request.setCurrency(currency);
        request.setMerchantCategory(merchantCategory);
        request.setCountry(country);

        return transactionService.createTransaction(request);
    }
}