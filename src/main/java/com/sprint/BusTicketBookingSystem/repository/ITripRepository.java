package com.sprint.BusTicketBookingSystem.repository;

import com.sprint.BusTicketBookingSystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITripRepository extends JpaRepository<Trip, Integer> {

}