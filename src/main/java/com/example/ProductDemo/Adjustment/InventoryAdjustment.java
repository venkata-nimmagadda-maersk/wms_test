package com.example.ProductDemo.Adjustment;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryAdjustment {
    @Id
    private int adjustmentID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseID", referencedColumnName = "warehouseID")
    private Warehouse warehouse;

    private String adjustmentType;
    private int quantityChange;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date adjustmentDate;
    private String reason;
}