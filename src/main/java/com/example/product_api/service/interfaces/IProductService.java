package com.example.product_api.service.interfaces;

import java.util.List;

import com.example.product_api.dto.ExpenseDto;
import com.example.product_api.dto.ProductResponse;

public interface IProductService {

    ProductResponse create(ExpenseDto request);

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    void delete(Long id);
}