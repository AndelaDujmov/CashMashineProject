package com.app.api;

import com.app.persistence.entities.Product;
import com.app.persistence.entities.Stock;
import com.app.services.ProductService;
import com.app.services.StockService;
import com.app.utils.dto.ProductCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;

    @GetMapping("/newProduct")
    public void addnewProduct(@RequestBody ProductCreationDto productCreationDto){
        var product = productService.createProduct(productCreationDto);

        var stock = new Stock();
        stock.setProduct(product);
        stock.setQuantityPerProduct(productCreationDto.getCount());
        stockService.save(stock);
    }


}
