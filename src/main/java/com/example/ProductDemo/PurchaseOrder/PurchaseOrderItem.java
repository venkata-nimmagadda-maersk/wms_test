package com.example.ProductDemo.PurchaseOrder;

import com.example.ProductDemo.Product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseOrderItem {
    @Id
    private int poItemID;

    @ManyToOne
    @JoinColumn(name = "poid", referencedColumnName = "poid")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    private Product product;

    private int quantityOrdered;
}
