package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.appointment.Appointment;
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

@ExtendWith(MockitoExtension.class)
class AddAppointmentUseCaseTest {

        @Mock
        ICustomerRepository mockedRepository;

        ModelMapper modelMapper;

        AddAppointmentUseCase addAppointmentUseCase;

        @BeforeEach
        void init(){
            modelMapper = new ModelMapper();
            addAppointmentUseCase = new AddAppointmentUseCase(modelMapper, mockedRepository);
        }

        @Test
        @DisplayName("AddAppointment_Success")
        void AddAppointment(){

            var appointment = new Appointment("appointmentId","03/22/2023", "Ryan Watson");
            var customer = new Customer("John", "watson","Sr", "3127675489");

            Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(customer));

            Mockito.when(mockedRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(Mono.just(modelMapper.map(customer, Customer.class)));

            var response = addAppointmentUseCase.apply("", appointment);

            StepVerifier.create(response)
                    .expectNextMatches(c -> c.getAppointments().size() == 1)
                    .verifyComplete();

            Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
            Mockito.verify(mockedRepository).save(ArgumentMatchers.any(Customer.class));
    }

    @Test
    @DisplayName("AddAppointment_NonSuccess")
    void AddAppointmentWithInvalidCustomer(){

        var appointment = new Appointment("appointmentId","03/22/2023", "Ryan Watson");

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = addAppointmentUseCase.apply("invalidId", appointment);

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
    }
}