package io.github.ardonplay.pbz.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String type;
}
