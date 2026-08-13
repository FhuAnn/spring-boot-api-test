package com.example.product_api.mapper;

import org.springframework.stereotype.Component;

import com.example.product_api.dto.ExpenseDto;
import com.example.product_api.dto.ProductResponse;
import com.example.product_api.entity.Product;

@Component
public class ProductMapper {
    public Product toEntity(ExpenseDto request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getCreatedAt());
    }
}