package com.example.ProductDemo.AdjustmentTest;

import com.example.ProductDemo.Adjustment.InventoryAdjustment;
import com.example.ProductDemo.Adjustment.InventoryAdjustmentRepo;
import com.example.ProductDemo.Adjustment.InventoryAdjustmentService;
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

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InventoryAdjustmentServiceTest {

    @Mock
    private InventoryAdjustmentRepo inventoryAdjustmentRepo;
    @InjectMocks
    private InventoryAdjustmentService inventoryAdjustmentService;

    List<InventoryAdjustment> adjustmentSampleList;
    InventoryAdjustment adjustmentSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        adjustmentSampleList = new ArrayList<>(
                List.of(
                        new InventoryAdjustment(1, product, warehouse, "ADD", 10, new Date(), "Initial Stock"),
                        new InventoryAdjustment(2, product, warehouse, "REMOVE", 5, new Date(), "Damaged")
                )
        );
        adjustmentSample = new InventoryAdjustment(3, product, warehouse, "ADD", 20, new Date(), "Restock");
    }

    @Test
    void getAllAdjustmentsTest_thenReturnsAdjustments() {
        when(inventoryAdjustmentRepo.findAll()).thenReturn(adjustmentSampleList);
        List<InventoryAdjustment> adjustments = inventoryAdjustmentService.getAllAdjustments();
        assertEquals(2, adjustments.size());
        assertEquals(1, adjustments.get(0).getAdjustmentID());
    }

    @Test
    void getAdjustmentById_thenReturnsAdjustment() {
        int adjId = 3;
        when(inventoryAdjustmentRepo.findById(adjId)).thenReturn(Optional.of(adjustmentSample));
        Optional<InventoryAdjustment> adj = inventoryAdjustmentService.getAdjustmentById(adjId);
        assertTrue(adj.isPresent());
        assertEquals(adjId, adj.get().getAdjustmentID());
    }

    @Test
    void saveAdjustment_thenReturnsAdjustment() {
        when(inventoryAdjustmentRepo.save(adjustmentSample)).thenReturn(adjustmentSample);
        InventoryAdjustment adj = inventoryAdjustmentService.saveAdjustment(adjustmentSample);
        assertNotNull(adj);
        assertEquals(3, adj.getAdjustmentID());
    }

    @Test
    void deleteAdjustment_thenAdjustmentIsDeleted() {
        int adjId = 3;
        when(inventoryAdjustmentRepo.findById(adjId)).thenReturn(Optional.of(adjustmentSample));
        inventoryAdjustmentService.deleteAdjustment(adjId);
        when(inventoryAdjustmentRepo.findById(adjId)).thenReturn(Optional.empty());
        Optional<InventoryAdjustment> adj = inventoryAdjustmentService.getAdjustmentById(adjId);
        assertFalse(adj.isPresent());
    }
}
