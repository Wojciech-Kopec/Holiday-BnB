package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
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

    public void createBooking(BookingDto bookingDto) {
        bookingRepository.save(BookingDto.toEntity(bookingDto));
    }

    public void deleteBooking(BookingDto bookingDto) {
        bookingRepository.delete(BookingDto.toEntity(bookingDto));
    }

    public List<BookingDto> findAll() {
        return bookingRepository.findAll()
                .stream().map(BookingDto::toDto)
                .collect(Collectors.toList());
    }
}
