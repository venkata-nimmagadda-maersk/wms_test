package com.example.ProductDemo.PurchaseOrderTest;

import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItem;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItemController;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrderItemService;
import com.example.ProductDemo.Product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseOrderItemController.class)
public class PurchaseOrderItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
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
    void testGetAllPurchaseOrderItems() throws Exception {
        when(purchaseOrderItemService.getAllPurchaseOrderItems()).thenReturn(poItemSampleList);
        mvc.perform(get("/api/purchase-order-items"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPurchaseOrderItemById() throws Exception {
        when(purchaseOrderItemService.getPurchaseOrderItemById(1)).thenReturn(Optional.of(poItemSampleList.get(0)));
        mvc.perform(get("/api/purchase-order-items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePurchaseOrderItem() throws Exception {
        when(purchaseOrderItemService.savePurchaseOrderItem(poItemSample)).thenReturn(poItemSample);
        mvc.perform(post("/api/purchase-order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"poItemID\":3,\"purchaseOrder\":{\"poid\":1,\"supplierName\":\"SupplierA\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"product\":{\"prodId\":1,\"prodName\":\"P1\",\"price\":100,\"category\":\"C1\",\"categoryName\":\"CN1\"},\"quantityOrdered\":30}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePurchaseOrderItem() throws Exception {
        when(purchaseOrderItemService.getPurchaseOrderItemById(3)).thenReturn(Optional.of(poItemSample));
        when(purchaseOrderItemService.savePurchaseOrderItem(poItemSample)).thenReturn(poItemSample);
        mvc.perform(put("/api/purchase-order-items/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"poItemID\":3,\"purchaseOrder\":{\"poid\":1,\"supplierName\":\"SupplierA\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"product\":{\"prodId\":1,\"prodName\":\"P1\",\"price\":100,\"category\":\"C1\",\"categoryName\":\"CN1\"},\"quantityOrdered\":30}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllPurchaseOrderItems() throws Exception {
        doNothing().when(purchaseOrderItemService).deleteAllPurchaseOrderItems();
        mvc.perform(delete("/api/purchase-order-items"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePurchaseOrderItem() throws Exception {
        when(purchaseOrderItemService.getPurchaseOrderItemById(3)).thenReturn(Optional.of(poItemSample));
        doNothing().when(purchaseOrderItemService).deletePurchaseOrderItem(3);
        mvc.perform(delete("/api/purchase-order-items/3"))
                .andExpect(status().isOk());
    }
}

