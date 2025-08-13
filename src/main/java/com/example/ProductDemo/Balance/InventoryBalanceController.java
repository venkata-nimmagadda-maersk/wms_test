package com.example.ProductDemo.Balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/inventory-balances")
public class InventoryBalanceController {

    @Autowired
    private InventoryBalanceService inventoryBalanceService;

    @GetMapping
    public ResponseEntity<List<InventoryBalance>> getAllBalances() {
        return ResponseEntity.ok(inventoryBalanceService.getAllBalances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryBalance> getBalanceById(@PathVariable Long id) {
        return inventoryBalanceService.getBalanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryBalance> createBalance(@RequestBody InventoryBalance balance) {
        InventoryBalance created = inventoryBalanceService.saveBalance(balance);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryBalance> updateBalance(@PathVariable Long id, @RequestBody InventoryBalance balance) {
        return inventoryBalanceService.getBalanceById(id)
                .map(existing -> {
                    balance.setBalanceID(id);
                    InventoryBalance updated = inventoryBalanceService.saveBalance(balance);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllBalances() {
        inventoryBalanceService.deleteAllBalances();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        if (inventoryBalanceService.getBalanceById(id).isPresent()) {
            inventoryBalanceService.deleteBalance(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/snapshot")
    public ResponseEntity<List<Map<String, Object>>> getInventorySnapshot() {
        List<Map<String, Object>> snapshot = inventoryBalanceService.getInventorySnapshot();
        if (snapshot == null || snapshot.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(snapshot);
    }
}
