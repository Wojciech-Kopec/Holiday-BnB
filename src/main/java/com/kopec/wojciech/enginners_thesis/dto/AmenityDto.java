package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import com.kopec.wojciech.enginners_thesis.model.AmenityType;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AmenityDto {
    private Integer id;

    @NotNull
    private AmenityType type;

    @NotBlank
    private String description;

    private static ModelMapper modelMapper = new ModelMapper();


    public static AmenityDto toDto(Amenity amenity) {
        return amenity != null ? modelMapper.map(amenity, AmenityDto.class) : null;
    }

    public static Amenity toEntity(AmenityDto amenityDto) {
        return amenityDto != null ? modelMapper.map(amenityDto, Amenity.class) : null;
    }
}
