package io.github.ardonplay.pbz.model.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "waybil_products")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class WaybillProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "quantity")
    private Integer count;

    @NonNull
    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "waybil_id", referencedColumnName = "id")
    private Waybill waybill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaybillProduct that = (WaybillProduct) o;
        return Objects.equals(id, that.id) && Objects.equals(count, that.count) && Objects.equals(price, that.price) && Objects.equals(product, that.product) && Objects.equals(waybill, that.waybill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, price, product, waybill);
    }
}
