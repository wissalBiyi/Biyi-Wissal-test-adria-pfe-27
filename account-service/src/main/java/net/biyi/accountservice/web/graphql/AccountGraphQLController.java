package net.biyi.accountservice.web.graphql;

import net.biyi.accountservice.domain.Account;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountGraphQLController {

    private final AccountService accountService;

    @QueryMapping
    public List<Account> accounts() {
        return accountService.getAllAccounts();
    }

    @QueryMapping
    public Account account(@Argument String id) {
        return accountService.getAccountById(UUID.fromString(id));
    }

    @QueryMapping
    public BigDecimal balance(@Argument String id) {
        return accountService.getBalance(UUID.fromString(id));
    }

    @QueryMapping
    public List<Account> accountsByCustomer(
            @Argument String customerReference) {

        return accountService.getAccountsByCustomer(customerReference);
    }

    @MutationMapping
    public Account createAccount(@Argument AccountInput input) {

        Account account = Account.builder()
                .customerReference(input.customerReference())
                .currency(input.currency())
                .balance(input.balance())
                .build();

        return accountService.createAccount(account);
    }

    @MutationMapping
    public Account blockAccount(@Argument String id) {
        return accountService.blockAccount(UUID.fromString(id));
    }

    public record AccountInput(
            String customerReference,
            String currency,
            BigDecimal balance
    ) {}
}
