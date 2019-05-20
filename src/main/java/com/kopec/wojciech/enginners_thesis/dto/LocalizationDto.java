package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class LocalizationDto {
    private Integer id;
    private AccommodationDto accommodationDto;
    private String country;
    private String city;
    private String address;

    private static ModelMapper modelMapper = new ModelMapper();

    public static LocalizationDto toDto(Localization localization) {
        return localization != null ? modelMapper.map(localization, LocalizationDto.class) : null;
    }

    public static Localization toEntity(LocalizationDto localizationDto) {
        return localizationDto != null ? modelMapper.map(localizationDto, Localization.class) : null;
    }
}
