package com.arturk.controller;

import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.entity.OrderEntity;
import com.arturk.repository.OrderRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Testcontainers
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18");

    @BeforeEach
    public void cleanDatabase() {
        orderRepository.deleteAll();
    }

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
        orderRepository.save(order);

        mockMvc.perform(get("/api/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].skuCode").value("APL-IP17PRO-MAX-512GB-BLACK"));
    }
}
