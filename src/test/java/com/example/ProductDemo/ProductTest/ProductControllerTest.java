package com.example.ProductDemo.ProductTest;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Product.ProductController;
import com.example.ProductDemo.Product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
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
    void testGetAllProducts() throws Exception {
        when(productService.getProducts()).thenReturn(productSampleList);
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    private void performGetProductById(int prId) throws Exception {
        when(productService.getProductById(prId)).thenReturn(productSampleList.get(prId - 1));
        mvc.perform(get("/api/products/" + prId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetProductById() throws Exception {
        performGetProductById(2);
    }

    private void postProduct(String JsonContent) throws Exception {
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonContent))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void testPostProduct() throws Exception {
        postProduct("{\"prodId\":3,\"prodName\":\"p3\",\"price\":250,\"category\":\"C3\",\"categoryName\":\"CN3\"}");
        performGetProductById(2);
    }

    @Test
    void testDeleteProduct() throws Exception {
        postProduct("{\"prodId\":3,\"prodName\":\"p3\",\"price\":250,\"category\":\"C3\",\"categoryName\":\"CN3\"}");
        mvc.perform(delete("/api/products/3"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}