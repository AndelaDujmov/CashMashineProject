package com.app.utils.admin;


import com.app.exceptions.NotFoundException;
import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Product;
import com.app.persistence.entities.Transaction;
import com.app.persistence.repositories.CashBalanceRepository;
import com.app.persistence.repositories.CathegoryRepository;
import com.app.persistence.repositories.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class SoftDeletingService {
    @Autowired
    private CashBalanceRepository cashBalanceRepository;
    @Autowired
    private CathegoryRepository cathegoryRepository;
    @Autowired
    private ProductRepository productRepository;


    private List<Transaction> readBalance(long id){
        var cash = cashBalanceRepository.findById(id).orElseThrow(() -> new NotFoundException("balance closed"));
        var listTransactions = new ArrayList<Transaction>();

        cash.getPayementHistory().stream()
                                 .filter(c -> c.isDeleted() == false)
                                 .collect(Collectors.toList());

        return listTransactions;
    }

    private List<Cathegory> readCathegories(){
        var cathegories = cathegoryRepository.findAll();
        var catList = new ArrayList<Cathegory>();

        cathegories.stream()
                   .filter(c -> c.isDeleted() == false)
                   .collect(Collectors.toList());

        return catList;
    }

    private List<Product> readProducts(){
        var products = productRepository.findAll();
        var prodList = new ArrayList<Product>();

        products.stream()
                .filter(c -> c.isDeleted() == false)
                .collect(Collectors.toList());

        return prodList;
    }
}
