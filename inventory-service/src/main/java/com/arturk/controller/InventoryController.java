package com.arturk.controller;

import com.arturk.model.dto.request.InventoryRequest;
import com.arturk.model.dto.response.InventoryResponse;
import com.arturk.service.InventoryService;
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
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory API", description = "API for managing inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(
            summary = "Check product availability",
            description = "Checks if the requested quantity of a product is available in stock"
    )
    @GetMapping("/{skuCode}/availability")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@PathVariable String skuCode,
                             @RequestParam Integer quantity
    ) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @Operation(
            summary = "Create inventory record",
            description = "Creates a new inventory record for a product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory successfully created",
                    content = @Content(schema = @Schema(implementation = InventoryResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.createInventory(inventoryRequest);
    }

    @Operation(
            summary = "Add quantity to existing inventory",
            description = "Adds specified quantity to the product stock"
    )
    @PostMapping("/{skuCode}/add")
    @ResponseStatus(HttpStatus.OK)
    public void addToInventory(@PathVariable String skuCode,
                               @RequestParam Integer quantity
    ) {
        inventoryService.addProductQuantity(skuCode, quantity);
    }

    @Operation(
            summary = "Get all inventory records",
            description = "Returns paginated list of inventory records"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventories(Pageable pageable) {
        return inventoryService.getInventories(pageable);
    }
}
