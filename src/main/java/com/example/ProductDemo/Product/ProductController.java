package com.example.ProductDemo.Product;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> products(){
        return new ResponseEntity<List<Product>>(service.getProducts(), HttpStatus.OK);
    }
    @GetMapping("/products/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId){
        if(prodId<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product=service.getProductById(prodId);
        if(product==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody @NotNull Product product) {
        if (product.getProdName() == null || product.getProdName().isEmpty()) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/products")
    public ResponseEntity<?> editProduct(@RequestBody @NotNull Product product){
        if (product.getProdId() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (product.getProdName() == null || product.getProdName().isEmpty()) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        service.editProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/products")
    public ResponseEntity<?> deleteAll(){
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/products/{prodId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int prodId){
        if (prodId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = service.getProductById(prodId);
        if (product == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        service.deleteProduct(prodId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
