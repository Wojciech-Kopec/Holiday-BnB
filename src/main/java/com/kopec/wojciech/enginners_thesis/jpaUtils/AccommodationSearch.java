package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationSearch {
    private String name;
    private String accommodationType;
    private int requiredGuestCount;
    private int pricePerNight;
    private LocalizationSearch localizationSearch;
    private List<Amenity> amenities;
}
