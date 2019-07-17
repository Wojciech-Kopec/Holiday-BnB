package com.kopec.wojciech.engineers_thesis.specification;

import com.kopec.wojciech.engineers_thesis.model.*;
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
                .and(withLocalization(criteria.getCountry(), criteria.getCity()));
    }

    private static Predicate withAccommodationCriteria(AccommodationCriteria criteria) {
        return new BooleanBuilder(
                withNameContains(criteria.getName()))
                .and(withAccommodationTypes(criteria.getAccommodationTypes()))
                .and(withMinGuestsOf(criteria.getRequiredGuestCount()))
                .and(withPriceBetween(criteria.getMinPricePerNight(), criteria.getMaxPricePerNight()))
                .and(withAmenities(criteria.getAmenities()));
    }

    private static Predicate withNameContains(String name) {
        return name != null ? QAccommodation.accommodation.name.contains(name) : null;
    }

    private static Predicate withAccommodationTypes(List<Accommodation.AccommodationType> types) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (types != null && types.size() > 0) {
            for (Accommodation.AccommodationType type : types) {
                booleanBuilder.or(withAccommodationType(type));
            }
        }
        return booleanBuilder;
    }

    private static Predicate withAccommodationType(Accommodation.AccommodationType type) {
        return type != null ? QAccommodation.accommodation.accommodationType.eq(type) : null;
    }

    private static Predicate withMinGuestsOf(Integer requiredGuests) {
        return requiredGuests != null ? QAccommodation.accommodation.maxGuests.goe(requiredGuests) : null;
    }

    private static Predicate withPriceBetween(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null && maxPrice > minPrice) {
            return QAccommodation.accommodation.pricePerNight.between(minPrice, maxPrice);
        } else {
            return null;
        }
    }

    private static Predicate withAmenity(String amenityType) {
        ListPath<Amenity, QAmenity> qAmenities = QAccommodation.accommodation.amenities;
        return qAmenities.any().type.eq(Amenity.AmenityType.valueOf(amenityType));
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

    private static Predicate withLocalization(String country, String city) {
        return LocalizationSpecification.withCriteria(country, city);
    }
}




































































