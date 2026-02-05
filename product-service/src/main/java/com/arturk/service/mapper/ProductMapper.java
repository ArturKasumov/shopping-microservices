package com.arturk.service.mapper;

import com.arturk.common.annotation.Mapper;
import com.arturk.model.dto.request.ProductRequest;
import com.arturk.model.dto.response.ProductResponse;
import com.arturk.model.entity.ProductEntity;

@Mapper
public class ProductMapper {

    public ProductEntity fromProductRequest(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.getName())
                .skuCode(request.getSkuCode())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

    }

    public ProductResponse toProductResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .skuCode(entity.getSkuCode())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .build();

    }
}
