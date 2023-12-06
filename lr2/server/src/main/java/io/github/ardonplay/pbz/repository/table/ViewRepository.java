package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.dto.DatePriceProductDTO;
import io.github.ardonplay.pbz.model.table.MaxWaybillPerDateView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<MaxWaybillPerDateView, Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM find_price_by_product_id(:product_id, :start_date, :end_date)"
    )
    List<Object[]> findPriceByProductId(@Param("product_id") Integer productId, @Param("start_date") Date startDate, @Param("end_date") Date endDate);


    @Query(
            nativeQuery = true,
            value = "SELECT * FROM get_waybills_with_product(:product_id)"
    )
    List<Object[]> getWaybillWithProduct(@Param("product_id") Integer productId);
}
