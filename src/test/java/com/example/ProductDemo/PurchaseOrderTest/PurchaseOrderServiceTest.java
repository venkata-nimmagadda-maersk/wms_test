package com.example.ProductDemo.PurchaseOrderTest;

import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderRepo;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @Mock
    private PurchaseOrderRepo purchaseOrderRepo;
    @InjectMocks
    private PurchaseOrderService purchaseOrderService;

    List<PurchaseOrder> poSampleList;
    PurchaseOrder poSample;

    @BeforeEach
    void setUp() {
        poSampleList = new ArrayList<>(
                List.of(
                        new PurchaseOrder(1, "SupplierA", new Date(), new Date(), "Pending"),
                        new PurchaseOrder(2, "SupplierB", new Date(), new Date(), "Received")
                )
        );
        poSample = new PurchaseOrder(3, "SupplierC", new Date(), new Date(), "Pending");
    }

    @Test
    void getAllPurchaseOrders_returnsList() {
        when(purchaseOrderRepo.findAll()).thenReturn(poSampleList);
        List<PurchaseOrder> result = purchaseOrderService.getAllPurchaseOrders();
        assertEquals(2, result.size());
        assertEquals("SupplierA", result.get(0).getSupplierName());
    }

    @Test
    void getPurchaseOrderById_returnsOrder() {
        when(purchaseOrderRepo.findById(3)).thenReturn(Optional.of(poSample));
        Optional<PurchaseOrder> result = purchaseOrderService.getPurchaseOrderById(3);
        assertTrue(result.isPresent());
        assertEquals("SupplierC", result.get().getSupplierName());
    }

    @Test
    void savePurchaseOrder_returnsSavedOrder() {
        when(purchaseOrderRepo.save(poSample)).thenReturn(poSample);
        PurchaseOrder result = purchaseOrderService.savePurchaseOrder(poSample);
        assertNotNull(result);
        assertEquals(3, result.getPoid());
    }

    @Test
    void deletePurchaseOrder_deletesOrder() {
        purchaseOrderService.deletePurchaseOrder(3);
        verify(purchaseOrderRepo, times(1)).deleteById(3);
    }

    @Test
    void deleteAllPurchaseOrders_deletesAll() {
        purchaseOrderService.deleteAllPurchaseOrders();
        verify(purchaseOrderRepo, times(1)).deleteAll();
    }

    @Test
    void createPurchaseOrderWithSP_callsRepoProcedure() {
        doNothing().when(purchaseOrderRepo).createPurchaseOrder(anyInt(), anyString(), any(), any(), anyString(), anyInt(), anyInt());
        purchaseOrderService.createPurchaseOrderWithSP(poSample, 10, 5);
        verify(purchaseOrderRepo, times(1)).createPurchaseOrder(
                eq(poSample.getPoid()),
                eq(poSample.getSupplierName()),
                any(),
                any(),
                eq(poSample.getStatus()),
                eq(10),
                eq(5)
        );
    }

    @Test
    void updateStatusToReceived_callsRepoUpdate() {
        doNothing().when(purchaseOrderRepo).updateStatusToReceived(anyInt());
        purchaseOrderService.updateStatusToReceived(3);
        verify(purchaseOrderRepo, times(1)).updateStatusToReceived(3);
    }
}
