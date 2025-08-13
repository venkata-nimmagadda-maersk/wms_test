package com.example.ProductDemo.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
public class
Product {
    @Id
    private int prodId;
    private String prodName;
    private BigDecimal price;
    private String category;
    private String categoryName;
}
