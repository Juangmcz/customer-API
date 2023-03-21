package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllCustomersUseCase implements Supplier<Flux<CustomerDTO>> {

    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<CustomerDTO> get() {
        return this.customerRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }
}
