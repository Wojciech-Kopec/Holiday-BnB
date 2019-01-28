package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingDto save(BookingDto bookingDto) {
        return mapSavedBooking(bookingDto);
    }

    private BookingDto mapSavedBooking(BookingDto bookingDto) {
        Booking booking = BookingDto.toEntity(bookingDto);
        Booking savedBooking = bookingRepository.save(booking);
        return BookingDto.toDto(savedBooking);
    }

    public BookingDto update(BookingDto bookingDto) {
        return mapSavedBooking(bookingDto);
    }

    public void delete(final BookingDto bookingDto) {
        bookingRepository.delete(BookingDto.toEntity(bookingDto));
    }

    public List<BookingDto> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingDto::toDto)
                .collect(Collectors.toList());
    }

    //TODO to be used in Users REST API
    public List<BookingDto> findAllByUser(UserDto user) {
        return bookingRepository.findAllByUserOrderBySubmissionDateDesc(UserDto.toEntity(user))
                .stream()
                .map(BookingDto::toDto)
                .collect(Collectors.toList());
    }

    //TODO to be used in Accommodation REST API
    public List<BookingDto> findAllByAccommodation(AccommodationDto accommodation) {
        return bookingRepository.findAllByAccommodationOrderBySubmissionDateDesc(AccommodationDto.toEntity(accommodation))
                .stream()
                .map(BookingDto::toDto)
                .collect(Collectors.toList());
    }

    //TODO to be used in Accommodation REST API
    public List<BookingDto> findAllByAccommodation(Integer accommodationId) {
        return bookingRepository.findAllByAccommodation_IdOrderBySubmissionDateDesc(accommodationId)
                .stream()
                .map(BookingDto::toDto)
                .collect(Collectors.toList());
    }

    public BookingDto findById(Integer id) {
        return BookingDto.toDto(bookingRepository.findById(id).orElse(null));
    }
}
