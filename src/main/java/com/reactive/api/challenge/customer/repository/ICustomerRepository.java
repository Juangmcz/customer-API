package com.reactive.api.challenge.customer.repository;

import com.reactive.api.challenge.customer.domain.collection.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
