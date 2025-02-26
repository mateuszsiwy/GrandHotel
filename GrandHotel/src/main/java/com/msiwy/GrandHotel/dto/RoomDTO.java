package com.msiwy.GrandHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msiwy.GrandHotel.entity.Booking;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private List<Booking> bookings;

}
