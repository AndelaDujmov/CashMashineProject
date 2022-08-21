package com.app.api;

import com.app.persistence.entities.Costumer;
import com.app.persistence.entities.Receipt;
import com.app.persistence.entities.User;
import com.app.services.ProductService;
import com.app.services.StockService;
import com.app.services.TransactionService;
import com.app.utils.printers.StockPrinterIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@RestController
public class TransactionController {

    private static final Logger logger = Logger.getLogger(TransactionController.class.toString());

    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TransactionService transactionService;
    private StockPrinterIO stockPrinterIO;

   @GetMapping("/slay")
   public String fuck(Model model) {
       model.addAttribute("me", "i dont get this");
       return "damn";
   }

    @GetMapping("/transactions")
    public String transactionPage(@RequestParam(name = "Not Found", required = false) String notFound, Model model){
        model.addAttribute("Not Found", notFound!=null);
        var getProducts = stockService.getAllProducts();
        model.addAttribute("Products", getProducts);
        return "transaction-page";
    }

    @PostMapping("/payment")
    public String payment(@AuthenticationPrincipal User user, @PathVariable(value = "description") String description, Costumer costumer, double price, Model model){
        var temp = new Receipt();
        model.addAttribute("receipt", temp);
        var receipt = (Receipt) model.getAttribute("receipt");
        transactionService.addHistory(user, costumer, receipt, description);

        return "/redirect:";
    }

    @PostMapping("/transactions/write")
    public String printStockReport(@RequestParam String city){
        stockPrinterIO.printTXTStockRecordLocation(city);
        return "printer";
    }

    @PostMapping("/transactions/writePDF")
    public String printStockReportPDF(@RequestParam String city){
        stockPrinterIO.printStockRecordPDF(city);
        return "printer";
    }
}
