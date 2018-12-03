package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Amenity;
import com.kopec.wojciech.enginners_thesis.model.AmenityType;
import com.kopec.wojciech.enginners_thesis.model.QAccommodation;
import com.kopec.wojciech.enginners_thesis.model.QAmenity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.List;

public class AccommodationSpecification {
    private final AccommodationSearch criteria;

    public AccommodationSpecification(AccommodationSearch criteria) {
        this.criteria = criteria;
    }

    public Predicate toPredicate() {
        return Expressions.asBoolean(true)
                .and(withNameContains(criteria.getName()))
                .and(withAccommodationType(criteria.getAccommodationType()))
                .and(withMinGuestsOf(criteria.getRequiredGuestCount()))
                .and(withPriceLowerOrEqual(criteria.getPricePerNight()))
                .and(criteria.getAmenities().forEach(amenity -> withAmenity(amenity)));

    }

    private static BooleanExpression withNameContains(String name) {
        return QAccommodation.accommodation.name.contains(name);
    }

    private static BooleanExpression withAccommodationType(String type) {
        return QAccommodation.accommodation.accommodationType.eq(type);
    }

    private static BooleanExpression withMinGuestsOf(int requiredGuests) {
        return QAccommodation.accommodation.maxGuests.goe(requiredGuests);
    }

    private static BooleanExpression withPriceLowerOrEqual(int price) {
        return QAccommodation.accommodation.pricePerNight.loe(price);
    }

    private static BooleanExpression withAmenity(String amenity) {
        JPQLQuery<Void> query = new JPAQuery<Void>(em);
        QAccommodation accommodation = QAccommodation.accommodation;
        List<QAmenity> amenities = accommodation.amenities;
        return query.select(accommodation)
                .from(accommodation);
        accommodation.amenities.
                .where(accommodation.amenities("this"))
                .fetch();

        QAmenity.amenity.type.eq(amenity);
        return QAccommodation.accommodation.amenities.getType(AmenityType.parse(amenity));
    }

    public class AmenitySearch {
        QAmenity.
    }
}




































































