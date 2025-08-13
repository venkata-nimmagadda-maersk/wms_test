package com.example.ProductDemo.ReplenishmentRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRequestRepo extends JpaRepository<ReplenishmentRequest, Integer> {

    @Procedure(procedureName = "CreateReplenishmentRequest")
    void createReplenishmentRequest(
        @Param("productid") int productId,
        @Param("from_warehouseid") int fromWarehouseId,
        @Param("to_warehouseid") int toWarehouseId,
        @Param("quantity_requested") int quantityRequested,
        @Param("request_date") java.sql.Date requestDate,
        @Param("status") String status
    );

    @Procedure(procedureName = "FulfillReplenishmentRequest")
    void fulfillReplenishmentRequest(@Param("requestid") int requestId);
}
