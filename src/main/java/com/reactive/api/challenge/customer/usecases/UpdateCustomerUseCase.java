package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.collection.Customer;
import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UpdateCustomerUseCase implements BiFunction<String, CustomerDTO, Mono<CustomerDTO>> {

    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<CustomerDTO> apply(String id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Not found")))
                .flatMap(customer -> {
                    customerDTO.setId(customer.getId());
                    return customerRepository.save(modelMapper.map(customerDTO, Customer.class));
                }).map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .onErrorResume(Mono::error);
    }
}
