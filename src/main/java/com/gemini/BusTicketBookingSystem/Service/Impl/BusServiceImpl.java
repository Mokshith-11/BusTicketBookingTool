package com.gemini.BusTicketBookingSystem.Service.Impl;

import com.gemini.BusTicketBookingSystem.Entity.AgencyOffice;
import com.gemini.BusTicketBookingSystem.Entity.Bus;
import com.gemini.BusTicketBookingSystem.Exception.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.Exception.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.Repository.IAgencyOfficeRepository;
import com.gemini.BusTicketBookingSystem.Repository.IBusRepository;
import com.gemini.BusTicketBookingSystem.Service.IBusService;
import com.gemini.BusTicketBookingSystem.Dto.Request.BusRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.BusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements IBusService {


    private final IBusRepository iBusRepository;
    public BusServiceImpl(IBusRepository iBusRepository) {
        this.iBusRepository = iBusRepository;
    }

    @Autowired
    private IBusRepository busRepository;

    @Autowired
    private IAgencyOfficeRepository officeRepository;



    @Override
    @Transactional
    public BusResponse registerBus(Integer officeId, BusRequest requestDTO) {
        AgencyOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("AgencyOffice", "officeId", officeId));

        // Check for duplicate registration number
        if (iBusRepository.existsByRegistrationNumber(requestDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Bus", "registrationNumber",
                    requestDTO.getRegistrationNumber());
        }

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber(requestDTO.getRegistrationNumber());
        bus.setCapacity(requestDTO.getCapacity());
        bus.setType(requestDTO.getType());

        Bus savedBus = iBusRepository.save(bus);
        return convertToResponseDTO(savedBus);
    }

    @Override
    public List<BusResponse> getBusesByOffice(Integer officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new ResourceNotFoundException("AgencyOffice", "officeId", officeId);
        }

        return iBusRepository.findByOffice_OfficeId(officeId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponse getBusById(Integer busId) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));
        return convertToResponseDTO(bus);
    }

    @Override
    @Transactional
    public BusResponse updateBus(Integer busId, BusRequest requestDTO) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));

        // Check if registration number is being changed and if it's duplicate
        if (!bus.getRegistrationNumber().equals(requestDTO.getRegistrationNumber()) &&
                iBusRepository.existsByRegistrationNumber(requestDTO.getRegistrationNumber())) {
            throw new DuplicateResourceException("Bus", "registrationNumber",
                    requestDTO.getRegistrationNumber());
        }

        bus.setRegistrationNumber(requestDTO.getRegistrationNumber());
        bus.setCapacity(requestDTO.getCapacity());
        bus.setType(requestDTO.getType());

        Bus updatedBus = iBusRepository.save(bus);
        return convertToResponseDTO(updatedBus);
    }

    @Override
    @Transactional
    public void retireBus(Integer busId) {
        Bus bus = iBusRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", busId));
        iBusRepository.delete(bus);
    }

    private BusResponse convertToResponseDTO(Bus bus) {
        BusResponse dto = new BusResponse();
        dto.setBusId(bus.getBusId());
        dto.setOfficeId(bus.getOffice().getOfficeId());
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setCapacity(bus.getCapacity());
        dto.setType(bus.getType());
        return dto;
    }
}
 