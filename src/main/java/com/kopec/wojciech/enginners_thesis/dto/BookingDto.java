package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Booking;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class BookingDto {
    private long id;
    private AccommodationDto accommodation;
    private UserDto user;
    private int guestsCount;
    private String status;
    private LocalDateTime submissionDate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int finalPrice;

    private static ModelMapper modelMapper = new ModelMapper();


    public static BookingDto toDto(Booking booking) {
        return modelMapper.map(booking, BookingDto.class);
    }

    public static Booking toEntity(BookingDto bookingDto) {
        return modelMapper.map(bookingDto, Booking.class);
    }
}
