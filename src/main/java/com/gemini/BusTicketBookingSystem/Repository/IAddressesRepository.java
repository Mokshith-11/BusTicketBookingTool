package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressesRepository extends JpaRepository<Addresses,Integer> {
}
