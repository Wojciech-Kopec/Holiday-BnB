package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@AllArgsConstructor
public class AccommodationRestController {

    private AccommodationService accommodationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Accommodation> getResource(Model model, @PathVariable Integer id) {
        Accommodation accommodation = accommodationService.findAccommodationById(id);
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodation, HttpStatus.FOUND); //TODO HttpStatus. ~FOUND or ~OK ?
    }

//    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
//    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Accommodation> addAccommodation(@RequestBody @Valid Accommodation accommodation, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
//        BindingErrorsResponse errors = new BindingErrorsResponse();
//        HttpHeaders headers = new HttpHeaders();
//        if(bindingResult.hasErrors() || (accommodation == null)){
//            errors.addAllErrors(bindingResult);
//            headers.add("errors", errors.toJSON());
//            return new ResponseEntity<Accommodation>(headers, HttpStatus.BAD_REQUEST);
//        }
//        this.accommodationService.saveAccommodation(accommodation);
//        headers.setLocation(ucBuilder.path("/api/accommodations/{id}").buildAndExpand(accommodation.getId()).toUri());
//        return new ResponseEntity<>(accommodation, headers, HttpStatus.CREATED);
//    }

    @PostMapping()
    public String saveResource(Model model) {
        //store resource via someService and return to view
        return null;
    }

    @PutMapping(value = "/{id}")
    public String modifyResource(Model model, @PathVariable Integer id) {
        //update resource with given identifier and given data via someService and return to view
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteResource(Model model, @PathVariable Integer id) {
        //delete resource with given identifier via someService and return to view
        return null;
    }

    @GetMapping("")
    public List<AccommodationDto> findAll() {
        return accommodationService.findAll();
    }
}
