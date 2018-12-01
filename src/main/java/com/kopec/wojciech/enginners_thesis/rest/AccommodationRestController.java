package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@AllArgsConstructor
public class AccommodationRestController {

    private AccommodationService accommodationService;

    @GetMapping("")
    public List<AccommodationDto> findAll() {
        return accommodationService.findAll();
    }
}
