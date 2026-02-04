package com.arturk.service.mapper;

import com.arturk.model.dto.request.ProductRequest;
import com.arturk.model.dto.response.ProductResponse;
import com.arturk.model.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity fromProductRequest(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

    }

    public ProductResponse toProductResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .build();

    }
}
