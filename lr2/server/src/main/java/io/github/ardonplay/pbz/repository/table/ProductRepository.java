package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
