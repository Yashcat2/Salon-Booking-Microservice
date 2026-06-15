package com.example.booking.service.service;

import com.example.booking.service.domain.BookingStatus;
import com.example.booking.service.dto.BookingRequest;
import com.example.booking.service.dto.SalonDTO;
import com.example.booking.service.dto.ServiceDTO;
import com.example.booking.service.dto.UserDTO;
import com.example.booking.service.model.Booking;
import com.example.booking.service.model.SalonReport;
import com.example.booking.service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    @Override
    public Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salonDTO,bookingStartTime,bookingEndTime);

        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();

        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(userDTO.getId());
        newBooking.setSalonId(salonDTO.getId());
        newBooking.setServiceId(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO,LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {
        List<Booking> existingBookings = getBookingBySalonId(salonDTO.getId());
        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime)||salonCloseTime.isAfter(salonCloseTime)){
            throw new Exception("Booking time must be within salon's working hours");
        }
        for(Booking existingBooking: existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("Slot not available, choose different time");
            }

            if (bookingStartTime.isEqual(bookingStartTime)|| bookingEndTime.isEqual(bookingEndTime))
                throw new Exception("Slot not available, choose different time");

        }
        return  true;
    }

    @Override
    public List<Booking> getBookingByCustomerId(Long customerId) {
        return bookingRepository.findBookingByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return bookingRepository.findBookingBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking==null){
            throw new Exception("booking not found");
        }
        return null;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {

        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingBySalonId(salonId);
        if (date ==null){
            return allBookings;
        }

        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(),date)|| isSameDate(booking.getEndTime(),date)).
                collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }
    @Override
    public SalonReport geSalonReport(Long salonId) {
        List<Booking> bookings = getBookingBySalonId(salonId);

        int totalEarnings = bookings.stream()
                .mapToInt(Booking::getTotalPrice)
                .sum();

        Integer totalBooking =bookings.size();

        List<Booking> cancelledBooking = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());

        Double totalRefund = cancelledBooking.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
//        report.setSalonName();
        report.setCancelBookings(cancelledBooking.size());
        report.setTotalBookings(totalBooking);
        report.setTotalEarnings(totalEarnings);
        report.setTotalRefund(totalRefund);
        report.setTotalBookings(totalBooking);

        return report;
    }
}
