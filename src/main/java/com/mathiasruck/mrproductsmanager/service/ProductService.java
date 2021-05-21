package com.mathiasruck.mrproductsmanager.service;

import java.util.List;

import com.mathiasruck.mrproductsmanager.model.Product;

public interface ProductService {

    public List<Product> listAll();

    public Product save(Product product);

}
