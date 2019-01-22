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

    public AccommodationDto save(AccommodationDto accommodationDto) {
        return mapSavedAccommodation(accommodationDto);
    }

    private AccommodationDto mapSavedAccommodation(AccommodationDto accommodationDto) {
        Accommodation accommodation = AccommodationDto.toEntity(accommodationDto);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        return AccommodationDto.toDto(savedAccommodation);
    }

    public AccommodationDto update(AccommodationDto accommodationDto) {
        return mapSavedAccommodation(accommodationDto);
    }

    public void delete(final AccommodationDto accommodationDto) {
        accommodationRepository.delete(AccommodationDto.toEntity(accommodationDto));
    }

    public List<AccommodationDto> findAll() {
        return accommodationRepository.findAll().stream().map(AccommodationDto::toDto).collect(Collectors.toList());
    }

//    public AccommodationDto findByAccommodationname(String accommodationname) {
//        return AccommodationDto.toDto(accommodationRepository.findByAccommodationname(accommodationname));
//    }
//
//    public List<AccommodationDto> findByAccommodationnameContaining(String accommodationname) {
//        return accommodationRepository.findAllByAccommodationnameContainingIgnoreCaseOrderByAccommodationnameAsc(accommodationname)
//                .stream()
//                .map(AccommodationDto::toDto)
//                .collect(Collectors.toList());
//    }

    public AccommodationDto findById(Integer id) {
        return AccommodationDto.toDto(accommodationRepository.findById(id).orElse(null));
    }
}
