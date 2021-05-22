package com.mathiasruck.mrproductsmanager.builder;

import java.util.Date;
import java.util.UUID;

import com.mathiasruck.mrproductsmanager.model.Product;

public class ProductBuilder {

    Product product;

    private ProductBuilder() {
    }

    public static ProductBuilder getProduct() {
        ProductBuilder builder = new ProductBuilder();
        builder.product = new Product();
        return builder;
    }

    public ProductBuilder withPrice(Double price) {
        product.setPrice(price);
        return this;

    }

    public ProductBuilder withRandonSku() {
        product.setSku(UUID.randomUUID().toString());
        return this;
    }

    public ProductBuilder withSku(String sku) {
        product.setSku(sku);
        return this;
    }

    public ProductBuilder withRandonCreationDate() {
        product.setCreationDate(new Date());
        return this;
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withId(Long id) {
        product.setId(id);
        return this;
    }

    public Product build() {
        return product;
    }
}
