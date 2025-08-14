package com.example.ProductDemo.InventoryTransfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransferRepo extends JpaRepository<InventoryTransfer,Integer> {


}
