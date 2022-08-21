package com.app.api;

import com.app.exceptions.NotFoundException;
import com.app.exceptions.TransactionException;
import com.app.persistence.entities.Product;
import com.app.persistence.entities.Receipt;
import com.app.persistence.entities.ReceiptPerProduct;
import com.app.persistence.entities.User;
import com.app.services.ReceiptService;
import com.app.services.StockService;
import com.app.services.UserService;
import com.app.utils.enums.PaymentMethod;
import com.app.utils.filters.CheckFilter;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class EmployeeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReceiptService receiptService;
    private CheckFilter filter;
    @Autowired
    private StockService stockService;

    // izdaje račune, briše račune (soft deletea račune)
    // pregledava izdane račune
    //pregled transakcija, cancellanje transakcija

    @PostMapping("/check/open")
    public String openReceipt(@RequestParam double Givenmoney){

        return "check";
    }

    @PostMapping("/check/cancel")
    public String cancelReceipt(Model model, @RequestParam double givenMoney, long id){
        var check = receiptService.findReceipt(id);


        check.getProductList().forEach(product -> {
            try {
                stockService.updateProductOfStock(product.getProductName(), product.getQuantity());
            }catch (NotFoundException e){
                e.printStackTrace();
            }
        });
        model.addAttribute("return", givenMoney);
        receiptService.cancelReceipt(check.getID());
        return "/redirect:" + "/index";
    }

    @PostMapping("/employee/add-product")
    public String addProductToCheck(@RequestParam Integer productCount, HttpSession session){
        var product = (Product) session.getAttribute("Product");
        try{
            stockService.updateSoldProduct(product, productCount);
        }catch (TransactionException e){
            e.printStackTrace();
            return "/index";
        }

        var rece = new Receipt();
        session.setAttribute("receipt", rece);
        var receipt = (Receipt) session.getAttribute("receipt");
        Optional<ReceiptPerProduct> receiptPerProduct = receipt.getProductList()
                                                                .stream().filter(s -> s.getProductName().equals(product.getName()))
                                                                .findAny();
        filter.caluculateTotalPrice(receipt, receiptPerProduct, productCount, product);
        session.removeAttribute("receiptx");
        return "/redirect";
    }

    @PostMapping("/remove")
    public String removeProduct(HttpSession httpSession, String code){
        var receipt = (Receipt)httpSession.getAttribute("Receipt");

        var productsInCheck = receipt.getProductList()
                                                            .stream()
                                                            .filter(product -> product.getProductName().getProductCode().equals(code))
                                                            .findAny();
        try {
            var productInCheck = productsInCheck.get();
            receipt.getProductList().remove(productInCheck);
            try{
                stockService.updateProductOfStock(productInCheck.getProductName(), productInCheck.getQuantity());
            }catch (TransactionException e){
                e.printStackTrace();
            }
        }catch (NotFoundException e){
            e.printStackTrace();
        }
        receipt.finalPriceCalculation();
        return "redirect:";
    }





}

