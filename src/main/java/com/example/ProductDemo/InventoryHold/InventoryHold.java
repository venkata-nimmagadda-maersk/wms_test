package com.example.ProductDemo.InventoryHold;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InventoryHold {
    @Id
    private int holdID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseID", referencedColumnName = "warehouseID")
    private Warehouse warehouse;

    private int quantityHeld;
    private String holdReason;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date holdDate;
    private Date releaseDate;
}
