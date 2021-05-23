package com.mathiasruck.mrproductsmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mathiasruck.mrproductsmanager.ProductDto;
import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.service.ProductsService;

@CrossOrigin()
@RestController
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/v1/products")
    public List<Product> listAll() {
        return productsService.listAll();
    }

    @GetMapping("/v1/products/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productsService.getById(id);
    }

    @DeleteMapping("/v1/products/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productsService.delete(id);
    }

    @PostMapping("/v1/products")
    public Product create(@RequestBody ProductDto product) {
        return productsService.save(product.get());
    }

    @PutMapping("/v1/products")
    public Product update(@RequestBody ProductDto product) {
        return productsService.save(product.get());
    }

}
