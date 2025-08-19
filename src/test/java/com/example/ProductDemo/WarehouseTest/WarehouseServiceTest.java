package com.example.ProductDemo.WarehouseTest;

import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.Warehouse.WarehouseRepo;
import com.example.ProductDemo.Warehouse.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private WarehouseRepo warehouseRepo;
    @InjectMocks
    private WarehouseService warehouseService;

    List<Warehouse> warehouseSampleList;
    Warehouse warehouseSample;

    @BeforeEach
    void initSamples() {
        warehouseSampleList = new ArrayList<>(
                List.of(
                        new Warehouse(1, "W1", "Loc1"),
                        new Warehouse(2, "W2", "Loc2")
                )
        );
        warehouseSample = new Warehouse(3, "W3", "Loc3");
    }

    @Test
    void getAllWarehouses_returnsList() {
        when(warehouseRepo.findAll()).thenReturn(warehouseSampleList);
        List<Warehouse> result = warehouseService.getAllWarehouses();
        assertEquals(2, result.size());
        assertEquals("W1", result.get(0).getWarehouseName());
    }

    @Test
    void getWarehouseById_returnsWarehouse() {
        when(warehouseRepo.findById(3)).thenReturn(Optional.of(warehouseSample));
        Optional<Warehouse> result = warehouseService.getWarehouseById(3);
        assertTrue(result.isPresent());
        assertEquals("W3", result.get().getWarehouseName());
    }

    @Test
    void saveWarehouse_returnsSavedWarehouse() {
        when(warehouseRepo.save(warehouseSample)).thenReturn(warehouseSample);
        Warehouse result = warehouseService.saveWarehouse(warehouseSample);
        assertNotNull(result);
        assertEquals(3, result.getWarehouseID());
    }

    @Test
    void deleteWarehouse_deletesWarehouse() {
        warehouseService.deleteWarehouse(3);
        verify(warehouseRepo).deleteById(3);
    }

    @Test
    void deleteAllWarehouses_deletesAll() {
        warehouseService.deleteAllWarehouses();
        verify(warehouseRepo).deleteAll();
    }
}

