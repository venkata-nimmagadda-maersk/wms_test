package com.example.ProductDemo.PurchaseOrderTest;

import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItem;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItemRepo;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItemService;
import com.example.ProductDemo.Product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderItemServiceTest {

    @Mock
    private PurchaseOrderItemRepo purchaseOrderItemRepo;
    @InjectMocks
    private PurchaseOrderItemService purchaseOrderItemService;

    List<PurchaseOrderItem> poItemSampleList;
    PurchaseOrderItem poItemSample;

    @BeforeEach
    void initSamples() {
        PurchaseOrder po = new PurchaseOrder(1, "SupplierA", new Date(), new Date(), "Pending");
        Product product = new Product(1, "P1", new BigDecimal("100"), "C1", "CN1");
        poItemSampleList = new ArrayList<>(
                List.of(
                        new PurchaseOrderItem(1, po, product, 10),
                        new PurchaseOrderItem(2, po, product, 20)
                )
        );
        poItemSample = new PurchaseOrderItem(3, po, product, 30);
    }

    @Test
    void getAllPurchaseOrderItems_returnsItems() {
        when(purchaseOrderItemRepo.findAll()).thenReturn(poItemSampleList);
        List<PurchaseOrderItem> items = purchaseOrderItemService.getAllPurchaseOrderItems();
        assertEquals(2, items.size());
        assertEquals(1, items.get(0).getPoItemID());
    }

    @Test
    void getPurchaseOrderItemById_returnsItem() {
        int poItemId = 3;
        when(purchaseOrderItemRepo.findById(poItemId)).thenReturn(Optional.of(poItemSample));
        Optional<PurchaseOrderItem> item = purchaseOrderItemService.getPurchaseOrderItemById(poItemId);
        assertTrue(item.isPresent());
        assertEquals(poItemId, item.get().getPoItemID());
    }

    @Test
    void savePurchaseOrderItem_returnsSavedItem() {
        when(purchaseOrderItemRepo.save(poItemSample)).thenReturn(poItemSample);
        PurchaseOrderItem item = purchaseOrderItemService.savePurchaseOrderItem(poItemSample);
        assertNotNull(item);
        assertEquals(3, item.getPoItemID());
    }

    @Test
    void deletePurchaseOrderItem_deletesItem() {
        int poItemId = 3;
        purchaseOrderItemService.deletePurchaseOrderItem(poItemId);
        verify(purchaseOrderItemRepo).deleteById(poItemId);
    }

    @Test
    void deleteAllPurchaseOrderItems_deletesAll() {
        purchaseOrderItemService.deleteAllPurchaseOrderItems();
        verify(purchaseOrderItemRepo).deleteAll();
    }
}

