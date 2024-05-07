package com.javatechie.repository;

import com.javatechie.dto.Customers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customers, String> {
}
