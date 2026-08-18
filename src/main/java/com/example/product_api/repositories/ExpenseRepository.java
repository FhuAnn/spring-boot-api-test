package com.example.product_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_api.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    /**
     * It will find the single expense from database
     * 
     * @param expenseId
     * @return Optional
     */

    Optional<ExpenseEntity> findByExpenseId(String expenseId);

    List<ExpenseEntity> findByOwnerId(Long ownerId);

    Optional<ExpenseEntity> findByOwnerIdAndExpenseId(Long id, String expenseId);
}
