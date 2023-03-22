package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.collection.Customer;
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
class GetCustomerByIdUseCaseTest {

    @Mock
    ICustomerRepository mockedRepository;
    ModelMapper modelMapper;
    GetCustomerByIdUseCase getCustomerByIdUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getCustomerByIdUseCase = new GetCustomerByIdUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("getCustomerById_Success")
    void getCustomerById() {

        var customer = Mono.just(new Customer("customerId",
                        "Ryan",
                        "Lincoln",
                        "Sr",
                        "3128986514",
                        List.of()
        ));

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(customer);

        var response = getCustomerByIdUseCase.apply("customerId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(mockedRepository).findById("customerId");
    }

    @Test
    @DisplayName("getCustomerByWrongId_NonSuccess")
    void getCustomerByWrongId() {

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = getCustomerByIdUseCase.apply("customerId");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById("customerId");

    }
}