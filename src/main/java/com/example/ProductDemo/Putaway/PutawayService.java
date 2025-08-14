package com.example.ProductDemo.Putaway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PutawayService {

    @Autowired
    private PutawayRepo putawayRepo;

    public List<Putaway> getAllPutaways() {
        return putawayRepo.findAll();
    }

    public Optional<Putaway> getPutawayById(int id) {
        return putawayRepo.findById(id);
    }

    public Putaway savePutaway(Putaway putaway) {
        return putawayRepo.save(putaway);
    }

    public void deletePutaway(int id) {
        putawayRepo.deleteById(id);
    }

    public void deleteAllPutaways() {
        putawayRepo.deleteAll();
    }

    public void putawayStockWithSP(Putaway putaway) {
        putawayRepo.putawayStock(
            putaway.getPutawayID(),
            putaway.getPurchaseOrder().getPoid(),
            putaway.getProduct().getProdId(),
            putaway.getWarehouse().getWarehouseID(),
            putaway.getQuantityPutaway(),
            new java.sql.Date(putaway.getPutawayDate().getTime())
        );
    }

}
