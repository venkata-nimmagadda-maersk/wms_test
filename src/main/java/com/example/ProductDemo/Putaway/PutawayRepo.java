package com.example.ProductDemo.Putaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PutawayRepo extends JpaRepository<Putaway, Integer> {
    @Procedure(value = "PutawayStock")
    void putawayStock(
        @Param("putawayid") Integer putawayId,
        @Param("poid") Integer poid,
        @Param("productid") Integer productId,
        @Param("warehouseid") Integer warehouseId,
        @Param("quantity_putaway") Integer quantityPutaway,
        @Param("putaway_date") java.sql.Date putawayDate
    );
}
