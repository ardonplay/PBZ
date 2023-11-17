package io.github.ardonplay.pbz.model.table;

import io.github.ardonplay.pbz.model.enums.PersonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "type", columnDefinition = "person_type")
    @Enumerated(EnumType.STRING)
    @NonNull
    private PersonType type;

    @Column(name = "adress")
    @NonNull
    private String adress;

    @Column(name = "phone_number")
    @NonNull
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "customer")
    private BankDetails bankDetails;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "customer")
    private List<Waybill> waybills;
}
