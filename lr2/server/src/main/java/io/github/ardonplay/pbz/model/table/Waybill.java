package io.github.ardonplay.pbz.model.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "waybills")
@Getter
@Setter
public class Waybill {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @NonNull
    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private Destination destination;

    @OneToMany(cascade = {CascadeType.MERGE}, orphanRemoval = true, mappedBy = "waybill")
    private Set<WaybillProduct> waybillProducts = new HashSet<>();

}
