package com.example.ProductDemo.Adjustment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAdjustmentRepo extends JpaRepository<InventoryAdjustment,Integer> {
    @Procedure(name = "AdjustInventory")
    void adjustInventory(
        @Param("adjustmentid") Integer adjustmentId,
        @Param("productid") Integer productId,
        @Param("warehouseid") Integer warehouseId,
        @Param("adjustment_type") String adjustmentType,
        @Param("quantity_change") Integer quantityChange,
        @Param("adjustment_date") java.sql.Date adjustmentDate,
        @Param("reason") String reason
    );
}
