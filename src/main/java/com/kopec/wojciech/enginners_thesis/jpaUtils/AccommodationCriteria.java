package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationCriteria {
    private String name;
    private String accommodationType;
    private int requiredGuestCount;
    private int pricePerNight;
    private Localization localization;
    private List<String> amenities;
}
