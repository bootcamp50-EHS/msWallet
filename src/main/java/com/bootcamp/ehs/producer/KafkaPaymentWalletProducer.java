package com.bootcamp.ehs.producer;

import com.bootcamp.ehs.dto.PayWalletDTO;
import com.bootcamp.ehs.model.Wallet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class KafkaPaymentWalletProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPaymentWalletProducer.class);
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplateCredit; // Envia mensajes a topico de kafka

    public KafkaPaymentWalletProducer(KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper objectMapper) {
        this.kafkaTemplateCredit = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> sendMessagePaymentWallet(PayWalletDTO payWalletDTO) throws JsonProcessingException {
        LOGGER.info("En SendMessagePaymentWallet");
        String message = objectMapper.writeValueAsString(payWalletDTO);
        LOGGER.info("Producing message PaymentWallet{} ", message);
        this.kafkaTemplateCredit.send("paymentWallet-topic", message);
        return Mono.empty();
    }
}
