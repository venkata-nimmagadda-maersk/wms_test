package com.example.ProductDemo.InventoryHold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryHoldRepo extends JpaRepository<InventoryHold, Integer> {

}
