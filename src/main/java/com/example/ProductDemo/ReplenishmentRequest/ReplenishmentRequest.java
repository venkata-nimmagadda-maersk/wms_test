package com.example.ProductDemo.ReplenishmentRequest;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReplenishmentRequest {
    @Id
    private int requestID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fromWarehouseID", referencedColumnName = "warehouseID")
    private Warehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "toWarehouseID", referencedColumnName = "warehouseID")
    private Warehouse toWarehouse;

    private int quantityRequested;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date requestDate;
    private String status;
}
