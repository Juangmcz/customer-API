package com.reactive.api.challenge.customer.consumer;

import com.reactive.api.challenge.customer.domain.appointment.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentEvent {

    private String customerId;
    private Appointment appointmentScheduled;
    private String eventType;
}
