package com.reactive.api.challenge.customer.usecases;

import com.reactive.api.challenge.customer.domain.appointment.Appointment;
import com.reactive.api.challenge.customer.domain.dto.CustomerDTO;
import com.reactive.api.challenge.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class AddAppointmentUseCase implements BiFunction<String, Appointment, Mono<CustomerDTO>> {

    private final ModelMapper modelMapper;
    private final ICustomerRepository customerRepository;

    @Override
    public Mono<CustomerDTO> apply(String id, Appointment appointment){

        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(customer -> {
                    var appointmentList = customer.getAppointments();
                    appointmentList.add(appointment);
                    customer.setName("Maradonna");
                    customer.setAppointments(appointmentList);
                    return this.customerRepository.save(customer);
                })
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }
}
