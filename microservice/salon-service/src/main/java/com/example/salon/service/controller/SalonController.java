package com.example.salon.service.controller;

import com.example.salon.service.map.SalonMapModal;
import com.example.salon.service.modal.Salon;
import com.example.salon.service.payload.dto.SalonDTO;
import com.example.salon.service.payload.dto.UserDTO;
import com.example.salon.service.service.salon.ISalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SalonController {

    private final ISalonService salonService;

    // http://localhost:5002/api/salon
    @PostMapping("/salon")
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapModal.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @PutMapping("/salon/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(@PathVariable Long salonId,
                                                @RequestBody SalonDTO salonDTO) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1 = SalonMapModal.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping("/salons")
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception {
        List<Salon> salon = salonService.getAllSalons();

        List<SalonDTO> salonDTOS = salon.stream().map((salon1) ->
                {
                    SalonDTO salonDTO1 = SalonMapModal.mapToDTO(salon1);
                    return salonDTO1;
                }
        ).toList();
        return ResponseEntity.ok(salonDTOS);
    }


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {
        Salon salon = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapModal.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }


    @GetMapping("/salon/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) throws Exception {
        List<Salon> salon = salonService.seaerchSalonByCity(city);
        List<SalonDTO> salonDTO = salon.stream().map((salon1) ->
                {
                    SalonDTO salonDTO1 = SalonMapModal.mapToDTO(salon1);
                    return salonDTO1;
                }
        ).toList();
        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long ownerId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.getSalonOwnerById(userDTO.getId());
        SalonDTO salonDTO = SalonMapModal.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }

}
