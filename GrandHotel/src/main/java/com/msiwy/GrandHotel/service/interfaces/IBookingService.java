package com.msiwy.GrandHotel.service.interfaces;

import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}