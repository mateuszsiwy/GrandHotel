package com.msiwy.GrandHotel.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.msiwy.GrandHotel.entity.Booking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List<Booking> bookings = new ArrayList<>();
}
