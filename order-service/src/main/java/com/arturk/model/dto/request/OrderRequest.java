package com.arturk.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    private String orderNumber;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;
}
