package com.example.ProductDemo.Balance;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Stock.Stock;
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
public class InventoryBalance {
    @Id
    private Long balanceID;

    @ManyToOne
    @JoinColumn(name = "productID",referencedColumnName = "prodId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseID",referencedColumnName = "warehouseID")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "stockID", referencedColumnName = "stockID")
    private Stock stock;

    private int quantityOnHand;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date lastUpdated;

    public void updateQuantityOnHandFromStock() {
        if (stock != null) {
            this.quantityOnHand = stock.getQuantityAvailable() - stock.getQuantityDamaged();
        }
    }
}