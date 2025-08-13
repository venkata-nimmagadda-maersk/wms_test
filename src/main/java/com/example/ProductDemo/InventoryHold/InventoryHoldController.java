package com.example.ProductDemo.InventoryHold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/inventory-holds")
public class InventoryHoldController {

    @Autowired
    private InventoryHoldService inventoryHoldService;

    @GetMapping
    public ResponseEntity<List<InventoryHold>> getAllHolds() {
        return ResponseEntity.ok(inventoryHoldService.getAllHolds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryHold> getHoldById(@PathVariable int id) {
        return inventoryHoldService.getHoldById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryHold> createHold(@RequestBody InventoryHold hold) {
        InventoryHold created = inventoryHoldService.saveHold(hold);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryHold> updateHold(@PathVariable int id, @RequestBody InventoryHold hold) {
        return inventoryHoldService.getHoldById(id)
                .map(existing -> {
                    hold.setHoldID(id);
                    InventoryHold updated = inventoryHoldService.saveHold(hold);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllHolds() {
        inventoryHoldService.deleteAllHolds();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHold(@PathVariable int id) {
        if (inventoryHoldService.getHoldById(id).isPresent()) {
            inventoryHoldService.deleteHold(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

