package com.app.persistence.repositories;


import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Discount;
import com.app.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    public Product findProductByCathegory(Cathegory cathegory);

    public Product findProductByDiscountPercent(Discount discount);

    public Product findProductByName(String name);

    public Product findProductByProductCode(String code);


}