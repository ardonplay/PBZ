package io.github.ardonplay.pbz.model.table;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name="max_waybills_per_date")
@Getter
public class MaxWaybillPerDateView {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "waybill_date")
    private Date date;

    @Column(name = "total_price", precision = 18, scale = 2)
    private BigDecimal price;

}
