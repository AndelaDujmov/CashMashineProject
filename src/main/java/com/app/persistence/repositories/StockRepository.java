package com.app.persistence.repositories;


import com.app.persistence.entities.Product;
import com.app.persistence.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, PagingAndSortingRepository<Stock, Long> {
    Stock findByProduct(Product product);
    Stock findByAddress_City(String city);
}