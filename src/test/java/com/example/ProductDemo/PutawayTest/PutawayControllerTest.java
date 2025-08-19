package com.example.ProductDemo.PutawayTest;

import com.example.ProductDemo.Putaway.Putaway;
import com.example.ProductDemo.Putaway.PutawayController;
import com.example.ProductDemo.Putaway.PutawayService;
import com.example.ProductDemo.Product.Product;
import com.example.ProductDemo.Warehouse.Warehouse;
import com.example.ProductDemo.PurchaseOrder.PurchaseOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PutawayController.class)
public class PutawayControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PutawayService putawayService;

    List<Putaway> putawaySampleList;
    Putaway putawaySample;

    @BeforeEach
    void initSamples() {
        PurchaseOrder po = new PurchaseOrder(1, "SupplierA", new Date(), new Date(), "Pending");
        Product product = new Product(1, "P1", null, null, null);
        Warehouse warehouse = new Warehouse(1, "W1", "Loc1");
        putawaySampleList = new ArrayList<>(
                List.of(
                        new Putaway(1, po, product, warehouse, 10, new Date()),
                        new Putaway(2, po, product, warehouse, 20, new Date())
                )
        );
        putawaySample = new Putaway(3, po, product, warehouse, 30, new Date());
    }

    @Test
    void testGetAllPutaways() throws Exception {
        when(putawayService.getAllPutaways()).thenReturn(putawaySampleList);
        mvc.perform(get("/api/putaways"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPutawayById() throws Exception {
        when(putawayService.getPutawayById(1)).thenReturn(Optional.of(putawaySampleList.get(0)));
        mvc.perform(get("/api/putaways/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePutaway() throws Exception {
        when(putawayService.savePutaway(putawaySample)).thenReturn(putawaySample);
        mvc.perform(post("/api/putaways")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"putawayID\":3,\"purchaseOrder\":{\"poid\":1,\"supplierName\":\"SupplierA\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityPutaway\":30,\"putawayDate\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePutaway() throws Exception {
        when(putawayService.getPutawayById(3)).thenReturn(Optional.of(putawaySample));
        when(putawayService.savePutaway(putawaySample)).thenReturn(putawaySample);
        mvc.perform(put("/api/putaways/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"putawayID\":3,\"purchaseOrder\":{\"poid\":1,\"supplierName\":\"SupplierA\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityPutaway\":30,\"putawayDate\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAllPutaways() throws Exception {
        doNothing().when(putawayService).deleteAllPutaways();
        mvc.perform(delete("/api/putaways"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePutaway() throws Exception {
        when(putawayService.getPutawayById(3)).thenReturn(Optional.of(putawaySample));
        doNothing().when(putawayService).deletePutaway(3);
        mvc.perform(delete("/api/putaways/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testPutawayStockWithSP() throws Exception {
        doNothing().when(putawayService).putawayStockWithSP(putawaySample);
        mvc.perform(post("/api/putaways/sp")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"putawayID\":3,\"purchaseOrder\":{\"poid\":1,\"supplierName\":\"SupplierA\",\"orderDate\":\"2024-06-01\",\"expectedDeliveryDate\":\"2024-06-10\",\"status\":\"Pending\"},\"product\":{\"prodId\":1,\"prodName\":\"P1\"},\"warehouse\":{\"warehouseID\":1,\"warehouseName\":\"W1\",\"location\":\"Loc1\"},\"quantityPutaway\":30,\"putawayDate\":\"2024-06-01\"}"))
                .andExpect(status().isOk());
    }
}

