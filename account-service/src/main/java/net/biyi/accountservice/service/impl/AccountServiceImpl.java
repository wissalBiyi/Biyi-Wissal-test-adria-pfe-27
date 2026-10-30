package net.biyi.accountservice.service.impl;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import net.biyi.accountservice.domain.Account;
import net.biyi.accountservice.domain.AccountStatus;
import net.biyi.accountservice.repository.AccountRepository;
import net.biyi.accountservice.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {

        if (account.getBalance() == null ||
                account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid account balance");
        }

        if (account.getCurrency() == null ||
                account.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (account.getCustomerReference() == null ||
                account.getCustomerReference().isBlank()) {
            throw new IllegalArgumentException("Customer reference is required");
        }

        account.setStatus(AccountStatus.ACTIVE);

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsByCustomer(String customerReference) {
        return accountRepository.findByCustomerReference(customerReference);
    }

    @Override
    public Account blockAccount(UUID id) {
        Account account = getAccountById(id);
        account.setStatus(AccountStatus.BLOCKED);
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(UUID id) {
        return getAccountById(id).getBalance();
    }
    @Override
    public Account debitAccount(UUID id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid debit amount");
        }

        Account account = getAccountById(id);

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is blocked");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        return accountRepository.save(account);
    }
}