package com.mathiasruck.mrproductsmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductRepository;
import com.mathiasruck.mrproductsmanager.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> listAll() {
        List<Product> prodList = new ArrayList<>();
        productRepository.findAll().forEach(prodList::add);
        return prodList;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

}
