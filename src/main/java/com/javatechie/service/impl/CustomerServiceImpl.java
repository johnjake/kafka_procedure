package com.javatechie.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.Customers;
import com.javatechie.repository.CustomerRepository;
import com.javatechie.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customers saveCustomer(Customers customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customers> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customers> findCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customers jsonToCustomer(String json) {
        try {
            return mapper.readValue(json, Customers.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error parsing JSON to Customer", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
