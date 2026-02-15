package com.arturk.service;

import com.arturk.model.dto.request.InventoryRequest;
import com.arturk.model.dto.response.InventoryResponse;
import com.arturk.model.entity.InventoryEntity;
import com.arturk.repository.InventoryRepository;
import com.arturk.service.mapper.InventoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public Boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public void addProductQuantity(String skuCode, Integer quantity) {
        InventoryEntity inventory = inventoryRepository.findBySkuCode(skuCode);
        if (inventory == null) {
            throw new EntityNotFoundException("No product with skuCode: " + skuCode);
        }
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    public List<InventoryResponse> getInventories(Pageable pageable) {
        return inventoryRepository.findAll(pageable)
                .map(inventoryMapper::toInventoryResponse).toList();
    }

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        InventoryEntity inventory = inventoryMapper.fromInventoryRequest(inventoryRequest);
        inventoryRepository.save(inventory);
        return inventoryMapper.toInventoryResponse(inventory);
    }
}
