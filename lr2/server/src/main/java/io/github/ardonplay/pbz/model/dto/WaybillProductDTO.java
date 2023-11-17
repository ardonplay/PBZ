package io.github.ardonplay.pbz.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WaybillProductDTO {
    private Integer count;
    private BigDecimal price;
    private ProductDTO product;
}
