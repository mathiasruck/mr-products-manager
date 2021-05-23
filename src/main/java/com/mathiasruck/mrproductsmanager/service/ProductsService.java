package com.mathiasruck.mrproductsmanager.service;

import java.util.List;

import com.mathiasruck.mrproductsmanager.model.Product;

public interface ProductsService {

    public List<Product> listAll();

    public Product save(Product product);

    public void delete(Long id);

    public Product getById(Long id);

}
