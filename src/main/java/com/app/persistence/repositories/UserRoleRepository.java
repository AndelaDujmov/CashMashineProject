package com.app.persistence.repositories;


import com.app.persistence.entities.UserRole;
import com.app.utils.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, PagingAndSortingRepository<UserRole, Long> {
    UserRole findRoleByName(UserType userType);
}