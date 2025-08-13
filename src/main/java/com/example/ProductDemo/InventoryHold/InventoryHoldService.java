package com.example.ProductDemo.InventoryHold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryHoldService {

    @Autowired
    private InventoryHoldRepo inventoryHoldRepo;

    public List<InventoryHold> getAllHolds() {
        return inventoryHoldRepo.findAll();
    }

    public Optional<InventoryHold> getHoldById(int id) {
        return inventoryHoldRepo.findById(id);
    }

    public InventoryHold saveHold(InventoryHold hold) {
        return inventoryHoldRepo.save(hold);
    }

    public void deleteHold(int id) {
        inventoryHoldRepo.deleteById(id);
    }

    public void deleteAllHolds() {
        inventoryHoldRepo.deleteAll();
    }
}

