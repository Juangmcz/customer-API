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
class DeleteCustomerUseCaseTest {

    @Mock
    ICustomerRepository mockedRepository;
    ModelMapper modelMapper;
    DeleteCustomerUseCase deleteCustomerUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        deleteCustomerUseCase = new DeleteCustomerUseCase(mockedRepository);
    }

    @Test
    @DisplayName("deleteCustomer_Success")
    void deleteCustomer() {

        var customer = Mono.just(new Customer("customerId",
                        "Ryan",
                        "Lincoln",
                        "Sr",
                        "3128986514",
                        List.of()
        ));

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(customer);

        Mockito.when(mockedRepository.deleteById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = deleteCustomerUseCase.apply("customerId");

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(mockedRepository).deleteById(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("deleteInvalidCustomer_NonSuccess")
    void deleteInvalidCustomer(){

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = deleteCustomerUseCase.apply("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById("");

    }
}