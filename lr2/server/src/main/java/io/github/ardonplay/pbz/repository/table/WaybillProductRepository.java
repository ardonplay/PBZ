package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Product;
import io.github.ardonplay.pbz.model.table.WaybillProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaybillProductRepository extends JpaRepository<WaybillProduct, Integer> {

    List<WaybillProduct> findAllByProduct(Product product);
}
