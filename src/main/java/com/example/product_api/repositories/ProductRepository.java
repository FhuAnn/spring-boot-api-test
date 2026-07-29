package com.example.product_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}