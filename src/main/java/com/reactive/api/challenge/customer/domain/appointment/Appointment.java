package com.reactive.api.challenge.customer.domain.appointment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Appointment {

    private String id;
    private String date;
    private String barberName;
    private boolean isScheduled;

    public Appointment(String id, String date, String barberName) {
        this.id = id;
        this.date = date;
        this.barberName = barberName;
        this.isScheduled = false;
    }
}
