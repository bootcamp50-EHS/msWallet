package com.bootcamp.ehs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayWalletDTO {

    private String walletId;
    private BigDecimal amount;
}
