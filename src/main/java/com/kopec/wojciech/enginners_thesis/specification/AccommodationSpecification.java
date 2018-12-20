package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ListPath;

import java.util.List;


public final class AccommodationSpecification {

    private AccommodationSpecification() {
    }

    public static Predicate withCriteria(AccommodationCriteria criteria) {
        return new BooleanBuilder(
                withAccommodationCriteria(criteria))
                .and(withLocalization(criteria.getLocalization()));
    }

    public static Predicate withAccommodationCriteria(AccommodationCriteria criteria) {
        return new BooleanBuilder(
                withNameContains(criteria.getName()))
                .and(withAccommodationType(criteria.getAccommodationType()))
                .and(withMinGuestsOf(criteria.getRequiredGuestCount()))
                .and(withPriceLowerOrEqual(criteria.getPricePerNight()))
                .and(withAmenities(criteria.getAmenities()));
    }

    private static Predicate withNameContains(String name) {
        return name != null ? QAccommodation.accommodation.name.contains(name) : null;
    }

    private static Predicate withAccommodationType(AccommodationType type) {
        return type != null ? QAccommodation.accommodation.accommodationType.eq(type) : null;
    }

    private static Predicate withMinGuestsOf(int requiredGuests) {
        return QAccommodation.accommodation.maxGuests.goe(requiredGuests);
    }

    private static Predicate withPriceLowerOrEqual(int price) {
        return price > 0 ? QAccommodation.accommodation.pricePerNight.loe(price) : null;
    }

    private static Predicate withAmenity(String amenityType) {
        ListPath<Amenity, QAmenity> qAmenities = QAccommodation.accommodation.amenities;
        return qAmenities.any().type.eq(AmenityType.valueOf(amenityType));
    }

    private static Predicate withAmenities(List<String> amenities) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (amenities != null && amenities.size() > 0) {
            for (String amenityType : amenities) {
                booleanBuilder.and(withAmenity(amenityType));
            }
        }
        return booleanBuilder;
    }

    private static Predicate withLocalization(LocalizationDto localization) {
        return localization != null ? LocalizationSpecification.withCriteria(localization) : null;
    }
}




































































