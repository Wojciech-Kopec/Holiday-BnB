package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import com.kopec.wojciech.enginners_thesis.model.Amenity;
import com.kopec.wojciech.enginners_thesis.model.QAccommodation;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AccommodationSpecification implements Specification<Accommodation> {
    private final AccommodationSearch criteria;

    public AccommodationSpecification(AccommodationSearch criteria) {
        this.criteria = criteria;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Accommodation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        BooleanExpression predicate = null;
//        final List<Predicate> predicates = new ArrayList<>()
        if (criteria.getName() != null) {
            predicate.and(QAccommodation.accommodation.name.contains(criteria.getName()));
        }
        if (criteria.getAccommodationType() != null) {
            predicate.and(QAccommodation.accommodation.accommodationType.eq(criteria.getAccommodationType()));
        }
        if (criteria.getMaxGuests() != 0) {
            predicate.and(QAccommodation.accommodation.maxGuests.goe(criteria.getMaxGuests()));
        }
        if (criteria.getPricePerNight() != 0) {
            predicate.and(QAccommodation.accommodation.pricePerNight.loe(criteria.getPricePerNight()));
        }
        if (criteria.getAmenities() != null) {
            criteria.getAmenities().forEach(amenity -> predicate.and(QAccommodation.accommodation.amenities.contains(amenity)));
        }
        if (criteria.getLocalizationSearch() != null) {

        }

        return null;
    }

}
