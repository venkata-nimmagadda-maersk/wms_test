package com.example.ProductDemo.Adjustment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryAdjustmentService {

    @Autowired
    private InventoryAdjustmentRepo inventoryAdjustmentRepo;

    public List<InventoryAdjustment> getAllAdjustments() {
        return inventoryAdjustmentRepo.findAll();
    }

    public Optional<InventoryAdjustment> getAdjustmentById(int id) {
        return inventoryAdjustmentRepo.findById(id);
    }

    public InventoryAdjustment saveAdjustment(InventoryAdjustment adjustment) {
        return inventoryAdjustmentRepo.save(adjustment);
    }
    public void deleteAllAdjustments() {
        inventoryAdjustmentRepo.deleteAll();
    }
    public void deleteAdjustment(int id) {
        inventoryAdjustmentRepo.deleteById(id);
    }
    public void adjustInventoryWithProcedure(InventoryAdjustment adjustment) {
        inventoryAdjustmentRepo.adjustInventory(
            adjustment.getAdjustmentID(),
            adjustment.getProduct().getProdId(),
            adjustment.getWarehouse().getWarehouseID(),
            adjustment.getAdjustmentType(),
            adjustment.getQuantityChange(),
            new java.sql.Date(adjustment.getAdjustmentDate().getTime()),
            adjustment.getReason()
        );
    }


}
