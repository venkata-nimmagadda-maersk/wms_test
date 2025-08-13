package com.example.ProductDemo.Warehouse;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Warehouse {
    @Id
    private int warehouseID;
    private String warehouseName;
    private String location;
}
