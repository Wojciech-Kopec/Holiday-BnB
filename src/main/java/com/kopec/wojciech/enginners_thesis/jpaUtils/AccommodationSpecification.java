package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import com.kopec.wojciech.enginners_thesis.model.QAccommodation;
import com.kopec.wojciech.enginners_thesis.model.QAmenity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.ListPath;

import java.util.List;

public class AccommodationSpecification {
    private final AccommodationCriteria criteria;

    public AccommodationSpecification(AccommodationCriteria criteria) {
        this.criteria = criteria;
    }

    public Predicate withCriteria() {
        return Expressions.asBoolean(true)
                .and(withNameContains(criteria.getName()))
                .and(withAccommodationType(criteria.getAccommodationType()))
                .and(withMinGuestsOf(criteria.getRequiredGuestCount()))
                .and(withPriceLowerOrEqual(criteria.getPricePerNight()))
                .and(withAmenities(criteria.getAmenities()));
    }

    private static Predicate withNameContains(String name) {
        return QAccommodation.accommodation.name.contains(name);
    }

    private static Predicate withAccommodationType(String type) {
        return QAccommodation.accommodation.accommodationType.eq(type);
    }

    private static Predicate withMinGuestsOf(int requiredGuests) {
        return QAccommodation.accommodation.maxGuests.goe(requiredGuests);
    }

    private static Predicate withPriceLowerOrEqual(int price) {
        return QAccommodation.accommodation.pricePerNight.loe(price);
    }

    private static Predicate withAmenity(String amenityType) {
        ListPath<Amenity, QAmenity> qAmenities = QAccommodation.accommodation.amenities;
        return qAmenities.any().type.eq(amenityType);
    }

    private static Predicate withAmenities(List<String> amenities) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String amenityType : amenities) {
            booleanBuilder.and(withAmenity(amenityType));
        }
        return booleanBuilder;
    }
}




































































