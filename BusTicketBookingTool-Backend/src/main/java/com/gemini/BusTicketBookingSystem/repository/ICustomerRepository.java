package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
/*
 * - This repository is the database access layer for Customer records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface ICustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
