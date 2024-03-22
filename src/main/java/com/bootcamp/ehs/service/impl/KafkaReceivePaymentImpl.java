package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.model.Wallet;
import com.bootcamp.ehs.repo.IWalletRepo;
import com.bootcamp.ehs.service.IKafkaReceivePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaReceivePaymentImpl implements IKafkaReceivePayment {

    private final IWalletRepo walletRepo;
    @Override
    public Mono<Wallet> receivePaymentKafka(String phoneNumber, BigDecimal amount) {
        return walletRepo.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    wallet.setBalance(wallet.getBalance().add(amount));
                    return walletRepo.save(wallet);
                });
    }
}
