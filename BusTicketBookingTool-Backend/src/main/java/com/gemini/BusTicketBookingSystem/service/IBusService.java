package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;

import java.util.List;
/*
 * - This service interface lists the Bus actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

/**
 * Service interface for bus management.
 * Defines the contract for registering, viewing, updating, and retiring buses.
 * Implemented by BusServiceImpl.
 */
public interface IBusService {

    /** Registers a new bus under a specific agency office */
    BusResponse registerBus(Integer officeId, BusRequest requestDTO);

    /** Retrieves all buses belonging to a specific office */
    List<BusResponse> getBusesByOffice(Integer officeId);

    /** Retrieves a bus by its unique ID */
    BusResponse getBusById(Integer busId);

    /** Updates an existing bus with new data */
    BusResponse updateBus(Integer busId, BusRequest requestDTO);

    /** Retires (deletes) a bus from the system */
    void retireBus(Integer busId);
}