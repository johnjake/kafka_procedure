package com.javatechie.controller;

import com.javatechie.dto.Customers;
import com.javatechie.service.CustomerService;
import com.javatechie.service.KafkaMessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/producer-app")
public class KafkaController {

    private final KafkaMessagePublisher publisher;
    private final CustomerService service;
    public KafkaController(KafkaMessagePublisher publisher, CustomerService service) {
        this.publisher = publisher;
        this.service = service;
    }

    /***
     *
     * @param message
     * @return
     */
    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try {
            for (int i = 0; i <= 10; i++) {
                publisher.sendMessageToTopic(message + " : " + i);
            }
            return ResponseEntity.ok("message published successfully ..");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    /***
     *
     * @param customer
     */
    @PostMapping("/publish")
    public void sendEvents(@RequestBody Customers customer) {
        publisher.sendEventsToTopic(customer);
    }

    @PostMapping("/customer")
    public ResponseEntity<Customers> saveCustomer(@RequestBody Customers customer) {
        try {
            Customers result = service.saveCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customers> findCustomer(@PathVariable String id) {
        try {
            Optional<Customers> result = service.findCustomerById(id);
            return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
