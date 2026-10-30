package net.biyi.accountservice.web.rest;

import lombok.RequiredArgsConstructor;
import net.biyi.accountservice.domain.Account;
import net.biyi.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    // Create an account
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account) {

        Account created = accountService.createAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Get account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // Get accounts by customer
    @GetMapping("/customer/{customerReference}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(
            @PathVariable String customerReference) {

        return ResponseEntity.ok(
                accountService.getAccountsByCustomer(customerReference));
    }

    // Block an account
    @PatchMapping("/{id}/block")
    public ResponseEntity<Account> blockAccount(
            @PathVariable UUID id) {

        return ResponseEntity.ok(accountService.blockAccount(id));
    }

    // Get account balance
    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @PathVariable UUID id) {

        return ResponseEntity.ok(accountService.getBalance(id));
    }
    @PostMapping("/{id}/debit")
    public ResponseEntity<Account> debitAccount(
            @PathVariable UUID id,
            @RequestBody BigDecimal amount) {

        return ResponseEntity.ok(
                accountService.debitAccount(id, amount)
        );
    }
}