package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.Entity.Agency;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AgencyRepositoryTesting {

    @Autowired
    private IAgencyRepository agencyRepository;


    @Test
    void testSaveAgency_Positive() {
        Agency agency = Agency.builder()
                .name("tnstc travels")
                .contactPersonName("vijay")
                .email("vijay@yahoo")
                .phone("9876548790")
                .build();

        Agency saved = agencyRepository.save(agency);

        assertThat(saved).isNotNull();
        assertThat(saved.getAgencyId()).isGreaterThan(0);
    }


    @Test
    void testSaveAgency_Negative_NullValues() {
        Agency agency = new Agency(); // no values

        assertThrows(ConstraintViolationException.class, () -> {
            agencyRepository.save(agency);
        });
    }


    @Test
    void testFindById_Positive() {
        Agency agency = Agency.builder()
                .name("Find Travels")
                .contactPersonName("Test")
                .email("find@gmail.com")
                .phone("9999999999")
                .build();

        Agency saved = agencyRepository.save(agency);

        Optional<Agency> found = agencyRepository.findById(saved.getAgencyId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Find Travels");
    }


    @Test
    void testFindById_Negative_InvalidId() {
        Optional<Agency> found = agencyRepository.findById(-1);

        assertThat(found).isNotPresent();
    }


    @Test
    void testDeleteAgency_Positive() {
        Agency agency = Agency.builder()
                .name("Delete Travels")
                .contactPersonName("Delete Test")
                .email("delete@gmail.com")
                .phone("8888888888")
                .build();

        Agency saved = agencyRepository.save(agency);

        agencyRepository.deleteById(saved.getAgencyId());

        Optional<Agency> deleted = agencyRepository.findById(saved.getAgencyId());

        assertThat(deleted).isNotPresent();
    }
}
