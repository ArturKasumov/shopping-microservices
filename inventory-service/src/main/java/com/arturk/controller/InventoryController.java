package com.arturk.controller;

import com.arturk.model.dto.request.InventoryRequest;
import com.arturk.model.dto.response.InventoryResponse;
import com.arturk.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}/availability")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@PathVariable String skuCode,
                             @RequestParam Integer quantity
    ) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.createInventory(inventoryRequest);
    }

    @PostMapping("/{skuCode}/add")
    @ResponseStatus(HttpStatus.OK)
    public void addToInventory(@PathVariable String skuCode,
                               @RequestParam Integer quantity
    ) {
        inventoryService.addProductQuantity(skuCode, quantity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventories(Pageable pageable) {
        return inventoryService.getInventories(pageable);
    }
}
