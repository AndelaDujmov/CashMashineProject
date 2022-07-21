package com.app.persistence.repositories;


import com.app.persistence.entities.Cathegory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CathegoryRepository extends JpaRepository<Cathegory, Long>, PagingAndSortingRepository<Cathegory, Long> {
    Cathegory findCathegoryByName(String name);

}