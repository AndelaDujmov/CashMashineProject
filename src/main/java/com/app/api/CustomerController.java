package com.app.api;

import com.app.persistence.entities.Product;
import com.app.persistence.entities.Receipt;
import com.app.persistence.entities.Transaction;
import com.app.services.ProductService;
import com.app.services.StockService;
import com.app.services.TransactionService;
import com.app.utils.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class CustomerController {
    //plaćanje računa, način plaćanja

    //dodavanje stavki na naplatu

    @Autowired
    private final ProductService productService;
    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final StockService stockService;

    @GetMapping("/merchandiser_page")
    public String merchandiserPage(@RequestParam(required = false) String notFound,
                                   Model model){
        var products = stockService.getAllProducts();
        model.addAttribute("Products available:", products);
        return "merchandiser-page";
    }

    @GetMapping("/select/paymentMethod")
    public Model selectPaymentMethod(Model model,
                                     PaymentMethod paymentMethod,
                                     @RequestBody Receipt receipt){

        model.addAttribute("payment", transactionService.selectPaymentMethod(paymentMethod, receipt));
        return model;
    }

    @PostMapping("/add/products")
    public Model addToCart(Model model,
                            @RequestBody Product product,
                            @RequestParam int quantity)
    {
         var cart = transactionService.addToCart(product, quantity);
         model.addAttribute("cart is ", cart);
         return model;
    }

}
