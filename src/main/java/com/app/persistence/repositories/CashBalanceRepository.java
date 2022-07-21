package com.app.persistence.repositories;

import com.app.persistence.entities.CashBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBalanceRepository extends JpaRepository<CashBalance, Long>, PagingAndSortingRepository<CashBalance, Long> {
}