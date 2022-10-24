package com.app.api;

import com.app.persistence.entities.Discount;
import com.app.persistence.entities.Product;
import com.app.persistence.entities.Stock;
import com.app.services.ProductService;
import com.app.services.StockService;
import com.app.utils.dto.ProductCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;

    @PostMapping("/newStock")
    public Stock addNewStock(Model model){
        var stock = stockService.addNewStock();
        model.addAttribute("stock",stock);

        return stock;
    }

    @PostMapping("/newProduct")
    public Product addnewProduct(@RequestBody ProductCreationDto productCreationDto, Model model){
        var product = productService.createProduct(productCreationDto);
        var stock = addNewStock(model);

        model.addAttribute("add", stockService.addProductToStock(stock.getID(), product, productCreationDto.getCount()));
        return product;
    }

    @PutMapping("/update")
    public void updateProductStock(@RequestParam long id, @RequestParam int quantity){
        stockService.updateProductOfStock(id, quantity);

    }

    @GetMapping("/pageProducts")
    public Page<Product> sortByDiscount(@RequestParam@Min(0)@Max(10) int page,
                                        @RequestParam@Min(0)@Max(15) int pageNumber, Model model){
        var sorted = productService.pageAllProducts(page, pageNumber);
        model.addAttribute("sort", sorted);
        return sorted;
    }

    @PostMapping("discount/create")
    public Discount createDiscount( float percent){
        return productService.createDiscount(percent);
    }

    @PostMapping("discount/prod")
    public String addDiscountToProduct(@RequestParam float percent, @RequestParam long id){
        var discount = createDiscount(percent);
        productService.addDiscountToProduct(discount.getID(), id);
        return "discount";
    }

    @PutMapping("/categorizing")
    public void cathegorizeProduct(@RequestParam long idP, long idC){
        productService.addCathegoryToProduct(idC, idP);

    }

}
