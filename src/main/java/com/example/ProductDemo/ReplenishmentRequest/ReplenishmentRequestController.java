package com.example.ProductDemo.ReplenishmentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/replenishment-requests")
public class ReplenishmentRequestController {

    @Autowired
    private ReplenishmentRequestService service;

    @GetMapping
    public ResponseEntity<List<ReplenishmentRequest>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReplenishmentRequest> getRequestById(@PathVariable int id) {
        return service.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReplenishmentRequest> createRequest(@RequestBody ReplenishmentRequest request) {
        ReplenishmentRequest created = service.saveRequest(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/sp")
    public ResponseEntity<Void> createRequestWithSP(@RequestBody ReplenishmentRequest request) {
        service.createReplenishmentRequestWithSP(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplenishmentRequest> updateRequest(@PathVariable int id, @RequestBody ReplenishmentRequest request) {
        return service.getRequestById(id)
                .map(existing -> {
                    request.setRequestID(id);
                    ReplenishmentRequest updated = service.saveRequest(request);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllRequests() {
        service.deleteAllRequests();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
        if (service.getRequestById(id).isPresent()) {
            service.deleteRequest(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
