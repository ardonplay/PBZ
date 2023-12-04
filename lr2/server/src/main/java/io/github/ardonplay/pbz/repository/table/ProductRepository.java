package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Procedure(procedureName = "add_product")
    Integer addProduct(@Param("product_name") String name, @Param("product_type_name") String type);

    @Procedure(procedureName = "delete_product")
    void deleteProduct(@Param("product_id") Integer id);

    @Procedure(procedureName = "update_product")
    void updateProduct(@Param("product_id") Integer id, @Param("product_name") String name, @Param("product_type_name") String type);
}
