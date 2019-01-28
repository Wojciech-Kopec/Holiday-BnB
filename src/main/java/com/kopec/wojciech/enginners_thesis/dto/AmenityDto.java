package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import com.kopec.wojciech.enginners_thesis.model.AmenityType;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class AmenityDto {
    private Integer id;
    private AmenityType type;
    private String description;

    private static ModelMapper modelMapper = new ModelMapper();


    public static AmenityDto toDto(Amenity amenity) {
        if(amenity != null)
        return modelMapper.map(amenity, AmenityDto.class);
    return null;
    }

    public static Amenity toEntity(AmenityDto amenityDto) {
        if(amenityDto != null)
        return modelMapper.map(amenityDto, Amenity.class);
    return null;
    }
}
