package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IAgencyOfficeRepository extends JpaRepository<AgencyOffice,Integer> {
    List<AgencyOffice> findByAgency_AgencyId(Integer agencyId);
}
