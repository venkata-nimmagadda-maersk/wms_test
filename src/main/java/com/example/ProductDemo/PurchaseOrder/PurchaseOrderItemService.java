package com.example.ProductDemo.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderItemService {

    @Autowired
    private PurchaseOrderItemRepo purchaseOrderItemRepo;

    public List<PurchaseOrderItem> getAllPurchaseOrderItems() {
        return purchaseOrderItemRepo.findAll();
    }

    public Optional<PurchaseOrderItem> getPurchaseOrderItemById(int id) {
        return purchaseOrderItemRepo.findById(id);
    }

    public PurchaseOrderItem savePurchaseOrderItem(PurchaseOrderItem item) {
        return purchaseOrderItemRepo.save(item);
    }

    public void deletePurchaseOrderItem(int id) {
        purchaseOrderItemRepo.deleteById(id);
    }

    public void deleteAllPurchaseOrderItems() {
        purchaseOrderItemRepo.deleteAll();
    }
}

