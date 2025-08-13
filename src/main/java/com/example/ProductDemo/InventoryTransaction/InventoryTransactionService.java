package com.example.ProductDemo.InventoryTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryTransactionService {

    @Autowired
    private InventoryTransactionRepo inventoryTransactionRepo;

    public List<InventoryTransaction> getAllTransactions() {
        return inventoryTransactionRepo.findAll();
    }

    public Optional<InventoryTransaction> getTransactionById(int id) {
        return inventoryTransactionRepo.findById(id);
    }

    public InventoryTransaction saveTransaction(InventoryTransaction transaction) {
        return inventoryTransactionRepo.save(transaction);
    }
    public void deleteAllTransactions() {
        inventoryTransactionRepo.deleteAll();
    }
    public void deleteTransaction(int id) {
        inventoryTransactionRepo.deleteById(id);
    }
}

