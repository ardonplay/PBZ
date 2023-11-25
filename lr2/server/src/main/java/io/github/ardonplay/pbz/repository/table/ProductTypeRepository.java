package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
   ProductType findByType(String type);
}
