package com.example.ProductDemo.BalanceTest;

import com.example.ProductDemo.Balance.InventoryBalance;
import com.example.ProductDemo.Balance.InventoryBalanceRepo;
import com.example.ProductDemo.Balance.InventoryBalanceService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.Stock.Stock;
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
public class InventoryBalanceServiceTest {
    @Mock
    private InventoryBalanceRepo inventoryBalanceRepo;
    @InjectMocks
    private InventoryBalanceService inventoryBalanceService;

    List<InventoryBalance> balanceSampleList;
    InventoryBalance balanceSample;

    @BeforeEach
    void initSamples() {
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        Stock stock = new Stock(1, product, warehouse, 100, 5, 0, new Date());
        balanceSampleList = new ArrayList<>(
                List.of(
                        new InventoryBalance(1L, product, warehouse, stock, 95, new Date()),
                        new InventoryBalance(2L, product, warehouse, stock, 90, new Date())
                )
        );
        balanceSample = new InventoryBalance(3L, product, warehouse, stock, 80, new Date());
    }

    @Test
    void getAllBalancesTest_thenReturnsBalances() {
        when(inventoryBalanceRepo.findAll()).thenReturn(balanceSampleList);
        List<InventoryBalance> balances = inventoryBalanceService.getAllBalances();
        assertEquals(2, balances.size());
        assertEquals(1L, balances.get(0).getBalanceID());
    }

    @Test
    void getBalanceById_thenReturnsBalance() {
        Long balId = 3L;
        when(inventoryBalanceRepo.findById(balId.intValue())).thenReturn(Optional.of(balanceSample));
        Optional<InventoryBalance> bal = inventoryBalanceService.getBalanceById(balId);
        assertTrue(bal.isPresent());
        assertEquals(balId, bal.get().getBalanceID());
    }

    @Test
    void saveBalance_thenReturnsBalance() {
        when(inventoryBalanceRepo.save(balanceSample)).thenReturn(balanceSample);
        InventoryBalance bal = inventoryBalanceService.saveBalance(balanceSample);
        assertNotNull(bal);
        assertEquals(3L, bal.getBalanceID());
    }

    @Test
    void deleteBalance_thenBalanceIsDeleted() {
        Long balId = 3L;
        when(inventoryBalanceRepo.findById(balId.intValue())).thenReturn(Optional.of(balanceSample));
        inventoryBalanceService.deleteBalance(balId);
        when(inventoryBalanceRepo.findById(balId.intValue())).thenReturn(Optional.empty());
        Optional<InventoryBalance> bal = inventoryBalanceService.getBalanceById(balId);
        assertFalse(bal.isPresent());
    }
}
