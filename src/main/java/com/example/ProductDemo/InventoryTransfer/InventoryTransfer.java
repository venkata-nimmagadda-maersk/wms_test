package com.example.ProductDemo.InventoryTransfer;

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
public class InventoryTransfer {
    @Id
    private int transferID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fromWarehouseID", referencedColumnName = "warehouseID")
    private Warehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "toWarehouseID", referencedColumnName = "warehouseID")
    private Warehouse toWarehouse;

    private int quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date transferDate;
    private String referenceNote;
}
