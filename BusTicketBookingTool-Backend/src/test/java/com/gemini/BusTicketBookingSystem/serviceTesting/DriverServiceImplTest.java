package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;
import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.entity.Driver;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import com.gemini.BusTicketBookingSystem.repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.repository.IDriverRepositorty;
import com.gemini.BusTicketBookingSystem.service.impl.DriverServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private IDriverRepositorty driverRepository;

    @Mock
    private IAgencyOfficeRepository officeRepository;

    @Mock
    private IAddressesRepository addressRepository;

    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver driver;
    private AgencyOffice office;
    private Addresses address;
    private DriverRequest request;

    @BeforeEach
    void setUp() {

        office = new AgencyOffice();
        office.setOfficeId(1);
        office.setOfficeMail("office@mail.com");

        address = new Addresses();
        address.setAddressId(1);
        address.setAddress("Street");
        address.setCity("Chennai");
        address.setState("TN");

        driver = new Driver();
        driver.setDriverId(1);
        driver.setOffice(office);
        driver.setLicenseNumber("LIC123");
        driver.setName("Ravi");
        driver.setPhone("9999999999");
        driver.setAddress(address);

        request = new DriverRequest();
        request.setLicenseNumber("LIC123");
        request.setName("Ravi");
        request.setPhone("9999999999");
        request.setAddressId(1);
    }

    // ✅ REGISTER SUCCESS
    @Test
    void testRegisterDriver_Success() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));
        when(driverRepository.existsByLicenseNumber("LIC123")).thenReturn(false);
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        DriverResponse response = driverService.registerDriver(1, request);

        assertNotNull(response);
        assertEquals("Ravi", response.getName());

        verify(driverRepository).save(any(Driver.class));
    }

    // ❌ OFFICE NOT FOUND
    @Test
    void testRegisterDriver_OfficeNotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.registerDriver(1, request));
    }

    // ❌ DUPLICATE LICENSE
    @Test
    void testRegisterDriver_DuplicateLicense() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));
        when(driverRepository.existsByLicenseNumber("LIC123")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> driverService.registerDriver(1, request));
    }

    // ❌ ADDRESS NOT FOUND
    @Test
    void testRegisterDriver_AddressNotFound() {

        when(officeRepository.findById(1)).thenReturn(Optional.of(office));
        when(driverRepository.existsByLicenseNumber("LIC123")).thenReturn(false);
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.registerDriver(1, request));
    }

    // ✅ GET DRIVER BY ID
    @Test
    void testGetDriverById_Success() {

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        DriverResponse response = driverService.getDriverById(1);

        assertEquals("Ravi", response.getName());
    }

    // ❌ GET DRIVER NOT FOUND
    @Test
    void testGetDriverById_NotFound() {

        when(driverRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.getDriverById(1));
    }

    // ✅ GET DRIVERS BY OFFICE
    @Test
    void testGetDriversByOffice_Success() {

        when(officeRepository.existsById(1)).thenReturn(true);
        when(driverRepository.findByOffice_OfficeId(1)).thenReturn(List.of(driver));

        List<DriverResponse> result = driverService.getDriversByOffice(1);

        assertEquals(1, result.size());
    }

    // ❌ GET DRIVERS BY OFFICE NOT FOUND
    @Test
    void testGetDriversByOffice_NotFound() {

        when(officeRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.getDriversByOffice(1));
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdateDriver_Success() {

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        DriverResponse response = driverService.updateDriver(1, request);

        assertEquals("Ravi", response.getName());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateDriver_NotFound() {

        when(driverRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.updateDriver(1, request));
    }

    // ❌ UPDATE DUPLICATE LICENSE
    @Test
    void testUpdateDriver_DuplicateLicense() {

        Driver existing = new Driver();
        existing.setDriverId(1);
        existing.setLicenseNumber("OLD123");

        when(driverRepository.findById(1)).thenReturn(Optional.of(existing));
        when(driverRepository.existsByLicenseNumber("LIC123")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> driverService.updateDriver(1, request));
    }

    // ✅ DELETE SUCCESS
    @Test
    void testRemoveDriver_Success() {

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        driverService.removeDriver(1);

        verify(driverRepository).delete(driver);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testRemoveDriver_NotFound() {

        when(driverRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.removeDriver(1));
    }
}
