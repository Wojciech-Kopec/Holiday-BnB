package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AccommodationCriteria {
    private String name;
    private List<Accommodation.AccommodationType> accommodationTypes;
    private Integer requiredGuestCount;
    private Integer minPricePerNight;
    private Integer maxPricePerNight;
    private List<String> amenities;
    private String country;
    private String city;
}
