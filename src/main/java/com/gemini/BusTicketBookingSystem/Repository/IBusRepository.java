package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Bus;
import com.gemini.BusTicketBookingSystem.Entity.Driver;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IBusRepository extends JpaRepository<Bus,Integer> {

    List<Bus> findByOffice_OfficeId(Integer officeId);

    boolean existsByRegistrationNumber(@NotBlank(message = "Registration number is required") String registrationNumber);
}
