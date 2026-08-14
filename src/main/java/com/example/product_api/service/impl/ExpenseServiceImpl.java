package com.example.product_api.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.product_api.dto.ExpenseDTO;
import com.example.product_api.entity.ExpenseEntity;
import com.example.product_api.repositories.ExpenseRepository;
import com.example.product_api.service.AuthService;
import com.example.product_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return null;
    }

    /**
     * It will fetch the single expense details from database
     * 
     * @param expenseId
     * @return ExpenseDTO
     */

    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        return null;
    }

    /**
     * It will delete the expense from database
     * 
     * @param expenseId
     * @return void
     */
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
    }

    /**
     * It will save the expense details to database
     * 
     * @param expenseDTO
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        return null;
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        return null;
    }

    /**
     * Mapper method to map values from Expense dto to Expense entity
     * 
     * @param expenseDTO
     * @return ExpenseEntity
     */
    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseEntity.class);
    }

    /**
     * Mapper method to convert expense entity to expense DTO
     * 
     * @param expenseEntity
     * @return ExpenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }

    /**
     * Fetch the expense by expense id from database
     * 
     * @param expenseId
     * @return ExpenseEntity
     */
    private ExpenseEntity getExpenseEntity(String expenseId) {
        return null;
    }
}
