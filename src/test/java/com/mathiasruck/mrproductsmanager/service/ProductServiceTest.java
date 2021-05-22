package com.mathiasruck.mrproductsmanager.service;

import static com.mathiasruck.mrproductsmanager.builder.ProductBuilder.getProduct;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductRepository;
import com.mathiasruck.mrproductsmanager.service.impl.ProductServiceImpl;

public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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

        when(productRepository.findAll()).thenReturn(of(apple, orange).collect(toList()));
        List<Product> prodList = productService.listAll();

        assertThat(prodList.size(), is(equalTo(2)));
    }

    @Test
    public void listAllProductsReturnsZeroItems() {

        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> prodList = productService.listAll();

        assertThat(prodList.size(), is(equalTo(0)));
        verify(productRepository, times(1)).findAll();
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
        productService.save(apple);

        verify(productRepository, times(1)).save(apple);
    }

    @Test
    public void saveNewProductSuccessfully() {
        Product apple = getProduct()
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .withRandonCreationDate()
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(apple);

        Product prodCreated = productService.save(apple);
        assertThat(prodCreated.getPrice(), is(equalTo(apple.getPrice())));
        assertThat(prodCreated.getSku(), is(equalTo(apple.getSku())));
        assertThat(prodCreated.getName(), is(equalTo(apple.getName())));
        assertThat(prodCreated.getCreationDate(), is(equalTo(apple.getCreationDate())));
        verify(productRepository, times(1)).save(apple);
    }

    @Test
    public void deleteProductSuccessfully() {
        productService.delete(15L);

        verify(productRepository, times(1)).deleteById(15L);
    }

    @Test
    public void getProductSuccessFully() {
        Product eggfruit = getProduct()
                .withId(10L)
                .withName("Eggfruit")
                .withPrice(1.5)
                .withRandonSku()
                .build();
        when(productRepository.findById(any())).thenReturn(Optional.of(eggfruit));
        Product prod = productService.getById(10L);
        assertThat(eggfruit.getId(), is(equalTo(prod.getId())));
        assertThat(eggfruit.getSku(), is(equalTo(prod.getSku())));
        verify(productRepository, times(1)).findById(any());

    }

    @Test
    public void getUnexistentProduct() {
        assertThrows(NoSuchElementException.class, () -> productService.getById(10L));

    }
}
