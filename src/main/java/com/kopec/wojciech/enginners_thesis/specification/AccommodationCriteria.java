package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import com.kopec.wojciech.enginners_thesis.model.Localization;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccommodationCriteria {
    private String name;
    private AccommodationType accommodationType;
    private int requiredGuestCount;
    private int pricePerNight;
    private Localization localization;
    private List<String> amenities;
}
