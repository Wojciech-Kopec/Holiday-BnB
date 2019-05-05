package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationCriteria;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Transactional
    public void delete(final AccommodationDto accommodationDto) {
        Accommodation accommodation = AccommodationDto.toEntity(accommodationDto);
        //TODO find a way to do it within JPA
        accommodationRepository.delete(accommodation);
    }

    public List<AccommodationDto> findAll(AccommodationCriteria criteria) {
        Iterable<Accommodation> accommodations;
        if (criteria != null) {
            accommodations = accommodationRepository.findAll(AccommodationSpecification.withCriteria(criteria));
        } else {
            accommodations = accommodationRepository.findAll();
        }
        return StreamSupport.stream(accommodations.spliterator(), false)
                .map(AccommodationDto::toDto)
                .collect(Collectors.toList());
    }

    //TODO to be used in Users REST API
    public List<AccommodationDto> findAllByUser(UserDto user) {
        return accommodationRepository.findAllByUserOrderByCreatedDateDesc(UserDto.toEntity(user))
                .stream()
                .map(AccommodationDto::toDto)
                .collect(Collectors.toList());
    }

    public AccommodationDto findById(Integer id) {
        return AccommodationDto.toDto(accommodationRepository.findById(id).orElse(null));
    }
}
