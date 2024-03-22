package com.bootcamp.ehs.service;

import com.bootcamp.ehs.model.Wallet;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IWalletService {

    Mono<Wallet> sendPayment(String phoneNumber, BigDecimal amount);
    Mono<Wallet> receivePayment(String phoneNumber, BigDecimal amount);
    Mono<Wallet> associateWithDebitCard(String phoneNumber, String cardNumber);


}
