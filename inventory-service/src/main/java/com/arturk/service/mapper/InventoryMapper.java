package com.arturk.service.mapper;

import com.arturk.common.annotation.Mapper;
import com.arturk.model.dto.request.InventoryRequest;
import com.arturk.model.dto.response.InventoryResponse;
import com.arturk.model.entity.InventoryEntity;

@Mapper
public class InventoryMapper {

    public InventoryEntity fromInventoryRequest(InventoryRequest request) {
        return InventoryEntity.builder()
                .skuCode(request.getSkuCode())
                .quantity(request.getQuantity())
                .build();
    }

    public InventoryResponse toInventoryResponse(InventoryEntity entity) {
        return InventoryResponse.builder()
                .id(entity.getId())
                .skuCode(entity.getSkuCode())
                .quantity(entity.getQuantity())
                .build();
    }
}
