package com.kopec.wojciech.enginners_thesis.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccommodationDto {

    private Integer id;

    @NotNull
    @Size(min = 10, max = 120)
    private String name;

    @NotBlank //todo
    @Size(min = 100)
    private String description;

    @NotNull
    private AccommodationType accommodationType;

    @NotNull
    @Positive
    private int maxGuests;

    @NotNull
    @Positive
    private int pricePerNight;

    @NotNull
    private LocalizationDto localization;

    @NotNull
    private List<AmenityDto> amenities;

    @NotNull
    private UserDto user;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    private static ModelMapper modelMapper = new ModelMapper();


    public static AccommodationDto toDto(Accommodation accommodation) {
        return accommodation != null ? modelMapper.map(accommodation, AccommodationDto.class) : null;
    }

    public static Accommodation toEntity(AccommodationDto accommodationDto) {
        return accommodationDto != null ? modelMapper.map(accommodationDto, Accommodation.class) : null;
    }
}
