package com.dev.yank.springsecurityclass.controllers;

import com.dev.yank.springsecurityclass.model.Customer;
import com.dev.yank.springsecurityclass.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;


    public UserController(CustomerRepository customerRepository, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashPwd = encoder.encode(customer.getPassword());
            customer.setPassword(hashPwd);
            Customer savedCustomer = customerRepository.save(customer);

            if(savedCustomer.getId()>0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }

    }
}
