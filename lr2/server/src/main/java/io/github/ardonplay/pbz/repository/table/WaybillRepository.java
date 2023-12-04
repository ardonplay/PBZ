package io.github.ardonplay.pbz.repository.table;

import io.github.ardonplay.pbz.model.table.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;
@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Integer> {
    @Procedure(procedureName = "add_new_waybill")
    Integer addNewWaybill(
            @Param("customer_id") UUID customerId,
            @Param("date_param")  Date dateParam,
            @Param("destination_id") Integer destinationId,
            @Param("products_data") String productsData
    );

    @Procedure(procedureName = "update_waybill")
    void updateWaybill(
            @Param("waybill_id") Integer waybillId,
            @Param("customer_id") UUID customerId,
            @Param("date_param")  Date dateParam,
            @Param("destination_id") Integer destinationId,
            @Param("products_data") String productsData
    );

    @Procedure(procedureName = "delete_waybill")
    void deleteWaybill(@Param("waybill_id") Integer waybillId);
}
