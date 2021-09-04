package org.aadi.ecommerce.product.service;

import lombok.AllArgsConstructor;
import org.aadi.ecommerce.product.config.ProductConfiguration;
import org.aadi.ecommerce.product.dto.Product;
import org.aadi.ecommerce.product.dto.ProductResponse;
import org.aadi.ecommerce.product.exception.CurrencyNotValidException;
import org.aadi.ecommerce.product.exception.OfferNotValidException;
import org.aadi.ecommerce.product.exception.ProductNotFoundException;
import org.aadi.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConfiguration productConfiguration;

    public ProductResponse addProduct(Product product) throws OfferNotValidException, CurrencyNotValidException {
        //log.info("adding product");
        if(product.getDiscount() > product.getPrice()){
            throw new OfferNotValidException("Invalid discount offered, as discount exceeds price.");
        }

        if(!productConfiguration.getCurrencies().contains(product.getCurrency().toUpperCase())){
            throw new CurrencyNotValidException("Invalid currency. Valid currencies are - "+productConfiguration.getCurrencies());
        }

        Product savedProduct=productRepository.save(product);
        return new ProductResponse("Success", savedProduct.getName() + "added into the system.");
    }

    public List<Product> listAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty())
        {
            throw new ProductNotFoundException("No product found for the given query");
        }
        return products;
    }

    public List<Product> productCategoryList(String category) {
        List<Product> productsByCategory = productRepository.findByCategory(category);
        if(productsByCategory == null || productsByCategory.isEmpty()){
            throw new ProductNotFoundException("No product found for the category - " + category);
        }
        return productsByCategory;
    }

    public Product productById(String id) {
        return productRepository.findById(id).orElseThrow( () -> new ProductNotFoundException("Product not found for the id -" + id) );
    }

    public ProductResponse updateProduct(Product product) {
        Optional<Product> prod = productRepository.findById(product.getId());
        if(prod.isEmpty()){
            return new ProductResponse("FAILED", "Product to be updated not found in the system.");
        }
        Product updatedProduct = productRepository.save(product);
        return new ProductResponse("SUCCESS", "Product updated - " + updatedProduct.getName());
    }

    public ProductResponse deleteProductById(String id) {
        Optional<Product> prod = productRepository.findById(id);
        if(prod.isEmpty()){
            return new ProductResponse("FAILED", "Product to be deleted not found in the system.");
        }
        productRepository.deleteById(id);
        return new ProductResponse("SUCCESS", "Product deleted.");
    }
}