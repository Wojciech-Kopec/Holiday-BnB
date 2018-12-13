package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
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
    private List<String> amenities;
    private LocalizationDto localization;
}
