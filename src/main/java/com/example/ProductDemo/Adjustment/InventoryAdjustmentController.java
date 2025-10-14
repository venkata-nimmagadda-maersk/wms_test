package com.example.ProductDemo.Adjustment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@CrossOrigin
@RestController
@RequestMapping("/api/inventory-adjustments")
public class InventoryAdjustmentController {

    private final InventoryAdjustmentService inventoryAdjustmentService;

    public InventoryAdjustmentController(InventoryAdjustmentService inventoryAdjustmentService) {
        this.inventoryAdjustmentService = inventoryAdjustmentService;
    }

    @Operation(summary = "List inventory adjustments", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = InventoryAdjustment.class))))
    })
    @GetMapping
    public ResponseEntity<List<InventoryAdjustment>> getAllAdjustments() {
        return ResponseEntity.ok(inventoryAdjustmentService.getAllAdjustments());
    }

    @Operation(summary = "Get an inventory adjustment by id", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryAdjustment.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryAdjustment> getAdjustmentById(@PathVariable int id) {
        return inventoryAdjustmentService.getAdjustmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create an inventory adjustment", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "InventoryAdjustment payload (uses InventoryAdjustment schema)", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class))
    ), responses = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class)))
    })
    @PostMapping
    public ResponseEntity<InventoryAdjustment> createAdjustment(@RequestBody InventoryAdjustment adjustment) {
        InventoryAdjustment created = inventoryAdjustmentService.saveAdjustment(adjustment);
        // build Location header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getAdjustmentID())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update an inventory adjustment", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "InventoryAdjustment payload (uses InventoryAdjustment schema)", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class))
    ), responses = {
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventoryAdjustment> updateAdjustment(@PathVariable int id, @RequestBody InventoryAdjustment adjustment) {
        return inventoryAdjustmentService.getAdjustmentById(id)
                .map(existing -> {
                    adjustment.setAdjustmentID(id);
                    InventoryAdjustment updated = inventoryAdjustmentService.saveAdjustment(adjustment);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete all inventory adjustments", responses = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAllAdjustments() {
        inventoryAdjustmentService.deleteAllAdjustments();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete adjustment by id", responses = {
            @ApiResponse(responseCode = "204", description = "Deleted (no content)"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdjustment(@PathVariable int id) {
        if (inventoryAdjustmentService.getAdjustmentById(id).isPresent()) {
            inventoryAdjustmentService.deleteAdjustment(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Adjust inventory via stored procedure", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "InventoryAdjustment payload (uses InventoryAdjustment schema)", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class))
    ), responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryAdjustment.class)))
    })
    @PostMapping("/procedure")
    public ResponseEntity<InventoryAdjustment> adjustInventoryWithProcedure(@RequestBody(required = true) InventoryAdjustment adjustment) {
        if (adjustment == null) {
            return ResponseEntity.badRequest().build();
        }
        inventoryAdjustmentService.adjustInventoryWithProcedure(adjustment);
        return ResponseEntity.ok(adjustment);
    }

}
