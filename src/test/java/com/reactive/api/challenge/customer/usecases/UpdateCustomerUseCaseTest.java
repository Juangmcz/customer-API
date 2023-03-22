package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.collection.Customer;
import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseTest {

    @Mock
    ICustomerRepository mockedRepository;
    ModelMapper modelMapper;
    UpdateCustomerUseCase updateCustomerUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateCustomerUseCase = new UpdateCustomerUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("updateCustomer_Success")
    void updateCustomer() {

        var customer = new Customer("customerId",
                "Ryan",
                "Lincoln",
                "Sr",
                "3128986514",
                List.of()
        );

        Mockito.when(mockedRepository.findById("customerId")).thenReturn(Mono.just(customer));

        Mockito.when(mockedRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(Mono.just(customer));

        var response = updateCustomerUseCase.apply("customerId",
                modelMapper.map(customer, CustomerDTO.class)
        );

        StepVerifier.create(response)
                .expectNext(modelMapper.map(customer, CustomerDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
        Mockito.verify(mockedRepository).save(ArgumentMatchers.any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomer_NonSuccess")
    void updateInvalidCustomer() {

        var customer = new Customer("customerId",
                "Ryan",
                "Lincoln",
                "Sr",
                "3128986514",
                List.of()
        );

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = updateCustomerUseCase.apply("",modelMapper.map(customer, CustomerDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());

    }
}