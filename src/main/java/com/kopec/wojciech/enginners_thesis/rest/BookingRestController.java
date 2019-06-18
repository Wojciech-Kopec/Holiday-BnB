package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.exception.GuestsCountBiggerThanAvailable;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private BookingService bookingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingRestController.class);

    @Autowired
    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingDto> save(@RequestBody @Valid BookingDto booking) {
        if (booking.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_set}");
        BookingDto savedBooking = bookingService.save(booking);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBooking.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(savedBooking, headers, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingDto> update(@PathVariable Integer id, @RequestBody @Valid BookingDto booking) {
        if (!id.equals(booking.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_not_consistent}");
        BookingDto updatedBooking = bookingService.update(booking);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingDto> get(@PathVariable Integer id) {
        BookingDto booking = bookingService.findById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookingDto>> findAll() {
        List<BookingDto> bookings = bookingService.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto> delete(@PathVariable Integer id) {
        BookingDto bookingToDelete = bookingService.findById(id);
        if (bookingToDelete != null) {
            bookingService.delete(bookingToDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
