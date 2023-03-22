package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.collection.Customer;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllCustomersUseCaseTest {

    @Mock
    ICustomerRepository mockedRepository;
    ModelMapper modelMapper;
    GetAllCustomersUseCase getAllCustomersUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getAllCustomersUseCase = new GetAllCustomersUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("getAllCustomers_Success")
    void getAllCustomers() {

        var customer1 = new Customer("customer1Id",
                        "Ryan",
                        "Lincoln",
                        "Sr",
                        "3128986514",
                        List.of()
        );

        var customer2 = new Customer("customer2Id",
                        "Ryan",
                        "Lincoln",
                        "Jr",
                        "3556928742",
                        List.of()
        );

        var fluxCustomers = Flux.just(customer1, customer2);

        Mockito.when(mockedRepository.findAll()).thenReturn(fluxCustomers);

        var response = getAllCustomersUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(mockedRepository).findAll();

    }
}