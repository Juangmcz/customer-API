package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteCustomerUseCase implements Function<String, Mono<Void>> {

    private final ICustomerRepository customerRepository;

    @Override
    public Mono<Void> apply(String id) {
        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Customer not found")))
                .flatMap(customer -> this.customerRepository.deleteById(customer.getId()))
                .onErrorResume(throwable -> Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));
    }
}
