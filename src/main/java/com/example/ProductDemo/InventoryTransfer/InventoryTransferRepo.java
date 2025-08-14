package com.example.ProductDemo.InventoryTransfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransferRepo extends JpaRepository<InventoryTransfer,Integer> {
    @Procedure(value = "PerformInventoryTransfer")
    void performInventoryTransfer(
        @Param("transferid") Integer transferId,
        @Param("productid") Integer productId,
        @Param("from_warehouseid") Integer fromWarehouseId,
        @Param("to_warehouseid") Integer toWarehouseId,
        @Param("quantity") Integer quantity,
        @Param("transfer_date") java.sql.Date transferDate,
        @Param("reference_note") String referenceNote
    );
}
