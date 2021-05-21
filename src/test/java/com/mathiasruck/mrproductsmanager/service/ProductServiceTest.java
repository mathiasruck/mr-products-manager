package com.mathiasruck.mrproductsmanager.service;

import static com.mathiasruck.mrproductsmanager.builder.ProductBuilder.getProduct;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductRepository;
import com.mathiasruck.mrproductsmanager.service.impl.ProductServiceImpl;

public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setup() {
        openMocks(this);
        productService = new ProductServiceImpl();
        setField(productService, "productRepository", productRepository);
    }

    @Test
    public void listAllProductsSuccessfully() {
        Product apple = getProduct()
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .build();
        Product orange = getProduct()
                .withName("Orange")
                .withPrice(1.25)
                .withRandonSku()
                .build();

        Mockito.when(productRepository.findAll()).thenReturn(of(apple, orange).collect(toList()));
        List<Product> prodList = productService.listAll();
        assertThat(prodList.size(), is(equalTo(2)));
    }

    @Test
    public void updateProductSuccessfully() {
        Product apple = getProduct()
                .withId(30L)
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(apple);

        Product prodCreated = productService.save(apple);
        assertThat(prodCreated.getId(), is(equalTo(apple.getId())));
        assertThat(prodCreated.getSku(), is(equalTo(apple.getSku())));
    }

    @Test
    public void saveNewProductSuccessfully() {
        Product apple = getProduct()
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(apple);

        Product prodCreated = productService.save(apple);
        assertThat(prodCreated.getSku(), is(equalTo(apple.getSku())));
    }

    @Test
    public void deleteProductsSuccessfully() {
    }

}
