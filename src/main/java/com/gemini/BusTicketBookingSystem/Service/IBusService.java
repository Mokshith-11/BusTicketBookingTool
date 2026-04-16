package com.gemini.BusTicketBookingSystem.Service;


import com.gemini.BusTicketBookingSystem.Dto.Request.BusRequest;
import com.gemini.BusTicketBookingSystem.Dto.Response.BusResponse;

import java.util.List;

public interface IBusService {


    BusResponse registerBus(Integer officeId, BusRequest requestDTO);
    List<BusResponse> getBusesByOffice(Integer officeId);
    BusResponse getBusById(Integer busId);
    BusResponse updateBus(Integer busId, BusRequest requestDTO);
    void retireBus(Integer busId);
}