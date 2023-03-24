package com.reactive.api.challenge.customer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.api.challenge.customer.usecases.AddAppointmentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerConsumer {

    private final ObjectMapper objectMapper;
    private final AddAppointmentUseCase addAppointmentUsecase;

    @RabbitListener(queues = "appointments.queue")
    public void receiveEventBook(String message) throws JsonProcessingException {
        AppointmentEvent event = objectMapper.readValue(message, AppointmentEvent.class);
        addAppointmentUsecase.apply(event.getCustomerId(), event.getAppointmentScheduled()).subscribe();

    }
}
