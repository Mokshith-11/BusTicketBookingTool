package com.gemini.BusTicketBookingSystem;



import com.gemini.BusTicketBookingSystem.entity.Addresses;
import com.gemini.BusTicketBookingSystem.repository.IAddressesRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTesting {

    @Autowired
    private IAddressesRepository addressesRepository;


    @Test
    void testSaveAddress_Positive() {
        Addresses address = new Addresses();
        address.setAddress("Anna Nagar");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setZipCode("600040");

        Addresses saved = addressesRepository.save(address);

        assertThat(saved).isNotNull();
    }


    @Test
    void testSaveAddress_Negative_NullValues() {
        Addresses address = new Addresses(); // no values set

        assertThrows(ConstraintViolationException.class, () -> {
            addressesRepository.save(address);
        });
    }


    @Test
    void testFindById_Positive() {
        Addresses address = new Addresses();
        address.setAddress("T Nagar");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setZipCode("600017");

        Addresses saved = addressesRepository.save(address);

        Optional<Addresses> found = addressesRepository.findById(saved.getAddressId());

        assertThat(found).isPresent();
    }


    @Test
    void testFindById_Negative_InvalidId() {
        Optional<Addresses> found = addressesRepository.findById(-1);

        assertThat(found).isNotPresent();
    }


    @Test
    void testDeleteAddress_Positive() {
        Addresses address = new Addresses();

        address.setAddress("Velachery");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setZipCode("600042");

        Addresses saved = addressesRepository.save(address);

        addressesRepository.deleteById(saved.getAddressId());

        Optional<Addresses> deleted = addressesRepository.findById(saved.getAddressId());

        assertThat(deleted).isNotPresent();
    }
}
