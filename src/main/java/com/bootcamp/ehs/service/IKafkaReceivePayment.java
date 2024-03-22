package com.bootcamp.ehs.service;

import com.bootcamp.ehs.model.Wallet;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IKafkaReceivePayment {

    Mono<Wallet> receivePaymentKafka(String phoneNumber, BigDecimal amount);

}
