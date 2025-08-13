package com.example.ProductDemo.Adjustment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAdjustmentRepo extends JpaRepository<InventoryAdjustment,Integer> {

    @Procedure(procedureName = "AdjustInventory")
    void adjustInventory(
        @Param("productid") int productId,
        @Param("warehouseid") int warehouseId,
        @Param("adjustment_type") String adjustmentType,
        @Param("quantity_change") int quantityChange,
        @Param("adjustment_date") java.sql.Date adjustmentDate,
        @Param("reason") String reason
    );
}
