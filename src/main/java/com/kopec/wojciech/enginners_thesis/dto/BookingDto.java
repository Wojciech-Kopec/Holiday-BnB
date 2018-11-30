package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Booking;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class BookingDto implements DtoConvertible<Booking, BookingDto> {
    private long id;
    private AccommodationDto accommodation;
    private UserDto user;
    private int guestsCount;
    private String status;
    private LocalDateTime submissionDate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int finalPrice;


    @Override
    public BookingDto toDto(Booking booking) {
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public Booking toEntity() {
        Booking booking = modelMapper.map(this, Booking.class);
        return booking;
    }
}
