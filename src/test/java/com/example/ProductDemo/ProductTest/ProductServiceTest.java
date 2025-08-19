package com.example.ProductDemo.ProductTest;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Product.ProductRepo;
import com.example.ProductDemo.Product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService productService;
    List<Product> productSampleList;
    Product productSample;

    @BeforeEach
    void initSamples() {
        productSampleList = new ArrayList<>(
                List.of(
                        new Product(1, "P1", new BigDecimal("100"), "C1", "CN1"),
                        new Product(2, "P2", new BigDecimal("150"), "C2", "CN2")
                )
        );
        productSample = new Product(3, "p3", new BigDecimal("250"), "C3", "CN3");
    }
    @Test
    void getProductsTest_thenReturnsProducts(){
        when(productRepo.findAll()).thenReturn(productSampleList);
        List<Product> products = productService.getProducts();
        assertEquals(1,products.get(0).getProdId());
        assertEquals(2,products.size());
    }
    @Test
    void getProductById_thenReturnsProduct(){
        int prId = 3;
        when(productRepo.findById(prId)).thenReturn(Optional.of(productSample));
        Product prod = productService.getProductById(prId);
        assertNotNull(prod);
        assertEquals(prId, prod.getProdId());
        assertEquals("p3", prod.getProdName());
    }
    @Test
    void addProduct_thenReturnsNothing() {
        when(productRepo.save(productSample)).thenReturn(productSample);
        when(productRepo.findById(productSample.getProdId())).thenReturn(Optional.of(productSample));
        productService.addProduct(productSample);
        Product prod = productService.getProductById(productSample.getProdId());
        assertNotNull(prod);
        assertEquals(3, prod.getProdId());
        assertEquals("p3", prod.getProdName());
    }
    @Test
    void deleteProduct_thenReturnsNothing() {
        int prId = 3;
        when(productRepo.findById(prId)).thenReturn(Optional.of(productSample));
        productService.deleteProduct(prId);
        when(productRepo.findById(prId)).thenReturn(Optional.empty());
        when(productRepo.findById(1)).thenReturn(Optional.of(productSampleList.get(0)));
        Product prod = productService.getProductById(prId);
        Product prod1 =productService.getProductById(1);
        assertNull(prod);
        assertNotNull(prod1);
    }
}