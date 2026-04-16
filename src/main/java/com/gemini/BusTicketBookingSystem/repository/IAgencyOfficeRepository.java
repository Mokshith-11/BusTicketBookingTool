package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IAgencyOfficeRepository extends JpaRepository<AgencyOffice,Integer> {
    @Query("SELECT ao FROM AgencyOffice ao WHERE ao.agency.agencyId = :agencyId")

List<AgencyOffice> findOfficesByAgencyId(@Param("agencyId") Integer agencyId);













}
