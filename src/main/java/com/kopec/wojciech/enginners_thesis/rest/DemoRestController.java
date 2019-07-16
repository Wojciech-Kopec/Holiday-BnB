package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.EntityGenerator;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
public class DemoRestController {
    private UserService userService;
    private AccommodationService accommodationService;
    private BookingService bookingService;

    private EntityGenerator entityGenerator = EntityGenerator.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
    private static String result;

    @Autowired
    public DemoRestController(UserService userService, AccommodationService accommodationService, BookingService
            bookingService) {
        this.userService = userService;
        this.accommodationService = accommodationService;
        this.bookingService = bookingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> get() {
        if (result == null) {
            result = persistEntities();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private String persistEntities() {
        int beforeUsersCount = userService.findAll().size();
        int beforeAccommodationsCount = accommodationService.findAll(null).size();
        int beforeBookingsCount = bookingService.findAll().size();

        logger.info("START: Persisting entities for Demo Mode");
        persistUsers(userService);
        persistAccommodations(accommodationService);
        persistBookings(bookingService);
        logger.info("FINISHED: Persisting entities for Demo Mode");

        int afterUsersCount = userService.findAll().size();
        int afterAccommodationsCount = accommodationService.findAll(null).size();
        int afterBookingsCount = bookingService.findAll().size();

        String msg = "Users count before: " + beforeUsersCount + "\n" +
                "Users count after: " + afterUsersCount + "\n" +
                "Users persisted: " + (afterUsersCount - beforeUsersCount) + "\n\n" +
                "Accommodations count before: " + beforeAccommodationsCount + "\n" +
                "Accommodations count after: " + afterAccommodationsCount + "\n" +
                "Accommodations persisted: " + (afterAccommodationsCount - beforeAccommodationsCount) + "\n\n" +
                "Bookings count before: " + beforeBookingsCount + "\n" +
                "Bookings count after: " + afterBookingsCount + "\n" +
                "Bookings persisted: " + (afterBookingsCount - beforeBookingsCount) + "\n\n";

        return msg;

    }

    private void persistBookings(BookingService bookingService) {
        entityGenerator.setBookings(entityGenerator.getBookings().stream()
                .map(BookingDto::toDto)
                .map(bookingService::save)
                .map(BookingDto::toEntity)
                .collect(Collectors.toList()));
    }

    private void persistAccommodations(AccommodationService accommodationService) {
        entityGenerator.setAccommodations(entityGenerator.getAccommodations().stream()
                .map(AccommodationDto::toDto)
                .map(accommodationService::save)
                .map(AccommodationDto::toEntity)
                .collect(Collectors.toList()));
    }

    private void persistUsers(UserService userService) {
        entityGenerator.setOwners(entityGenerator.getOwners().stream()
                .map(UserDto::toDto)
                .map(userService::save)
                .map(UserDto::toEntity)
                .collect(Collectors.toList()));

        entityGenerator.setClients(entityGenerator.getClients().stream()
                .map(UserDto::toDto)
                .map(userService::save)
                .map(UserDto::toEntity)
                .collect(Collectors.toList()));
    }
}
