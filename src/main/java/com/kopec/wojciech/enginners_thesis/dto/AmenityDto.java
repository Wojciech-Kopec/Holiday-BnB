package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Amenity;

public class AmenityDto implements DtoConvertible<Amenity, AmenityDto> {
    private long id;
    private String type;
    private String description;

    @Override
    public AmenityDto toDto(Amenity amenity) {
        AmenityDto amenityDto = modelMapper.map(amenity, AmenityDto.class);
        return amenityDto;
    }

    @Override
    public Amenity toEntity(AmenityDto amenityDto) {
        Amenity amenity = modelMapper.map(amenityDto, Amenity.class);
        return amenity;
    }
}
