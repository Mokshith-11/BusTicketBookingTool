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

/**
 * Service implementation for managing bus drivers.
 * Contains business logic for registering, retrieving, updating,
 * and removing drivers. Each driver is assigned to an agency office.
 */
@Service
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private IDriverRepositorty driverRepository;

    @Autowired
    private IAgencyOfficeRepository officeRepository;

    @Autowired
    private IAddressesRepository addressRepository;

    /**
     * Registers a new driver under a specific agency office.
     * Validates that the office exists, the license number is unique,
     * and the address exists. Creates and saves the driver record.
     * This method is transactional to ensure data consistency.
     *
     * @param officeId   - the ID of the office this driver belongs to
     * @param requestDTO - contains licenseNumber, name, phone, addressId
     * @return DriverResponse - the registered driver data with generated ID
     */
    @Override
    @Transactional
    public DriverResponse registerDriver(Integer officeId, DriverRequest requestDTO) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("AgencyOffice", "officeId", officeId));

        // Check for duplicate license number to prevent two drivers with same license
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

    /**
     * Retrieves all drivers assigned to a specific agency office.
     * First verifies the office exists, then fetches all its drivers.
     *
     * @param officeId - the ID of the office whose drivers to retrieve
     * @return List of DriverResponse - all drivers for that office
     */
    @Override
    public List<DriverResponse> getDriversByOffice(Integer officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new ResourceNotFoundException("AgencyOffice", "officeId", officeId);
        }

        return driverRepository.findByOffice_OfficeId(officeId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single driver by their unique driver ID.
     * Throws ResourceNotFoundException if no driver exists with that ID.
     *
     * @param driverId - the unique ID of the driver to find
     * @return DriverResponse - the driver details
     */
    @Override
    public DriverResponse getDriverById(Integer driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", driverId));
        return convertToResponseDTO(driver);
    }

    /**
     * Updates an existing driver's details.
     * Finds the driver by ID, checks for duplicate license numbers
     * (only if the license is changing), verifies the new address exists,
     * then updates all fields.
     * This method is transactional to ensure data consistency.
     *
     * @param driverId   - the ID of the driver to update
     * @param requestDTO - the new driver data
     * @return DriverResponse - the updated driver data
     */
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

    /**
     * Removes a driver from the system permanently.
     * Finds the driver by ID and deletes the record from the database.
     * Throws ResourceNotFoundException if the driver doesn't exist.
     * This method is transactional to ensure data consistency.
     *
     * @param driverId - the ID of the driver to remove
     */
    @Override
    @Transactional
    public void removeDriver(Integer driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", driverId));


        driverRepository.delete(driver);
    }

    /**
     * Finds drivers by their office ID.
     * Currently returns an empty list (placeholder implementation).
     *
     * @param officeId - the office ID to search by
     * @return List of Driver - empty list (not yet implemented)
     */
    @Override
    public List<Driver> findByOffice_OfficeId(Integer officeId) {
        return List.of();
    }

    /**
     * Helper method to convert a Driver entity to a DriverResponse DTO.
     * Maps driver details including office info and formatted address string.
     *
     * @param driver - the Driver entity to convert
     * @return DriverResponse - the mapped DTO
     */
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