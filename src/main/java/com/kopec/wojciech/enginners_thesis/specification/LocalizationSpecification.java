package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.model.QLocalization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class LocalizationSpecification {

    private LocalizationSpecification() {
    }

    public static Predicate withCriteria(String country, String city) {
        return new BooleanBuilder(
                withCountry(country))
                .and(withCity(city));
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
