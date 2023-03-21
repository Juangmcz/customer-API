package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.collection.Customer;
import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveCustomerUseCase implements Function<CustomerDTO, Mono<CustomerDTO>> {

    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<CustomerDTO> apply(CustomerDTO studentDTO) {
        return this.customerRepository.save(modelMapper.map(studentDTO, Customer.class))
                .switchIfEmpty(Mono.empty())
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .onErrorResume(Mono::error);
    }
}
