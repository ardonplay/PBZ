package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer> {
}
