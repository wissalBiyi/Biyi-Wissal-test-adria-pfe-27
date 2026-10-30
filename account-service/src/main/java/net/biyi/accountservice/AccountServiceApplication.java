package net.biyi.accountservice;

import net.biyi.accountservice.domain.Account;
import net.biyi.accountservice.domain.AccountStatus;
import net.biyi.accountservice.repository.AccountRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository) {
        return args -> {

            if (accountRepository.count() == 0) {

                Account account1 = new Account();
                account1.setCustomerReference("CUST001");
                account1.setStatus(AccountStatus.ACTIVE);
                account1.setCurrency("MAD");
                account1.setBalance(new BigDecimal("15000"));

                Account account2 = new Account();
                account2.setCustomerReference("CUST002");
                account2.setStatus(AccountStatus.ACTIVE);
                account2.setCurrency("MAD");
                account2.setBalance(new BigDecimal("25000"));

                Account account3 = new Account();
                account3.setCustomerReference("CUST003");
                account3.setStatus(AccountStatus.BLOCKED);
                account3.setCurrency("EUR");
                account3.setBalance(new BigDecimal("5000"));

                accountRepository.save(account1);
                accountRepository.save(account2);
                accountRepository.save(account3);

                System.out.println("3 accounts inserted successfully!");
            }
        };
    }
}