package com.gemini.BusTicketBookingSystem.repository;

import com.gemini.BusTicketBookingSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDriverRepositorty extends JpaRepository<Driver,Integer> {
    @Query("SELECT COUNT(d) > 0 FROM Driver d WHERE d.licenseNumber = :licenseNumber")
    boolean existsByLicenseNumber(@Param("licenseNumber") String licenseNumber);
    @Query("SELECT d FROM Driver d WHERE d.office.officeId = :officeId")
    List<Driver> findByOffice_OfficeId(@Param("officeId") Integer officeId);}
