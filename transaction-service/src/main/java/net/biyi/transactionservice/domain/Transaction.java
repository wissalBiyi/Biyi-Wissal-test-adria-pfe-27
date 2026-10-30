package net.biyi.transactionservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID accountId;

    private BigDecimal amount;

    private String currency;

    private String merchantCategory;

    private String country;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}