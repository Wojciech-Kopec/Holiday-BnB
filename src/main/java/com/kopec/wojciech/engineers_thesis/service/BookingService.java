package com.kopec.wojciech.engineers_thesis.service;

import com.kopec.wojciech.engineers_thesis.dto.AccommodationDto;
import com.kopec.wojciech.engineers_thesis.dto.BookingDto;
import com.kopec.wojciech.engineers_thesis.dto.UserDto;
import com.kopec.wojciech.engineers_thesis.exception.GuestsCountBiggerThanAvailable;
import com.kopec.wojciech.engineers_thesis.model.Booking;
import com.kopec.wojciech.engineers_thesis.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        if (bookingDto.getSubmissionDate() == null)
            bookingDto.setSubmissionDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return mapSavedBooking(bookingDto);
    }

    private BookingDto mapSavedBooking(BookingDto bookingDto) {
        Booking booking = BookingDto.toEntity(bookingDto);
        Booking savedBooking = bookingRepository.saveEntity(booking);
        return BookingDto.toDto(savedBooking);
    }

    public BookingDto update(BookingDto bookingDto) {
        int accommodationMaxGuests = bookingDto.getAccommodation().getMaxGuests();
        if(bookingDto.getGuestsCount() > accommodationMaxGuests)
            throw new GuestsCountBiggerThanAvailable("Number of guests cannot be accepted. Max guests for this apartment is " + accommodationMaxGuests);
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
        return bookingRepository.findAllByAccommodationOrderBySubmissionDateDesc(AccommodationDto.toEntity
                (accommodation))
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
