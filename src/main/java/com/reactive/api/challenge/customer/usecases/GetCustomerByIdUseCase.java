package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetCustomerByIdUseCase implements Function<String, Mono<CustomerDTO>> {

    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<CustomerDTO> apply(String id) {
        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Not found")))
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .onErrorResume(Mono::error);
    }
}
