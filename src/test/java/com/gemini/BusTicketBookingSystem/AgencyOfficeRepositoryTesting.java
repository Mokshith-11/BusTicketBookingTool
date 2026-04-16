package com.gemini.BusTicketBookingSystem;

import com.gemini.BusTicketBookingSystem.Entity.Agency;
import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AgencyOfficeRepositoryTesting {

    @Autowired
    private IAgencyOfficeRepository agencyOfficeRepository;

    @Autowired
    private IAgencyRepository agencyRepository;


    @Test
    void testSaveAgencyOffice_Positive() {
        Agency agency = agencyRepository.save(
                Agency.builder()
                        .name("ABC Travels")
                        .contactPersonName("Aksha")
                        .email("abc@gmail.com")
                        .phone("9876543210")
                        .build()
        );

        AgencyOffice office = new AgencyOffice();

        office.setAgency(agency);

        AgencyOffice saved = agencyOfficeRepository.save(office);

        assertThat(saved).isNotNull();
        assertThat(saved.getOfficeId()).isGreaterThan(0);
    }







}
