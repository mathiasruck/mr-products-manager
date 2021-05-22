package com.mathiasruck.mrproductsmanager;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathiasruck.mrproductsmanager.model.Product;

public class ProductDto {
    private Product product;

    public ProductDto() {
        product = new Product();
    }

    public Long getId() {
        return product.getId();
    }

    public void setId(Long id) {
        product.setId(id);
    }

    public String getSku() {
        return product.getSku();
    }

    public void setSku(String sku) {
        product.setSku(sku);
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name) {
        product.setName(name);
    }

    public Double getPrice() {
        return product.getPrice();
    }

    public void setPrice(Double price) {
        product.setPrice(price);
    }

    public Date getCreationDate() {
        return product.getCreationDate();
    }

    public void setCreationDate(Date creationDate) {
        product.setCreationDate(creationDate);
    }

    @JsonIgnore
    public Product get() {
        return product;
    }
}
