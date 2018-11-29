package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalizationDto implements DtoConvertible<Localization,LocalizationDto> {
    private long id;
    private String country;
    private String state;
    private String city;
    private String address;


    @Override
    public LocalizationDto toDto(Localization localization) {
        LocalizationDto localizationDto = modelMapper.map(localization, LocalizationDto.class);
        return localizationDto;
    }

    @Override
    public Localization toEntity(LocalizationDto localizationDto) {
        Localization localization = modelMapper.map(localizationDto, Localization.class);
        return localization;
    }
}
