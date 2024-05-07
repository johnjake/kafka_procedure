package com.javatechie.service;

import com.javatechie.dto.Customers;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customers saveCustomer(Customers customer);
    List<Customers> findAllCustomers();
    Optional<Customers> findCustomerById(String id);
    Customers jsonToCustomer(String json);
}
