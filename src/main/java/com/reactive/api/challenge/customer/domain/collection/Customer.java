package com.reactive.api.challenge.customer.domain.collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);
    @NotNull(message = "The name cannot be null")
    private String name;
    @NotNull(message = "The last name cannot be null")
    private String lastName;
    @NotNull(message = "The prefix cannot be null")
    private String prefix;
    @NotNull(message = "The cellphone cannot be null")
    private String cell;

}
