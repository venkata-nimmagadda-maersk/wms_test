package com.example.ProductDemo.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable int id) {
        return purchaseOrderService.getPurchaseOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder created = purchaseOrderService.savePurchaseOrder(purchaseOrder);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable int id, @RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.getPurchaseOrderById(id)
                .map(existing -> {
                    purchaseOrder.setPoid(id);
                    PurchaseOrder updated = purchaseOrderService.savePurchaseOrder(purchaseOrder);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPurchaseOrders() {
        purchaseOrderService.deleteAllPurchaseOrders();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable int id) {
        if (purchaseOrderService.getPurchaseOrderById(id).isPresent()) {
            purchaseOrderService.deletePurchaseOrder(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-with-procedure")
    public ResponseEntity<Void> createPurchaseOrderWithProcedure(
            @RequestParam String supplierName,
            @RequestParam Date orderDate,
            @RequestParam Date expectedDeliveryDate,
            @RequestParam String status) {
        purchaseOrderService.createPurchaseOrderWithProcedure(supplierName, orderDate, expectedDeliveryDate, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive/{poid}")
    public ResponseEntity<Void> receivePurchaseOrderWithProcedure(@PathVariable int poid) {
        purchaseOrderService.receivePurchaseOrderWithProcedure(poid);
        return ResponseEntity.ok().build();
    }
}
