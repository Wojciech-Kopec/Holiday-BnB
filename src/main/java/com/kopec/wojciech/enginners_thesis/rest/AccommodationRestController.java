package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationCriteria;
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
@RequestMapping("/api/accommodations")
public class AccommodationRestController {

    private AccommodationService accommodationService;
    private BookingService bookingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccommodationRestController.class);


    @Autowired
    public AccommodationRestController(AccommodationService accommodationService, BookingService bookingService) {
        this.accommodationService = accommodationService;
        this.bookingService = bookingService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccommodationDto> save(@RequestBody @Valid AccommodationDto accommodation) {
        if (accommodation.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_set}");
        AccommodationDto savedAccommodation = accommodationService.save(accommodation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAccommodation.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(savedAccommodation, headers, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccommodationDto> update(@PathVariable Integer id, @RequestBody @Valid AccommodationDto accommodation) {
        if (!id.equals(accommodation.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_not_consistent}");
        AccommodationDto updatedAccommodation = accommodationService.update(accommodation);
        return new ResponseEntity<>(updatedAccommodation, HttpStatus.OK);

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccommodationDto> get(@PathVariable Integer id) {
        AccommodationDto accommodation = accommodationService.findById(id);
        if (accommodation != null) {
            return new ResponseEntity<>(accommodation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AccommodationDto>> findAll(AccommodationCriteria criteria) {
        List<AccommodationDto> accommodations = accommodationService.findAll(criteria);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccommodationDto> delete(@PathVariable Integer id) {
        AccommodationDto accommodationToDelete = accommodationService.findById(id);
        if (accommodationToDelete != null) {
            accommodationService.delete(accommodationToDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/bookings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookingDto>> findAllBookings(@PathVariable Integer id) {
        AccommodationDto accommodation = accommodationService.findById(id);
        if (accommodation != null) {
            List<BookingDto> accommodationsBookings = bookingService.findAllByAccommodation(accommodation);
            return new ResponseEntity<>(accommodationsBookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}