package com.example.ProductDemo.AdjustmentTest;

import com.example.ProductDemo.Adjustment.InventoryAdjustment;
import com.example.ProductDemo.Adjustment.InventoryAdjustmentController;
import com.example.ProductDemo.Adjustment.InventoryAdjustmentService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryAdjustmentController.class)
public class InventoryAdjustmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryAdjustmentService inventoryAdjustmentService;

    List<InventoryAdjustment> adjustmentSampleList;
    InventoryAdjustment adjustmentSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        adjustmentSampleList = new ArrayList<>(
                List.of(
                        new InventoryAdjustment(1, product, warehouse, "ADD", 10, new Date(), "Initial Stock"),
                        new InventoryAdjustment(2, product, warehouse, "REMOVE", 5, new Date(), "Damaged")
                )
        );
        adjustmentSample = new InventoryAdjustment(3, product, warehouse, "ADD", 20, new Date(), "Restock");
    }

    @Test
    void testGetAllAdjustments() throws Exception {
        when(inventoryAdjustmentService.getAllAdjustments()).thenReturn(adjustmentSampleList);
        mvc.perform(get("/api/inventory-adjustments"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAdjustmentById() throws Exception {
        when(inventoryAdjustmentService.getAdjustmentById(1)).thenReturn(java.util.Optional.of(adjustmentSampleList.get(0)));
        mvc.perform(get("/api/inventory-adjustments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateAdjustment() throws Exception {
        when(inventoryAdjustmentService.saveAdjustment(adjustmentSample)).thenReturn(adjustmentSample);
        mvc.perform(post("/api/inventory-adjustments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adjustmentID\":3,\"product\":{\"prodId\":1},\"warehouse\":{\"warehouseID\":1},\"adjustmentType\":\"ADD\",\"quantityChange\":20,\"adjustmentDate\":\"2024-06-01\",\"reason\":\"Restock\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAdjustment() throws Exception {
        when(inventoryAdjustmentService.getAdjustmentById(3)).thenReturn(java.util.Optional.of(adjustmentSample));
        mvc.perform(delete("/api/inventory-adjustments/3"))
                .andExpect(status().isOk());
    }
    @Test
    void testAdjustInventoryWithProcedure() throws Exception {
        mvc.perform(post("/api/inventory-adjustments/procedure")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adjustmentID\":3,\"product\":{\"prodId\":1},\"warehouse\":{\"warehouseID\":1},\"adjustmentType\":\"ADD\",\"quantityChange\":20,\"adjustmentDate\":\"2024-06-01\",\"reason\":\"Restock\"}"))
                .andExpect(status().isOk());
    }
}
