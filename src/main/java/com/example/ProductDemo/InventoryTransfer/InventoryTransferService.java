package com.example.ProductDemo.InventoryTransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryTransferService {

    @Autowired
    private InventoryTransferRepo inventoryTransferRepo;

    public List<InventoryTransfer> getAllTransfers() {
        return inventoryTransferRepo.findAll();
    }

    public Optional<InventoryTransfer> getTransferById(int id) {
        return inventoryTransferRepo.findById(id);
    }

    public InventoryTransfer saveTransfer(InventoryTransfer transfer) {
        return inventoryTransferRepo.save(transfer);
    }

    public void deleteTransfer(int id) {
        inventoryTransferRepo.deleteById(id);
    }

    public void deleteAllTransfers() {
        inventoryTransferRepo.deleteAll();
    }


}
