package net.biyi.accountservice.mcp;

import lombok.RequiredArgsConstructor;
import net.biyi.accountservice.domain.Account;
import net.biyi.accountservice.service.AccountService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountMcpTools {

    private final AccountService accountService;

    @Tool(description = "Get an account using its ID")
    public Account getAccount(
            @ToolParam(description = "Account UUID") String id) {

        return accountService.getAccountById(UUID.fromString(id));
    }

    @Tool(description = "Get all bank accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Tool(description = "Get the balance of an account")
    public BigDecimal getBalance(
            @ToolParam(description = "Account UUID") String id) {

        return accountService.getBalance(UUID.fromString(id));
    }

    @Tool(description = "Create a new bank account")
    public Account createAccount(
            @ToolParam(description = "Customer reference") String customerReference,
            @ToolParam(description = "Currency, for example MAD or EUR") String currency,
            @ToolParam(description = "Initial balance") BigDecimal balance) {

        Account account = Account.builder()
                .customerReference(customerReference)
                .currency(currency)
                .balance(balance)
                .build();

        return accountService.createAccount(account);
    }

    @Tool(description = "Block a bank account")
    public Account blockAccount(
            @ToolParam(description = "Account UUID") String id) {

        return accountService.blockAccount(UUID.fromString(id));
    }
}