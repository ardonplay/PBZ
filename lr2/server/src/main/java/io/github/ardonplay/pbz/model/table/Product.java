package io.github.ardonplay.pbz.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "type", referencedColumnName = "id")
    private ProductType productType;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "product")
    private List<WaybillProduct> waybillProducts;
}