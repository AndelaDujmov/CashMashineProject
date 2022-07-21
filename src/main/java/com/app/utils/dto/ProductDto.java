package com.app.utils.dto;

import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Discount;
import com.app.utils.enums.ProductType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private final long ID;
    private final String name;
    private final String productCode;
    private final ProductType type;
    private final Discount discount;
    private final double price;
    private final Cathegory cathegory;
    private final boolean deleted;
}
