package com.app.utils.filters;

import com.app.utils.enums.ProductType;
import lombok.Data;

@Data
public class PriceFilter {
    private ProductType type;

    public double calculate(double price) {
        switch (type) {
            case Beverage:
                return price * 0.25;
            case Food:
                return price * 0.15;
            case Cloth:
                return price * 0.25;

        }

        return price;
    }
}
