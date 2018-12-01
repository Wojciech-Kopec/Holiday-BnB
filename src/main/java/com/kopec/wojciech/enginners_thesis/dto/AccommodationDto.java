package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccommodationDto {
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

    private static ModelMapper modelMapper = new ModelMapper();


    public static AccommodationDto toDto(Accommodation accommodation) {
        return modelMapper.map(accommodation, AccommodationDto.class);
    }

    public static Accommodation toEntity(AccommodationDto accommodationDto) {
        return modelMapper.map(accommodationDto, Accommodation.class);
    }
}
