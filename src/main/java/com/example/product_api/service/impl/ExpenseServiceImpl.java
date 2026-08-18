package com.example.product_api.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.product_api.dto.ExpenseDTO;
import com.example.product_api.entity.ExpenseEntity;
import com.example.product_api.entity.ProfileEntity;
import com.example.product_api.exceptions.ResourceNotFoundException;
import com.example.product_api.repositories.ExpenseRepository;
import com.example.product_api.service.AuthService;
import com.example.product_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Service implementation for Expense module
 * 
 * @author FhuAnn
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    /**
     * It will fetch the expenses from database
     * 
     * @return list
     */

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // Call the repository method
        Long loggedInProfileId = authService.getLoggedInProfile().getId();
        List<ExpenseEntity> list = expenseRepository.findByOwnerId(loggedInProfileId);
        log.info("Printing the dataa from repositoru {}", list);
        // Convert the list of expense entity to list of expense DTO
        List<ExpenseDTO> expenseDTOList = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).toList();
        return expenseDTOList;
    }

    /**
     * It will fetch the single expense details from database
     * 
     * @param expenseId
     * @return ExpenseDTO
     */

    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense entity details {}", expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }

    /**
     * It will delete the expense from database
     * 
     * @param expenseId
     * @return void
     */
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense entity details {}", expenseEntity);
        expenseRepository.delete(expenseEntity);
    }

    /**
     * It will save the expense details to database
     * 
     * @param expenseDTO
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ProfileEntity loggedInProfile = authService.getLoggedInProfile();
        ExpenseEntity newExpenseEntity = mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity.setOwner(loggedInProfile);
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        log.info("Printing the new expense entity details {}", newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);
        ExpenseEntity updatedExpenseEntity = mapToExpenseEntity(expenseDTO);
        updatedExpenseEntity.setId(existingExpense.getId());
        updatedExpenseEntity.setExpenseId(existingExpense.getExpenseId());
        updatedExpenseEntity.setCreatedAt(existingExpense.getCreatedAt());
        updatedExpenseEntity.setUpdatedAt(existingExpense.getUpdatedAt());
        updatedExpenseEntity.setOwner(authService.getLoggedInProfile());
        updatedExpenseEntity = expenseRepository.save(updatedExpenseEntity);
        log.info("Printing the updated expense entity details {}", updatedExpenseEntity);
        return mapToExpenseDTO(updatedExpenseEntity);
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
        Long id = authService.getLoggedInProfile().getId();
        return expenseRepository.findByOwnerIdAndExpenseId(id, expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id " + expenseId));
    }
}
