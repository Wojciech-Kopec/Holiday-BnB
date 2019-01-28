package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.BookingStatus;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class BookingDto {
    private Integer id;
    private AccommodationDto accommodation;
    private UserDto user;
    private int guestsCount;
    private BookingStatus status;
    private LocalDateTime submissionDate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int finalPrice;

    private static ModelMapper modelMapper = new ModelMapper();


    public static BookingDto toDto(Booking booking) {
        if(booking != null)
        return modelMapper.map(booking, BookingDto.class);
    return null;
    }

    public static Booking toEntity(BookingDto bookingDto) {
        if(bookingDto != null)
        return modelMapper.map(bookingDto, Booking.class);
    return null;
    }
}
