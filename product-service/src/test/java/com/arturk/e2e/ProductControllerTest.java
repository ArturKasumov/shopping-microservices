package com.arturk.e2e;

import com.arturk.model.entity.ProductEntity;
import com.arturk.repository.ProductRepository;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Container
    @ServiceConnection
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8");

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldCreateProduct() {
        String json = """
                {
                   "name" : "Iphone 17 Pro",
                   "skuCode" : "APL-IP17PRO-512GB-BLACK",
                   "description" : "Apple smartphone",
                   "price" : 730.99
                 }
                """;

        given()
                .baseUri("http://127.0.0.1")
                .port(serverPort)
                .contentType(ContentType.JSON)
                .body(json)
        .when()
                .post("/product")
        .then()
                .statusCode(201)
                .body("name", equalTo("Iphone 17 Pro"))
                .body("skuCode", equalTo("APL-IP17PRO-512GB-BLACK"));
    }

    @Test
    public void shouldGetProducts() {
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .name("Iphone 17 Pro Max")
                .skuCode("APL-IP17PRO-MAX-512GB-BLACK")
                .description("Apple smartphone")
                .price(new BigDecimal("860.99"))
                .build();
        productRepository.save(productEntity);

        //when
        given()
                .baseUri("http://127.0.0.1")
                .port(serverPort)
        .when()
                .get("/product")
        .then()
                .statusCode(200)
                .body("$.size()", equalTo(1))
                .body("[0].name", equalTo("Iphone 17 Pro Max"))
                .body("[0].skuCode", equalTo("APL-IP17PRO-MAX-512GB-BLACK"));
    }
}
