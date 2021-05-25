package com.mathiasruck.mrproductsmanager;

import static com.mathiasruck.mrproductsmanager.builder.ProductBuilder.getProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductsRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private ProductsRepository productsRepository;

    @Autowired
    public DataLoader(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (Iterables.size(productsRepository.findAll()) > 0) {
            return;
        }
        Product smartphone = getProduct()
                .withName("Smartphone")
                .withPrice(1000.0)
                .withRandonCreationDate()
                .withRandonSku()
                .build();
        Product computer = getProduct()
                .withName("Computer")
                .withPrice(1250.0)
                .withRandonCreationDate()
                .withRandonSku()
                .build();

        productsRepository.save(smartphone);
        productsRepository.save(computer);
    }
}
