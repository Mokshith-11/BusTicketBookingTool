package com.gemini.BusTicketBookingSystem.service;

import com.gemini.BusTicketBookingSystem.entity.Driver;
import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;

import java.util.List;
public interface IDriverService {
    DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO);
    List<DriverResponse> getDriversByOffice(Integer officeId);
    DriverResponse getDriverById(Integer driverId);
    DriverResponse updateDriver(Integer driverId, DriverRequest requestDTO);
    void removeDriver(Integer driverId);
    List<Driver> findByOffice_OfficeId(Integer officeId);
}