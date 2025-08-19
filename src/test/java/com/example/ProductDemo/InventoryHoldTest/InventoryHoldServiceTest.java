package com.example.ProductDemo.InventoryHoldTest;

import com.example.ProductDemo.InventoryHold.InventoryHold;
import com.example.ProductDemo.InventoryHold.InventoryHoldRepo;
import com.example.ProductDemo.InventoryHold.InventoryHoldService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InventoryHoldServiceTest {

    @Mock
    private InventoryHoldRepo inventoryHoldRepo;
    @InjectMocks
    private InventoryHoldService inventoryHoldService;

    List<InventoryHold> holdSampleList;
    InventoryHold holdSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        holdSampleList = new ArrayList<>(
                List.of(
                        new InventoryHold(1, product, warehouse, 10, "Quality Check", new Date(), null),
                        new InventoryHold(2, product, warehouse, 5, "Damaged", new Date(), new Date())
                )
        );
        holdSample = new InventoryHold(3, product, warehouse, 20, "Inspection", new Date(), null);
    }

    @Test
    void getAllHoldsTest_thenReturnsHolds() {
        when(inventoryHoldRepo.findAll()).thenReturn(holdSampleList);
        List<InventoryHold> holds = inventoryHoldService.getAllHolds();
        assertEquals(2, holds.size());
        assertEquals(1, holds.get(0).getHoldID());
    }

    @Test
    void getHoldById_thenReturnsHold() {
        int holdId = 3;
        when(inventoryHoldRepo.findById(holdId)).thenReturn(Optional.of(holdSample));
        Optional<InventoryHold> hold = inventoryHoldService.getHoldById(holdId);
        assertTrue(hold.isPresent());
        assertEquals(holdId, hold.get().getHoldID());
    }

    @Test
    void saveHold_thenReturnsHold() {
        when(inventoryHoldRepo.save(holdSample)).thenReturn(holdSample);
        InventoryHold hold = inventoryHoldService.saveHold(holdSample);
        assertNotNull(hold);
        assertEquals(3, hold.getHoldID());
    }

    @Test
    void deleteHold_thenHoldIsDeleted() {
        int holdId = 3;
        when(inventoryHoldRepo.findById(holdId)).thenReturn(Optional.of(holdSample));
        inventoryHoldService.deleteHold(holdId);
        when(inventoryHoldRepo.findById(holdId)).thenReturn(Optional.empty());
        Optional<InventoryHold> hold = inventoryHoldService.getHoldById(holdId);
        assertFalse(hold.isPresent());
    }
}
