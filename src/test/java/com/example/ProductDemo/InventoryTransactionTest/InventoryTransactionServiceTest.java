package com.example.ProductDemo.InventoryTransactionTest;

import com.example.ProductDemo.InventoryTransaction.InventoryTransaction;
import com.example.ProductDemo.InventoryTransaction.InventoryTransactionRepo;
import com.example.ProductDemo.InventoryTransaction.InventoryTransactionService;
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
public class InventoryTransactionServiceTest {

    @Mock
    private InventoryTransactionRepo inventoryTransactionRepo;
    @InjectMocks
    private InventoryTransactionService inventoryTransactionService;

    List<InventoryTransaction> transactionSampleList;
    InventoryTransaction transactionSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", new BigDecimal("100"), "C1", "CN1");
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        transactionSampleList = new ArrayList<>(
                List.of(
                        new InventoryTransaction(1, product, warehouse, "IN", 10, new Date(), 1001, "Initial Stock"),
                        new InventoryTransaction(2, product, warehouse, "OUT", 5, new Date(), 1002, "Order Shipped")
                )
        );
        transactionSample = new InventoryTransaction(3, product, warehouse, "IN", 20, new Date(), 1003, "Restock");
    }

    @Test
    void getAllTransactionsTest_thenReturnsTransactions() {
        when(inventoryTransactionRepo.findAll()).thenReturn(transactionSampleList);
        List<InventoryTransaction> transactions = inventoryTransactionService.getAllTransactions();
        assertEquals(2, transactions.size());
        assertEquals(1, transactions.get(0).getTransactionID());
    }

    @Test
    void getTransactionById_thenReturnsTransaction() {
        int transactionId = 3;
        when(inventoryTransactionRepo.findById(transactionId)).thenReturn(Optional.of(transactionSample));
        Optional<InventoryTransaction> transaction = inventoryTransactionService.getTransactionById(transactionId);
        assertTrue(transaction.isPresent());
        assertEquals(transactionId, transaction.get().getTransactionID());
    }

    @Test
    void saveTransaction_thenReturnsTransaction() {
        when(inventoryTransactionRepo.save(transactionSample)).thenReturn(transactionSample);
        InventoryTransaction transaction = inventoryTransactionService.saveTransaction(transactionSample);
        assertNotNull(transaction);
        assertEquals(3, transaction.getTransactionID());
    }

    @Test
    void deleteTransaction_thenTransactionIsDeleted() {
        int transactionId = 3;
        when(inventoryTransactionRepo.findById(transactionId)).thenReturn(Optional.of(transactionSample));
        inventoryTransactionService.deleteTransaction(transactionId);
        when(inventoryTransactionRepo.findById(transactionId)).thenReturn(Optional.empty());
        Optional<InventoryTransaction> transaction = inventoryTransactionService.getTransactionById(transactionId);
        assertFalse(transaction.isPresent());
    }
}

