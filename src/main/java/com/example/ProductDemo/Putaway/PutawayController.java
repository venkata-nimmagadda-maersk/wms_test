package com.example.ProductDemo.Putaway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/putaways")
public class PutawayController {

    @Autowired
    private PutawayService putawayService;

    @GetMapping
    public ResponseEntity<List<Putaway>> getAllPutaways() {
        return ResponseEntity.ok(putawayService.getAllPutaways());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Putaway> getPutawayById(@PathVariable int id) {
        return putawayService.getPutawayById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Putaway> createPutaway(@RequestBody Putaway putaway) {
        Putaway created = putawayService.savePutaway(putaway);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Putaway> updatePutaway(@PathVariable int id, @RequestBody Putaway putaway) {
        return putawayService.getPutawayById(id)
                .map(existing -> {
                    putaway.setPutawayID(id);
                    Putaway updated = putawayService.savePutaway(putaway);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPutaways() {
        putawayService.deleteAllPutaways();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePutaway(@PathVariable int id) {
        if (putawayService.getPutawayById(id).isPresent()) {
            putawayService.deletePutaway(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/putaway-stock")
    public ResponseEntity<Void> putawayStock(
            @RequestParam int poid,
            @RequestParam int productId,
            @RequestParam int warehouseId,
            @RequestParam int quantityPutaway,
            @RequestParam Date putawayDate) {
        putawayService.putawayStock(poid, productId, warehouseId, quantityPutaway, putawayDate);
        return ResponseEntity.ok().build();
    }
}
