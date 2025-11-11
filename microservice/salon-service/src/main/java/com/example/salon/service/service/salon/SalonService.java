package com.example.salon.service.service.salon;

import com.example.salon.service.modal.Salon;
import com.example.salon.service.payload.dto.SalonDTO;
import com.example.salon.service.payload.dto.UserDTO;
import com.example.salon.service.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonService implements ISalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());


        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon != null && salon.getOwnerId().equals((user.getId()))) {
            existingSalon.setCity(salon.getCity());
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            salon.setOwnerId(user.getId());
            salon.setOpenTime(salon.getOpenTime());
            salon.setCloseTime(salon.getCloseTime());
            salon.setPhoneNumber(salon.getPhoneNumber());
            return salonRepository.save(existingSalon);

        }
        throw new Exception("Salon not found");
    }

    @Override
    public List<Salon> getAllSalons() {

        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long id) throws Exception {
        Salon salon = salonRepository.findById(id).orElse(null);
        if (salon == null) {
            throw new Exception("salon not exist");
        }

        return salon;
}
    @Override
    public Salon getSalonOwnerById(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> seaerchSalonByCity(String city) {
        return salonRepository.searchSalon(city);
    }
}
