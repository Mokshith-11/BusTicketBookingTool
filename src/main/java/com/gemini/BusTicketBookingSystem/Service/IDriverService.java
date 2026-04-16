package com.gemini.BusTicketBookingSystem.Service;

import com.gemini.BusTicketBookingSystem.Entity.Driver;
import com.gemini.BusTicketBookingSystem.Dto.Request.DriverRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.DriverResponse;

import java.util.List;
public interface IDriverService {
    DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO);
    List<DriverResponse> getDriversByOffice(Integer officeId);
    DriverResponse getDriverById(Integer driverId);
    DriverResponse updateDriver(Integer driverId, DriverRequest requestDTO);
    void removeDriver(Integer driverId);
    List<Driver> findByOffice_OfficeId(Integer officeId);
}