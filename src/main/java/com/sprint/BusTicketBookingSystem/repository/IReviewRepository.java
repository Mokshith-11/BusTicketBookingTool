package com.sprint.BusTicketBookingSystem.repository;

import com.sprint.BusTicketBookingSystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {


}