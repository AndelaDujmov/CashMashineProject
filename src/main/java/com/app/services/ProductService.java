package com.app.services;


import com.app.exceptions.InvalidInputException;
import com.app.exceptions.NotFoundException;
import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Product;
import com.app.persistence.repositories.CathegoryRepository;
import com.app.persistence.repositories.DiscountRepository;
import com.app.persistence.repositories.ProductRepository;
import com.app.utils.dto.ProductCreationDto;
import com.app.utils.filters.PriceFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;


@Service
@Data
@AllArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CathegoryRepository cathegoryRepository;
    @Autowired
    private DiscountRepository discountRepository;
    private PriceFilter filter;

    public Product addNewProduct(Product product){
        return productRepository.save(product);}

    public Product createProduct(ProductCreationDto p){
        var product = new Product();
        product.setName(p.getName());
        product.setType(p.getType());
        product.setProductCode(p.getProductCode());
        product.setPrice(p.getPrice());
        return addNewProduct(product);
    }

    public Product findProductByName(String name){ return productRepository.findProductByName(name);}

    public Product findProductByCathegory(Cathegory cathegory){return productRepository.findProductByCathegory(cathegory);}


    public Page<Product> pageAllProducts(int page, int numberOfElements, String sortDirection, @Pattern(regexp = "[A-Za-z]+") String sortBy){
        var sorting = Sort.by(sortBy);
        switch (sortDirection){
            case "asc":
                sorting = sorting.ascending();
                break;
            case "desc":
                sorting = sorting.descending();
                break;
            default:
                throw new InvalidInputException("Input must be only asc and desc!");
        }

        Pageable pageable = PageRequest.of(page, numberOfElements, sorting);
        return productRepository.findAll(pageable);
    }

    public void addCathegoryToProduct(long CID, long PID){
        var product = productRepository.findById(PID).orElse(null);
        var cathegory = cathegoryRepository.findById(CID).orElse(null);

        product.setCathegory(cathegory);
        productRepository.save(product);
    }

    public void addDiscountToProduct(long DID, long PID){
        var product = productRepository.findById(PID).orElse(null);
        var discount = discountRepository.findById(DID).orElse(null);

        product.setDiscount(discount);
        productRepository.save(product);
    }

    public void softDeleteProduct(long id){
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("product is not found"));

        product.setDeleted(true);
        productRepository.save(product);
    }

    public void calculatePrice(String name, long price){
        var product = findProductByName(name);

        filter.calculate(price);
    }

    public void hardDeleteProduct(long id){
        var product = productRepository.findById(id).orElse(null);
        productRepository.delete(product);
    }

}
