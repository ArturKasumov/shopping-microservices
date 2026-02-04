package com.arturk.service;

import com.arturk.model.dto.request.ProductRequest;
import com.arturk.model.dto.response.ProductResponse;
import com.arturk.model.entity.ProductEntity;
import com.arturk.repository.ProductRepository;
import com.arturk.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = productMapper.fromProductRequest(productRequest);
        productEntity = productRepository.save(productEntity);
        log.info("Product created successfully - id: {}, name: {}", productEntity.getId(), productEntity.getName());
        return productMapper.toProductResponse(productEntity);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
