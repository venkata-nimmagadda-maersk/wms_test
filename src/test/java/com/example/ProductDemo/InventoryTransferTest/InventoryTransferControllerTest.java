package com.example.ProductDemo.InventoryTransferTest;

import com.example.ProductDemo.InventoryTransfer.InventoryTransfer;
import com.example.ProductDemo.InventoryTransfer.InventoryTransferController;
import com.example.ProductDemo.InventoryTransfer.InventoryTransferService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(InventoryTransferController.class)
public class InventoryTransferControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryTransferService inventoryTransferService;

    List<InventoryTransfer> transferSampleList;
    InventoryTransfer transferSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", new BigDecimal("100"), "C1", "CN1");
        Warehouse fromWarehouse = new Warehouse(1, "W1", "Loc1");
        Warehouse toWarehouse = new Warehouse(2, "W2", "Loc2");
        transferSampleList = new ArrayList<>(
                List.of(
                        new InventoryTransfer(1, product, fromWarehouse, toWarehouse, 10, new Date(), "Initial Transfer"),
                        new InventoryTransfer(2, product, fromWarehouse, toWarehouse, 5, new Date(), "Second Transfer")
                )
        );
        transferSample = new InventoryTransfer(3, product, fromWarehouse, toWarehouse, 20, new Date(), "Bulk Transfer");
    }

    @Test
    void testGetAllTransfers() throws Exception {
        when(inventoryTransferService.getAllTransfers()).thenReturn(transferSampleList);
        mvc.perform(get("/api/inventory-transfers"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransferById() throws Exception {
        when(inventoryTransferService.getTransferById(1)).thenReturn(java.util.Optional.of(transferSampleList.get(0)));
        mvc.perform(get("/api/inventory-transfers/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testCreateTransfer() throws Exception {
        when(inventoryTransferService.saveTransfer(transferSample)).thenReturn(transferSample);
        mvc.perform(post("/api/inventory-transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transferID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\",\"price\":100,\"category\":\"C1\",\"categoryName\":\"CN1\"},\"fromWarehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"toWarehouse\":{\"warehouseID\":2,\"warehouseName\":\"W2\",\"location\":\"Loc2\"},\"quantity\":20,\"transferDate\":\"2024-06-01\",\"referenceNote\":\"Bulk Transfer\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTransfer() throws Exception {
        when(inventoryTransferService.getTransferById(3)).thenReturn(java.util.Optional.of(transferSample));
        mvc.perform(delete("/api/inventory-transfers/3"))
                .andExpect(status().isOk());
    }
}

