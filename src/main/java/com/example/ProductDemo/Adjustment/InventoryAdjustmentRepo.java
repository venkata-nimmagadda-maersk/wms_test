package com.example.ProductDemo.Adjustment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAdjustmentRepo extends JpaRepository<InventoryAdjustment,Integer> {


}
