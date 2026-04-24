package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on Driver entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains custom queries for checking duplicate licenses and finding drivers by office.
 */
@Repository
/*
 * - This repository is the database access layer for Driver Repositorty records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IDriverRepositorty extends JpaRepository<Driver,Integer> {

    /**
     * Checks if a driver with the given license number already exists.
     * Used to enforce unique license numbers when registering/updating drivers.
     *
     * @param licenseNumber - the license number to check
     * @return boolean - true if a driver with that license number exists
     */
    @Query("SELECT COUNT(d) > 0 FROM Driver d WHERE d.licenseNumber = :licenseNumber")
    boolean existsByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    /**
     * Finds all drivers assigned to a specific agency office.
     *
     * @param officeId - the office ID to filter by
     * @return List of Driver - all drivers for that office
     */
    @Query("SELECT d FROM Driver d WHERE d.office.officeId = :officeId")
    List<Driver> findByOffice_OfficeId(@Param("officeId") Integer officeId);
}
