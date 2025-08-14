package com.example.ProductDemo.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.function.UnaryOperator;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {
    @Procedure(name = "CreatePurchaseOrder")
    void createPurchaseOrder(
        @Param("poid") Integer poid,
        @Param("supplier_name") String supplierName,
        @Param("order_date") java.sql.Date orderDate,
        @Param("expected_delivery_date") java.sql.Date expectedDeliveryDate,
        @Param("status") String status,
        @Param("product_id") Integer productId,
        @Param("quantity_ordered") Integer quantityOrdered
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE dbo.purchase_order SET status = 'Received' WHERE poid = :poid", nativeQuery = true)
    void updateStatusToReceived(@Param("poid") Integer poid);
}
