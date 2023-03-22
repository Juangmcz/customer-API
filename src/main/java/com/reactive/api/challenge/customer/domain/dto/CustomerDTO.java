package com.reactive.api.challenge.customer.domain.dto;

import com.reactive.api.challenge.customer.domain.appointment.Appointment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {


    private String id;
    @NotNull(message = "The name cannot be null")
    private String name;
    @NotNull(message = "The last name cannot be null")
    private String lastName;
    @NotNull(message = "The prefix cannot be null")
    private String prefix;
    @NotNull(message = "The cellphone cannot be null")
    private String cell;
    private List<Appointment> appointments;
}
