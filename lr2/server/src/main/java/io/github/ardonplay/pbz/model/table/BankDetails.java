package io.github.ardonplay.pbz.model.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_details")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    @NonNull
    private String number;

    @Column(name = "bank_name")
    @NonNull
    private String name;

    @OneToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

}
