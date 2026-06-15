package com.example.booking.service.service;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.BookingRequest;
import com.example.booking.service.dto.SalonDTO;
import com.example.booking.service.dto.ServiceDTO;
import com.example.booking.service.dto.UserDTO;
import com.example.booking.service.model.Booking;
import com.example.booking.service.model.SalonReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IBookingService {

    Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingByCustomerId(Long customerId);

    List<Booking> getBookingBySalonId(Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingByDate(LocalDate date, Long salonId);

    SalonReport geSalonReport(Long salonId);


}
