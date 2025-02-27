package com.msiwy.GrandHotel.service.impl;

import com.msiwy.GrandHotel.dto.LoginRequest;
import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.dto.UserDTO;
import com.msiwy.GrandHotel.entity.User;
import com.msiwy.GrandHotel.exception.OurException;
import com.msiwy.GrandHotel.repo.UserRepository;
import com.msiwy.GrandHotel.service.interfaces.IUserService;
import com.msiwy.GrandHotel.utils.JWTUtils;
import com.msiwy.GrandHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try{
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new OurException("User with the given email already exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setUser(userDTO);

        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during User registration" + e.getMessage());
//            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User not found during login"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("successful");
            return response;
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during User login" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();
        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("succesfull");
            response.setUserList(userDTOList);


        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during getting all users" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserWithBookingsToDTO(user);
            response.setStatusCode(200);
            response.setMessage("succesfull");
            response.setUser(userDTO);


        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during getting all users" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("succesfull");


        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during getting all users" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("succesfull");
            response.setUser(userDTO);


        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during getting all users" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("succesfull");
            response.setUser(userDTO);

        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occured during getting all users" + e.getMessage());
//            throw new RuntimeException(e);
        }

        return response;
    }
}
