package com.msiwy.GrandHotel.service.impl;

import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.dto.RoomDTO;
import com.msiwy.GrandHotel.entity.Room;
import com.msiwy.GrandHotel.exception.OurException;
import com.msiwy.GrandHotel.repo.BookingRepository;
import com.msiwy.GrandHotel.repo.RoomRepository;
import com.msiwy.GrandHotel.service.AwsS3Service;
import com.msiwy.GrandHotel.service.interfaces.IRoomService;
import com.msiwy.GrandHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try{
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoom(roomDTO);
        }catch(OurException e) {

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding a new room " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        List<String> roomTypeList = roomRepository.findDistinctRoomTypes();
        return roomTypeList;
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try{
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoomList(roomDTOList);
        }catch(OurException e) {

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all rooms" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try{
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("succesful");
        }catch(OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();

        try{
            String imageUrl = null;
            if(photo != null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if(roomType != null){
                room.setRoomType(roomType);
            }
            if(roomPrice != null){
                room.setRoomPrice(roomPrice);
            }
            if(description != null){
                room.setRoomDescription(description);
            }
            if(imageUrl != null){
                room.setRoomPhotoUrl(imageUrl);
            }

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoom(roomDTO);
        }catch(OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;         }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try{
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            RoomDTO roomDTO = Utils.mapRoomWithBookingsToDTO(room);
            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoom(roomDTO);
        }catch(OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try{
            List<Room> availableRooms = roomRepository.findAvailableRoomByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoomList(roomDTOList);
        }catch(OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try{
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setRoomList(roomDTOList);
        }catch(OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;
    }
}
