package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AccommodationCriteria {
    private String name;
    private List<AccommodationType> accommodationTypes;
    private Integer requiredGuestCount;
    private Integer minPricePerNight;
    private Integer maxPricePerNight;
    private List<String> amenities;
    private String country;
    private String city;
}
