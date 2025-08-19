package com.example.ProductDemo.WarehouseTest;

import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.Warehouse.WarehouseController;
import com.example.ProductDemo.Warehouse.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WarehouseService warehouseService;

    List<Warehouse> warehouseSampleList;
    Warehouse warehouseSample;

    @BeforeEach
    void initSamples() {
        warehouseSampleList = new ArrayList<>(
                List.of(
                        new Warehouse(1, "W1", "Loc1"),
                        new Warehouse(2, "W2", "Loc2")
                )
        );
        warehouseSample = new Warehouse(3, "W3", "Loc3");
    }

    @Test
    void testGetAllWarehouses() throws Exception {
        when(warehouseService.getAllWarehouses()).thenReturn(warehouseSampleList);
        mvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWarehouseById() throws Exception {
        when(warehouseService.getWarehouseById(1)).thenReturn(Optional.of(warehouseSampleList.get(0)));
        mvc.perform(get("/api/warehouses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWarehouse() throws Exception {
        when(warehouseService.saveWarehouse(warehouseSample)).thenReturn(warehouseSample);
        mvc.perform(post("/api/warehouses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"warehouseID\":3,\"warehouseName\":\"W3\",\"location\":\"Loc3\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWarehouse() throws Exception {
        when(warehouseService.getWarehouseById(3)).thenReturn(Optional.of(warehouseSample));
        when(warehouseService.saveWarehouse(warehouseSample)).thenReturn(warehouseSample);
        mvc.perform(put("/api/warehouses/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"warehouseID\":3,\"warehouseName\":\"W3\",\"location\":\"Loc3\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllWarehouses() throws Exception {
        doNothing().when(warehouseService).deleteAllWarehouses();
        mvc.perform(delete("/api/warehouses"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteWarehouse() throws Exception {
        when(warehouseService.getWarehouseById(3)).thenReturn(Optional.of(warehouseSample));
        doNothing().when(warehouseService).deleteWarehouse(3);
        mvc.perform(delete("/api/warehouses/3"))
                .andExpect(status().isOk());
    }
}

