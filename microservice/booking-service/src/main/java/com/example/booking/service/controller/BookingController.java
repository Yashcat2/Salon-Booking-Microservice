package com.example.booking.service.controller;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.*;
import com.example.booking.service.mapper.BookingMapper;
import com.example.booking.service.model.Booking;
import com.example.booking.service.model.SalonReport;
import com.example.booking.service.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));

        Set<ServiceDTO> serviceDTOS = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        serviceDTOS.add(serviceDTO);
        Booking booking = bookingService.createBooking(bookingRequest,userDTO,salon,serviceDTOS);

        return  ResponseEntity.ok(booking);

    }

    @GetMapping("/customer")
    public  ResponseEntity<Set<BookingDTO>> getBookingByCustomer (){
        List<Booking> bookings = bookingService.getBookingByCustomerId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public  ResponseEntity<Set<BookingDTO>> getBookingBySalon (){

        List<Booking> bookings = bookingService.getBookingBySalonId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public  ResponseEntity<BookingDTO> getBookingById (@PathVariable Long bookingId) throws Exception {

        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public  ResponseEntity<BookingDTO> updateBookingStatus (@PathVariable Long bookingId, @RequestParam BookingStatus bookingStatus) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId,bookingStatus);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slot/salon/{salonId}/date/{date}")
    public  ResponseEntity<List<BookingSlotDTO>> getBookingSlot (@PathVariable Long salonId,@RequestParam(required = false) LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingByDate(date,salonId);

        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }


    @GetMapping("/report")
        public  ResponseEntity<SalonReport> getBookingSlot () throws Exception {

            SalonReport report = bookingService.geSalonReport(1L);

            return ResponseEntity.ok(report);
        }


}
