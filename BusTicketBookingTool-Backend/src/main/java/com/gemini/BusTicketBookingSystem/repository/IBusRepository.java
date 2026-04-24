package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*
 * Beginner guide:
 * - This repository is the database access layer for Bus records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IBusRepository extends JpaRepository<Bus,Integer> {

    @Query("SELECT b FROM Bus b WHERE b.office.officeId = :officeId")
    List<Bus> findByOffice_OfficeId(@ Param("officeId") Integer officeId);

    @Query("SELECT COUNT(b) > 0 FROM Bus b WHERE b.registrationNumber = :registrationNumber")
    boolean existsByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
}
