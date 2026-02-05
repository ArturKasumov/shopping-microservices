package com.arturk.service.mapper;

import com.arturk.common.annotation.Mapper;
import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.dto.response.OrderRespone;
import com.arturk.model.entity.OrderEntity;

@Mapper
public class OrderMapper {

    public OrderEntity fromOrderRequest(OrderRequest request) {
        return OrderEntity.builder()
                .orderNumber(request.getOrderNumber())
                .skuCode(request.getSkuCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public OrderRespone toOrderResponse(OrderEntity entity) {
        return OrderRespone.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .skuCode(entity.getSkuCode())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
