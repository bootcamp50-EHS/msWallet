package com.bootcamp.ehs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    private String id;
    private String dni;
    private String phoneNumber;
    private String imei;
    private String email;
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0);

}
