package com.example.salon.service.map;

import com.example.salon.service.modal.Salon;
import com.example.salon.service.payload.dto.SalonDTO;

public class SalonMapModal {

    public static SalonDTO mapToDTO(Salon salon){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());
        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setImages(salon.getImages());
        salonDTO.setEmail(salon.getEmail());


        return salonDTO;
    }
}
