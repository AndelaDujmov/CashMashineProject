package com.app.services;


import com.app.exceptions.NotFoundException;
import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Product;
import com.app.persistence.repositories.CathegoryRepository;
import com.app.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CathegoryService {
    @Autowired
    private CathegoryRepository cathegoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cathegory createNewCathegory(String name) {
        Cathegory cathegory = new Cathegory();
        cathegory.setName(name);
        return cathegoryRepository.save(cathegory);
    }

    public List<Cathegory> getAll(){
        return cathegoryRepository.findAll();
    }

    public Cathegory findCathegoryByName(String name){
        return cathegoryRepository.findCathegoryByName(name);
    }

    public List<Product> listProductsInCathegory(String name){
        var cathegory = findCathegoryByName(name);
        var products = productRepository.findAll();

        var producCategory = products.stream().filter(p -> p.getCathegory().equals(cathegory)).collect(Collectors.toList());
        return producCategory;
    }



    public Cathegory findCathegoryByID(long id){
        return cathegoryRepository.getById(id);
    }

    public void deleteCathegoryByID(long id){
        cathegoryRepository.deleteById(id);
    }

    public void softDeleteCathegory(long id){
        var cathegory = cathegoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find cathegory"));
        cathegory.setDeleted(true);
    }
}
