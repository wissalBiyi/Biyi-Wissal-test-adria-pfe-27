package net.biyi.transactionservice;

import net.biyi.transactionservice.domain.Transaction;
import net.biyi.transactionservice.domain.TransactionStatus;
import net.biyi.transactionservice.repository.TransactionRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(TransactionRepository transactionRepository) {
        return args -> {

            if (transactionRepository.count() == 0) {

                Transaction transaction1 = new Transaction();
                transaction1.setAccountId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
                transaction1.setAmount(new BigDecimal("500"));
                transaction1.setCurrency("MAD");
                transaction1.setMerchantCategory("Shopping");
                transaction1.setCountry("Morocco");
                transaction1.setStatus(TransactionStatus.SUCCESS);
                transaction1.setCreatedAt(LocalDateTime.now());

                Transaction transaction2 = new Transaction();
                transaction2.setAccountId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
                transaction2.setAmount(new BigDecimal("200"));
                transaction2.setCurrency("MAD");
                transaction2.setMerchantCategory("Restaurant");
                transaction2.setCountry("Morocco");
                transaction2.setStatus(TransactionStatus.SUCCESS);
                transaction2.setCreatedAt(LocalDateTime.now());

                Transaction transaction3 = new Transaction();
                transaction3.setAccountId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
                transaction3.setAmount(new BigDecimal("1000"));
                transaction3.setCurrency("MAD");
                transaction3.setMerchantCategory("Travel");
                transaction3.setCountry("France");
                transaction3.setStatus(TransactionStatus.SUCCESS);
                transaction3.setCreatedAt(LocalDateTime.now());

                transactionRepository.save(transaction1);
                transactionRepository.save(transaction2);
                transactionRepository.save(transaction3);

                System.out.println("3 transactions inserted successfully!");
            }
        };
    }
}