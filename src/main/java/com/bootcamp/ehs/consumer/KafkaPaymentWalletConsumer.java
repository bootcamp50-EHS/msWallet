package com.bootcamp.ehs.consumer;

import com.bootcamp.ehs.dto.PayWalletDTO;
import com.bootcamp.ehs.service.IKafkaReceivePayment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentWalletConsumer {

    @Autowired
    private IKafkaReceivePayment kafkaReceivePayment;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPaymentWalletConsumer.class);
    private final ObjectMapper objectMapper;

    public KafkaPaymentWalletConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "paymentWallet-topic" , groupId = "default")
    public void payCredit(String message) throws JsonProcessingException {
        PayWalletDTO payWallet = objectMapper.readValue(message, PayWalletDTO.class);
        LOGGER.info("Obteniendo Pago de Monedero de Consumer: {} " , payWallet);
        kafkaReceivePayment.receivePaymentKafka(payWallet.getWalletId(), payWallet.getAmount())
                .subscribe(credit -> {
                    LOGGER.info("KafkaConsumer ->  Monedero a sido actualizado" + payWallet.getWalletId());
                });

    }

}
