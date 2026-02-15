package com.arturk.service;

import com.arturk.AbstractIntegrationTest;
import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.dto.response.OrderRespone;
import com.arturk.utils.InventoryStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.math.BigDecimal;
import java.util.UUID;

@AutoConfigureWireMock(port = 0)
public class OrderServiceTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderWhenProductIsInStock() {
        //given
        String skuCode = "APL-IP17PRO-512GB-BLACK";
        Integer quantity = 3;

        OrderRequest request = orderRequest(skuCode, new BigDecimal("710.99"), quantity);
        InventoryStubs.productIsInStock(skuCode, quantity);

        //when
        OrderRespone response = orderService.saveOrder(request);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals("APL-IP17PRO-512GB-BLACK", response.getSkuCode());

        Assertions.assertEquals(1, getOrderRepository().count());
    }

    @Test
    public void createOrderWhenProductIsOutOfStock() {
        //given
        String skuCode = "APL-IP17PRO-512GB-BLACK";
        Integer quantity = 3;

        OrderRequest request = orderRequest(skuCode, new BigDecimal("710.99"), quantity);
        InventoryStubs.productIsOutOfStock(skuCode, quantity);

        //when
        //then
        Assertions.assertThrows(RuntimeException.class, () -> orderService.saveOrder(request));

        Assertions.assertEquals(0, getOrderRepository().count());
    }

    private OrderRequest orderRequest(String skuCode, BigDecimal price, Integer quantity) {
        return OrderRequest.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(skuCode)
                .price(price)
                .quantity(quantity)
                .build();
    }

}
