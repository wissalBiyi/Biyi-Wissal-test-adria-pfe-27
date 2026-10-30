package net.biyi.transactionservice.client;

import net.biyi.transactionservice.dto.AccountResponse;
import net.biyi.transactionservice.dto.DebitRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/accounts/{id}")
    AccountResponse getAccount(@PathVariable("id") UUID id);

    @PostMapping("/api/accounts/{id}/debit")
    AccountResponse debitAccount(
            @PathVariable("id") UUID id,
            @RequestBody DebitRequest request
    );
}