package com.example.ProductDemo.InventoryTransaction;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryTransaction {
    @Id
    private int transactionID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseID", referencedColumnName = "warehouseID")
    private Warehouse warehouse;

    private String transactionType;
    private int quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date transactionDate;
    private int referenceID;
    private String remarks;
}
