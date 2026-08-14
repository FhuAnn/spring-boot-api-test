package com.example.product_api.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_api.dto.ExpenseDTO;
import com.example.product_api.entity.ExpenseEntity;
import com.example.product_api.entity.ProfileEntity;
import com.example.product_api.io.ExpenseRequest;
import com.example.product_api.io.ExpenseResponse;
import com.example.product_api.repositories.ExpenseRepository;
import com.example.product_api.service.ExpenseService;
import com.example.product_api.service.impl.ExpenseServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses() {
        log.info("API GET /expenses called");

        // Call the service method

        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Printing the data from service {}", list);
        // Convert the Expense DTO to Expense Response
        return list.stream().map(expenseDTO -> mapToExpenseResponse(expenseDTO))
                .collect(Collectors.toList());
    }

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId) {
        log.info("API GET /expenses/{} called", expenseId);
        ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId(expenseId);
        log.info("Printing the expense details {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    /**
     * It will delete the expense from database
     * 
     * @param expenseId
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseByExpenseId(@PathVariable String expenseId) {
        log.info("API DELETE /expenses/{} called", expenseId);
        expenseService.deleteExpenseByExpenseId(expenseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        log.info("API POST /expenses called {}", expenseRequest);
        ExpenseDTO expenseDTO = mapToExpenseDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        log.info("Printing the expense dto {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    /**
     * It will update the expense details to database
     * 
     * @param updateRequest`
     * @param expenseId
     * @return ExpenseResponse
     */
    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@Valid @RequestBody ExpenseRequest updateRequest,
            @PathVariable String expenseId) {
        log.info("API PUT /expenses/{} request body {}", expenseId, updateRequest);
        ExpenseDTO updatedExpenseDTO = mapToExpenseDTO(updateRequest);
        updatedExpenseDTO = expenseService.updateExpenseDetails(updatedExpenseDTO, expenseId);
        log.info("Printing the updated expense dto details {}", updatedExpenseDTO);
        return mapToExpenseResponse(updatedExpenseDTO);
    }

    /**
     * Mapper method to map values from Expense request to expense dto
     * 
     * @param expenseRequest
     * @return ExpenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseRequest expenseRequest) {
        return modelMapper.map(expenseRequest, ExpenseDTO.class);
    }

    /**
     * Mapper method for converting expense dto object to expense response
     * 
     * @param expenseDTO
     * @return ExpenseResponse
     */
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }
}
