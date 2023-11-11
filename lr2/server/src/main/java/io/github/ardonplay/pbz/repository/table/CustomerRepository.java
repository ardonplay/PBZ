package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
