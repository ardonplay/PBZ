package io.github.ardonplay.pbz.model.table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @ColumnTransformer(write = "?::person_type")
    @NonNull
    private PersonType type;

    @Column(name = "adress")
    @NonNull
    private String adress;

    @Column(name = "phone_number")
    @NonNull
    private String phoneNumber;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private BankDetails bankDetails;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Waybill> waybills;
}
