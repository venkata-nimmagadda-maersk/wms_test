package com.example.ProductDemo.PutawayTest;

import com.example.ProductDemo.Putaway.Putaway;
import com.example.ProductDemo.Putaway.PutawayRepo;
import com.example.ProductDemo.Putaway.PutawayService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
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
public class PutawayServiceTest {

    @Mock
    private PutawayRepo putawayRepo;
    @InjectMocks
    private PutawayService putawayService;

    List<Putaway> putawaySampleList;
    Putaway putawaySample;

    @BeforeEach
    void initSamples() {
        PurchaseOrder po = new PurchaseOrder(1, "SupplierA", new Date(), new Date(), "Pending");
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        putawaySampleList = new ArrayList<>(
                List.of(
                        new Putaway(1, po, product, warehouse, 10, new Date()),
                        new Putaway(2, po, product, warehouse, 20, new Date())
                )
        );
        putawaySample = new Putaway(3, po, product, warehouse, 30, new Date());
    }

    @Test
    void getAllPutaways_returnsList() {
        when(putawayRepo.findAll()).thenReturn(putawaySampleList);
        List<Putaway> result = putawayService.getAllPutaways();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getPutawayID());
    }

    @Test
    void getPutawayById_returnsPutaway() {
        when(putawayRepo.findById(3)).thenReturn(Optional.of(putawaySample));
        Optional<Putaway> result = putawayService.getPutawayById(3);
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getPutawayID());
    }

    @Test
    void savePutaway_returnsSavedPutaway() {
        when(putawayRepo.save(putawaySample)).thenReturn(putawaySample);
        Putaway result = putawayService.savePutaway(putawaySample);
        assertNotNull(result);
        assertEquals(3, result.getPutawayID());
    }

    @Test
    void deletePutaway_deletesPutaway() {
        putawayService.deletePutaway(3);
        verify(putawayRepo, times(1)).deleteById(3);
    }

    @Test
    void deleteAllPutaways_deletesAll() {
        putawayService.deleteAllPutaways();
        verify(putawayRepo, times(1)).deleteAll();
    }

    @Test
    void putawayStockWithSP_callsRepoProcedure() {
        doNothing().when(putawayRepo).putawayStock(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), any(java.sql.Date.class));
        putawayService.putawayStockWithSP(putawaySample);
        verify(putawayRepo, times(1)).putawayStock(
                eq(putawaySample.getPutawayID()),
                eq(putawaySample.getPurchaseOrder().getPoid()),
                eq(putawaySample.getProduct().getProdId()),
                eq(putawaySample.getWarehouse().getWarehouseID()),
                eq(putawaySample.getQuantityPutaway()),
                any(java.sql.Date.class)
        );
    }
}

