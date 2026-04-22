package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.entity.Driver;
import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;

import java.util.List;

/**
 * Service interface for driver management.
 * Defines the contract for registering, viewing, updating, and removing drivers.
 * Implemented by DriverServiceImpl.
 */
public interface IDriverService {
    /** Registers a new driver under a specific agency office */
    DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO);

    /** Retrieves all drivers belonging to a specific office */
    List<DriverResponse> getDriversByOffice(Integer officeId);

    /** Retrieves a driver by their unique ID */
    DriverResponse getDriverById(Integer driverId);

    /** Updates an existing driver's details */
    DriverResponse updateDriver(Integer driverId, DriverRequest requestDTO);

    /** Removes a driver from the system */
    void removeDriver(Integer driverId);

    /** Finds drivers by their office ID (returns entity list) */
    List<Driver> findByOffice_OfficeId(Integer officeId);
}