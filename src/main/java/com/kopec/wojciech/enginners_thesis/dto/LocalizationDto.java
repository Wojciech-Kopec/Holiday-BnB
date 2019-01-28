package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class LocalizationDto {
    private Integer id;
    private String country;
    private String state;
    private String city;
    private String address;

    private static ModelMapper modelMapper = new ModelMapper();

    public static LocalizationDto toDto(Localization localization) {
        if(localization != null)
        return modelMapper.map(localization, LocalizationDto.class);
    return null;
    }

    public static Localization toEntity(LocalizationDto localizationDto) {
        if (localizationDto != null)
        return modelMapper.map(localizationDto, Localization.class);
    return null;
    }
}
