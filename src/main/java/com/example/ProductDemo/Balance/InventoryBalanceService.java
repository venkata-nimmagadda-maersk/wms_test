package com.example.ProductDemo.Balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryBalanceService {

    @Autowired
    private InventoryBalanceRepo inventoryBalanceRepo;

    public List<InventoryBalance> getAllBalances() {
        return inventoryBalanceRepo.findAll();
    }

    public Optional<InventoryBalance> getBalanceById(Long id) {
        return inventoryBalanceRepo.findById(id.intValue());
    }

    public InventoryBalance saveBalance(InventoryBalance balance) {
        return inventoryBalanceRepo.save(balance);
    }

    public void deleteBalance(Long id) {
        inventoryBalanceRepo.deleteById(id.intValue());
    }

    public void deleteAllBalances() {
        inventoryBalanceRepo.deleteAll();
    }

    public List<Map<String, Object>> getInventorySnapshot() {
        return inventoryBalanceRepo.getInventorySnapshot();
    }
}
