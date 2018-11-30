package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import lombok.Data;

@Data
public class AmenityDto implements DtoConvertible<Amenity, AmenityDto> {
    private long id;
    private String type;
    private String description;

    @Override
    public AmenityDto toDto(Amenity amenity) {
        return modelMapper.map(amenity, AmenityDto.class);
    }

    @Override
    public Amenity toEntity() {
        return modelMapper.map(this, Amenity.class);
    }
}
