package com.arturk.controller;

import com.arturk.AbstractIntegrationTest;
import com.arturk.client.InventoryClient;
import com.arturk.model.entity.OrderEntity;
import com.arturk.repository.OrderRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class OrderControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryClient inventoryClient;

    @Test
    @SneakyThrows
    public void shouldCreateOrder() {

        //given
       String json = """
               {
                 "orderNumber": "ORD-2026-0001",
                 "skuCode": "APL-IP17PRO-MAX-512GB-BLACK",
                 "price": 860.99,
                 "quantity": 1
               }
               """;

       when(inventoryClient.getProductAvailability("APL-IP17PRO-MAX-512GB-BLACK", 1))
               .thenReturn(true);

       //when
       mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.skuCode").value("APL-IP17PRO-MAX-512GB-BLACK"));
    }

    @Test
    @SneakyThrows
    public void shouldGetOrders() {

        //given
        OrderEntity order = OrderEntity.builder()
                .orderNumber("ORD-2026-0002")
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .price(new BigDecimal("860.99"))
                .quantity(1)
                .build();

        getOrderRepository().save(order);

        //when
        mockMvc.perform(get("/api/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].skuCode").value("APL-IP17PRO-MAX-512GB-BLACK"));
    }
}
