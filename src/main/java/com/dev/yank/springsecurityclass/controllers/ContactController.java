package com.dev.yank.springsecurityclass.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/contact")
    public String getContactDetails() {
        return "Inquiry details are saved to the DB.";
    }
}
