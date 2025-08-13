package com.example.ProductDemo.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepo extends JpaRepository<PurchaseOrderItem, Integer> {

}
