package com.app.persistence.repositories;

import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Long>, PagingAndSortingRepository<Costumer, Long> {
    Costumer findCostumerByCardNumber(String cardNum);
}