package com.javatechie.config;

import com.javatechie.dto.Customers;
import com.javatechie.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    private final CustomerService customerService;

    @Autowired
    public KafkaMessageListener(CustomerService customerService) {
        this.customerService = customerService;
    }

    @KafkaListener(topics = "pl_library", groupId = "dbs-id")
    public void listen(String message) {
        if (!message.isEmpty()) {
            try {
                Customers customer = customerService.jsonToCustomer(message);
                System.out.println("#### Received message: ########" + customer.getId());
                customerService.saveCustomer(customer);
            } catch (Exception e) {
                System.err.println("Error converting message to Customer: " + e.getMessage());
            }
        }
    }
}
