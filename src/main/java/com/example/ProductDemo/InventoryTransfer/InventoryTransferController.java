package com.example.ProductDemo.InventoryTransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/inventory-transfers")
public class InventoryTransferController {

    @Autowired
    private InventoryTransferService inventoryTransferService;

    @GetMapping
    public ResponseEntity<List<InventoryTransfer>> getAllTransfers() {
        return ResponseEntity.ok(inventoryTransferService.getAllTransfers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryTransfer> getTransferById(@PathVariable int id) {
        return inventoryTransferService.getTransferById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryTransfer> createTransfer(@RequestBody InventoryTransfer transfer) {
        InventoryTransfer created = inventoryTransferService.saveTransfer(transfer);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryTransfer> updateTransfer(@PathVariable int id, @RequestBody InventoryTransfer transfer) {
        return inventoryTransferService.getTransferById(id)
                .map(existing -> {
                    transfer.setTransferID(id);
                    InventoryTransfer updated = inventoryTransferService.saveTransfer(transfer);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransfers() {
        inventoryTransferService.deleteAllTransfers();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable int id) {
        if (inventoryTransferService.getTransferById(id).isPresent()) {
            inventoryTransferService.deleteTransfer(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/perform-transfer")
    public ResponseEntity<Void> performInventoryTransfer(
            @RequestParam int transferId,
            @RequestParam int productId,
            @RequestParam int fromWarehouseId,
            @RequestParam int toWarehouseId,
            @RequestParam int quantity,
            @RequestParam Date transferDate,
            @RequestParam String referenceNote) {
        inventoryTransferService.performInventoryTransfer(
            transferId,
            productId,
            fromWarehouseId,
            toWarehouseId,
            quantity,
            transferDate,
            referenceNote
        );
        return ResponseEntity.ok().build();
    }
}
