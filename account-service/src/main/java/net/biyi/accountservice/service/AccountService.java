package net.biyi.accountservice.service;

import net.biyi.accountservice.domain.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountById(UUID id);

    List<Account> getAllAccounts();

    List<Account> getAccountsByCustomer(String customerReference);

    Account blockAccount(UUID id);

    BigDecimal getBalance(UUID id);
}