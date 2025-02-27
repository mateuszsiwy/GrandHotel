package com.msiwy.GrandHotel.utils;

import com.msiwy.GrandHotel.dto.BookingDTO;
import com.msiwy.GrandHotel.dto.RoomDTO;
import com.msiwy.GrandHotel.dto.UserDTO;
import com.msiwy.GrandHotel.entity.Booking;
import com.msiwy.GrandHotel.entity.Room;
import com.msiwy.GrandHotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // Reusable helper for creating RoomDTO
    private static RoomDTO toRoomDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomType(room.getRoomType());
        dto.setRoomPrice(room.getRoomPrice());
        dto.setRoomPhotoUrl(room.getRoomPhotoUrl());
        dto.setRoomDescription(room.getRoomDescription());
        return dto;
    }

    // Helper for bookings null/empty check
    private static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder confirmationCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = ALPHANUMERIC_STRING.charAt(SECURE_RANDOM.nextInt(ALPHANUMERIC_STRING.length()));
            confirmationCode.append(randomChar);
        }
        return confirmationCode.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
        return toRoomDTO(room); // Reuse helper method
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuests(booking.getTotalNumOfGuests());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }

    public static RoomDTO mapRoomWithBookingsToDTO(Room room) {
        RoomDTO roomDTO = toRoomDTO(room); // Reuse common fields mapping
        if (!isEmpty(room.getBookings())) {
            roomDTO.setBookings(
                    room.getBookings().stream()
                            .map(Utils::mapBookingEntityToBookingDTO)
                            .collect(Collectors.toList())
            );
        }
        return roomDTO;
    }

    public static BookingDTO mapBookingWithRoomAndUserToDTO(Booking booking, boolean mapUser) {
        BookingDTO bookingDTO = mapBookingEntityToBookingDTO(booking); // Reuse simple mapping

        // Map user if required
        if (mapUser && booking.getUser() != null) {
            bookingDTO.setUser(mapUserEntityToUserDTO(booking.getUser()));
        }

        // Map room
        if (booking.getRoom() != null) {
            bookingDTO.setRoom(toRoomDTO(booking.getRoom()));
        }
        return bookingDTO;
    }

    public static UserDTO mapUserWithBookingsToDTO(User user) {
        UserDTO userDTO = mapUserEntityToUserDTO(user); // Reuse simple mapping

        if (!isEmpty(user.getBookings())) {
            userDTO.setBookings(
                    user.getBookings().stream()
                            .map(booking -> mapBookingWithRoomAndUserToDTO(booking, false)) // Avoid circular references
                            .collect(Collectors.toList())
            );
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> users) {
        return users.stream()
                .map(Utils::mapUserEntityToUserDTO) // Reuse mapping
                .collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> rooms) {
        return rooms.stream()
                .map(Utils::mapRoomEntityToRoomDTO) // Reuse mapping
                .collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookings) {
        return bookings.stream()
                .map(Utils::mapBookingEntityToBookingDTO) // Reuse mapping
                .collect(Collectors.toList());
    }
}