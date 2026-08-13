package com.example.product_api.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.product_api.dto.ExpenseDto;
import com.example.product_api.dto.ProductResponse;
import com.example.product_api.entity.Product;
import com.example.product_api.mapper.ProductMapper;
import com.example.product_api.repositories.ProductRepository;
import com.example.product_api.service.interfaces.IProductService;

public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse create(ExpenseDto request) {
        // Product product = productMapper.toEntity(request);
        // Product savedProduct = productRepository.save(product);
        // return productMapper.toResponse(savedProduct);
        return null;
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        // Product product = productRepository.findById(id)
        // .orElseThrow(() -> new ResourceNotFoundException("Product not found with id:
        // " + id));
        // return productMapper.toResponse(product);
        return null;
    }

    @Override
    public void delete(Long id) {
        // if (!productRepository.existsById(id)) {
        // throw new ResourceNotFoundException("Product not found with id: " + id);
        // }
        // productRepository.deleteById(id);

    }
}