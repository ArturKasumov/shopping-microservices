package com.arturk.service;

import com.arturk.model.dto.request.ProductRequest;
import com.arturk.model.dto.response.ProductResponse;
import com.arturk.model.entity.ProductEntity;
import com.arturk.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Container
    @ServiceConnection
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8");

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldCreateProduct() {
        // given
        ProductRequest request = ProductRequest.builder()
                .name("Iphone 17 Pro")
                .skuCode("APL-IP17PRO-512GB-BLACK")
                .description("Apple smartphone")
                .price(new BigDecimal("730.99"))
                .build();

        //when
        ProductResponse response = productService.createProduct(request);

        //then
        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals("Iphone 17 Pro", response.getName());
    }

    @Test
    public void shouldGetProducts() {
        //give
        ProductEntity productEntity = ProductEntity.builder()
                .name("Iphone 17 Pro Max")
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .description("Apple smartphone")
                .price(new BigDecimal("860.99"))
                .build();
        productRepository.save(productEntity);

        //when
        List<ProductResponse> response = productService.getProducts();

        //then
        Assertions.assertEquals(1, response.size());
        Assertions.assertTrue(response.stream().anyMatch(product -> "Iphone 17 Pro Max".equals(product.getName())));
    }
}
