package org.aadi.ecommerce.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.aadi.ecommerce.product.dto.Product;
import org.aadi.ecommerce.product.dto.ProductResponse;
import org.aadi.ecommerce.product.exception.CurrencyNotValidException;
import org.aadi.ecommerce.product.exception.OfferNotValidException;
import org.aadi.ecommerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.aadi.ecommerce.product.advice.TrackExecutionTime;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings({"deprecation", "CommentedOutCode"})
@RestController
@RequestMapping("/v1")
@Api(description = "Product API Class having endpoint to interact with product Microservice")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    @ApiOperation("Used to add the product into system")
    public ResponseEntity<ProductResponse> addProduct(@ApiParam("Information about products to be added") @RequestBody @Valid Product product) throws OfferNotValidException, CurrencyNotValidException {
        ProductResponse productResponse = productService.addProduct(product);
        //log.info("Product added status - {}",status);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/productList")
    public List<Product> productList(){
        /*
        log.info("Listing product");
        log.info("All the product returned - {}", productList);
        */
        return productService.listAllProducts();
    }

    //@TrackExecutionTime
    @GetMapping("/productList/{category}")
    public List<Product> productCategoryList(@ApiParam("category of the products to be listed") @PathVariable String category) {
        return productService.productCategoryList(category);
    }

    @GetMapping("/product/{id}")
    public Product productById(@PathVariable String id){
        return productService.productById(id);
    }

    @PutMapping("/productUpdate")
    public ProductResponse updateProduct(@RequestBody @Valid Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public ProductResponse deleteProductById(@PathVariable String id){
        return productService.deleteProductById(id);
    }

}