package io.github.ardonplay.pbz.model.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "waybills")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Waybill {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @NonNull
    @Column(name = "date")
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private Destination destination;

    @OneToMany(mappedBy = "waybill")
    private List<WaybillProduct> waybillProducts;
}
