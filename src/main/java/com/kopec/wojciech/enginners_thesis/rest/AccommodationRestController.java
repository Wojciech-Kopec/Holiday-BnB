package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@AllArgsConstructor
public class AccommodationRestController {

    private AccommodationService accommodationService;

    @GetMapping(value="/{id}")
    public ResponseEntity<Accommodation> getResource(Model model, @PathVariable Integer id)
    {
        Accommodation accommodation = accommodationService.findAccommodationById(id);
        if(accommodation == null){
            return new ResponseEntity<Accommodation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Accommodation>(accommodation, HttpStatus.FOUND); //TODO HttpStatus. ~FOUND or ~OK ?
    }
//
//    @PostMapping()
//    public String saveResource(Model model)
//    {
//        //store resource via someService and return to view
//    }
//
//    @PutMapping(value="/{id}")
//    public String modifyResource(Model model, @PathVariable Integer id)
//    {
//        //update resource with given identifier and given data via someService and return to view
//    }
//
//    @DeleteMapping(value="/{id}")
//    public String deleteResource(Model model, @PathVariable Integer id)
//    {
//        //delete resource with given identifier via someService and return to view
//    }

    @GetMapping("")
    public List<AccommodationDto> findAll() {
        return accommodationService.findAll();
    }
}
