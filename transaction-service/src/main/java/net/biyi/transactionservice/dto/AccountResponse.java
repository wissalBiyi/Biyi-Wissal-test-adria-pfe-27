package net.biyi.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountResponse {

    private UUID id;
    private String customerReference;
    private String status;
    private String currency;
    private BigDecimal balance;
}