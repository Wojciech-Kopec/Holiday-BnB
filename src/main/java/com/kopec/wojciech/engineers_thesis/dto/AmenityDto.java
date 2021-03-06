package com.kopec.wojciech.engineers_thesis.dto;

import com.kopec.wojciech.engineers_thesis.model.Amenity;
import lombok.Data;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString(exclude = {"accommodationId"})
public class AmenityDto {
    private Integer id;

    @NotNull
    private Amenity.AmenityType type;

    @NotBlank
    private String description;

    @NotBlank
    private Integer accommodationId;

    private static ModelMapper modelMapper = new ModelMapper();


    public static AmenityDto toDto(Amenity amenity) {
        return amenity != null ? modelMapper.map(amenity, AmenityDto.class) : null;
    }

    public static Amenity toEntity(AmenityDto amenityDto) {
        return amenityDto != null ? modelMapper.map(amenityDto, Amenity.class) : null;
    }
}
