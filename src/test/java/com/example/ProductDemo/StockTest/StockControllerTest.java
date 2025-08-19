package com.example.ProductDemo.StockTest;

import com.example.ProductDemo.Stock.Stock;
import com.example.ProductDemo.Stock.StockController;
import com.example.ProductDemo.Stock.StockService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
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

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockService stockService;

    List<Stock> stockSampleList;
    Stock stockSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        stockSampleList = new ArrayList<>(
                List.of(
                        new Stock(1, product, warehouse, 100, 5, 0, new Date()),
                        new Stock(2, product, warehouse, 200, 10, 2, new Date())
                )
        );
        stockSample = new Stock(3, product, warehouse, 300, 15, 1, new Date());
    }

    @Test
    void testGetAllStocks() throws Exception {
        when(stockService.getAllStocks()).thenReturn(stockSampleList);
        mvc.perform(get("/api/stocks"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStockById() throws Exception {
        when(stockService.getStockById(1)).thenReturn(Optional.of(stockSampleList.get(0)));
        mvc.perform(get("/api/stocks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateStock() throws Exception {
        when(stockService.saveStock(stockSample)).thenReturn(stockSample);
        mvc.perform(post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stockID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityAvailable\":300,\"quantityReserved\":15,\"quantityDamaged\":1,\"lastUpdated\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateStock() throws Exception {
        when(stockService.getStockById(3)).thenReturn(Optional.of(stockSample));
        when(stockService.saveStock(stockSample)).thenReturn(stockSample);
        mvc.perform(put("/api/stocks/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stockID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityAvailable\":300,\"quantityReserved\":15,\"quantityDamaged\":1,\"lastUpdated\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllStocks() throws Exception {
        doNothing().when(stockService).deleteAllStocks();
        mvc.perform(delete("/api/stocks"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteStock() throws Exception {
        when(stockService.getStockById(3)).thenReturn(Optional.of(stockSample));
        doNothing().when(stockService).deleteStock(3);
        mvc.perform(delete("/api/stocks/3"))
                .andExpect(status().isOk());
    }
}

