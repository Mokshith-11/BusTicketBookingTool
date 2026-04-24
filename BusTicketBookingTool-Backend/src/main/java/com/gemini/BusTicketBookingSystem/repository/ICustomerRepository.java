package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Customer entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains custom queries to check for duplicate phone numbers and emails.
 */
@Repository
/*
 * - This repository is the database access layer for Customer records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface ICustomerRepository extends JpaRepository<Customer,Integer> {

    /**
     * Checks if a customer with the given phone number already exists.
     * Used to enforce unique phone numbers when registering/updating customers.
     *
     * @param phone - the phone number to check
     * @return boolean - true if a customer with that phone exists
     */
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    /**
     * Checks if a customer with the given email already exists.
     * Used to enforce unique emails when registering/updating customers.
     *
     * @param email - the email to check
     * @return boolean - true if a customer with that email exists
     */
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
