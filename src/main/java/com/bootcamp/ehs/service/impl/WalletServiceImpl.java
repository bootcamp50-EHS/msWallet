package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.dto.PayWalletDTO;
import com.bootcamp.ehs.exeptions.InsufficientBalanceException;
import com.bootcamp.ehs.model.Wallet;
import com.bootcamp.ehs.producer.KafkaPaymentWalletProducer;
import com.bootcamp.ehs.repo.IWalletRepo;
import com.bootcamp.ehs.service.IWalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepo walletRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Override
    public Mono<Wallet> sendPayment(String phoneNumber, BigDecimal amount) {
        return walletRepo.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    if (wallet.getBalance().compareTo(amount) >= 0) {
                        wallet.setBalance(wallet.getBalance().subtract(amount));
                        return walletRepo.save(wallet);
                    } else {
                        return Mono.error(new InsufficientBalanceException("Wallet -> sendPayment: Saldo insuficiente"));
                    }
                });
    }

    @Override
    public Mono<Wallet> receivePayment(String phoneNumber, BigDecimal amount) {
        return walletRepo.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    wallet.setBalance(wallet.getBalance().add(amount));
                    return walletRepo.save(wallet);
                });
    }

    @Override
    public Mono<Wallet> associateWithDebitCard(String phoneNumber, String cardNumber) {
        return null; /*walletRepo.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    wallet.setDebitCardNumber(cardNumber);
                    return walletRepository.save(wallet);
                });*/
    }


}
