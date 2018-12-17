package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.Localization;
import com.kopec.wojciech.enginners_thesis.model.QLocalization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.Builder;

public final class LocalizationSpecification {

    private LocalizationSpecification() {}

    public static Predicate withCriteria(LocalizationDto criteria) {
        return new BooleanBuilder(
                withCountry(criteria.getCountry()))
                .and(withState(criteria.getState()))
                .and(withCity(criteria.getCity()))
                .and(withAddressContains(criteria.getAddress()));
    }

    private static Predicate withCountry(String country) {
        return QLocalization.localization.country.eq(country);
    }

    private static Predicate withState(String state) {
        return QLocalization.localization.state.eq(state);
    }

    private static Predicate withCity(String city) {
        return QLocalization.localization.city.eq(city);
    }

    private static Predicate withAddressContains(String address) {
        return QLocalization.localization.address.contains(address);
    }
}
