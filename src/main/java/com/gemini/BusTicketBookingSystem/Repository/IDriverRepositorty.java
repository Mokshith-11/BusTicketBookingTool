package com.gemini.BusTicketBookingSystem.Repository;

import com.gemini.BusTicketBookingSystem.Entity.Driver;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IDriverRepositorty extends JpaRepository<Driver,Integer> {
    boolean existsByLicenseNumber(@NotBlank(message = "License number is required") String licenseNumber);

    List<Driver> findByOffice_OfficeId(Integer officeId);
}
