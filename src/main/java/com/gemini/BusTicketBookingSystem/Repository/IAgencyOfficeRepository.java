package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAgencyOfficeRepository extends JpaRepository<AgencyOffice,Integer> {
}
