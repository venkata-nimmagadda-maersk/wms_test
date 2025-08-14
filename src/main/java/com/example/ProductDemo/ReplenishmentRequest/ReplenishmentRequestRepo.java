package com.example.ProductDemo.ReplenishmentRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRequestRepo extends JpaRepository<ReplenishmentRequest, Integer> {

    @Procedure(name = "CreateReplenishmentRequest")
    void createReplenishmentRequest(
        @Param("requestid") Integer requestId,
        @Param("productid") Integer productId,
        @Param("from_warehouseid") Integer fromWarehouseId,
        @Param("to_warehouseid") Integer toWarehouseId,
        @Param("quantity_requested") Integer quantityRequested,
        @Param("request_date") java.sql.Date requestDate,
        @Param("status") String status
    );

}
