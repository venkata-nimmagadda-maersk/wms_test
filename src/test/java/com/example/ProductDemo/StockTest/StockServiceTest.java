package com.example.ProductDemo.StockTest;

import com.example.ProductDemo.Stock.Stock;
import com.example.ProductDemo.Stock.StockRepo;
import com.example.ProductDemo.Stock.StockService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
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

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepo stockRepo;
    @InjectMocks
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
    void getAllStocks_returnsList() {
        when(stockRepo.findAll()).thenReturn(stockSampleList);
        List<Stock> result = stockService.getAllStocks();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getStockID());
    }

    @Test
    void getStockById_returnsStock() {
        when(stockRepo.findById(3)).thenReturn(Optional.of(stockSample));
        Optional<Stock> result = stockService.getStockById(3);
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getStockID());
    }

    @Test
    void saveStock_returnsSavedStock() {
        when(stockRepo.save(stockSample)).thenReturn(stockSample);
        Stock result = stockService.saveStock(stockSample);
        assertNotNull(result);
        assertEquals(3, result.getStockID());
    }

    @Test
    void deleteStock_deletesStock() {
        stockService.deleteStock(3);
        verify(stockRepo).deleteById(3);
    }

    @Test
    void deleteAllStocks_deletesAll() {
        stockService.deleteAllStocks();
        verify(stockRepo).deleteAll();
    }
}

