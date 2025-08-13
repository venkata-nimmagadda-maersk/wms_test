package com.example.ProductDemo.InventoryTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionRepo extends JpaRepository<InventoryTransaction, Integer> {
}
