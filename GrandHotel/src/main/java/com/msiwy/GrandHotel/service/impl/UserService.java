package com.msiwy.GrandHotel.service.impl;

import com.msiwy.GrandHotel.dto.LoginRequest;
import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.entity.User;
import com.msiwy.GrandHotel.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Override
    public Response register(User loginRequest) {
        return null;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Response getAllUsers() {
        return null;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        return null;
    }

    @Override
    public Response deleteUser(String userId) {
        return null;
    }

    @Override
    public Response getUserById(String userId) {
        return null;
    }

    @Override
    public Response getMyInfo(String userId) {
        return null;
    }
}
