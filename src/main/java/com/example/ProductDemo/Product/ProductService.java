package com.example.ProductDemo.Product;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.*;
@Getter
@Component
@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;
    public List<Product> getProducts(){
        return repo.findAll();
    }
    public Product getProductById(int prodId){ return repo.findById(prodId).orElse(null); }
    public void addProduct(Product product) {
        repo.save(product);
    }
    public void editProduct(Product product){repo.save(product);}
    public void deleteProduct(int prodId){repo.deleteById(prodId);}
    public void deleteAll() {
        repo.deleteAll();
    }
}
