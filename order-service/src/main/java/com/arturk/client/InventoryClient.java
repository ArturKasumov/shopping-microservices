package com.arturk.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory-client", url = "${clients.inventory.base-url}")
public interface InventoryClient {

    @GetMapping("/{skuCode}/availability")
    boolean getProductAvailability(@PathVariable String skuCode, @RequestParam Integer quantity);
}
