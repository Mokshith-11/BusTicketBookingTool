package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on Bus entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains custom queries for finding buses by office and checking for duplicates.
 */
@Repository
public interface IBusRepository extends JpaRepository<Bus,Integer> {

    /**
     * Finds all buses assigned to a specific agency office.
     *
     * @param officeId - the office ID to filter by
     * @return List of Bus - all buses for that office
     */
    @Query("SELECT b FROM Bus b WHERE b.office.officeId = :officeId")
    List<Bus> findByOffice_OfficeId(@ Param("officeId") Integer officeId);

    /**
     * Checks if a bus with the given registration number already exists.
     * Used to prevent duplicate registration numbers in the system.
     *
     * @param registrationNumber - the registration number to check
     * @return boolean - true if a bus with that registration number exists
     */
    @Query("SELECT COUNT(b) > 0 FROM Bus b WHERE b.registrationNumber = :registrationNumber")
    boolean existsByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
}
