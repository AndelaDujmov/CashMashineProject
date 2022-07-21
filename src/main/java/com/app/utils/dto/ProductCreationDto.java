package com.app.utils.dto;

import com.app.utils.enums.ProductType;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ProductCreationDto implements Serializable {
    @NotBlank(message = "Product name must not be empty")
    @Size(min = 3, max = 25, message = "Product name should be 3 to 25 characters long")
    private final String name;
    @NotBlank(message = "Product code should not be empty")
    @Pattern(regexp = "[A-Z1-9]+")
    private final String productCode;
    @NonNull
    private final double price;
    @NotBlank(message = "enter the number of new products - in store")
    private Integer count;
    @NonNull
    private ProductType type;
}
