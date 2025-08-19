package com.example.ProductDemo.ReplenishmentRequestTest;

import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequest;
import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequestController;
import com.example.ProductDemo.ReplenishmentRequest.ReplenishmentRequestService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplenishmentRequestController.class)
public class ReplenishmentRequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
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
    void testGetAllRequests() throws Exception {
        when(service.getAllRequests()).thenReturn(sampleList);
        mvc.perform(get("/api/replenishment-requests"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRequestById() throws Exception {
        when(service.getRequestById(1)).thenReturn(Optional.of(sampleList.get(0)));
        mvc.perform(get("/api/replenishment-requests/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateRequest() throws Exception {
        when(service.saveRequest(sample)).thenReturn(sample);
        mvc.perform(post("/api/replenishment-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"requestID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"fromWarehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"toWarehouse\":{\"warehouseID\":2,\"warehouseName\":\"W2\",\"location\":\"Loc2\"},\"quantityRequested\":30,\"requestDate\":\"2024-06-01\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRequest() throws Exception {
        when(service.getRequestById(3)).thenReturn(Optional.of(sample));
        when(service.saveRequest(sample)).thenReturn(sample);
        mvc.perform(put("/api/replenishment-requests/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"requestID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"fromWarehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"toWarehouse\":{\"warehouseID\":2,\"warehouseName\":\"W2\",\"location\":\"Loc2\"},\"quantityRequested\":30,\"requestDate\":\"2024-06-01\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllRequests() throws Exception {
        doNothing().when(service).deleteAllRequests();
        mvc.perform(delete("/api/replenishment-requests"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRequest() throws Exception {
        when(service.getRequestById(3)).thenReturn(Optional.of(sample));
        doNothing().when(service).deleteRequest(3);
        mvc.perform(delete("/api/replenishment-requests/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateRequestWithSP() throws Exception {
        doNothing().when(service).createReplenishmentRequestWithSP(sample);
        mvc.perform(post("/api/replenishment-requests/sp")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"requestID\":3,\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"fromWarehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"toWarehouse\":{\"warehouseID\":2,\"warehouseName\":\"W2\",\"location\":\"Loc2\"},\"quantityRequested\":30,\"requestDate\":\"2024-06-01\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk());
    }
}

