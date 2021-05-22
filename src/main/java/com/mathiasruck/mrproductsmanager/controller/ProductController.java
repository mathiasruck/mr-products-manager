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
import com.mathiasruck.mrproductsmanager.service.ProductService;

@CrossOrigin()
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/v1/product")
    public List<Product> listAll() {
        return productService.listAll();
    }

    @GetMapping("/v1/product/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    @DeleteMapping("/v1/product/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PostMapping("/v1/product")
    public Product create(@RequestBody ProductDto product) {
        return productService.save(product.get());
    }

    @PutMapping("/v1/product")
    public Product update(@RequestBody ProductDto product) {
        return productService.save(product.get());
    }

}
