package com.arturk.service;

import com.arturk.client.InventoryClient;
import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.dto.response.OrderRespone;
import com.arturk.model.entity.OrderEntity;
import com.arturk.repository.OrderRepository;
import com.arturk.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;

    public OrderRespone saveOrder(OrderRequest orderRequest) {
        if (!isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity())) {
            throw new RuntimeException(String.format("Product with skuCode: %s is out of stock", orderRequest.getSkuCode()));
        }

        OrderEntity orderEntity = orderMapper.fromOrderRequest(orderRequest);
        orderEntity = orderRepository.save(orderEntity);

        log.info("Order - orderNumber: {} is saved", orderEntity.getOrderNumber());
        return orderMapper.toOrderResponse(orderEntity);
    }

    public List<OrderRespone> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponse)
                .stream()
                .toList();
    }

    private boolean isInStock(String skuCode, Integer quantity) {
        return inventoryClient.getProductAvailability(skuCode, quantity);
    }
}
