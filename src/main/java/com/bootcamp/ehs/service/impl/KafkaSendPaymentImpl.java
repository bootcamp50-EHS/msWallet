package com.bootcamp.ehs.service.impl;

import com.bootcamp.ehs.dto.PayWalletDTO;
import com.bootcamp.ehs.exeptions.InsufficientBalanceException;
import com.bootcamp.ehs.model.Wallet;
import com.bootcamp.ehs.producer.KafkaPaymentWalletProducer;
import com.bootcamp.ehs.repo.IWalletRepo;
import com.bootcamp.ehs.service.IKafkaSendPayment;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaSendPaymentImpl implements IKafkaSendPayment {

    private final IWalletRepo walletRepo;

    private final KafkaPaymentWalletProducer kafkaPaymentWalletProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Override
    public Mono<Wallet> sendPaymentKafka(String phoneNumber, BigDecimal amount) {
        return walletRepo.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    if (wallet.getBalance().compareTo(amount) >=0) {
                        PayWalletDTO payWalletDTO = new PayWalletDTO(wallet.getId(), amount);
                        // Emitir evento de pago al tópico de Kafka
                        try {
                            kafkaPaymentWalletProducer.sendMessagePaymentWallet(payWalletDTO);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        wallet.setBalance(wallet.getBalance().subtract(amount));
                        return walletRepo.save(wallet);
                    } else {
                        return Mono.error(new InsufficientBalanceException("Saldo insuficiente"));
                    }
                });
    }
}
