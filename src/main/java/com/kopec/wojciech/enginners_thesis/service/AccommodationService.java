package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationCriteria;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public void createAccommodation(AccommodationDto accommodationDto) {
        accommodationRepository.save(AccommodationDto.toEntity(accommodationDto));
    }

    public void deleteAccommodation(AccommodationDto accommodationDto) {
        accommodationRepository.delete(AccommodationDto.toEntity(accommodationDto));
    }

    public List<AccommodationDto> findByCriteria(AccommodationCriteria criteria) {
        return ((List<Accommodation>) accommodationRepository.findAll(
                AccommodationSpecification.withCriteria(criteria)))
                .stream()
                .map(AccommodationDto::toDto)
                .collect(Collectors.toList());
    }

    public List<AccommodationDto> findAll() {
        return accommodationRepository.findAll()
                .stream().map(AccommodationDto::toDto)
                .collect(Collectors.toList());
    }



    public Accommodation findAccommodationById(Integer id) {
        return null;
    }

    public void saveAccommodation(@Valid Accommodation accommodation) {

    }
}
