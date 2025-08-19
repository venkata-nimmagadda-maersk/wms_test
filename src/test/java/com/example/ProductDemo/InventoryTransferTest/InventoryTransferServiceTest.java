package com.example.ProductDemo.InventoryTransferTest;

import com.example.ProductDemo.InventoryTransfer.InventoryTransfer;
import com.example.ProductDemo.InventoryTransfer.InventoryTransferRepo;
import com.example.ProductDemo.InventoryTransfer.InventoryTransferService;
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
public class InventoryTransferServiceTest {

    @Mock
    private InventoryTransferRepo inventoryTransferRepo;
    @InjectMocks
    private InventoryTransferService inventoryTransferService;

    List<InventoryTransfer> transferSampleList;
    InventoryTransfer transferSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", new BigDecimal("100"), "C1", "CN1");
        Warehouse fromWarehouse = new Warehouse(1, "W1", "Loc1");
        Warehouse toWarehouse = new Warehouse(2, "W2", "Loc2");
        transferSampleList = new ArrayList<>(
                List.of(
                        new InventoryTransfer(1, product, fromWarehouse, toWarehouse, 10, new Date(), "Initial Transfer"),
                        new InventoryTransfer(2, product, fromWarehouse, toWarehouse, 5, new Date(), "Second Transfer")
                )
        );
        transferSample = new InventoryTransfer(3, product, fromWarehouse, toWarehouse, 20, new Date(), "Bulk Transfer");
    }

    @Test
    void getAllTransfersTest_thenReturnsTransfers() {
        when(inventoryTransferRepo.findAll()).thenReturn(transferSampleList);
        List<InventoryTransfer> transfers = inventoryTransferService.getAllTransfers();
        assertEquals(2, transfers.size());
        assertEquals(1, transfers.get(0).getTransferID());
    }

    @Test
    void getTransferById_thenReturnsTransfer() {
        int transferId = 3;
        when(inventoryTransferRepo.findById(transferId)).thenReturn(Optional.of(transferSample));
        Optional<InventoryTransfer> transfer = inventoryTransferService.getTransferById(transferId);
        assertTrue(transfer.isPresent());
        assertEquals(transferId, transfer.get().getTransferID());
    }

    @Test
    void saveTransfer_thenReturnsTransfer() {
        when(inventoryTransferRepo.save(transferSample)).thenReturn(transferSample);
        InventoryTransfer transfer = inventoryTransferService.saveTransfer(transferSample);
        assertNotNull(transfer);
        assertEquals(3, transfer.getTransferID());
    }

    @Test
    void deleteTransfer_thenTransferIsDeleted() {
        int transferId = 3;
        when(inventoryTransferRepo.findById(transferId)).thenReturn(Optional.of(transferSample));
        inventoryTransferService.deleteTransfer(transferId);
        when(inventoryTransferRepo.findById(transferId)).thenReturn(Optional.empty());
        Optional<InventoryTransfer> transfer = inventoryTransferService.getTransferById(transferId);
        assertFalse(transfer.isPresent());
    }
}

