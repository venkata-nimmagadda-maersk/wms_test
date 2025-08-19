package com.example.ProductDemo.BalanceTest;

import com.example.ProductDemo.Balance.InventoryBalance;
import com.example.ProductDemo.Balance.InventoryBalanceController;
import com.example.ProductDemo.Balance.InventoryBalanceService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.Stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(InventoryBalanceController.class)
public class InventoryBalanceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryBalanceService inventoryBalanceService;

    List<InventoryBalance> balanceSampleList;
    InventoryBalance balanceSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        Stock stock = new Stock(1, product, warehouse, 100, 5, 0, new Date());
        balanceSampleList = new ArrayList<>(
                List.of(
                        new InventoryBalance(1L, product, warehouse, stock, 95, new Date()),
                        new InventoryBalance(2L, product, warehouse, stock, 90, new Date())
                )
        );
        balanceSample = new InventoryBalance(3L, product, warehouse, stock, 80, new Date());
    }

    @Test
    void testGetAllBalances() throws Exception {
        when(inventoryBalanceService.getAllBalances()).thenReturn(balanceSampleList);
        mvc.perform(get("/api/inventory-balances"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBalanceById() throws Exception {
        when(inventoryBalanceService.getBalanceById(1L)).thenReturn(java.util.Optional.of(balanceSampleList.get(0)));
        mvc.perform(get("/api/inventory-balances/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testCreateBalance() throws Exception {
        when(inventoryBalanceService.saveBalance(balanceSample)).thenReturn(balanceSample);
        mvc.perform(post("/api/inventory-balances")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balanceID\":3,\"product\":{\"prodId\":1},\"warehouse\":{\"warehouseID\":1},\"stock\":{\"stockID\":1},\"quantityOnHand\":80,\"lastUpdated\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBalance() throws Exception {
        when(inventoryBalanceService.getBalanceById(3L)).thenReturn(java.util.Optional.of(balanceSample));
        mvc.perform(delete("/api/inventory-balances/3"))
                .andExpect(status().isOk());
    }
}
