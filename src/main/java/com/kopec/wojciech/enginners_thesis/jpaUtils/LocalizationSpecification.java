package com.kopec.wojciech.enginners_thesis.jpaUtils;

import com.kopec.wojciech.enginners_thesis.model.Localization;
import com.kopec.wojciech.enginners_thesis.model.QLocalization;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

public class LocalizationSpecification {
    private Localization criteria;

    public LocalizationSpecification(Localization localization) {
        this.criteria = localization;
    }

    public Predicate withCriteria() {
        return Expressions.asBoolean(true)
                .and(withCountry(criteria.getCountry()))
                .and(withState(criteria.getState()))
                .and(withCity(criteria.getCity()))
                .and(withAddressContains(criteria.getAddress()));
    }

    private Predicate withCountry(String country) {
        return QLocalization.localization.country.eq(country);
    }

    private Predicate withState(String state) {
        return QLocalization.localization.state.eq(state);
    }

    private Predicate withCity(String city) {
        return QLocalization.localization.city.eq(city);
    }

    private Predicate withAddressContains(String address) {
        return QLocalization.localization.address.contains(address);
    }
}
