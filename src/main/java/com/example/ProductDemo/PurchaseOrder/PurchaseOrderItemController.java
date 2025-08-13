package com.example.ProductDemo.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/purchase-order-items")
public class PurchaseOrderItemController {

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderItem>> getAllPurchaseOrderItems() {
        return ResponseEntity.ok(purchaseOrderItemService.getAllPurchaseOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderItem> getPurchaseOrderItemById(@PathVariable int id) {
        return purchaseOrderItemService.getPurchaseOrderItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderItem> createPurchaseOrderItem(@RequestBody PurchaseOrderItem item) {
        PurchaseOrderItem created = purchaseOrderItemService.savePurchaseOrderItem(item);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderItem> updatePurchaseOrderItem(@PathVariable int id, @RequestBody PurchaseOrderItem item) {
        return purchaseOrderItemService.getPurchaseOrderItemById(id)
                .map(existing -> {
                    item.setPoItemID(id);
                    PurchaseOrderItem updated = purchaseOrderItemService.savePurchaseOrderItem(item);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllPurchaseOrderItems() {
        purchaseOrderItemService.deleteAllPurchaseOrderItems();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrderItem(@PathVariable int id) {
        if (purchaseOrderItemService.getPurchaseOrderItemById(id).isPresent()) {
            purchaseOrderItemService.deletePurchaseOrderItem(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

