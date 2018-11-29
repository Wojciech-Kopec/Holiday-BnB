package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Booking;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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
        BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
        return bookingDto;
    }

    @Override
    public Booking toEntity(BookingDto bookingDto) {
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        return booking;
    }
}
