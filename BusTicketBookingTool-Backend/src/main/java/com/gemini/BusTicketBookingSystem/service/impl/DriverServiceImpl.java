package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.entity.Driver;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IDriverRepositorty;
import com.gemini.BusTicketBookingSystem.service.IDriverService;
import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
/*
 * Beginner guide:
 * - This class contains the real business logic for Driver operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
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