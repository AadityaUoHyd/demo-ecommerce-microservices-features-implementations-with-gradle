package org.aadi.ecommerce.product.service;

import org.aadi.ecommerce.product.config.ProductConfiguration;
import org.aadi.ecommerce.product.dto.Product;
import org.aadi.ecommerce.product.dto.ProductResponse;
import org.aadi.ecommerce.product.exception.CurrencyNotValidException;
import org.aadi.ecommerce.product.exception.OfferNotValidException;
import org.aadi.ecommerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductConfiguration productConfiguration;
    private ProductService productService;

    @BeforeEach()
    void setup(){
        productRepository = Mockito.mock(ProductRepository.class);
        productConfiguration = new ProductConfiguration();
        productConfiguration.setCurrencies(List.of("INR","USD","EUR"));
        productService = new ProductService(productRepository, productConfiguration);
    }

    @Test
    @DisplayName("Offer not valid scenario")
    void addProductOfferNotValidException() throws OfferNotValidException {

        Product product = new Product();
        product.setPrice(0.0);
        product.setDiscount(10);

        assertThrows(OfferNotValidException.class, () -> productService.addProduct(product));
    }

    @Test
    @DisplayName("Currency not valid scenario")
    void addProductCurrencyNotValidException() throws CurrencyNotValidException {

        Product product = new Product();
        product.setPrice(1000.0);
        product.setDiscount(10);
        product.setCurrency("DINAR");

        assertThrows(CurrencyNotValidException.class, () -> productService.addProduct(product));
    }

    @Test
    @DisplayName("Happy Path testing - Saving product scenario")
    void addProductSaved() throws OfferNotValidException {

        Product product = new Product();
        product.setName("The Complete Reference of Java");
        product.setPrice(1000.0);
        product.setDiscount(10);
        product.setCurrency("INR");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse actualResult = productService.addProduct(product);
        ProductResponse expectedResult = new ProductResponse("Success", product.getName() + "added into the system.");

        Assertions.assertThat(expectedResult).isEqualTo(actualResult);
    }
}