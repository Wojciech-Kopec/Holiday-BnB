package com.kopec.wojciech.engineers_thesis.dto;

import com.kopec.wojciech.engineers_thesis.model.Booking;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class BookingDto {
    private Integer id;

    @NotNull
    private AccommodationDto accommodation;

    @NotNull
    private UserDto user;

    @Positive
    @NotNull
    private int guestsCount;

    private Booking.BookingStatus status;
    private LocalDateTime submissionDate;

    @Future
    @NotNull
    private LocalDate startDate;

    @Future
    @NotNull
    private LocalDate finishDate;

    @NotNull
    private int finalPrice;


    private static ModelMapper modelMapper = new ModelMapper();


    public static BookingDto toDto(Booking booking) {
        return booking != null ? modelMapper.map(booking, BookingDto.class) : null;
    }

    public static Booking toEntity(BookingDto bookingDto) {
        return bookingDto != null ? modelMapper.map(bookingDto, Booking.class) : null;
    }
}
