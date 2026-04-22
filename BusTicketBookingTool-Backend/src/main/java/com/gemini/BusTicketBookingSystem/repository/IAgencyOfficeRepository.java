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
