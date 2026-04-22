package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Agency entities.
 * Extends JpaRepository to get built-in CRUD methods (save, findById, findAll, delete).
 * No custom queries needed — uses only the default JPA methods.
 */
@Repository
public interface IAgencyRepository extends JpaRepository<Agency,Integer> {
}
