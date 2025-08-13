package com.example.ProductDemo.Warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepo warehouseRepo;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }

    public Optional<Warehouse> getWarehouseById(int id) {
        return warehouseRepo.findById(id);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public void deleteWarehouse(int id) {
        warehouseRepo.deleteById(id);
    }

    public void deleteAllWarehouses() {
        warehouseRepo.deleteAll();
    }
}

