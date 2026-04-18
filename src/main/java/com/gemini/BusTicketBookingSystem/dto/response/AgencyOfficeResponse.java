package com.gemini.BusTicketBookingSystem.dto.response;

import lombok.*;


@Builder


public class AgencyOfficeResponse {
    private Integer officeId;
    private Integer agencyId;
    private String agencyName;
    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;
    private AddressResponse officeAddress;


 }

