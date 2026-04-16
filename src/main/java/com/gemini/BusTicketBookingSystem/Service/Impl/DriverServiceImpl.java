package com.gemini.BusTicketBookingSystem.Service.Impl;

import com.gemini.BusTicketBookingSystem.Entity.Addresses;
import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.Entity.Driver;
import com.gemini.BusTicketBookingSystem.Exception.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.Exception.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.Repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.Repository.IDriverRepositorty;
import com.gemini.BusTicketBookingSystem.Service.IDriverService;
import com.gemini.BusTicketBookingSystem.Dto.Request.DriverRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private IDriverRepositorty driverRepository;

    @Autowired
    private IAgencyOfficeRepository officeRepository;

    @Autowired
    private IAddressesRepository addressRepository;

    @Override
    @Transactional
    public DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("AgencyOffice", "officeId", officeId));

        // Check for duplicate license number
        if (driverRepository.existsByLicenseNumber(requestDTO.getLicenseNumber())) {
            throw new DuplicateResourceException("Driver", "licenseNumber",
                    requestDTO.getLicenseNumber());
        }

        Addresses address = addressRepository.findById(requestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                        requestDTO.getAddressId()));

        Driver driver = new Driver();
        driver.setOffice(office);
        driver.setLicenseNumber(requestDTO.getLicenseNumber());
        driver.setName(requestDTO.getName());
        driver.setPhone(requestDTO.getPhone());
        driver.setAddress(address);

        Driver savedDriver = driverRepository.save(driver);
        return convertToResponseDTO(savedDriver);
    }

    @Override
    public List<DriverResponse> getDriversByOffice(Integer officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new ResourceNotFoundException("AgencyOffice", "officeId", officeId);
        }

        return driverRepository.findByOffice_OfficeId(officeId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DriverResponse getDriverById(Integer driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", driverId));
        return convertToResponseDTO(driver);
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(Integer driverId, DriverRequest requestDTO) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", driverId));

        // Check if license number is being changed and if it's duplicate
        if (!driver.getLicenseNumber().equals(requestDTO.getLicenseNumber()) &&
                driverRepository.existsByLicenseNumber(requestDTO.getLicenseNumber())) {
            throw new DuplicateResourceException("Driver", "licenseNumber",
                    requestDTO.getLicenseNumber());
        }

        Addresses address = addressRepository.findById(requestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                        requestDTO.getAddressId()));

        driver.setLicenseNumber(requestDTO.getLicenseNumber());
        driver.setName(requestDTO.getName());
        driver.setPhone(requestDTO.getPhone());
        driver.setAddress(address);

        Driver updatedDriver = driverRepository.save(driver);
        return convertToResponseDTO(updatedDriver);
    }

    @Override
    @Transactional
    public void removeDriver(Integer driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", driverId));

        // Check if driver is assigned to any active trips
        // This would require checking Trip entity for this driver
        // For now, we'll just delete
        driverRepository.delete(driver);
    }

    @Override
    public List<Driver> findByOffice_OfficeId(Integer officeId) {
        return List.of();
    }

    private DriverResponse convertToResponseDTO(Driver driver) {
        DriverResponse dto = new DriverResponse();
        dto.setDriverId(driver.getDriverId());
        dto.setOfficeId(driver.getOffice().getOfficeId());
        dto.setOfficeName(driver.getOffice().getOfficeMail());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());
        dto.setAddressId(driver.getAddress().getAddressId());
        dto.setAddress(driver.getAddress().getAddress() + ", " +
                driver.getAddress().getCity() + ", " +
                driver.getAddress().getState());
        return dto;
    }
}