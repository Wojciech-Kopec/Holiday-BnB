package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.Localization;
import com.kopec.wojciech.enginners_thesis.model.QLocalization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.Builder;

public final class LocalizationSpecification {

    private LocalizationSpecification() {
    }

    public static Predicate withCriteria(LocalizationDto criteria) {
        return new BooleanBuilder(
                withCountry(criteria.getCountry()))
                .and(withCity(criteria.getCity()))
                .and(withAddressContains(criteria.getAddress()));
    }

    private static Predicate withCountry(String country) {
        return country != null ? QLocalization.localization.country.eq(country) : null;
    }

    private static Predicate withCity(String city) {
        return city != null ? QLocalization.localization.city.eq(city) : null;
    }

    private static Predicate withAddressContains(String address) {
        return address != null ? QLocalization.localization.address.contains(address) : null;
    }
}
