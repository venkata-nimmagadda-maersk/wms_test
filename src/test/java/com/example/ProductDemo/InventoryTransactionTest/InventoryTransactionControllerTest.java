package com.example.ProductDemo.InventoryTransactionTest;

import com.example.ProductDemo.InventoryTransaction.InventoryTransaction;
import com.example.ProductDemo.InventoryTransaction.InventoryTransactionController;
import com.example.ProductDemo.InventoryTransaction.InventoryTransactionService;
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

@WebMvcTest(InventoryTransactionController.class)
public class InventoryTransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryTransactionService inventoryTransactionService;

    List<InventoryTransaction> transactionSampleList;
    InventoryTransaction transactionSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", new BigDecimal("100"), "C1", "CN1");
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        transactionSampleList = new ArrayList<>(
                List.of(
                        new InventoryTransaction(1, product, warehouse, "IN", 10, new Date(), 1001, "Initial Stock"),
                        new InventoryTransaction(2, product, warehouse, "OUT", 5, new Date(), 1002, "Order Shipped")
                )
        );
        transactionSample = new InventoryTransaction(3, product, warehouse, "IN", 20, new Date(), 1003, "Restock");
    }

    @Test
    void testGetAllTransactions() throws Exception {
        when(inventoryTransactionService.getAllTransactions()).thenReturn(transactionSampleList);
        mvc.perform(get("/api/inventory-transactions"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactionById() throws Exception {
        when(inventoryTransactionService.getTransactionById(1)).thenReturn(java.util.Optional.of(transactionSampleList.get(0)));
        mvc.perform(get("/api/inventory-transactions/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(inventoryTransactionService.saveTransaction(transactionSample)).thenReturn(transactionSample);
        mvc.perform(post("/api/inventory-transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\",\"price\":100,\"category\":\"C1\",\"categoryName\":\"CN1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"transactionType\":\"IN\",\"quantity\":20,\"transactionDate\":\"2024-06-01\",\"referenceID\":1003,\"remarks\":\"Restock\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTransaction() throws Exception {
        when(inventoryTransactionService.getTransactionById(3)).thenReturn(java.util.Optional.of(transactionSample));
        mvc.perform(delete("/api/inventory-transactions/3"))
                .andExpect(status().isOk());
    }
}

