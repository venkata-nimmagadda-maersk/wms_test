package com.example.ProductDemo.Putaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PutawayRepo extends JpaRepository<Putaway, Integer> {
    @Procedure(procedureName = "PutawayStock")
    void putawayStock(
        @Param("poid") int poid,
        @Param("productid") int productId,
        @Param("warehouseid") int warehouseId,
        @Param("quantity_putaway") int quantityPutaway,
        @Param("putaway_date") java.sql.Date putawayDate
    );
}
