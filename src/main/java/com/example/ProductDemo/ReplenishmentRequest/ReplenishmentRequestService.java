package com.example.ProductDemo.ReplenishmentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReplenishmentRequestService {

    @Autowired
    private ReplenishmentRequestRepo repo;

    public List<ReplenishmentRequest> getAllRequests() {
        return repo.findAll();
    }

    public Optional<ReplenishmentRequest> getRequestById(int id) {
        return repo.findById(id);
    }

    public ReplenishmentRequest saveRequest(ReplenishmentRequest request) {
        return repo.save(request);
    }

    public void deleteRequest(int id) {
        repo.deleteById(id);
    }

    public void deleteAllRequests() {
        repo.deleteAll();
    }

    public void createReplenishmentRequestWithSP(ReplenishmentRequest request) {
        repo.createReplenishmentRequest(
            request.getRequestID(),
            request.getProduct().getProdId(),
            request.getFromWarehouse().getWarehouseID(),
            request.getToWarehouse().getWarehouseID(),
            request.getQuantityRequested(),
            new java.sql.Date(request.getRequestDate().getTime()),
            request.getStatus()
        );
    }
}
