package com.example.ProductDemo.Adjustment;

import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "InventoryAdjustment", description = "Represents an inventory adjustment record")
public class InventoryAdjustment {
    @Id
    @Schema(description = "Adjustment identifier", example = "101")
    private int adjustmentID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "prodId")
    @Schema(description = "Reference to product entity")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouseID", referencedColumnName = "warehouseID")
    @Schema(description = "Reference to warehouse entity")
    private Warehouse warehouse;

    @Schema(description = "Type of adjustment", example = "MANUAL")
    private String adjustmentType;

    @Schema(description = "Quantity change applied by this adjustment", example = "5")
    private int quantityChange;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Adjustment date", example = "2025-10-14")
    private Date adjustmentDate;

    @Schema(description = "Reason for adjustment", example = "Stock recount")
    private String reason;
}