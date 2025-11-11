package com.example.salon.service.service.salon;

import com.example.salon.service.modal.Salon;
import com.example.salon.service.payload.dto.SalonDTO;
import com.example.salon.service.payload.dto.UserDTO;

import java.util.List;

public interface ISalonService {
    Salon createSalon(SalonDTO salon, UserDTO user);
    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception;
    List<Salon> getAllSalons();
    Salon getSalonById(Long id) throws Exception;
    Salon getSalonOwnerById(Long ownerId);
    List<Salon> seaerchSalonByCity(String city);


}
