package com.reactive.api.challenge.customer.domain.appointment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Appointment {

    private String id;
    private String date;
    private String barberName;
    private Boolean customerArrived;
    private Boolean isFulFilled;
}
