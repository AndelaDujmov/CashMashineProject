package com.app.persistence.repositories;


import com.app.persistence.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, PagingAndSortingRepository<Discount, Long> {
    Discount findByPercent(long percent);
}