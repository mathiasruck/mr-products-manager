package com.mathiasruck.mrproductsmanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.mathiasruck.mrproductsmanager.model.Product;

public interface ProductsRepository extends CrudRepository<Product, Long> {

}
