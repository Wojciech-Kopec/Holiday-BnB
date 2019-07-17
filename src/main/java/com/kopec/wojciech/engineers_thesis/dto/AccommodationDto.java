package com.kopec.wojciech.engineers_thesis.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kopec.wojciech.engineers_thesis.model.Accommodation;
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

    @NotBlank
    @Size(min = 10, max = 150)
    private String name;

    @NotBlank
    @Size(min = 100, max = 2000)
    private String description;

    @NotNull
    private Accommodation.AccommodationType accommodationType;

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
