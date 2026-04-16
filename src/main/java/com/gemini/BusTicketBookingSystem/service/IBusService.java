package com.gemini.BusTicketBookingSystem.service;


import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;

import java.util.List;

public interface IBusService {


    BusResponse registerBus(Integer officeId, BusRequest requestDTO);
    List<BusResponse> getBusesByOffice(Integer officeId);
    BusResponse getBusById(Integer busId);
    BusResponse updateBus(Integer busId, BusRequest requestDTO);
    void retireBus(Integer busId);
}