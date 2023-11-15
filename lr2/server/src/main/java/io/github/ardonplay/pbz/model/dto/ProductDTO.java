package io.github.ardonplay.pbz.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private String type;
    private String name;
    private int id;

    private int count;
}
