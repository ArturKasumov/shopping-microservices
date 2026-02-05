package com.arturk.controller;

import com.arturk.model.dto.request.OrderRequest;
import com.arturk.model.dto.response.OrderRespone;
import com.arturk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRespone saveOrder(@RequestBody OrderRequest request) {
        return orderService.saveOrder(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderRespone> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }
}
