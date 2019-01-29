package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;
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

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private UserService userService;
    private final BookingService bookingService;
    private final AccommodationService accommodationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);


    @Autowired
    public UserRestController(UserService userService,
                              BookingService bookingService,
                              AccommodationService accommodationService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.accommodationService = accommodationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto user) {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_set}");
        UserDto savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(savedUser, headers, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody @Valid UserDto user) {
        if (!id.equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_not_consistent}");
        UserDto updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> get(@PathVariable Integer id) {
        UserDto user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDto>> findAll(@RequestParam(required = false) String username) {
        List<UserDto> users;
        if (username != null)
            users = userService.findByUsernameContaining(username);
        else
            users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        UserDto userToDelete = userService.findById(id);
        if (userToDelete != null) {
            userService.delete(userToDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}/bookings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BookingDto>> findAllBookings(@PathVariable Integer id) {
        UserDto user = userService.findById(id);
        if (user != null) {
            List<BookingDto> usersBookings = bookingService.findAllByUser(user);
            return new ResponseEntity<>(usersBookings, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}/accommodations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AccommodationDto>> findAllAccommodations(@PathVariable Integer id) {
        UserDto user = userService.findById(id);
        if (user != null) {
            LOGGER.info(user.toString());
            List<AccommodationDto> usersAccommodations = accommodationService.findAllByUser(user);
            return new ResponseEntity<>(usersAccommodations, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}