package com.example.ProductDemo.InventoryTransfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransferRepo extends JpaRepository<InventoryTransfer,Integer> {

    @Procedure(procedureName = "PerformInventoryTransfer")
    void performInventoryTransfer(
        @Param("transferid") int transferId,
        @Param("productid") int productId,
        @Param("from_warehouseid") int fromWarehouseId,
        @Param("to_warehouseid") int toWarehouseId,
        @Param("quantity") int quantity,
        @Param("transfer_date") java.sql.Date transferDate,
        @Param("reference_note") String referenceNote
    );
}
