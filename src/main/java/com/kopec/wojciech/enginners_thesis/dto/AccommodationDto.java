package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccommodationDto implements DtoConvertible<Accommodation, AccommodationDto> {
    private long id;
    private String name;
    private String description;
    private String accommodationType;
    private int maxGuests;
    private int pricePerNight;
    private LocalizationDto localization;
    private List<AmenityDto> amenities;
    private UserDto user;
    private LocalDateTime createdDate;


    @Override
    public AccommodationDto toDto(Accommodation accommodation) {
        return modelMapper.map(accommodation, AccommodationDto.class);
    }

    @Override
    public Accommodation toEntity() {
        return modelMapper.map(this, Accommodation.class);
    }
}
