package io.github.ardonplay.pbz.model.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "type", referencedColumnName = "id")
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    private List<WaybillProduct> waybillProducts;
}