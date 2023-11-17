package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);
}
