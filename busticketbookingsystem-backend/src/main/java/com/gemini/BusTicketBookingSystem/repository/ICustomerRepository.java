package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
