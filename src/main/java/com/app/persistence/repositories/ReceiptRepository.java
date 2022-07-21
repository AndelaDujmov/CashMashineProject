package com.app.persistence.repositories;


import com.app.persistence.entities.Receipt;
import com.app.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Set;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, PagingAndSortingRepository<Receipt, Long> {
    Receipt findReceiptByAddress_CityAndAddress_Country(String city, String country);
    Receipt findReceiptByDate(Date date);
    Set<Receipt> findAllByDate(Date date);
    Receipt findAllByUser(User user);
}