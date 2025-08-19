package com.example.ProductDemo.InventoryHoldTest;

import com.example.ProductDemo.InventoryHold.InventoryHold;
import com.example.ProductDemo.InventoryHold.InventoryHoldController;
import com.example.ProductDemo.InventoryHold.InventoryHoldService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(InventoryHoldController.class)
public class InventoryHoldControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryHoldService inventoryHoldService;

    List<InventoryHold> holdSampleList;
    InventoryHold holdSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        holdSampleList = new ArrayList<>(
                List.of(
                        new InventoryHold(1, product, warehouse, 10, "Quality Check", new Date(), null),
                        new InventoryHold(2, product, warehouse, 5, "Damaged", new Date(), new Date())
                )
        );
        holdSample = new InventoryHold(3, product, warehouse, 20, "Inspection", new Date(), null);
    }

    @Test
    void testGetAllHolds() throws Exception {
        when(inventoryHoldService.getAllHolds()).thenReturn(holdSampleList);
        mvc.perform(get("/api/inventory-holds"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetHoldById() throws Exception {
        when(inventoryHoldService.getHoldById(1)).thenReturn(java.util.Optional.of(holdSampleList.get(0)));
        mvc.perform(get("/api/inventory-holds/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testCreateHold() throws Exception {
        when(inventoryHoldService.saveHold(holdSample)).thenReturn(holdSample);
        mvc.perform(post("/api/inventory-holds")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"holdID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\",\"price\":null,\"category\":null,\"categoryName\":null},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityHeld\":20,\"holdReason\":\"Inspection\",\"holdDate\":\"2024-06-01\",\"releaseDate\":null}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteHold() throws Exception {
        when(inventoryHoldService.getHoldById(3)).thenReturn(java.util.Optional.of(holdSample));
        mvc.perform(delete("/api/inventory-holds/3"))
                .andExpect(status().isOk());
    }
}
