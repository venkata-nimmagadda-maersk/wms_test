package com.example.ProductDemo.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepo.findAll();
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(int id) {
        return purchaseOrderRepo.findById(id);
    }

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepo.save(purchaseOrder);
    }

    public void deletePurchaseOrder(int id) {
        purchaseOrderRepo.deleteById(id);
    }

    public void deleteAllPurchaseOrders() {
        purchaseOrderRepo.deleteAll();
    }

    public void createPurchaseOrderWithSP(PurchaseOrder purchaseOrder, Integer productId, Integer quantityOrdered) {
        purchaseOrderRepo.createPurchaseOrder(
            purchaseOrder.getPoid(),
            purchaseOrder.getSupplierName(),
            new java.sql.Date(purchaseOrder.getOrderDate().getTime()),
            new java.sql.Date(purchaseOrder.getExpectedDeliveryDate().getTime()),
            purchaseOrder.getStatus(),
            productId,
            quantityOrdered
        );
    }
    public void updateStatusToReceived(Integer poid) {
        purchaseOrderRepo.updateStatusToReceived(poid);
    }

}
