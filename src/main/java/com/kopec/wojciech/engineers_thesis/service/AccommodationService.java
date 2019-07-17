package com.kopec.wojciech.engineers_thesis.service;

import com.kopec.wojciech.engineers_thesis.dto.AccommodationDto;
import com.kopec.wojciech.engineers_thesis.dto.UserDto;
import com.kopec.wojciech.engineers_thesis.model.Accommodation;
import com.kopec.wojciech.engineers_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.engineers_thesis.specification.AccommodationCriteria;
import com.kopec.wojciech.engineers_thesis.specification.AccommodationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        if(accommodationDto.getCreatedDate() == null)
        accommodationDto.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return mapSavedAccommodation(accommodationDto);
    }

    private AccommodationDto mapSavedAccommodation(AccommodationDto accommodationDto) {
        Accommodation accommodation = AccommodationDto.toEntity(accommodationDto);
        Accommodation savedAccommodation = accommodationRepository.saveEntity(accommodation);
        return AccommodationDto.toDto(savedAccommodation);
    }

    public AccommodationDto update(AccommodationDto accommodationDto) {
        return mapSavedAccommodation(accommodationDto);
    }

    @Transactional
    public void delete(final AccommodationDto accommodationDto) {
        Accommodation accommodation = AccommodationDto.toEntity(accommodationDto);
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
