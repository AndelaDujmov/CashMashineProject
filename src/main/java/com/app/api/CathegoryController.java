package com.app.api;

import com.app.persistence.entities.Cathegory;
import com.app.services.CathegoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CathegoryController {
    @Autowired
    private CathegoryService cathegoryService;

    @PostMapping ("/create")
    public String createNewCategory(@RequestBody Cathegory cathegory){
        var category = new Cathegory();
        category.setName(category.getName());
        category.setDeleted(false);
        return "redirect";
    }

    @GetMapping("/ListAll")
    public Model listCategories(Model model,
                                @RequestParam(defaultValue = "1") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "name") String sortBy){
        var categories = cathegoryService.getAllSortedAndPaged(pageNo, pageSize, sortBy);
        model.addAttribute("categories:", categories);
        return model;
    }

    @GetMapping("/listAll/products")
    public Model listProducts(Model model,
                              @RequestParam String name){
        var products = cathegoryService.listProductsInCathegory(name);
        model.addAttribute("products", products);
        return model;
    }
}
