package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
