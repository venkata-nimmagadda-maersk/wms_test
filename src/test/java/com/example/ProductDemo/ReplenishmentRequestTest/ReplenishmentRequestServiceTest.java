package com.example.ProductDemo.ReplenishmentRequestTest;

import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequest;
import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequestRepo;
import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequestService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReplenishmentRequestServiceTest {

    @Mock
    private ReplenishmentRequestRepo repo;
    @InjectMocks
    private ReplenishmentRequestService service;

    List<ReplenishmentRequest> sampleList;
    ReplenishmentRequest sample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse fromWarehouse = new Warehouse(1, "W1", "Loc1");
        Warehouse toWarehouse = new Warehouse(2, "W2", "Loc2");
        sampleList = new ArrayList<>(
                List.of(
                        new ReplenishmentRequest(1, product, fromWarehouse, toWarehouse, 10, new Date(), "Pending"),
                        new ReplenishmentRequest(2, product, fromWarehouse, toWarehouse, 20, new Date(), "Approved")
                )
        );
        sample = new ReplenishmentRequest(3, product, fromWarehouse, toWarehouse, 30, new Date(), "Pending");
    }

    @Test
    void getAllRequests_returnsList() {
        when(repo.findAll()).thenReturn(sampleList);
        List<ReplenishmentRequest> result = service.getAllRequests();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRequestID());
    }

    @Test
    void getRequestById_returnsRequest() {
        when(repo.findById(3)).thenReturn(Optional.of(sample));
        Optional<ReplenishmentRequest> result = service.getRequestById(3);
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getRequestID());
    }

    @Test
    void saveRequest_returnsSavedRequest() {
        when(repo.save(sample)).thenReturn(sample);
        ReplenishmentRequest result = service.saveRequest(sample);
        assertNotNull(result);
        assertEquals(3, result.getRequestID());
    }

    @Test
    void deleteRequest_deletesRequest() {
        service.deleteRequest(3);
        verify(repo, times(1)).deleteById(3);
    }

    @Test
    void deleteAllRequests_deletesAll() {
        service.deleteAllRequests();
        verify(repo, times(1)).deleteAll();
    }

    @Test
    void createReplenishmentRequestWithSP_callsRepoProcedure() {
        doNothing().when(repo).createReplenishmentRequest(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), any(java.sql.Date.class), anyString());
        service.createReplenishmentRequestWithSP(sample);
        verify(repo, times(1)).createReplenishmentRequest(
                eq(sample.getRequestID()),
                eq(sample.getProduct().getProdId()),
                eq(sample.getFromWarehouse().getWarehouseID()),
                eq(sample.getToWarehouse().getWarehouseID()),
                eq(sample.getQuantityRequested()),
                any(java.sql.Date.class),
                eq(sample.getStatus())
        );
    }
}

