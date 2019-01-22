package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationRestController {

//    private AccommodationService accommodationService;
//
//    @Autowired
//    public AccommodationRestController(AccommodationService accommodationService) {
//        this.accommodationService = accommodationService;
//    }
//
//    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<AccommodationDto> register(@RequestBody AccommodationDto accommodation) {
//        if (accommodation.getId() != null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_set}");
//        AccommodationDto savedAccommodation = accommodationService.save(accommodation);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedAccommodation.getId())
//                .toUri();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(location);
//        return new ResponseEntity<>(savedAccommodation, headers, HttpStatus.CREATED);
//    }
//
//    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<AccommodationDto> update(@PathVariable Integer id, @RequestBody AccommodationDto accommodation) {
//        if (!id.equals(accommodation.getId()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{resource.id_not_consistent}");
//        AccommodationDto updatedAccommodation = accommodationService.update(accommodation);
//        return new ResponseEntity<>(updatedAccommodation, HttpStatus.OK);
//
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<List<AccommodationDto>> findAll(@RequestParam(required = false) String accommodationname) {
//        List<AccommodationDto> accommodations;
//        if (accommodationname != null)
//            accommodations = accommodationService.findByAccommodationnameContaining(accommodationname);
//        else
//            accommodations = accommodationService.findAll();
//        return new ResponseEntity<>(accommodations, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<AccommodationDto> delete(@PathVariable Integer id) {
//        AccommodationDto accommodationToDelete = accommodationService.findById(id);
//        if (accommodationToDelete != null) {
//            accommodationService.delete(accommodationToDelete);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}