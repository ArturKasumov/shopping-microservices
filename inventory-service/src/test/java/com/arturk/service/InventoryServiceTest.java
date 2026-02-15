package com.arturk.service;

import com.arturk.model.dto.response.InventoryResponse;
import com.arturk.model.entity.InventoryEntity;
import com.arturk.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
public class InventoryServiceTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18");

    @BeforeEach
    public void cleanDatabase() {
        inventoryRepository.deleteAll();
    }

    @Test
    public void productIsInStock() {
        //given
        InventoryEntity inventory = InventoryEntity.builder()
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .quantity(5)
                .build();
        inventoryRepository.save(inventory);

        //when
        boolean isInStock  = inventoryService.isInStock("APL-IP17PRO-MAX-512GB-BLACK", 5);

        // then
        Assertions.assertTrue(isInStock);
    }

    @Test
    public void productIsNotInStock() {
        //given
        InventoryEntity inventory = InventoryEntity.builder()
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .quantity(5)
                .build();
        inventoryRepository.save(inventory);

        //when
        boolean isInStock  = inventoryService.isInStock("APL-IP17PRO-MAX-512GB-BLACK", 6);

        // then
        Assertions.assertFalse(isInStock);
    }

    @Test
    public void addProductQuantity() {
        //given
        InventoryEntity inventory = InventoryEntity.builder()
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .quantity(5)
                .build();
        inventoryRepository.save(inventory);

        //when
        inventoryService.addProductQuantity("APL-IP17PRO-MAX-512GB-BLACK", 10);

        // then
        inventory = inventoryRepository.findBySkuCode("APL-IP17PRO-MAX-512GB-BLACK");
        Assertions.assertEquals( 15, inventory.getQuantity());
    }

    @Test
    public void addProductQuantity_noSuchProduct() {

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> inventoryService.addProductQuantity("APL-IP17PRO-MAX-512GB-BLACK", 10)
        );
    }

    @Test
    public void shouldReturnInventories() {
        //given
        InventoryEntity inventory = InventoryEntity.builder()
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .quantity(5)
                .build();
        inventoryRepository.save(inventory);

        //when
        List<InventoryResponse> result = inventoryService.getInventories(PageRequest.of(0, 10));

        //then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("APL-IP17PRO-MAX-512GB-BLACK", result.get(0).getSkuCode());
    }
}
