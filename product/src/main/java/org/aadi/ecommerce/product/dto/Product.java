package org.aadi.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private String id;

    @NotNull(message = "Product name should not be null")
    private String name;

    @NotNull(message = "Product category should not be null")
    private Category category;

    @Min(0)
    private double price;

    private String currency;

    @Max(100)
    @Min(0)
    private double discount;

    private String discountDescription;
    private List<String> imageURLs;

}