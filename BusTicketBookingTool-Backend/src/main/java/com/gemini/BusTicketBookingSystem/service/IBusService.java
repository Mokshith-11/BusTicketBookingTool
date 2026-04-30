package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;

import java.util.List;
/*
 * - This service interface lists the Bus actions that controllers are allowed to call.
 * - The interface shows the contract: method names, input DTOs/IDs, and response DTOs.
 * - The implementation class contains the actual validations, repository calls, and save/update logic.
 */

public interface IBusService {


    BusResponse registerBus(Integer officeId, BusRequest requestDTO);
    List<BusResponse> getAllBuses();
    List<BusResponse> getBusesByOffice(Integer officeId);
    BusResponse getBusById(Integer busId);
    BusResponse updateBus(Integer busId, BusRequest requestDTO);
    void retireBus(Integer busId);
}
