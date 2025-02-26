package com.msiwy.GrandHotel.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.msiwy.GrandHotel.entity.Room;
import com.msiwy.GrandHotel.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuests;
    private String bookingConfirmationCode;

    private UserDTO user;
    private RoomDTO room;

}
