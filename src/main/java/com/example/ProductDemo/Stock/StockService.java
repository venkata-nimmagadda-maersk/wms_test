package com.example.ProductDemo.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepo stockRepo;

    public List<Stock> getAllStocks() {
        return stockRepo.findAll();
    }

    public Optional<Stock> getStockById(int id) {
        return stockRepo.findById(id);
    }

    public Stock saveStock(Stock stock) {
        return stockRepo.save(stock);
    }

    public void deleteStock(int id) {
        stockRepo.deleteById(id);
    }

    public void deleteAllStocks() {
        stockRepo.deleteAll();
    }

    public Map<String, Object> getStockStatus(int productId, int warehouseId) {
        return stockRepo.getStockStatus(productId, warehouseId);
    }
}
