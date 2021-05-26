package com.mathiasruck.mrproductsmanager.controller;

import static com.mathiasruck.mrproductsmanager.builder.ProductBuilder.getProduct;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.service.ProductsService;

@WebMvcTest(controllers = ProductsController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService service;

    @Test
    public void listAllShouldReturnAllAvalilableProducts() throws Exception {
        Product apple = getProduct()
                .withId(1L)
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .build();
        Product orange = getProduct()
                .withId(2L)
                .withName("Orange")
                .withPrice(1.25)
                .withRandonSku()
                .build();
        when(service.listAll()).thenReturn(of(apple, orange).collect(toList()));
        this.mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("Apple"))
                .andExpect(jsonPath("$.[0].sku").value(apple.getSku()))
                .andExpect(jsonPath("$.[0].price").value(1.5))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].name").value("Orange"))
                .andExpect(jsonPath("$.[1].sku").value(orange.getSku()))
                .andExpect(jsonPath("$.[1].price").value(1.25));
    }

    @Test
    public void getByProductShouldReturnCorrectProduc() throws Exception {
        Product orange = getProduct()
                .withId(2L)
                .withName("Orange")
                .withPrice(1.25)
                .withRandonSku()
                .build();
        when(service.getById(any(Long.class))).thenReturn(orange);
        this.mockMvc.perform(get("/v1/products/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Orange"))
                .andExpect(jsonPath("$.sku").value(orange.getSku()))
                .andExpect(jsonPath("$.price").value(1.25));
    }

    @Test
    public void createNewProductShouldReturnCorrectly() throws Exception {
        Product orangeToSend = getProduct()
                .withName("Orange")
                .withPrice(1.25)
                .withSku("sku-value")
                .build();

        Product orangeSaved = getProduct()
                .withId(5L)
                .withName("Orange")
                .withPrice(1.25)
                .withSku("sku-value")
                .build();

        String productJson = new ObjectMapper().writeValueAsString(orangeToSend);
        when(service.save(any(Product.class))).thenReturn(orangeSaved);
        this.mockMvc.perform(post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("Orange"))
                .andExpect(jsonPath("$.sku").value(orangeToSend.getSku()))
                .andExpect(jsonPath("$.price").value(1.25));
    }

    @Test
    public void deleteProductSuccessfully() throws Exception {
        this.mockMvc.perform(delete("/v1/products/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void udpateProductSuccessfully() throws Exception {

        Product orangeToSend = getProduct()
                .withId(5L)
                .withName("Orange")
                .withPrice(1.25)
                .withSku("sku-value")
                .build();

        Product orangeSaved = getProduct()
                .withId(5L)
                .withName("Orange")
                .withPrice(1.25)
                .withSku("sku-value")
                .build();

        String productJson = new ObjectMapper().writeValueAsString(orangeToSend);
        when(service.save(any(Product.class))).thenReturn(orangeSaved);
        this.mockMvc.perform(put("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("Orange"))
                .andExpect(jsonPath("$.sku").value(orangeToSend.getSku()))
                .andExpect(jsonPath("$.price").value(1.25));
    }
}
