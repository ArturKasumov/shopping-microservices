package com.arturk.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRespone {

    private Long id;

    private String orderNumber;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;
}
