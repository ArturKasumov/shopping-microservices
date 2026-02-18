package com.arturk.controller;

import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.dto.response.OrderRespone;
import com.arturk.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "API for managing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create new order",
            description = "Creates a new order and returns order details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created",
                    content = @Content(schema = @Schema(implementation = OrderRespone.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRespone saveOrder(@RequestBody OrderRequest request) {
        return orderService.saveOrder(request);
    }

    @Operation(
            summary = "Get all orders",
            description = "Returns paginated list of orders"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderRespone> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }
}
