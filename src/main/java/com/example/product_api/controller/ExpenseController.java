package com.example.product_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ExpenseController {
    @GetMapping("/expenses")
    public String getMethodName(@RequestParam String param) {
        return "Reading the expenses from database";
    }

}
