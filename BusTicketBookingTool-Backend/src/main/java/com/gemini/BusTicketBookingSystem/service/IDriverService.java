package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.entity.Driver;
import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;

import java.util.List;
/*
 * - This service interface lists the Driver actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */
public interface IDriverService {
    DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO);
    List<DriverResponse> getAllDrivers();
    List<DriverResponse> getDriversByOffice(Integer officeId);
    DriverResponse getDriverById(Integer driverId);
    DriverResponse updateDriver(Integer driverId, DriverRequest requestDTO);
    void removeDriver(Integer driverId);
    List<Driver> findByOffice_OfficeId(Integer officeId);
}
