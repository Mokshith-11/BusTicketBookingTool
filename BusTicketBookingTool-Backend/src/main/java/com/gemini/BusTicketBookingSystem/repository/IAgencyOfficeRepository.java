package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on AgencyOffice entities.
 * Extends JpaRepository to get built-in CRUD methods.
 * Contains a custom query to find all offices belonging to a specific agency.
 */
@Repository
/*
 * - This repository is the database access layer for Agency Office records.
 * - Spring Data JPA automatically provides common CRUD methods like save, findById, findAll, and delete.
 * - Service classes call this repository so SQL/database work stays separate from business rules.
 */
public interface IAgencyOfficeRepository extends JpaRepository<AgencyOffice,Integer> {

    /**
     * Finds all offices that belong to a specific agency.
     *
     * @param agencyId - the agency ID to filter by
     * @return List of AgencyOffice - all offices for that agency
     */
    @Query("SELECT ao FROM AgencyOffice ao WHERE ao.agency.agencyId = :agencyId")
    List<AgencyOffice> findOfficesByAgencyId(@Param("agencyId") Integer agencyId);

}
