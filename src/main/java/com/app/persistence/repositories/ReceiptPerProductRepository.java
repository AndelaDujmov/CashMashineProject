package com.app.persistence.repositories;


import com.app.persistence.entities.Product;
import com.app.persistence.entities.ReceiptPerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptPerProductRepository extends JpaRepository<ReceiptPerProduct, Long>, PagingAndSortingRepository<ReceiptPerProduct, Long> {
    ReceiptPerProduct findByProductName(Product product);
}