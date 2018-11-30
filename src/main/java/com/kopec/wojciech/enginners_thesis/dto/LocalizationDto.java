package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LocalizationDto implements DtoConvertible<Localization,LocalizationDto> {
    private long id;
    private String country;
    private String state;
    private String city;
    private String address;


    @Override
    public LocalizationDto toDto(Localization localization) {
        return modelMapper.map(localization, LocalizationDto.class);
    }

    @Override
    public Localization toEntity() {
        return modelMapper.map(this, Localization.class);
    }
}
