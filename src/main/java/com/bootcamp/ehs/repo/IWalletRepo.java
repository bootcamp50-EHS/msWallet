package com.bootcamp.ehs.repo;

import com.bootcamp.ehs.model.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IWalletRepo extends ReactiveMongoRepository<Wallet, String> {

    Mono<Wallet> findByDni(String dni);
    Mono<Wallet> findByPhoneNumber(String phoneNumber);

}
