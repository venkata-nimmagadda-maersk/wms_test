
---

## ✅ Recent Changes

### 1. **Controller Enhancements**

The `InventoryAdjustmentController` now includes:

- **Swagger/OpenAPI annotations** for all endpoints using:
  - `@Operation`
  - `@ApiResponse`
  - `@RequestBody`
  - `@Schema` and `@ArraySchema`

- **New Endpoint**:
  - `POST /api/inventory-adjustments/procedure`: Executes inventory adjustment via a stored procedure.

- **Improved Response Handling**:
  - Uses `ResponseEntity` for consistent HTTP status codes (`200`, `201`, `204`, `404`).

---

### 2. **DTO Enhancements**

The `InventoryAdjustment` DTO now includes:

- **Swagger `@Schema` annotations** with:
  - Descriptions for each field.
  - Example values for better Swagger UI representation.

- **Date Formatting**:
  - `@JsonFormat` applied to `adjustmentDate` for consistent date format (`yyyy-MM-dd`).

#### Annotated Fields:

```java
@Schema(description = "Adjustment identifier", example = "101")
private int adjustmentID;

@Schema(description = "Reference to product entity")
private Product product;

@Schema(description = "Reference to warehouse entity")
private Warehouse warehouse;

@Schema(description = "Type of adjustment", example = "MANUAL")
private String adjustmentType;

@Schema(description = "Quantity change applied by this adjustment", example = "5")
private int quantityChange;

@Schema(description = "Adjustment date", example = "2025-10-14")
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
private Date adjustmentDate;

@Schema(description = "Reason for adjustment", example = "Stock recount")
private String reason;

```
