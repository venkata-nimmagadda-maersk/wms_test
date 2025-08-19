package com.example.ProductDemo.PurchaseOrderTest;

import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderController;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(PurchaseOrderController.class)
public class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
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
    void testGetAllPurchaseOrders() throws Exception {
        when(purchaseOrderService.getAllPurchaseOrders()).thenReturn(poSampleList);
        mvc.perform(get("/api/purchase-orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPurchaseOrderById() throws Exception {
        when(purchaseOrderService.getPurchaseOrderById(1)).thenReturn(Optional.of(poSampleList.get(0)));
        mvc.perform(get("/api/purchase-orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePurchaseOrder() throws Exception {
        when(purchaseOrderService.savePurchaseOrder(poSample)).thenReturn(poSample);
        mvc.perform(post("/api/purchase-orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"poid\":3,\"supplierName\":\"SupplierC\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePurchaseOrder() throws Exception {
        when(purchaseOrderService.getPurchaseOrderById(3)).thenReturn(Optional.of(poSample));
        when(purchaseOrderService.savePurchaseOrder(poSample)).thenReturn(poSample);
        mvc.perform(put("/api/purchase-orders/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"poid\":3,\"supplierName\":\"SupplierC\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllPurchaseOrders() throws Exception {
        doNothing().when(purchaseOrderService).deleteAllPurchaseOrders();
        mvc.perform(delete("/api/purchase-orders"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePurchaseOrder() throws Exception {
        when(purchaseOrderService.getPurchaseOrderById(3)).thenReturn(Optional.of(poSample));
        doNothing().when(purchaseOrderService).deletePurchaseOrder(3);
        mvc.perform(delete("/api/purchase-orders/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePurchaseOrderWithSP() throws Exception {
        doNothing().when(purchaseOrderService).createPurchaseOrderWithSP(any(), any(), any());
        mvc.perform(post("/api/purchase-orders/sp")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"purchaseOrder\":{\"poid\":3,\"supplierName\":\"SupplierC\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"productId\":10,\"quantityOrdered\":5}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateStatusToReceived() throws Exception {
        when(purchaseOrderService.getPurchaseOrderById(3)).thenReturn(Optional.of(poSample));
        doNothing().when(purchaseOrderService).updateStatusToReceived(3);
        mvc.perform(put("/api/purchase-orders/3/status/received"))
                .andExpect(status().isOk());
    }
}
