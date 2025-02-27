package com.msiwy.GrandHotel.service.interfaces;

import com.msiwy.GrandHotel.dto.LoginRequest;
import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.entity.User;

public interface IUserService {
    Response register(User loginRequest);

    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String userId);

}