package com.example.ProductDemo.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {
    @Procedure(procedureName = "CreatePurchaseOrder")
    void createPurchaseOrder(
        @Param("supplier_name") String supplierName,
        @Param("order_date") java.sql.Date orderDate,
        @Param("expected_delivery_date") java.sql.Date expectedDeliveryDate,
        @Param("status") String status
    );

    @Procedure(procedureName = "ReceivePurchaseOrder")
    void receivePurchaseOrder(@Param("poid") int poid);
}
