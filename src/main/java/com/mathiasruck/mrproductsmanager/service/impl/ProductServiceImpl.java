package com.mathiasruck.mrproductsmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductsRepository;
import com.mathiasruck.mrproductsmanager.service.ProductsService;

@Service
public class ProductServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Product> listAll() {
        List<Product> prodList = new ArrayList<>();
        productsRepository.findAll().forEach(prodList::add);
        return prodList;
    }

    @Override
    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productsRepository.deleteById(id);

    }

    @Override
    public Product getById(Long id) {
        return productsRepository.findById(id).orElseThrow();
    }

}
