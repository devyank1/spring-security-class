package com.dev.yank.springsecurityclass.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/balance")
    public String getBalanceDetails() {
        return "Here are balance details from DB";
    }
}
