package com.app.api;

import com.app.exceptions.NotFoundException;
import com.app.exceptions.TransactionException;
import com.app.persistence.entities.*;
import com.app.services.*;
import com.app.utils.complex.Address;
import com.app.utils.filters.PriceFilter;
import com.app.utils.printers.BalancePrinterIO;
import com.app.utils.printers.ReceiptPrinterIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private UserService userService;
    private final String RECEIPT_CONST = "receipt";
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CashBalanceService cashBalanceService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BalancePrinterIO balancePrinterIO;
    @Autowired
    private ReceiptPrinterIO printerIO;
    @Autowired
    private StockService stockService;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @PostMapping("add/products")
    public List<ReceiptPerProduct> addToCart(Model model,
                                             @RequestParam String name,
                                             @RequestParam int quantity,
                                             @RequestParam long id)
    {
        var product = productService.findProductByName(name);
        try{
            stockService.updateSoldProduct(product, quantity);
        }catch (TransactionException e){
            e.printStackTrace();
        }

        var receipt = receiptService.findReceipt(id);

        var productsInCheck = receipt.getProductList().stream().filter(p -> p.getProductName().equals(product)).findAny();

        if(productsInCheck.isPresent()){
            var products = productsInCheck.get();
            products.setQuantity(quantity);
            df.setRoundingMode(RoundingMode.UP);
            products.setPrice(Double.parseDouble(df.format(product.getPrice())));
            products.setTotalPrice(Double.parseDouble(df.format(product.getPrice()*quantity)));
            receiptService.saveInCheck(products);
            receipt.getProductList().add(products);
            receiptService.saveReceipt(receipt);
        }
        else{
            var productInCheck = new ReceiptPerProduct();
            df.setRoundingMode(RoundingMode.UP);
            productInCheck.setProductName(product);
            productInCheck.setQuantity(quantity);
            productInCheck.setPrice(Double.parseDouble(df.format(product.getPrice())));
            productInCheck.setTotalPrice(Double.parseDouble(df.format(product.getPrice()*quantity)));
            receiptService.saveInCheck(productInCheck);
            receipt.getProductList().add(productInCheck);
            receiptService.saveReceipt(receipt);
        }

        return receipt.getProductList();
    }


    @PostMapping("/check/open")
    public Receipt openReceipt(@RequestBody Address address, @RequestParam String codeName){
        return receiptService.createReceipt(new Date(System.currentTimeMillis()), address, codeName);
    }

    @GetMapping("/check/date")
    public List<Receipt> findReceiptsByDate(@RequestParam Date date,
                                            @RequestParam String codeName,
                                            Model model){
        return receiptService.findReceiptsByDate(date, codeName);
    }

    @PostMapping("balance/set")
    public String setBalance(@RequestParam double deposit, Model model){
        model.addAttribute("balance", cashBalanceService.addDeposit(deposit));

        return "balance";
    }

    @PutMapping("/balance/update")
    public String updateBalance(@RequestParam long idt, @RequestParam long id){
        cashBalanceService.addTransaction(id, idt);
        return "balance";
    }

    @PostMapping("/balance/list")
    public String transactionList(@RequestParam long id, Model model){
        model.addAttribute("list", cashBalanceService.showAllTransactions(id));

        return cashBalanceService.showAllTransactions(id).toString();
    }

    @PostMapping("/balance/delete")
    public String deleteTransaction(@RequestParam long idB, @RequestParam long idT, Model model){
        cashBalanceService.softDeleteTransaction(idB, idT);

        return "deleted";
    }

    @GetMapping("/balance/post")
    public String postReceipt(@RequestParam long id){
        var balance = cashBalanceService.getBalance(id);

        balancePrinterIO.printBalanceTXT(balance);
        return "printed!";
    }

    @GetMapping("/balance/pdf")
    public String printReceiptPDF(@RequestParam long id){
        var receipt = receiptService.findReceipt(id);

        printerIO.printReceiptPdf(receipt);
        return "printeddd!";
    }

    @GetMapping("balance/printf")
    public void readReceipt(){
        printerIO.readReceipt();
    }

    @GetMapping("/balance/read")
    public void readBalance(){
        balancePrinterIO.readBalanceTXT();

    }

    @PostMapping ("plose")
    public double closeReceipt(Model model, @RequestParam(value = "id") long id){
        var receipt = receiptService.findReceipt(id);
        var total = receipt.getProductList().stream().mapToDouble(p ->  p.getTotalPrice()).sum();
        receipt.setFinalPrice(Double.parseDouble(df.format(total)));
        df.setRoundingMode(RoundingMode.UP);
        model.addAttribute("final", df.format(receipt.getFinalPrice()));
        receiptService.saveReceipt(receipt);
        return Double.parseDouble(df.format(receipt.getFinalPrice()));
    }

    @PostMapping("/check/cancel")
    public String cancelReceipt(Model model, @RequestParam double givenMoney, @RequestParam long id){
        var check = receiptService.findReceipt(id);


        check.getProductList().forEach(product -> {
            try {
                stockService.updateProductOfStock(product.getProductName().getID(), product.getQuantity());
            }catch (NotFoundException e){
                e.printStackTrace();
            }
        });
        model.addAttribute("return", givenMoney);
        receiptService.cancelReceipt(check.getID());
        return "cancelled";
    }



    @PostMapping("/employee/add-product")
    public Receipt addProductToCheck(Integer productCount, ReceiptPerProduct receiptP, long id){
        var receipt = receiptService.findReceipt(id);

        if(receipt.equals(null))
            throw new RuntimeException("Receipt has not been created!");

        receipt.getProductList().add(receiptP);
        receiptService.saveReceipt(receipt);
        return receipt;
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam long id, @RequestParam String code){
        var receipt = receiptService.findReceipt(id);
        receiptService.deleteProduct(code, id);
        receipt.finalPriceCalculation();
        return "redirectttt";
    }

    @PostMapping("/sum")
    public CashBalance closeBalance(@RequestParam long id){
        return cashBalanceService.closeBalance(id);
    }


    @GetMapping("/check/add")
    public String addCheck(Model model, @RequestParam long id){
        var receipt = receiptService.findReceipt(id);

        printerIO.printReceipt(receipt);


        return "bye";
    }


}

