package com.example.ProductDemo.Adjustment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/inventory-adjustments")
public class InventoryAdjustmentController {

    @Autowired
    private InventoryAdjustmentService inventoryAdjustmentService;

    @GetMapping
    public ResponseEntity<List<InventoryAdjustment>> getAllAdjustments() {
        return ResponseEntity.ok(inventoryAdjustmentService.getAllAdjustments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryAdjustment> getAdjustmentById(@PathVariable int id) {
        return inventoryAdjustmentService.getAdjustmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryAdjustment> createAdjustment(@RequestBody InventoryAdjustment adjustment) {
        InventoryAdjustment created = inventoryAdjustmentService.saveAdjustment(adjustment);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryAdjustment> updateAdjustment(@PathVariable int id, @RequestBody InventoryAdjustment adjustment) {
        return inventoryAdjustmentService.getAdjustmentById(id)
                .map(existing -> {
                    adjustment.setAdjustmentID(id);
                    InventoryAdjustment updated = inventoryAdjustmentService.saveAdjustment(adjustment);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllAdjustments() {
        inventoryAdjustmentService.deleteAllAdjustments();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdjustment(@PathVariable int id) {
        if (inventoryAdjustmentService.getAdjustmentById(id).isPresent()) {
            inventoryAdjustmentService.deleteAdjustment(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/procedure")
    public ResponseEntity<Void> adjustInventoryWithProcedure(@RequestBody(required = true) InventoryAdjustment adjustment) {
        if (adjustment == null) {
            return ResponseEntity.badRequest().build();
        }
        inventoryAdjustmentService.adjustInventoryWithProcedure(adjustment);
        return ResponseEntity.ok().build();
    }


}
