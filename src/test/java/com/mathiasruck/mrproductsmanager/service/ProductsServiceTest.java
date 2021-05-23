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
import org.junit.Test;
import org.mockito.Mock;

import com.mathiasruck.mrproductsmanager.model.Product;
import com.mathiasruck.mrproductsmanager.repository.ProductsRepository;
import com.mathiasruck.mrproductsmanager.service.impl.ProductServiceImpl;

public class ProductsServiceTest {

    private ProductsService productsService;

    @Mock
    private ProductsRepository productsRepository;

    @Before
    public void setup() {
        openMocks(this);
        productsService = new ProductServiceImpl();
        setField(productsService, "productsRepository", productsRepository);
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

        when(productsRepository.findAll()).thenReturn(of(apple, orange).collect(toList()));
        List<Product> prodList = productsService.listAll();

        assertThat(prodList.size(), is(equalTo(2)));
    }

    @Test
    public void listAllProductsReturnsZeroItems() {

        when(productsRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> prodList = productsService.listAll();

        assertThat(prodList.size(), is(equalTo(0)));
        verify(productsRepository, times(1)).findAll();
    }

    @Test
    public void updateProductSuccessfully() {
        Product apple = getProduct()
                .withId(30L)
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .build();

        when(productsRepository.save(any(Product.class))).thenReturn(apple);
        productsService.save(apple);

        verify(productsRepository, times(1)).save(apple);
    }

    @Test
    public void saveNewProductSuccessfully() {
        Product apple = getProduct()
                .withName("Apple")
                .withPrice(1.5)
                .withRandonSku()
                .withRandonCreationDate()
                .build();
        when(productsRepository.save(any(Product.class))).thenReturn(apple);

        Product prodCreated = productsService.save(apple);
        assertThat(prodCreated.getPrice(), is(equalTo(apple.getPrice())));
        assertThat(prodCreated.getSku(), is(equalTo(apple.getSku())));
        assertThat(prodCreated.getName(), is(equalTo(apple.getName())));
        assertThat(prodCreated.getCreationDate(), is(equalTo(apple.getCreationDate())));
        verify(productsRepository, times(1)).save(apple);
    }

    @Test
    public void deleteProductSuccessfully() {
        productsService.delete(15L);

        verify(productsRepository, times(1)).deleteById(15L);
    }

    @Test
    public void getProductSuccessFully() {
        Product eggfruit = getProduct()
                .withId(10L)
                .withName("Eggfruit")
                .withPrice(1.5)
                .withRandonSku()
                .build();
        when(productsRepository.findById(any())).thenReturn(Optional.of(eggfruit));
        Product prod = productsService.getById(10L);
        assertThat(eggfruit.getId(), is(equalTo(prod.getId())));
        assertThat(eggfruit.getSku(), is(equalTo(prod.getSku())));
        verify(productsRepository, times(1)).findById(any());

    }

    @Test
    public void getWithInvalidProductId() {
        assertThrows(NoSuchElementException.class, () -> productsService.getById(10L));

    }
}
