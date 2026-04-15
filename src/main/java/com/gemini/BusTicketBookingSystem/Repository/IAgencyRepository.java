package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAgencyRepository extends JpaRepository<Agency,Integer> {
}
