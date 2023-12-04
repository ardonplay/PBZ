package io.github.ardonplay.pbz.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DatePriceProductDTO {
    private String name;

    private Date date;

    private BigDecimal price;

}
